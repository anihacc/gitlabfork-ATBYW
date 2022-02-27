package net.azagwen.atbyw.block.registry;

import com.google.common.collect.Lists;
import net.azagwen.atbyw.block.*;
import net.azagwen.atbyw.block.registry.containers.MiningLevelContainer;
import net.azagwen.atbyw.block.registry.containers.RequiredToolContainer;
import net.azagwen.atbyw.main.AtbywMain;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

import java.util.function.ToIntFunction;

import static net.azagwen.atbyw.main.AtbywMain.*;
import static net.azagwen.atbyw.block.registry.BlockRegistryUtils.*;

public class AtbywBlocks {
    public static final RequiredToolContainer AXE_MINEABLE = new RequiredToolContainer("axe_mineable");
    public static final RequiredToolContainer HOE_MINEABLE = new RequiredToolContainer("hoe_mineable");
    public static final RequiredToolContainer PICKAXE_MINEABLE = new RequiredToolContainer("pickaxe_mineable");
    public static final RequiredToolContainer SHOVEL_MINEABLE = new RequiredToolContainer("shovel_mineable");
    public static final MiningLevelContainer NEEDS_STONE_TOOL = new MiningLevelContainer("needs_stone_tool");
    public static final MiningLevelContainer NEEDS_IRON_TOOL = new MiningLevelContainer("needs_iron_tool");
    public static final MiningLevelContainer NEEDS_DIAMOND_TOOL = new MiningLevelContainer("needs_diamond_tool");

    public static final Block DEVELOPER_BLOCK = new DevBlock(FabricBlockSettings.of(Material.WOOL, MapColor.ORANGE).nonOpaque().breakByHand(true).strength(0.1F).sounds(BlockSoundGroup.BONE));
    public static final Block SHROOMSTICK = new ShroomStickBlock(FabricBlockSettings.of(AtbywMaterials.SHROOMSTICK).breakByHand(true).breakInstantly().noCollision().nonOpaque().luminance((state) -> 15));
    public static final Block CTM_DEBUG_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE));

    public static final Block PUZZLED_POLISHED_GRANITE = new Block(FabricBlockSettings.of(Material.STONE));         //TODO FINISH OR CANCEL
    public static final Block PUZZLED_POLISHED_DIORITE = new Block(FabricBlockSettings.of(Material.STONE));         //TODO FINISH OR CANCEL
    public static final Block PUZZLED_POLISHED_ANDESITE = new Block(FabricBlockSettings.of(Material.STONE));        //TODO FINISH OR CANCEL
    public static final Block PUZZLED_POLISHED_BLACKSTONE = new Block(FabricBlockSettings.of(Material.STONE));      //TODO FINISH OR CANCEL
    public static final Block PUZZLED_POLISHED_DEEPSLATE = new Block(FabricBlockSettings.of(Material.STONE));       //TODO FINISH OR CANCEL

    public static final Block COBBLED_GRANITE = new Block(FabricBlockSettings.of(Material.STONE));                  //TODO FINISH OR CANCEL
    public static final Block COBBLED_DIORITE = new Block(FabricBlockSettings.of(Material.STONE));                  //TODO FINISH OR CANCEL
    public static final Block COBBLED_ANDESITE = new Block(FabricBlockSettings.of(Material.STONE));                 //TODO FINISH OR CANCEL

    public static final Block SMOOTH_DEEPSLATE = new Block(FabricBlockSettings.of(Material.STONE));                 //TODO FINISH OR CANCEL
    public static final Block SMOOTH_GRANITE = new Block(FabricBlockSettings.of(Material.STONE));                   //TODO FINISH OR CANCEL
    public static final Block SMOOTH_DIORITE = new Block(FabricBlockSettings.of(Material.STONE));                   //TODO FINISH OR CANCEL
    public static final Block SMOOTH_ANDESITE = new Block(FabricBlockSettings.of(Material.STONE));                  //TODO FINISH OR CANCEL

    public static final Block CRIMSON_NYLIUM_MOSS_BLOCK = new Block(FabricBlockSettings.of(Material.MOSS_BLOCK));   //TODO FINISH OR CANCEL
    public static final Block WARPED_NYLIUM_MOSS_BLOCK = new Block(FabricBlockSettings.of(Material.MOSS_BLOCK));    //TODO FINISH OR CANCEL
    public static final Block CRIMSON_NYLIUM_MOSS_CARPET = new Block(FabricBlockSettings.of(Material.MOSS_BLOCK));  //TODO FINISH OR CANCEL
    public static final Block WARPED_NYLIUM_MOSS_CARPET = new Block(FabricBlockSettings.of(Material.MOSS_BLOCK));   //TODO FINISH OR CANCEL


    public static Boolean always(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return true; }
    public static Boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) { return false; }
    public static boolean always(BlockState state, BlockView world, BlockPos pos) { return true; }
    public static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }

    static ToIntFunction<BlockState> lightLevelFromState(int litLevel, BooleanProperty isLit) {
        return (blockState) -> blockState.get(isLit) ? litLevel : 0;
    }

    static ToIntFunction<BlockState> lightLevelFromState(int divider, IntProperty litLevel, BooleanProperty isLit) {
        return (blockState) -> blockState.get(isLit) ? ((int) Math.ceil((double) blockState.get(litLevel) / (double) divider)) : 0;
    }

    public static void init() {
        BlockRegistryUtils.setIdentifierFunc(AtbywMain::id);
        BuildingBlockRegistry.registerAll();    //ATBYW BUILDING BLOCKS
        DecorationBlockRegistry.registerAll();  //ATBYW DECORATION
        RedstoneBlockRegistry.registerAll();    //ATBYW REDSTONE

        //ATBYW MISC
        registerBlock(MISC_TAB, "dev_block", DEVELOPER_BLOCK);
        registerBlock(MISC_TAB, "ctm_debug_block", CTM_DEBUG_BLOCK);

        //Item-less blocks
        BlockRegistryUtils.registerBlock("shroomstick", SHROOMSTICK);

        LOGGER.info("ATBYW Blocks Initialized ({} Blocks registered)", BLOCK_NUMBER);
    }
}

//TODO: Idea: locks to lock chests & doors
//TODO: Experiment with connected models/textures further (Update: going well)
//TODO: Experiment with World Gen
//TODO: Port Atbyw Mod Interaction recipes to datagen (Update: no need, Mod interactions cancelled)

//Ideas
//TODO: STATUES Add Bipedal Statues
//TODO: STATUES Add signing fish function.
//TODO: STATUES Make slime statues combine-able.
//TODO: Add thin ice (world gen when ready)
//TODO: Add Railing Blocks (catwalk handles) || update: WIP
//TODO: Add regular ice bricks that melt
//TODO: Idea > "dried" coral blocks that keep their colors
//TODO: Add chairs ?
//TODO: Add step detectors.
//TODO: Add a chain hook that you can hook items and blocks to.
//TODO: Add Iron ladder that can be deployed downwards using redstone (Update: Done, might needs proof-testing)
//TODO: Add carpets that connect together in patterns
//TODO: Add more blocks exploiting connected textures
//TODO: Add smooth variants of Deepslathe, Granite, Diorite, Andesite, Tuff...
//TODO: Add Amethyst bricks
//TODO: Add Amethyst Walls/Fences
//TODO: Add Cactus Planks & assorted stuff (Update: WIP)
//TODO: Add Stone melter furnace
//TODO: Add string curtains for doorways
//TODO: Energized Dropper/Dispenser > Instantly dispenses what it holds out of "anger"
