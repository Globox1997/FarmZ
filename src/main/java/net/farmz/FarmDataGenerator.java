package net.farmz;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.farmz.loader.BlockTagLoader;
import net.farmz.loader.ItemTagLoader;
import net.farmz.loader.LootTableLoader;
import net.farmz.loader.ModelLoader;

public class FarmDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModelLoader::new);
        pack.addProvider(LootTableLoader::new);
        pack.addProvider(BlockTagLoader::new);
        pack.addProvider(ItemTagLoader::new);
    }
}
