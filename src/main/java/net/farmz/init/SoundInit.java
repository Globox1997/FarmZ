package net.farmz.init;

import net.farmz.FarmMain;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class SoundInit {

    public static SoundEvent WATERING_CAN = register("watering_can");

    private static SoundEvent register(String id) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(FarmMain.identifierOf(id)));
    }

    public static void init() {

    }
}
