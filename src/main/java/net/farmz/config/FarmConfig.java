package net.farmz.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "farmz")
@Config.Gui.Background("minecraft:textures/block/stone.png")
public class FarmConfig implements ConfigData {

    @Comment("Farmland water source range check")
    public int waterSourceRange = 0;
    @ConfigEntry.BoundedDiscrete(min = 0, max = 100)
    @Comment("Chance to plant a golden crop")
    public int farmersHatGoldChance = 5;

}