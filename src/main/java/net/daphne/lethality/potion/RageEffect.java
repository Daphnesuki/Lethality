package net.daphne.lethality.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class RageEffect extends MobEffect {
    public RageEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFF0000); // color rojo
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        // Puedes dejar esto vacío si no necesitas efectos por tick.
        // O añadir efectos adicionales como fuego, velocidad, partículas, etc.
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false; // false si no necesitas tick constante (evita lag innecesario)
    }
}