package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Iterator;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class ViolenceEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();
        float damage = event.getAmount();

        if (source instanceof Player player) {
            ItemStack held = player.getMainHandItem();
            if (held.is(ModItems.VIOLENCE.get())) {
                target.invulnerableTime = 0;
                // Guardar para segundo golpe
                ViolenceDamageScheduler.schedule(target, damage, 4);
            }
        }
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        ViolenceDamageScheduler.flushNew();

        Iterator<ScheduledViolence> iterator = ViolenceDamageScheduler.scheduled.iterator();

        while (iterator.hasNext()) {
            ScheduledViolence scheduled = iterator.next();
            scheduled.ticksRemaining--;

            if (scheduled.ticksRemaining <= 0) {
                for (ServerLevel level : ServerLifecycleHooks.getCurrentServer().getAllLevels()) {
                    Entity entity = level.getEntity(scheduled.targetUUID);
                    if (entity instanceof LivingEntity target) {
                        if (target.getLastHurtByMob() instanceof Player player) {
                            ItemStack held = player.getMainHandItem();
                            if (held.is(ModItems.VIOLENCE.get())) {
                                target.invulnerableTime = 0;
                                DamageSource delayedSource = target.level().damageSources().playerAttack(player);
                                target.hurt(delayedSource, scheduled.damage);
                            }
                        }
                    }
                }
                iterator.remove(); // ✅ eliminación segura
            }
        }
    }

}