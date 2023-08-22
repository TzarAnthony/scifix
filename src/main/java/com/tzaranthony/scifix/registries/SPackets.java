package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.core.network.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.Iterator;

public class SPackets {
    private static SimpleChannel net;
    private static int id = 0;
    private static int nextId() {
        return id++;
    }

    public static void registerPackets() {
        net = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(Scifix.MOD_ID, "network"), () -> "1.0", v -> true, v -> true
        );

        net.messageBuilder(FluidS2CPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(FluidS2CPacket::new)
                .encoder(FluidS2CPacket::write)
                .consumer(FluidS2CPacket::handle)
                .add();

        net.messageBuilder(ItemS2CPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ItemS2CPacket::new)
                .encoder(ItemS2CPacket::write)
                .consumer(ItemS2CPacket::handle)
                .add();

        net.messageBuilder(EnergyS2CPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergyS2CPacket::new)
                .encoder(EnergyS2CPacket::write)
                .consumer(EnergyS2CPacket::handle)
                .add();

        net.messageBuilder(HeatS2CPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(HeatS2CPacket::new)
                .encoder(HeatS2CPacket::write)
                .consumer(HeatS2CPacket::handle)
                .add();

//        net.messageBuilder(GasS2CPacket.class, nextId(), NetworkDirection.PLAY_TO_CLIENT)
//                .decoder(GasS2CPacket::new)
//                .encoder(GasS2CPacket::write)
//                .consumer(GasS2CPacket::handle)
//                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        net.sendToServer(message);
    }

    public static <MSG> void sendToAllPlayers(MSG message) {
        Iterator players = ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().iterator();

        while(players.hasNext()) {
            ServerPlayer player = (ServerPlayer) players.next();
            sendToPlayer(message, player);
        }
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        net.sendTo(message, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <MSG> void sendToClients(MSG message) {
        net.send(PacketDistributor.ALL.noArg(), message);
    }
}