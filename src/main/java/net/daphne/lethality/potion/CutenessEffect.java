package net.daphne.lethality.potion;

import net.daphne.lethality.particles.ModParticles;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class CutenessEffect extends MobEffect implements IStackingEffect {
    private static final UUID CUTENESS_DAMAGE_UUID = UUID.fromString("17c9f9f2-c3f3-4bda-a06a-59bb616c1e32");

    public CutenessEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFBDCFD);

        this.addAttributeModifier(
                Attributes.ATTACK_DAMAGE,
                CUTENESS_DAMAGE_UUID.toString(),
                0.05, // se multiplica por (amplifier + 1)
                AttributeModifier.Operation.MULTIPLY_TOTAL
        );
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Tu lógica de partículas, si querés
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public void onIncreasedTo(MobEffectInstance instance, ItemStack source, LivingEntity target, Level level) {
        if (!level.isClientSide && target.getRandom().nextFloat() < 0.01f) {
            ((ServerLevel) level).sendParticles(ModParticles.CUTE_SPARKLES.get(),
                    target.getX(), target.getY() + 1, target.getZ(),
                    1 + instance.getAmplifier(),
                    0.2, 0.4, 0.2,
                    0.05);
        }
    }
}