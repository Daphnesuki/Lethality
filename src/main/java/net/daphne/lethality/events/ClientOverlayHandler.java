package net.daphne.lethality.events;

import com.mojang.blaze3d.systems.RenderSystem;
import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.item.custom.RealKnifeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LethalityMod.MOD_ID, value = Dist.CLIENT)
public class ClientOverlayHandler {
    private static final ResourceLocation CHARGE_BAR = new ResourceLocation(LethalityMod.MOD_ID, "textures/gui/genocide_charge_bar.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        ItemStack stack = player.getMainHandItem();
        if (!(stack.getItem() instanceof RealKnifeItem)) return;

        CompoundTag tag = stack.getOrCreateTag();
        int genocideCount = tag.getInt("GenocideCount");
        boolean active = tag.getBoolean("GenocideActive");

        int maxBars = 11; // 11 frames (0 al 10)

        // Clamp para evitar salirnos del rango de frames válidos
        int barIndex = Math.min(genocideCount, maxBars - 1);

        if (active) {
            // Opcional: mostrar un frame especial si está en modo genocida
            barIndex = maxBars - 1; // o usar otro valor si tu sprite tiene uno especial
        }

        int spriteWidth = 32;
        int spriteHeight = 99;

        int x = 10;
        int screenHeight = mc.getWindow().getGuiScaledHeight();
        int y = (screenHeight / 2) - (spriteHeight / 2); // Centrado vertical


        RenderSystem.enableBlend();
        event.getGuiGraphics().blit(
                CHARGE_BAR,
                x, y,
                barIndex * spriteWidth, 0,
                spriteWidth, spriteHeight,
                spriteWidth * maxBars, spriteHeight
        );
        RenderSystem.disableBlend();
    }
}


