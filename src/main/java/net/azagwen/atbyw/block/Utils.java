package net.azagwen.atbyw.block;

import net.azagwen.atbyw.main.Tags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldView;

public record Utils() {

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

    public static Direction getDirectionFromPosToPos(BlockPos posA, BlockPos posB) {
        var xA = posA.getX();
        var yA = posA.getY();
        var zA = posA.getZ();
        var xB = posB.getX();
        var yB = posB.getY();
        var zB = posB.getZ();
        Direction.AxisDirection axisDirection = Direction.AxisDirection.POSITIVE;
        Direction.Axis axis = getAxis(yA, yB, zA, zB, Direction.Axis.X, getAxis(xA, xB, zA, zB, Direction.Axis.Y, Direction.Axis.Z));

        if (xA - xB == 1 || yA - yB == 1 || zA - zB == 1) {
            axisDirection = Direction.AxisDirection.NEGATIVE;
        }

        return Direction.from(axis, axisDirection);
    }

    private static Direction.Axis getAxis(int axisA1, int axisA2, int axisB1, int axisB2, Direction.Axis trueAxis, Direction.Axis falseAxis) {
        if (axisA1 == axisA2 && axisB1 == axisB2) {
            return trueAxis;
        }
        return falseAxis;
    }
}
