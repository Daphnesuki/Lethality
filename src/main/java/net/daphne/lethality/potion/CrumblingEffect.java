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
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;

public class CrumblingEffect extends MobEffect {
    public CrumblingEffect() {
        super(MobEffectCategory.HARMFUL, 0xFF484563);
        this.addAttributeModifier(Attributes.ARMOR,
                "b98c17a9-fc8c-45f2-b3d9-8c9f4d457fbe", // UUID como string
                -10.0,
                AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level level = entity.level();
        if (!level.isClientSide && entity.getRandom().nextFloat() < 0.25f) { // 20% chance
            double offsetX = (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();
            double offsetY = entity.getRandom().nextDouble() * entity.getBbHeight();
            double offsetZ = (entity.getRandom().nextDouble() - 0.5) * entity.getBbWidth();

            ((ServerLevel) level).sendParticles(ModParticles.CRUMBLING.get(),
                    entity.getX() + offsetX,
                    entity.getY() + offsetY,
                    entity.getZ() + offsetZ,
                    1, 0, 0, 0, 0);
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
