package net.farmz.compat;

import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.FertilizableUtil;
import net.farmz.data.FarmLoader;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FabricSeasonsCompat {

    private static boolean disabled = false;

    public static float getGrowthMultiplier(ServerWorld world, BlockPos pos, BlockState state) {
        if (!disabled) {
            try {
                if (!FabricSeasons.CONFIG.isSeasonMessingCrops()) {
                    return 1.0F;
                }
                return FertilizableUtil.getMultiplier(world, pos, state);
            } catch (LinkageError e) {
                disabled = true;
                FarmLoader.LOGGER.warn("Fabric Seasons compat got disabled, this Fabric Seasons version is not supported: {}", e.toString());
            }
        }
        return 1.0F;
    }
}
