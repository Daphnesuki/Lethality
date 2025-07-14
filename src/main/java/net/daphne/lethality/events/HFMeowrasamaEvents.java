package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.util.ModUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.daphne.lethality.util.ModUtils.hasFullBattleMaidSet;
import static net.daphne.lethality.util.ModUtils.hasFullHFMaidSet;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class HFMeowrasamaEvents {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (player.level().isClientSide) return;

        ItemStack stack = player.getMainHandItem();
        if (stack.is(ModItems.HF_MEOWRASAMA.get()) && stack.getOrCreateTag().getInt("CustomModelData") == 1) {
            if (!player.hasEffect(ModMobEffects.BLADE_MODE.get())) {
                stack.getOrCreateTag().putInt("CustomModelData", 0);
                player.level().playSound(null, player.blockPosition(), SoundEvents.CAT_DEATH, SoundSource.PLAYERS, 2.5F, 0.85F);
            }
        }
        if (stack.is(ModItems.HF_MEOWRASAMA.get()) && ModUtils.hasFullBattleMaidSet(player)) {
            MobEffectInstance current = player.getEffect(ModMobEffects.CUTENESS.get());
            if (player.hasEffect(ModMobEffects.BMMODE.get())) {
                return;
            }
        }
    }
}
