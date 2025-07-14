package net.daphne.lethality.util;

import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.Random;

public class TooltipGlitchHelper {
    private static final Random RANDOM = new Random();
    private static final List<String> TOOLTIP_KEYS = List.of(
            "tooltip.lethality.stop_sign.tooltip",
            "tooltip.lethality.stop_sign.tooltip",
            "tooltip.lethality.stop_sign.tooltip",
            "tooltip.lethality.stop_sign.tooltip",
            "tooltip.lethality.stop_sign.tooltip",
            "tooltip.lethality.stop_sign.alt_tooltip_1",
            "tooltip.lethality.stop_sign.alt_tooltip_2",
            "tooltip.lethality.stop_sign.alt_tooltip_3",
            "tooltip.lethality.stop_sign.alt_tooltip_4",
            "tooltip.lethality.stop_sign.alt_tooltip_5",
            "tooltip.lethality.stop_sign.alt_tooltip_6",
            "tooltip.lethality.stop_sign.alt_tooltip_7"
    );

    private static int tickCounter = 0;
    private static final int SWITCH_INTERVAL = 2;
    private static int currentIndex = 0;

    public static void clientTick() {
        tickCounter++;
        if (tickCounter >= SWITCH_INTERVAL) {
            tickCounter = 0;
            currentIndex = RANDOM.nextInt(TOOLTIP_KEYS.size());
        }
    }

    public static Component getGlitchedTooltip() {
        return Component.translatable(TOOLTIP_KEYS.get(currentIndex));
    }
}
