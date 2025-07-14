package net.daphne.lethality.item.custom;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.entity.RealSlashEntity;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.init.ModSounds;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.item.ModToolTiers;
import net.daphne.lethality.util.ModRarities;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.*;

public class RealKnifeItem extends SwordItem {
    public RealKnifeItem(Properties pProperties) {
        super(ModToolTiers.SILLY, 11, -1.5f, new Properties());
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ModRarities.STRANGE;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,
                new int[]{110, 0, 0},
                new int[]{165, 0, 0},
                new int[]{255, 0, 0},
                new int[]{255, 55, 0},
                new int[]{255, 110, 0},
                new int[]{255, 110, 0},
                new int[]{255, 55, 0},
                new int[]{255, 0, 0},
                new int[]{165, 0, 0},
                new int[]{110, 0, 0}
        ).withStyle(ChatFormatting.BOLD);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> pTooltipComponents, TooltipFlag isAdvanced) {
        int kills = stack.getOrCreateTag().getInt("KillCount");

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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.real_knife.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.real_knife.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.real_knife.advanced2"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.real_knife.advanced3"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.real_knife.advanced4"));
            pTooltipComponents.add(Component.literal(" "));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
            pTooltipComponents.add(Component.literal(" "));
        }

        pTooltipComponents.add(Component.literal("§4Kills: §6" + kills));
        pTooltipComponents.add(Component.literal(" "));
        super.appendHoverText(stack, level, pTooltipComponents, isAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public static void shootRealSlash(Level world, Player user) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = user.getItemInHand(hand);
            boolean acceptItem = itemStack.getItem() == ModItems.REAL_KNIFE.get();
            if (acceptItem && user.hasEffect(ModMobEffects.RAGE.get())) {

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
                    RealSlashEntity entity = new RealSlashEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    entity.setDeltaMovement(user.getLookAngle());
                    entity.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
                    entity.setOwner(user);
                    world.addFreshEntity(entity);
                }
                SoundEvent sound = ModSounds.REAL_KNIFE_SLASH.get();
                float pitch = Mth.nextFloat(RandomSource.create(), 0.7F, 1F);
                float volume = 0.5F;

                if (!world.isClientSide()) {
                    world.playSound(null, user.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);
                } else {
                    world.playLocalSound(user.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch, false);
                }
            }
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        CompoundTag tag = stack.getOrCreateTag();

        int genocide = tag.getInt("GenocideCount");

        if (genocide >= 10) {
            if (player.hasEffect(ModMobEffects.RAGE.get())) {
                return InteractionResultHolder.fail(stack); // Ya está activo
            }

            if (!level.isClientSide()) {
                player.addEffect(new MobEffectInstance(ModMobEffects.RAGE.get(), 660, 0)); // 33 segundos

                level.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE,
                        SoundSource.RECORDS, 0.70f, 0.5f);
                level.playSound(null, player.blockPosition(), ModSounds.MEGALO_STRIKE_BACK.get(),
                        SoundSource.RECORDS, 0.35f, 1.0f);

                player.displayClientMessage(Component.literal("§4SINCE WHEN WERE YOU THE ONE IN CONTROL?"), true);

                tag.putInt("GenocideCount", 0);
                player.setItemInHand(hand, stack);
            }

            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }
}
