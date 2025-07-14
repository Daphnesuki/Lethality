package net.daphne.lethality.potion;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class BrimstoneFlamesBuffEffect extends MobEffect {
    public BrimstoneFlamesBuffEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF3B00); // color rojo
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level level = entity.level();

        if (!level.isClientSide) {
            float damage = 4.0F * (amplifier + 1);
            entity.hurt(new DamageSource(level.registryAccess()
                    .registryOrThrow(Registries.DAMAGE_TYPE)
                    .getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE,
                            new ResourceLocation("lethality:brimstone_flames")))), damage);
        }

        // Partículas: enviar desde el servidor para que siempre aparezcan
        if (!level.isClientSide) {
            for (int i = 0; i < 3 + amplifier; i++) {
                double offsetX = (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();
                double offsetY = entity.getRandom().nextDouble() * entity.getBbHeight();
                double offsetZ = (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();

                ((ServerLevel) level).sendParticles(ParticleTypes.SMOKE,
                        entity.getX() + offsetX,
                        entity.getY() + offsetY,
                        entity.getZ() + offsetZ,
                        1, 0, 0, 0, 0);
                ((ServerLevel) level).sendParticles(ParticleTypes.FALLING_LAVA,
                        entity.getX() + offsetX,
                        entity.getY() + offsetY,
                        entity.getZ() + offsetZ,
                        1, 0, 0, 0, 0);
                ((ServerLevel) level).sendParticles(ParticleTypes.FLAME,
                        entity.getX() + offsetX,
                        entity.getY() + offsetY,
                        entity.getZ() + offsetZ,
                        1, 0, 0, 0, 0);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // ejecuta `applyEffectTick` cada tick
    }
}
