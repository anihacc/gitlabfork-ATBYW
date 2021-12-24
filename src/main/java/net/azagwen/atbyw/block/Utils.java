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
}
