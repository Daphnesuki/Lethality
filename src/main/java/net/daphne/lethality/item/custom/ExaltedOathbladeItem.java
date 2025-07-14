package net.daphne.lethality.item.custom;

import com.google.common.collect.Multimap;
import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.entity.ForbiddenScytheEntity;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModItems;
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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static net.daphne.lethality.util.ModUtils.*;

public class ExaltedOathbladeItem extends SwordItem {
    public ExaltedOathbladeItem(Properties pProperties) {
        super(ModToolTiers.DEMONIC, 12, -2.2f, new Properties());
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

        ResourceLocation VERMIN = new ResourceLocation(LethalityMod.MOD_ID, "vermin");
        ResourceLocation MKART = new ResourceLocation(LethalityMod.MOD_ID, "mkart");
        ResourceLocation STRANGE = new ResourceLocation(LethalityMod.MOD_ID, "grapesoda");
        ResourceLocation CALAMITOUS = new ResourceLocation(LethalityMod.MOD_ID, "homicide");

        Component legendaryText = addColorGradientTextWithFont(
                Component.literal("Calamitous"), CALAMITOUS,
                0.25f,
                5.0f,
                new int[]{255, 254, 251},
                new int[]{255, 242, 203},
                new int[]{255, 221, 177},
                new int[]{255, 202, 153},
                new int[]{255, 172, 131},
                new int[]{255, 156, 119},
                new int[]{255, 139, 107},
                new int[]{252, 114, 94},
                new int[]{251, 101, 87},
                new int[]{235, 75, 76},
                new int[]{197, 32, 57},
                new int[]{235, 75, 76},
                new int[]{251, 101, 87},
                new int[]{252, 114, 94},
                new int[]{255, 139, 107},
                new int[]{255, 156, 119},
                new int[]{255, 172, 131},
                new int[]{255, 202, 153},
                new int[]{255, 221, 177},
                new int[]{255, 254, 251}
        );

        pTooltipComponents.add(legendaryText);

        pTooltipComponents.add(Component.literal(" "));

        pTooltipComponents.add(Component.translatable("tooltip.lethality.exalted_oathblade.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.exalted_oathblade.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.exalted_oathblade.advanced2"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.exalted_oathblade.advanced3"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.exalted_oathblade.advanced4"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        int currentAmplifier = 0;

        // Verificamos si ya tiene el efecto
        if (pTarget.hasEffect(TerramityModMobEffects.NYXIUM_FIRE.get())) {
            currentAmplifier = pTarget.getEffect(TerramityModMobEffects.NYXIUM_FIRE.get()).getAmplifier() + 1;
        }

        // Limita el amplifier a un máximo de 9
        currentAmplifier = Math.min(currentAmplifier, 4);

        // Aplica el efecto con nuevo amplifier
        pTarget.addEffect(new MobEffectInstance(TerramityModMobEffects.NYXIUM_FIRE.get(), 100, currentAmplifier), pAttacker);
        pTarget.setSecondsOnFire(5);
        pTarget.invulnerableTime = 0;
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public static void shootForbiddenScytheSlash(Level world, Player user) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = user.getItemInHand(hand);
            boolean acceptItem = itemStack.getItem() == ModItems.EXALTED_OATHBLADE.get();
            if ((acceptItem)) {

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
                    float speed = 1.75F;
                    float inaccuracy = 0.5F;

                    float angleOffset = 25F;

                    // Main projectile
                    ForbiddenScytheEntity center = new ForbiddenScytheEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    center.setDeltaMovement(user.getLookAngle());
                    center.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, speed, inaccuracy);
                    center.setOwner(user);
                    world.addFreshEntity(center);

                    // Left-side projectile
                    ForbiddenScytheEntity left = new ForbiddenScytheEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    left.setDeltaMovement(user.getLookAngle());
                    left.shootFromRotation(user, user.getXRot(), user.getYRot() - angleOffset, 0.0F, speed, inaccuracy);
                    left.setOwner(user);
                    world.addFreshEntity(left);

                    // Right-side projectile
                    ForbiddenScytheEntity right = new ForbiddenScytheEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    right.setDeltaMovement(user.getLookAngle());
                    right.shootFromRotation(user, user.getXRot(), user.getYRot() + angleOffset, 0.0F, speed, inaccuracy);
                    right.setOwner(user);
                    world.addFreshEntity(right);
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

}
