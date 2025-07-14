package net.daphne.lethality.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;

public class SoftClothItem extends Item {

    public SoftClothItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f, // velocidad y expansión
                new int[]{254, 244, 255},
                new int[]{255, 229, 246},
                new int[]{255, 216, 238},
                new int[]{255, 204, 233},
                new int[]{255, 186, 225},
                new int[]{255, 186, 225},
                new int[]{255, 204, 233},
                new int[]{255, 216, 238},
                new int[]{255, 229, 246},
                new int[]{254, 244, 255}
        ).withStyle(ChatFormatting.BOLD);
    }
}
