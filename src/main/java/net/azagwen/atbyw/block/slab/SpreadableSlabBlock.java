package net.azagwen.atbyw.block.slab;

import net.azagwen.atbyw.block.Utils;
import net.azagwen.atbyw.block.extensions.AtbywSlabBlock;
import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Random;

public class SpreadableSlabBlock extends AtbywSlabBlock {
    private final Block fullBlockEquivalent;

    public SpreadableSlabBlock(Block copiedBlock, Settings settings) {
        super(settings);
        this.fullBlockEquivalent = copiedBlock;
    }

    private static boolean canSurvive(BlockState state, WorldView worldView, BlockPos pos) {
        return !Utils.simulateIsGrassCovered(worldView, pos) || state.get(TYPE) == SlabType.BOTTOM;
    }

    private static boolean canSpread(BlockState state, WorldView worldView, BlockPos pos) {
        var upPos = pos.up();

        return !Utils.simulateIsGrassCovered(worldView, pos) && (!worldView.getFluidState(upPos).isIn(FluidTags.WATER) || !state.get(WATERLOGGED));
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(state, world, pos)) {
            var oldState = world.getBlockState(pos);

            world.setBlockState(pos, BuildingBlockRegistry.DIRT_SLAB.getStateWithProperties(oldState));
        }
        else if (world.getLightLevel(pos.up()) >= 9 || (state.get(TYPE) == SlabType.BOTTOM)) {
            for(int i = 0; i < 4; ++i) {
                var blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                var newState = world.getBlockState(blockPos);

                if (world.getBlockState(blockPos).isOf(BuildingBlockRegistry.DIRT_SLAB) && canSpread(state, world, blockPos)) {
                    world.setBlockState(blockPos, this.getStateWithProperties(newState));
                }
                else if (world.getBlockState(blockPos).isOf(BuildingBlockRegistry.DIRT_STAIRS) && canSpread(state, world, blockPos)) {
                    if (this == BuildingBlockRegistry.GRASS_BLOCK_SLAB)
                        world.setBlockState(blockPos, BuildingBlockRegistry.GRASS_BLOCK_STAIRS.getStateWithProperties(newState));
                    else if (this == BuildingBlockRegistry.MYCELIUM_SLAB)
                        world.setBlockState(blockPos, BuildingBlockRegistry.MYCELIUM_STAIRS.getStateWithProperties(newState));
                }
                else if (world.getBlockState(blockPos).isOf(Blocks.DIRT) && canSpread(state, world, blockPos)) {
                    world.setBlockState(blockPos, this.fullBlockEquivalent.getDefaultState());
                }
            }
        }
    }
}
