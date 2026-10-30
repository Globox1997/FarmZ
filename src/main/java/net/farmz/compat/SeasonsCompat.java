package net.farmz.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class SeasonsCompat {

    public static final boolean isFabricSeasonsLoaded = FabricLoader.getInstance().isModLoaded("seasons");
    public static final boolean isSereneSeasonsLoaded = FabricLoader.getInstance().isModLoaded("sereneseasons");

    public static float getGrowthMultiplier(ServerWorld world, BlockPos pos, BlockState state) {
        if (isFabricSeasonsLoaded) {
            return FabricSeasonsCompat.getGrowthMultiplier(world, pos, state);
        }
        return 1.0F;
    }

    public static boolean canGrow(ServerWorld world, BlockPos pos, BlockState state) {
        if (isSereneSeasonsLoaded) {
            return SereneSeasonsCompat.canGrow(world, pos, state);
        }
        return true;
    }
}
