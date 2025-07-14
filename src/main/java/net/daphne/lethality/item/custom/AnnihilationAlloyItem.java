package net.daphne.lethality.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;

public class AnnihilationAlloyItem extends Item {

    public AnnihilationAlloyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f, // velocidad y expansión

                new int[]{41, 37, 55},      // Gray-ish blue, purple and pink
                new int[]{50, 38, 70},
                new int[]{76, 44, 90},
                new int[]{105, 50, 105},
                new int[]{141, 53, 136},
                new int[]{180, 40, 180},
                new int[]{180, 40, 120},
                new int[]{255, 10, 85},
                new int[]{255, 0, 65},

                new int[]{255, 0, 30},      // Red

                new int[]{255, 40, 0},
                new int[]{255, 72, 0},
                new int[]{187, 75, 0},
                new int[]{255, 120, 0},
                new int[]{224, 136, 0},
                new int[]{255, 203, 25},
                new int[]{255, 229, 110},
                new int[]{255, 255, 180},
                new int[]{255, 255, 203},
                new int[]{255, 255, 251},
                new int[]{255, 255, 251},
                new int[]{255, 255, 203},
                new int[]{255, 255, 180},
                new int[]{255, 203, 25},
                new int[]{224, 136, 0},
                new int[]{255, 120, 0},
                new int[]{187, 75, 0},
                new int[]{255, 72, 0},
                new int[]{255, 40, 0},

                new int[]{255, 0, 30},      // Red

                new int[]{255, 0, 65},      // Gray-ish blue, purple and pink
                new int[]{255, 10, 85},
                new int[]{180, 40, 120},
                new int[]{180, 40, 180},
                new int[]{141, 53, 136},
                new int[]{105, 50, 105},
                new int[]{76, 44, 90},
                new int[]{50, 38, 70},
                new int[]{41, 37, 55}

        ).withStyle(ChatFormatting.BOLD);
    }
}
