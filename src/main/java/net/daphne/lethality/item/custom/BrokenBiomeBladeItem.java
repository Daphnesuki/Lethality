package net.daphne.lethality.item.custom;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.client.RadialInputHandler;
import net.daphne.lethality.client.RadialModeHandler;
import net.daphne.lethality.entity.AcidicSlashEntity;
import net.daphne.lethality.entity.BBBProjectionEntity;
import net.daphne.lethality.entity.StarlightStabEntity;
import net.daphne.lethality.item.ModToolTiers;
import net.daphne.lethality.util.ModRarities;
import net.mcreator.terramity.init.TerramityModMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.*;

public class BrokenBiomeBladeItem extends SwordItem {
    public BrokenBiomeBladeItem(Properties pProperties) {
        super(ModToolTiers.BIOME, 7, -2.4f, new Properties());
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

        ResourceLocation VERMIN = new ResourceLocation(LethalityMod.MOD_ID, "vermin"); // Font used: Alagard https://www.dafont.com/alagard.font
        ResourceLocation MKART = new ResourceLocation(LethalityMod.MOD_ID, "mkart");
        ResourceLocation CALAMITOUS = new ResourceLocation(LethalityMod.MOD_ID, "homicide");

        Component legendaryText = addColorGradientTextWithFont(
                Component.literal("W.I.P"), VERMIN,
                0.5f,
                1.0f,
                new int[]{55, 55, 55},
                new int[]{255, 255, 0}
        );
        pTooltipComponents.add(Component.translatable("tooltip.lethality.wip_warning"));

        pTooltipComponents.add(legendaryText);

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.advanced2"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.advanced3"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.advanced4"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.advanced5"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.advanced6"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.advanced7"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.broken_biome_blade.advanced8"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (!pStack.hasTag()) return super.hurtEnemy(pStack, pTarget, pAttacker);

        int mode = pStack.getOrCreateTag().getInt("BiomeBladeMode");

        pTarget.invulnerableTime = 0;

        switch (mode) {
            case 1 -> {
                pTarget.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 1)); // 5 segundos (100 ticks) de veneno nivel 2
            }
            case 2 -> {
                pTarget.setSecondsOnFire(4);
            }
            case 3 -> {

            }
            case 4 -> {
                pTarget.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 1)); // 5 segundos (100 ticks) de veneno nivel 2
            }
        }

        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    private static final String MODE_KEY = "BiomeBladeMode";

    private int getMode(ItemStack stack) {
        return stack.getOrCreateTag().getInt(MODE_KEY);
    }

    public static void setMode(ItemStack stack, int mode) {
        stack.getOrCreateTag().putInt(MODE_KEY, mode);
    }

    public static void shootProjection(Level world, Player user) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = user.getItemInHand(hand);
            boolean acceptItem = itemStack.getItem() instanceof BrokenBiomeBladeItem;
            if ((acceptItem)) {
                int mode = itemStack.getOrCreateTag().getInt("BiomeBladeMode");
                if (mode != 0) return;
                boolean hasBetterCombat = ModList.get().isLoaded("bettercombat");
                if (!hasBetterCombat) {
                    // Verifica si hay cooldown
                    if (user.getCooldowns().isOnCooldown(itemStack.getItem())) return;

                    // Calcula cooldown con base en la velocidad de ataque
                    float attackSpeed = 4.0f;
                    AttributeInstance attribute = user.getAttribute(Attributes.ATTACK_SPEED);
                    if (attribute != null) {
                        attackSpeed = (float) attribute.getValue();
                    }
                    int cooldownTicks = (int) (20.0f / attackSpeed);

                    // Aplica cooldown
                    user.getCooldowns().addCooldown(itemStack.getItem(), cooldownTicks);
                }

                if (!world.isClientSide) {
                    float fullDamage = getItemAttackDamage(user.getItemInHand(hand));
                    float halfDamage = fullDamage * 0.5f;
                    BBBProjectionEntity entity = new BBBProjectionEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    entity.setDeltaMovement(user.getLookAngle());
                    entity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
                    entity.setOwner(user);
                    world.addFreshEntity(entity);
                }
                SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:crescent_moonblade_wave"));
                float pitch = Mth.nextFloat(RandomSource.create(), 1.1F, 1.3F);
                float volume = 1.5F;

                if (!world.isClientSide()) {
                    // En servidor
                    world.playSound(null, user.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);
                } else {
                    // En cliente
                    world.playLocalSound(user.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch, false);
                }
            }
        }
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        SoundEvent transmute = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:transmute"));
        SoundEvent bass = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:bassy_thud"));
        SoundEvent unlock = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:unlock"));
        SoundEvent zap = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:slash2"));
        SoundEvent ascend = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:ascendedtop"));
        float pitch = Mth.nextFloat(RandomSource.create(), 1.0F, 1.5F);
        float volume = 0.35F;

        if (!level.isClientSide()) {
            level.playSound(null, player.blockPosition(), transmute, SoundSource.PLAYERS, 0.75F, pitch);
            level.playSound(null, player.blockPosition(), bass, SoundSource.PLAYERS, volume, pitch);
            level.playSound(null, player.blockPosition(), unlock, SoundSource.PLAYERS, volume, pitch);
            level.playSound(null, player.blockPosition(), zap, SoundSource.PLAYERS, volume, pitch);
            level.playSound(null, player.blockPosition(), ascend, SoundSource.PLAYERS, volume, pitch);
        } else {
            level.playLocalSound(player.blockPosition(), transmute, SoundSource.PLAYERS, 0.75F, pitch, false);
            level.playLocalSound(player.blockPosition(), bass, SoundSource.PLAYERS, volume, pitch, false);
            level.playLocalSound(player.blockPosition(), unlock, SoundSource.PLAYERS, volume, pitch, false);
            level.playLocalSound(player.blockPosition(), zap, SoundSource.PLAYERS, volume, pitch, false);
            level.playLocalSound(player.blockPosition(), bass, SoundSource.PLAYERS, volume, pitch, false);
            level.playLocalSound(player.blockPosition(), ascend, SoundSource.PLAYERS, volume, pitch, false);
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingUseDuration) {
        if (level.isClientSide && entity instanceof Player) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.screen == null && !RadialModeHandler.isOpen) {
                RadialModeHandler.open(stack);
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        SoundEvent zap = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:slash2"));
        SoundEvent bass = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:bassy_thud"));
        SoundEvent ascend = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:ascendedtop"));
        float pitch = Mth.nextFloat(RandomSource.create(), 1.0F, 1.5F);
        float volume = 0.35F;

        if (!level.isClientSide()) {
            level.playSound(null, entity.blockPosition(), zap, SoundSource.PLAYERS, 0.75F, pitch);
            level.playSound(null, entity.blockPosition(), bass, SoundSource.PLAYERS, volume, pitch);
            level.playSound(null, entity.blockPosition(), ascend, SoundSource.PLAYERS, volume, pitch);
        } else {
            level.playLocalSound(entity.blockPosition(), zap, SoundSource.PLAYERS, 0.75F, pitch, false);
            level.playLocalSound(entity.blockPosition(), bass, SoundSource.PLAYERS, volume, pitch, false);
            level.playLocalSound(entity.blockPosition(), ascend, SoundSource.PLAYERS, volume, pitch, false);
        }
    }
}