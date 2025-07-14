package net.daphne.lethality.networking;

import net.daphne.lethality.LethalityMod;
import net.daphne.lethality.networking.packets.C2S.AcidicSlashC2S;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {

    private static SimpleChannel INSTANCE;

    //Makes all messages have different id's
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(LethalityMod.MOD_ID, "messages")).networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
        INSTANCE = net;
        net.messageBuilder(AcidicSlashC2S.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(AcidicSlashC2S::new).encoder(AcidicSlashC2S::toBytes).consumerMainThread(AcidicSlashC2S::handle).add();
        net.messageBuilder(SelectModePacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SelectModePacket::new)
                .encoder(SelectModePacket::toBytes)
                .consumerMainThread(SelectModePacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToAllPlayers(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }
}
