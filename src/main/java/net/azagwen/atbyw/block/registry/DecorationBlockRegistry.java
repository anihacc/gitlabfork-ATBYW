package net.azagwen.atbyw.block.registry;

import com.google.common.collect.Maps;
import net.azagwen.atbyw.block.*;
import net.azagwen.atbyw.block.extensions.AtbywLadderBlock;
import net.azagwen.atbyw.block.extensions.AtbywWallBlock;
import net.azagwen.atbyw.block.sign.AtbywSignType;
import net.azagwen.atbyw.block.sign.SignBlockExt;
import net.azagwen.atbyw.block.sign.WallSignBlockExt;
import net.azagwen.atbyw.item.CanvasBlockItem;
import net.azagwen.atbyw.main.AtbywMain;
import net.azagwen.atbyw.util.AtbywUtils;
import net.azagwen.atbyw.util.naming.WoodType;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.item.SignItem;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.Map;

import static net.azagwen.atbyw.block.registry.BlockRegistryUtils.*;
import static net.azagwen.atbyw.main.AtbywMain.BLOCKS_TAB;
import static net.azagwen.atbyw.main.AtbywMain.DECO_TAB;

public class DecorationBlockRegistry extends AtbywBlocks {
    public static final Map<StringIdentifiable, Block> LADDER_MAP = Maps.newLinkedHashMap();
    public static final Map<StringIdentifiable, Block> TERRACOTTA_BRICKS_WALL_MAP = Maps.newLinkedHashMap();
    public static final Map<StringIdentifiable, Block> CINDER_BLOCKS_WALL_MAP = Maps.newLinkedHashMap();

    public static final Block TINTING_TABLE = new TintingTableBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).requiresTool().sounds(BlockSoundGroup.COPPER));
    public static final Block CANVAS_BLOCK = createCanvasBlock(false);
    public static final Block GLOWING_CANVAS_BLOCK = createCanvasBlock(true);
    public static final Block SPRUCE_LADDER = new AtbywLadderBlock(LADDER_MAP, WoodType.SPRUCE, FabricBlockSettings.copyOf(Blocks.LADDER));
    public static final Block BIRCH_LADDER = new AtbywLadderBlock(LADDER_MAP, WoodType.BIRCH, FabricBlockSettings.copyOf(Blocks.LADDER));
    public static final Block JUNGLE_LADDER = new AtbywLadderBlock(LADDER_MAP, WoodType.JUNGLE, FabricBlockSettings.copyOf(Blocks.LADDER));
    public static final Block ACACIA_LADDER = new AtbywLadderBlock(LADDER_MAP, WoodType.ACACIA, FabricBlockSettings.copyOf(Blocks.LADDER));
    public static final Block DARK_OAK_LADDER = new AtbywLadderBlock(LADDER_MAP, WoodType.DARK_OAK, FabricBlockSettings.copyOf(Blocks.LADDER));
    public static final Block CRIMSON_LADDER = new AtbywLadderBlock(LADDER_MAP, WoodType.CRIMSON, FabricBlockSettings.copyOf(Blocks.LADDER));
    public static final Block WARPED_LADDER = new AtbywLadderBlock(LADDER_MAP, WoodType.WARPED, FabricBlockSettings.copyOf(Blocks.LADDER));
    public static final Block BAMBOO_LADDER = new BambooLadderBlock(FabricBlockSettings.copyOf(Blocks.BAMBOO));
    public static final Block CACTUS_LADDER = new AtbywLadderBlock(FabricBlockSettings.copyOf(Blocks.LADDER));
    public static final Block CACTUS_FENCE = new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_DOOR));
    public static final Block CACTUS_SIGN = new SignBlockExt(AtbywSignType.CACTUS, FabricBlockSettings.copyOf(Blocks.OAK_SIGN));
    public static final Block CACTUS_WALL_SIGN = new WallSignBlockExt(AtbywSignType.CACTUS, FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN));
    public static final Block COMPACTED_SNOW = new SnowBlockSubClass(FabricBlockSettings.of(Material.SNOW_LAYER).strength(0.2F).requiresTool().sounds(BlockSoundGroup.SNOW));
    public static final Block LARGE_CHAIN = new LargeChainBlock(FabricBlockSettings.copyOf(Blocks.CHAIN).requiresTool());
    public static final Block GRANITE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.GRANITE).requiresTool());
    public static final Block DIORITE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.DIORITE).requiresTool());
    public static final Block ANDESITE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.ANDESITE).requiresTool());
    public static final Block SANDSTONE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.SANDSTONE).requiresTool());
    public static final Block CHISELED_SANDSTONE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.SANDSTONE).requiresTool());
    public static final Block RED_SANDSTONE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE).requiresTool());
    public static final Block CHISELED_RED_SANDSTONE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.RED_SANDSTONE).requiresTool());
    public static final Block PURPUR_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.PURPUR_BLOCK).requiresTool());
    public static final Block STONE_BRICKS_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS).requiresTool());
    public static final Block MOSSY_STONE_BRICKS_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.MOSSY_STONE_BRICKS).requiresTool());
    public static final Block CRACKED_STONE_BRICKS_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.CRACKED_STONE_BRICKS).requiresTool());
    public static final Block NETHER_BRICKS_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.NETHER_BRICKS).requiresTool());
    public static final Block QUARTZ_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK).requiresTool());
    public static final Block PRISMARINE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.PRISMARINE).requiresTool());
    public static final Block BLACKSTONE_COLUMN = new ColumnBlock(FabricBlockSettings.copyOf(Blocks.BLACKSTONE).requiresTool());
    public static final Block ACACIA_RAILING = new RailingBlock(AtbywMain.id("acacia_railing"), FabricBlockSettings.copyOf(Blocks.ACACIA_FENCE));
    public static final Block TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(FabricBlockSettings.copyOf(Blocks.TERRACOTTA).requiresTool());
    public static final Block WHITE_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.WHITE, FabricBlockSettings.copyOf(Blocks.WHITE_TERRACOTTA).requiresTool());
    public static final Block ORANGE_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.ORANGE, FabricBlockSettings.copyOf(Blocks.ORANGE_TERRACOTTA).requiresTool());
    public static final Block MAGENTA_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.MAGENTA, FabricBlockSettings.copyOf(Blocks.MAGENTA_TERRACOTTA).requiresTool());
    public static final Block LIGHT_BLUE_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.LIGHT_BLUE, FabricBlockSettings.copyOf(Blocks.LIGHT_BLUE_TERRACOTTA).requiresTool());
    public static final Block YELLOW_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.YELLOW, FabricBlockSettings.copyOf(Blocks.YELLOW_TERRACOTTA).requiresTool());
    public static final Block LIME_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.LIME, FabricBlockSettings.copyOf(Blocks.LIME_TERRACOTTA).requiresTool());
    public static final Block PINK_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.PINK, FabricBlockSettings.copyOf(Blocks.PINK_TERRACOTTA).requiresTool());
    public static final Block GRAY_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.GRAY, FabricBlockSettings.copyOf(Blocks.GRAY_TERRACOTTA).requiresTool());
    public static final Block LIGHT_GRAY_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.LIGHT_GRAY, FabricBlockSettings.copyOf(Blocks.LIGHT_GRAY_TERRACOTTA).requiresTool());
    public static final Block CYAN_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.CYAN, FabricBlockSettings.copyOf(Blocks.CYAN_TERRACOTTA).requiresTool());
    public static final Block PURPLE_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.PURPLE, FabricBlockSettings.copyOf(Blocks.PURPLE_TERRACOTTA).requiresTool());
    public static final Block BLUE_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.BLUE, FabricBlockSettings.copyOf(Blocks.BLUE_TERRACOTTA).requiresTool());
    public static final Block BROWN_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.BROWN, FabricBlockSettings.copyOf(Blocks.BROWN_TERRACOTTA).requiresTool());
    public static final Block GREEN_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.GREEN, FabricBlockSettings.copyOf(Blocks.GREEN_TERRACOTTA).requiresTool());
    public static final Block RED_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.RED, FabricBlockSettings.copyOf(Blocks.RED_TERRACOTTA).requiresTool());
    public static final Block BLACK_TERRACOTTA_BRICKS_WALL = new AtbywWallBlock(TERRACOTTA_BRICKS_WALL_MAP, DyeColor.BLACK, FabricBlockSettings.copyOf(Blocks.BLACK_TERRACOTTA).requiresTool());
    public static final Block WHITE_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.WHITE, FabricBlockSettings.copyOf(Blocks.WHITE_CONCRETE).requiresTool());
    public static final Block ORANGE_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.ORANGE, FabricBlockSettings.copyOf(Blocks.ORANGE_CONCRETE).requiresTool());
    public static final Block MAGENTA_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.MAGENTA, FabricBlockSettings.copyOf(Blocks.MAGENTA_CONCRETE).requiresTool());
    public static final Block LIGHT_BLUE_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.LIGHT_BLUE, FabricBlockSettings.copyOf(Blocks.LIGHT_BLUE_CONCRETE).requiresTool());
    public static final Block YELLOW_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.YELLOW, FabricBlockSettings.copyOf(Blocks.YELLOW_CONCRETE).requiresTool());
    public static final Block LIME_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.LIME, FabricBlockSettings.copyOf(Blocks.LIME_CONCRETE).requiresTool());
    public static final Block PINK_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.PINK, FabricBlockSettings.copyOf(Blocks.PINK_CONCRETE).requiresTool());
    public static final Block GRAY_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.GRAY, FabricBlockSettings.copyOf(Blocks.GRAY_CONCRETE).requiresTool());
    public static final Block LIGHT_GRAY_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.LIGHT_GRAY, FabricBlockSettings.copyOf(Blocks.LIGHT_GRAY_CONCRETE).requiresTool());
    public static final Block CYAN_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.CYAN, FabricBlockSettings.copyOf(Blocks.CYAN_CONCRETE).requiresTool());
    public static final Block PURPLE_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.PURPLE, FabricBlockSettings.copyOf(Blocks.PURPLE_CONCRETE).requiresTool());
    public static final Block BLUE_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.BLUE, FabricBlockSettings.copyOf(Blocks.BLUE_CONCRETE).requiresTool());
    public static final Block BROWN_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.BROWN, FabricBlockSettings.copyOf(Blocks.BROWN_CONCRETE).requiresTool());
    public static final Block GREEN_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.GREEN, FabricBlockSettings.copyOf(Blocks.GREEN_CONCRETE).requiresTool());
    public static final Block RED_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.RED, FabricBlockSettings.copyOf(Blocks.RED_CONCRETE).requiresTool());
    public static final Block BLACK_CINDER_BLOCKS_WALL = new CinderBlocksWallBlock(CINDER_BLOCKS_WALL_MAP, DyeColor.BLACK, FabricBlockSettings.copyOf(Blocks.BLACK_CONCRETE).requiresTool());
    public static final Block AMETHYST_BRICKS_WALL = new Block(FabricBlockSettings.copyOf(BuildingBlockRegistry.AMETHYST_BRICKS).requiresTool());
    public static final Block DRIPSTONE_BRICKS_WALL = new Block(FabricBlockSettings.copyOf(BuildingBlockRegistry.DRIPSTONE_BRICKS).requiresTool());

    public static final Block TEST_MINI_BLOCK = new MiniBlock(FabricBlockSettings.of(Material.STONE));

    private static CanvasBlock createCanvasBlock(boolean glowing) {
        return new CanvasBlock(glowing, FabricBlockSettings.of(Material.WOOL).strength(0.5F).sounds(BlockSoundGroup.WOOL));
    }

    public static void registerCanvasBlock(ArrayList<Item> itemTab, String name, Block block) {
        Registry.register(Registry.BLOCK, AtbywMain.id(name), block);
        Registry.register(Registry.ITEM, AtbywMain.id(name), new CanvasBlockItem(block, new Item.Settings()));

        itemTab.add(block.asItem());
    }

    public static void registerSign(ArrayList<Item> itemTab, Block standingSign, Block wallSign) {
        var type = ((SignBlockExt) standingSign).getType().getName();
        Registry.register(Registry.BLOCK, AtbywMain.id(type + "_sign"), standingSign);
        Registry.register(Registry.BLOCK, AtbywMain.id(type + "_wall_sign"), wallSign);
        Registry.register(Registry.ITEM, AtbywMain.id(type + "_sign"), new SignItem(new Item.Settings(), standingSign, wallSign));

        itemTab.add(standingSign.asItem());
        AXE_MINEABLE.add(standingSign);
        AXE_MINEABLE.add(wallSign);
    }

    public static void registerAll() {
        registerBlock(DECO_TAB, "mini_block", TEST_MINI_BLOCK);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "tinting_table", TINTING_TABLE);
        registerCanvasBlock(DECO_TAB, "canvas_block", CANVAS_BLOCK);
        registerCanvasBlock(DECO_TAB, "glowing_canvas_block", GLOWING_CANVAS_BLOCK);
        registerColoredBlocks(DECO_TAB, AXE_MINEABLE, "ladder", LADDER_MAP);
        registerBlock(DECO_TAB, AXE_MINEABLE, "bamboo_ladder", BAMBOO_LADDER);
        registerBlock(DECO_TAB, AXE_MINEABLE, "cactus_ladder", CACTUS_LADDER);
        registerBlock(DECO_TAB, AXE_MINEABLE, "cactus_fence", CACTUS_FENCE);
        registerSign(DECO_TAB, CACTUS_SIGN, CACTUS_WALL_SIGN);
        registerBlock(DECO_TAB, SHOVEL_MINEABLE, "compacted_snow", COMPACTED_SNOW);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "large_chain", LARGE_CHAIN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "granite_column", GRANITE_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "diorite_column", DIORITE_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "andesite_column", ANDESITE_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "sandstone_column", SANDSTONE_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "chiseled_sandstone_column", CHISELED_SANDSTONE_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "red_sandstone_column", RED_SANDSTONE_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "chiseled_red_sandstone_column", CHISELED_RED_SANDSTONE_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "purpur_column", PURPUR_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "stone_bricks_column", STONE_BRICKS_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "mossy_stone_bricks_column", MOSSY_STONE_BRICKS_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "cracked_stone_bricks_column", CRACKED_STONE_BRICKS_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "nether_bricks_column", NETHER_BRICKS_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "quartz_column", QUARTZ_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "prismarine_column", PRISMARINE_COLUMN);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "blackstone_column", BLACKSTONE_COLUMN);
        registerBlock(AXE_MINEABLE, "acacia_railing", ACACIA_RAILING);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "dripstone_bricks_wall", DRIPSTONE_BRICKS_WALL);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "amethyst_bricks_wall", AMETHYST_BRICKS_WALL);
        registerBlock(DECO_TAB, PICKAXE_MINEABLE, "terracotta_bricks_wall", TERRACOTTA_BRICKS_WALL);
        registerColoredBlocks(DECO_TAB, PICKAXE_MINEABLE, "terracotta_bricks_wall", TERRACOTTA_BRICKS_WALL_MAP);
        registerColoredBlocks(DECO_TAB, PICKAXE_MINEABLE, "cinder_blocks_wall", CINDER_BLOCKS_WALL_MAP);

        StatueRegistry.registerAll();
    }
}
