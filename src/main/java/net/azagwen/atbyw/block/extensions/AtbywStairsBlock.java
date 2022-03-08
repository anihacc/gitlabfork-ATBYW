package net.azagwen.atbyw.block.extensions;

import net.azagwen.atbyw.containers.AtbywBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;
import java.util.Map;

public class AtbywStairsBlock extends StairsBlock {
    public static final AtbywBlockContainer ATBYW_STAIRS = new AtbywBlockContainer("atbyw_stairs");

    public AtbywStairsBlock(Block copiedBlock, Settings settings) {
        super(copiedBlock.getDefaultState(), settings);
        ATBYW_STAIRS.add(this);
    }


    public AtbywStairsBlock(Map<StringIdentifiable, Block> variantMap, @Nullable StringIdentifiable variant, Block copiedBlock, Settings settings) {
        this(copiedBlock, settings);
        if (variant != null) {
            variantMap.put(variant, this);
        }
    }

    public static BlockState transferState(BlockState newState, BlockState oldState) {
        return newState.with(FACING, oldState.get(FACING)).with(SHAPE, oldState.get(SHAPE)).with(HALF, oldState.get(HALF)).with(WATERLOGGED, oldState.get(WATERLOGGED));
    }
}
