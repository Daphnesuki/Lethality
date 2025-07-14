package net.daphne.lethality.item.custom;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.entity.GaelSkullEntity;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.item.ModToolTiers;
import net.daphne.lethality.util.ModRarities;
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
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.*;

public class GaelsGreatswordItem extends SwordItem {
    public GaelsGreatswordItem(Properties pProperties) {
        super(ModToolTiers.CALAMITAS, 47, -2.4f, new Properties());
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ModRarities.CALAMITOUS;
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,

                new int[]{41, 37, 55},      // Gray-ish blue, purple and pink
                new int[]{50, 38, 70},
                new int[]{76, 44, 90},
                new int[]{105, 50, 105},
                new int[]{141, 53, 136},
                new int[]{180, 40, 180},
                new int[]{180, 40, 120},
                new int[]{255, 10, 85},
                new int[]{255, 0, 65},

                new int[]{255, 0, 30},      // Red

                new int[]{255, 40, 0},
                new int[]{255, 72, 0},
                new int[]{187, 75, 0},
                new int[]{255, 120, 0},
                new int[]{224, 136, 0},
                new int[]{255, 203, 25},
                new int[]{255, 229, 110},
                new int[]{255, 255, 180},
                new int[]{255, 255, 203},
                new int[]{255, 255, 251},
                new int[]{255, 255, 251},
                new int[]{255, 255, 203},
                new int[]{255, 255, 180},
                new int[]{255, 203, 25},
                new int[]{224, 136, 0},
                new int[]{255, 120, 0},
                new int[]{187, 75, 0},
                new int[]{255, 72, 0},
                new int[]{255, 40, 0},

                new int[]{255, 0, 30},      // Red

                new int[]{255, 0, 65},      // Gray-ish blue, purple and pink
                new int[]{255, 10, 85},
                new int[]{180, 40, 120},
                new int[]{180, 40, 180},
                new int[]{141, 53, 136},
                new int[]{105, 50, 105},
                new int[]{76, 44, 90},
                new int[]{50, 38, 70},
                new int[]{41, 37, 55}

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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.gaels_greatsword.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gaels_greatsword.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gaels_greatsword.advanced2"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gaels_greatsword.advanced3"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gaels_greatsword.advanced4"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.gaels_greatsword.advanced5"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        int currentAmplifier = 4;

        // Aplica HEX_FLAMES con acumulación
        if (pTarget.hasEffect(ModMobEffects.HEX_FLAMES.get())) {
            currentAmplifier = pTarget.getEffect(ModMobEffects.HEX_FLAMES.get()).getAmplifier() + 5;
        }

        currentAmplifier = Math.min(currentAmplifier, 24);
        pTarget.addEffect(new MobEffectInstance(ModMobEffects.HEX_FLAMES.get(), 100, currentAmplifier), pAttacker);

        int currentAmplifier2 = 7;

        // Acumulación de STAINED_CALAMITY
        if (pTarget.hasEffect(ModMobEffects.STAINED_CALAMITY.get())) {
            currentAmplifier2 = pTarget.getEffect(ModMobEffects.STAINED_CALAMITY.get()).getAmplifier() + 8;
        }

        currentAmplifier2 = Math.min(currentAmplifier2, 50);

        // Aplica STAINED_CALAMITY si aún no alcanzó 50
        if (currentAmplifier2 < 50) {
            pTarget.addEffect(new MobEffectInstance(ModMobEffects.STAINED_CALAMITY.get(), 60, currentAmplifier2), pAttacker);
        } else {
            // Aplica daño basado en 10% de la vida actual
            float currentHealth = pTarget.getHealth();
            float damage = currentHealth * 0.10f;
            playHexedParticlesAndSound(pTarget.level(), pTarget.getX(), pTarget.getY(), pTarget.getZ());
            pTarget.invulnerableTime = 0;
            pTarget.hurt(pTarget.damageSources().indirectMagic(pAttacker, pAttacker), damage);

            // Elimina el efecto STAINED_CALAMITY
            pTarget.removeEffect(ModMobEffects.STAINED_CALAMITY.get());
        }

        pTarget.invulnerableTime = 0;
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public static void shootForbiddenScytheSlash(Level world, Player user) {
        Minecraft client = Minecraft.getInstance();
        if (client.options.keyAttack.isDown() && client.level != null && client.player != null) {

        }
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = user.getItemInHand(hand);
            boolean acceptItem = itemStack.getItem() == ModItems.GAELS_GREATSWORD.get();
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

                    float pitchforkAngleOffset = 12.5F;

                    // Main projectile
                    GaelSkullEntity center = new GaelSkullEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    center.setDeltaMovement(user.getLookAngle());
                    center.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, speed, inaccuracy);
                    center.setOwner(user);
                    world.addFreshEntity(center);

                    // Left-side projectile
                    GaelSkullEntity left = new GaelSkullEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    left.setDeltaMovement(user.getLookAngle());
                    left.shootFromRotation(user, user.getXRot(), user.getYRot() - pitchforkAngleOffset, 0.0F, speed, inaccuracy);
                    left.setOwner(user);
                    world.addFreshEntity(left);

                    // Right-side projectile
                    GaelSkullEntity right = new GaelSkullEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    right.setDeltaMovement(user.getLookAngle());
                    right.shootFromRotation(user, user.getXRot(), user.getYRot() + pitchforkAngleOffset, 0.0F, speed, inaccuracy);
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

//                        // Abyss inspired palette
//                        new int[]{0, 104, 93},     // turquesa profundo
//                        new int[]{0, 130, 120},    // jade
//                        new int[]{0, 159, 142},    // verde-agua brillante
//                        new int[]{45, 200, 170},   // aqua profundo
//                        new int[]{89, 255, 193},   // brillante aguamarina
//                        new int[]{130, 255, 205},  // transición clara
//                        new int[]{167, 255, 216},  // verde agua pastel
//
//                        // Interlude
//                        new int[]{150, 200, 220},  // niebla energética
//                        new int[]{120, 130, 200},  // azul violáceo
//                        new int[]{127, 0, 175},    // púrpura oscuro
//
//                        // Calamitas inspired palette
//                        new int[]{170, 0, 200},
//                        new int[]{210, 0, 225},
//                        new int[]{255, 0, 255},     // magenta brillante
//
//                        // Yharon maybe?
//                        new int[]{255, 0, 128},
//                        new int[]{255, 0, 64},
//                        new int[]{255, 0, 0},       // rojo puro
//                        new int[]{255, 90, 0},      // rojo-naranja
//                        new int[]{255, 145, 0},     // naranja intenso
//                        new int[]{255, 170, 30},    // naranja dorado
//                        new int[]{255, 195, 55},    // amarillo fuego
//                        new int[]{255, 230, 90}     // luz final