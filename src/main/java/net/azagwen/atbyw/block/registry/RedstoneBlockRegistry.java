package net.azagwen.atbyw.block.registry;

import net.azagwen.atbyw.block.*;
import net.azagwen.atbyw.block.state.AtbywProperties;
import net.azagwen.atbyw.block.state.SpikeTrapMaterials;
import net.azagwen.atbyw.util.naming.FlowerNames;
import net.azagwen.atbyw.util.naming.WoodNames;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;

import static net.azagwen.atbyw.main.AtbywMain.REDSTONE_TAB;
import static net.azagwen.atbyw.block.registry.BlockRegistryUtils.*;
import static net.azagwen.atbyw.block.registry.BlockRegistryUtils.registerBlockOnly;

public class RedstoneBlockRegistry extends AtbywBlocks {
    public static final Block IRON_LADDER = new IronLadderBlock(FabricBlockSettings.of(Material.METAL).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block IRON_LADDER_PIECE = new IronLadderPieceBlock(FabricBlockSettings.of(Material.METAL).requiresTool().sounds(BlockSoundGroup.METAL).nonOpaque());
    public static final Block REDSTONE_PIPE = new RedstonePipeBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.COPPER));
    public static final Block REDSTONE_PIPE_INVERTER = new RedstonePipeInverterBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.COPPER));
    public static final Block REDSTONE_PIPE_REPEATER = new RedstonePipeRepeaterBlock(FabricBlockSettings.of(Material.METAL).sounds(BlockSoundGroup.COPPER));
    public static final Block TIMER_REPEATER = new TimerRepeaterBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
    public static final Block REDSTONE_CROSS_PATH = new RedstoneCrossPathBlock(FabricBlockSettings.copyOf(Blocks.REPEATER));
    public static final Block REDSTONE_LANTERN = new RedstoneLanternBlock(FabricBlockSettings.copyOf(Blocks.LANTERN).luminance(lightLevelFromState(2, AtbywProperties.POWER_INTENSITY, Properties.LIT)));
    public static final Block REDSTONE_JACK_O_LANTERN = new RedstoneJackOlantern(FabricBlockSettings.of(Material.GOURD, MapColor.ORANGE).strength(1.0F).sounds(BlockSoundGroup.WOOD).luminance(lightLevelFromState(7, RedstoneJackOlantern.LIT)).solidBlock(AtbywBlocks::never).allowsSpawning(AtbywBlocks::always));
    public static final Block OAK_BOOKSHELF_TOGGLE = new BookshelfToggleBlock();
    public static final Block SPRUCE_BOOKSHELF_TOGGLE = new BookshelfToggleBlock();
    public static final Block BIRCH_BOOKSHELF_TOGGLE = new BookshelfToggleBlock();
    public static final Block JUNGLE_BOOKSHELF_TOGGLE = new BookshelfToggleBlock();
    public static final Block ACACIA_BOOKSHELF_TOGGLE = new BookshelfToggleBlock();
    public static final Block DARK_OAK_BOOKSHELF_TOGGLE = new BookshelfToggleBlock();
    public static final Block CRIMSON_BOOKSHELF_TOGGLE = new BookshelfToggleBlock();
    public static final Block WARPED_BOOKSHELF_TOGGLE = new BookshelfToggleBlock();
    public static final Block DANDELION_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.DANDELION));
    public static final Block POPPY_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.POPPY));
    public static final Block BLUE_ORCHID_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.BLUE_ORCHID));
    public static final Block ALLIUM_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.ALLIUM));
    public static final Block AZURE_BLUET_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.AZURE_BLUET));
    public static final Block RED_TULIP_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.RED_TULIP));
    public static final Block ORANGE_TULIP_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.ORANGE_TULIP));
    public static final Block WHITE_TULIP_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.WHITE_TULIP));
    public static final Block PINK_TULIP_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.PINK_TULIP));
    public static final Block OXEYE_DAISY_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.OXEYE_DAISY));
    public static final Block CORNFLOWER_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.CORNFLOWER));
    public static final Block LILY_OF_THE_VALLEY_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.LILY_OF_THE_VALLEY));
    public static final Block WITHER_ROSE_PULL_SWITCH = new FlowerButtonBlock(flowerPullSwitchSettings(Blocks.WITHER_ROSE));
    public static final Block IRON_SPIKE_TRAP = new SpikeTrapBlock(SpikeTrapMaterials.IRON, spikeTrapSettings());
    public static final Block GOLD_SPIKE_TRAP = new SpikeTrapBlock(SpikeTrapMaterials.GOLD, spikeTrapSettings());
    public static final Block DIAMOND_SPIKE_TRAP = new SpikeTrapBlock(SpikeTrapMaterials.DIAMOND, spikeTrapSettings());
    public static final Block NETHERITE_SPIKE_TRAP = new SpikeTrapBlock(SpikeTrapMaterials.NETHERITE, spikeTrapSettings());
    public static final Block IRON_SPIKE_TRAP_SPIKES = new SpikeBlock(SpikeTrapMaterials.IRON, spikeSettings());
    public static final Block GOLD_SPIKE_TRAP_SPIKES = new SpikeBlock(SpikeTrapMaterials.GOLD, spikeSettings());
    public static final Block DIAMOND_SPIKE_TRAP_SPIKES = new SpikeBlock(SpikeTrapMaterials.DIAMOND, spikeSettings());
    public static final Block NETHERITE_SPIKE_TRAP_SPIKES = new SpikeBlock(SpikeTrapMaterials.NETHERITE, spikeSettings());
    public static final Block OAK_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.OAK_PLANKS, true));
    public static final Block SPRUCE_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.SPRUCE_DOOR, true));
    public static final Block BIRCH_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.BIRCH_DOOR, true));
    public static final Block JUNGLE_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.JUNGLE_DOOR, true));
    public static final Block ACACIA_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.ACACIA_DOOR, true));
    public static final Block DARK_OAK_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.DARK_OAK_DOOR, true));
    public static final Block CRIMSON_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.CRIMSON_DOOR, true));
    public static final Block WARPED_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.WARPED_DOOR, true));
    public static final Block IRON_FENCE_DOOR = new FenceDoorBlock(fenceDoorSettings(Blocks.IRON_DOOR, false));

    private static FabricBlockSettings spikeTrapSettings() {
        return FabricBlockSettings.of(Material.PISTON).strength(1.5F).requiresTool().solidBlock(AtbywBlocks::never);
    }

    private static FabricBlockSettings spikeSettings() {
        return FabricBlockSettings.of(Material.PISTON).strength(1.5F).requiresTool().solidBlock(AtbywBlocks::never).suffocates(AtbywBlocks::never).blockVision(AtbywBlocks::never).dropsNothing().nonOpaque().noCollision();
    }

    private static FabricBlockSettings flowerPullSwitchSettings(Block flower) {
        var material = flower.getDefaultState().getMaterial();
        var mapColor = flower.getDefaultMapColor();
        var lightLevel = lightLevelFromState(8, Properties.LIT);
        return FabricBlockSettings.of(material, mapColor).strength(0.25F).luminance(lightLevel).sounds(BlockSoundGroup.WOOD);
    }

    private static FabricBlockSettings fenceDoorSettings(Block door, boolean wooden) {
        var material = door.getDefaultState().getMaterial();
        var mapColor = door.getDefaultMapColor();
        var hardness = door.getHardness();
        var resistance = door.getBlastResistance();
        var sound = door.getSoundGroup(door.getDefaultState());

        var settings = FabricBlockSettings.of(material, mapColor).strength(hardness, resistance).sounds(sound);
        return wooden ? settings : settings.requiresTool();
    }

    public static void registerAll() {
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "iron_ladder", IRON_LADDER);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "redstone_pipe", REDSTONE_PIPE);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "redstone_pipe_inverter", REDSTONE_PIPE_INVERTER);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "redstone_pipe_repeater", REDSTONE_PIPE_REPEATER);
        registerBlock(REDSTONE_TAB, "timer_repeater", TIMER_REPEATER);
        registerBlock(REDSTONE_TAB, "redstone_cross_path", REDSTONE_CROSS_PATH);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "redstone_lantern", REDSTONE_LANTERN);
        registerBlock(REDSTONE_TAB, AXE_MINEABLE, "redstone_jack_o_lantern", REDSTONE_JACK_O_LANTERN);
        registerBlocks(REDSTONE_TAB, AXE_MINEABLE, "bookshelf_toggle", WoodNames.getNames(), OAK_BOOKSHELF_TOGGLE, SPRUCE_BOOKSHELF_TOGGLE, BIRCH_BOOKSHELF_TOGGLE, JUNGLE_BOOKSHELF_TOGGLE, ACACIA_BOOKSHELF_TOGGLE, DARK_OAK_BOOKSHELF_TOGGLE, CRIMSON_BOOKSHELF_TOGGLE, WARPED_BOOKSHELF_TOGGLE);
        registerBlocks(REDSTONE_TAB, AXE_MINEABLE, "pull_switch", FlowerNames.getNames(), DANDELION_PULL_SWITCH, POPPY_PULL_SWITCH, BLUE_ORCHID_PULL_SWITCH, ALLIUM_PULL_SWITCH, AZURE_BLUET_PULL_SWITCH, RED_TULIP_PULL_SWITCH, ORANGE_TULIP_PULL_SWITCH, WHITE_TULIP_PULL_SWITCH, PINK_TULIP_PULL_SWITCH, OXEYE_DAISY_PULL_SWITCH, CORNFLOWER_PULL_SWITCH, LILY_OF_THE_VALLEY_PULL_SWITCH, WITHER_ROSE_PULL_SWITCH);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "iron_spike_trap", IRON_SPIKE_TRAP);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "gold_spike_trap", GOLD_SPIKE_TRAP);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "diamond_spike_trap", DIAMOND_SPIKE_TRAP);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "netherite_spike_trap", NETHERITE_SPIKE_TRAP);
        registerBlocks(REDSTONE_TAB, AXE_MINEABLE, "fence_door", WoodNames.getNames(), OAK_FENCE_DOOR, SPRUCE_FENCE_DOOR, BIRCH_FENCE_DOOR, JUNGLE_FENCE_DOOR, ACACIA_FENCE_DOOR, DARK_OAK_FENCE_DOOR, CRIMSON_FENCE_DOOR, WARPED_FENCE_DOOR);
        registerBlock(REDSTONE_TAB, PICKAXE_MINEABLE, "iron_fence_door", IRON_FENCE_DOOR);

        //Item-less blocks
        registerBlockOnly(PICKAXE_MINEABLE, "iron_spike_trap_spikes", IRON_SPIKE_TRAP_SPIKES);
        registerBlockOnly(PICKAXE_MINEABLE, "gold_spike_trap_spikes", GOLD_SPIKE_TRAP_SPIKES);
        registerBlockOnly(PICKAXE_MINEABLE, "diamond_spike_trap_spikes", DIAMOND_SPIKE_TRAP_SPIKES);
        registerBlockOnly(PICKAXE_MINEABLE, "netherite_spike_trap_spikes", NETHERITE_SPIKE_TRAP_SPIKES);
        registerBlockOnly(PICKAXE_MINEABLE, "iron_ladder_piece", IRON_LADDER_PIECE);
    }
}
