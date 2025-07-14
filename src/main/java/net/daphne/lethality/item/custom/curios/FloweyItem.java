package net.daphne.lethality.item.custom.curios;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;
import static net.daphne.lethality.util.ModUtils.addColorGradientTextWithFont;

public class FloweyItem extends Item implements ICurioItem {

    public FloweyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,
                new int[]{255, 0, 0},
                new int[]{255, 55, 55},
                new int[]{255, 110, 110},
                new int[]{255, 110, 110},
                new int[]{255, 55, 55},
                new int[]{255, 0, 0}
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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.flowey.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.translatable("tooltip.lethality.flowey.advanced1"));
        pTooltipComponents.add(Component.translatable("tooltip.lethality.flowey.advanced2"));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        if (!(entity instanceof Player player)) return;
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 2, 0, false, false, true));
    }


    public boolean canEquipFromUse(SlotContext context, ItemStack stack) {
        return !context.entity().isCrouching();
    }
}