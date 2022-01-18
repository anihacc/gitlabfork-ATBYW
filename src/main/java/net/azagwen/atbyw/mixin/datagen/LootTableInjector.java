package net.azagwen.atbyw.mixin.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonElement;
import net.azagwen.atbyw.datagen.loot.LootDatagen;
import net.minecraft.loot.LootManager;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;

@Mixin(LootManager.class)
public class LootTableInjector {

    @ModifyVariable(at = @At("STORE"), method = "apply(Ljava/util/Map;Lnet/minecraft/resource/ResourceManager;Lnet/minecraft/util/profiler/Profiler;)V")
    private ImmutableMap.Builder<Identifier, LootTable> onReload(ImmutableMap.Builder<Identifier, LootTable> builder, Map<Identifier, JsonElement> map) {
        LootDatagen.applyLoots(map, builder);
        return builder;
    }

}
