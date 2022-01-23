package net.azagwen.atbyw.datagen.loot;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.azagwen.atbyw.main.AtbywMain;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class LootDatagen {
    private static final Map<Identifier, LootTable> LOOT_TABLES = new Object2ObjectOpenHashMap<>();

    //Used in net.azagwen.atbyw.mixin.datagen.LootManagerMixin
    public static void applyLoots(Map<Identifier, JsonElement> map, ImmutableMap.Builder<Identifier, LootTable> builder) {
        var lootCount = new AtomicInteger();
        LOOT_TABLES.forEach((identifier, lootTable) -> {
            if (!map.containsKey(identifier)) {
                builder.put(identifier, lootTable);
                lootCount.getAndIncrement();
            }
        });

        AtbywMain.LOGGER.info("Loaded {} additional loot table" + (lootCount.get() > 1 ? "s" : ""), lootCount);
    }

    public static void registerLoot(LootTable lootTable, Identifier identifier, String category) {
        var newIdentifier = new Identifier(identifier.getNamespace(), category + "/" + identifier.getPath());

        LOOT_TABLES.put(newIdentifier, lootTable);
    }

    public static void registerBlockLoot(LootTable lootTable, Identifier identifier) {
        registerLoot(lootTable, identifier, "blocks");
    }
}
