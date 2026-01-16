package net.farmz.block;

import com.mojang.serialization.MapCodec;
import net.farmz.init.BlockInit;
import net.farmz.init.ItemInit;
import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class GoldenCropBlock extends PlantBlock {

    public static final MapCodec<GoldenCropBlock> CODEC = createCodec(GoldenCropBlock::new);

    private static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 9.0, 16.0);

    @Nullable
    private VoxelShape shape;
    private Item item;

    public GoldenCropBlock(@Nullable VoxelShape shape, Settings settings) {
        this(settings);
        this.shape = shape;
    }

    public GoldenCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public MapCodec<GoldenCropBlock> getCodec() {
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
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (this.shape != null) {
            return this.shape;
        }
        return SHAPE;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.FARMLAND);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isClient() && entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            world.breakBlock(pos, true, entity);
        }

        if (state.isOf(BlockInit.GLISTERING_SWEET_BERRY_BUSH) && entity instanceof LivingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.slowMovement(state, new Vec3d(0.8F, 0.75, 0.8F));
            if (!world.isClient() && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                double d = Math.abs(entity.getX() - entity.lastRenderX);
                double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                if (d >= 0.003F || e >= 0.003F) {
                    entity.damage(world.getDamageSources().sweetBerryBush(), 1.0F);
                }
            }
        }
        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (state.isOf(BlockInit.GLISTERING_SWEET_BERRY_BUSH)) {
            int j = 1 + world.getRandom().nextInt(2);
            dropStack(world, pos, new ItemStack(ItemInit.GLISTERING_SWEET_BERRIES, j));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + world.getRandom().nextFloat() * 0.4F);
            BlockState blockState = state.with(SweetBerryBushBlock.AGE, 1);
            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));
            return ActionResult.success(world.isClient());
        } else {
            return super.onUse(state, world, pos, player, hit);
        }
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return hasEnoughLightAt(world, pos) && super.canPlaceAt(state, world, pos);
    }

    private static boolean hasEnoughLightAt(WorldView world, BlockPos pos) {
        return world.getBaseLightLevel(pos, 0) >= 8;
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return false;
    }

}
