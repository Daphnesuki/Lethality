package net.daphne.lethality.item.custom;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModToolTiers;
import net.daphne.lethality.util.ModRarities;
import net.daphne.lethality.util.TooltipGlitchHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;
import static net.daphne.lethality.util.ModUtils.addColorGradientTextWithFont;

public class StopSignItem extends SwordItem {
    public StopSignItem(Properties pProperties) {
        super(ModToolTiers.SILLY, 8, -2.4f, new Properties());
    }

    public static final String HIT_COUNTER_TAG = "StopSignHits";
    public static final int MAX_HITS = 20; // máximo de stacks
    public static final UUID STOP_SIGN_SPEED_MODIFIER = UUID.fromString("a7b964c3-7643-4bc5-b763-f4a3f933bfb2");
    public static final String LAST_HIT_TIME_TAG = "StopSignLastHitTime";

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ModRarities.STRANGE;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f, // velocidad y expansión
                new int[]{75, 75, 99},
                new int[]{94, 99, 124},
                new int[]{126, 127, 159},
                new int[]{162, 161, 193},
                new int[]{188, 187, 216},
                new int[]{188, 187, 216},
                new int[]{162, 161, 193},
                new int[]{126, 127, 159},
                new int[]{94, 99, 124},
                new int[]{75, 75, 99}
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

        pTooltipComponents.add(TooltipGlitchHelper.getGlitchedTooltip());

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.stop_sign.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.stop_sign.advanced2"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide) {
            int hits = stack.getOrCreateTag().getInt(HIT_COUNTER_TAG);
            hits = Math.min(hits + 1, MAX_HITS);
            stack.getOrCreateTag().putInt(HIT_COUNTER_TAG, hits);

            // Guardar el tiempo actual del mundo
            stack.getOrCreateTag().putLong(LAST_HIT_TIME_TAG, attacker.level().getGameTime());

            applyAttackSpeedModifier(attacker, hits);
        }

        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (level.isClientSide || !(entity instanceof LivingEntity living)) return;

        var tag = stack.getOrCreateTag();
        long currentTime = level.getGameTime();

        boolean isEquipped = selected || living.getOffhandItem() == stack;

        // Si el arma estaba equipada en el tick anterior y ya no lo está
        if (!isEquipped && tag.getBoolean("StopSignEquipped")) {
            tag.putInt(HIT_COUNTER_TAG, 0);
            tag.putBoolean("StopSignEquipped", false);

            // Remover modificador de velocidad
            var attr = living.getAttributes().getInstance(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED);
            if (attr != null) {
                attr.removeModifier(STOP_SIGN_SPEED_MODIFIER);
            }

            return;
        }

        if (isEquipped) {
            tag.putBoolean("StopSignEquipped", true); // marcar como equipada

            if (tag.contains(LAST_HIT_TIME_TAG)) {
                long lastHitTime = tag.getLong(LAST_HIT_TIME_TAG);

                if (currentTime - lastHitTime > 100) { // 5 segundos sin golpear
                    int hits = tag.getInt(HIT_COUNTER_TAG);
                    if (hits > 0) {
                        tag.putInt(HIT_COUNTER_TAG, 0);

                        var attr = living.getAttributes().getInstance(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED);
                        if (attr != null) {
                            attr.removeModifier(STOP_SIGN_SPEED_MODIFIER);
                        }
                    }
                }
            }
        }
    }

    private void applyAttackSpeedModifier(LivingEntity attacker, int hits) {
        double bonusSpeed = hits * 0.25; // +2.5% velocidad por golpe

        var attributeInstance = attacker.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED);
        if (attributeInstance != null) {
            // Eliminar modificador anterior si existe
            attributeInstance.removeModifier(STOP_SIGN_SPEED_MODIFIER);

            // Aplicar modificador temporal (no se conserva al desequipar)
            attributeInstance.addTransientModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                    STOP_SIGN_SPEED_MODIFIER,
                    "Stop Sign Attack Speed",
                    bonusSpeed,
                    net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION
            ));
        }
    }

}
