package net.azagwen.atbyw.block;

import net.azagwen.atbyw.block.extensions.AtbywBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Stainable;
import net.minecraft.util.DyeColor;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class ShatteredGlassBlock extends AtbywBlock implements Stainable {
    private final Block copiedBlock;
    private final DyeColor color;

    public ShatteredGlassBlock(Map<StringIdentifiable, Block> variantMap, @Nullable DyeColor variant, Block copiedBlock, Settings settings) {
        super(variantMap, variant, settings);
        this.copiedBlock = copiedBlock;
        this.color = variant;
    }

    @Override
    public DyeColor getColor() {
        return color;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState stateFrom, Direction direction) {
        var isBlockCullable = stateFrom.isOf(this) || stateFrom.isOf(this.copiedBlock);

        return isBlockCullable || super.isSideInvisible(state, stateFrom, direction);
    }

    public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0F;
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }
}
