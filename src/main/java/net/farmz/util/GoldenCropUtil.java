package net.farmz.util;

import net.farmz.FarmMain;
import net.farmz.block.SprinklerBlock;
import net.farmz.init.ConfigInit;
import net.farmz.init.ItemInit;
import net.levelz.access.LevelManagerAccess;
import net.levelz.entity.LevelExperienceOrbEntity;
import net.levelz.level.LevelManager;
import net.levelz.level.SkillBonus;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class GoldenCropUtil {

    public static void setGoldenBlockState(World world, BlockPos pos, BlockState state, LivingEntity placer) {
        if (!world.isClient() && placer != null) {
            int goldenPlantChance = 0;
            if (FarmMain.isLevelzLoaded && placer instanceof PlayerEntity playerEntity) {
                goldenPlantChance += levelZGoldenPlantChanceBonus(playerEntity);
            }
            if (placer.getEquippedStack(EquipmentSlot.HEAD).isOf(ItemInit.FARMERS_HAT)) {
                goldenPlantChance += ConfigInit.CONFIG.farmersHatGoldChance;
            }
            if (goldenPlantChance > 0 && world.getRandom().nextInt(100) + 1 < goldenPlantChance) {
                world.setBlockState(pos, state.with(SprinklerBlock.GOLDEN, true));
            }
        }
    }

    public static void dropGoldenPlantExperience(BlockState state, ServerWorld world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        if (dropExperience) {
            int i = EnchantmentHelper.getBlockExperience(world, tool, UniformIntProvider.create(ConfigInit.CONFIG.goldenPlantMinXp, ConfigInit.CONFIG.goldenPlantMaxXp).get(world.getRandom()));
            if (i > 0 && world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS)) {
                ExperienceOrbEntity.spawn(world, Vec3d.ofCenter(pos), i);

                if (FarmMain.isLevelzLoaded) {
                    LevelExperienceOrbEntity.spawn(world, Vec3d.ofCenter(pos), (int) (i * ConfigInit.CONFIG.goldenPlantXPMultiplier));
                }
            }
        }
    }

    private static int levelZGoldenPlantChanceBonus(PlayerEntity playerEntity) {
        if (EnchantmentHelper.getEquipmentLevel(playerEntity.getRegistryManager().get(RegistryKeys.ENCHANTMENT).entryOf(Enchantments.SILK_TOUCH), playerEntity) <= 0) {
            if (LevelManager.BONUSES.containsKey("plantDropChance")) {
                LevelManager levelManager = ((LevelManagerAccess) playerEntity).getLevelManager();
                SkillBonus skillBonus = LevelManager.BONUSES.get("plantDropChance");
                int level = levelManager.getPlayerSkills().get(skillBonus.getId()).getLevel();
                return (int) (level * net.levelz.init.ConfigInit.CONFIG.plantDropChanceBonus * 100);
            }
        }
        return 0;
    }
}
