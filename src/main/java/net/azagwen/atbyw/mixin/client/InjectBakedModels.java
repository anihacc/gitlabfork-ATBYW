package net.azagwen.atbyw.mixin.client;

import net.azagwen.atbyw.client.render.model.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Environment(EnvType.CLIENT)
@Mixin(ModelLoader.class)
public abstract class InjectBakedModels {
    @Shadow protected abstract void putModel(Identifier id, UnbakedModel unbakedModel);
    private final AtbywModelManager atbywModelManager = new AtbywModelManager(this::putModel);

    @Inject(method = "putModel", at = @At("HEAD"), cancellable = true)
    private void onPutModel(Identifier id, UnbakedModel unbakedModel, CallbackInfo ci) {
        BlockModelRegistry.appendToModels(id, unbakedModel, ci, this.atbywModelManager);
    }

    @Inject(method = "getBakedModelMap", at = @At("RETURN"), cancellable = true)
    private void getModelMap(CallbackInfoReturnable<Map<Identifier, BakedModel>> cir) {
        var map = cir.getReturnValue();
        this.atbywModelManager.addNewModels(map);
        cir.setReturnValue(map);
    }
}
