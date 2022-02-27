package net.azagwen.atbyw.mixin.datagen;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import net.azagwen.atbyw.block.extensions.AtbywLadderBlock;
import net.azagwen.atbyw.block.extensions.AtbywSlabBlock;
import net.azagwen.atbyw.block.extensions.AtbywStairsBlock;
import net.azagwen.atbyw.block.extensions.AtbywWallBlock;
import net.azagwen.atbyw.block.registry.AtbywBlocks;
import net.azagwen.atbyw.block.registry.containers.AtbywBlockContainer;
import net.azagwen.atbyw.block.slab.NyliumSlabBlock;
import net.azagwen.atbyw.block.stairs.NyliumStairsBlock;
import net.azagwen.atbyw.main.AtbywMain;
import net.azagwen.atbyw.util.AtbywUtils;
import net.fabricmc.mappings.model.CommentEntry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Random;

@Mixin(TagGroupLoader.class)
public class AppendToVanillaTags {

    @Inject(method = "buildGroup", at = @At("HEAD"))
    private <T> void appendToTag(Map<Identifier, Tag.Builder> tags, CallbackInfoReturnable<TagGroup<T>> cir) {
        for (var tag : tags.entrySet()) {
            this.appendToTag(tag, BlockTags.AXE_MINEABLE, AtbywBlocks.AXE_MINEABLE);
            this.appendToTag(tag, BlockTags.HOE_MINEABLE, AtbywBlocks.HOE_MINEABLE);
            this.appendToTag(tag, BlockTags.PICKAXE_MINEABLE, AtbywBlocks.PICKAXE_MINEABLE);
            this.appendToTag(tag, BlockTags.SHOVEL_MINEABLE, AtbywBlocks.SHOVEL_MINEABLE);
            this.appendToTag(tag, BlockTags.NEEDS_STONE_TOOL, AtbywBlocks.NEEDS_STONE_TOOL);
            this.appendToTag(tag, BlockTags.NEEDS_IRON_TOOL, AtbywBlocks.NEEDS_IRON_TOOL);
            this.appendToTag(tag, BlockTags.NEEDS_DIAMOND_TOOL, AtbywBlocks.NEEDS_DIAMOND_TOOL);
            this.appendToTag(tag, BlockTags.STAIRS, AtbywStairsBlock.ATBYW_STAIRS);
            this.appendToTag(tag, ItemTags.STAIRS, AtbywStairsBlock.ATBYW_STAIRS);
            this.appendToTag(tag, BlockTags.SLABS, AtbywSlabBlock.ATBYW_SLABS);
            this.appendToTag(tag, BlockTags.WALLS, AtbywWallBlock.ATBYW_WALLS);
            this.appendToTag(tag, BlockTags.CLIMBABLE, AtbywLadderBlock.ATBYW_LADDERS);
            this.appendToTag(tag, BlockTags.NYLIUM, NyliumStairsBlock.NYLIUM_STAIRS, NyliumSlabBlock.NYLIUM_SLABS);
        }
    }

    //TODO: find a pretty implementation for universal tag appending VVVV

    private <C, T> boolean checkTagType(Class<C> clazz, Tag<T> tag) {
        var tagType = ((ParameterizedType) tag.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        var tagClass = TypeToken.of(tagType).getRawType();
        var condition = tagClass.isAssignableFrom(clazz);
        if (condition) {
            System.out.println(clazz.getName() + " matches type " + tagClass.getName() + " for " + ((Tag.Identified<?>) tag).getId());
        }
        return condition;
    }

    private <T> void appendToTag(Map.Entry<Identifier, Tag.Builder> tagEntry, Tag<T> targetTag, AtbywBlockContainer... blocksToAdd) {
//        this.checkTagType(Item.class, targetTag);
        var tag = (Tag.Identified<?>) targetTag;
        if (tagEntry.getKey().equals(tag.getId())) {
            var blockCount = 0;
            for (var container : blocksToAdd) {
                for (var object : container){
                    tagEntry.getValue().add(AtbywUtils.getId(object), AtbywMain.ATBYW);
                    blockCount++;
                }
            }
            if (blockCount > 0) {
                AtbywMain.LOGGER.info("Added {} additional block" + (blockCount > 1 ? "s" : "") + " to {}", blockCount, tag.getId());
            }
        }
    }
}
