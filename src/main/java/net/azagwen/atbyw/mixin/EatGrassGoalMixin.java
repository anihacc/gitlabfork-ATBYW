package net.azagwen.atbyw.mixin;

import net.azagwen.atbyw.block.registry.BuildingBlockRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Allows Any mob that eats grass to eat from Grass Block Slabs and Grass Block Stairs
 */
@Mixin(EatGrassGoal.class)
public class EatGrassGoalMixin {
    private final @Mutable @Final @Shadow MobEntity mob;
    private final @Mutable @Final @Shadow World world;
    private final @Mutable @Shadow int timer;

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    private void canStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.getRandom().nextInt(this.mob.isBaby() ? 50 : 1000) == 0) {
            var blockPos = this.mob.getBlockPos();
            var isGrassStairs = this.world.getBlockState(blockPos.down()).isOf(BuildingBlockRegistry.GRASS_BLOCK_STAIRS);
            var isGrassSlab = this.world.getBlockState(blockPos.down()).isOf(BuildingBlockRegistry.GRASS_BLOCK_SLAB);
            var isInGrassSlab = this.world.getBlockState(blockPos).isOf(BuildingBlockRegistry.GRASS_BLOCK_SLAB);

            cir.setReturnValue(isGrassStairs || isGrassSlab || isInGrassSlab);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (this.timer == 4) {
            var mobPos = this.mob.getBlockPos();
            var downPos = mobPos.down();
            this.replaceGrass(BuildingBlockRegistry.GRASS_BLOCK_STAIRS, BuildingBlockRegistry.DIRT_STAIRS, downPos);
            this.replaceGrass(BuildingBlockRegistry.GRASS_BLOCK_SLAB, BuildingBlockRegistry.DIRT_SLAB, downPos);
            this.replaceGrass(BuildingBlockRegistry.GRASS_BLOCK_SLAB, BuildingBlockRegistry.DIRT_SLAB, mobPos);
        }
    }

    private void replaceGrass(Block grass, Block dirt, BlockPos pos) {
        if (this.world.getBlockState(pos).isOf(grass)) {
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
                this.world.syncWorldEvent(2001, pos, Block.getRawIdFromState(grass.getDefaultState()));
                this.world.setBlockState(pos, dirt.getStateWithProperties(this.world.getBlockState(pos)), 2);
            }

            this.mob.onEatingGrass();
            this.mob.emitGameEvent(GameEvent.EAT, this.mob.getCameraBlockPos());
        }
    }

    public EatGrassGoalMixin(MobEntity mob, World world, int timer) {
        this.mob = mob;
        this.world = world;
        this.timer = timer;
    }
}
