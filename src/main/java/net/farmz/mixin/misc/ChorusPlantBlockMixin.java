package net.farmz.mixin.misc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChorusPlantBlock;
import net.minecraft.block.ConnectingBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ChorusPlantBlock.class)
public abstract class ChorusPlantBlockMixin extends ConnectingBlock {

    public ChorusPlantBlockMixin(float radius, Settings settings) {
        super(radius, settings);
    }

    @WrapOperation(method = "withConnectionProperties", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"))
    private static boolean withConnectionPropertiesMixin(BlockState instance, Block block, Operation<Boolean> original) {
        if (instance.isOf(BlockInit.GOLDEN_CHORUS_FLOWER)) {
            return true;
        }
        return original.call(instance, block);
    }

    @WrapOperation(method = "getStateForNeighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1))
    private boolean getStateForNeighborUpdateMixin(BlockState instance, Block block, Operation<Boolean> original) {
        if (instance.isOf(BlockInit.GOLDEN_CHORUS_FLOWER)) {
            return true;
        }
        return original.call(instance, block);
    }

}
