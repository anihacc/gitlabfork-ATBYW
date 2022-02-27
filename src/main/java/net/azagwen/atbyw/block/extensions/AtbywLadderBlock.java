package net.azagwen.atbyw.block.extensions;

import net.azagwen.atbyw.block.registry.containers.AtbywBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.util.StringIdentifiable;

import javax.annotation.Nullable;
import java.util.Map;

public class AtbywLadderBlock extends LadderBlock {
    public static final AtbywBlockContainer ATBYW_LADDERS = new AtbywBlockContainer("atbyw_ladders");

    public AtbywLadderBlock(Settings settings) {
        super(settings);
        ATBYW_LADDERS.add(this);
    }

    public AtbywLadderBlock(Map<StringIdentifiable, Block> variantMap, @Nullable StringIdentifiable variant, Settings settings) {
        this(settings);
        if (variant != null) {
            variantMap.put(variant, this);
        }
    }

    public static BlockState transferState(BlockState newState, BlockState oldState) {
        return newState.with(FACING, oldState.get(FACING)).with(WATERLOGGED, oldState.get(WATERLOGGED));
    }
}
