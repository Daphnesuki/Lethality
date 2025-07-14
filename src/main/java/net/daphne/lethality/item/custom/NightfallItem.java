package net.daphne.lethality.item.custom;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModDamageTypes;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModToolTiers;
import net.daphne.lethality.util.ModRarities;
import net.mcreator.terramity.init.TerramityModItems;
import net.mcreator.terramity.init.TerramityModMobEffects;
import net.mcreator.terramity.init.TerramityModParticleTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;
import static net.daphne.lethality.util.ModUtils.addColorGradientTextWithFont;

public class NightfallItem extends SwordItem {
    public NightfallItem(Properties pProperties) {
        super(ModToolTiers.SILLY, 17, -3.0f, new Properties());
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ModRarities.STRANGE;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,
                new int[]{0, 104, 93},
                new int[]{0, 159, 142},
                new int[]{89, 255, 193},
                new int[]{167, 255, 216},
                new int[]{167, 255, 216},
                new int[]{89, 255, 193},
                new int[]{0, 159, 142},
                new int[]{0, 104, 93}
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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.nightfall.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        float value = getAccumulatedShield(pStack);
        if (value > 0) {
            Component shieldText = addColorGradientTextWithFont(
                    Component.literal(String.format("SHIELD:%.1f", value)), VERMIN,
                    0.5f,
                    5.0f,
                    new int[]{0, 104, 93},
                    new int[]{0, 159, 142},
                    new int[]{89, 255, 193},
                    new int[]{167, 255, 216},
                    new int[]{167, 255, 216},
                    new int[]{89, 255, 193},
                    new int[]{0, 159, 142},
                    new int[]{0, 104, 93}
            );
            pTooltipComponents.add(shieldText);
        }

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.nightfall.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.nightfall.advanced2"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.nightfall.advanced3"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.nightfall.advanced4"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.nightfall.advanced5"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.nightfall.advanced6"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("lethality", "nightfall_hit"));
        float pitch = Mth.nextFloat(pAttacker.getRandom(), 0.8F, 1.2F);
        float volume = 5F;
        if (pAttacker.level().isClientSide()) {
            pAttacker.level().playLocalSound(pAttacker.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch, false);
        } else {
            pAttacker.level().playSound(null, pAttacker.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);
        }
        pTarget.invulnerableTime = 0;
        pTarget.hurt(
                pTarget.damageSources().indirectMagic(pAttacker, pAttacker),
                (pTarget.getMaxHealth() / 20));
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public static void addAccumulatedShield(ItemStack stack, Player player, float amount) {
        if (player.hasEffect(ModMobEffects.IRON_WILL.get())) return; // NO acumular si tiene el efecto

        CompoundTag tag = stack.getOrCreateTag();
        float current = tag.getFloat("ShieldCharge");
        tag.putFloat("ShieldCharge", current + amount);
    }

    public static float getAccumulatedShield(ItemStack stack) {
        return stack.getOrCreateTag().getFloat("ShieldCharge");
    }

    public static void resetShield(ItemStack stack) {
        stack.getOrCreateTag().putFloat("ShieldCharge", 0f);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        float accumulated = getAccumulatedShield(stack);

        if (!level.isClientSide) {
            boolean hasShield = player.hasEffect(ModMobEffects.IRON_WILL.get());

            if (hasShield) {
                // Segunda activación: curar
                float absorption = player.getAbsorptionAmount();
                float heal = absorption * 0.6f;

                player.heal(heal);
                player.setAbsorptionAmount(0);
                player.removeEffect(ModMobEffects.IRON_WILL.get());
                SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("lethality", "nightfall_shield_recast"));
                float pitch = Mth.nextFloat(player.getRandom(), 0.8F, 1.2F);
                float volume = 1F;
                if (player.level().isClientSide()) {
                    player.level().playLocalSound(player.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch, false);
                } else {
                    player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);
                }
                player.getCooldowns().addCooldown(this, 200); // Cooldown

            } else if (accumulated > 1f) {
                // Primera activación: aplicar escudo
                player.setAbsorptionAmount(accumulated);
                player.addEffect(new MobEffectInstance(ModMobEffects.IRON_WILL.get(), 200, 0));
                SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("lethality", "nightfall_shield_use"));
                float pitch = Mth.nextFloat(player.getRandom(), 0.8F, 1.2F);
                float volume = 1F;
                if (!player.level().isClientSide) {
                    player.level().playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);
                }
                resetShield(stack);
            }
        }

        player.swing(hand, true);
        return InteractionResultHolder.success(stack);
    }

    public static void setInternalCooldown(ItemStack stack, int ticks) {
        stack.getOrCreateTag().putInt("NightfallCooldown", ticks);
    }

    public static int getInternalCooldown(ItemStack stack) {
        return stack.getOrCreateTag().getInt("NightfallCooldown");
    }

    public static void tickCooldown(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int current = tag.getInt("NightfallCooldown");
        if (current > 0) {
            tag.putInt("NightfallCooldown", current - 1);
        }
    }

    public static boolean hadIronWill(ItemStack stack) {
        return stack.getOrCreateTag().getBoolean("HadIronWill");
    }

    public static void setHadIronWill(ItemStack stack, boolean value) {
        stack.getOrCreateTag().putBoolean("HadIronWill", value);
    }
}