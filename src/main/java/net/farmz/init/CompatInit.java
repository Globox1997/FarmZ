package net.farmz.init;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.farmz.FarmMain;
import net.farmz.block.GoldenCropBlock;
import net.farmz.compat.GoldenTomatoCropBodyBlock;
import net.farmz.compat.GoldenTomatoCropHeadBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;

public class CompatInit {

    // Farm and Charm
    public static Item GLISTERING_STRAWBERRY;
    public static Item GOLDEN_BARLEY;
    public static Item GOLDEN_CORN;
    public static Item GOLDEN_LETTUCE;
    public static Item GOLDEN_OAT;
    public static Item GOLDEN_ONION;
    public static Item GOLDEN_TOMATO;

    public static Block GLISTERING_STRAWBERRY_CROP;
    public static Block GOLDEN_CORN_CROP;
    public static Block GOLDEN_BARLEY_CROP;
    public static Block GOLDEN_LETTUCE_CROP;
    public static Block GOLDEN_OAT_CROP;
    public static Block GOLDEN_ONION_CROP;
    public static Block GOLDEN_TOMATO_CROP;
    public static Block GOLDEN_TOMATO_CROP_BODY;

    public static void init() {
        if (FabricLoader.getInstance().isModLoaded("farm_and_charm") && FabricLoader.getInstance().isModLoaded("brewery")) {
            ResourceManagerHelper.registerBuiltinResourcePack(FarmMain.identifierOf("brewery_charm_farmz_compat"), FabricLoader.getInstance().getModContainer("farmz").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);
        }
        if (FabricLoader.getInstance().isModLoaded("farm_and_charm")) {
            ResourceManagerHelper.registerBuiltinResourcePack(FarmMain.identifierOf("farm_and_charm_farmz_compat"), FabricLoader.getInstance().getModContainer("farmz").orElseThrow(),
                    ResourcePackActivationType.DEFAULT_ENABLED);

            GLISTERING_STRAWBERRY = ItemInit.register("glistering_strawberry", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(2).saturationModifier(1.2F).build())));
            GOLDEN_CORN = ItemInit.register("golden_corn", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.2F).build())));
            GOLDEN_LETTUCE = ItemInit.register("golden_lettuce", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(6).saturationModifier(1.2F).build())));
            GOLDEN_ONION = ItemInit.register("golden_onion", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(4).saturationModifier(0.2F).build())));
            GOLDEN_TOMATO = ItemInit.register("golden_tomato", new Item(new Item.Settings().food(new FoodComponent.Builder().nutrition(8).saturationModifier(0.6F).build())));

            GOLDEN_BARLEY = ItemInit.register("golden_barley", new Item(new Item.Settings()));
            GOLDEN_OAT = ItemInit.register("golden_oat", new Item(new Item.Settings()));

            GLISTERING_STRAWBERRY_CROP = BlockInit.register("glistering_strawberry_crop", new GoldenCropBlock(null,
                    AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);
            GOLDEN_CORN_CROP = BlockInit.register("golden_corn_crop", new GoldenCropBlock(null,
                    AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);
            GOLDEN_BARLEY_CROP = BlockInit.register("golden_barley_crop", new GoldenCropBlock(null,
                    AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);
            GOLDEN_LETTUCE_CROP = BlockInit.register("golden_lettuce_crop", new GoldenCropBlock(null,
                    AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);
            GOLDEN_OAT_CROP = BlockInit.register("golden_oat_crop", new GoldenCropBlock(null,
                    AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);
            GOLDEN_ONION_CROP = BlockInit.register("golden_onion_crop", new GoldenCropBlock(null,
                    AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);

            GOLDEN_TOMATO_CROP = BlockInit.register("golden_tomato_crop", GoldenTomatoCropHeadBlock.create(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)), false, false);
            GOLDEN_TOMATO_CROP_BODY = BlockInit.register("golden_tomato_crop_body", GoldenTomatoCropBodyBlock.create(AbstractBlock.Settings.copy(Blocks.SWEET_BERRY_BUSH)), false, false);

            ItemInit.GOLDEN_CROP_ITEMS.put(GLISTERING_STRAWBERRY_CROP, GLISTERING_STRAWBERRY);
            ItemInit.GOLDEN_CROP_ITEMS.put(GOLDEN_CORN_CROP, GOLDEN_CORN);
            ItemInit.GOLDEN_CROP_ITEMS.put(GOLDEN_BARLEY_CROP, GOLDEN_BARLEY);
            ItemInit.GOLDEN_CROP_ITEMS.put(GOLDEN_LETTUCE_CROP, GOLDEN_LETTUCE);
            ItemInit.GOLDEN_CROP_ITEMS.put(GOLDEN_OAT_CROP, GOLDEN_OAT);
            ItemInit.GOLDEN_CROP_ITEMS.put(GOLDEN_ONION_CROP, GOLDEN_ONION);
            ItemInit.GOLDEN_CROP_ITEMS.put(GOLDEN_TOMATO_CROP, GOLDEN_TOMATO);
            ItemInit.GOLDEN_CROP_ITEMS.put(GOLDEN_TOMATO_CROP_BODY, GOLDEN_TOMATO);
        }
    }

    public static void clientInit() {
        if (FabricLoader.getInstance().isModLoaded("farm_and_charm")) {
            BlockRenderLayerMap.INSTANCE.putBlock(GLISTERING_STRAWBERRY_CROP, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(GOLDEN_CORN_CROP, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(GOLDEN_BARLEY_CROP, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(GOLDEN_LETTUCE_CROP, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(GOLDEN_OAT_CROP, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(GOLDEN_ONION_CROP, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(GOLDEN_TOMATO_CROP, RenderLayer.getCutout());
            BlockRenderLayerMap.INSTANCE.putBlock(GOLDEN_TOMATO_CROP_BODY, RenderLayer.getCutout());
        }
    }
}
