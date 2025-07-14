package net.daphne.lethality.client;

import net.daphne.lethality.init.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "lethality")
public class BladeModeEvents {

    private static final UUID BLADE_MODE_SPEED_ID = UUID.fromString("d3e76a14-5b9e-4b41-a421-d0a7a9c979a8");
    private static final UUID BLADE_MODE_ATTACK_SPEED_ID = UUID.fromString("3e4a8e2f-6e6a-4a9f-91fc-b4bb51d7c1e2");

    private static final AttributeModifier SPEED_MODIFIER = new AttributeModifier(
            BLADE_MODE_SPEED_ID, "Blade Mode Speed", 0.75, AttributeModifier.Operation.MULTIPLY_TOTAL);

    private static final AttributeModifier ATTACK_SPEED_MODIFIER = new AttributeModifier(
            BLADE_MODE_ATTACK_SPEED_ID, "Blade Mode Attack Speed", 0.75, AttributeModifier.Operation.MULTIPLY_TOTAL);

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        if (player.level().isClientSide) return;

        boolean hasBladeMode = player.hasEffect(ModMobEffects.BLADE_MODE.get());

        if (hasBladeMode) {
            AABB area = player.getBoundingBox().inflate(5);
            List<LivingEntity> targets = player.level().getEntitiesOfClass(LivingEntity.class, area,
                    e -> e != player && e.isAlive() && e instanceof Mob);

            for (LivingEntity target : targets) {
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 2, false, false));
            }

            if (!player.getAttribute(Attributes.MOVEMENT_SPEED).hasModifier(SPEED_MODIFIER)) {
                player.getAttribute(Attributes.MOVEMENT_SPEED).addTransientModifier(SPEED_MODIFIER);
            }
            if (!player.getAttribute(Attributes.ATTACK_SPEED).hasModifier(ATTACK_SPEED_MODIFIER)) {
                player.getAttribute(Attributes.ATTACK_SPEED).addTransientModifier(ATTACK_SPEED_MODIFIER);
            }

        } else {
            player.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(BLADE_MODE_SPEED_ID);
            player.getAttribute(Attributes.ATTACK_SPEED).removeModifier(BLADE_MODE_ATTACK_SPEED_ID);
        }
    }


}
