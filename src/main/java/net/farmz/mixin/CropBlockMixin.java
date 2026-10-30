package net.farmz.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.FarmMain;
import net.farmz.block.SprinklerBlock;
import net.farmz.compat.SeasonsCompat;
import net.farmz.data.CropGrowthLoader;
import net.farmz.init.BlockInit;
import net.farmz.init.ConfigInit;
import net.farmz.util.GoldenCropUtil;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
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
        if (getGrowthTicks(state) > 0) {
            scheduleGrowthTick(state, world, pos);
            info.cancel();
        }
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.scheduledTick(state, world, pos, random);

        if (getGrowthTicks(state) > 0 && world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED) > 0) {
            int age = this.getAge(state);
            if (age < this.getMaxAge() && world.getBaseLightLevel(pos, 0) >= 9 && SeasonsCompat.getGrowthMultiplier(world, pos, state) > 0.0F && SeasonsCompat.canGrow(world, pos, state)) {
                world.setBlockState(pos, getGrowthState(world, pos, state, age + 1, this.withAge(age + 1)), Block.NOTIFY_LISTENERS);
            }
            scheduleGrowthTick(world.getBlockState(pos), world, pos);
        }
    }

    @Override
    protected void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        if (world instanceof ServerWorld serverWorld) {
            scheduleGrowthTick(state, serverWorld, pos);
        }
    }

    @Unique
    private static void scheduleGrowthTick(BlockState state, ServerWorld world, BlockPos pos) {
        int growthTicks = getGrowthTicks(state);
        if (growthTicks > 0 && state.getBlock() instanceof CropBlock cropBlock && !cropBlock.isMature(state) && !world.getBlockTickScheduler().isQueued(pos, cropBlock)
                && world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED) > 0) {
            int stageTicks = Math.max(1, growthTicks / Math.max(1, cropBlock.getMaxAge()));
            float multiplier = SeasonsCompat.getGrowthMultiplier(world, pos, state);
            if (multiplier > 0.0F) {
                stageTicks = Math.max(1, Math.round(stageTicks / multiplier));
            }
            world.scheduleBlockTick(pos, cropBlock, stageTicks);
        }
    }

    @Unique
    private static int getGrowthTicks(BlockState state) {
        if (!ConfigInit.CONFIG.timedCropGrowth) {
            return 0;
        }
        return CropGrowthLoader.CROP_GROWTH_TICKS.getOrDefault(state.getBlock(), 0);
    }
}
