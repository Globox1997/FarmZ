package net.farmz.init;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.farmz.FarmMain;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ParticleInit {

    public static final SimpleParticleType SPRINKLER_PARTICLE = Registry.register(Registries.PARTICLE_TYPE, FarmMain.identifierOf("sprinkler"), FabricParticleTypes.simple());

    public static void init() {
    }
}
