package net.azagwen.atbyw.block;

import net.azagwen.atbyw.block.entity.MiniBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MiniBlock extends BlockWithEntity {

    public MiniBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        Direction direction = ctx.getSide();
        FluidState fluidState = ctx.getWorld().getFluidState(blockPos);

        var isHittingUp = direction == Direction.UP || ctx.getHitPos().y - blockPos.getY() > 0.5D;
        var isHittingEast = direction == Direction.EAST || ctx.getHitPos().x - blockPos.getX() > 0.5D;
        var isHittingSouth = direction == Direction.SOUTH || ctx.getHitPos().z - blockPos.getZ() > 0.5D;

        System.out.println(String.format("Hit side: %s%s%s",
                (direction.getAxis() != Direction.Axis.Y ? (isHittingUp ? "UP " : "DOWN ") : ""),
                (direction.getAxis() != Direction.Axis.X ? (isHittingEast ? "EAST " : "WEST ") : ""),
                (direction.getAxis() != Direction.Axis.Z ? (isHittingSouth ? "SOUTH" : "NORTH") : "")
        ));

        return super.getPlacementState(ctx);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MiniBlockEntity(pos, state);
    }
}
