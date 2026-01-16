package net.farmz.block;

import net.farmz.init.BlockInit;
import net.farmz.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public class GoldenCaveVinesBlock extends Block {

    public static final IntProperty AGE = Properties.AGE_25;

    public GoldenCaveVinesBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(AGE, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Block.dropStack(world, pos, new ItemStack(ItemInit.GLISTERING_GLOW_BERRIES, 1));

        float f = MathHelper.nextBetween(world.getRandom(), 0.8F, 1.2F);
        world.playSound(null, pos, SoundEvents.BLOCK_CAVE_VINES_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, f);
        BlockState blockState = Registries.BLOCK.get(Identifier.ofVanilla(Registries.BLOCK.getId(state.getBlock()).getPath().replace("glistering_", ""))).getDefaultState().with(CaveVines.BERRIES, false).withIfExists(SprinklerBlock.GOLDEN, true);
        world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
        world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
        return ActionResult.success(world.isClient());
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        if (ItemInit.GOLDEN_CROP_ITEMS.containsKey(state.getBlock())) {
            return new ItemStack(ItemInit.GOLDEN_CROP_ITEMS.get(state.getBlock()));
        }
        return super.getPickStack(world, pos, state);
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        if (state.isOf(BlockInit.GLISTERING_CAVE_VINES) && state.get(AGE) < 25) {
            return true;
        }
        return false;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextDouble() < 0.11f) {
            BlockPos blockPos = pos.offset(Direction.DOWN);
            if (world.getBlockState(blockPos).isAir()) {
                int age = Math.min(state.get(AGE) + random.nextInt(2) + 1, 25);
                if (random.nextDouble() < 0.11f) {
                    world.setBlockState(blockPos, state.with(AGE, age));
                } else {
                    world.setBlockState(blockPos, Blocks.CAVE_VINES.getDefaultState().with(AGE, age).with(SprinklerBlock.GOLDEN, true));
                }
                world.setBlockState(pos, BlockInit.GLISTERING_CAVE_VINES_PLANT.getDefaultState().with(AGE, state.get(AGE)));
            }
        }
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.offset(Direction.UP);
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isOf(Blocks.CAVE_VINES) || blockState.isOf(Blocks.CAVE_VINES_PLANT) || blockState.isOf(BlockInit.GLISTERING_CAVE_VINES)
                || blockState.isOf(BlockInit.GLISTERING_CAVE_VINES_PLANT) || blockState.isSideSolidFullSquare(world, blockPos, Direction.DOWN);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !state.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }

//        AbstractPlantStemBlock abstractPlantStemBlock = this.getStem();
//        if (direction == Direction.DOWN && !neighborState.isOf(this) && !neighborState.isOf(abstractPlantStemBlock)) {
//            return this.copyState(state, abstractPlantStemBlock.getRandomGrowthState(world));
//        } else {
//            if (this.tickWater) {
//                world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
//            }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
//        }
    }

}
