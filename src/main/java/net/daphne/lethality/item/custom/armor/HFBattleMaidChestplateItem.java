package net.daphne.lethality.item.custom.armor;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;
import static net.daphne.lethality.util.ModUtils.addColorGradientTextWithFont;

public class HFBattleMaidChestplateItem extends ArmorItem {
    public HFBattleMaidChestplateItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f, // velocidad y expansión
                new int[]{235, 13, 209},
                new int[]{255, 98, 207},
                new int[]{255, 135, 199},
                new int[]{255, 172, 216},
                new int[]{255, 197, 229},
                new int[]{255, 241, 254},
                new int[]{255, 241, 254},
                new int[]{255, 197, 229},
                new int[]{255, 172, 216},
                new int[]{255, 135, 199},
                new int[]{255, 98, 207},
                new int[]{235, 13, 209}
        ).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        ResourceLocation VERMIN = new ResourceLocation(LethalityMod.MOD_ID, "vermin"); // Font used: Alagard https://www.dafont.com/alagard.font
        ResourceLocation MKART = new ResourceLocation(LethalityMod.MOD_ID, "mkart");
        ResourceLocation CALAMITOUS = new ResourceLocation(LethalityMod.MOD_ID, "homicide");

        Component legendaryText = addColorGradientTextWithFont(
                Component.literal("silly"), MKART,
                0.5f,
                5.0f,
                new int[]{255, 0, 0},     // rojo
                new int[]{255, 165, 0},   // naranja
                new int[]{255, 255, 0},   // amarillo
                new int[]{0, 255, 0},     // verde
                new int[]{0, 127, 255},   // celeste
                new int[]{0, 0, 255},     // azul
                new int[]{139, 0, 255}    // violeta
        );

        pTooltipComponents.add(legendaryText);

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_bm_chestplate.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced1"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced2"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced3"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced4"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced5"));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced6"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced7"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (player.hasEffect(ModMobEffects.HFMMODE.get())) {
                return;
            }
            MobEffectInstance current = player.getEffect(ModMobEffects.CUTENESS.get());

            if (current == null) {
                player.addEffect(new MobEffectInstance(ModMobEffects.CUTENESS.get(), 0, 1, false, false, true));
            } else {
                ModUtils.applyEffectPlus(
                        new MobEffectInstance(ModMobEffects.CUTENESS.get(), 0, 0, false, false, true),
                        stack,
                        player,
                        null,
                        2
                );
            }
        }
        super.onArmorTick(stack, level, player);
    }
}
