package net.farmz;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.farmz.init.RenderInit;
import net.farmz.network.FarmClientPacket;

@Environment(EnvType.CLIENT)
public class FarmClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RenderInit.init();
        FarmClientPacket.init();
    }

}
