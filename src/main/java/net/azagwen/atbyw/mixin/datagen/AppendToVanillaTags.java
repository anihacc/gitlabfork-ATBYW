package net.azagwen.atbyw.mixin.datagen;

import com.google.common.reflect.TypeToken;
import net.azagwen.atbyw.block.extensions.AtbywLadderBlock;
import net.azagwen.atbyw.block.extensions.AtbywSlabBlock;
import net.azagwen.atbyw.block.extensions.AtbywStairsBlock;
import net.azagwen.atbyw.block.extensions.AtbywWallBlock;
import net.azagwen.atbyw.block.registry.AtbywBlocks;
import net.azagwen.atbyw.block.slab.NyliumSlabBlock;
import net.azagwen.atbyw.block.stairs.NyliumStairsBlock;
import net.azagwen.atbyw.datagen.TagDatagen;
import net.azagwen.atbyw.main.AtbywMain;
import net.minecraft.tag.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

@Mixin(TagGroupLoader.class)
public class AppendToVanillaTags {

    @Inject(method = "buildGroup", at = @At("HEAD"))
    private <T> void appendToTag(Map<Identifier, Tag.Builder> tags, CallbackInfoReturnable<TagGroup<T>> cir) {
        tags.forEach((identifier, builder) -> {
            TagDatagen.appendToTag(identifier, builder, BlockTags.AXE_MINEABLE, AtbywBlocks.AXE_MINEABLE);
            TagDatagen.appendToTag(identifier, builder, BlockTags.HOE_MINEABLE, AtbywBlocks.HOE_MINEABLE);
            TagDatagen.appendToTag(identifier, builder, BlockTags.PICKAXE_MINEABLE, AtbywBlocks.PICKAXE_MINEABLE);
            TagDatagen.appendToTag(identifier, builder, BlockTags.SHOVEL_MINEABLE, AtbywBlocks.SHOVEL_MINEABLE);
            TagDatagen.appendToTag(identifier, builder, BlockTags.NEEDS_STONE_TOOL, AtbywBlocks.NEEDS_STONE_TOOL);
            TagDatagen.appendToTag(identifier, builder, BlockTags.NEEDS_IRON_TOOL, AtbywBlocks.NEEDS_IRON_TOOL);
            TagDatagen.appendToTag(identifier, builder, BlockTags.NEEDS_DIAMOND_TOOL, AtbywBlocks.NEEDS_DIAMOND_TOOL);
            TagDatagen.appendToTag(identifier, builder, BlockTags.STAIRS, AtbywStairsBlock.ATBYW_STAIRS);
            TagDatagen.appendToTag(identifier, builder, BlockTags.SLABS, AtbywSlabBlock.ATBYW_SLABS);
            TagDatagen.appendToTag(identifier, builder, BlockTags.WALLS, AtbywWallBlock.ATBYW_WALLS);
            TagDatagen.appendToTag(identifier, builder, BlockTags.CLIMBABLE, AtbywLadderBlock.ATBYW_LADDERS);
            TagDatagen.appendToTag(identifier, builder, BlockTags.NYLIUM, NyliumStairsBlock.NYLIUM_STAIRS, NyliumSlabBlock.NYLIUM_SLABS);

            TagDatagen.appendToTag(identifier, builder, ItemTags.STAIRS, AtbywStairsBlock.ATBYW_STAIRS.getAsItemContainer());
            TagDatagen.appendToTag(identifier, builder, ItemTags.SLABS, AtbywSlabBlock.ATBYW_SLABS.getAsItemContainer());
            TagDatagen.appendToTag(identifier, builder, ItemTags.WALLS, AtbywWallBlock.ATBYW_WALLS.getAsItemContainer());

        });
    }

    //TODO: find out why this shit breaks the whole resource loader when used (lowest priority of all, I don't want to touch this)
    private <C, T> boolean checkTagType(Class<C> clazz, Tag<T> tag) {
        var tagType = ((ParameterizedType) tag.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        var tagClass = TypeToken.of(tagType).getRawType();
        var condition = clazz.isAssignableFrom(tagClass);
        if (condition) {
            System.out.println(clazz.getName() + " matches type " + tagClass.getName() + " for " + ((Tag.Identified<?>) tag).getId());
        }
        return condition;
    }
}
