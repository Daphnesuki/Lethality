package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.custom.NightfallItem;
import net.daphne.lethality.item.custom.StopSignItem;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class StopSignEvents {

    private static final UUID STOP_SIGN_SPEED_MODIFIER = UUID.fromString("a7b964c3-7643-4bc5-b763-f4a3f933bfb2");

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;

        boolean hasStopSignEquipped =
                player.getMainHandItem().getItem() instanceof StopSignItem ||
                        player.getOffhandItem().getItem() instanceof StopSignItem;

        var attrInstance = player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ATTACK_SPEED);

        if (attrInstance != null) {
            var currentModifier = attrInstance.getModifier(StopSignItem.STOP_SIGN_SPEED_MODIFIER); // 👈 recupera el modifier por UUID

            if (!hasStopSignEquipped && currentModifier != null) {
                attrInstance.removeModifier(currentModifier); // 👈 removeModifier necesita el objeto, no solo UUID
            }
        }
    }
}