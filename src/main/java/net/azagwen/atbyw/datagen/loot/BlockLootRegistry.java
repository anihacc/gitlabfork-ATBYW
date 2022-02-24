package net.azagwen.atbyw.datagen.loot;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.azagwen.atbyw.block.FenceDoorBlock;
import net.azagwen.atbyw.block.SnowBlockSubClass;
import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.azagwen.atbyw.block.registry.DecorationBlockRegistry;
import net.azagwen.atbyw.block.registry.RedstoneBlockRegistry;
import net.azagwen.atbyw.block.registry.StatueRegistry;
import net.azagwen.atbyw.item.AtbywItems;
import net.azagwen.atbyw.util.AtbywUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.SlabType;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.AlternativeEntry;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.CopyNbtLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.nbt.ContextLootNbtProvider;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.StatePredicate;
import net.minecraft.predicate.item.EnchantmentPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.state.property.Property;
import net.minecraft.util.StringIdentifiable;

import java.util.function.Function;

import static net.azagwen.atbyw.datagen.loot.LootDatagen.*;

public class BlockLootRegistry {

    public static LootTable.Builder blockLootTable() {
        return LootTable.builder().type(LootContextTypes.BLOCK);
    }

    public static LootPool.Builder createBasicLootPool(float lootNumber, ItemConvertible drop) {
        var itemEntry = ItemEntry.builder(drop);
        var surviveExplosionCondition = SurvivesExplosionLootCondition.builder();

        return LootPool.builder().rolls(ConstantLootNumberProvider.create(lootNumber)).with(itemEntry).conditionally(surviveExplosionCondition);
    }

    public static LootCondition.Builder silkTouchCondition() {
        var itemPredicate = ItemPredicate.Builder.create();
        var enchantmentPredicate = new EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1));

        return MatchToolLootCondition.builder(itemPredicate.enchantment(enchantmentPredicate));
    }

    public static LootCondition.Builder propertyCondition(Block drop, Property<Integer> state, Integer value) {
        var stateLootCondition = BlockStatePropertyLootCondition.builder(drop);
        var predicate = StatePredicate.Builder.create().exactMatch(state, value);
        return stateLootCondition.properties(predicate);
    }

    public static <T extends Comparable<T> & StringIdentifiable> LootCondition.Builder propertyCondition(Block drop, Property<T> state, T value) {
        var stateLootCondition = BlockStatePropertyLootCondition.builder(drop);
        var predicate = StatePredicate.Builder.create().exactMatch(state, value);
        return stateLootCondition.properties(predicate);
    }

    public static LootTable dropsSelf(Block drop) {
        return blockLootTable().pool(createBasicLootPool(1.0F, drop)).build();
    }

    public static LootTable dropsSilkTouch(Block drop) {
        return blockLootTable().pool(createBasicLootPool(1.0F, drop).conditionally(silkTouchCondition())).build();
    }

    public static LootTable dropsSilkTouchAlternatives(Block silkTouchDrop, ItemConvertible regularDrop, int regularDropCount) {
        var pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F));
        var countLootFunction = SetCountLootFunction.builder(ConstantLootNumberProvider.create(regularDropCount));
        var silkTouchAlternative = ItemEntry.builder(silkTouchDrop).conditionally(silkTouchCondition());
        var regularAlternative = ItemEntry.builder(regularDrop).apply(countLootFunction);
        var alternativeEntry = AlternativeEntry.builder().alternatively(silkTouchAlternative).alternatively(regularAlternative);
        var surviveExplosionCondition = SurvivesExplosionLootCondition.builder();

        return blockLootTable().pool(pool.with(alternativeEntry).conditionally(surviveExplosionCondition)).build();
    }

    public static LootTable slabLootTable(Block drop) {
        var pool = createBasicLootPool(1.0F, drop);
        var countLootFunction = SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F));
        var propertyCondition = propertyCondition(drop, SlabBlock.TYPE, SlabType.DOUBLE);

        return blockLootTable().pool(pool.apply(countLootFunction.conditionally(propertyCondition))).build();
    }

    public static LootTable silkTouchAlternativesSlabLootTable(Block regularDrop, Block silkTouchDrop) {
        var pool = LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0F));
        var silkTouchAlternative = ItemEntry.builder(silkTouchDrop).conditionally(silkTouchCondition());
        var regularAlternative = ItemEntry.builder(regularDrop);
        var alternativeEntry = AlternativeEntry.builder().alternatively(silkTouchAlternative).alternatively(regularAlternative);
        var countLootFunction = SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F));
        var propertyCondition = propertyCondition(silkTouchDrop, SlabBlock.TYPE, SlabType.DOUBLE);
        var surviveExplosionCondition = SurvivesExplosionLootCondition.builder();

        return blockLootTable().pool(pool.with(alternativeEntry).apply(countLootFunction.conditionally(propertyCondition)).conditionally(surviveExplosionCondition)).build();
    }

    public static LootTable silkTouchSlabLootTable(Block drop) {
        var pool = createBasicLootPool(1.0F, drop).conditionally(silkTouchCondition());
        var countLootFunction = SetCountLootFunction.builder(ConstantLootNumberProvider.create(2.0F));
        var propertyCondition = propertyCondition(drop, SlabBlock.TYPE, SlabType.DOUBLE);

        return blockLootTable().pool(pool.apply(countLootFunction.conditionally(propertyCondition))).build();
    }

    public static LootTable fenceDoorLootTable(Block drop) {
        var pool = createBasicLootPool(1.0F, drop);
        var propertyCondition = propertyCondition(drop, FenceDoorBlock.HALF, DoubleBlockHalf.LOWER);

        return blockLootTable().pool(pool.conditionally(propertyCondition)).build();
    }

    public static ConditionalLootFunction.Builder compactedSnowLayerCountCondition(Block drop, int layers) {
        var layerCondition = propertyCondition(drop, SnowBlockSubClass.LAYERS,  layers);
        var countLootFunction = SetCountLootFunction.builder(ConstantLootNumberProvider.create(layers));

        return countLootFunction.conditionally(layerCondition);
    }

    public static LootTable compactedSnowLootTable(Block drop) {
        var pool = createBasicLootPool(1.0F, drop);
        var layerCondition2 = compactedSnowLayerCountCondition(drop, 2);
        var layerCondition3 = compactedSnowLayerCountCondition(drop, 3);
        var layerCondition4 = compactedSnowLayerCountCondition(drop, 4);
        var layerCondition5 = compactedSnowLayerCountCondition(drop, 5);
        var layerCondition6 = compactedSnowLayerCountCondition(drop, 6);
        var layerCondition7 = compactedSnowLayerCountCondition(drop, 7);
        var layerCondition8 = compactedSnowLayerCountCondition(drop, 8);

        return blockLootTable().pool(pool.apply(layerCondition2).apply(layerCondition3).apply(layerCondition4).apply(layerCondition5).apply(layerCondition6).apply(layerCondition7).apply(layerCondition8)).build();
    }

    public static LootTable canvasBlockLootTable(Block drop) {
        var pool = createBasicLootPool(1.0F, drop);

        return blockLootTable().pool(pool.apply(CopyNbtLootFunction.builder(ContextLootNbtProvider.BLOCK_ENTITY).withOperation("color", "display.color"))).build();
    }

    public static void registerSimpleMiscLoot(Block drop, Function<Block, LootTable> lootTableSupplier) {
        registerBlockLoot(lootTableSupplier.apply(drop), AtbywUtils.getId(drop));
    }

    public static void registerAll() {
        registerDropsSelf();
        registerDropsSilkTouch();
        registerDropsSilkTouchAlternatives();
        registerSlabLoots();
        registerSilkTouchSlabLoots();
        registerSilkTouchAlternativesSlabLoots();
        registerFenceDoorLoots();

        registerSimpleMiscLoot(DecorationBlockRegistry.COMPACTED_SNOW, BlockLootRegistry::compactedSnowLootTable);
        registerSimpleMiscLoot(DecorationBlockRegistry.CANVAS_BLOCK, BlockLootRegistry::canvasBlockLootTable);
        registerSimpleMiscLoot(DecorationBlockRegistry.GLOWING_CANVAS_BLOCK, BlockLootRegistry::canvasBlockLootTable);
    }

    private static void registerDropsSelf() {
        var blocks = Lists.<Block>newArrayList();
        blocks.add(RedstoneBlockRegistry.OAK_BOOKSHELF_TOGGLE);
        blocks.add(RedstoneBlockRegistry.SPRUCE_BOOKSHELF_TOGGLE);
        blocks.add(RedstoneBlockRegistry.BIRCH_BOOKSHELF_TOGGLE);
        blocks.add(RedstoneBlockRegistry.JUNGLE_BOOKSHELF_TOGGLE);
        blocks.add(RedstoneBlockRegistry.ACACIA_BOOKSHELF_TOGGLE);
        blocks.add(RedstoneBlockRegistry.DARK_OAK_BOOKSHELF_TOGGLE);
        blocks.add(RedstoneBlockRegistry.CRIMSON_BOOKSHELF_TOGGLE);
        blocks.add(RedstoneBlockRegistry.WARPED_BOOKSHELF_TOGGLE);
        blocks.add(RedstoneBlockRegistry.REDSTONE_LANTERN);
        blocks.add(RedstoneBlockRegistry.DANDELION_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.POPPY_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.BLUE_ORCHID_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.ALLIUM_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.AZURE_BLUET_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.RED_TULIP_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.ORANGE_TULIP_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.WHITE_TULIP_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.PINK_TULIP_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.OXEYE_DAISY_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.CORNFLOWER_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.LILY_OF_THE_VALLEY_PULL_SWITCH);
        blocks.add(RedstoneBlockRegistry.WITHER_ROSE_PULL_SWITCH);
        blocks.add(BuildingBlockRegistry.DIRT_STAIRS);
        blocks.add(BuildingBlockRegistry.COARSE_DIRT_STAIRS);
        blocks.add(BuildingBlockRegistry.NETHERRACK_STAIRS);
        blocks.add(DecorationBlockRegistry.SPRUCE_LADDER);
        blocks.add(DecorationBlockRegistry.BIRCH_LADDER);
        blocks.add(DecorationBlockRegistry.JUNGLE_LADDER);
        blocks.add(DecorationBlockRegistry.ACACIA_LADDER);
        blocks.add(DecorationBlockRegistry.DARK_OAK_LADDER);
        blocks.add(DecorationBlockRegistry.CRIMSON_LADDER);
        blocks.add(DecorationBlockRegistry.WARPED_LADDER);
        blocks.add(DecorationBlockRegistry.BAMBOO_LADDER);
        blocks.add(BuildingBlockRegistry.PURPUR_TILES);
        blocks.add(BuildingBlockRegistry.CHISELED_PURPUR_BLOCK);
        blocks.add(BuildingBlockRegistry.CUT_PURPUR_BLOCK);
        blocks.add(BuildingBlockRegistry.SMOOTH_PURPUR_BLOCK);
        blocks.add(BuildingBlockRegistry.PURPUR_TILES_STAIRS);
        blocks.add(BuildingBlockRegistry.CUT_PURPUR_STAIRS);
        blocks.add(BuildingBlockRegistry.SMOOTH_PURPUR_STAIRS);
        blocks.add(BuildingBlockRegistry.COMPACTED_SNOW_BLOCK);
        blocks.add(BuildingBlockRegistry.COMPACTED_SNOW_BRICKS);
        blocks.add(BuildingBlockRegistry.CHISELED_COMPACTED_SNOW_BRICKS);
        blocks.add(BuildingBlockRegistry.COMPACTED_SNOW_BLOCK_STAIRS);
        blocks.add(BuildingBlockRegistry.COMPACTED_SNOW_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.BASALT_BRICKS);
        blocks.add(BuildingBlockRegistry.BASALT_PILLAR);
        blocks.add(BuildingBlockRegistry.TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.WHITE_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.ORANGE_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.MAGENTA_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.LIGHT_BLUE_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.YELLOW_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.LIME_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.PINK_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.GRAY_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.LIGHT_GRAY_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.CYAN_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.PURPLE_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.BLUE_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.BROWN_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.GREEN_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.RED_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.BLACK_TERRACOTTA_STAIRS);
        blocks.add(BuildingBlockRegistry.TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.WHITE_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.ORANGE_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.MAGENTA_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.LIGHT_BLUE_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.YELLOW_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.LIME_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.PINK_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.GRAY_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.LIGHT_GRAY_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.CYAN_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.PURPLE_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.BLUE_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.BROWN_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.GREEN_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.RED_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.BLACK_TERRACOTTA_BRICKS);
        blocks.add(BuildingBlockRegistry.TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.WHITE_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.ORANGE_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.MAGENTA_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.LIGHT_BLUE_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.YELLOW_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.LIME_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.PINK_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.GRAY_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.LIGHT_GRAY_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.CYAN_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.PURPLE_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.BLUE_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.BROWN_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.GREEN_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.RED_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.BLACK_TERRACOTTA_BRICKS_STAIRS);
        blocks.add(DecorationBlockRegistry.TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.WHITE_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.ORANGE_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.MAGENTA_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.LIGHT_BLUE_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.YELLOW_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.LIME_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.PINK_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.GRAY_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.LIGHT_GRAY_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.CYAN_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.PURPLE_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.BLUE_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.BROWN_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.GREEN_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.RED_TERRACOTTA_BRICKS_WALL);
        blocks.add(DecorationBlockRegistry.BLACK_TERRACOTTA_BRICKS_WALL);
        blocks.add(BuildingBlockRegistry.WHITE_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.ORANGE_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.MAGENTA_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.LIGHT_BLUE_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.YELLOW_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.LIME_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.PINK_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.GRAY_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.LIGHT_GRAY_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.CYAN_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.PURPLE_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.BLUE_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.BROWN_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.GREEN_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.RED_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.BLACK_CONCRETE_STAIRS);
        blocks.add(BuildingBlockRegistry.WHITE_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.ORANGE_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.MAGENTA_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.LIGHT_BLUE_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.YELLOW_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.LIME_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.PINK_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.GRAY_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.LIGHT_GRAY_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.CYAN_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.PURPLE_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.BLUE_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.BROWN_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.GREEN_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.RED_CINDER_BLOCKS);
        blocks.add(BuildingBlockRegistry.BLACK_CINDER_BLOCKS);
        blocks.add(DecorationBlockRegistry.WHITE_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.ORANGE_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.MAGENTA_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.LIGHT_BLUE_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.YELLOW_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.LIME_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.PINK_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.GRAY_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.LIGHT_GRAY_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.CYAN_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.PURPLE_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.BLUE_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.BROWN_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.GREEN_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.RED_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.BLACK_CINDER_BLOCKS_WALL);
        blocks.add(DecorationBlockRegistry.GRANITE_COLUMN);
        blocks.add(DecorationBlockRegistry.DIORITE_COLUMN);
        blocks.add(DecorationBlockRegistry.ANDESITE_COLUMN);
        blocks.add(DecorationBlockRegistry.SANDSTONE_COLUMN);
        blocks.add(DecorationBlockRegistry.CHISELED_SANDSTONE_COLUMN);
        blocks.add(DecorationBlockRegistry.RED_SANDSTONE_COLUMN);
        blocks.add(DecorationBlockRegistry.CHISELED_RED_SANDSTONE_COLUMN);
        blocks.add(DecorationBlockRegistry.PURPUR_COLUMN);
        blocks.add(DecorationBlockRegistry.STONE_BRICKS_COLUMN);
        blocks.add(DecorationBlockRegistry.MOSSY_STONE_BRICKS_COLUMN);
        blocks.add(DecorationBlockRegistry.CRACKED_STONE_BRICKS_COLUMN);
        blocks.add(DecorationBlockRegistry.NETHER_BRICKS_COLUMN);
        blocks.add(DecorationBlockRegistry.QUARTZ_COLUMN);
        blocks.add(DecorationBlockRegistry.PRISMARINE_COLUMN);
        blocks.add(DecorationBlockRegistry.BLACKSTONE_COLUMN);
        blocks.add(DecorationBlockRegistry.DEVELOPER_BLOCK);
        blocks.add(DecorationBlockRegistry.SHROOMSTICK);
        blocks.add(StatueRegistry.WAXED_CLEAN_BEE_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_BEE_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_BEE_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_BEE_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_BEE_STATUE);
        blocks.add(StatueRegistry.BEE_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_SILVERFISH_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_SILVERFISH_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_SILVERFISH_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_SILVERFISH_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_SILVERFISH_STATUE);
        blocks.add(StatueRegistry.SILVERFISH_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_ENDERMITE_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_ENDERMITE_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_ENDERMITE_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_ENDERMITE_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_ENDERMITE_STATUE);
        blocks.add(StatueRegistry.ENDERMITE_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_SHULKER_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_SHULKER_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_SHULKER_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_SHULKER_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_SHULKER_STATUE);
        blocks.add(StatueRegistry.SHULKER_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_WOLF_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_WOLF_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_WOLF_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_WOLF_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_WOLF_STATUE);
        blocks.add(StatueRegistry.WOLF_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_CAT_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_CAT_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_CAT_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_CAT_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_CAT_STATUE);
        blocks.add(StatueRegistry.CAT_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_CHICKEN_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_CHICKEN_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_CHICKEN_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_CHICKEN_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_CHICKEN_STATUE);
        blocks.add(StatueRegistry.CHICKEN_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_RABBIT_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_RABBIT_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_RABBIT_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_RABBIT_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_RABBIT_STATUE);
        blocks.add(StatueRegistry.RABBIT_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_FOX_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_FOX_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_FOX_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_FOX_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_FOX_STATUE);
        blocks.add(StatueRegistry.FOX_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_COD_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_COD_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_COD_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_COD_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_COD_STATUE);
        blocks.add(StatueRegistry.COD_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_SALMON_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_SALMON_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_SALMON_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_SALMON_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_SALMON_STATUE);
        blocks.add(StatueRegistry.SALMON_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_PUFFER_FISH_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_PUFFER_FISH_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_PUFFER_FISH_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_PUFFER_FISH_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_PUFFER_FISH_STATUE);
        blocks.add(StatueRegistry.PUFFER_FISH_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_SLIME_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_SLIME_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_SLIME_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_SLIME_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_SLIME_STATUE);
        blocks.add(StatueRegistry.SLIME_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_MAGMA_CUBE_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_MAGMA_CUBE_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_MAGMA_CUBE_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_MAGMA_CUBE_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_MAGMA_CUBE_STATUE);
        blocks.add(StatueRegistry.MAGMA_CUBE_STATUE);
        blocks.add(RedstoneBlockRegistry.IRON_SPIKE_TRAP);
        blocks.add(RedstoneBlockRegistry.GOLD_SPIKE_TRAP);
        blocks.add(RedstoneBlockRegistry.DIAMOND_SPIKE_TRAP);
        blocks.add(RedstoneBlockRegistry.NETHERITE_SPIKE_TRAP);
        blocks.add(RedstoneBlockRegistry.REDSTONE_JACK_O_LANTERN);
        blocks.add(BuildingBlockRegistry.SOUL_JACK_O_LANTERN);
        blocks.add(RedstoneBlockRegistry.TIMER_REPEATER);
        blocks.add(RedstoneBlockRegistry.REDSTONE_CROSS_PATH);
        blocks.add(BuildingBlockRegistry.GRANITE_TILES);
        blocks.add(BuildingBlockRegistry.DIORITE_BRICKS);
        blocks.add(BuildingBlockRegistry.ANDESITE_BRICKS);
        blocks.add(BuildingBlockRegistry.GRANITE_TILES_STAIRS);
        blocks.add(BuildingBlockRegistry.DIORITE_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.ANDESITE_BRICKS_STAIRS);
        blocks.add(DecorationBlockRegistry.LARGE_CHAIN);
        blocks.add(BuildingBlockRegistry.ROOTED_DIRT_STAIRS);
        blocks.add(StatueRegistry.AXOLOTL_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_AXOLOTL_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_AXOLOTL_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_AXOLOTL_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_AXOLOTL_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_AXOLOTL_STATUE);
        blocks.add(StatueRegistry.BAT_STATUE);
        blocks.add(StatueRegistry.WAXED_CLEAN_BAT_STATUE);
        blocks.add(StatueRegistry.WAXED_EXPOSED_BAT_STATUE);
        blocks.add(StatueRegistry.WAXED_DIRTY_BAT_STATUE);
        blocks.add(StatueRegistry.WAXED_MOSSY_BAT_STATUE);
        blocks.add(StatueRegistry.WAXED_VERY_MOSSY_BAT_STATUE);
        blocks.add(BuildingBlockRegistry.SAND_STAIRS);
        blocks.add(BuildingBlockRegistry.RED_SAND_STAIRS);
        blocks.add(BuildingBlockRegistry.GRAVEL_STAIRS);
        blocks.add(BuildingBlockRegistry.OAK_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.SPRUCE_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.BIRCH_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.JUNGLE_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.ACACIA_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.DARK_OAK_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.CRIMSON_STEM_STAIRS);
        blocks.add(BuildingBlockRegistry.WARPED_STEM_STAIRS);
        blocks.add(BuildingBlockRegistry.STRIPPED_OAK_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.STRIPPED_SPRUCE_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.STRIPPED_BIRCH_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.STRIPPED_JUNGLE_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.STRIPPED_ACACIA_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.STRIPPED_DARK_OAK_LOG_STAIRS);
        blocks.add(BuildingBlockRegistry.STRIPPED_CRIMSON_STEM_STAIRS);
        blocks.add(BuildingBlockRegistry.STRIPPED_WARPED_STEM_STAIRS);
        blocks.add(RedstoneBlockRegistry.REDSTONE_PIPE);
        blocks.add(RedstoneBlockRegistry.REDSTONE_PIPE_REPEATER);
        blocks.add(RedstoneBlockRegistry.REDSTONE_PIPE_INVERTER);
        blocks.add(DecorationBlockRegistry.TINTING_TABLE);
        blocks.add(BuildingBlockRegistry.RAW_CACTUS_PLANKS);
        blocks.add(BuildingBlockRegistry.CACTUS_PLANKS);
        blocks.add(BuildingBlockRegistry.CACTUS_STAIRS);
        blocks.add(DecorationBlockRegistry.CACTUS_FENCE);
        blocks.add(DecorationBlockRegistry.CACTUS_LADDER);
        blocks.add(DecorationBlockRegistry.CACTUS_SIGN);
        blocks.add(RedstoneBlockRegistry.IRON_LADDER);

        for (var block : blocks) {
            var lootTable = dropsSelf(block);
            var lootTableId = AtbywUtils.getId(block);
            registerBlockLoot(lootTable, lootTableId);
        }
    }

    private static void registerDropsSilkTouch() {
        var blocks = Lists.<Block>newArrayList();
        blocks.add(BuildingBlockRegistry.CHISELED_PACKED_ICE_BRICKS);
        blocks.add(BuildingBlockRegistry.CHISELED_BLUE_ICE_BRICKS);
        blocks.add(BuildingBlockRegistry.PACKED_ICE_BRICKS);
        blocks.add(BuildingBlockRegistry.BLUE_ICE_BRICKS);
        blocks.add(BuildingBlockRegistry.PACKED_ICE_STAIRS);
        blocks.add(BuildingBlockRegistry.BLUE_ICE_STAIRS);
        blocks.add(BuildingBlockRegistry.PACKED_ICE_BRICKS_STAIRS);
        blocks.add(BuildingBlockRegistry.BLUE_ICE_BRICKS_STAIRS);

        for (var block : blocks) {
            var lootTable = dropsSilkTouch(block);
            var lootTableId = AtbywUtils.getId(block);
            registerBlockLoot(lootTable, lootTableId);
        }
    }

    private static void registerDropsSilkTouchAlternatives() {
        //R: Regular drop, C: Silk touch drop, V: Regular drop count
        var blocks = HashBasedTable.<Block, ItemConvertible, Integer>create();
        blocks.put(BuildingBlockRegistry.GRASS_BLOCK_STAIRS, BuildingBlockRegistry.DIRT_STAIRS, 1);
        blocks.put(BuildingBlockRegistry.MYCELIUM_STAIRS, BuildingBlockRegistry.DIRT_STAIRS, 1);
        blocks.put(BuildingBlockRegistry.PODZOL_STAIRS, BuildingBlockRegistry.DIRT_STAIRS, 1);
        blocks.put(BuildingBlockRegistry.GRASS_PATH_STAIRS, BuildingBlockRegistry.DIRT_STAIRS, 1);
        blocks.put(BuildingBlockRegistry.CRIMSON_NYLIUM_STAIRS, BuildingBlockRegistry.NETHERRACK_STAIRS, 1);
        blocks.put(BuildingBlockRegistry.WARPED_NYLIUM_STAIRS, BuildingBlockRegistry.NETHERRACK_STAIRS, 1);
        blocks.put(BuildingBlockRegistry.SPRUCE_BOOKSHELF, Items.BOOK, 3);
        blocks.put(BuildingBlockRegistry.BIRCH_BOOKSHELF, Items.BOOK, 3);
        blocks.put(BuildingBlockRegistry.JUNGLE_BOOKSHELF, Items.BOOK, 3);
        blocks.put(BuildingBlockRegistry.ACACIA_BOOKSHELF, Items.BOOK, 3);
        blocks.put(BuildingBlockRegistry.DARK_OAK_BOOKSHELF, Items.BOOK, 3);
        blocks.put(BuildingBlockRegistry.CRIMSON_BOOKSHELF, Items.BOOK, 3);
        blocks.put(BuildingBlockRegistry.WARPED_BOOKSHELF, Items.BOOK, 3);
        blocks.put(BuildingBlockRegistry.SHATTERED_GLASS, AtbywItems.GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.WHITE_STAINED_SHATTERED_GLASS, AtbywItems.WHITE_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.ORANGE_STAINED_SHATTERED_GLASS, AtbywItems.ORANGE_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.MAGENTA_STAINED_SHATTERED_GLASS, AtbywItems.MAGENTA_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.LIGHT_BLUE_STAINED_SHATTERED_GLASS, AtbywItems.LIGHT_BLUE_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.YELLOW_STAINED_SHATTERED_GLASS, AtbywItems.YELLOW_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.LIME_STAINED_SHATTERED_GLASS, AtbywItems.LIME_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.PINK_STAINED_SHATTERED_GLASS, AtbywItems.PINK_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.GRAY_STAINED_SHATTERED_GLASS, AtbywItems.GRAY_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.LIGHT_GRAY_STAINED_SHATTERED_GLASS, AtbywItems.LIGHT_GRAY_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.CYAN_STAINED_SHATTERED_GLASS, AtbywItems.CYAN_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.PURPLE_STAINED_SHATTERED_GLASS, AtbywItems.PURPLE_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.BLUE_STAINED_SHATTERED_GLASS, AtbywItems.BLUE_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.BROWN_STAINED_SHATTERED_GLASS, AtbywItems.BROWN_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.GREEN_STAINED_SHATTERED_GLASS, AtbywItems.GREEN_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.RED_STAINED_SHATTERED_GLASS, AtbywItems.RED_STAINED_GLASS_SHARD, 4);
        blocks.put(BuildingBlockRegistry.BLACK_STAINED_SHATTERED_GLASS, AtbywItems.BLACK_STAINED_GLASS_SHARD, 4);

        for (var block : blocks.cellSet()) {
            var lootTable = dropsSilkTouchAlternatives(block.getRowKey(), block.getColumnKey(), block.getValue());
            var lootTableId = AtbywUtils.getId(block.getRowKey());
            registerBlockLoot(lootTable, lootTableId);
        }
    }

    private static void registerSlabLoots() {
        var blocks = Lists.<Block>newArrayList();
        blocks.add(BuildingBlockRegistry.DIRT_SLAB);
        blocks.add(BuildingBlockRegistry.COARSE_DIRT_SLAB);
        blocks.add(BuildingBlockRegistry.NETHERRACK_SLAB);
        blocks.add(BuildingBlockRegistry.PURPUR_TILES_SLAB);
        blocks.add(BuildingBlockRegistry.CUT_PURPUR_SLAB);
        blocks.add(BuildingBlockRegistry.SMOOTH_PURPUR_SLAB);
        blocks.add(BuildingBlockRegistry.COMPACTED_SNOW_BLOCK_SLAB);
        blocks.add(BuildingBlockRegistry.COMPACTED_SNOW_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.WHITE_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.ORANGE_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.MAGENTA_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.LIGHT_BLUE_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.YELLOW_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.LIME_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.PINK_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.GRAY_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.LIGHT_GRAY_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.CYAN_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.PURPLE_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.BLUE_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.BROWN_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.GREEN_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.RED_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.BLACK_TERRACOTTA_SLAB);
        blocks.add(BuildingBlockRegistry.TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.WHITE_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.ORANGE_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.MAGENTA_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.LIGHT_BLUE_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.YELLOW_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.LIME_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.PINK_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.GRAY_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.LIGHT_GRAY_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.CYAN_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.PURPLE_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.BLUE_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.BROWN_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.GREEN_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.RED_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.BLACK_TERRACOTTA_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.WHITE_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.ORANGE_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.MAGENTA_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.LIGHT_BLUE_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.YELLOW_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.LIME_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.PINK_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.GRAY_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.LIGHT_GRAY_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.CYAN_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.PURPLE_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.BLUE_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.BROWN_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.GREEN_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.RED_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.BLACK_CONCRETE_SLAB);
        blocks.add(BuildingBlockRegistry.GRANITE_TILES_SLAB);
        blocks.add(BuildingBlockRegistry.DIORITE_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.ANDESITE_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.ROOTED_DIRT_SLAB);
        blocks.add(BuildingBlockRegistry.SAND_SLAB);
        blocks.add(BuildingBlockRegistry.RED_SAND_SLAB);
        blocks.add(BuildingBlockRegistry.GRAVEL_SLAB);
        blocks.add(BuildingBlockRegistry.OAK_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.SPRUCE_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.BIRCH_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.JUNGLE_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.ACACIA_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.DARK_OAK_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.CRIMSON_STEM_SLAB);
        blocks.add(BuildingBlockRegistry.WARPED_STEM_SLAB);
        blocks.add(BuildingBlockRegistry.STRIPPED_OAK_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.STRIPPED_SPRUCE_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.STRIPPED_BIRCH_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.STRIPPED_JUNGLE_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.STRIPPED_ACACIA_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.STRIPPED_DARK_OAK_LOG_SLAB);
        blocks.add(BuildingBlockRegistry.STRIPPED_CRIMSON_STEM_SLAB);
        blocks.add(BuildingBlockRegistry.STRIPPED_WARPED_STEM_SLAB);
        blocks.add(BuildingBlockRegistry.CACTUS_SLAB);

        for (var block : blocks) {
            var lootTable = slabLootTable(block);
            var lootTableId = AtbywUtils.getId(block);
            registerBlockLoot(lootTable, lootTableId);
        }
    }

    private static void registerSilkTouchSlabLoots() {
        var blocks = Lists.<Block>newArrayList();
        blocks.add(BuildingBlockRegistry.PACKED_ICE_SLAB);
        blocks.add(BuildingBlockRegistry.BLUE_ICE_SLAB);
        blocks.add(BuildingBlockRegistry.PACKED_ICE_BRICKS_SLAB);
        blocks.add(BuildingBlockRegistry.BLUE_ICE_BRICKS_SLAB);

        for (var block : blocks) {
            var lootTable = silkTouchSlabLootTable(block);
            var lootTableId = AtbywUtils.getId(block);
            registerBlockLoot(lootTable, lootTableId);
        }
    }

    private static void registerSilkTouchAlternativesSlabLoots() {
        var blocks = Maps.<Block, Block>newHashMap();
        blocks.put(BuildingBlockRegistry.GRASS_BLOCK_SLAB, BuildingBlockRegistry.DIRT_SLAB);
        blocks.put(BuildingBlockRegistry.MYCELIUM_SLAB, BuildingBlockRegistry.DIRT_SLAB);
        blocks.put(BuildingBlockRegistry.PODZOL_SLAB, BuildingBlockRegistry.DIRT_SLAB);
        blocks.put(BuildingBlockRegistry.GRASS_PATH_SLAB, BuildingBlockRegistry.DIRT_SLAB);
        blocks.put(BuildingBlockRegistry.CRIMSON_NYLIUM_SLAB, BuildingBlockRegistry.NETHERRACK_SLAB);
        blocks.put(BuildingBlockRegistry.WARPED_NYLIUM_SLAB, BuildingBlockRegistry.NETHERRACK_SLAB);

        for (var block : blocks.entrySet()) {
            var lootTable = silkTouchAlternativesSlabLootTable(block.getValue(), block.getKey());
            var lootTableId = AtbywUtils.getId(block.getKey());
            registerBlockLoot(lootTable, lootTableId);
        }
    }

    private static void registerFenceDoorLoots() {
        var blocks = Lists.<Block>newArrayList();
        blocks.add(RedstoneBlockRegistry.IRON_FENCE_DOOR);
        blocks.add(RedstoneBlockRegistry.OAK_FENCE_DOOR);
        blocks.add(RedstoneBlockRegistry.SPRUCE_FENCE_DOOR);
        blocks.add(RedstoneBlockRegistry.BIRCH_FENCE_DOOR);
        blocks.add(RedstoneBlockRegistry.JUNGLE_FENCE_DOOR);
        blocks.add(RedstoneBlockRegistry.ACACIA_FENCE_DOOR);
        blocks.add(RedstoneBlockRegistry.DARK_OAK_FENCE_DOOR);
        blocks.add(RedstoneBlockRegistry.CRIMSON_FENCE_DOOR);
        blocks.add(RedstoneBlockRegistry.WARPED_FENCE_DOOR);

        for (var block : blocks) {
            var lootTable = fenceDoorLootTable(block);
            var lootTableId = AtbywUtils.getId(block);
            registerBlockLoot(lootTable, lootTableId);
        }
    }
}
