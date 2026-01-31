package net.farmz.init;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.farmz.FarmMain;
import net.farmz.entity.BeethuutEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EntityInit {

    public static final EntityType<BeethuutEntity> BEETHUUT = register("beethuut", 10757932, 2846762,
            EntityType.Builder.create(BeethuutEntity::new, SpawnGroup.CREATURE).dimensions(0.7F, 1.0F).eyeHeight(0.6F).build());


    private static <T extends Entity> EntityType<T> register(String id, int primaryColor, int secondaryColor, EntityType<T> entityType) {
        if (primaryColor != 0) {
            Item item = Registry.register(Registries.ITEM, FarmMain.identifierOf("spawn_" + id),
                    new SpawnEggItem((EntityType<? extends MobEntity>) entityType, primaryColor, secondaryColor, new Item.Settings()));
            ItemGroupEvents.modifyEntriesEvent(ItemInit.FARMZ_ITEM_GROUP).register(entries -> entries.add(item));
        }
        return Registry.register(Registries.ENTITY_TYPE, FarmMain.identifierOf(id), entityType);
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(BEETHUUT, BeethuutEntity.createBeethuutAttributes());
    }
}
