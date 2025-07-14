package net.daphne.lethality.item.custom;

import com.google.common.collect.Multimap;
import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.entity.BloodScytheEntity;
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

public class BladecrestOathswordItem extends SwordItem {
    public BladecrestOathswordItem(Properties pProperties) {
        super(ModToolTiers.DEMONIC, 7, -2.4f, new Properties());
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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.bladecrest_oathsword.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.bladecrest_oathsword.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.bladecrest_oathsword.advanced2"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.bladecrest_oathsword.advanced3"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.bladecrest_oathsword.advanced4"));
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        pTarget.setSecondsOnFire(3);
        pTarget.invulnerableTime = 0;
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public static void shootBloodScytheSlash(Level world, Player user) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = user.getItemInHand(hand);
            boolean acceptItem = itemStack.getItem() instanceof BladecrestOathswordItem;
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
                    BloodScytheEntity spear = new BloodScytheEntity(world, user.getX(), user.getY() + 0.25, user.getZ(), halfDamage);
                    spear.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 0.75F, 2F);
                    spear.setOwner(user);
                    world.addFreshEntity(spear);
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
