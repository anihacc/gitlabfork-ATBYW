package net.azagwen.atbyw.block.stairs;

import net.azagwen.atbyw.block.Utils;
import net.azagwen.atbyw.block.extensions.AtbywStairsBlock;
import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.azagwen.atbyw.block.registry.containers.AtbywBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Random;

public class NyliumStairsBlock extends AtbywStairsBlock {
    public static final AtbywBlockContainer NYLIUM_STAIRS = new AtbywBlockContainer();

    public NyliumStairsBlock(Block copiedBlock, Settings settings) {
        super(copiedBlock, settings);
        NYLIUM_STAIRS.add(this);
    }

    private static boolean stayAlive(WorldView world, BlockPos pos) {
        return !Utils.simulateIsGrassCovered(world, pos);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!stayAlive(world, pos)) {
            var newState = world.getBlockState(pos);
            world.setBlockState(pos, BuildingBlockRegistry.NETHERRACK_STAIRS.getStateWithProperties(newState));
        }
    }
}
