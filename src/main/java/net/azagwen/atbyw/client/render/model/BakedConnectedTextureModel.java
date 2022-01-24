package net.azagwen.atbyw.client.render.model;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.material.BlendMode;
import net.fabricmc.fabric.api.renderer.v1.material.RenderMaterial;
import net.fabricmc.fabric.api.renderer.v1.model.ForwardingBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BuiltinBakedModel;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.BlockRenderView;
import net.minecraft.world.LightType;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;

public class BakedConnectedTextureModel extends ForwardingBakedModel {
    private final SpriteIdentifier texture;
    private final SpriteIdentifier particleTexture;
    private final RenderMaterial material;
    private final boolean hasTint;

    public BakedConnectedTextureModel(SpriteIdentifier texture, SpriteIdentifier particleTexture, @Nullable RenderMaterial material, boolean hasTint) {
        this.texture = texture;
        this.particleTexture = particleTexture;
        this.material = material;
        this.hasTint = hasTint;
        this.wrapped = new BuiltinBakedModel(ModelTransformation.NONE, ModelOverrideList.EMPTY, texture.getSprite(), true);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public Sprite getParticleSprite() {
        return this.particleTexture.getSprite();
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        var emitter = context.getEmitter();
        var sprite = texture.getSprite();
        var from = new Vec3f(0.0F, 0.0F, 0.0F);
        var to = new Vec3f(16.0F, 16.0F, 16.0F);
        var faceDataMap = Maps.<Direction, Face>newHashMap();

        for (var direction : Direction.values()) {
            var offsetPos = pos.offset(direction);
            var offsetState = blockView.getBlockState(offsetPos);

            if (!(offsetState.isSideSolidFullSquare(blockView, offsetPos, direction.getOpposite()) && offsetState.isOpaque()) || !offsetState.isOpaqueFullCube(blockView, offsetPos)) {
                var ownerBlock = blockView.getBlockState(pos).getBlock();
                var connections = ConnectedTextureHelper.getFaceConnections(blockView, pos, ownerBlock, direction);
                faceDataMap.put(direction, ConnectedTextureHelper.chooseFace(connections, sprite, direction, false));
            }
        }
        ModelUtil.emitBox(emitter, from, to, faceDataMap, this.material, this.hasTint, 0);
    }
}
