package net.azagwen.atbyw.block;

import net.azagwen.atbyw.block.registry.RedstoneBlockRegistry;
import net.azagwen.atbyw.block.state.AtbywProperties;
import net.azagwen.atbyw.block.state.IronLadderPiece;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LadderBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class IronLadderPieceBlock extends LadderBlock {
    public static final EnumProperty<IronLadderPiece> LADDER_PIECE;


    public IronLadderPieceBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LADDER_PIECE, IronLadderPiece.LADDER_1));
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return new ItemStack(RedstoneBlockRegistry.IRON_LADDER.asItem());
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        var level = state.get(LADDER_PIECE).getLevel();
        var stepsDown = -(7 - level);

        for (int i = (stepsDown - 1); i <= level; i++) {
            var offsetPos = pos.offset(Direction.Axis.Y, i);
            var offsetState = world.getBlockState(offsetPos);

            if ((i < 0 && offsetState.isOf(RedstoneBlockRegistry.IRON_LADDER_PIECE)) || (i > 0 && (offsetState.isOf(RedstoneBlockRegistry.IRON_LADDER_PIECE) || offsetState.isOf(RedstoneBlockRegistry.IRON_LADDER)))) {
                world.breakBlock(offsetPos, true);
            }
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(LADDER_PIECE));
    }

    static {
        LADDER_PIECE = AtbywProperties.LADDER_PIECE;
    }
}
