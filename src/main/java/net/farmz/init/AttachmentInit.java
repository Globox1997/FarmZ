package net.farmz.init;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.farmz.FarmMain;

import java.util.HashMap;
import java.util.Map;

public class AttachmentInit {

    public static final AttachmentType<Map<Long, Long>> CROP_GROWTH_TIMES = AttachmentRegistry.<Map<Long, Long>>builder()
            .persistent(Codec.unboundedMap(Codec.STRING.xmap(Long::parseLong, String::valueOf), Codec.LONG).xmap(map -> (Map<Long, Long>) new HashMap<>(map), map -> map))
            .buildAndRegister(FarmMain.identifierOf("crop_growth_times"));

    public static void init() {
    }
}
