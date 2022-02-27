package net.azagwen.atbyw.block.registry.containers;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;

/**
 * Safe-typing class for storing a list of {@link Item}s
 */
public class AtbywItemContainer extends AtbywContainer<Item> {

    public AtbywItemContainer(String name) {
        super(name, "item");
    }

    public void add(ItemConvertible itemConvertible) {
        if (itemConvertible.asItem() != null) {
            this.add(itemConvertible.asItem());
        }
    }

    public AtbywBlockContainer getAsBlockContainer() {
        var container = new AtbywBlockContainer(this.name);
        for (var item : this) {
            if (item instanceof BlockItem blockItem){
                container.add(blockItem);
            }
        }
        return container;
    }
}
