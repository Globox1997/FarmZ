package net.farmz.block;

import com.mojang.serialization.MapCodec;
import net.farmz.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class GoldenCocoaBlock extends HorizontalFacingBlock {

    public static final MapCodec<GoldenCocoaBlock> CODEC = createCodec(GoldenCocoaBlock::new);

    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Block.createCuboidShape(7.0, 3.0, 4.0, 15.0, 12.0, 12.0),
            Block.createCuboidShape(1.0, 3.0, 4.0, 9.0, 12.0, 12.0),
            Block.createCuboidShape(4.0, 3.0, 1.0, 12.0, 12.0, 9.0),
            Block.createCuboidShape(4.0, 3.0, 7.0, 12.0, 12.0, 15.0)
    };

    public GoldenCocoaBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return CODEC;
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        if (ItemInit.GOLDEN_CROP_ITEMS.containsKey(state.getBlock())) {
            return new ItemStack(ItemInit.GOLDEN_CROP_ITEMS.get(state.getBlock()));
        }
        return super.getPickStack(world, pos, state);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.offset(state.get(FACING)));
        return blockState.isIn(BlockTags.JUNGLE_LOGS);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case EAST -> SHAPES[0];
            case WEST -> SHAPES[1];
            default -> SHAPES[2];
            case SOUTH -> SHAPES[3];
        };
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState();
        WorldView worldView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();

        for (Direction direction : ctx.getPlacementDirections()) {
            if (direction.getAxis().isHorizontal()) {
                blockState = blockState.with(FACING, direction);
                if (blockState.canPlaceAt(worldView, blockPos)) {
                    return blockState;
                }
            }
        }

        return null;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(
            BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == state.get(FACING) && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState()
                : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }
}
