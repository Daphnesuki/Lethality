package net.daphne.lethality.item.custom;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.item.ModToolTiers;
import net.daphne.lethality.util.ModRarities;
import net.mcreator.terramity.init.TerramityModItems;
import net.mcreator.terramity.init.TerramityModParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;
import java.util.Set;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;
import static net.daphne.lethality.util.ModUtils.addColorGradientTextWithFont;

public class GamblersBladeItem extends SwordItem {
    public GamblersBladeItem(Properties pProperties) {
        super(ModToolTiers.SILLY, 31, -2.4f, new Properties());
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ModRarities.STRANGE;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,
                new int[]{255, 204, 0},   // dorado puro
                new int[]{255, 180, 0},   // ámbar intenso
                new int[]{255, 220, 50},  // amarillo dorado claro
                new int[]{255, 240, 120}, // crema cálido
                new int[]{255, 210, 100}, // dorado suave
                new int[]{255, 160, 60},  // naranja suave tostado
                new int[]{255, 120, 30}   // naranja profundo/caramelo
        ).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        ResourceLocation VERMIN = new ResourceLocation(LethalityMod.MOD_ID, "vermin");
        ResourceLocation MKART = new ResourceLocation(LethalityMod.MOD_ID, "mkart");
        ResourceLocation STRANGE = new ResourceLocation(LethalityMod.MOD_ID, "grapesoda");
        ResourceLocation CALAMITOUS = new ResourceLocation(LethalityMod.MOD_ID, "homicide");

        Component legendaryText = addColorGradientTextWithFont(
                Component.literal("Strange"), STRANGE,
                0.1f,
                5.0f,
                new int[]{255, 167, 175}, // rojo pastel
                new int[]{255, 196, 175},
                new int[]{255, 225, 175},
                new int[]{255, 237, 181},
                new int[]{255, 243, 181},
                new int[]{255, 249, 181},
                new int[]{255, 255, 181},
                new int[]{243, 255, 181},
                new int[]{232, 255, 181},
                new int[]{220, 255, 181},
                new int[]{208, 255, 181},
                new int[]{191, 255, 179}, // verde limón pastel
                new int[]{179, 255, 204},
                new int[]{179, 255, 229},
                new int[]{179, 255, 255}, // celeste pastel
                new int[]{179, 225, 255},
                new int[]{179, 196, 255},
                new int[]{179, 162, 255},
                new int[]{196, 162, 255},
                new int[]{225, 162, 255},
                new int[]{255, 162, 255}, // violeta pastel
                new int[]{255, 162, 225},
                new int[]{255, 162, 196},
                new int[]{255, 162, 162}, // rosa pastel
                new int[]{255, 192, 192},
                new int[]{255, 222, 222},
                new int[]{255, 237, 237},
                new int[]{255, 247, 247},
                new int[]{255, 255, 255}, // blanco
                new int[]{237, 255, 255},
                new int[]{222, 255, 237},
                new int[]{222, 255, 222},
                new int[]{222, 255, 211},
                new int[]{222, 255, 196},
                new int[]{222, 255, 181},
                new int[]{222, 243, 181},
                new int[]{222, 232, 181},
                new int[]{222, 220, 181},
                new int[]{222, 208, 181},
                new int[]{222, 196, 181},
                new int[]{222, 181, 181},
                new int[]{222, 167, 181},
                new int[]{222, 153, 181},
                new int[]{222, 139, 181},
                new int[]{222, 125, 181},
                new int[]{222, 111, 181},
                new int[]{222, 97,  181}

        );

        pTooltipComponents.add(legendaryText);

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.translatable("tooltip.lethality.gamblers_blade.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gamblers_blade.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gamblers_blade.advanced2"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gamblers_blade.advanced3"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gamblers_blade.advanced4"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gamblers_blade.advanced5"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gamblers_blade.advanced6"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.invulnerableTime = 0;
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }
}
