package net.azagwen.atbyw.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.azagwen.atbyw.block.state.SpikeTrapMaterial;
import net.azagwen.atbyw.main.AtbywDamageSource;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Map;

public class SpikeBlock extends Block implements Waterloggable {
    public static final Map<SpikeTrapMaterial, Block> SPIKE_MAP = Maps.newHashMap();
    private static final VoxelShape SHAPE;
    private final SpikeTrapMaterial material;
    public static final BooleanProperty WATERLOGGED;

    public SpikeBlock(SpikeTrapMaterial material, Settings settings) {
        super(settings);
        this.material = material;
        this.setDefaultState(this.getStateManager().getDefaultState().with(WATERLOGGED, false));
        SPIKE_MAP.put(material, this);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        boolean isPlayerCreative = player.isCreative();

        if (world.getBlockState(pos.down()).getBlock() instanceof SpikeTrapBlock) {
            world.breakBlock(pos.down(), !isPlayerCreative, player);
        }

        super.onBreak(world, pos, state, player);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    private boolean isEntityImmune(Entity entity) {
        boolean isImmune = false;
        ArrayList<EntityType<?>> immuneEntities = Lists.newArrayList(
                EntityType.SKELETON,
                EntityType.SKELETON_HORSE,
                EntityType.WITHER_SKELETON,
                EntityType.STRAY,
                EntityType.BLAZE,
                EntityType.IRON_GOLEM,
                EntityType.BAT
        );

        for (EntityType<?> entityType : immuneEntities) {
            if (entity.getType() == entityType) {
                isImmune = true;
                break;
            }
        }

        return isImmune;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        boolean isEntityPlayerAndCreative = entity instanceof PlayerEntity player && player.isCreative();

        if (entity instanceof LivingEntity && !isEntityImmune(entity) && !isEntityPlayerAndCreative) {
            ((LivingEntity) entity).addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 40, this.material.getEffectAmplifier()));
            entity.slowMovement(state, new Vec3d(0.75D, 1.0D, 0.75D));
            entity.damage(AtbywDamageSource.SPIKE_TRAP, this.material.getDamageValue());
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return SpikeTrapBlock.SPIKE_TRAP_MAP.get(this.material).asItem().getDefaultStack();
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        if (!(world.getBlockState(pos.down()).getBlock() instanceof SpikeTrapBlock)) {
            world.breakBlock(pos, false);
        }
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    static {
        WATERLOGGED = Properties.WATERLOGGED;
        SHAPE = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    }
}
