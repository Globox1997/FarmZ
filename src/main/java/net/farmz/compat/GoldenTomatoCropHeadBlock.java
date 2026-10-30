package net.farmz.compat;

import net.farmz.init.CompatInit;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.satisfy.farm_and_charm.core.block.crops.ClimbingCropBlock;
import org.jetbrains.annotations.NotNull;

public class GoldenTomatoCropHeadBlock extends ClimbingCropBlock implements Fertilizable {

    private static final VoxelShape SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    public static final IntProperty AGE = Properties.AGE_1;

    public GoldenTomatoCropHeadBlock(AbstractBlock.Settings properties) {
        super(properties, SHAPE);
        this.setDefaultState(this.getDefaultState().with(SUPPORTED, false));
    }

    public static Block create(AbstractBlock.Settings settings) {
        return new GoldenTomatoCropHeadBlock(settings);
    }

    @Override
    protected IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected int getMaxAge() {
        return 1;
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.down());
        return !(below.getBlock() instanceof GoldenTomatoCropHeadBlock) && !(below.getBlock() instanceof GoldenTomatoCropBodyBlock) && super.canPlaceAt(state, level, pos);
    }

    @Override
    protected ItemConvertible getRipeItem() {
        return CompatInit.GOLDEN_TOMATO;
    }

    @Override
    protected ItemConvertible getRottenItem() {
        return CompatInit.GOLDEN_TOMATO;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
        super.scheduledTick(state, level, pos, random);
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return false;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random random) {
    }

    @Override
    public void grow(ServerWorld level, Random random, BlockPos pos, BlockState state) {
    }

    @Override
    public @NotNull BlockState getStateForNeighborUpdate(BlockState state, Direction dir, BlockState neighbor, WorldAccess level, BlockPos pos, BlockPos neighborPos) {
        return state;
    }

    @Override
    public boolean isFertilizable(WorldView level, BlockPos pos, BlockState state) {
        return false;
    }

    @Override
    public boolean canGrow(World level, Random random, BlockPos pos, BlockState state) {
        return false;
    }

}
