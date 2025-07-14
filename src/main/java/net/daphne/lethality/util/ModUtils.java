package net.daphne.lethality.util;

import com.google.common.collect.Multimap;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.potion.IStackingEffect;
import net.mcreator.terramity.init.TerramityModParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Map;
import java.util.stream.Stream;

public class ModUtils {
    public static int[] changeBrightness(int[] colors, float factor) {
        int[] result = new int[colors.length];
        for (int i = 0; i < colors.length; i++) {
            result[i] = (int) (colors[i] * factor);
        }
        return result;
    }

    public static Component tooltipHelper(String localeKey, boolean bold, ResourceLocation font, float waveSpeed, float spreadMultiplier, int[]... colors) {
        if (font == null) {
            if (colors.length == 1) {
                return Component.translatable(localeKey).withStyle(Style.EMPTY.withBold(bold).withColor(rgbToInt(colors[0])));
            } else {
                return addColorGradientText(Component.translatable(localeKey), waveSpeed, spreadMultiplier, colors).withStyle(Style.EMPTY.withBold(bold));
            }
        } else {
            if (colors.length == 1) {
                return Component.translatable(localeKey).withStyle(Style.EMPTY.withBold(bold).withFont(font).withColor(rgbToInt(colors[0])));
            } else {
                return addColorGradientText(Component.translatable(localeKey), waveSpeed, spreadMultiplier, colors).withStyle(Style.EMPTY.withBold(bold).withFont(font));
            }
        }
    }

    public static MutableComponent tooltipHelper(String localeKey, boolean bold, ResourceLocation font, int[]... colors) {
        if (font == null) {
            if (colors.length == 1) {
                return Component.translatable(localeKey).withStyle(Style.EMPTY.withBold(bold).withColor(rgbToInt(colors[0])));
            } else {
                return addColorGradientText(Component.translatable(localeKey), colors).withStyle(Style.EMPTY.withBold(bold));
            }
        } else {
            if (colors.length == 1) {
                return Component.translatable(localeKey).withStyle(Style.EMPTY.withBold(bold).withFont(font).withColor(rgbToInt(colors[0])));
            } else {
                return addColorGradientText(Component.translatable(localeKey), colors).withStyle(Style.EMPTY.withBold(bold).withFont(font));
            }
        }
    }

    public static int[] ensureVisible(int[] rgb) {
        float[] hsv = new float[3];
        Color.RGBtoHSB(rgb[0], rgb[1], rgb[2], hsv);

        float minBrightness = 25;
        float boost = 0.25F;

        if (hsv[2] < minBrightness) {
            hsv[2] = Math.min(1.0f, hsv[2] + boost);
        }

        // Convert back to RGB
        int rgbInt = Color.HSBtoRGB(hsv[0], hsv[1], hsv[2]);
        return new int[]{
                (rgbInt >> 16) & 0xFF,
                (rgbInt >> 8) & 0xFF,
                rgbInt & 0xFF
        };
    }

    public static int getColorFromGradient(int percentage, int[]... rgbColors) {
        // Handle edge cases when there are less than 2 colors
        if (rgbColors.length < 2) {
            return rgbToInt(rgbColors[0]);
        }

        // Calculate the ratio based on the percentage
        float ratio = percentage / 100f;
        int totalSegments = rgbColors.length - 1; // Number of segments created by color stops

        // Determine the segment range for the given ratio
        int segment = Math.min((int) (ratio * totalSegments), totalSegments - 1);
        float localRatio = (ratio * totalSegments) - segment;

        // Interpolate between the two colors
        int[] color1 = rgbColors[segment];
        int[] color2 = rgbColors[segment + 1];

        // Calculate the resulting color
        int r = (int) ((1 - localRatio) * color1[0] + localRatio * color2[0]);
        int g = (int) ((1 - localRatio) * color1[1] + localRatio * color2[1]);
        int b = (int) ((1 - localRatio) * color1[2] + localRatio * color2[2]);

        return (r << 16) | (g << 8) | b; // Return the final color
    }

    public static MutableComponent addColorGradientText(Component text, float speed, float spreadMultiplier, int[]... rgbColors) {
        MutableComponent gradientTextComponent = Component.empty();
        String string = text.getString();
        int length = string.length();
        int numColors = rgbColors.length;

        if (numColors == 0 || length == 0) {
            return gradientTextComponent;
        }

        int tickCount = DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            if (Minecraft.getInstance().player != null)
                return Minecraft.getInstance().player.tickCount;
            return (int) (System.currentTimeMillis() / 50L);
        });

        int[][] adjustedColors = new int[numColors + 1][3];
        System.arraycopy(rgbColors, 0, adjustedColors, 0, numColors);
        adjustedColors[numColors] = rgbColors[0]; // Looping suave

        speed = 1 / speed;
        float effectiveTickCount = tickCount % (speed * 20);
        float ratio = effectiveTickCount / (speed * 20);

        spreadMultiplier = 1 / spreadMultiplier;
        int effectiveLength = (int) (length * spreadMultiplier);

        for (int i = 0; i < length; i++) {
            float adjustedIndex = (((float) i * spreadMultiplier) / length + ratio) * effectiveLength;
            adjustedIndex = adjustedIndex % effectiveLength;
            int percentage = (int) ((adjustedIndex / effectiveLength) * 100);

            int color = getColorFromGradient(percentage, adjustedColors);

            Component letterComponent = Component.literal(String.valueOf(string.charAt(i)))
                    .withStyle(Style.EMPTY.withColor(color));

            gradientTextComponent = gradientTextComponent.append(letterComponent);
        }

        return gradientTextComponent;
    }

    public static MutableComponent addColorGradientText(Component text, int[]... rgbColors) {
        // Create a component to hold all the parts of the gradient text
        MutableComponent gradientTextComponent = Component.empty();

        String string = text.getString();
        int length = string.length();
        int numColors = rgbColors.length; // Number of color stops

        if (numColors == 0 || length == 0) {
            return gradientTextComponent; // Return empty component if no colors or no text
        }

        for (int i = 0; i < length; i++) {
            // Calculate the percentage based on character index
            int percentage = (i * 100) / (length - 1); // Avoid division by zero for single character strings

            // Get the color from the gradient using the helper method
            int color = getColorFromGradient(percentage, rgbColors);

            // Create a component for the letter with the computed color
            Component letterComponent = Component.literal(String.valueOf(string.charAt(i)))
                    .withStyle(Style.EMPTY.withColor(color));

            // Append the letter component to the main gradient text component
            gradientTextComponent = gradientTextComponent.append(letterComponent);
        }

        // Return the complete gradient text component
        return gradientTextComponent;
    }

    public static MutableComponent addColorGradientTextWithFont(Component text, ResourceLocation font, float speed, float spreadMultiplier, int[]... rgbColors) {
        MutableComponent gradientTextComponent = Component.empty();
        String string = text.getString();
        int length = string.length();
        int numColors = rgbColors.length;

        if (numColors == 0 || length == 0) {
            return gradientTextComponent;
        }

        int tickCount = DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> {
            if (Minecraft.getInstance().player != null)
                return Minecraft.getInstance().player.tickCount;
            return (int) (System.currentTimeMillis() / 50L);
        });

        int[][] adjustedColors = new int[numColors + 1][3];
        System.arraycopy(rgbColors, 0, adjustedColors, 0, numColors);
        adjustedColors[numColors] = rgbColors[0];

        speed = 1 / speed;
        float effectiveTickCount = tickCount % (speed * 20);
        float ratio = effectiveTickCount / (speed * 20);

        spreadMultiplier = 1 / spreadMultiplier;
        int effectiveLength = (int) (length * spreadMultiplier);

        for (int i = 0; i < length; i++) {
            float adjustedIndex = (((float) i * spreadMultiplier) / length + ratio) * effectiveLength;
            adjustedIndex = adjustedIndex % effectiveLength;
            int percentage = (int) ((adjustedIndex / effectiveLength) * 100);

            int color = getColorFromGradient(percentage, adjustedColors);

            Component letterComponent = Component.literal(String.valueOf(string.charAt(i)))
                    .withStyle(Style.EMPTY.withColor(color).withFont(font));

            gradientTextComponent = gradientTextComponent.append(letterComponent);
        }

        return gradientTextComponent;
    }

    // Example RGB conversion method
    public static int rgbToInt(int[] rgb) {
        return (rgb[0] << 16) | (rgb[1] << 8) | rgb[2]; // Assume RGB value is in the range [0, 255]
    }

    public static int rgbToInt(int r, int g, int b) {
        return (r << 16) | (g << 8) | b; // Assume RGB value is in the range [0, 255]
    }

    public static int argbToInt(int[] rgb, int... alpha) {
        int a = alpha.length > 0 ? alpha[0] : 255;
        rgb[0] = Math.max(0, Math.min(255, rgb[0]));
        rgb[1] = Math.max(0, Math.min(255, rgb[1]));
        rgb[2] = Math.max(0, Math.min(255, rgb[2]));
        return (a << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
    }

    public static int[] argbToRgb(int argb) {
        return new int[]{
                (argb >> 16) & 0xFF, // Red component
                (argb >> 8) & 0xFF,  // Green component
                argb & 0xFF          // Blue component
        };
    }

    public static float getItemAttackDamage(ItemStack weapon) {
        if (weapon.isEmpty()) return 1.0f; // o 0.0f si preferís

        Multimap<Attribute, AttributeModifier> modifiers = weapon.getAttributeModifiers(EquipmentSlot.MAINHAND);
        double damage = 1.0;

        for (Map.Entry<Attribute, AttributeModifier> entry : modifiers.entries()) {
            if (entry.getKey().equals(Attributes.ATTACK_DAMAGE)) {
                damage += entry.getValue().getAmount();
            }
        }

        // También podés agregar el bonus por encantamientos si querés:
        damage += EnchantmentHelper.getDamageBonus(weapon, MobType.UNDEFINED); // o el MobType que corresponda

        return (float) damage;
    }

    public static int countBloodCoins(Player player) {
        int count = 0;
        for (ItemStack stack : player.getInventory().items) {
            if (stack.getItem() == ModItems.BLOOD_COIN.get()) {
                count += stack.getCount();
            }
        }
        return count;
    }

    public static void consumeBloodCoins(Player player, int amount) {
        for (int i = 0; i < player.getInventory().items.size(); i++) {
            ItemStack stack = player.getInventory().items.get(i);
            if (stack.getItem() == ModItems.BLOOD_COIN.get()) {
                int toRemove = Math.min(amount, stack.getCount());
                stack.shrink(toRemove);
                amount -= toRemove;
                if (amount <= 0) break;
            }
        }
    }

    public static boolean hasFullBattleMaidSet(Player player) {
        ItemStack head = player.getInventory().getArmor(3);
        ItemStack chest = player.getInventory().getArmor(2);
        ItemStack legs = player.getInventory().getArmor(1);
        ItemStack feet = player.getInventory().getArmor(0);

        return head.getItem() == ModItems.BATTLE_MAID_HELMET.get()
                && chest.getItem() == ModItems.BATTLE_MAID_CHESTPLATE.get()
                && legs.getItem() == ModItems.BATTLE_MAID_LEGGINGS.get()
                && feet.getItem() == ModItems.BATTLE_MAID_BOOTS.get();
    }

    public static boolean hasFullHFMaidSet(Player player) {
        ItemStack head = player.getInventory().getArmor(3);
        ItemStack chest = player.getInventory().getArmor(2);
        ItemStack legs = player.getInventory().getArmor(1);
        ItemStack feet = player.getInventory().getArmor(0);

        return head.getItem() == ModItems.HF_BATTLE_MAID_HELMET.get()
                && chest.getItem() == ModItems.HF_BATTLE_MAID_CHESTPLATE.get()
                && legs.getItem() == ModItems.HF_BATTLE_MAID_LEGGINGS.get()
                && feet.getItem() == ModItems.HF_BATTLE_MAID_BOOTS.get();
    }

    private static boolean isStackingEffect(MobEffect effect) {
        var holder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(effect);
        return holder.is(ModMobEffects.STACKING_EFFECTS);
    }

    public static void applyEffect(MobEffectInstance instance, ItemStack source, LivingEntity target, @Nullable LivingEntity cause) {
        var existing = target.getEffect(instance.getEffect());

        if (existing != null && isStackingEffect(instance.getEffect())) {
            var increased = new MobEffectInstance(
                    instance.getEffect(),
                    instance.getDuration(),
                    existing.getAmplifier() + 1,
                    instance.isAmbient(),
                    instance.isVisible(),
                    instance.showIcon(),
                    null,
                    instance.getFactorData()
            );
            target.addEffect(increased);
            if (instance.getEffect() instanceof IStackingEffect stacking) {
                stacking.onIncreasedTo(increased, source, target, target.level());
            }
        } else if (instance.getEffect().isInstantenous()) {
            instance.getEffect().applyInstantenousEffect(cause, cause, target, instance.getAmplifier(), 1.0);
        } else {
            target.addEffect(instance);
        }
    }

    public static void applyEffectPlus(MobEffectInstance instance, ItemStack source, LivingEntity target, @Nullable LivingEntity cause, int pAmplifier) {
        var existing = target.getEffect(instance.getEffect());

        if (existing != null && isStackingEffect(instance.getEffect())) {
            var increased = new MobEffectInstance(
                    instance.getEffect(),
                    instance.getDuration(),
                    existing.getAmplifier() + pAmplifier,
                    instance.isAmbient(),
                    instance.isVisible(),
                    instance.showIcon(),
                    null,
                    instance.getFactorData()
            );
            target.addEffect(increased);
            if (instance.getEffect() instanceof IStackingEffect stacking) {
                stacking.onIncreasedTo(increased, source, target, target.level());
            }
        } else if (instance.getEffect().isInstantenous()) {
            instance.getEffect().applyInstantenousEffect(cause, cause, target, instance.getAmplifier(), 1.0);
        } else {
            target.addEffect(instance);
        }
    }

    public static void playHexedParticlesAndSound(LevelAccessor world, double x, double y, double z) {
        if (!(world instanceof ServerLevel serverLevel)) return;

        // Partículas de humo
        serverLevel.sendParticles(ParticleTypes.SMOKE, x, y + 0.72, z, 25, 0.0, 0.25, 0.0, 0.15);

        // Partículas personalizadas
        serverLevel.sendParticles(
                TerramityModParticleTypes.STYGIAN_PARTICLE.get(),
                x, y + 0.72, z,
                35, 0.0, 0.25, 0.0, 0.15
        );

        // Sonido (servidor y cliente)
        if (world instanceof Level level) {
            SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:hexed"));
            SoundEvent sound2 = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity:hexed_super"));
            if (sound == null) return;

            if (!level.isClientSide) {
                level.playSound(null, new BlockPos((int)x, (int)y, (int)z), sound, SoundSource.PLAYERS, 2.0F, 1.0F);
                level.playSound(null, new BlockPos((int)x, (int)y, (int)z), sound2, SoundSource.PLAYERS, 0.75F, 1.0F);
            } else {
                level.playLocalSound(x, y, z, sound, SoundSource.PLAYERS, 2.0F, 1.0F, false);
                level.playLocalSound(x, y, z, sound2, SoundSource.PLAYERS, 0.75F, 1.0F, false);
            }
        }
    }

}
