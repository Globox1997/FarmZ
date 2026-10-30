package net.farmz.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.farmz.data.FarmLoader;
import net.farmz.network.packet.GoldItemChancePacket;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class FarmServerPacket {

    public static void init() {
        PayloadTypeRegistry.playS2C().register(GoldItemChancePacket.PACKET_ID, GoldItemChancePacket.PACKET_CODEC);

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> syncGoldItemChances(handler.getPlayer()));
    }

    public static void syncGoldItemChances(ServerPlayerEntity serverPlayerEntity) {
        ServerPlayNetworking.send(serverPlayerEntity, new GoldItemChancePacket(new HashMap<>(FarmLoader.GOLD_CHANCE_ITEMS)));
    }
}
