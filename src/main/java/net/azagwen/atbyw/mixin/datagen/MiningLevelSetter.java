package net.azagwen.atbyw.mixin.datagen;

import net.azagwen.atbyw.block.registry.AtbywBlocks;
import net.azagwen.atbyw.main.AtbywMain;
import net.azagwen.atbyw.util.AtbywUtils;
import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagGroup;
import net.minecraft.tag.TagGroupLoader;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(TagGroupLoader.class)
public class MiningLevelSetter {

    @Inject(method = "buildGroup", at = @At("HEAD"))
    private <T> void addToMiningLevelTags(Map<Identifier, Tag.Builder> tags, CallbackInfoReturnable<TagGroup<T>> cir) {
        for (var tag : tags.entrySet()) {
            this.appendToTag(tag, BlockTags.AXE_MINEABLE, AtbywBlocks.AXE_MINEABLE);
            this.appendToTag(tag, BlockTags.HOE_MINEABLE, AtbywBlocks.HOE_MINEABLE);
            this.appendToTag(tag, BlockTags.PICKAXE_MINEABLE, AtbywBlocks.PICKAXE_MINEABLE);
            this.appendToTag(tag, BlockTags.SHOVEL_MINEABLE, AtbywBlocks.SHOVEL_MINEABLE);
            this.appendToTag(tag, BlockTags.NEEDS_STONE_TOOL, AtbywBlocks.NEEDS_STONE_TOOL);
            this.appendToTag(tag, BlockTags.NEEDS_IRON_TOOL, AtbywBlocks.NEEDS_IRON_TOOL);
            this.appendToTag(tag, BlockTags.NEEDS_DIAMOND_TOOL, AtbywBlocks.NEEDS_DIAMOND_TOOL);
        }
    }

    private void appendToTag(Map.Entry<Identifier, Tag.Builder> tagEntry, Tag<Block> targetTag, List<Block> blocksToAdd) {
        if (tagEntry.getKey().equals(((Tag.Identified<Block>)targetTag).getId())) {
            var blockCount = 0;
            for (var block : blocksToAdd) {
                tagEntry.getValue().add(AtbywUtils.getBlockID(block), AtbywMain.ATBYW);
                blockCount++;
            }
            if (blockCount > 0) {
                AtbywMain.LOGGER.info("Added {} additional block" + (blockCount > 1 ? "s" : "") + " to " + ((Tag.Identified<Block>) targetTag).getId(), blockCount);
            }
        }
    }
}
