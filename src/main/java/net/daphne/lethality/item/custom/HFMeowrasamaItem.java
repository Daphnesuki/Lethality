package net.daphne.lethality.item.custom;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.entity.HFMeowrasamaSlashEntity;
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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.daphne.lethality.util.ModUtils.*;

public class HFMeowrasamaItem extends SwordItem {
    public HFMeowrasamaItem(Properties pProperties) {
        super(ModToolTiers.SILLY, 15, 0.0f, new Properties());
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return ModRarities.STRANGE;
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

        pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_meowrasama.tooltip"));

        pTooltipComponents.add(Component.literal(" "));

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_meowrasama.advanced1"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_meowrasama.advanced2"));
            pTooltipComponents.add(Component.literal(" "));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_meowrasama.advanced3"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_meowrasama.advanced4"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_meowrasama.advanced5"));
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_meowrasama.advanced6"));
//            if (Minecraft.getInstance().player != null && hasFullBattleMaidSet(Minecraft.getInstance().player)) {
//                pTooltipComponents.add(Component.literal(" "));
//                pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced6"));
//                pTooltipComponents.add(Component.translatable("tooltip.lethality.battle_maid_set.advanced7"));
//            }
//            if (Minecraft.getInstance().player != null && hasFullHFMaidSet(Minecraft.getInstance().player)) {
//                pTooltipComponents.add(Component.literal(" "));
//                pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced6"));
//                pTooltipComponents.add(Component.translatable("tooltip.lethality.hf_battle_maid_set.advanced7"));
//            }
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.lethality.hold_ctrl"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if (pStack.is(ModItems.HF_MEOWRASAMA.get()) && pStack.getOrCreateTag().getInt("CustomModelData") == 1) {
            pTarget.addEffect(new MobEffectInstance(TerramityModMobEffects.VULNERABLE.get(), 100, 0), pAttacker);
            pTarget.addEffect(new MobEffectInstance(ModMobEffects.CRUMBLING.get(), 100, 0), pAttacker);
        }
        pTarget.addEffect(new MobEffectInstance(TerramityModMobEffects.ELECTRIC_SHOCK_EFFECT.get(), 100, 0), pAttacker);
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        // Evitar usar la habilidad si el jugador tiene el cooldown activo
        if (player.hasEffect(TerramityModMobEffects.ABILITY_COOLDOWN.get())) {
            return InteractionResultHolder.fail(stack); // Cancela el uso
        }

        // Activar la habilidad
        stack.getOrCreateTag().putInt("CustomModelData", 1);
        if (!level.isClientSide) {
            player.addEffect(new MobEffectInstance(ModMobEffects.BLADE_MODE.get(), 200, 0));
            player.addEffect(new MobEffectInstance(TerramityModMobEffects.ABILITY_COOLDOWN.get(), 600)); // cooldown de 5 segundos

            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("lethality:bloodshed_start"));
            float volume = 0.75F;

            level.playSound(null, player.blockPosition(), sound, SoundSource.PLAYERS, volume, 1);
            level.playSound(null, player.blockPosition(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 0.85F);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    public static void meowrasamaSlashParticle(Level world, Player user) {
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = user.getItemInHand(hand);
            boolean acceptItem = itemStack.getItem() == ModItems.HF_MEOWRASAMA.get();
            if ((acceptItem)) {

                boolean hasBetterCombat = ModList.get().isLoaded("bettercombat");
                if (!hasBetterCombat) {
                    if (user.getCooldowns().isOnCooldown(itemStack.getItem())) return;

                    float attackSpeed = 4.0f;
                    AttributeInstance attribute = user.getAttribute(Attributes.ATTACK_SPEED);

                    if (attribute != null) {
                        attackSpeed = (float) attribute.getValue();
                    }

                    int cooldownTicks = (int) (20.0f / attackSpeed);

                    user.getCooldowns().addCooldown(itemStack.getItem(), cooldownTicks);
                }

                if (!world.isClientSide) {
                    float fullDamage = getItemAttackDamage(user.getItemInHand(hand));
                    float halfDamage = fullDamage * 0.5f;

                    HFMeowrasamaSlashEntity spear = new HFMeowrasamaSlashEntity(
                            world,
                            user.getX(),
                            user.getY() - 0.5 + user.getBbHeight() * 0.5, // centro del jugador
                            user.getZ(),
                            halfDamage
                    );

                    // Obtiene la dirección de visión completa (pitch + yaw)
                    Vec3 look = user.getLookAngle(); // dirección normalizada
                    double speed = 0.01;
                    spear.setDeltaMovement(look.scale(speed));
                    int mode = itemStack.getOrCreateTag().getInt("CustomModelData");
                    spear.setMode(mode);
                    spear.setOwner(user);
                    world.addFreshEntity(spear);
                }


                SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:zap"));
                SoundEvent sound2 = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:electric_shock"));
                float pitch = Mth.nextFloat(RandomSource.create(), 1.2F, 1.5F);
                float volume = 0.75F;

                if (!world.isClientSide()) {
                    // Sonido (servidor)
                    world.playSound(null, user.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch);
                    world.playSound(null, user.blockPosition(), sound2, SoundSource.PLAYERS, volume, pitch);
                } else {
                    // En cliente
                    world.playLocalSound(user.blockPosition(), sound, SoundSource.PLAYERS, volume, pitch, false);
                    world.playLocalSound(user.blockPosition(), sound2, SoundSource.PLAYERS, volume, pitch, false);
                }
            }
        }
    }
}
