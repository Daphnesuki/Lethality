package net.daphne.lethality.potion;

import com.mrcrayfish.configured.platform.Services;
import net.daphne.lethality.particles.ModParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class TranquilityEffect extends MobEffect implements IStackingEffect {
    public TranquilityEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00FFFF);
    }

    @Override
    public void onIncreasedTo(MobEffectInstance instance, ItemStack source, LivingEntity target, Level level) {
        //maybe gonna add something later :]
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level level = entity.level();

        if (!level.isClientSide) {

        }

        // Partículas: enviar desde el servidor para que siempre aparezcan
        if (!level.isClientSide) {
            double range = 1.5;

            double offsetX = (entity.getRandom().nextDouble() - 0.5) * range * 2;
            double offsetY = (entity.getRandom().nextDouble() - 0.5) * range * 2;
            double offsetZ = (entity.getRandom().nextDouble() - 0.5) * range * 2;

            ((ServerLevel) level).sendParticles(ModParticles.TRANQUILITY.get(),
                    entity.getX() + offsetX,
                    entity.getY() + offsetY,
                    entity.getZ() + offsetZ,
                    1, 0, 0, 0, 0);
        }

        // Forzar a que todos los mobs cercanos ignoren al jugador
        double checkRadius = 16.0;
        for (Mob mob : level.getEntitiesOfClass(Mob.class, entity.getBoundingBox().inflate(checkRadius))) {
            if (mob.getTarget() == entity) {
                mob.setTarget(null);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // ejecuta `applyEffectTick` cada tick
    }
}
