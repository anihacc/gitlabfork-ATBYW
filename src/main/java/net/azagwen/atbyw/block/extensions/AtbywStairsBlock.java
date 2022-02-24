package net.azagwen.atbyw.block.extensions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class AtbywStairsBlock extends StairsBlock {

    public AtbywStairsBlock(Block copiedBlock, Settings settings) {
        super(copiedBlock.getDefaultState(), settings);
    }

    public AtbywStairsBlock(Set<Block> set, Block copiedBlock, Settings settings) {
        super(copiedBlock.getDefaultState(), settings);
        set.add(this);
    }

    public AtbywStairsBlock(Map<StringIdentifiable, Block> variantMap, @Nullable StringIdentifiable variant, Block copiedBlock, Settings settings) {
        super(copiedBlock.getDefaultState(), settings);
        if (variant != null) {
            variantMap.put(variant, this);
        }
    }

    public static BlockState transferState(BlockState newState, BlockState oldState) {
        return newState.with(FACING, oldState.get(FACING)).with(SHAPE, oldState.get(SHAPE)).with(HALF, oldState.get(HALF)).with(WATERLOGGED, oldState.get(WATERLOGGED));
    }
}
