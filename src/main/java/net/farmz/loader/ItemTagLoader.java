package net.farmz.loader;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.farmz.init.ItemInit;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ItemTagLoader extends FabricTagProvider.ItemTagProvider {

    public ItemTagLoader(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.HEAD_ARMOR).add(ItemInit.FARMERS_HAT);

        getOrCreateTagBuilder(ItemTags.FOX_FOOD).add(ItemInit.GLISTERING_SWEET_BERRIES);
        getOrCreateTagBuilder(ItemTags.FOX_FOOD).add(ItemInit.GLISTERING_GLOW_BERRIES);

        getOrCreateTagBuilder(ItemTags.PIG_FOOD).add(ItemInit.GOLDEN_POTATO);
        getOrCreateTagBuilder(ItemTags.PIG_FOOD).add(ItemInit.GOLDEN_BEETROOT);
        getOrCreateTagBuilder(ItemTags.PIG_FOOD).add(Items.GOLDEN_CARROT);
    }
}
