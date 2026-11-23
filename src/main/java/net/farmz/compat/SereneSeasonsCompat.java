package net.farmz.compat;

import net.farmz.data.FarmLoader;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sereneseasons.season.SeasonalCropGrowthHandler;

public class SereneSeasonsCompat {

    private static boolean disabled = false;

    public static boolean canGrow(ServerWorld world, BlockPos pos, BlockState state) {
        if (!disabled) {
            try {
                CallbackInfo info = new CallbackInfo("farmz_crop_growth", true);
                SeasonalCropGrowthHandler.onCropGrowth(world, pos, state, info);
                return !info.isCancelled();
            } catch (LinkageError e) {
                disabled = true;
                FarmLoader.LOGGER.warn("Serene Seasons compat got disabled, this Serene Seasons version is not supported: {}", e.toString());
            }
        }
        return true;
    }
}
