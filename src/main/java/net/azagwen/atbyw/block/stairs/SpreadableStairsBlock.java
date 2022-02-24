package net.azagwen.atbyw.block.stairs;

import net.azagwen.atbyw.block.Utils;
import net.azagwen.atbyw.block.extensions.AtbywStairsBlock;
import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Random;

public class SpreadableStairsBlock extends AtbywStairsBlock {
    private final Block fullBlockEquivalent;

    public SpreadableStairsBlock(Block copiedBlock, Block fullBlockEquivalent, Settings settings) {
        super(copiedBlock, settings);
        this.fullBlockEquivalent = fullBlockEquivalent;
    }

    private static boolean canSurvive(WorldView worldView, BlockPos pos) {
        return !Utils.simulateIsGrassCovered(worldView, pos);
    }

    private static boolean canSpread(BlockState state, WorldView worldView, BlockPos pos) {
        var blockPos = pos.up();

        return canSurvive(worldView, pos) && (!worldView.getFluidState(blockPos).isIn(FluidTags.WATER) || !state.get(WATERLOGGED));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(world, pos)) {
            var oldState = world.getBlockState(pos);

            world.setBlockState(pos, BuildingBlockRegistry.DIRT_STAIRS.getStateWithProperties(oldState));
        }
        else  if (world.getLightLevel(pos.up()) >= 9) {
            for(int i = 0; i < 4; ++i) {
                var blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                var newState = world.getBlockState(blockPos);

                if (world.getBlockState(blockPos).isOf(BuildingBlockRegistry.DIRT_STAIRS) && canSpread(state, world, blockPos)) {
                    world.setBlockState(blockPos, this.getStateWithProperties(newState));
                }
                else if (world.getBlockState(blockPos).isOf(BuildingBlockRegistry.DIRT_SLAB) && canSpread(state, world, blockPos)) {
                    if (this == BuildingBlockRegistry.GRASS_BLOCK_STAIRS)
                        world.setBlockState(blockPos, BuildingBlockRegistry.GRASS_BLOCK_SLAB.getStateWithProperties(newState));
                    else if (this == BuildingBlockRegistry.MYCELIUM_STAIRS)
                        world.setBlockState(blockPos, BuildingBlockRegistry.GRASS_BLOCK_SLAB.getStateWithProperties(newState));
                }
                else if (world.getBlockState(blockPos).isOf(Blocks.DIRT) && canSpread(state, world, blockPos)) {
                    world.setBlockState(blockPos, this.fullBlockEquivalent.getDefaultState());
                }
            }
        }
    }
}
