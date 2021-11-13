package net.azagwen.atbyw.block.registry;

import net.azagwen.atbyw.block.*;
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
import static net.azagwen.atbyw.util.BlockUtils.*;

public class AtbywBlocks {
    //TODO: Idea: locks to lock chests & doors
    //TODO: Experiment with connected models/textures further (Update: going well)
    //TODO: Experiment with World Gen
    //TODO: Port Atbyw Mod Interaction recipes to datagen

    //Blocks to add
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
    //TODO: Add Iron ladder that can be deployed downwards using redstone
    //TODO: Add carpets that connect together in patterns
    //TODO: Add more blocks exploiting connected textures
    //TODO: Add smooth variants of Deepslathe, Granite, Diorite, Andesite, Tuff...
    //TODO: Add Amethyst bricks
    //TODO: Add Amethyst Walls/Fences
    //TODO: Add Cactus Planks & assorted stuff (Update: WIP)
    //TODO: Add Stone melter furnace
    //TODO: Add string curtains for doorways
    //TODO: Energized Dropper/Dispenser > Instantly dispenses what it holds out of "anger"

    //TODO: Fix Tinting table not working anymore (bruh I hate it what the fuck)a

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

    public static final Block PUZZLED_POLISHED_GRANITE = new Block(FabricBlockSettings.of(Material.STONE));         //TODO FINISH
    public static final Block PUZZLED_POLISHED_DIORITE = new Block(FabricBlockSettings.of(Material.STONE));         //TODO FINISH
    public static final Block PUZZLED_POLISHED_ANDESITE = new Block(FabricBlockSettings.of(Material.STONE));        //TODO FINISH
    public static final Block PUZZLED_POLISHED_BLACKSTONE = new Block(FabricBlockSettings.of(Material.STONE));      //TODO FINISH
    public static final Block PUZZLED_POLISHED_DEEPSLATE = new Block(FabricBlockSettings.of(Material.STONE));       //TODO FINISH

    public static final Block COBBLED_GRANITE = new Block(FabricBlockSettings.of(Material.STONE));                  //TODO FINISH
    public static final Block COBBLED_DIORITE = new Block(FabricBlockSettings.of(Material.STONE));                  //TODO FINISH
    public static final Block COBBLED_ANDESITE = new Block(FabricBlockSettings.of(Material.STONE));                 //TODO FINISH

    public static final Block SMOOTH_DEEPSLATE = new Block(FabricBlockSettings.of(Material.STONE));                 //TODO FINISH
    public static final Block SMOOTH_GRANITE = new Block(FabricBlockSettings.of(Material.STONE));                   //TODO FINISH
    public static final Block SMOOTH_DIORITE = new Block(FabricBlockSettings.of(Material.STONE));                   //TODO FINISH
    public static final Block SMOOTH_ANDESITE = new Block(FabricBlockSettings.of(Material.STONE));                  //TODO FINISH

    public static final Block AMETHYST_BRICKS = new Block(FabricBlockSettings.of(Material.AMETHYST));               //TODO FINISH
    public static final Block AMETHYST_BRICKS_SLAB = new Block(FabricBlockSettings.of(Material.AMETHYST));          //TODO FINISH
    public static final Block AMETHYST_BRICKS_STAIRS = new Block(FabricBlockSettings.of(Material.AMETHYST));        //TODO FINISH
    public static final Block CHISELED_AMETHYST_BRICKS = new Block(FabricBlockSettings.of(Material.AMETHYST));      //TODO FINISH
    public static final Block AMETHYST_FENCE = new Block(FabricBlockSettings.of(Material.AMETHYST));                //TODO FINISH

    public static final Block DRIPSTONE_BLOCK_BRICKS = new Block(FabricBlockSettings.of(Material.STONE));           //TODO FINISH
    public static final Block DRIPSTONE_BLOCK_BRICKS_SLAB = new Block(FabricBlockSettings.of(Material.STONE));      //TODO FINISH
    public static final Block DRIPSTONE_BLOCK_BRICKS_STAIRS = new Block(FabricBlockSettings.of(Material.STONE));    //TODO FINISH
    public static final Block DRIPSTONE_BLOCK_BRICKS_WALL = new Block(FabricBlockSettings.of(Material.STONE));      //TODO FINISH

    public static final Block CRIMSON_NYLIUM_MOSS_BLOCK = new Block(FabricBlockSettings.of(Material.MOSS_BLOCK));   //TODO FINISH
    public static final Block WARPED_NYLIUM_MOSS_BLOCK = new Block(FabricBlockSettings.of(Material.MOSS_BLOCK));    //TODO FINISH
    public static final Block CRIMSON_NYLIUM_MOSS_CARPET = new Block(FabricBlockSettings.of(Material.MOSS_BLOCK));  //TODO FINISH
    public static final Block WARPED_NYLIUM_MOSS_CARPET = new Block(FabricBlockSettings.of(Material.MOSS_BLOCK));   //TODO FINISH

    public static final Block DEVELOPER_BLOCK = new DevBlock(FabricBlockSettings.of(Material.WOOL, MapColor.ORANGE).nonOpaque().breakByHand(true).strength(0.1F).sounds(BlockSoundGroup.BONE));
    public static final Block SHROOMSTICK = new ShroomStickBlock(FabricBlockSettings.of(AtbywMaterials.SHROOMSTICK).breakByHand(true).breakInstantly().noCollision().nonOpaque().luminance((state) -> 15));
    public static final Block CTM_DEBUG_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE));

    public static void init() {
        BuildingBlockRegistry.init();   //ATBYW BUILDING BLOCKS
        DecorationBlockRegistry.init(); //ATBYW DECORATION
        RedstoneBlockRegistry.init();   //ATBYW REDSTONE

        //ATBYW MISC
        registerBlock(false, MISC_TAB, "dev_block", DEVELOPER_BLOCK);
        registerBlock(false, MISC_TAB, "ctm_debug_block", CTM_DEBUG_BLOCK);

        //Item-less blocks
        registerBlockOnly("shroomstick", SHROOMSTICK);

        LOGGER.info("ATBYW Blocks Inintiliazed");
    }
}
