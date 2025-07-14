package net.daphne.lethality.networking.packets.C2S;

import net.daphne.lethality.item.custom.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AcidicSlashC2S {

    public AcidicSlashC2S() {

    }

    //Same as decode
    public AcidicSlashC2S(FriendlyByteBuf buf) {

    }

    // Same as encode
    public void toBytes(FriendlyByteBuf buf) {

    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            this.handlePacket(player, this);
        });
        context.setPacketHandled(true);
    }

    private void handlePacket(ServerPlayer player, AcidicSlashC2S packet) {
        DefiledGreatswordItem.shootTripleAcidicSlash(player.level(), player);
        BlightedCleaverItem.shootAcidicSlash(player.level(), player);
        StarlightItem.shootStarlightStab(player.level(), player);
        RealKnifeItem.shootRealSlash(player.level(), player);
        BladecrestOathswordItem.shootBloodScytheSlash(player.level(), player);
        ForbiddenOathbladeItem.shootForbiddenScytheSlash(player.level(), player);
        ExaltedOathbladeItem.shootForbiddenScytheSlash(player.level(), player);
        DevilsDevastationItem.shootForbiddenScytheSlash(player.level(), player);
        GaelsGreatswordItem.shootForbiddenScytheSlash(player.level(), player);
        VehemenceItem.shootVehemenceBolt(player.level(), player);
        PicklePaddleItem.shootPickle(player.level(), player);
        BrokenBiomeBladeItem.shootProjection(player.level(), player);
        HFMeowrasamaItem.meowrasamaSlashParticle(player.level(), player);
    }
}
