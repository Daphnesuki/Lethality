package net.daphne.lethality.potion;

import net.daphne.lethality.particles.ModParticles;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class AcidVenomEffect extends MobEffect {
    public AcidVenomEffect() {
        super(MobEffectCategory.HARMFUL, 0XCBEE5D); // color rojo
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level level = entity.level();

        if (!level.isClientSide) {
            float damage = 2.0F * (amplifier + 1);
            entity.hurt(new DamageSource(level.registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE,
                            new ResourceLocation("lethality:acid_venom")))), damage);
        }

        if (!level.isClientSide && entity.getRandom().nextFloat() < 0.25f) { // 20% chance
            double offsetX = (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();
            double offsetY = entity.getRandom().nextDouble() * entity.getBbHeight();
            double offsetZ = (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();

            ((ServerLevel) level).sendParticles(ModParticles.ACIDIC_BUBBLE_FORK.get(),
                    entity.getX() + offsetX,
                    entity.getY() + offsetY,
                    entity.getZ() + offsetZ,
                    1, 0, 0, 0, 0);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // ejecuta `applyEffectTick` cada tick
    }
}
