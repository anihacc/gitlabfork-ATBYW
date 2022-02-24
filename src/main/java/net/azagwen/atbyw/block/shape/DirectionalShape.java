package net.azagwen.atbyw.block.shape;

import net.azagwen.atbyw.block.Utils;
import net.minecraft.block.Block;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;

public class DirectionalShape {
    public final VoxelShape north;
    public final VoxelShape south;
    public final VoxelShape east;
    public final VoxelShape west;

    public DirectionalShape(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        var xMax2 = Utils.invert(zMax);
        var zMin2 = Utils.invert(zMin);
        this.north = Block.createCuboidShape(xMin , yMin, zMin , xMax , yMax, zMax );
        this.south = Block.createCuboidShape(xMin , yMin, xMax2, xMax , yMax, zMin2);
        this.east  = Block.createCuboidShape(xMax2, yMin, xMin , zMin2, yMax, xMax );
        this.west  = Block.createCuboidShape(zMin , yMin, xMin , zMax , yMax, xMax );
    }

    private DirectionalShape(VoxelShape north, VoxelShape south, VoxelShape east, VoxelShape west) {
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }

    public static DirectionalShape union(DirectionalShape first, DirectionalShape second) {
        var north = VoxelShapes.union(first.north, second.north);
        var south = VoxelShapes.union(first.south, second.south);
        var east = VoxelShapes.union(first.east, second.east);
        var west = VoxelShapes.union(first.west, second.west);
        return new DirectionalShape(north, south, east, west);
    }
}
