package net.azagwen.atbyw.mod_interaction;

import com.google.common.collect.Lists;

import java.util.List;

import static net.azagwen.atbyw.main.AtbywMain.isModLoaded;

/**
 * This class is meant to register Mod interaction features for ATBYW
 */
public class AtbywModInteractions {
    public static List<AtbywMiRegistry> MOD_INTERACTION_BULDING_BLOCKS = Lists.newArrayList();
    public static List<AtbywMiRegistry> MOD_INTERACTION_DECORATION_BLOCKS = Lists.newArrayList();
    public static List<AtbywMiRegistry> MOD_INTERACTION_REDSTONE_BLOCKS = Lists.newArrayList();
    public static List<String> MOD_INTERACTION_NAMESPACES = Lists.newArrayList();

    protected static String registerMiNamespace(String namespace) {
        MOD_INTERACTION_NAMESPACES.add(namespace);
        return namespace;
    }

    public static boolean isModInteractionEnabled() {
        var result = false;
        for (var namespace : MOD_INTERACTION_NAMESPACES) {
            result = result || isModLoaded(namespace);
        }
        return result;
    }

    private static void registerBuildingBlocks() {
        MOD_INTERACTION_BULDING_BLOCKS.forEach(AtbywMiRegistry::register);
    }

    private static void registerDecorationBlocks() {
        MOD_INTERACTION_DECORATION_BLOCKS.forEach(AtbywMiRegistry::register);
    }

    private static void registerRedstoneBlocks() {
        MOD_INTERACTION_REDSTONE_BLOCKS.forEach(AtbywMiRegistry::register);
    }

    public static void init() {
        registerBuildingBlocks();
        registerDecorationBlocks();
        registerRedstoneBlocks();
    }
}
