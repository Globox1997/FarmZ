package net.farmz.init;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.farmz.config.FarmConfig;

public class ConfigInit {

    public static FarmConfig CONFIG = new FarmConfig();

    public static void init() {
        AutoConfig.register(FarmConfig.class, JanksonConfigSerializer::new);
        CONFIG = AutoConfig.getConfigHolder(FarmConfig.class).getConfig();
    }

}