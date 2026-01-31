package net.farmz.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.block.SprinklerBlock;
import net.farmz.init.BlockInit;
import net.farmz.init.ConfigInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FarmlandBlock.class)
public abstract class FarmlandBlockMixin extends Block {

    public FarmlandBlockMixin(Settings settings) {
        super(settings);
    }

    @ModifyConstant(method = "isWaterNearby", constant = @Constant(intValue = -4))
    private static int isWaterNearbyMixin(int original) {
        if (ConfigInit.CONFIG.waterSourceRange != 0) {
            return ConfigInit.CONFIG.waterSourceRange;
        }
        return original;
    }

    @ModifyConstant(method = "isWaterNearby", constant = @Constant(intValue = 4))
    private static int isWaterNearbyMixinTwo(int original) {
        if (ConfigInit.CONFIG.waterSourceRange != 0) {
            return ConfigInit.CONFIG.waterSourceRange;
        }
        return original;
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo info) {
        if (!world.isClient() && state.get(SprinklerBlock.WATERED) > 0) {
            world.setBlockState(pos, state.with(SprinklerBlock.WATERED, state.get(SprinklerBlock.WATERED) - 1), Block.NOTIFY_LISTENERS);
            info.cancel();
        }
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void appendPropertiesMixin(StateManager.Builder<Block, BlockState> builder, CallbackInfo info) {
        builder.add(SprinklerBlock.SPRINKLED);
        builder.add(SprinklerBlock.WATERED);
        builder.add(SprinklerBlock.EGG);
    }

    @WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/FarmlandBlock;setDefaultState(Lnet/minecraft/block/BlockState;)V"))
    private void initMixin(FarmlandBlock instance, BlockState blockState, Operation<Void> original) {
        original.call(instance, blockState.with(SprinklerBlock.SPRINKLED, false).with(SprinklerBlock.WATERED, 0).with(SprinklerBlock.EGG, false));
    }

    @Inject(method = "isWaterNearby", at = @At("HEAD"), cancellable = true)
    private static void isWaterNearbyMixin(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (world.getBlockState(pos).get(SprinklerBlock.SPRINKLED) || world.getBlockState(pos).get(SprinklerBlock.WATERED) > 0) {
            info.setReturnValue(true);
        }
    }

    @Inject(method = "canPlaceAt", at = @At(value = "RETURN"), cancellable = true)
    private void canPlaceAtMixin(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
        if (!info.getReturnValue() && world.getBlockState(pos.up()).isOf(BlockInit.BEETHUUT_EGG)) {
            info.setReturnValue(true);
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getStackInHand(hand).isOf(Items.EGG) && !state.get(SprinklerBlock.EGG)) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(SprinklerBlock.EGG, true));
            } else {
                player.playSound(SoundEvents.ENTITY_CHICKEN_EGG);
            }
            return ItemActionResult.success(world.isClient());
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }
}
