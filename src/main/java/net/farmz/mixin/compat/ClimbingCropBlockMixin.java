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
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.satisfy.farm_and_charm.core.block.crops.ClimbingCropBlock;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClimbingCropBlock.class)
public abstract class ClimbingCropBlockMixin extends Block {

    @Shadow
    @Mutable
    @Final
    public static BooleanProperty SUPPORTED;

    public ClimbingCropBlockMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    protected abstract IntProperty getAgeProperty();

    @Shadow
    protected abstract int getMaxAge();

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initMixin(Settings settings, VoxelShape shape, CallbackInfo info) {
        this.setDefaultState(this.getDefaultState().withIfExists(SprinklerBlock.GOLDEN, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendPropertiesMixin(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(SprinklerBlock.GOLDEN);
    }

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean randomTickMixin(ServerWorld instance, BlockPos pos, BlockState state, int flags, Operation<Boolean> original) {
        if (state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN) && state.get(getAgeProperty()) == getMaxAge()) {
            String path = Registries.BLOCK.getId(state.getBlock()).getPath();
            Block block = Registries.BLOCK.get(FarmMain.identifierOf("golden_" + path));
            if (block.getDefaultState().isAir()) {
                block = Registries.BLOCK.get(FarmMain.identifierOf("glistering_" + path));
            }
            if (!block.getDefaultState().isAir()) {
                return original.call(instance, pos, block.getDefaultState().withIfExists(SUPPORTED, state.get(SUPPORTED)), flags);
            }
        }
        return original.call(instance, pos, state, flags);
    }
}
