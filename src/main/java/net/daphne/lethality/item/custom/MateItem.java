package net.daphne.lethality.item.custom;

import net.daphne.lethality.init.ModSounds;
import net.daphne.lethality.item.ModFoods;
import net.mcreator.terramity.init.TerramityModMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;

public class MateItem extends Item {
    public MateItem() {
        super(new Item.Properties()
                .food(ModFoods.MATE)
                .stacksTo(1));
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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.mate.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.mate.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.mate.advanced2"));
            pTooltipComponents.add(Component.literal(" "));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
            pTooltipComponents.add(Component.literal(" "));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.GENERIC_DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 24; // Puedes ajustar esto
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player player) {
            float pitch = Mth.nextFloat(RandomSource.create(), 0.75F, 1.25F);
            float volume = 0.55F;

            if (!player.getAbilities().instabuild) {
                player.getCooldowns().addCooldown(this, 250);
            }

            player.getFoodData().eat(4, 0.5F); // Ejemplo
            player.addEffect(new MobEffectInstance(TerramityModMobEffects.AMPED.get(), 160));

            level.playSound(null, player.blockPosition(),
                    ModSounds.MATE_DRINK.get(), SoundSource.PLAYERS,
                    volume, pitch);
        }

        return stack.isEmpty() ? ItemStack.EMPTY : stack;
    }
}

