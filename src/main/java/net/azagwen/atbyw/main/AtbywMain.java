package net.azagwen.atbyw.main;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import net.azagwen.atbyw.block.entity.AtbywBlockEntityTypes;
import net.azagwen.atbyw.block.registry.AtbywBlocks;
import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.azagwen.atbyw.datagen.loot.BlockLootRegistry;
import net.azagwen.atbyw.datagen.recipe.registry.RecipeRegistry;
import net.azagwen.atbyw.dev_tools.AutoJsonWriter;
import net.azagwen.atbyw.group.AtbywItemGroup;
import net.azagwen.atbyw.item.AtbywItems;
import net.azagwen.atbyw.mod_interaction.AtbywModInteractions;
import net.azagwen.atbyw.util.AtbywUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.BiConsumer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AtbywMain implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Atbyw Main");
	public static final Logger D_LOGGER = LogManager.getLogger("Atbyw Debug");
	public static final String MINECRAFT = "minecraft";
	public static final String ATBYW = "atbyw";
	public static final String ATBYW_MI = "atbyw_mi";
	//Item group and its Sub-tabs
	public static ItemGroup ATBYW_GROUP;
	public static ArrayList<Item> BLOCKS_TAB = Lists.newArrayList();
	public static ArrayList<Item> DECO_TAB = Lists.newArrayList();
	public static ArrayList<Item> REDSTONE_TAB = Lists.newArrayList();
	public static ArrayList<Item> MISC_TAB = Lists.newArrayList();
	//Debug fields (used only for the client-side debug features)
	public static final Map<String, Boolean> DEBUG_FEATURES = Maps.newHashMap();
	public static List<BlockState> BLOCK_STATES = Lists.newArrayList();	//List of blocks to populate the debug world
	public static int X_SIDE_LENGTH;	//Width of the debug world grid
	public static int Z_SIDE_LENGTH;	//Length of the debug world grid

	//TODO: Fix and Investigate structure issues (on hold)

	public static Identifier id(String path, boolean isModInteraction) {
		var namespace = isModInteraction ? ATBYW_MI : ATBYW;
		return new Identifier(namespace, path);
	}

	public static Identifier id(String path) {
		return id(path, false);
	}

	public static boolean isModLoaded(String ModID) {
		return FabricLoader.getInstance().isModLoaded(ModID);
	}

	private void checkForDebugElement(JsonElement element, String name) {
		if (element.getAsString().equals(name)) {
			DEBUG_FEATURES.put(name, true);
		}
	}

	public void tryEnableDebug() {
		try {
			var client = MinecraftClient.getInstance();
			var file = new File(client.runDirectory, "config/atbyw.json");
			try {
				var reader = new JsonReader(new FileReader(file));
				var parser = new JsonParser();
				var json = parser.parse(reader).getAsJsonObject();

				if (json.has("enable_debug")) {
					DEBUG_FEATURES.clear();
					var debugObj = JsonHelper.getArray(json, "enable_debug");

					for (var element : debugObj) {
						this.checkForDebugElement(element, "redstone_cross");
						this.checkForDebugElement(element, "shroomstick");
						this.checkForDebugElement(element, "debug_world");
					}

					D_LOGGER.info("ATBYW Client-Side debug options have been enabled as specified in \"config/atbyw.json\"");
				}
			} catch (FileNotFoundException ignored) {
			}
		} catch (RuntimeException ignored) {
		}
	}

	public static boolean checkDebugEnabled(String key) {
		return DEBUG_FEATURES.containsKey(key) && DEBUG_FEATURES.get(key);
	}

	@Override
	public void onInitialize() {
		var assetWriter = new AutoJsonWriter();
		assetWriter.writeAll();

		this.tryEnableDebug();

		//Registries
		AtbywSoundEvents.init();
		AtbywItems.init();
		AtbywBlocks.init();
		AtbywStats.init();
		AtbywBlockEntityTypes.init();
		AtbywNetworking.init();
		RecipeRegistry.registerAll();
		BlockLootRegistry.registerAll();
		AtbywModInteractions.init();

		//Populate debug world with this mod's blocks (dev only)
		if (checkDebugEnabled("debug_world")) {
			for (var block : Registry.BLOCK.stream().toList()) {
				if (Registry.BLOCK.getId(block).getNamespace().equals(ATBYW)) {
					BLOCK_STATES.addAll(block.getStateManager().getStates());
				}
			}

			X_SIDE_LENGTH = MathHelper.ceil(MathHelper.sqrt((float)BLOCK_STATES.size()));
			Z_SIDE_LENGTH = MathHelper.ceil((float)BLOCK_STATES.size() / (float)X_SIDE_LENGTH);

			D_LOGGER.info("ATBYW Debug world replacement enabled.");
		}

		BiConsumer<String, Tag<Block>> consumer = (string, blockTag) -> {
			LOGGER.warn(string);
			for (var block : blockTag.values()) {
				if (AtbywUtils.getId(block).getNamespace().equals(ATBYW)) {
					LOGGER.info(block);
				}
			}
		};

		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
//			consumer.accept("AXE MINEABLES", BlockTags.AXE_MINEABLE);
//			consumer.accept("HOE MINEABLES", BlockTags.HOE_MINEABLE);
//			consumer.accept("PICKAXE MINEABLES", BlockTags.PICKAXE_MINEABLE);
//			consumer.accept("SHOVEL MINEABLES", BlockTags.SHOVEL_MINEABLE);
		});

		ATBYW_GROUP = new AtbywItemGroup(AtbywMain.id("atbyw"));

		var blockNumber = new AtomicInteger();
		for (var block : Registry.BLOCK.stream().toList()) {
			if (AtbywUtils.getId(block).getNamespace().equals(ATBYW)) {
				blockNumber.getAndIncrement();
			}
		}
		LOGGER.info("ATBYW registered {} blocks", blockNumber);
		LOGGER.info("ATBYW Inintiliazed");
	}
}
