package net.farmz.mixin.misc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.init.BlockInit;
import net.minecraft.block.AbstractPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractPlantBlock.class)
public class AbstractPlantBlockMixin {

    @WrapOperation(method = "getStateForNeighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private boolean getStateForNeighborUpdateMixin(BlockState instance, Block block, Operation<Boolean> original) {
        if ((block == Blocks.CAVE_VINES || block == Blocks.CAVE_VINES_PLANT) && (instance.isOf(BlockInit.GLISTERING_CAVE_VINES) || instance.isOf(BlockInit.GLISTERING_CAVE_VINES_PLANT))) {
            return true;
        }
        return original.call(instance, block);
    }
}
