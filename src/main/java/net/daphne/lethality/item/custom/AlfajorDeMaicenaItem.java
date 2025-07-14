package net.daphne.lethality.item.custom;

import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModFoods;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;

public class AlfajorDeMaicenaItem extends Item {
    public AlfajorDeMaicenaItem() {
        super(new Item.Properties()
                .food(ModFoods.ALFAJOR_DE_MAICENA)
                .stacksTo(64));
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,
                new int[]{0, 255, 255},
                new int[]{161, 255, 255},
                new int[]{185, 255, 255},
                new int[]{208, 255, 255},
                new int[]{238, 255, 255},
                new int[]{254, 255, 255},
                new int[]{255, 205, 0},
                new int[]{254, 255, 255},
                new int[]{238, 255, 255},
                new int[]{208, 255, 255},
                new int[]{185, 255, 255},
                new int[]{161, 255, 255}
        ).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add(Component.translatable("tooltip.lethality.alfajor_de_maicena.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.alfajor_de_maicena.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.alfajor_de_maicena.advanced2"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 24; // Puedes ajustar esto
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        if (!level.isClientSide && entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 0, 0), player);
        }
        return result;
    }
}

