package net.farmz.item;

import net.farmz.block.SprinklerBlock;
import net.farmz.init.SoundInit;
import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.world.event.GameEvent;

public class WateringCan extends Item {

    private final int size;

    public WateringCan(Settings settings, int size) {
        super(settings);
        this.size = size;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getStack().getDamage() > 0) {
            BlockHitResult blockHitResult = raycast(context.getWorld(), context.getPlayer(), RaycastContext.FluidHandling.SOURCE_ONLY);
            if (blockHitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = blockHitResult.getBlockPos();
                if (!context.getWorld().canPlayerModifyAt(context.getPlayer(), blockPos)) {
                    return ActionResult.CONSUME;
                }

                if (context.getWorld().getFluidState(blockPos).isIn(FluidTags.WATER)) {
                    context.getWorld().playSound(context.getPlayer(), context.getPlayer().getX(), context.getPlayer().getY(), context.getPlayer().getZ(), SoundEvents.ITEM_BUCKET_FILL, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                    context.getWorld().emitGameEvent(context.getPlayer(), GameEvent.FLUID_PICKUP, blockPos);
                    if (!context.getWorld().isClient()) {
                        context.getStack().setDamage(0);
                    }
                    return ActionResult.success(context.getWorld().isClient());
                }
            }
        }
        if (!(context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof FarmlandBlock)) {
            return ActionResult.CONSUME;
        }
        if (context.getStack().getDamage() == context.getStack().getMaxDamage()) {
            return ActionResult.CONSUME;
        }

        if (!context.getWorld().isClient()) {
            for (int i = -this.size; i <= this.size; i++) {
                for (int u = -this.size; u <= this.size; u++) {
                    BlockPos checkPos = context.getBlockPos().add(u, 0, i);
                    if (context.getWorld().getBlockState(checkPos).getBlock() instanceof FarmlandBlock) {
                        context.getWorld().setBlockState(checkPos, context.getWorld().getBlockState(checkPos).with(SprinklerBlock.WATERED, 7).with(FarmlandBlock.MOISTURE, 7), Block.NOTIFY_LISTENERS);
                    }
                }
            }
            if (context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof FarmlandBlock) {
                context.getWorld().setBlockState(context.getBlockPos(), context.getWorld().getBlockState(context.getBlockPos()).with(SprinklerBlock.WATERED, 7).with(FarmlandBlock.MOISTURE, 7), Block.NOTIFY_LISTENERS);
            }

            if (context.getStack().getDamage() == context.getStack().getMaxDamage() - 1) {
                context.getStack().setDamage(context.getStack().getMaxDamage());
            } else {
                context.getStack().damage(1, context.getPlayer(), LivingEntity.getSlotForHand(context.getHand()));
            }
        } else {
            context.getWorld().playSound(context.getPlayer(), context.getBlockPos(), SoundInit.WATERING_CAN, SoundCategory.BLOCKS, 1.0f, 1.0f);
            for (int i = -this.size; i <= this.size; i++) {
                for (int u = -this.size; u <= this.size; u++) {
                    for (int o = 0; o < 9; o++) {
                        BlockPos particlePos = context.getBlockPos().add(u, 0, i);
                        if (context.getWorld().getBlockState(particlePos).getBlock() instanceof FarmlandBlock) {
                            context.getWorld().addParticle(ParticleTypes.DRIPPING_WATER, particlePos.getX() + context.getWorld().getRandom().nextFloat(), context.getBlockPos().getY() + 0.91, particlePos.getZ() + context.getWorld().getRandom().nextFloat(), 0D, 0D, 0D);
                        }
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

}
