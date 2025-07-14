package net.daphne.lethality.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import net.daphne.lethality.client.RadialModeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;
import java.util.List;

@Mod.EventBusSubscriber(modid = "lethality", value = Dist.CLIENT)
public class RadialOverlayRenderer {

    private static final ResourceLocation CURSOR_TEX = new ResourceLocation("lethality", "textures/gui/cursor_icon.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation("lethality", "textures/gui/biome_menu.png");

    public record ModeEntry(String name, ResourceLocation icon, double angle, java.awt.Color color) {}

    public static final List<ModeEntry> MODES = List.of(
            new ModeEntry("Grovetender's Touch", new ResourceLocation("lethality", "textures/gui/poison.png"), 0,
                    new Color(0x4CCF4C)), // Veneno - verde
            new ModeEntry("Biting Embrace", new ResourceLocation("lethality", "textures/gui/ice.png"), Math.PI / 2,
                    new Color(0x5AC8FA)), // Hielo - azul claro
            new ModeEntry("Arid Grandeur", new ResourceLocation("lethality", "textures/gui/fire.png"), Math.PI,
                    new Color(0xFF8C42)), // Fuego - naranja
            new ModeEntry("Decay's Retort", new ResourceLocation("lethality", "textures/gui/bleed.png"), 3 * Math.PI / 2,
                    new Color(0xD33636)) // Sangrado - rojo
    );

    private static double lastAngle = 0;
    private static final float[] iconScales = new float[MODES.size()];
    private static int lastSelectedIndex = -1;

    private static float cursorR = 1.0f;
    private static float cursorG = 1.0f;
    private static float cursorB = 1.0f;

    public static List<ModeEntry> getModes() {
        return MODES;
    }

    static {
        for (int i = 0; i < iconScales.length; i++) {
            iconScales[i] = 1.0f;
        }
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        if (!RadialModeHandler.isOpen || RadialModeHandler.currentStack.isEmpty()) return;

        GuiGraphics guiGraphics = event.getGuiGraphics();
        Minecraft mc = Minecraft.getInstance();
        int width = mc.getWindow().getGuiScaledWidth();
        int height = mc.getWindow().getGuiScaledHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        double mouseX = mc.mouseHandler.xpos() * (width / (double) mc.getWindow().getScreenWidth());
        double mouseY = mc.mouseHandler.ypos() * (height / (double) mc.getWindow().getScreenHeight());


        guiGraphics.blit(TEXTURE, centerX - 64, centerY - 64, 0, 0, 128, 128, 128, 128);

        double dx = mouseX - centerX;
        double dy = mouseY - centerY;
        double targetAngle = Math.atan2(dy, dx);
        targetAngle = (targetAngle + Math.PI * 2) % (Math.PI * 2);
        double distance = Math.sqrt(dx * dx + dy * dy);
        double deadZoneRadius = 15.0;

        final double smoothing = 0.2;

        lastAngle = interpolateAngle(lastAngle, targetAngle, smoothing);

        int newIndex = getClosestModeIndex(lastAngle);

        if (distance > deadZoneRadius) {
            if (lastSelectedIndex == -1) {
                lastSelectedIndex = newIndex;
            } else if (lastSelectedIndex != newIndex) {
                double currentAngle = MODES.get(lastSelectedIndex).angle();
                double newAngle = MODES.get(newIndex).angle();
                double diff = angleDifference(newAngle, currentAngle);

                if (diff > Math.toRadians(10)) {
                    lastSelectedIndex = newIndex;
                }
            }
            RadialModeHandler.selectedIndex = lastSelectedIndex;

            double closestAngle = MODES.get(newIndex).angle();
            double angleDiff = angleDifference(lastAngle, closestAngle);
            double normalizedProximity = 1.0 - Math.min(angleDiff / Math.PI, 1.0); // 1.0 cerca, 0.0 lejos

            float hue = (float) (0.0 + 0.4 * normalizedProximity);
            float saturation = 1.0f;
            float brightness = 1.0f;

            int rgb = java.awt.Color.HSBtoRGB(hue, saturation, brightness);
            float r = ((rgb >> 16) & 0xFF) / 255f;
            float g = ((rgb >> 8) & 0xFF) / 255f;
            float b = (rgb & 0xFF) / 255f;

            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(r, g, b, 1.0f);
        } else {
            lastSelectedIndex = -1;
            //RadialModeHandler.selectedIndex = -1;
            RenderSystem.enableBlend();
            RenderSystem.setShaderColor(0.6f, 0.6f, 0.6f, 1.0f);
        }

        double closestAngle = MODES.get(newIndex).angle();
        double angleDiff = angleDifference(lastAngle, closestAngle);
        double normalizedProximity = 1.0 - Math.min(angleDiff / Math.PI, 1.0); // 1.0 cerca, 0.0 lejos

        float targetR, targetG, targetB;

        float deadZoneColor = 0.4f;
        float colorSmoothing = 0.1f;

        if (distance <= deadZoneRadius + 5) {
            targetR = targetG = targetB = deadZoneColor; // gris
        } else {
            ModeEntry mode = MODES.get(newIndex);
            Color baseColor = mode.color();

            float proximity = (float) normalizedProximity;

            float baseR = baseColor.getRed() / 255f;
            float baseG = baseColor.getGreen() / 255f;
            float baseB = baseColor.getBlue() / 255f;

            float gray = 0.4f;
            targetR = baseR * proximity + gray * (1f - proximity);
            targetG = baseG * proximity + gray * (1f - proximity);
            targetB = baseB * proximity + gray * (1f - proximity);
        }

        cursorR += (targetR - cursorR) * colorSmoothing;
        cursorG += (targetG - cursorG) * colorSmoothing;
        cursorB += (targetB - cursorB) * colorSmoothing;

        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(cursorR, cursorG, cursorB, 1.0f);
        guiGraphics.blit(CURSOR_TEX, (int) mouseX - 10, (int) mouseY - 10, 0, 0, 20, 20, 20, 20);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();


        for (int i = 0; i < MODES.size(); i++) {
            ModeEntry mode = MODES.get(i);
            double a = i * (2 * Math.PI / MODES.size());
            int radius = 60;
            int x = (int) (centerX + Math.cos(a) * radius);
            int y = (int) (centerY + Math.sin(a) * radius);

            float targetScale = (i == lastSelectedIndex) ? 1.75f : 1.0f;
            float scaleSmoothing = 0.005f;
            iconScales[i] += (targetScale - iconScales[i]) * scaleSmoothing;

            int baseSize = 32;
            int size = (int) (baseSize * iconScales[i]);
            int halfSize = size / 2;

            boolean selected = (i == lastSelectedIndex);
            if (!selected) {
                RenderSystem.enableBlend();
                RenderSystem.setShaderColor(0.35f, 0.35f, 0.35f, 0.6f);
            }

            guiGraphics.blit(mode.icon, x - halfSize, y - halfSize, 0, 0, size, size, size, size);

            if (!selected) {
                RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                RenderSystem.disableBlend();
            }
        }
        if (distance > deadZoneRadius && lastSelectedIndex >= 0 && lastSelectedIndex < MODES.size()) {
            String selectedName = MODES.get(lastSelectedIndex).name;
            guiGraphics.drawCenteredString(mc.font, selectedName, centerX, centerY + 24, 0xFFFFFF);
        }
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
    }

    private static int getClosestModeIndex(double angle) {
        int closestIndex = -1;
        double smallestDiff = Double.MAX_VALUE;

        for (int i = 0; i < MODES.size(); i++) {
            double modeAngle = MODES.get(i).angle();
            double diff = angleDifference(angle, modeAngle);
            if (diff < smallestDiff) {
                smallestDiff = diff;
                closestIndex = i;
            }
        }

        return closestIndex;
    }

    private static double angleDifference(double a, double b) {
        double diff = Math.abs(a - b) % (2 * Math.PI);
        return diff > Math.PI ? (2 * Math.PI - diff) : diff;
    }
    private static double interpolateAngle(double current, double target, double factor) {
        double diff = ((target - current + Math.PI * 3) % (Math.PI * 2)) - Math.PI;
        return current + diff * factor;
    }
}