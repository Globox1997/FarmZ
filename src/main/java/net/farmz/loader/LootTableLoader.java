package net.farmz.loader;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.farmz.init.BlockInit;
import net.farmz.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PotatoesBlock;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class LootTableLoader extends FabricBlockLootTableProvider {

    public LootTableLoader(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
//        this.addDrop(
//                BlockInit.GLISTERING_SWEET_BERRY_BUSH,
//                block -> this.applyExplosionDecay(
//                        block,
//                        LootTable.builder().pool(LootPool.builder()
//                                                .conditionally(
//                                                        BlockStatePropertyLootCondition.builder(BlockInit.GLISTERING_SWEET_BERRY_BUSH).properties(StatePredicate.Builder.create().exactMatch(SweetBerryBushBlock.AGE, 3)))
//                                                .with(ItemEntry.builder(ItemInit.GLISTERING_SWEET_BERRIES))
//                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0F, 3.0F)))
//                                                .apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE)))).pool(LootPool.builder()
//                                                .conditionally(
//                                                        BlockStatePropertyLootCondition.builder(BlockInit.GLISTERING_SWEET_BERRY_BUSH).properties(StatePredicate.Builder.create().exactMatch(SweetBerryBushBlock.AGE, 2)))
//                                                .with(ItemEntry.builder(ItemInit.GLISTERING_SWEET_BERRIES))
//                                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0F, 2.0F)))
//                                                .apply(ApplyBonusLootFunction.uniformBonusCount(impl.getOrThrow(Enchantments.FORTUNE))))));
//
//        LootCondition.Builder builder = BlockStatePropertyLootCondition.builder(BlockInit.GOLDEN_POTATOES)
//                .properties(StatePredicate.Builder.create().exactMatch(PotatoesBlock.AGE, 7));
//        this.addDrop(
//                BlockInit.GOLDEN_POTATOES,
//                this.applyExplosionDecay(
//                        BlockInit.GOLDEN_POTATOES,
//                        LootTable.builder()
//                                .pool(LootPool.builder().with(ItemEntry.builder(ItemInit.GOLDEN_POTATO))).pool(
//                                        LootPool.builder()
//                                                .conditionally(builder)
//                                                .with(ItemEntry.builder(ItemInit.GOLDEN_POTATO).apply(ApplyBonusLootFunction.binomialWithBonusCount(impl.getOrThrow(Enchantments.FORTUNE), 0.5714286F, 3))))
//                                .pool(LootPool.builder().conditionally(builder).with(ItemEntry.builder(Items.POISONOUS_POTATO).conditionally(RandomChanceLootCondition.builder(0.02F))))));
    }

}
