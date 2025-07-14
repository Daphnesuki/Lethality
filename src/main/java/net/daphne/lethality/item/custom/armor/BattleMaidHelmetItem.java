package net.daphne.lethality.item.custom.armor;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.util.ModUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.Nullable;
import java.util.List;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;
import static net.daphne.lethality.util.ModUtils.addColorGradientTextWithFont;

public class BattleMaidHelmetItem extends ArmorItem {
    public BattleMaidHelmetItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f, // velocidad y expansión
                new int[]{254, 244, 255},
                new int[]{255, 229, 246},
                new int[]{255, 216, 238},
                new int[]{255, 204, 233},
                new int[]{255, 186, 225},
                new int[]{255, 186, 225},
                new int[]{255, 204, 233},
                new int[]{255, 216, 238},
                new int[]{255, 229, 246},
                new int[]{254, 244, 255}
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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.bm_helmet.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced1"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced2"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced3"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced4"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced5"));
        pTooltipComponents.add(Component.literal(" "));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced6"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced7"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (!level.isClientSide) {
            if (player.hasEffect(ModMobEffects.BMMODE.get())) {
                return;
            }

            MobEffectInstance current = player.getEffect(ModMobEffects.CUTENESS.get());

            if (current == null) {
                // Si no tiene el efecto, se lo damos con nivel 0
                player.addEffect(new MobEffectInstance(ModMobEffects.CUTENESS.get(), 0, 0, false, false, true));
            } else {
                ModUtils.applyEffect(
                        new MobEffectInstance(ModMobEffects.CUTENESS.get(), 0, 0, false, false, true),
                        stack,
                        player,
                        null
                );
            }
        }
        super.onArmorTick(stack, level, player);
    }
}
