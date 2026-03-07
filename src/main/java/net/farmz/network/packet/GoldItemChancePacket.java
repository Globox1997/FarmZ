package net.farmz.network.packet;

import net.farmz.FarmMain;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.Map;

public record GoldItemChancePacket(Map<Identifier, Integer> goldChanceItems) implements CustomPayload {

    public static final CustomPayload.Id<GoldItemChancePacket> PACKET_ID = new CustomPayload.Id<>(FarmMain.identifierOf("gold_item_chance_packet"));

    public static final PacketCodec<RegistryByteBuf, GoldItemChancePacket> PACKET_CODEC = PacketCodec.of((value, buf) -> {
        buf.writeMap(value.goldChanceItems, PacketByteBuf::writeIdentifier, PacketByteBuf::writeInt);
    }, buf -> new GoldItemChancePacket(buf.readMap(PacketByteBuf::readIdentifier, PacketByteBuf::readInt)));

    @Override
    public Id<? extends CustomPayload> getId() {
        return PACKET_ID;
    }

}
