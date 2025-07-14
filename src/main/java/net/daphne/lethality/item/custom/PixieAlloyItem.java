package net.daphne.lethality.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;

public class PixieAlloyItem extends Item {

    public PixieAlloyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f, // velocidad y expansión
                new int[]{235, 13, 209},
                new int[]{255, 98, 207},
                new int[]{255, 135, 199},
                new int[]{255, 172, 216},
                new int[]{255, 197, 229},
                new int[]{255, 241, 254},
                new int[]{255, 241, 254},
                new int[]{255, 197, 229},
                new int[]{255, 172, 216},
                new int[]{255, 135, 199},
                new int[]{255, 98, 207},
                new int[]{235, 13, 209}
        ).withStyle(ChatFormatting.BOLD);
    }
}
