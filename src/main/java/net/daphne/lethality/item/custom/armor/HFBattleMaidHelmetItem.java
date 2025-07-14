package net.daphne.lethality.item.custom.armor;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.client.model.ModelCatEarsHelmet;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.util.ModUtils;
import net.mcreator.terramity.client.model.Modelconjuror_armor;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;


import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;
import static net.daphne.lethality.util.ModUtils.addColorGradientTextWithFont;

public class HFBattleMaidHelmetItem extends ArmorItem {
    public HFBattleMaidHelmetItem(ArmorMaterial material, Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            public HumanoidModel getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of("head", (new ModelCatEarsHelmet(Minecraft.getInstance().getEntityModels().bakeLayer(ModelCatEarsHelmet.LAYER_LOCATION))).head.getChild("helmet"), "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                armorModel.crouching = living.isShiftKeyDown();
                armorModel.riding = defaultModel.riding;
                armorModel.young = living.isBaby();
                return armorModel;
            }
        });
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "lethality:textures/models/armor/hf_battle_maid_layer.png";
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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_bm_helmet.tooltip"));

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
