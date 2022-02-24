package net.azagwen.atbyw.block;

import net.azagwen.atbyw.block.extensions.AtbywWallBlock;
import net.azagwen.atbyw.block.shape.DirectionalShape;
import net.minecraft.block.*;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.shape.VoxelShape;
import java.util.Map;

public class CinderBlocksWallBlock extends AtbywWallBlock {
    private static final VoxelShape POST_SHAPE;
    private static final VoxelShape POST_SLAB_SHAPE;
    private static final DirectionalShape SLAB_SHAPE;
    private static final DirectionalShape LOW_SHAPE;
    private static final DirectionalShape TALL_SHAPE;
    private static final VoxelShape POST_COLLISION_SHAPE;
    private static final DirectionalShape LOW_COLLISION_SHAPE;
    private static final DirectionalShape TALL_COLLISION_SHAPE;

    public CinderBlocksWallBlock(Map<StringIdentifiable, Block> variantMap, StringIdentifiable variant, Settings settings) {
        super(variantMap, variant, settings);
        this.outlineShapeMap.set(this.getShapeMap(POST_SHAPE, POST_SLAB_SHAPE, LOW_SHAPE, TALL_SHAPE));
        this.collisionShapeMap.set(this.getShapeMap(POST_COLLISION_SHAPE, null, LOW_COLLISION_SHAPE, TALL_COLLISION_SHAPE));
    }

    static {
        POST_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
        POST_SLAB_SHAPE =  Block.createCuboidShape(3.0D, 16.0D, 3.0D , 13.0D, 18.0D, 13.0D);
        POST_COLLISION_SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 24.0D, 12.0D);
        SLAB_SHAPE = new DirectionalShape(4.0D, 12.0D, 0.0D , 12.0D, 14.0D, 8.0D);
        LOW_SHAPE = DirectionalShape.union(SLAB_SHAPE, new DirectionalShape(5.0D, 0.0D , 0.0D , 11.0D, 12.0D, 8.0D));
        TALL_SHAPE = new DirectionalShape(5.0D, 0.0D , 0.0D , 11.0D, 16.0D, 8.0D);
        LOW_COLLISION_SHAPE = new DirectionalShape(5.0D, 0.0D , 0.0D , 11.0D, 24.0D, 8.0D);
        TALL_COLLISION_SHAPE = new DirectionalShape(5.0D, 0.0D , 0.0D , 11.0D, 24.0D, 8.0D);
    }
}
