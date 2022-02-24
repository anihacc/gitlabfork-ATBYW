package net.azagwen.atbyw.block.entity;

import com.mojang.datafixers.types.Type;
import net.azagwen.atbyw.block.registry.DecorationBlockRegistry;
import net.azagwen.atbyw.block.registry.RedstoneBlockRegistry;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

import static net.azagwen.atbyw.util.AtbywUtils.*;

public class AtbywBlockEntityTypes<T extends BlockEntity>{

    public static <T extends BlockEntity> BlockEntityType<T> create(FabricBlockEntityTypeBuilder.Factory<T> factory, Block block) {
        Type<?> type = Util.getChoiceType(TypeReferences.BLOCK_ENTITY, getId(block).getPath());
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, getId(block), FabricBlockEntityTypeBuilder.create(factory, block).build(type));
    }

    public static <T extends BlockEntity> BlockEntityType<T> create(FabricBlockEntityTypeBuilder.Factory<T> factory, Block... blocks) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, getId(blocks[0]), FabricBlockEntityTypeBuilder.create(factory, blocks).build(null));
    }

    public static BlockEntityType<CanvasBlockEntity> CANVAS_BLOCK_ENTITY;
    public static BlockEntityType<TintingTableBlockEntity> TINTING_TABLE_BLOCK_ENTITY;
    public static BlockEntityType<IronLadderBlockEntity> IRON_LADDER_BLOCK_ENTITY;

    public static void init() {
        CANVAS_BLOCK_ENTITY = create(CanvasBlockEntity::new, DecorationBlockRegistry.CANVAS_BLOCK, DecorationBlockRegistry.GLOWING_CANVAS_BLOCK);
        TINTING_TABLE_BLOCK_ENTITY = create(TintingTableBlockEntity::new, DecorationBlockRegistry.TINTING_TABLE);
        IRON_LADDER_BLOCK_ENTITY = create(IronLadderBlockEntity::new, RedstoneBlockRegistry.IRON_LADDER);
    }
}
