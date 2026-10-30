package net.farmz.item;

import net.farmz.block.SprinklerBlock;
import net.farmz.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class WateringCan extends Item {

    private final int size;

    public WateringCan(Settings settings, int size) {
        super(settings);
        this.size = size;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        if (player == null) {
            return ActionResult.PASS;
        }
        if (context.getStack().getDamage() > 0) {
            BlockHitResult blockHitResult = raycast(world, player, RaycastContext.FluidHandling.SOURCE_ONLY);
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!world.canPlayerModifyAt(player, blockPos)) {
                    return ActionResult.CONSUME;
                }

                if (world.getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    world.emitGameEvent(player, GameEvent.FLUID_PICKUP, blockPos);
                    if (!world.isClient()) {
                        context.getStack().setDamage(0);
                    }
                    return ActionResult.success(world.isClient());
                }
            }
        }
        if (context.getStack().getDamage() == context.getStack().getMaxDamage()) {
            return ActionResult.CONSUME;
        }

        if (!(world.getBlockState(context.getBlockPos()).getBlock() instanceof FarmlandBlock) && !(world.getBlockState(context.getBlockPos()).getBlock() instanceof CropBlock)) {
            return ActionResult.CONSUME;
        }
        BlockPos pos = context.getBlockPos();
        if (world.getBlockState(context.getBlockPos()).getBlock() instanceof CropBlock) {
            pos = pos.down();
        }

        world.playSound(player, pos, SoundInit.WATERING_CAN, SoundCategory.BLOCKS, 1.0f, 1.0f);
        if (!world.isClient()) {
            for (int i = -this.size; i <= this.size; i++) {
                for (int u = -this.size; u <= this.size; u++) {
                    for (int y = -1; y <= 1; y++) {
                        BlockPos checkPos = pos.add(u, y, i);
                        if (world.getBlockState(checkPos).getBlock() instanceof FarmlandBlock) {
                            world.setBlockState(checkPos, world.getBlockState(checkPos).with(SprinklerBlock.WATERED, 7).with(FarmlandBlock.MOISTURE, 7), Block.NOTIFY_LISTENERS);
                        }
                    }
                }
            }

            if (context.getStack().getDamage() == context.getStack().getMaxDamage() - 1) {
                context.getStack().setDamage(context.getStack().getMaxDamage());
            } else {
                context.getStack().damage(1, player, LivingEntity.getSlotForHand(context.getHand()));
            }
        } else {
            for (int i = -this.size; i <= this.size; i++) {
                for (int u = -this.size; u <= this.size; u++) {
                    for (int y = -1; y <= 1; y++) {
                        BlockPos particlePos = pos.add(u, y, i);
                        if (world.getBlockState(particlePos).getBlock() instanceof FarmlandBlock) {
                            for (int o = 0; o < 9; o++) {
                                world.addParticle(ParticleTypes.DRIPPING_WATER, particlePos.getX() + world.getRandom().nextFloat(), particlePos.getY() + 0.91, particlePos.getZ() + world.getRandom().nextFloat(), 0D, 0D, 0D);
                            }
                        }
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

}
