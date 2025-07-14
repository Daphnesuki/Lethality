package net.daphne.lethality.networking;

import net.daphne.lethality.item.custom.BrokenBiomeBladeItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SelectModePacket {
    private final int mode;

    public SelectModePacket(int mode) {
        this.mode = mode;
    }

    public SelectModePacket(FriendlyByteBuf buf) {
        this.mode = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(mode);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayer player = contextSupplier.get().getSender();
            if (player != null) {
                ItemStack stack = player.getMainHandItem();
                if (stack.getItem() instanceof BrokenBiomeBladeItem blade) {
                    blade.setMode(stack, mode);
                    stack.getOrCreateTag().putInt("CustomModelData", mode);
                    //player.displayClientMessage(Component.literal("Switched to mode " + mode), true);
                }
            }
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
