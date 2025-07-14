package net.daphne.lethality.events;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.init.ModMobEffects;
import net.daphne.lethality.item.ModItems;
import net.daphne.lethality.item.custom.RealKnifeItem;
import net.daphne.lethality.util.TooltipGlitchHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.daphne.lethality.networking.ModMessages;
import net.daphne.lethality.networking.packets.C2S.AcidicSlashC2S;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    @SubscribeEvent
    public static void onLeftClickEmpty(PlayerInteractEvent.LeftClickEmpty event) {
        if (event.getSide().isClient()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                triggerSlashEvent(player);
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEntity(AttackEntityEvent event) {
        if (event.getEntity().level().isClientSide) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player != null) {
                triggerSlashEvent(player);
            }
        }
    }

    @SubscribeEvent
    public static void onMobKilled(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof Player player)) return;

        ItemStack heldItem = player.getMainHandItem();
        if (heldItem.getItem() instanceof RealKnifeItem) {
            CompoundTag tag = heldItem.getOrCreateTag();

            // Siempre incrementa el contador total de muertes
            int kills = tag.getInt("KillCount");
            tag.putInt("KillCount", kills + 1);

            // Solo incrementar el GenocideCount si el jugador NO está bajo el efecto RAGE
            if (!player.hasEffect(ModMobEffects.RAGE.get())) {
                int genocide = tag.getInt("GenocideCount");
                tag.putInt("GenocideCount", genocide + 1);
            }
        }
    }

    public static void triggerSlashEvent(LocalPlayer player) {
        ItemStack stack = player.getMainHandItem();
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());

        if (id == null) return;

        Set<ResourceLocation> validIds = Set.of(
                new ResourceLocation("lethality", "defiled_greatsword"),
                new ResourceLocation("lethality", "blighted_cleaver"),
                new ResourceLocation("lethality", "starlight"),
                new ResourceLocation("lethality", "real_knife"),
                new ResourceLocation("lethality", "bladecrest_oathsword"),
                new ResourceLocation("lethality", "forbidden_oathblade"),
                new ResourceLocation("lethality", "exalted_oathblade"),
                new ResourceLocation("lethality", "devils_devastation"),
                new ResourceLocation("lethality", "gaels_greatsword"),
                new ResourceLocation("lethality", "vehemence"),
                new ResourceLocation("lethality", "pickle_paddle"),
                new ResourceLocation("lethality", "broken_biome_blade"),
                new ResourceLocation("lethality", "hf_meowrasama")
        );

        if (validIds.contains(id)) {
            ModMessages.sendToServer(new AcidicSlashC2S());
        }
    }


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            TooltipGlitchHelper.clientTick();
        }
    }
}
