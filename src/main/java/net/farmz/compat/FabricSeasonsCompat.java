package net.farmz.compat;

import io.github.lucaargolo.seasons.FabricSeasons;
import io.github.lucaargolo.seasons.utils.FertilizableUtil;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class FabricSeasonsCompat {

    public static float getGrowthMultiplier(ServerWorld world, BlockPos pos, BlockState state) {
        if (!FabricSeasons.CONFIG.isSeasonMessingCrops()) {
            return 1.0F;
        }
        return FertilizableUtil.getMultiplier(world, pos, state);
    }
}
