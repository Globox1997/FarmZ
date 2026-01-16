package net.farmz.init;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.farmz.FarmMain;
import net.farmz.item.FarmersHatItem;
import net.farmz.item.WateringCan;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Map;

public class ItemInit {

    // Item Group
    public static final RegistryKey<ItemGroup> FARMZ_ITEM_GROUP = RegistryKey.of(RegistryKeys.ITEM_GROUP, FarmMain.identifierOf("item_group"));

    public static final FoodComponent GLISTERING_SWEET_BERRIES_FOOD = new FoodComponent.Builder().nutrition(4).saturationModifier(0.2F).build();
    public static final FoodComponent GLISTERING_GLOW_BERRIES_FOOD = new FoodComponent.Builder().nutrition(4).saturationModifier(0.2F).build();
    public static final FoodComponent GOLDEN_POTATO_FOOD = new FoodComponent.Builder().nutrition(2).saturationModifier(0.6F).build();
    public static final FoodComponent BAKED_GOLDEN_POTATO_FOOD = new FoodComponent.Builder().nutrition(10).saturationModifier(1.2F).build();
    public static final FoodComponent GOLDEN_BEETROOT_FOOD = new FoodComponent.Builder().nutrition(2).saturationModifier(1.2F).build();
    public static final FoodComponent GOLDEN_CHORUS_FRUIT_FOOD = new FoodComponent.Builder().nutrition(8).saturationModifier(0.6F).alwaysEdible().build();
    public static final FoodComponent GOLDEN_BREAD_FOOD = new FoodComponent.Builder().nutrition(10).saturationModifier(1.2F).alwaysEdible().build();
    public static final FoodComponent GOLDEN_COOKIE_FOOD = new FoodComponent.Builder().nutrition(4).saturationModifier(0.2F).alwaysEdible().build();

    public static final Item FARMERS_HAT = register("farmers_hat", new FarmersHatItem(new Item.Settings().maxCount(1)));

    public static final Item GLISTERING_GLOW_BERRIES = register("glistering_glow_berries", new AliasedBlockItem(Blocks.CAVE_VINES, new Item.Settings().food(GLISTERING_GLOW_BERRIES_FOOD)));
    public static final Item BAKED_GOLDEN_POTATO = register("baked_golden_potato", new Item(new Item.Settings().food(BAKED_GOLDEN_POTATO_FOOD)));

//    public static final Item GLISTERING_SWEET_BERRIES = register("glistering_sweet_berries", new AliasedBlockItem(BlockInit.GLISTERING_SWEET_BERRY_BUSH, new Item.Settings().food(GLISTERING_SWEET_BERRIES_FOOD)));
//    public static final Item GOLDEN_POTATO = register("golden_potato", new AliasedBlockItem(BlockInit.GOLDEN_POTATOES, new Item.Settings().food(GOLDEN_POTATO_FOOD)));
//    public static final Item GOLDEN_BEETROOT = register("golden_beetroot", new AliasedBlockItem(BlockInit.GOLDEN_BEETROOTS, new Item.Settings().food(GOLDEN_BEETROOT_FOOD)));

    public static final Item GOLDEN_POTATO = register("golden_potato", new Item(new Item.Settings().food(GOLDEN_POTATO_FOOD)));
    public static final Item GOLDEN_BEETROOT = register("golden_beetroot", new Item(new Item.Settings().food(GOLDEN_BEETROOT_FOOD)));
    public static final Item GLISTERING_SWEET_BERRIES = register("glistering_sweet_berries", new Item(new Item.Settings().food(GLISTERING_SWEET_BERRIES_FOOD)));

    public static final Item GOLDEN_CHORUS_FRUIT = register("golden_chorus_fruit", new ChorusFruitItem(new Item.Settings().food(GOLDEN_CHORUS_FRUIT_FOOD)));
    public static final Item POPPED_GOLDEN_CHORUS_FRUIT = register("popped_golden_chorus_fruit", new Item(new Item.Settings()));

    public static final Item GOLDEN_BREAD = register("golden_bread", new Item(new Item.Settings().food(GOLDEN_BREAD_FOOD)));
    public static final Item GOLDEN_COOKIE = register("golden_cookie", new Item(new Item.Settings().food(GOLDEN_COOKIE_FOOD)));
    public static final Item GOLDEN_COCOA_BEANS = register("golden_cocoa_beans", new Item(new Item.Settings()));
    public static final Item GOLDEN_WHEAT = register("golden_wheat", new Item(new Item.Settings()));

    public static final Item COPPER_WATERING_CAN = register("copper_watering_can", new WateringCan(new Item.Settings().maxDamage(3), 0));
    public static final Item GOLDEN_WATERING_CAN = register("golden_watering_can", new WateringCan(new Item.Settings().maxDamage(5), 1));
    public static final Item IRON_WATERING_CAN = register("iron_watering_can", new WateringCan(new Item.Settings().maxDamage(8), 2));
    public static final Item DIAMOND_WATERING_CAN = register("diamond_watering_can", new WateringCan(new Item.Settings().maxDamage(12), 3));
    public static final Item NETHERITE_WATERING_CAN = register("netherite_watering_can", new WateringCan(new Item.Settings().maxDamage(15), 4));

    public static final Map<Block, Item> GOLDEN_CROP_ITEMS = Map.of(BlockInit.GOLDEN_POTATOES, ItemInit.GOLDEN_POTATO, BlockInit.GOLDEN_BEETROOTS, ItemInit.GOLDEN_BEETROOT,
            BlockInit.GOLDEN_CARROTS, Items.GOLDEN_CARROT, BlockInit.GLISTERING_SWEET_BERRY_BUSH, ItemInit.GLISTERING_SWEET_BERRIES, BlockInit.GLISTERING_CAVE_VINES, ItemInit.GLISTERING_GLOW_BERRIES,
            BlockInit.GLISTERING_CAVE_VINES_PLANT, ItemInit.GLISTERING_GLOW_BERRIES, BlockInit.GOLDEN_CHORUS_FLOWER, ItemInit.GOLDEN_CHORUS_FRUIT, BlockInit.GOLDEN_WHEAT, ItemInit.GOLDEN_WHEAT,
            BlockInit.GOLDEN_COCOA,ItemInit.GOLDEN_COCOA_BEANS);

    private static Item register(String id, Item item) {
        return register(FarmMain.identifierOf(id), item);
    }

    private static Item register(Identifier id, Item item) {
        ItemGroupEvents.modifyEntriesEvent(FARMZ_ITEM_GROUP).register(entries -> entries.add(item));
        return Registry.register(Registries.ITEM, id, item);
    }

    public static void init() {
        Registry.register(Registries.ITEM_GROUP, FARMZ_ITEM_GROUP,
                FabricItemGroup.builder().icon(() -> new ItemStack(ItemInit.GOLDEN_BEETROOT)).displayName(Text.translatable("item.farmz.item_group")).build());
    }

}
