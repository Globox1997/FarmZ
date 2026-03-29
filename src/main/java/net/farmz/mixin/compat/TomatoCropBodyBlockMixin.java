package net.farmz.mixin.compat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.block.SprinklerBlock;
import net.farmz.compat.GoldenTomatoCropBodyBlock;
import net.farmz.init.CompatInit;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.satisfy.farm_and_charm.core.block.crops.TomatoCropBodyBlock;
import net.satisfy.farm_and_charm.core.block.crops.TomatoCropHeadBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TomatoCropBodyBlock.class)
public class TomatoCropBodyBlockMixin {

    @WrapOperation(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z", ordinal = 0))
    private boolean growBodyGoldenBodyMixin(ServerWorld level, BlockPos headPos, BlockState bodyAtHead, int flags, Operation<Boolean> original, ServerWorld levelOuter, Random random, BlockPos pos, BlockState state) {
        if (state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
            boolean supported = bodyAtHead.contains(TomatoCropHeadBlock.SUPPORTED) && bodyAtHead.get(TomatoCropHeadBlock.SUPPORTED);
            BlockState goldenBody = CompatInit.GOLDEN_TOMATO_CROP_BODY.getDefaultState().with(GoldenTomatoCropBodyBlock.AGE, 0).with(TomatoCropBodyBlock.SUPPORTED, supported);
            return original.call(level, headPos, goldenBody, flags);
        }
        return original.call(level, headPos, bodyAtHead, flags);
    }
}
