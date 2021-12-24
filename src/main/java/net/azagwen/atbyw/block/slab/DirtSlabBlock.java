package net.azagwen.atbyw.block.slab;

import net.azagwen.atbyw.block.Utils;
import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Random;

public class DirtSlabBlock extends SlabBlock {

    public DirtSlabBlock(FabricBlockSettings settings) {
        super(settings);
    }

    private static boolean canHaveGrass(BlockState state, WorldView worldView, BlockPos pos) {
        return !Utils.simulateIsGrassCovered(worldView, pos) || state.get(TYPE) == SlabType.BOTTOM;
    }

    private static boolean canBeSpreadTo(BlockState state, WorldView worldView, BlockPos pos) {
        return canHaveGrass(state, worldView, pos) && !worldView.getFluidState(pos.up()).isIn(FluidTags.WATER);
    }


    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.getLightLevel(pos.up()) >= 9) {
            for(int i = 0; i < 4; ++i) {
                var blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(3) - 1, random.nextInt(3) - 1);
                var newState = world.getBlockState(pos);

                if (world.getBlockState(blockPos).isOf(Blocks.GRASS_BLOCK) && canBeSpreadTo(state, world, pos)) {
                        world.setBlockState(pos, BuildingBlockRegistry.GRASS_BLOCK_SLAB.getStateWithProperties(newState));
                }
                else if (world.getBlockState(blockPos).isOf(Blocks.MYCELIUM) && canBeSpreadTo(state, world, pos)) {
                        world.setBlockState(pos, BuildingBlockRegistry.MYCELIUM_SLAB.getStateWithProperties(newState));
                }

            }
        }
    }
}
