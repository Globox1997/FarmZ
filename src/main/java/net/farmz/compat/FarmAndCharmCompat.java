package net.farmz.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.farmz.data.FarmLoader;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.satisfy.farm_and_charm.platform.PlatformHelper;

public class FarmAndCharmCompat {

    public static final boolean isFarmAndCharmLoaded = FabricLoader.getInstance().isModLoaded("farm_and_charm");

    private static boolean disabled = false;

    public static float getRainGrowthMultiplier(ServerWorld world, BlockPos pos) {
        if (isFarmAndCharmLoaded && !disabled && world.hasRain(pos.up())) {
            try {
                if (PlatformHelper.isRainGrowthEffectEnabled()) {
                    return 1.0F + (world.isThundering() ? 0.7F : 0.5F) * PlatformHelper.getRainGrowthMultiplier();
                }
            } catch (LinkageError e) {
                disabled = true;
                FarmLoader.LOGGER.warn("Farm and Charm rain growth compat got disabled, this Farm and Charm version is not supported: {}", e.toString());
            }
        }
        return 1.0F;
    }
}
