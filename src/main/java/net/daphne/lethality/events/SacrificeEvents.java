package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.item.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public class SacrificeEvents {

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {

        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();

        if (source instanceof Player player) {
            ItemStack held = player.getMainHandItem();
            if (held.is(ModItems.SACRIFICE.get())) {

                // Verificamos si es desde la espalda
                if (isAttackFromBehind(player, target)) {
                    event.setAmount(event.getAmount() * 3.0f);
                    target.invulnerableTime = 0; // por si querés evitar inmunidad
                }
            }
        }
    }

    private static boolean isAttackFromBehind(Entity attacker, LivingEntity target) {
        // Dirección hacia la que el objetivo está mirando (su yaw)
        double targetYaw = wrapAngleTo180(target.getYRot());

        // Dirección desde el objetivo hacia el atacante
        double angleToAttacker = Math.toDegrees(Math.atan2(
                attacker.getX() - target.getX(),
                attacker.getZ() - target.getZ()
        ));
        angleToAttacker = wrapAngleTo180(angleToAttacker);

        return Math.abs(wrapAngleTo180(angleToAttacker - targetYaw)) > 135;
    }

    private static double wrapAngleTo180(double angle) {
        angle = angle % 360;
        if (angle >= 180) angle -= 360;
        if (angle < -180) angle += 360;
        return angle;
    }
}