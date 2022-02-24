package net.azagwen.atbyw.block.registry;

import net.azagwen.atbyw.main.AtbywMain;
import net.azagwen.atbyw.util.naming.Orderable;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public record BlockRegistryUtils() {
    public static int BLOCK_NUMBER;

    /** Registers a block without a block item.
     *
     *  @param name     Name of the block (path)
     *  @param block    The Block field
     */
    public static void registerBlockOnly(String name, Block block) {
        Registry.register(Registry.BLOCK, AtbywMain.id(name), block);
        BLOCK_NUMBER++;
    }

    public static void registerBlockOnly(List<Block> requiredTool, String name, Block block) {
        registerBlockOnly(name, block);
        requiredTool.add(block);
    }

    /** Registers a block and its block item.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param group        the ItemGroup this block should be in.
     *  @param name         Name of the block (Identifier path).
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, @Nullable ItemGroup group, String name, Block block) {
        Item.Settings normalSettings = group != null ? new Item.Settings().group(group) : new Item.Settings();
        Item.Settings fireproofSettings = group != null ? new Item.Settings().group(group).fireproof() : new Item.Settings().fireproof();

        registerBlockOnly(name, block);
        Registry.register(Registry.ITEM, AtbywMain.id(name), new BlockItem(block, (fireproof ? fireproofSettings : normalSettings)));
        BLOCK_NUMBER++;
    }

    //No Item group param
    public static void registerBlock(boolean fireproof, String name, Block block) {
        registerBlock(fireproof, (ItemGroup) null, name, block);
    }

    //Required tool param
    public static void registerBlock(boolean fireproof, @Nullable ItemGroup group, String name, List<Block> requiredTool, Block block) {
        registerBlock(fireproof, group, name, block);
        requiredTool.add(block);
    }

    //Required tool param, No Item group param
    public static void registerBlock(boolean fireproof, String name, List<Block> requiredTool, Block block) {
        registerBlock(fireproof, (ItemGroup) null, name, block);
        requiredTool.add(block);
    }

    /** Registers a block and its block item.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param identifier   Identifier of the block.
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, @Nullable ArrayList<Item> itemTab, Identifier identifier, Block block) {
        Item.Settings normalSettings = new Item.Settings();
        Item.Settings fireproofSettings = new Item.Settings().fireproof();

        Registry.register(Registry.BLOCK, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, (fireproof ? fireproofSettings : normalSettings)));

        if (itemTab != null) {
            itemTab.add(block.asItem());
        }
        BLOCK_NUMBER++;
    }

    public static void registerBlock(boolean fireproof, ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, String name, Block block) {
        registerBlock(fireproof, itemTab, AtbywMain.id(name), block);
        if (requiredTool != null) {
            requiredTool.add(block);
        }
    }

    public static void registerBlock(boolean fireproof, ArrayList<Item> itemTab, String name, Block block) {
        registerBlock(fireproof, itemTab, null, name, block);
    }

    public static void registerBlock(ArrayList<Item> itemTab, List<Block> requiredTool, String name, Block block) {
        registerBlock(false, itemTab, requiredTool, name, block);
    }

    public static void registerBlock(ArrayList<Item> itemTab, String name, Block block) {
        registerBlock(false, itemTab, null, name, block);
    }

    public static void registerBlock(List<Block> requiredTool, String name, Block block) {
        registerBlock(false, null, requiredTool, name, block);
    }

    public static void registerBlock(String name, Block block) {
        registerBlock(false, null, null, name, block);
    }


    /** Will only register blocks, without block items associated to them.
     *  Registers a given amount of blocks determined by "block" and "variant_type"'s length,
     *  those two arrays MUST match in order to register those blocks, if the lengths mismatch
     *  the game will crash on its own and notify you of that mistake.
     *
     *  @param block_name       The name of the block.
     *  @param variant_type     An array of Strings of which every index will be put between "prefix" and "block_name".
     *  @param block            An Array of Blocks that must match the length of "variant_type".
     */
    public static void registerBlocksOnly(String block_name, String[] variant_type, Block... block) {
        if (block.length == variant_type.length)
            for (int i = 0; i < block.length; i++) {
                registerBlockOnly(String.join("_", variant_type[i], block_name), block[i]);
            }
        else
            throw new IllegalArgumentException("could not register " + block_name + " : mismatched lengths !");
    }

    /** Registers a given amount of blocks determined by "block" and "variant_type"'s length,
     *  those two arrays MUST match in order to register those blocks, if the lengths mismatch
     *  the game will crash on its own and notify you of that mistake.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param prefix           Optional, a prefix that will be added in front of the "block_name".
     *  @param block_name       The name of the block.
     *  @param variant_type     An array of Strings of which every index will be put between "prefix" and "block_name".
     *  @param blocks            An Array of Blocks that must match the length of "variant_type".
     */
    public static void registerBlocks(boolean fireproof, ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, @Nullable String prefix, String block_name, String[] variant_type, Block... blocks) {
        if (blocks.length == variant_type.length)
            for (int i = 0; i < blocks.length; i++) {
                String name;
                if (prefix == null || prefix.isEmpty()) {
                    name = String.join("_", variant_type[i], block_name);
                } else {
                    name = String.join("_", prefix, variant_type[i], block_name);
                }

                registerBlock(fireproof, itemTab, name, blocks[i]);
                if (requiredTool != null) {
                    requiredTool.add(blocks[i]);
                }
            }
        else
            throw new IllegalArgumentException(String.join("could not register " + block_name + " : mismatched lengths !"));
    }

    //Required tool param, No prefix param
    public static void registerBlocks(boolean fireproof, ArrayList<Item> group, List<Block> requiredTool, String block_name, List<String> variant_type, Block... block) {
        registerBlocks(fireproof, group, requiredTool, null, block_name, variant_type.toArray(String[]::new), block);
    }

    //Required tool param, No prefix, No fireproof param
    public static void registerBlocks(ArrayList<Item> group, List<Block> requiredTool, String block_name, List<String> variant_type, Block... block) {
        registerBlocks(false, group, requiredTool, null, block_name, variant_type.toArray(String[]::new), block);
    }

    //Required tool param
    public static void registerBlocks(boolean fireproof, ArrayList<Item> group, List<Block> requiredTool, String prefix, String block_name, List<String> variant_type, Block... block) {
        registerBlocks(fireproof, group, requiredTool, prefix, block_name, variant_type.toArray(String[]::new), block);
    }

    //Required tool param, No fireproof param
    public static void registerBlocks(ArrayList<Item> group, List<Block> requiredTool, String prefix, String block_name, List<String> variant_type, Block... block) {
        registerBlocks(false, group, requiredTool, prefix, block_name, variant_type.toArray(String[]::new), block);
    }

    //No prefix, No required tool param
    public static void registerBlocks(boolean fireproof, ArrayList<Item> group, String block_name, List<String> variant_type, Block... block) {
        registerBlocks(fireproof, group, null, null, block_name, variant_type.toArray(String[]::new), block);
    }

    //No prefix, No fireproof param, No required tool param
    public static void registerBlocks(ArrayList<Item> group, String block_name, List<String> variant_type, Block... block) {
        registerBlocks(false, group, null, null, block_name, variant_type.toArray(String[]::new), block);
    }

    //No required tool param
    public static void registerBlocks(ArrayList<Item> group, String prefix, String block_name, List<String> variant_type, Block... block) {
        registerBlocks(false, group, null, prefix, block_name, variant_type.toArray(String[]::new), block);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool      required tool block {@link List} the block should go in
     * @param miningLevel       mining level block {@link List} the block should go in
     * @param prefix            prefix for the blocks, will be put BEFORE the variant name (ex: PREFIX_blue_block)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, @Nullable List<Block> miningLevel, @Nullable String prefix, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        for (var blockEntry : blocks.entrySet()) {
            var name = "";
            if (prefix == null || prefix.isEmpty())
                name = String.join("_", blockEntry.getKey().asString(), namingFunction.apply(blockEntry.getKey()));
            else
                name = String.join("_", prefix, blockEntry.getKey().asString(), namingFunction.apply(blockEntry.getKey()));

            registerBlock(fireproof, itemTab, name, blockEntry.getValue());

            if (requiredTool != null)
                requiredTool.add(blockEntry.getValue());
            if (miningLevel != null)
                miningLevel.add(blockEntry.getValue());
        }
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, null, null, null, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, null, null, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, @Nullable List<Block> miningLevel, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, miningLevel, null, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, @Nullable String prefix, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, null, null, prefix, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, @Nullable String prefix, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, null, prefix, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, null, null, null, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, requiredTool, null, null, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, @Nullable List<Block> miningLevel, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, requiredTool, miningLevel, null, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, @Nullable String prefix, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, null, null, prefix, (str) -> blockName, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, null, null, null, namingFunction, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, null, null, namingFunction, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, @Nullable List<Block> miningLevel, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, miningLevel, null, namingFunction, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, @Nullable String prefix, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, null, null, prefix, namingFunction, blocks);
    }

    public static void registerColoredBlocks(ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, @Nullable String prefix, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, null, prefix, namingFunction, blocks);
    }

    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, null, null, null, namingFunction, blocks);
    }

    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, requiredTool, null, null, namingFunction, blocks);
    }

    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, @Nullable List<Block> miningLevel, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, requiredTool, miningLevel, null, namingFunction, blocks);
    }

    public static void registerColoredBlocks(boolean fireproof, ArrayList<Item> itemTab, @Nullable String prefix, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, null, null, prefix, namingFunction, blocks);
    }
}
