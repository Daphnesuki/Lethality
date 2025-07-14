package net.daphne.lethality.networking.packets.C2S;

import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.particles.ModParticles;
import net.mcreator.terramity.init.TerramityModMobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static net.daphne.lethality.util.ModUtils.hasFullBattleMaidSet;
import static net.daphne.lethality.util.ModUtils.hasFullHFMaidSet;

// net.daphne.lethality.network.BattleMaidAbilityPacket
public class BattleMaidAbilityPacket {
    public BattleMaidAbilityPacket() {}

    public static void encode(BattleMaidAbilityPacket msg, FriendlyByteBuf buf) {}
    public static BattleMaidAbilityPacket decode(FriendlyByteBuf buf) {
        return new BattleMaidAbilityPacket();
    }

    public static void handle(BattleMaidAbilityPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                if (hasFullBattleMaidSet(player)){
                    MobEffectInstance current = player.getEffect(ModMobEffects.CUTENESS.get());
                    if (current != null) {
                        int newAmplifier = (current.getAmplifier() * 2) + 1;
                        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity", "buff_holy"));
                        BlockPos pos = player.blockPosition();
                        Level level = player.level();
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(
                                    ModParticles.CUTE_SPARKLES.get(),
                                    player.getX(), player.getY() + 1.0, player.getZ(),
                                    20, 0.3, 0.5, 0.3, 0.2);
                        }
                        if (!level.isClientSide()) {
                            // En servidor
                            level.playSound(null, pos, sound, SoundSource.PLAYERS, 1.5f, 1.5f);
                        } else {
                            // En cliente
                            level.playLocalSound(pos, sound, SoundSource.PLAYERS, 1.5f, 1.5f, false);
                        }

                        player.addEffect(new MobEffectInstance(ModMobEffects.CUTENESS.get(), 200, newAmplifier, false, false, true));
                        player.addEffect(new MobEffectInstance(ModMobEffects.BMMODE.get(), 200, 0, false, false, false));
                        player.addEffect(new MobEffectInstance(TerramityModMobEffects.ARMOR_SET_ABILITY_COOLDOWN.get(), 900, 0, false, false, true));
                    }
                } else if (hasFullHFMaidSet(player)) {
                    MobEffectInstance current = player.getEffect(ModMobEffects.CUTENESS.get());
                    if (current != null) {
                        int newAmplifier = (current.getAmplifier() * 3) + 1;

                        SoundEvent sound = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("terramity", "buff_holy"));
                        BlockPos pos = player.blockPosition();
                        Level level = player.level();
                        if (level instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(
                                    ModParticles.CUTE_SPARKLES.get(),
                                    player.getX(), player.getY() + 1.0, player.getZ(),
                                    40, 0.3, 0.5, 0.3, 0.4);
                        }
                        if (!level.isClientSide()) {
                            // En servidor
                            level.playSound(null, pos, sound, SoundSource.PLAYERS, 1.5f, 1.5f);
                            level.playSound(null, player.blockPosition(), SoundEvents.CAT_DEATH, SoundSource.PLAYERS, 2.5F, 0.85F);
                        } else {
                            // En cliente
                            level.playLocalSound(pos, sound, SoundSource.PLAYERS, 1.5f, 1.5f, false);
                            level.playLocalSound(pos, SoundEvents.CAT_DEATH, SoundSource.PLAYERS, 2.5F, 0.85F, false);
                        }

                        player.addEffect(new MobEffectInstance(ModMobEffects.CUTENESS.get(), 300, newAmplifier, false, false, true));
                        player.addEffect(new MobEffectInstance(ModMobEffects.HFMMODE.get(), 300, 0, false, false, false));
                        player.addEffect(new MobEffectInstance(TerramityModMobEffects.ARMOR_SET_ABILITY_COOLDOWN.get(), 1200, 0, false, false, true));
                    }
                }
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
