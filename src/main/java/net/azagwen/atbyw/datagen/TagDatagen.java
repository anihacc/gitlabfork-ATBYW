package net.azagwen.atbyw.datagen;

import com.google.common.collect.Maps;
import net.azagwen.atbyw.block.registry.containers.AtbywContainer;
import net.azagwen.atbyw.main.AtbywMain;
import net.azagwen.atbyw.util.AtbywUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

import java.util.Map;

public class TagDatagen {
    public static Map<AtbywContainer<?>, Identifier> APPEND_TRACKER = Maps.newLinkedHashMap();

    @SafeVarargs
    public static <T> void appendToTag(Identifier tagId, Tag.Builder tagBuilder, Tag<T> targetTag, AtbywContainer<T>... objectsToAdd) {
        var tag = (Tag.Identified<T>) targetTag;
        var tempContainer = new AtbywContainer<T>("temp_container", objectsToAdd[0].type);

        for (var container : objectsToAdd) {
            tempContainer.addAll(container);
            for (var object : container){
                if (object instanceof Block block) {
                    addToTag(tagId, tagBuilder, tag, block);
                }
                if (object instanceof Item item) {
                    addToTag(tagId, tagBuilder, tag, item);
                }
            }
        }
        if (tagId.equals(tag.getId())) {
            APPEND_TRACKER.put(tempContainer, tag.getId());
        }
    }

    public static void addToTag(Identifier tagId, Tag.Builder tagBuilder, Tag.Identified<?> tag, Object objectToAdd) {
        if (tagId.equals(tag.getId())) {
            tagBuilder.add(AtbywUtils.getId(objectToAdd), AtbywMain.ATBYW);
        }
    }

    public static String buildLogMessage(String type, int count) {
        var message = "Added {} additional %1s%2s to {}";
        return message.replace("%1s", type).replace("%2s", count > 1 ? "s" : "");
    }

    public static void printAppendLogs() {
        TagDatagen.APPEND_TRACKER.forEach((container, identifier) -> {
            if (container.size() > 0){
                AtbywMain.LOGGER.info(TagDatagen.buildLogMessage(container.type, container.size()), container.size(), identifier);
            }
        });
    }
}
