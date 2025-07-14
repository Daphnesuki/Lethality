package net.daphne.lethality.client;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

public class RadialModeHandler {
    public static boolean isOpen = false;
    public static ItemStack currentStack = ItemStack.EMPTY;
    public static int selectedIndex = 0;
    public static double openX;
    public static double openY;

    public static void open(ItemStack stack) {
        Minecraft mc = Minecraft.getInstance();
        openX = mc.mouseHandler.xpos();
        openY = mc.mouseHandler.ypos();
        isOpen = true;
        currentStack = stack;

        long window = mc.getWindow().getWindow();
        mc.mouseHandler.releaseMouse();
        Window window2 = Minecraft.getInstance().getWindow();
        org.lwjgl.glfw.GLFW.glfwSetInputMode(window2.getWindow(), org.lwjgl.glfw.GLFW.GLFW_CURSOR, org.lwjgl.glfw.GLFW.GLFW_CURSOR_HIDDEN);


        int centerX = mc.getWindow().getScreenWidth() / 2;
        int centerY = mc.getWindow().getScreenHeight() / 2;
        GLFW.glfwSetCursorPos(window, centerX, centerY);
    }


    public static void close() {
        Minecraft mc = Minecraft.getInstance();
        isOpen = false;
        currentStack = ItemStack.EMPTY;
        selectedIndex = 0;
        mc.mouseHandler.grabMouse();
    }
}
