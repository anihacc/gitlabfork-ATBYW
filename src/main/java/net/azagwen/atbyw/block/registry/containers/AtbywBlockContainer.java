package net.azagwen.atbyw.block.registry.containers;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;

/**
 * Safe-typing class for storing a list of {@link Block}s
 */
public class AtbywBlockContainer extends AtbywContainer<Block> {

    public AtbywBlockContainer(String name) {
        super(name, "block");
    }

    public void add(BlockItem block) {
        this.add(block.getBlock());
    }

    public AtbywItemContainer getAsItemContainer() {
        var container = new AtbywItemContainer(this.name);
        for (var block : this) {
            container.add(block);
        }
        return container;
    }
}
