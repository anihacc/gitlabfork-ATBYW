package net.azagwen.atbyw.block;

import net.azagwen.atbyw.block.entity.IronLadderBlockEntity;
import net.azagwen.atbyw.block.registry.RedstoneBlockRegistry;
import net.azagwen.atbyw.block.state.AtbywProperties;
import net.azagwen.atbyw.block.state.IronLadderPiece;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class IronLadderBlock extends LadderBlock implements BlockEntityProvider {
    public static final BooleanProperty EXTENDED;
    public static final IntProperty LENGTH;


    public IronLadderBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(EXTENDED, false).with(LENGTH, 3));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        for (int i = 0; i <= state.get(LENGTH); i++) {
            var currentPos = pos.down(i);
            if (world.getBlockState(currentPos).isOf(RedstoneBlockRegistry.IRON_LADDER_PIECE)) {
                world.breakBlock(currentPos, true);
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (player.getMainHandStack().isOf(RedstoneBlockRegistry.IRON_LADDER.asItem())) {
            if (state.get(LENGTH) < 7) {
                world.setBlockState(pos, state.with(LENGTH, state.get(LENGTH) + 1));
                player.getMainHandStack().decrement(1);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        world.setBlockState(pos, state.with(EXTENDED, world.isReceivingRedstonePower(pos)));
        world.getBlockTickScheduler().schedule(pos, this, 1);

        super.neighborUpdate(state, world, pos, block, fromPos, notify);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        var blockEntity = (IronLadderBlockEntity) world.getBlockEntity(pos);

        if (blockEntity != null) {
            var fluidState = world.getFluidState(pos.down(blockEntity.getLevel()));

            if (state.get(EXTENDED)) {
                if (blockEntity.getLevel() < state.get(LENGTH)) {
                    world.getBlockTickScheduler().schedule(pos, this, 1);
                    blockEntity.setLevel(blockEntity.getLevel() + 1);
                }
                var pos1 = pos.down(blockEntity.getLevel());
                if (world.getBlockState(pos1).getMaterial().isReplaceable()) {
                    world.setBlockState(pos1, RedstoneBlockRegistry.IRON_LADDER_PIECE.getDefaultState().with(FACING, state.get(FACING)).with(IronLadderPieceBlock.LADDER_PIECE, IronLadderPiece.getByLevel(blockEntity.getLevel())).with(WATERLOGGED, fluidState.isIn(FluidTags.WATER) && fluidState.isStill()));
                }
            } else {
                if (blockEntity.getLevel() != 0) {
                    world.getBlockTickScheduler().schedule(pos, this, 1);
                    blockEntity.setLevel(blockEntity.getLevel() - 1);
                }
                var pos1 = pos.down(blockEntity.getLevel() + 1);
                if (world.getBlockState(pos1).isOf(RedstoneBlockRegistry.IRON_LADDER) || world.getBlockState(pos1).isOf(RedstoneBlockRegistry.IRON_LADDER_PIECE)) {
                    world.setBlockState(pos.down(blockEntity.getLevel() + 1), Blocks.AIR.getDefaultState());
                }
            }
        }

    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(EXTENDED, LENGTH));
    }

    static {
        EXTENDED = Properties.EXTENDED;
        LENGTH = AtbywProperties.LENGTH;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new IronLadderBlockEntity(pos, state);
    }
}
