package net.daphne.lethality.item;

import net.daphne.lethality.init.ModMobEffects;
import net.mcreator.terramity.init.TerramityModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties STRAWBERRY = new FoodProperties.Builder().nutrition(2).fast()
            .saturationMod(0.2f).effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200), 0.1f).build();
    public static final FoodProperties FAIRY_PICKLE = new FoodProperties.Builder().nutrition(4).fast()
            .saturationMod(0.5f).effect(() -> new MobEffectInstance(TerramityModMobEffects.AMPED.get(), 100), 0.1f).build();
    public static final FoodProperties MATE = new FoodProperties.Builder().nutrition(4).fast()
            .saturationMod(0.35f).effect(() -> new MobEffectInstance(TerramityModMobEffects.AMPED.get(), 160), 1f).build();
    public static final FoodProperties MATE_DULCE = new FoodProperties.Builder().nutrition(6).fast()
            .saturationMod(0.35f).effect(() -> new MobEffectInstance(ModMobEffects.TRANQUILITY.get(), 160), 1f).build();
    public static final FoodProperties ALFAJOR_DE_MAICENA = new FoodProperties.Builder().nutrition(8).fast()
            .saturationMod(0.4f).effect(() -> new MobEffectInstance(MobEffects.HEAL), 1f).build();
}
