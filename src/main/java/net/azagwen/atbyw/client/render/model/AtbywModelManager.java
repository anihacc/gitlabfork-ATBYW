package net.azagwen.atbyw.client.render.model;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import net.azagwen.atbyw.util.AtbywUtils;
import net.azagwen.atbyw.util.Pair;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.minecraft.block.Block;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.BiConsumer;

@Environment(EnvType.CLIENT)
public record AtbywModelManager(BiConsumer<Identifier, UnbakedModel> modelAdder) {
    private static final Table<Identifier, Pair<SpriteIdentifier, SpriteIdentifier>, Pair<Boolean, Boolean>> CONNECTED_TEXTURE_MODELS = HashBasedTable.create();//model ID, <face sprite, particle sprite>, <is emissive, has tint>

    /**
     * Check if the given model is the one of the given Item Convertible
     *
     * @param modelId           the model ID to check correspondance against
     * @param itemConvertible   an {@link ItemConvertible} we want to verify this model belongs to
     * @return                  a boolean telling us if the given model ID is the one of our input {@link ItemConvertible}
     */
    boolean isModelOf(ModelIdentifier modelId, ItemConvertible itemConvertible) {
        var identifier = new Identifier("");

        if (itemConvertible instanceof Block block)
            identifier = AtbywUtils.getId(block);
        if (itemConvertible instanceof Item item)
            identifier = AtbywUtils.getId(item);

        return modelId.getPath().equals(identifier.getPath());
    }

    public void addNewModels(Map<Identifier, BakedModel> models) {
        for (var model : CONNECTED_TEXTURE_MODELS.cellSet()) {
            this.putConnectedTextureModel(models, model);
        }
    }

    /**
     * Adds a connected texture model to the input models map
     *
     * @param models    Input model map
     * @param data      The connected texture model data, should preferably be a {@link com.google.common.collect.Table.Cell}
     */
    private void putConnectedTextureModel(Map<Identifier, BakedModel> models, Table.Cell<Identifier, Pair<SpriteIdentifier, SpriteIdentifier>, Pair<Boolean, Boolean>> data) {
        var ctmSprite = data.getColumnKey().getFirst();
        var particleSprite = data.getColumnKey().getSecond();
        var isEmissive = data.getValue().getFirst();
        var hasTint = data.getValue().getSecond();
        var renderer = RendererAccess.INSTANCE.getRenderer();
        var material = renderer.materialFinder().emissive(0, true).disableDiffuse(0, true).find();
        models.put(data.getRowKey(), new BakedConnectedTextureModel(ctmSprite, particleSprite, (isEmissive ? material : null), hasTint));
    }


    /**
     * Adds connected texture model data to the CONNECTED_TEXTURE_MODELS {@link Table} from this class
     *
     * @param block         The block the data should be made for.
     * @param emissive      Weither the resulting model should be emissive or not.
     * @param hasTint       Weither the resulting model should be looking for a color provider tint or not.
     * @param useSuffix     Weither the texture name should use the CTM suffix or not.
     */
    void putConnectedTextureModelData(Block block, boolean emissive, boolean hasTint, boolean useSuffix) {
        var ctmSprite = SpriteRegistry.registerConnectedTexture(AtbywUtils.getId(block).getPath(), useSuffix);
        var particleSprite = SpriteRegistry.registerConnectedTextureParticles(AtbywUtils.getId(block).getPath(), useSuffix);
        CONNECTED_TEXTURE_MODELS.put(this.getEmptyModelId(block), new Pair<>(ctmSprite, particleSprite), new Pair<>(emissive, hasTint));
    }

    private ModelIdentifier getEmptyModelId(ItemConvertible itemConvertible) {
        return new ModelIdentifier(AtbywUtils.getId(itemConvertible), "");
    }

    void putModel(CallbackInfo ci, Identifier id, UnbakedModel unbakedModel) {
        this.modelAdder.accept(id, unbakedModel);
        ci.cancel();
    }
}
