package net.daphne.lethality.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;

public class BloodCoinItem extends Item {

    public BloodCoinItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,
                new int[]{255, 0, 0},
                new int[]{255, 55, 55},
                new int[]{255, 110, 110},
                new int[]{255, 110, 110},
                new int[]{255, 55, 55},
                new int[]{255, 0, 0}
        ).withStyle(ChatFormatting.BOLD);
    }

}
