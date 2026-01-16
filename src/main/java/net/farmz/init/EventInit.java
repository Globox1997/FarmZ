package net.farmz.init;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.PotatoesBlock;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;

public class EventInit {

    public static void init() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if(!world.isClient() && player.getStackInHand(hand).getItem() instanceof AliasedBlockItem){
//                Items
//                System.out.println("TEST");
//                PotatoesBlock
            }
            return ActionResult.PASS;
        });
    }
}
