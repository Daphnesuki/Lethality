package net.daphne.lethality.item.custom;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.util.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.daphne.lethality.item.ModToolTiers;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;
import static net.daphne.lethality.util.ModUtils.addColorGradientTextWithFont;

public class CausticEdgeItem extends SwordItem {
    public CausticEdgeItem(Properties pProperties) {
        super(ModToolTiers.TAINTED, 9, -2.4f, new Item.Properties());
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ModRarities.CALAMITOUS;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,
                new int[]{255, 75, 0},    // naranja rojizo fuerte (base baja)
                new int[]{255, 120, 0},   // naranja medio
                new int[]{255, 160, 0},   // naranja dorado
                new int[]{255, 185, 0},   // amarillo anaranjado claro
                new int[]{255, 205, 0}    // amarillo dorado (base alta)
        ).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        ResourceLocation VERMIN = new ResourceLocation(LethalityMod.MOD_ID, "vermin");
        ResourceLocation MKART = new ResourceLocation(LethalityMod.MOD_ID, "mkart");
        ResourceLocation STRANGE = new ResourceLocation(LethalityMod.MOD_ID, "grapesoda");
        ResourceLocation CALAMITOUS = new ResourceLocation(LethalityMod.MOD_ID, "homicide");

        Component legendaryText = addColorGradientTextWithFont(
                Component.literal("Calamitous"), CALAMITOUS,
                0.25f,
                5.0f,
                new int[]{255, 254, 251},
                new int[]{255, 242, 203},
                new int[]{255, 221, 177},
                new int[]{255, 202, 153},
                new int[]{255, 172, 131},
                new int[]{255, 156, 119},
                new int[]{255, 139, 107},
                new int[]{252, 114, 94},
                new int[]{251, 101, 87},
                new int[]{235, 75, 76},
                new int[]{197, 32, 57},
                new int[]{235, 75, 76},
                new int[]{251, 101, 87},
                new int[]{252, 114, 94},
                new int[]{255, 139, 107},
                new int[]{255, 156, 119},
                new int[]{255, 172, 131},
                new int[]{255, 202, 153},
                new int[]{255, 221, 177},
                new int[]{255, 254, 251}
        );

        pTooltipComponents.add(legendaryText);

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.translatable("tooltip.lethality.caustic_edge.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.caustic_edge.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.caustic_edge.advanced2"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        int currentAmplifier = 0;

        // Verificamos si ya tiene el efecto
        if (pTarget.hasEffect(ModMobEffects.ACID_VENOM.get())) {
            currentAmplifier = pTarget.getEffect(ModMobEffects.ACID_VENOM.get()).getAmplifier() + 1;
        }

        // Limita el amplifier a un máximo de 9
        currentAmplifier = Math.min(currentAmplifier, 0);

        // Aplica el efecto con nuevo amplifier
        pTarget.addEffect(new MobEffectInstance(ModMobEffects.ACID_VENOM.get(), 40, currentAmplifier), pAttacker);
        pTarget.invulnerableTime = 0;
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
