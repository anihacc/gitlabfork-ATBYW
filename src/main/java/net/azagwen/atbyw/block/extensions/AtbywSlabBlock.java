package net.azagwen.atbyw.block.extensions;

import net.azagwen.atbyw.block.registry.containers.AtbywBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class AtbywSlabBlock extends SlabBlock {
    public static final AtbywBlockContainer ATBYW_SLABS = new AtbywBlockContainer("atbyw_slabs");

    public AtbywSlabBlock(Settings settings) {
        super(settings);
        ATBYW_SLABS.add(this);
    }

    public AtbywSlabBlock(Map<StringIdentifiable, Block> variantMap, @Nullable StringIdentifiable variant, Settings settings) {
        this(settings);
        if (variant != null) {
            variantMap.put(variant, this);
        }
    }

    public static BlockState transferState(BlockState newState, BlockState oldState) {
        return newState.with(TYPE, oldState.get(TYPE)).with(WATERLOGGED, oldState.get(WATERLOGGED));
    }
}
