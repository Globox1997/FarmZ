package net.farmz.init;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.farmz.data.FarmLoader;
import net.farmz.network.FarmServerPacket;
import net.minecraft.resource.ResourceType;
import net.minecraft.server.network.ServerPlayerEntity;

public class LoaderInit {

    public static void init() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new FarmLoader());

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, serverResourceManager, success) -> {
            if (success) {
                for (int i = 0; i < server.getPlayerManager().getPlayerList().size(); i++) {
                    ServerPlayerEntity serverPlayerEntity = server.getPlayerManager().getPlayerList().get(i);

                    FarmServerPacket.syncGoldItemChances(serverPlayerEntity);
                }
                FarmLoader.LOGGER.info("Finished reload on {}", Thread.currentThread());
            } else {
                FarmLoader.LOGGER.error("Failed to reload on {}", Thread.currentThread());
            }
        });
    }
}
