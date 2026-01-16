package net.farmz.loader;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.farmz.init.BlockInit;
import net.farmz.init.ItemInit;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class BlockTagLoader extends FabricTagProvider.BlockTagProvider {

    public BlockTagLoader(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
            getOrCreateTagBuilder(BlockTags.FLOWERS).add(BlockInit.GOLDEN_CHORUS_FLOWER);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GOLDEN_CHORUS_FLOWER);
            getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(BlockInit.GOLDEN_CHORUS_FLOWER);

            getOrCreateTagBuilder(BlockTags.CROPS).add(BlockInit.GLISTERING_SWEET_BERRY_BUSH);
            getOrCreateTagBuilder(BlockTags.FALL_DAMAGE_RESETTING).add(BlockInit.GLISTERING_SWEET_BERRY_BUSH);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GLISTERING_SWEET_BERRY_BUSH);
            getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(BlockInit.GLISTERING_SWEET_BERRY_BUSH);

            getOrCreateTagBuilder(BlockTags.CROPS).add(BlockInit.GOLDEN_POTATOES);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GOLDEN_POTATOES);
            getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(BlockInit.GOLDEN_POTATOES);

            getOrCreateTagBuilder(BlockTags.CROPS).add(BlockInit.GOLDEN_WHEAT);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GOLDEN_WHEAT);
            getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(BlockInit.GOLDEN_WHEAT);

            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GOLDEN_COCOA);
            getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(BlockInit.GOLDEN_COCOA);

            getOrCreateTagBuilder(BlockTags.CROPS).add(BlockInit.GOLDEN_CARROTS);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GOLDEN_CARROTS);
            getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(BlockInit.GOLDEN_CARROTS);

            getOrCreateTagBuilder(BlockTags.CROPS).add(BlockInit.GOLDEN_BEETROOTS);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GOLDEN_BEETROOTS);
            getOrCreateTagBuilder(BlockTags.MAINTAINS_FARMLAND).add(BlockInit.GOLDEN_BEETROOTS);

            getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(BlockInit.GLISTERING_CAVE_VINES);
            getOrCreateTagBuilder(BlockTags.BEE_GROWABLES).add(BlockInit.GLISTERING_CAVE_VINES_PLANT);
            getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(BlockInit.GLISTERING_CAVE_VINES);
            getOrCreateTagBuilder(BlockTags.CLIMBABLE).add(BlockInit.GLISTERING_CAVE_VINES_PLANT);
            getOrCreateTagBuilder(BlockTags.CAVE_VINES).add(BlockInit.GLISTERING_CAVE_VINES);
            getOrCreateTagBuilder(BlockTags.CAVE_VINES).add(BlockInit.GLISTERING_CAVE_VINES_PLANT);
            getOrCreateTagBuilder(BlockTags.MOSS_REPLACEABLE).add(BlockInit.GLISTERING_CAVE_VINES);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GLISTERING_CAVE_VINES);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.GLISTERING_CAVE_VINES_PLANT);
            getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(BlockInit.GLISTERING_CAVE_VINES);
            getOrCreateTagBuilder(BlockTags.SWORD_EFFICIENT).add(BlockInit.GLISTERING_CAVE_VINES_PLANT);

            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(BlockInit.SPRINKLER);
    }
}
