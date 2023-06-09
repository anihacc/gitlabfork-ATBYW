package net.azagwen.atbyw.mixin;

import net.azagwen.atbyw.main.AtbywMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.gen.chunk.DebugChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DebugChunkGenerator.class)
public class DebugChunkGeneratorMixin {

    /**
     * @author To add my blocks to the debug world, because dumbass fabric doesn't.
     */
    @Inject(method = "getBlockState", at = @At("HEAD"), cancellable = true)
    private static void getBlockState(int x, int z, CallbackInfoReturnable<BlockState> cir) {

        if (AtbywMain.checkDebugEnabled("debug_world")) {
            var blockState = Blocks.AIR.getDefaultState();

            if (x > 0 && z > 0 && x % 2 != 0 && z % 2 != 0) {
                x /= 2;
                z /= 2;
                if (x <= AtbywMain.X_SIDE_LENGTH && z <= AtbywMain.Z_SIDE_LENGTH) {
                    int i = MathHelper.abs(x * AtbywMain.X_SIDE_LENGTH + z);
                    if (i < AtbywMain.BLOCK_STATES.size()) {
                        blockState = AtbywMain.BLOCK_STATES.get(i);
                    }
                }
            }

            cir.setReturnValue(blockState);
        }
    }
}
