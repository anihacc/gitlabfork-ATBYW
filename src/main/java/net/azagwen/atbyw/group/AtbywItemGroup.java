package net.azagwen.atbyw.group;

import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.azagwen.atbyw.block.registry.DecorationBlockRegistry;
import net.azagwen.atbyw.block.registry.RedstoneBlockRegistry;
import net.azagwen.atbyw.datagen.arrp.DatagenTags;
import net.azagwen.atbyw.item.AtbywItems;
import net.azagwen.atbyw.main.Tags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.azagwen.atbyw.datagen.arrp.AtbywRRP.ATBYW_RESOURCE_PACK;
import static net.azagwen.atbyw.main.AtbywMain.*;

// This code was originally taken from https://github.com/Lemonszz/gubbins/blob/master/src/main/java/party/lemons/gubbins/mixin/client/CreativeInventoryScreenMixin.java,
// which is licensed under MIT.
// and edited to match my needs.
public class AtbywItemGroup extends TabbedItemGroup {

    public AtbywItemGroup(Identifier id) {
        super(id);
    }

    public static ItemGroupTab ATBYW_BLOCKS = new ItemGroupTab(new ItemStack(BuildingBlockRegistry.CYAN_CINDER_BLOCKS), "blocks", BLOCKS_TAB);
    public static ItemGroupTab ATBYW_DECO = new ItemGroupTab(new ItemStack(DecorationBlockRegistry.CYAN_CINDER_BLOCKS_WALL), "decoration", DECO_TAB);
    public static ItemGroupTab ATBYW_REDSTONE = new ItemGroupTab(new ItemStack(RedstoneBlockRegistry.REDSTONE_LANTERN), "redstone", REDSTONE_TAB);
    public static ItemGroupTab ATBYW_MISC = new ItemGroupTab(new ItemStack(AtbywItems.BAMBOO_STICK), "misc", MISC_TAB);

    @Override
    public void initTabs(List<ItemGroupTab> tabs) {
        tabs.add(ATBYW_BLOCKS);
        tabs.add(ATBYW_DECO);
        tabs.add(ATBYW_REDSTONE);
        tabs.add(ATBYW_MISC);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(BuildingBlockRegistry.TERRACOTTA_BRICKS);
    }
}