package net.daphne.lethality.item;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {
    public static final Tier TAINTED = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1850, 0f, 0f, 25,
                    ModTags.Blocks.ANCIENT_WEAPON, () -> Ingredient.of(ModItems.TAINTED_BLADE.get())),
            new ResourceLocation(LethalityMod.MOD_ID, "tainted"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier DEMONIC = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1850, 0f, 0f, 25,
                    ModTags.Blocks.ANCIENT_WEAPON, () -> Ingredient.of(ModItems.BLADECREST_OATHSWORD.get())),
            new ResourceLocation(LethalityMod.MOD_ID, "demonic"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier BIOME = TierSortingRegistry.registerTier(
            new ForgeTier(5, 1850, 0f, 0f, 25,
                    ModTags.Blocks.ANCIENT_WEAPON, () -> Ingredient.of(ModItems.BROKEN_BIOME_BLADE.get())),
            new ResourceLocation(LethalityMod.MOD_ID, "biome"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier SILLY = TierSortingRegistry.registerTier(
            new ForgeTier(5, 4350, 0f, 0f, 25,
                    ModTags.Blocks.ANCIENT_WEAPON, () -> Ingredient.of(ModItems.STARLIGHT.get())),
            new ResourceLocation(LethalityMod.MOD_ID, "silly"), List.of(Tiers.NETHERITE), List.of());
    public static final Tier CALAMITAS = TierSortingRegistry.registerTier(
            new ForgeTier(5, 4350, 0f, 0f, 25,
                    ModTags.Blocks.ANCIENT_WEAPON, () -> Ingredient.of(ModItems.VIOLENCE.get())),
            new ResourceLocation(LethalityMod.MOD_ID, "calamitas"), List.of(Tiers.NETHERITE), List.of());
}
