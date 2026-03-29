package net.farmz.mixin.compat;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.FarmMain;
import net.farmz.block.SprinklerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.satisfy.farm_and_charm.core.block.crops.ClimbingCropBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClimbingCropBlock.class)
public abstract class ClimbingCropBlockMixin {

    @Shadow
    @Mutable
    @Final
    public static BooleanProperty SUPPORTED;

    @Shadow
    protected abstract int getMaxAge();

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendPropertiesMixin(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(SprinklerBlock.GOLDEN);
    }

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean randomTickMixin(ServerWorld instance, BlockPos pos, BlockState state, int age, Operation<Boolean> original) {
        boolean supported = state.get(SUPPORTED);
        if (age == getMaxAge()) {
            if (state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
                Block block = Registries.BLOCK.get(FarmMain.identifierOf("golden_" + Registries.BLOCK.getId(state.getBlock()).getPath()));
                if (!block.getDefaultState().isAir()) {
                    instance.setBlockState(pos, Registries.BLOCK.get(FarmMain.identifierOf("golden_" + Registries.BLOCK.getId(state.getBlock()).getPath())).getDefaultState().with(SUPPORTED, supported));
                }
                block = Registries.BLOCK.get(FarmMain.identifierOf("glistering_" + Registries.BLOCK.getId(state.getBlock()).getPath()));
                if (!block.getDefaultState().isAir()) {
                    instance.setBlockState(pos, Registries.BLOCK.get(FarmMain.identifierOf("glistering_" + Registries.BLOCK.getId(state.getBlock()).getPath())).getDefaultState().with(SUPPORTED, supported));
                }
            }
        }
        if (state.contains(SprinklerBlock.GOLDEN)) {
            return instance.setBlockState(pos, state.withIfExists(SprinklerBlock.GOLDEN, state.get(SprinklerBlock.GOLDEN)).with(SUPPORTED, supported));
        }
        return original.call(instance, pos, state, age);
    }
}
