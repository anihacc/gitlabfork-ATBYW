package net.azagwen.atbyw.block.registry;

import net.azagwen.atbyw.block.registry.containers.AtbywBlockContainer;
import net.azagwen.atbyw.block.registry.containers.ItemTabContainer;
import net.azagwen.atbyw.block.registry.containers.MiningLevelContainer;
import net.azagwen.atbyw.block.registry.containers.RequiredToolContainer;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Function;

public record BlockRegistryUtils() {
    public static int BLOCK_NUMBER;
    private static Function<String, Identifier> IDENTIFIER_FUNC;

    public static void setIdentifierFunc(Function<String, Identifier> function) {
        IDENTIFIER_FUNC = function;
    }

    /** Registers a block without a block item.
     *
     *  @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param identifier   {@link Identifier} of the block
     *  @param block        The Block field
     */
    public static void registerBlock(@Nullable RequiredToolContainer requiredTool, @Nullable MiningLevelContainer miningLevel, Identifier identifier, Block block) {
        Registry.register(Registry.BLOCK, identifier, block);

        if (requiredTool != null) {
            requiredTool.add(block);
        }
        if (miningLevel != null) {
            miningLevel.add(block);
        }
        BLOCK_NUMBER++;
    }

    /** Registers a block without a block item.
     *
     *  @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param name         Name of the block (path)
     *  @param block        The Block field
     */
    public static void registerBlock(@Nullable RequiredToolContainer requiredTool, @Nullable MiningLevelContainer miningLevel, String name, Block block) {
        registerBlock(requiredTool, miningLevel, IDENTIFIER_FUNC.apply(name), block);
    }

    /** Registers a block without a block item.
     *
     *  @param name     Name of the block (path)
     *  @param block    The Block field
     */
    public static void registerBlock(String name, Block block) {
        registerBlock((RequiredToolContainer) null, null, name, block);
    }

    /** Registers a block without a block item.
     *
     *  @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param name     Name of the block (path)
     *  @param block    The Block field
     */
    public static void registerBlock(RequiredToolContainer requiredTool, String name, Block block) {
        registerBlock(requiredTool, null, name, block);
    }

    /** Registers a block and its block item.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param group        the ItemGroup this block should be in.
     *  @param name         Name of the block (Identifier path).
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, @Nullable ItemGroup group, @Nullable RequiredToolContainer requiredTool, @Nullable MiningLevelContainer miningLevel, String name, Block block) {
        Item.Settings normalSettings = group != null ? new Item.Settings().group(group) : new Item.Settings();
        Item.Settings fireproofSettings = group != null ? new Item.Settings().group(group).fireproof() : new Item.Settings().fireproof();

        registerBlock(requiredTool, miningLevel, name, block);
        Registry.register(Registry.ITEM, IDENTIFIER_FUNC.apply(name), new BlockItem(block, (fireproof ? fireproofSettings : normalSettings)));
    }

    /** Registers a block and its block item.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param identifier   Identifier of the block.
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, @Nullable ItemTabContainer itemTab, @Nullable RequiredToolContainer requiredTool, @Nullable MiningLevelContainer miningLevel, Identifier identifier, Block block) {
        Item.Settings normalSettings = new Item.Settings();
        Item.Settings fireproofSettings = new Item.Settings().fireproof();

        registerBlock(requiredTool, miningLevel, identifier, block);
        Registry.register(Registry.ITEM, identifier, new BlockItem(block, (fireproof ? fireproofSettings : normalSettings)));

        if (itemTab != null) {
            itemTab.add(block.asItem());
        }
    }

    /** Registers a block and its block item.
     *
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param name         the name of the block.
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(ItemTabContainer itemTab, String name, Block block) {
        registerBlock(false, itemTab, null, null, IDENTIFIER_FUNC.apply(name), block);
    }

    /** Registers a block and its block item.
     *
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param name         the name of the block.
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(ItemTabContainer itemTab, RequiredToolContainer requiredTool, String name, Block block) {
        registerBlock(false, itemTab, requiredTool, null, IDENTIFIER_FUNC.apply(name), block);
    }

    /** Registers a block and its block item.
     *
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param name         the name of the block.
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(ItemTabContainer itemTab, RequiredToolContainer requiredTool, MiningLevelContainer miningLevel, String name, Block block) {
        registerBlock(false, itemTab, requiredTool, miningLevel, IDENTIFIER_FUNC.apply(name), block);
    }

    /** Registers a block and its block item.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param name         the name of the block.
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, ItemTabContainer itemTab, String name, Block block) {
        registerBlock(fireproof, itemTab, null, null, IDENTIFIER_FUNC.apply(name), block);
    }

    /** Registers a block and its block item.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param name         the name of the block.
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, ItemTabContainer itemTab, RequiredToolContainer requiredTool, String name, Block block) {
        registerBlock(fireproof, itemTab, requiredTool, null, IDENTIFIER_FUNC.apply(name), block);
    }

    /** Registers a block and its block item.
     *
     *  @param fireproof    if the Block item should resist to fire & Lava.
     *  @param itemTab      the ItemTab list this block should be in.
     *  @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     *  @param name         the name of the block.
     *  @param block        The declared Block that will be registered.
     */
    public static void registerBlock(boolean fireproof, ItemTabContainer itemTab, RequiredToolContainer requiredTool, MiningLevelContainer miningLevel, String name, Block block) {
        registerBlock(fireproof, itemTab, requiredTool, miningLevel, IDENTIFIER_FUNC.apply(name), block);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param prefix            prefix for the blocks, will be put BEFORE the variant name (ex: PREFIX_blue_block)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, @Nullable RequiredToolContainer requiredTool, @Nullable MiningLevelContainer miningLevel, @Nullable String prefix, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
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

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, null, null, null, (str) -> blockName, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, RequiredToolContainer requiredTool, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, null, null, (str) -> blockName, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, RequiredToolContainer requiredTool, MiningLevelContainer miningLevel, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, miningLevel, null, (str) -> blockName, blocks);
    }


    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param prefix            prefix for the blocks, will be put BEFORE the variant name (ex: PREFIX_blue_block)
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, String prefix, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, null, null, prefix, (str) -> blockName, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param prefix            prefix for the blocks, will be put BEFORE the variant name (ex: PREFIX_blue_block)
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, RequiredToolContainer requiredTool, String prefix, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, null, prefix, (str) -> blockName, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, null, null, null, (str) -> blockName, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, RequiredToolContainer requiredTool, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, requiredTool, null, null, (str) -> blockName, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, RequiredToolContainer requiredTool, MiningLevelContainer miningLevel, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, requiredTool, miningLevel, null, (str) -> blockName, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param prefix            prefix for the blocks, will be put BEFORE the variant name (ex: PREFIX_blue_block)
     * @param blockName         the block's name
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, String prefix, String blockName, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, null, null, prefix, (str) -> blockName, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, null, null, null, namingFunction, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, @Nullable RequiredToolContainer requiredTool, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, null, null, namingFunction, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, RequiredToolContainer requiredTool, MiningLevelContainer miningLevel, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, miningLevel, null, namingFunction, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param prefix            prefix for the blocks, will be put BEFORE the variant name (ex: PREFIX_blue_block)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, String prefix, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, null, null, prefix, namingFunction, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param prefix            prefix for the blocks, will be put BEFORE the variant name (ex: PREFIX_blue_block)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(ItemTabContainer itemTab, RequiredToolContainer requiredTool, String prefix, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(false, itemTab, requiredTool, null, prefix, namingFunction, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, null, null, null, namingFunction, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, RequiredToolContainer requiredTool, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, requiredTool, null, null, namingFunction, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param requiredTool the type of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param miningLevel  the material of tool required to break this block (must be a {@link AtbywBlockContainer}, will be treated like a tag)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, RequiredToolContainer requiredTool, MiningLevelContainer miningLevel, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, requiredTool, miningLevel, null, namingFunction, blocks);
    }

    /**
     * Registers a set of "colored" blocks using the input map
     *
     * @param fireproof         if the block items should be able to burn or not
     * @param itemTab           the item tab the blocks should end up in
     * @param prefix            prefix for the blocks, will be put BEFORE the variant name (ex: PREFIX_blue_block)
     * @param namingFunction    {@link Function} that will determine the block's name (ex: if the variant is CRIMSON, name is "stem", else it's "log")
     * @param blocks            {@link Map} of blocks to register
     */
    public static void registerColoredBlocks(boolean fireproof, ItemTabContainer itemTab, String prefix, Function<StringIdentifiable, String> namingFunction, Map<StringIdentifiable,Block> blocks) {
        registerColoredBlocks(fireproof, itemTab, null, null, prefix, namingFunction, blocks);
    }
}
