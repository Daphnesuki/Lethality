package net.daphne.lethality.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

import static net.daphne.lethality.util.ModUtils.addColorGradientText;

public class EscarapelaItem extends Item {

    public EscarapelaItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack stack) {
        Component baseName = Component.translatable(this.getDescriptionId(stack));
        return addColorGradientText(baseName, 0.25f, 2.0f,
                new int[]{0, 255, 255},
                new int[]{161, 255, 255},
                new int[]{185, 255, 255},
                new int[]{208, 255, 255},
                new int[]{238, 255, 255},
                new int[]{254, 255, 255},
                new int[]{255, 205, 0},
                new int[]{254, 255, 255},
                new int[]{238, 255, 255},
                new int[]{208, 255, 255},
                new int[]{185, 255, 255},
                new int[]{161, 255, 255}
        ).withStyle(ChatFormatting.BOLD);
    }

}
