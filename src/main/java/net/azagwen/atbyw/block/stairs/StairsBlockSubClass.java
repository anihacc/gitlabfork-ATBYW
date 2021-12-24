package net.azagwen.atbyw.block.stairs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StairsBlockSubClass extends StairsBlock {

    public StairsBlockSubClass(Block copiedBlock, Settings settings) {
        super(copiedBlock.getDefaultState(), settings);
    }

    public static BlockState transferState(BlockState newState, BlockState oldState) {
        return newState.with(FACING, oldState.get(FACING)).with(SHAPE, oldState.get(SHAPE)).with(HALF, oldState.get(HALF));
    }
}
