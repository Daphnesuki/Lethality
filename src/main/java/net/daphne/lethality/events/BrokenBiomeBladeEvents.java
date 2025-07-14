package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.item.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Iterator;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BrokenBiomeBladeEvents {

    // Etiqueta NBT usada como flag para saber si está en modo 3
    private static final String LIFESTEAL_TAG = "BiomeBladeLifesteal";

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        ItemStack held = player.getMainHandItem();

        if (held.is(ModItems.BROKEN_BIOME_BLADE.get()) && held.getOrCreateTag().getInt("BiomeBladeMode") == 3) {
            // Marcar como lifesteal activo
            player.getPersistentData().putBoolean(LIFESTEAL_TAG, true);
        } else {
            // Remover el flag si no tiene el arma o cambió de modo
            player.getPersistentData().remove(LIFESTEAL_TAG);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        Entity source = event.getSource().getEntity();
        if (!(source instanceof Player player)) return;

        // Si tiene la flag activa, curar al jugador
        if (player.getPersistentData().getBoolean(LIFESTEAL_TAG)) {
            float healed = event.getAmount() * 0.15f; // 10% de lifesteal
            player.heal(healed);
        }
    }
}
