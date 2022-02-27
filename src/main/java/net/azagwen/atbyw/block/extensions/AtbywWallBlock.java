package net.azagwen.atbyw.block.extensions;

import com.google.common.collect.ImmutableMap;
import net.azagwen.atbyw.block.registry.containers.AtbywBlockContainer;
import net.azagwen.atbyw.block.shape.DirectionalShape;
import net.azagwen.atbyw.block.state.AtbywProperties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.WallBlock;
import net.minecraft.block.enums.WallShape;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AtbywWallBlock extends WallBlock {
    public static final AtbywBlockContainer ATBYW_WALLS = new AtbywBlockContainer();
    public static final BooleanProperty POST_SLAB;
    private static final VoxelShape POST_SHAPE;
    private static final DirectionalShape LOW_SHAPE;
    private static final DirectionalShape TALL_SHAPE;
    private static final DirectionalShape LOW_COLLISION_SHAPE;
    private static final DirectionalShape TALL_COLLISION_SHAPE;
    private static final VoxelShape POST_COLLISION_SHAPE;
    protected final AtomicReference<Map<BlockState, VoxelShape>> outlineShapeMap = new AtomicReference<>();
    protected final AtomicReference<Map<BlockState, VoxelShape>> collisionShapeMap = new AtomicReference<>();

    public AtbywWallBlock(Settings settings) {
        super(settings);
        this.setDefaultState(super.getDefaultState().with(POST_SLAB, true));
        this.outlineShapeMap.set(this.getShapeMap(POST_SHAPE, null, LOW_SHAPE, TALL_SHAPE));
        this.collisionShapeMap.set(this.getShapeMap(POST_COLLISION_SHAPE, null, LOW_COLLISION_SHAPE, TALL_COLLISION_SHAPE));
        ATBYW_WALLS.add(this);
    }

    public AtbywWallBlock(Map<StringIdentifiable, Block> variantMap, StringIdentifiable variant, Settings settings) {
        this(settings);
        if (variant != null) {
            variantMap.put(variant, this);
        }
    }

    private static VoxelShape appendShapes(VoxelShape postShape, WallShape wallShape, VoxelShape lowSideShape, VoxelShape tallSideShape) {
        if (wallShape == WallShape.TALL) {
            return VoxelShapes.union(postShape, tallSideShape);
        } else {
            return wallShape == WallShape.LOW ? VoxelShapes.union(postShape, lowSideShape) : postShape;
        }
    }

    protected Map<BlockState, VoxelShape> getShapeMap(VoxelShape post, @Nullable VoxelShape slab, DirectionalShape low, DirectionalShape tall) {
        var builder = ImmutableMap.<BlockState, VoxelShape>builder();

        /* Defines what shapes to use depending on "shape" block states.
        *  "shape" block states define the direction and height of the wall.
        */
        for (var isUp : UP.getValues()) {
            for (var hasSlab : POST_SLAB.getValues()) {
                for (var eastShape : EAST_SHAPE.getValues()) {
                    for (var northShape : NORTH_SHAPE.getValues()) {
                        for (var westShape : WEST_SHAPE.getValues()) {
                            for (var southShape : SOUTH_SHAPE.getValues()) {
                                var SHAPE_EMPTY = VoxelShapes.empty();
                                SHAPE_EMPTY = appendShapes(SHAPE_EMPTY, eastShape, low.east, tall.east);
                                SHAPE_EMPTY = appendShapes(SHAPE_EMPTY, westShape, low.west, tall.west);
                                SHAPE_EMPTY = appendShapes(SHAPE_EMPTY, northShape, low.north, tall.north);
                                SHAPE_EMPTY = appendShapes(SHAPE_EMPTY, southShape, low.south, tall.south);

                                var SHAPE_POST = post;
                                if (slab != null) {
                                    SHAPE_POST = VoxelShapes.union(post, slab);
                                }

                                if (isUp) {
                                    if (hasSlab) {
                                        SHAPE_EMPTY = VoxelShapes.union(SHAPE_EMPTY, SHAPE_POST);
                                    } else {
                                        SHAPE_EMPTY = VoxelShapes.union(SHAPE_EMPTY, post);
                                    }
                                }

                                var blockState = this.getDefaultState().with(UP, isUp).with(POST_SLAB, hasSlab).with(EAST_SHAPE, eastShape).with(WEST_SHAPE, westShape).with(NORTH_SHAPE, northShape).with(SOUTH_SHAPE, southShape);
                                builder.put(blockState.with(WATERLOGGED, false), SHAPE_EMPTY);
                                builder.put(blockState.with(WATERLOGGED, true), SHAPE_EMPTY);
                            }
                        }
                    }
                }
            }
        }

        return builder.build();
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var superState = super.getPlacementState(ctx);
        var world = ctx.getWorld();
        var pos = ctx.getBlockPos();
        return superState.with(POST_SLAB, world.getBlockState(pos.up()).canReplace(ctx));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return outlineShapeMap.get().get(state);
    }

    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return this.collisionShapeMap.get().get(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        var superState = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        return superState.with(POST_SLAB, world.getBlockState(pos.up()).getMaterial().isReplaceable());
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POST_SLAB);
    }

    static {
        POST_SLAB = AtbywProperties.POST_SLAB;

        POST_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        LOW_SHAPE = new DirectionalShape(    5.0D, 0.0D, 0.0D, 11.0D, 14.0D, 8.0D);
        TALL_SHAPE = new DirectionalShape(   5.0D, 0.0D, 0.0D, 11.0D, 16.0D, 8.0D);

        POST_COLLISION_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 24.0D, 12.0D);
        LOW_COLLISION_SHAPE = new DirectionalShape(    5.0D, 0.0D, 0.0D, 11.0D, 24.0D, 8.0D);
        TALL_COLLISION_SHAPE = new DirectionalShape(   5.0D, 0.0D, 0.0D, 11.0D, 24.0D, 8.0D);
    }
}
