package net.azagwen.atbyw.block.slab;

import net.azagwen.atbyw.block.Utils;
import net.azagwen.atbyw.block.extensions.AtbywSlabBlock;
import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.azagwen.atbyw.containers.AtbywBlockContainer;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.SlabType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.Random;

public class NyliumSlabBlock extends AtbywSlabBlock {
    public static final AtbywBlockContainer NYLIUM_SLABS = new AtbywBlockContainer("nylium_slabs");

    public NyliumSlabBlock(Settings settings) {
        super(settings);
        NYLIUM_SLABS.add(this);
    }

    private static boolean stayAlive(BlockState state, WorldView world, BlockPos pos) {
        return !Utils.simulateIsGrassCovered(world, pos) || state.get(TYPE) == SlabType.BOTTOM;
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!stayAlive(state, world, pos)) {
            var newState = world.getBlockState(pos);
            world.setBlockState(pos, BuildingBlockRegistry.NETHERRACK_SLAB.getStateWithProperties(newState));
        }
    }
}
