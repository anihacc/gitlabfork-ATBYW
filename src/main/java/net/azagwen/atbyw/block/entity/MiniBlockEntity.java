package net.azagwen.atbyw.block.entity;

import com.google.common.collect.Maps;
import net.azagwen.atbyw.main.AtbywMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.BlockPos;

import java.util.Map;

public class MiniBlockEntity extends BlockEntity {
    private Map<MiniBlockCorner, Identifier> corners = Maps.newHashMap();

    public MiniBlockEntity(BlockPos pos, BlockState state) {
        super(AtbywBlockEntityTypes.MINI_BLOCK_ENTITY, pos, state);
        for (var value : MiniBlockCorner.values()) {
            corners.put(value, AtbywMain.id("empty"));
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        var corners = nbt.getCompound("Corners");
        for (var value : MiniBlockCorner.values()) {
            this.corners.put(value, new Identifier(corners.getString(value.asString())));
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        var cornerNbt = new NbtCompound();
        for (var corner : this.corners.entrySet()){
            cornerNbt.putString(corner.getKey().asString(), corner.getValue().toString());
        }
        nbt.put("Corners", cornerNbt);
        return nbt;
    }

    public void setCorner(MiniBlockCorner corner, Identifier block) {
        this.corners.put(corner, block);
    }

    public enum MiniBlockCorner implements StringIdentifiable {
        UP_NORTH_EAST("up_north_east", true, true, true),
        UP_NORTH_WEST("up_north_west", true, true, false),
        UP_SOUTH_EAST("up_south_east", true, false, true),
        UP_SOUTH_WEST("up_south_west", true, false, false),
        DOWN_NORTH_EAST("down_north_east", false, true, true),
        DOWN_NORTH_WEST("down_north_west", false, true, false),
        DOWN_SOUTH_EAST("down_south_east", false, false, true),
        DOWN_SOUTH_WEST("down_south_west", false, false, false);

        private final boolean isUp;
        private final boolean isNorth;
        private final boolean isEast;
        private final String name;
        MiniBlockCorner(String name, boolean up, boolean north, boolean east) {
            this.isUp = up;
            this.isNorth = north;
            this.isEast = east;
            this.name = name;
        }

        public boolean isUp() {
            return this.isUp;
        }

        public boolean isNorth() {
            return this.isNorth;
        }

        public boolean isEast() {
            return this.isEast;
        }

        @Override
        public String asString() {
            return this.name;
        }
    }
}
