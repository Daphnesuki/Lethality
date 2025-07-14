package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.item.custom.NightfallItem;
import net.daphne.lethality.particles.ModParticles;
import net.daphne.lethality.util.ModUtils;
import net.mcreator.terramity.init.TerramityModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Set;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class GamblersBladeEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        DamageSource source = event.getSource();
        Entity attacker = source.getEntity();

        if (!(attacker instanceof LivingEntity livingAttacker)) return;

        ItemStack mainHand = livingAttacker.getMainHandItem();
        if (!mainHand.is(ModItems.GAMBLERS_BLADE.get())) return;

        Set<Item> gamblingCurios = Set.of(
                TerramityModItems.POKER_CHIP_BRACELETS.get(),
                TerramityModItems.GAMBLERS_RING.get(),
                TerramityModItems.WEIGHTED_DIE.get(),
                TerramityModItems.DEVILS_DICE.get(),
                TerramityModItems.LUCKY_DICE.get(),
                TerramityModItems.DIE_OF_REVIVAL.get(),
                TerramityModItems.NULLIFYING_DICE.get(),
                TerramityModItems.FATEFUL_COIN.get()
        );

        final int[] equippedCount = {0};

        CuriosApi.getCuriosHelper().getEquippedCurios(livingAttacker).ifPresent(curios -> {
            for (int i = 0; i < curios.getSlots(); i++) {
                ItemStack stack = curios.getStackInSlot(i);
                if (!stack.isEmpty() && gamblingCurios.contains(stack.getItem())) {
                    equippedCount[0]++;
                }
            }
        });

        if (equippedCount[0] > 0) {
            float originalDamage = event.getAmount();
            float multiplier = 1.0f + (0.25f * equippedCount[0]);
            float boostedDamage = originalDamage * multiplier;
            event.setAmount(boostedDamage);
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingHurtEvent event) {

        if (event == null || event.getEntity() == null) return;

        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        BlockPos pos = target.blockPosition();

        if (!(attacker instanceof LivingEntity livingAttacker)) return;

        // --- Jackpot crítico basado en Blood Coins ---
        if (livingAttacker instanceof Player player) {
            ItemStack mainhand = player.getMainHandItem();
            if (mainhand.getItem() == ModItems.GAMBLERS_BLADE.get()) {
                int bloodCoins = ModUtils.countBloodCoins(player);

                float chance = bloodCoins; // 1% por coin
                float roll = player.getRandom().nextFloat() * 100f;

                // Mostrar mensaje de debug en el chat
//                player.sendSystemMessage(Component.literal(
//                        "§6[DEBUG] §cBlood Coins: " + bloodCoins +
//                                " | §eRoll: " + String.format("%.2f", roll) +
//                                " | §aChance: " + chance + "%"
//                ));

                if (roll < chance) {
                    float baseDamage = event.getAmount();
                    float criticalDamage = baseDamage * 2.5f;
                    event.setAmount(criticalDamage);
                    target.invulnerableTime = 0;

                    int toConsume = Mth.clamp(player.getRandom().nextInt(7) + 1, 1, bloodCoins);
                    ModUtils.consumeBloodCoins(player, toConsume);

                    // Mensaje de JACKPOT en dorado
//                    player.sendSystemMessage(Component.literal("§6✦ JACKPOT CRIT! ✦"));

                    if (player.level() instanceof ServerLevel serverLevel) {
                        serverLevel.playSound(null, pos, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.PLAYERS, 1.0f, 1.2f);
                        serverLevel.sendParticles(
                                ModParticles.REAL_CRIT.get(),
                                target.getX(), target.getY() + 1.0, target.getZ(),
                                30, 0.3, 0.5, 0.3, 0.3);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;

        ItemStack mainhand = player.getMainHandItem();
        if (!mainhand.is(ModItems.GAMBLERS_BLADE.get())) return;

        LivingEntity target = event.getEntity();

        // Soltar la moneda solo si no es un jugador y no es cliente
        if (!target.level().isClientSide && !(target instanceof Player)) {
            ItemStack drop = new ItemStack(ModItems.BLOOD_COIN.get());
            target.spawnAtLocation(drop);
        }
    }

}