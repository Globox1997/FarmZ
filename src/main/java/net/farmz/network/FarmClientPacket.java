package net.farmz.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.farmz.data.FarmLoader;
import net.farmz.network.packet.GoldItemChancePacket;
import net.minecraft.util.Identifier;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class FarmClientPacket {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(GoldItemChancePacket.PACKET_ID, (payload, context) -> {
            Map<Identifier, Integer> goldChanceItems = payload.goldChanceItems();
            context.client().execute(() -> {
                FarmLoader.GOLD_CHANCE_ITEMS.clear();
                FarmLoader.GOLD_CHANCE_ITEMS.putAll(goldChanceItems);
            });
        });
    }
}
