package net.farmz.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.farmz.FarmMain;
import net.farmz.block.*;
import net.farmz.block.entity.SprinklerBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class BlockInit {

    public static final Block GLISTERING_SWEET_BERRY_BUSH = register("glistering_sweet_berry_bush", new GoldenCropBlock(Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0),
            AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);

    public static final Block GOLDEN_POTATOES = register("golden_potatoes", new GoldenCropBlock(null,
            AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);

    public static final Block GOLDEN_CARROTS = register("golden_carrots", new GoldenCropBlock(null,
            AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);

    public static final Block GOLDEN_BEETROOTS = register("golden_beetroots", new GoldenCropBlock(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);

    public static final Block GOLDEN_WHEAT = register("golden_wheat", new GoldenCropBlock(Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0),
            AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)), false, false);

    public static final Block GOLDEN_COCOA = register("golden_cocoa", new GoldenCocoaBlock(AbstractBlock.Settings.create().mapColor(MapColor.GOLD).strength(0.2F, 3.0F).sounds(BlockSoundGroup.WOOD)
            .nonOpaque().pistonBehavior(PistonBehavior.DESTROY)), false, false);

    public static final Block GLISTERING_CAVE_VINES = register(
            "glistering_cave_vines",
            new GoldenCaveVinesBlock(AbstractBlock.Settings.create().mapColor(MapColor.GOLD).ticksRandomly().noCollision().luminance(state -> 14).breakInstantly().sounds(BlockSoundGroup.CAVE_VINES).pistonBehavior(PistonBehavior.DESTROY)), false, false);

    public static final Block GLISTERING_CAVE_VINES_PLANT = register(
            "glistering_cave_vines_plant",
            new GoldenCaveVinesBlock(AbstractBlock.Settings.create().mapColor(MapColor.GOLD).noCollision().luminance(state -> 14).breakInstantly().sounds(BlockSoundGroup.CAVE_VINES).pistonBehavior(PistonBehavior.DESTROY)), false, false);

    public static final Block GOLDEN_CHORUS_FLOWER = register(
            "golden_chorus_flower",
            new ChorusFlowerBlock(Blocks.CHORUS_PLANT, AbstractBlock.Settings.create().mapColor(MapColor.GOLD).notSolid().strength(0.4F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(Blocks::never).pistonBehavior(PistonBehavior.DESTROY).solidBlock(Blocks::never)), false, false);


    public static final Block SPRINKLER = register("sprinkler", new SprinklerBlock(AbstractBlock.Settings.copy(Blocks.BARREL).nonOpaque()), true, true);

    public static BlockEntityType<SprinklerBlockEntity> SPRINKLER_ENTITY;

    private static Block register(String id, Block block, boolean createItem, boolean itemGroup) {
        return register(FarmMain.identifierOf(id), block, createItem, itemGroup);
    }

    private static Block register(Identifier id, Block block, boolean createItem, boolean itemGroup) {
        if (createItem) {
            Item item = Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Settings()));
            if (itemGroup) {
                ItemGroupEvents.modifyEntriesEvent(ItemInit.FARMZ_ITEM_GROUP).register(entries -> entries.add(item));
            }
        }
        return Registry.register(Registries.BLOCK, id, block);
    }

    public static void init() {
        SPRINKLER_ENTITY = Registry.register(Registries.BLOCK_ENTITY_TYPE, "farmz:sprinkler_entity", BlockEntityType.Builder.create(SprinklerBlockEntity::new, SPRINKLER).build(null));
    }

}
