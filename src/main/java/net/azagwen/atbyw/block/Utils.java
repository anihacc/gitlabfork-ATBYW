package net.azagwen.atbyw.block;

import net.azagwen.atbyw.main.Tags;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public record Utils() {
    public static int NORTH = 0;
    public static int SOUTH = 1;
    public static int EAST = 2;
    public static int WEST = 3;

    /** Inverts the input value for use in VoxelShapes.
     *
     *  @param i Value to invert.
     *  @return  Inverted Value.
     */
    public static double invert(double i) {
        return -(i - 16);
    }

    /** Creates an array of 4 shapes, corresponding
     *  to the 4 directions statues can be in.
     *
     *  @return        Array of all 4 directions combined from the input arrays.
     */
    public static VoxelShape[] makeDirectionalShapes(double xMin, double yMin, double zMin, double xMax, double yMax, double zMax) {
        var xMax2 = invert(zMax);
        var zMin2 = invert(zMin);

        var NORTH = Block.createCuboidShape(xMin , yMin, zMin , xMax , yMax, zMax );
        var SOUTH = Block.createCuboidShape(xMin , yMin, xMax2, xMax , yMax, zMin2);
        var EAST  = Block.createCuboidShape(xMax2, yMin, xMin , zMin2, yMax, xMax );
        var WEST  = Block.createCuboidShape(zMin , yMin, xMin , zMax , yMax, xMax );

        return new VoxelShape[] {NORTH, SOUTH, EAST, WEST};
    }

    public static Direction getDirectionFromTwoPos(BlockPos start, BlockPos end) {
        var startX = start.getX();
        var startY = start.getY();
        var startZ = start.getZ();
        var endX = end.getX();
        var endY = end.getY();
        var endZ = end.getZ();
        var direction = Direction.NORTH;

        if (startX > endX) {
            direction = Direction.from(Direction.Axis.X, Direction.AxisDirection.POSITIVE);
        } else if (startX < endX) {
            direction = Direction.from(Direction.Axis.X, Direction.AxisDirection.NEGATIVE);
        } else if (startY > endY) {
            direction = Direction.from(Direction.Axis.Y, Direction.AxisDirection.POSITIVE);
        } else if (startY < endY) {
            direction = Direction.from(Direction.Axis.Y, Direction.AxisDirection.NEGATIVE);
        } else if (startZ > endZ) {
            direction = Direction.from(Direction.Axis.Z, Direction.AxisDirection.POSITIVE);
        } else if (startZ < endZ) {
            direction = Direction.from(Direction.Axis.Z, Direction.AxisDirection.NEGATIVE);
        }

        return direction;
    }

    public static boolean checkEmitsPower(Direction direction, BlockView world, BlockPos pos) {
        var offset = pos.offset(direction);
        return world.getBlockState(offset).emitsRedstonePower();
    }

    public static boolean checkFullSquare(Direction direction, BlockView world, BlockPos pos) {
        var offset = pos.offset(direction);
        return world.getBlockState(offset).isSideSolidFullSquare(world, offset, direction.getOpposite());
    }

    /**
     * True equals Covered.
     *
     * @param worldView     Access to the world
     * @param pos           The current Position
     *
     * @return              Wether the block is considered convered or not
     */
    public static boolean simulateIsGrassCovered(WorldView worldView, BlockPos pos) {
        var upPos = pos.up();
        var blockState = worldView.getBlockState(upPos);
        var isCoveringFaceFullSquare = blockState.isSideSolidFullSquare(worldView, upPos, Direction.DOWN);
        var isCoveringFaceOpaque = blockState.isOpaque();

        if (isCoveringFaceFullSquare && isCoveringFaceOpaque) {
            return true;
        }
        else {
            if (blockState.isIn(Tags.BlockTags.KILLS_GRASS)) {
                return true;
            }
            if (blockState.isIn(Tags.BlockTags.SPARES_GRASS)) {
                return false;
            }
            return false;
        }
    }
}
