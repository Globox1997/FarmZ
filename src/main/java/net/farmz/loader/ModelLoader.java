package net.farmz.loader;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.farmz.init.BlockInit;
import net.farmz.init.ItemInit;
import net.minecraft.data.client.*;
import net.minecraft.state.property.Properties;

public class ModelLoader extends FabricModelProvider {

    public ModelLoader(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
//        blockStateModelGenerator.registerCrop(BlockInit.GOLDEN_POTATOES, Properties.AGE_7, 0, 0, 1, 1, 2, 2, 2, 3);
//        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(BlockInit.GLISTERING_SWEET_BERRY_BUSH)
//                .coordinate(BlockStateVariantMap.create(Properties.AGE_3).register(stage -> BlockStateVariant.create()
//                        .put(VariantSettings.MODEL, blockStateModelGenerator.createSubModel(BlockInit.GLISTERING_SWEET_BERRY_BUSH, "_stage" + stage, Models.CROSS, TextureMap::cross)))));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemInit.GLISTERING_SWEET_BERRIES, Models.GENERATED);
        itemModelGenerator.register(ItemInit.GLISTERING_GLOW_BERRIES, Models.GENERATED);
        itemModelGenerator.register(ItemInit.GOLDEN_BEETROOT, Models.GENERATED);
        itemModelGenerator.register(ItemInit.GOLDEN_CHORUS_FRUIT, Models.GENERATED);
        itemModelGenerator.register(ItemInit.POPPED_GOLDEN_CHORUS_FRUIT, Models.GENERATED);
        itemModelGenerator.register(ItemInit.BAKED_GOLDEN_POTATO, Models.GENERATED);
        itemModelGenerator.register(ItemInit.GOLDEN_POTATO, Models.GENERATED);
        itemModelGenerator.register(ItemInit.GOLDEN_BREAD, Models.GENERATED);
        itemModelGenerator.register(ItemInit.GOLDEN_COOKIE, Models.GENERATED);
        itemModelGenerator.register(ItemInit.GOLDEN_COCOA_BEANS, Models.GENERATED);
        itemModelGenerator.register(ItemInit.GOLDEN_WHEAT, Models.GENERATED);

        itemModelGenerator.register(ItemInit.COPPER_WATERING_CAN, Models.HANDHELD);
        itemModelGenerator.register(ItemInit.GOLDEN_WATERING_CAN, Models.HANDHELD);
        itemModelGenerator.register(ItemInit.IRON_WATERING_CAN, Models.HANDHELD);
        itemModelGenerator.register(ItemInit.DIAMOND_WATERING_CAN, Models.HANDHELD);
        itemModelGenerator.register(ItemInit.NETHERITE_WATERING_CAN, Models.HANDHELD);
    }

}

