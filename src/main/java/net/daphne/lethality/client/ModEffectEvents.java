package net.daphne.lethality.client;

import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.custom.NightfallItem;
import net.daphne.lethality.particles.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mod.EventBusSubscriber(modid = "lethality")
public class ModEffectEvents {

    private static final List<DelayedDamage> damageQueue = new CopyOnWriteArrayList<>();

    @SubscribeEvent
    public static void onPlayerDealsDamage(LivingHurtEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;
        if (!player.hasEffect(ModMobEffects.BRIMSTONE_FLAMES_BUFF.get())) return;

        float originalDamage = event.getAmount();

        // Aumentar el daño en un 100%
        float amplifiedDamage = originalDamage * 2.0f;
        event.setAmount(amplifiedDamage);

        // Robo de vida: sanar al jugador un 20% del daño infligido
        float lifeSteal = amplifiedDamage * 0.2f;
        player.heal(lifeSteal);

        // Opcional: efecto visual o sonido
        Level level = player.level();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.FALLING_DRIPSTONE_LAVA, player.getX(), player.getY() + 1.0, player.getZ(), 10, 0.2, 0.5, 0.2, 0.1);
            serverLevel.playSound(null, player.blockPosition(), SoundEvents.BLAZE_SHOOT, SoundSource.PLAYERS, 0.6f, 1.2f);
        }
    }


    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.hasEffect(ModMobEffects.CRUMBLING.get())) {
            float damage = event.getAmount();
            float newDamage;

            if (entity instanceof Player) {
                // Jugador: daño x1 / 0.7 = ~1.43x
                newDamage = damage / 0.7f;
            } else {
                // Mobs: daño x1 / 0.92 = ~1.087x
                newDamage = damage / 0.92f;
            }

            event.setAmount(newDamage);
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent event) {

        if (event == null || event.getEntity() == null) return;

        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        BlockPos pos = target.blockPosition();

        if (!(attacker instanceof LivingEntity livingAttacker)) return;

        MobEffectInstance effect = livingAttacker.getEffect(ModMobEffects.RAGE.get());
        if (effect == null) return;

        float originalDamage = event.getAmount();
        float multiplier = 2.0f;
        event.setAmount(originalDamage * multiplier);
        target.invulnerableTime = 0;

        Level level = target.level();
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.playSound(null, pos, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0f, 1.0f);
            serverLevel.sendParticles(
                    ModParticles.REAL_CRIT.get(),
                    target.getX(), target.getY() + 1.0, target.getZ(),
                    20, 0.3, 0.5, 0.3, 0.2);
        } else {
            level.playLocalSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0f, 1.0f, false);
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        List<DelayedDamage> toRemove = new ArrayList<>();
        for (DelayedDamage delayed : damageQueue) {
            delayed.ticks--;
            if (delayed.ticks <= 0) {
                if (delayed.target.isAlive()) {
                    delayed.target.invulnerableTime = 0;
                    delayed.target.hurt(
                            delayed.target.damageSources().indirectMagic(delayed.attacker, delayed.attacker),
                            delayed.amount);
                }
                toRemove.add(delayed);
            }
        }
        damageQueue.removeAll(toRemove);
    }

    private static class DelayedDamage {
        public final LivingEntity target;
        public final LivingEntity attacker;
        public final float amount;
        public int ticks;

        public DelayedDamage(LivingEntity target, LivingEntity attacker, float amount, int ticks) {
            this.target = target;
            this.attacker = attacker;
            this.amount = amount;
            this.ticks = ticks;
        }
    }

    @SubscribeEvent
    public static void onMobTargetChange(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof Mob mob)) return;

        Entity newTarget = event.getNewTarget();
        if (!(newTarget instanceof LivingEntity target)) return;

        if (target.hasEffect(ModMobEffects.TRANQUILITY.get())) {
            event.setNewTarget(null);
            mob.setTarget(null);
            mob.getNavigation().stop();
            mob.getBrain().eraseMemory(net.minecraft.world.entity.ai.memory.MemoryModuleType.ATTACK_TARGET);
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.level().isClientSide || event.phase != TickEvent.Phase.END) return;

        ItemStack mainhand = player.getMainHandItem();
        boolean hasIronWill = player.hasEffect(ModMobEffects.IRON_WILL.get());

        CompoundTag data = player.getPersistentData();
        boolean hadNightfall = data.getBoolean("HadNightfallEquipped");
        boolean currentlyHasNightfall = mainhand.getItem() instanceof NightfallItem;

        if (hasIronWill && hadNightfall && !currentlyHasNightfall) {
            float absorption = player.getAbsorptionAmount();
            float heal = absorption * 0.6f;

            player.heal(heal);
            player.setAbsorptionAmount(0);
            player.removeEffect(ModMobEffects.IRON_WILL.get());

            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("lethality", "nightfall_shield_recast"));
            float pitch = Mth.nextFloat(player.getRandom(), 0.8F, 1.2F);
            float volume = 1F;
            player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);

            if (player.getOffhandItem().getItem() instanceof NightfallItem nightfall) {
                NightfallItem.resetShield(player.getOffhandItem());
            }
        }
        data.putBoolean("HadNightfallEquipped", currentlyHasNightfall);
    }

}
