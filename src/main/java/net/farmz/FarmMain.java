package net.farmz;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.farmz.init.*;
import net.minecraft.util.Identifier;

public class FarmMain implements ModInitializer {

    public static final boolean isLevelzLoaded = FabricLoader.getInstance().isModLoaded("levelz");

    @Override
    public void onInitialize() {
        ConfigInit.init();
        ItemInit.init();
        BlockInit.init();
        EventInit.init();
        SoundInit.init();
        EntityInit.init();
    }

    public static Identifier identifierOf(String name) {
        return Identifier.of("farmz", name);
    }

}
