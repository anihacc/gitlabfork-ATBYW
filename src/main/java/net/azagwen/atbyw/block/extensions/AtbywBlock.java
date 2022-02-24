package net.azagwen.atbyw.block.extensions;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public class AtbywBlock extends Block {

    public AtbywBlock(Settings settings) {
        super(settings);
    }

    public AtbywBlock(Set<Block> set, Settings settings) {
        super(settings);
        set.add(this);
    }

    public AtbywBlock(Map<StringIdentifiable, Block> variantMap, @Nullable StringIdentifiable variant, Settings settings) {
        super(settings);
        if (variant != null) {
            variantMap.put(variant, this);
        }
    }
}
