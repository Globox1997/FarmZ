package net.farmz.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "farmz")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class FarmConfig implements ConfigData {

    @Comment("Farmland water source range check")
    public int waterSourceRange = 0;

    @Comment("Crops listed in crop_growth datapack files grow on a fixed timer instead of random ticks")
    public boolean timedCropGrowth = false;
    @Comment("Fixed timed crops catch up on growth stages missed while their chunk was unloaded")
    public boolean timedCropGrowthCatchUp = true;
    @Comment("Growth speed multiplier for fixed timed crops on dry farmland, 0 stops the growth")
    public float dryFarmlandGrowthMultiplier = 0.5F;

    @Comment("Xp drop on breaking the plant")
    public int goldenPlantMinXp = 0;
    public int goldenPlantMaxXp = 2;
    @Comment("LevelZ compatibility")
    public float goldenPlantXPMultiplier = 1.0F;

}