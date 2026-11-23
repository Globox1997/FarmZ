package net.farmz.mixin;

import net.farmz.util.CropGrowthUtil;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class AbstractBlockStateMixin {

    @Shadow
    protected abstract BlockState asBlockState();

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void randomTickMixin(ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        BlockState state = this.asBlockState();
        if (CropGrowthUtil.isGrowingTimedCrop(state)) {
            CropGrowthUtil.scheduleGrowthTick(world, pos, state);
            info.cancel();
        }
    }
}
