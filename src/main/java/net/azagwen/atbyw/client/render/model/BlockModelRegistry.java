package net.azagwen.atbyw.client.render.model;

import net.azagwen.atbyw.block.registry.AtbywBlocks;
import net.azagwen.atbyw.block.registry.DecorationBlockRegistry;
import net.azagwen.atbyw.block.registry.RedstoneBlockRegistry;
import net.azagwen.atbyw.main.AtbywMain;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class BlockModelRegistry {

    public static void init() {
        var modelManager = new AtbywModelManager((identifier, model) -> {});
        modelManager.putConnectedTextureModelData(DecorationBlockRegistry.GLOWING_CANVAS_BLOCK, true, true, true);
        modelManager.putConnectedTextureModelData(AtbywBlocks.CTM_DEBUG_BLOCK, false, false, false);
    }

    public static void appendToModels(Identifier id, UnbakedModel unbakedModel, CallbackInfo ci, AtbywModelManager modelManager) {
        if (id instanceof ModelIdentifier modelId && !(unbakedModel instanceof AtbywUnbakedModel)) {
            if (modelId.getNamespace().equals(AtbywMain.ATBYW)) {
                if (!modelId.getVariant().equals("inventory")) {
                    if (modelManager.isModelOf(modelId, RedstoneBlockRegistry.TIMER_REPEATER)) {
                        modelManager.putModel(ci, id, new UnbakedForwardingModel(unbakedModel, BakedTimerRepeaterDigitModel::new));
                    }
                }
            }
        }
    }
}
