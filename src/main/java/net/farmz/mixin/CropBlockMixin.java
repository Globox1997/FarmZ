package net.farmz.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.FarmMain;
import net.farmz.block.SprinklerBlock;
import net.farmz.init.BlockInit;
import net.farmz.util.CropGrowthUtil;
import net.farmz.util.GoldenCropUtil;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CropBlock.class)
public abstract class CropBlockMixin extends PlantBlock {

    public CropBlockMixin(Settings settings) {
        super(settings);
    }

    @WrapOperation(method = "applyGrowth", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CropBlock;withAge(I)Lnet/minecraft/block/BlockState;"))
    private BlockState applyGrowthMixin(CropBlock instance, int age, Operation<BlockState> original, World world, BlockPos pos, BlockState state) {
        return getGrowthState(world, pos, state, age, original.call(instance, age));
    }

    @WrapOperation(method = "randomTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CropBlock;withAge(I)Lnet/minecraft/block/BlockState;"))
    private BlockState randomTickMixin(CropBlock instance, int age, Operation<BlockState> original, BlockState state, ServerWorld world, BlockPos pos, Random random) {
        return getGrowthState(world, pos, state, age, original.call(instance, age));
    }

    @Unique
    private BlockState getGrowthState(World world, BlockPos pos, BlockState state, int age, BlockState grownState) {
        if (age == getMaxAge()) {
            if (state.isOf(Blocks.BEETROOTS) && world.getBlockState(pos.down()).contains(SprinklerBlock.EGG) && world.getBlockState(pos.down()).get(SprinklerBlock.EGG)) {
                world.setBlockState(pos.down(), world.getBlockState(pos.down()).with(SprinklerBlock.EGG, false));
                return BlockInit.BEETHUUT_EGG.getDefaultState();
            } else if (state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
                Block block = Registries.BLOCK.get(FarmMain.identifierOf("golden_" + Registries.BLOCK.getId(state.getBlock()).getPath()));
                if (!block.getDefaultState().isAir()) {
                    return Registries.BLOCK.get(FarmMain.identifierOf("golden_" + Registries.BLOCK.getId(state.getBlock()).getPath())).getDefaultState();
                }
                block = Registries.BLOCK.get(FarmMain.identifierOf("glistering_" + Registries.BLOCK.getId(state.getBlock()).getPath()));
                if (!block.getDefaultState().isAir()) {
                    return Registries.BLOCK.get(FarmMain.identifierOf("glistering_" + Registries.BLOCK.getId(state.getBlock()).getPath())).getDefaultState();
                }
            }
        }
        if (state.contains(SprinklerBlock.GOLDEN)) {
            return grownState.withIfExists(SprinklerBlock.GOLDEN, state.get(SprinklerBlock.GOLDEN));
        }
        return grownState;
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/CropBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"))
    private void initMixin(CropBlock instance, BlockState blockState, Operation<Void> original) {
        original.call(instance, blockState.withIfExists(SprinklerBlock.GOLDEN, false));
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendPropertiesMixin(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(SprinklerBlock.GOLDEN);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        GoldenCropUtil.setGoldenBlockState(world, pos, state, placer);
    }

    @Shadow
    public int getMaxAge() {
        return 0;
    }

    @Shadow
    public abstract int getAge(BlockState state);

    @Shadow
    public abstract BlockState withAge(int age);

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void randomTickScheduleMixin(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        if (CropGrowthUtil.isGrowingTimedCrop(state)) {
            CropGrowthUtil.scheduleGrowthTick(world, pos, state);
            info.cancel();
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);

        if (CropGrowthUtil.getGrowthTicks(state) > 0) {
            int stages = CropGrowthUtil.getGrowthStages(world, pos, state, (CropBlock) (Object) this);
            if (stages > 0) {
                int age = Math.min(this.getAge(state) + stages, this.getMaxAge());
                world.setBlockState(pos, getGrowthState(world, pos, state, age, this.withAge(age)), Block.NOTIFY_LISTENERS);
            }
            CropGrowthUtil.scheduleGrowthTick(world, pos, world.getBlockState(pos));
        } else {
            CropGrowthUtil.removeLastGrowthTime(world, pos);
        }
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        if (world instanceof ServerWorld serverWorld) {
            CropGrowthUtil.scheduleGrowthTick(serverWorld, pos, state);
        }
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        super.onStateReplaced(state, world, pos, newState, moved);

        if (!state.isOf(newState.getBlock()) && world instanceof ServerWorld serverWorld) {
            CropGrowthUtil.removeLastGrowthTime(serverWorld, pos);
        }
    }
}
