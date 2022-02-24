package net.azagwen.atbyw.block.extensions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class AtbywLadderBlock extends LadderBlock {

    public AtbywLadderBlock(Settings settings) {
        super(settings);
    }

    public AtbywLadderBlock(Set<Block> set, Settings settings) {
        super(settings);
        set.add(this);
    }

    public AtbywLadderBlock(Map<StringIdentifiable, Block> variantMap, @Nullable StringIdentifiable variant, Settings settings) {
        super(settings);
        if (variant != null) {
            variantMap.put(variant, this);
        }
    }

    public static BlockState transferState(BlockState newState, BlockState oldState) {
        return newState.with(FACING, oldState.get(FACING)).with(WATERLOGGED, oldState.get(WATERLOGGED));
    }
}
