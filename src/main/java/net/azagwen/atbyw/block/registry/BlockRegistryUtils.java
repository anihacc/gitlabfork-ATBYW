package net.azagwen.atbyw.block.registry;

import net.azagwen.atbyw.main.AtbywMain;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public record BlockRegistryUtils() {

    /** Registers a block without a block item.
     *
     *  @param name     Name of the block (path)
     *  @param block    The Block field
     */
    public static void registerBlockOnly(String name, Block block) {
        Registry.register(Registry.BLOCK, AtbywMain.id(name), block);
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
    }

    public static void registerBlock(boolean fireproof, ArrayList<Item> itemTab, @Nullable List<Block> requiredTool, String name, Block block) {
        registerBlock(fireproof, itemTab, name, block);
        if (requiredTool != null) {
            requiredTool.add(block);
        }
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

    /** Registers a block and its block item under the default ATBYW namespace.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param name         Name of the block (Identifier path).
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, ArrayList<Item> itemTab, String name, Block block) {
        registerBlock(fireproof, itemTab, AtbywMain.id(name), block);
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
}
