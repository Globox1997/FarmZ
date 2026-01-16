package net.farmz.mixin.misc;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.FarmMain;
import net.farmz.block.GoldenCaveVinesBlock;
import net.farmz.block.SprinklerBlock;
import net.farmz.util.GoldenCropUtil;
import net.minecraft.block.AbstractPlantStemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CaveVinesHeadBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CaveVinesHeadBlock.class)
public abstract class CaveVinesHeadBlockMixin extends AbstractPlantStemBlock {

    public CaveVinesHeadBlockMixin(Settings settings, Direction growthDirection, VoxelShape outlineShape, boolean tickWater, double growthChance) {
        super(settings, growthDirection, outlineShape, tickWater, growthChance);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CaveVinesHeadBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"))
    private void initMixin(CaveVinesHeadBlock instance, BlockState blockState, Operation<Void> original) {
        original.call(instance, blockState.withIfExists(SprinklerBlock.GOLDEN, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendPropertiesMixin(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(SprinklerBlock.GOLDEN);
    }

    @Inject(method = "age", at = @At("HEAD"), cancellable = true)
    private void ageMixin(BlockState state, Random random, CallbackInfoReturnable<BlockState> info) {
        if (state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN) && random.nextFloat() < 0.11f) {
            int age = Math.min(state.get(AGE) + random.nextInt(2) + 1, 25);
            info.setReturnValue(Registries.BLOCK.get(FarmMain.identifierOf("glistering_" + Registries.BLOCK.getId(state.getBlock()).getPath())).getDefaultState().with(GoldenCaveVinesBlock.AGE,age));
        }
    }

    @WrapOperation(method = "age", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractPlantStemBlock;age(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/random/Random;)Lnet/minecraft/block/BlockState;"))
    private BlockState ageMixin(CaveVinesHeadBlock instance, BlockState state, Random random, Operation<BlockState> original) {
        return original.call(instance, state, random).withIfExists(SprinklerBlock.GOLDEN, state.get(SprinklerBlock.GOLDEN));
    }

    @WrapOperation(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private boolean randomTickMixin(ServerWorld instance, BlockPos blockPos, BlockState state, int age, Operation<Boolean> original) {
        if (state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
            return instance.setBlockState(blockPos, Registries.BLOCK.get(FarmMain.identifierOf("glistering_" + Registries.BLOCK.getId(state.getBlock()).getPath())).getDefaultState(), Block.NOTIFY_LISTENERS);
        }
        return original.call(instance, blockPos, state, age);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        GoldenCropUtil.setGoldenBlockState(world, pos, state, placer);
    }
}
