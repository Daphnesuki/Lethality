package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.networking.packets.C2S.BattleMaidAbilityPacket;
import net.mcreator.terramity.init.TerramityModKeyMappings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static net.daphne.lethality.util.ModUtils.hasFullBattleMaidSet;
import static net.daphne.lethality.util.ModUtils.hasFullHFMaidSet;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, value = Dist.CLIENT)
public class ClientArmorAbilityHandler {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        Player player = mc.player;

        // Solo si el jugador tiene la armadura completa
        if (hasFullBattleMaidSet(player)) {
            if (TerramityModKeyMappings.ARMOR_SET_BONUS_ABILITY.consumeClick()) {
                if (player.hasEffect(ModMobEffects.BMMODE.get())) {
                    return;
                }
                if (player.hasEffect(ModMobEffects.HFMMODE.get())) {
                    return;
                }
                // Enviar el paquete al servidor
                LethalityMod.NETWORK_CHANNEL.sendToServer(new BattleMaidAbilityPacket());
            }
        }
        if (hasFullHFMaidSet(player)) {
            if (TerramityModKeyMappings.ARMOR_SET_BONUS_ABILITY.consumeClick()) {
                if (player.hasEffect(ModMobEffects.HFMMODE.get())) {
                    return;
                }
                if (player.hasEffect(ModMobEffects.BMMODE.get())) {
                    return;
                }
                // Enviar el paquete al servidor
                LethalityMod.NETWORK_CHANNEL.sendToServer(new BattleMaidAbilityPacket());
            }
        }
    }
}
