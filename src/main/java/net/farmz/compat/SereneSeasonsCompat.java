package net.farmz.compat;

import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sereneseasons.season.SeasonalCropGrowthHandler;

public class SereneSeasonsCompat {

    public static boolean canGrow(ServerWorld world, BlockPos pos, BlockState state) {
        CallbackInfo info = new CallbackInfo("farmz_crop_growth", true);
        SeasonalCropGrowthHandler.onCropGrowth(world, pos, state, info);
        return !info.isCancelled();
    }
}
