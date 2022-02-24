package net.azagwen.atbyw.block.stairs;

import net.azagwen.atbyw.block.Utils;
import net.azagwen.atbyw.block.extensions.AtbywStairsBlock;
import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Random;

public class DirtStairsBlock extends AtbywStairsBlock {

    public DirtStairsBlock(Block copiedBlock, FabricBlockSettings settings) {
        super(copiedBlock, settings);
    }

    private static boolean canHaveGrass(WorldView worldView, BlockPos pos) {
        return !Utils.simulateIsGrassCovered(worldView, pos);
    }

    private static boolean canBeSpreadTo(WorldView worldView, BlockPos pos) {
        return canHaveGrass(worldView, pos) && !worldView.getFluidState(pos.up()).isIn(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos.up()) >= 9) {
            for(int i = 0; i < 4; ++i) {
                var blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                var newState = world.getBlockState(pos);

                if (world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK) && canBeSpreadTo(world, pos)) {
                    world.setBlockState(pos, BuildingBlockRegistry.GRASS_BLOCK_STAIRS.getStateWithProperties(newState));
                }
                else if (world.getBlockState(blockPos).isOf(Blocks.MYCELIUM) && canBeSpreadTo(world, pos)) {
                    world.setBlockState(pos, BuildingBlockRegistry.MYCELIUM_STAIRS.getStateWithProperties(newState));
                }
            }
        }
    }
}
