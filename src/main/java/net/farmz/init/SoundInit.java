package net.farmz.init;

import net.farmz.FarmMain;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

public class SoundInit {

    public static SoundEvent WATERING_CAN = register("watering_can");

    public static SoundEvent BEETHUUT_IDLE_EVENT = register("beethuut_idle");
    public static SoundEvent BEETHUUT_HURT_EVENT = register("beethuut_hurt");
    public static SoundEvent BEETHUUT_DEATH_EVENT = register("beethuut_death");

    private static SoundEvent register(String id) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(FarmMain.identifierOf(id)));
    }

    public static void init() {

    }
}
