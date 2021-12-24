package net.azagwen.atbyw.group;

import net.azagwen.atbyw.main.AtbywMain;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

// This code was originally taken from https://github.com/Lemonszz/gubbins/blob/master/src/main/java/party/lemons/gubbins/itemgroup/ItemTab.java,
// which is licensed under MIT.
// and edited to match my needs & updated to 1.17.
public record ItemGroupTab(ItemStack icon, String name, List<Item> itemList) {

    public ItemStack getIcon() {
        return icon;
    }

    public boolean matches(Item item) {
        return itemList == null || itemList.contains(item);
    }

    public boolean matches(ItemStack stack) {
        return matches(stack.getItem());
    }

    public List<Item> getItems() {
        return itemList;
    }

    public String getTranslationKey() {
        return "itemGroup.subTab." + AtbywMain.ATBYW + "." + name;
    }
}