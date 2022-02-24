package net.azagwen.atbyw.block.extensions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.StairsBlock;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class AtbywPillarBlock extends PillarBlock {

    public AtbywPillarBlock(Settings settings) {
        super(settings);
    }

    public AtbywPillarBlock(Set<Block> set, Settings settings) {
        super(settings);
        set.add(this);
    }

    public AtbywPillarBlock(Map<StringIdentifiable, Block> variantMap, @Nullable StringIdentifiable variant, Settings settings) {
        super(settings);
        if (variant != null) {
            variantMap.put(variant, this);
        }
    }

    public static BlockState transferState(BlockState newState, BlockState oldState) {
        return newState.with(AXIS, oldState.get(AXIS));
    }
}
