package net.daphne.lethality.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.daphne.lethality.networking.ModMessages;
import net.daphne.lethality.networking.SelectModePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import net.minecraftforge.client.event.InputEvent;

@Mod.EventBusSubscriber(modid = "lethality", value = Dist.CLIENT)
public class RadialInputHandler {

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton event) {
        if (RadialModeHandler.isOpen) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void cancelScrolling(InputEvent.MouseScrollingEvent event) {
        if (RadialModeHandler.isOpen) {
            event.setCanceled(true);
        }
    }

    private static int getSelectedMode(Minecraft mc, double mouseX, double mouseY) {
        int centerX = mc.getWindow().getGuiScaledWidth() / 2;
        int centerY = mc.getWindow().getGuiScaledHeight() / 2;
        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        int selectedMode;
        if (distance <= 16) {
            selectedMode = 0;
        } else {
            double angle = Math.atan2(dy, dx);
            if (angle < 0) angle += 2 * Math.PI;
            double adjusted = angle - Math.PI / 2 + Math.PI / 4;
            if (adjusted < 0) adjusted += 2 * Math.PI;
            int sector = (int) (adjusted / (2 * Math.PI / 4));
            selectedMode = sector + 1;
        }
        return selectedMode;
    }

    @SubscribeEvent
    public static void onMouseReleased(InputEvent.MouseButton event) {
        if (RadialModeHandler.isOpen && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT && event.getAction() == GLFW.GLFW_RELEASE) {
            Minecraft mc = Minecraft.getInstance();

            double mouseX = mc.mouseHandler.xpos() * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth();
            double mouseY = mc.mouseHandler.ypos() * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight();
            int selectedMode = getSelectedMode(mc, mouseX, mouseY);

            if (mc.player != null) {
                ItemStack stack = mc.player.getMainHandItem();

                if (!stack.isEmpty()) {
                    ModMessages.sendToServer(new SelectModePacket(selectedMode));
                }
            }
            RadialModeHandler.close();
        }
    }
}
