package net.azagwen.atbyw.block.slab;

import net.azagwen.atbyw.block.extensions.AtbywBlock;
import net.azagwen.atbyw.block.state.AtbywProperties;
import net.azagwen.atbyw.block.state.PillarSlabType;
import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.Map;

//entire rewrite of the slab block because yes
public class PillarSlabBlock extends AtbywBlock implements Waterloggable {
    public static final EnumProperty<PillarSlabType> PILLAR_SLAB_TYPE;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape BOTTOM_SHAPE;
    protected static final VoxelShape TOP_SHAPE;

    public PillarSlabBlock(Map<StringIdentifiable, Block> variantMap, StringIdentifiable variant, Settings settings) {
        super(variantMap, variant, settings);
        this.setDefaultState(this.getDefaultState().with(PILLAR_SLAB_TYPE, PillarSlabType.BOTTOM_Y).with(WATERLOGGED, false));
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        var typeRotation = switch (state.get(PILLAR_SLAB_TYPE).getSlabType()) {
            case TOP -> switch (state.get(PILLAR_SLAB_TYPE)) {
                case TOP_X -> PillarSlabType.TOP_Z;
                case TOP_Z -> PillarSlabType.TOP_X;
                default -> PillarSlabType.TOP_Y;
            };
            case BOTTOM -> switch (state.get(PILLAR_SLAB_TYPE)) {
                case BOTTOM_X -> PillarSlabType.BOTTOM_Z;
                case BOTTOM_Z -> PillarSlabType.BOTTOM_X;
                default -> PillarSlabType.BOTTOM_Y;
            };
            case DOUBLE -> switch (state.get(PILLAR_SLAB_TYPE)) {
                case DOUBLE_BX_TX -> PillarSlabType.DOUBLE_BZ_TZ;
                case DOUBLE_BZ_TZ -> PillarSlabType.DOUBLE_BX_TX;
                case DOUBLE_BZ_TX -> PillarSlabType.DOUBLE_BX_TZ;
                case DOUBLE_BX_TZ -> PillarSlabType.DOUBLE_BZ_TX;
                case DOUBLE_BX_TY -> PillarSlabType.DOUBLE_BZ_TY;
                case DOUBLE_BZ_TY -> PillarSlabType.DOUBLE_BX_TY;
                case DOUBLE_BY_TX -> PillarSlabType.DOUBLE_BY_TZ;
                case DOUBLE_BY_TZ -> PillarSlabType.DOUBLE_BY_TX;
                default -> PillarSlabType.BOTTOM_Y;
            };
        };

        return switch(rotation) {
            case COUNTERCLOCKWISE_90, CLOCKWISE_90 -> state.with(PILLAR_SLAB_TYPE, typeRotation);
            default -> state;
        };
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(PILLAR_SLAB_TYPE).getSlabType()) {
            case DOUBLE -> VoxelShapes.fullCube();
            case TOP -> TOP_SHAPE;
            default -> BOTTOM_SHAPE;
        };
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var pos = ctx.getBlockPos();
        var state = ctx.getWorld().getBlockState(pos);
        var direction = ctx.getSide();
        var hitAxis = direction.getAxis();
        var fluidState = ctx.getWorld().getFluidState(pos);

        if (state.isOf(this)) {
            var hitType = state.get(PILLAR_SLAB_TYPE);
            var type = switch (hitType.getSlabType()) {
                case TOP -> PillarSlabType.getTypeFromAxis(hitType.getTopAxis(), hitAxis, SlabType.DOUBLE);
                case BOTTOM -> PillarSlabType.getTypeFromAxis(hitAxis, hitType.getBottomAxis(), SlabType.DOUBLE);
                default -> PillarSlabType.DOUBLE_BY_TY;
            };
            return state.with(PILLAR_SLAB_TYPE, type).with(WATERLOGGED, false);
        } else {
            var slabType = direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getHitPos().y - (double)pos.getY() > 0.5D)) ? SlabType.BOTTOM : SlabType.TOP;
            return this.getDefaultState().with(PILLAR_SLAB_TYPE, PillarSlabType.getTypeFromAxis(hitAxis, hitAxis, slabType)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        }
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        var itemStack = context.getStack();
        var slabType = state.get(PILLAR_SLAB_TYPE).getSlabType();
        if (slabType != SlabType.DOUBLE && itemStack.isOf(this.asItem())) {
            if (context.canReplaceExisting()) {
                var bl = context.getHitPos().y - (double)context.getBlockPos().getY() > 0.5D;
                var direction = context.getSide();
                if (slabType == SlabType.BOTTOM) {
                    return direction == Direction.UP || bl && direction.getAxis().isHorizontal();
                } else {
                    return direction == Direction.DOWN || !bl && direction.getAxis().isHorizontal();
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        return state.get(PILLAR_SLAB_TYPE).getSlabType() != SlabType.DOUBLE;
    }

    @Override
    public boolean canFillWithFluid(BlockView world, BlockPos pos, BlockState state, Fluid fluid) {
        return state.get(PILLAR_SLAB_TYPE).getSlabType() != SlabType.DOUBLE;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.getFluidTickScheduler().schedule(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        if (type == NavigationType.WATER) {
            return world.getFluidState(pos).isIn(FluidTags.WATER);
        }
        return false;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(PILLAR_SLAB_TYPE, WATERLOGGED);
    }

    static {
        PILLAR_SLAB_TYPE = AtbywProperties.PILLAR_SLAB_TYPE;
        WATERLOGGED = Properties.WATERLOGGED;
        BOTTOM_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        TOP_SHAPE = Block.createCuboidShape(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    }
}
