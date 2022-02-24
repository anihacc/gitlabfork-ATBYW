package net.azagwen.atbyw.block.shape;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;

import java.util.Map;
import java.util.Objects;

public class AxisShape {
    private final double minRadius;
    private final double minDepth;
    private final double maxRadius;
    private final double maxDepth;
    private final double minDepthMirror;
    private final double maxDepthMirror;

    public AxisShape(double minRadius, double minDepth, double maxRadius, double maxDepth) {
        this.minRadius = minRadius;
        this.minDepth = minDepth;
        this.maxRadius = maxRadius;
        this.maxDepth = maxDepth;
        this.minDepthMirror = Math.abs(maxDepth - 16);
        this.maxDepthMirror = Math.abs(minDepth - 16);
    }

    public VoxelShape xShape(boolean isWest) {
        var eastShape = Block.createCuboidShape(this.minDepthMirror, this.minRadius, this.minRadius, this.maxDepthMirror, this.maxRadius, this.maxRadius);
        var westShape = Block.createCuboidShape(this.minDepth, this.minRadius, this.minRadius, this.maxDepth, this.maxRadius, this.maxRadius);

        return isWest ? westShape : eastShape;
    }

    public VoxelShape yShape(boolean isDown) {
        var upShape = Block.createCuboidShape(this.minRadius, this.minDepthMirror, this.minRadius, this.maxRadius, this.maxDepthMirror, this.maxRadius);
        var downShape = Block.createCuboidShape(this.minRadius, this.minDepth, this.minRadius, this.maxRadius, this.maxDepth, this.maxRadius);

        return isDown ? downShape : upShape;
    }

    public VoxelShape zShape(boolean isSouth) {
        var northShape = Block.createCuboidShape(this.minRadius, this.minRadius, this.minDepth, this.maxRadius, this.maxRadius, this.maxDepth);
        var southShape = Block.createCuboidShape(this.minRadius, this.minRadius, this.minDepthMirror, this.maxRadius, this.maxRadius, this.maxDepthMirror);

        return isSouth ? southShape : northShape;
    }

    public VoxelShape getFromDirection(Direction direction) {
        return switch (direction) {
            case UP -> this.yShape(false);
            case DOWN -> this.yShape(true);
            case NORTH -> this.zShape(false);
            case SOUTH -> this.zShape(true);
            case EAST -> this.xShape(false);
            case WEST -> this.xShape(true);
        };
    }

    public Map<Direction, VoxelShape> getAsDirectionShapeMap() {
        var map = Maps.<Direction, VoxelShape>newHashMap();
        for (var dir : Direction.values()) {
            map.put(dir, this.getFromDirection(dir));
        }
        return map;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        var that = (AxisShape) obj;
        var compareMinRadius = Double.doubleToLongBits(this.minRadius) == Double.doubleToLongBits(that.minRadius);
        var compareMaxRadius = Double.doubleToLongBits(this.maxRadius) == Double.doubleToLongBits(that.maxRadius);
        var compareMinDepth = Double.doubleToLongBits(this.minDepth) == Double.doubleToLongBits(that.minDepth);
        var compareMaxDepth = Double.doubleToLongBits(this.maxDepth) == Double.doubleToLongBits(that.maxDepth);
        var compareMinDepthMirror = Double.doubleToLongBits(this.minDepthMirror) == Double.doubleToLongBits(that.minDepthMirror);
        var compareMaxDepthMirror = Double.doubleToLongBits(this.maxDepthMirror) == Double.doubleToLongBits(that.maxDepthMirror);

        return compareMinRadius && compareMaxRadius && compareMinDepth && compareMaxDepth && compareMinDepthMirror && compareMaxDepthMirror;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minRadius, maxRadius, minDepth, maxDepth, minDepthMirror, maxDepthMirror);
    }

    @Override
    public String toString() {
        return "AxisShape["+"minRadius="+minRadius+", "+"maxRadius="+maxRadius+", "+"minDepth="+minDepth+", "+"maxDepth="+maxDepth+", "+"minDepthMirror="+minDepthMirror+", "+"maxDepthMirror="+maxDepthMirror+']';
    }

}
