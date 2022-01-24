package net.azagwen.atbyw.client.render.model;

import com.google.common.collect.Lists;
import net.azagwen.atbyw.main.AtbywMain;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

import java.util.List;

public class SpriteRegistry {
    public static List<SpriteIdentifier> SPRITES = Lists.newArrayList();
    public static SpriteIdentifier TIMER_REPEATER_DIGITS_TEXTURE;
    public static SpriteIdentifier GLOWING_CANVAS_BLOCK_SURFACE;
    public static SpriteIdentifier GLOWING_CANVAS_BLOCK_EDGES;

    public static SpriteIdentifier registerBlockSprite(Identifier identifier) {
        var sprite = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, identifier);
        SPRITES.add(sprite);
        return sprite;
    }

    public static SpriteIdentifier registerConnectedTexture(String name, boolean useSuffix) {
        return registerBlockSprite(AtbywMain.id("baked_models/ctm/" + name + (useSuffix ? "_ctm" : "")));
    }

    public static SpriteIdentifier registerConnectedTexture(String name) {
        return registerConnectedTexture(name, true);
    }

    public static SpriteIdentifier registerConnectedTextureParticles(String name, boolean useSuffix) {
        return registerBlockSprite(AtbywMain.id("baked_models/ctm/particle_sprites/" + name + (useSuffix ? "_ctm" : "")));
    }

    public static SpriteIdentifier registerConnectedTextureParticles(String name) {
        return registerConnectedTextureParticles(name, true);
    }

    public static void init() {
        TIMER_REPEATER_DIGITS_TEXTURE = registerBlockSprite(AtbywMain.id("baked_models/timer_repeater_digits"));
        GLOWING_CANVAS_BLOCK_SURFACE = registerBlockSprite(AtbywMain.id("baked_models/glowing_canvas_block"));
        GLOWING_CANVAS_BLOCK_EDGES = registerBlockSprite(AtbywMain.id("baked_models/glowing_canvas_block_outline"));

        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlas, registry) -> {
            for (var sprite : SPRITES) {
                registry.register(sprite.getTextureId());
            }
        });
    }
}
