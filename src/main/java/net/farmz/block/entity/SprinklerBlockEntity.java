package net.farmz.block.entity;

import net.farmz.block.SprinklerBlock;
import net.farmz.init.BlockInit;
import net.farmz.init.RenderInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SprinklerBlockEntity extends BlockEntity {

    private double rotation;
    private double lastRotation;

    public SprinklerBlockEntity(BlockPos pos, BlockState state) {
        super(BlockInit.SPRINKLER_ENTITY, pos, state);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.rotation = nbt.getDouble("Rotation");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putDouble("Rotation", this.rotation);
    }

    public static void clientTick(World world, BlockPos pos, BlockState state, SprinklerBlockEntity blockEntity) {
        blockEntity.setLastRotation(blockEntity.getRotation());
        if (blockEntity.hasPowerAndWater(state)) {
            blockEntity.setRotation(blockEntity.getRotation() + 0.1D);
            for (int i = 0; i < 4; i++) {

                double angleRadians = blockEntity.getRotation() + i * 90 * 0.0174532F;

                // Calculate x and z coordinates
                double x = 0.5D * Math.cos(-angleRadians);
                double z = 0.5D * Math.sin(-angleRadians);

                double particleX = (double) pos.getX() + 0.5D + x;
                double particleZ = (double) pos.getZ() + 0.5D + z;

                double centerToParticleX = particleX - ((double) pos.getX() + 0.5D);
                double centerToParticleZ = particleZ - ((double) pos.getZ() + 0.5D);

                // Set the particle position
                double e = centerToParticleX;
                double f = centerToParticleZ;

                world.addParticle(RenderInit.SPRINKLER_PARTICLE, (double) pos.getX() + 0.5D + x, (double) pos.getY() + 0.9f, (double) pos.getZ() + 0.5D + z, e * 30, 60.0D, f * 30);
            }
        }
    }

    public static void serverTick(World world, BlockPos pos, BlockState state, SprinklerBlockEntity blockEntity) {
        if (world.getTime() % 50 == 0) {
            sprinkle(world, pos, blockEntity.hasPowerAndWater(state));
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        this.sendUpdate();
    }

    private void sendUpdate() {
        if (this.getWorld() != null) {
            BlockState state = this.getWorld().getBlockState(this.getPos());
            this.getWorld().updateListeners(this.getPos(), state, state, 3);
        }
    }

    public boolean hasPowerAndWater(BlockState state) {
        return state.get(SprinklerBlock.WATER) && state.get(SprinklerBlock.POWERED);
    }

    public double getRotation() {
        return this.rotation;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getLastRotation() {
        return this.lastRotation;
    }

    public void setLastRotation(double lastRotation) {
        this.lastRotation = lastRotation;
    }

    public static void sprinkle(World world, BlockPos pos, boolean sprinkle) {
        for (int i = -4; i < 5; i++) {
            for (int u = -4; u < 5; u++) {
                if (sprinkle && world.getRandom().nextFloat() < 0.3F) {
                    continue;
                }
                BlockPos checkPos = pos.add(u, -1, i);
                BlockState state = world.getBlockState(checkPos);
                if (state.getBlock() instanceof FarmlandBlock farmlandBlock) {
                    if (sprinkle) {
                        int moisture = world.getBlockState(checkPos).get(FarmlandBlock.MOISTURE);
                        if (moisture < 7 || !state.get(SprinklerBlock.SPRINKLED)) {
                            state = state.with(FarmlandBlock.MOISTURE, state.get(FarmlandBlock.MOISTURE) + 1);
                            world.setBlockState(checkPos, state.with(SprinklerBlock.SPRINKLED, sprinkle), Block.NOTIFY_LISTENERS);
                        }
                    } else {
                        world.setBlockState(checkPos, state.with(SprinklerBlock.SPRINKLED, sprinkle), Block.NOTIFY_LISTENERS);
                    }
                }
            }
        }
    }

}
