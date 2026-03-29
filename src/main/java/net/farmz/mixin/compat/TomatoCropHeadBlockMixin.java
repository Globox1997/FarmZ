package net.farmz.mixin.compat;

import net.farmz.block.SprinklerBlock;
import net.farmz.compat.GoldenTomatoCropBodyBlock;
import net.farmz.compat.GoldenTomatoCropHeadBlock;
import net.farmz.init.CompatInit;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.satisfy.farm_and_charm.core.block.crops.TomatoCropBodyBlock;
import net.satisfy.farm_and_charm.core.block.crops.TomatoCropHeadBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TomatoCropHeadBlock.class)
public class TomatoCropHeadBlockMixin {

    @Shadow
    @Mutable
    @Final
    public static IntProperty AGE;

    @Inject(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", ordinal = 0), cancellable = true)
    private void randomTickMixin(BlockState state, ServerWorld level, BlockPos pos, Random random, CallbackInfo info) {
        if (state.get(AGE) + 1 == this.getMaxAge() && state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
            boolean supported = state.get(TomatoCropHeadBlock.SUPPORTED);
            BlockState goldenBody = CompatInit.GOLDEN_TOMATO_CROP_BODY.getDefaultState().with(GoldenTomatoCropBodyBlock.AGE, 0).with(TomatoCropHeadBlock.SUPPORTED, supported);
            level.setBlockState(pos, goldenBody, 2);
            BlockPos up = pos.up();
            BlockState goldenHead = CompatInit.GOLDEN_TOMATO_CROP.getDefaultState().with(GoldenTomatoCropHeadBlock.AGE, 0).with(TomatoCropHeadBlock.SUPPORTED, supported);
            level.setBlockState(up, goldenHead);
            info.cancel();
        }
    }

    @Inject(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", ordinal = 1), cancellable = true)
    private void randomTickTwoMixin(BlockState state, ServerWorld level, BlockPos pos, Random random, CallbackInfo info) {
        if (state.get(AGE) + 1 == this.getMaxAge() && state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
            BlockState goldenHead = CompatInit.GOLDEN_TOMATO_CROP.getDefaultState();
            level.setBlockState(pos, goldenHead);
            info.cancel();
        }
    }

    @Inject(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", ordinal = 0), cancellable = true)
    private void growMixin(ServerWorld level, Random random, BlockPos pos, BlockState state, CallbackInfo info) {
        if (state.get(AGE) + 1 == this.getMaxAge() && state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
            boolean supported = state.get(TomatoCropHeadBlock.SUPPORTED);
            BlockState goldenBody = CompatInit.GOLDEN_TOMATO_CROP_BODY.getDefaultState().with(GoldenTomatoCropBodyBlock.AGE, 0).with(TomatoCropHeadBlock.SUPPORTED, supported);
            level.setBlockState(pos, goldenBody, 2);
            BlockPos up = pos.up();
            BlockState goldenHead = CompatInit.GOLDEN_TOMATO_CROP.getDefaultState().with(GoldenTomatoCropHeadBlock.AGE, 0).with(TomatoCropHeadBlock.SUPPORTED, supported);
            level.setBlockState(up, goldenHead);
            info.cancel();
        }
    }

    @Inject(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Z", ordinal = 1), cancellable = true)
    private void growTwoMixin(ServerWorld level, Random random, BlockPos pos, BlockState state, CallbackInfo info) {
        if (state.get(AGE) + 1 == this.getMaxAge() && state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
            BlockState goldenHead = CompatInit.GOLDEN_TOMATO_CROP.getDefaultState();
            level.setBlockState(pos, goldenHead);
            info.cancel();
        }
    }

    @Shadow
    protected int getMaxAge() {
        return 4;
    }
}
