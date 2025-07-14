package net.daphne.lethality.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;

public class ImprobabilitySteelItem extends Item {

    public ImprobabilitySteelItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f, // velocidad y expansión
                new int[]{75, 75, 99},
                new int[]{94, 99, 124},
                new int[]{126, 127, 159},
                new int[]{162, 161, 193},
                new int[]{188, 187, 216},
                new int[]{188, 187, 216},
                new int[]{162, 161, 193},
                new int[]{126, 127, 159},
                new int[]{94, 99, 124},
                new int[]{75, 75, 99}
        ).withStyle(ChatFormatting.BOLD);
    }
}
