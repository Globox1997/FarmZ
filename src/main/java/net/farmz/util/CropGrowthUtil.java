package net.farmz.util;

import net.farmz.compat.FarmAndCharmCompat;
import net.farmz.compat.SeasonsCompat;
import net.farmz.data.CropGrowthLoader;
import net.farmz.init.AttachmentInit;
import net.farmz.init.ConfigInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.chunk.WorldChunk;

import java.util.HashMap;
import java.util.Map;

public class CropGrowthUtil {

    public static int getGrowthTicks(BlockState state) {
        if (!ConfigInit.CONFIG.timedCropGrowth) {
            return 0;
        }
        return CropGrowthLoader.CROP_GROWTH_TICKS.getOrDefault(state.getBlock(), 0);
    }

    public static boolean isGrowingTimedCrop(BlockState state) {
        return state.getBlock() instanceof CropBlock cropBlock && !cropBlock.isMature(state) && getGrowthTicks(state) > 0;
    }

    public static int getGrowthStages(ServerWorld world, BlockPos pos, BlockState state, CropBlock cropBlock) {
        long time = world.getTime();
        Long lastGrowthTime = getLastGrowthTime(world, pos);
        float multiplier = getGrowthMultiplier(world, pos, state);
        if (lastGrowthTime == null || multiplier <= 0.0F || world.getGameRules().getInt(GameRules.RANDOM_TICK_SPEED) <= 0 || world.getBaseLightLevel(pos, 0) < 9) {
            setLastGrowthTime(world, pos, time);
            return 0;
        }
        int stageTicks = getStageTicks(state, cropBlock, multiplier);
        int stages = (int) Math.min((time - lastGrowthTime) / stageTicks, cropBlock.getMaxAge() - cropBlock.getAge(state));
        if (stages <= 0) {
            return 0;
        }
        if (!ConfigInit.CONFIG.timedCropGrowthCatchUp) {
            stages = 1;
        }

        int grownStages = 0;
        for (int i = 0; i < stages; i++) {
            if (SeasonsCompat.canGrow(world, pos, state)) {
                grownStages++;
            }
            if (!world.getBlockState(pos).isOf(state.getBlock())) {
                return 0;
            }
        }
        setLastGrowthTime(world, pos, ConfigInit.CONFIG.timedCropGrowthCatchUp ? lastGrowthTime + (long) stages * stageTicks : time);
        return grownStages;
    }

    public static void scheduleGrowthTick(ServerWorld world, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof CropBlock cropBlock && getGrowthTicks(state) > 0) {
            if (cropBlock.isMature(state)) {
                removeLastGrowthTime(world, pos);
            } else if (!world.getBlockTickScheduler().isQueued(pos, cropBlock)) {
                long time = world.getTime();
                Long lastGrowthTime = getLastGrowthTime(world, pos);
                if (lastGrowthTime == null) {
                    lastGrowthTime = time;
                    setLastGrowthTime(world, pos, time);
                }
                int stageTicks = getStageTicks(state, cropBlock, getGrowthMultiplier(world, pos, state));
                world.scheduleBlockTick(pos, cropBlock, (int) MathHelper.clamp(lastGrowthTime + stageTicks - time, 1L, stageTicks));
            }
        }
    }

    private static float getGrowthMultiplier(ServerWorld world, BlockPos pos, BlockState state) {
        float multiplier = SeasonsCompat.getGrowthMultiplier(world, pos, state) * FarmAndCharmCompat.getRainGrowthMultiplier(world, pos);
        BlockState soilState = world.getBlockState(pos.down());
        if (soilState.isOf(Blocks.FARMLAND) && soilState.get(FarmlandBlock.MOISTURE) <= 0) {
            multiplier *= ConfigInit.CONFIG.dryFarmlandGrowthMultiplier;
        }
        return multiplier;
    }

    private static int getStageTicks(BlockState state, CropBlock cropBlock, float multiplier) {
        int stageTicks = Math.max(1, getGrowthTicks(state) / Math.max(1, cropBlock.getMaxAge()));
        if (multiplier > 0.0F) {
            stageTicks = Math.max(1, Math.round(stageTicks / multiplier));
        }
        return stageTicks;
    }

    private static Long getLastGrowthTime(ServerWorld world, BlockPos pos) {
        Map<Long, Long> growthTimes = world.getWorldChunk(pos).getAttached(AttachmentInit.CROP_GROWTH_TIMES);
        return growthTimes != null ? growthTimes.get(pos.asLong()) : null;
    }

    private static void setLastGrowthTime(ServerWorld world, BlockPos pos, long time) {
        WorldChunk chunk = world.getWorldChunk(pos);
        chunk.getAttachedOrCreate(AttachmentInit.CROP_GROWTH_TIMES, HashMap::new).put(pos.asLong(), time);
        chunk.setNeedsSaving(true);
    }

    public static void removeLastGrowthTime(ServerWorld world, BlockPos pos) {
        WorldChunk chunk = world.getWorldChunk(pos);
        Map<Long, Long> growthTimes = chunk.getAttached(AttachmentInit.CROP_GROWTH_TIMES);
        if (growthTimes != null && growthTimes.remove(pos.asLong()) != null) {
            if (growthTimes.isEmpty()) {
                chunk.removeAttached(AttachmentInit.CROP_GROWTH_TIMES);
            }
            chunk.setNeedsSaving(true);
        }
    }
}
