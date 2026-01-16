package net.farmz.mixin.misc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.init.BlockInit;
import net.minecraft.block.AbstractPlantPartBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractPlantPartBlock.class)
public class AbstractPlantPartBlockMixin {

    @WrapOperation(method = "canPlaceAt", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1))
    private boolean canPlaceAtMixin(BlockState instance, Block block, Operation<Boolean> original) {
        if (instance.isOf(BlockInit.GLISTERING_CAVE_VINES) || instance.isOf(BlockInit.GLISTERING_CAVE_VINES_PLANT)) {
            if (block.getDefaultState().isOf(Blocks.CAVE_VINES_PLANT) || block.getDefaultState().isOf(Blocks.CAVE_VINES)) {
                return true;
            }
        }
        return original.call(instance, block);
    }
}
