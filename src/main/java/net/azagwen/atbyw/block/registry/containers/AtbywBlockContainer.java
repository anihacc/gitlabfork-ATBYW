package net.azagwen.atbyw.block.registry.containers;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

import java.util.ArrayList;

/**
 * Safe-typing class for storing a list of {@link Block}s
 */
public class AtbywBlockContainer extends ArrayList<Block> {
    
    public void add(BlockItem block) {
        this.add(block.getBlock());
    }

    public AtbywItemContainer getAsItemContainer() {
        var container = new AtbywItemContainer();
        for (var block : this) {
            container.add(block);
        }
        return container;
    }
}
