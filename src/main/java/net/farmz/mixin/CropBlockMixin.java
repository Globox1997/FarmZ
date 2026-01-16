package net.farmz.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.farmz.FarmMain;
import net.farmz.block.SprinklerBlock;
import net.farmz.util.GoldenCropUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.state.StateManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
        if (age == getMaxAge() && state.contains(SprinklerBlock.GOLDEN) && state.get(SprinklerBlock.GOLDEN)) {
            Block block = Registries.BLOCK.get(FarmMain.identifierOf("golden_" + Registries.BLOCK.getId(state.getBlock()).getPath()));
            if (!block.getDefaultState().isAir()) {
                return Registries.BLOCK.get(FarmMain.identifierOf("golden_" + Registries.BLOCK.getId(state.getBlock()).getPath())).getDefaultState();
            }
        }
        return original.call(instance, age).withIfExists(SprinklerBlock.GOLDEN, state.get(SprinklerBlock.GOLDEN));
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
}
