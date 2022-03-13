package net.azagwen.atbyw.block.state;

import net.minecraft.block.enums.SlabType;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public enum PillarSlabType implements StringIdentifiable {
    BOTTOM_X(SlabType.BOTTOM, Direction.Axis.X, null),
    BOTTOM_Y(SlabType.BOTTOM, Direction.Axis.Y, null),
    BOTTOM_Z(SlabType.BOTTOM, Direction.Axis.Z, null),
    TOP_X(SlabType.TOP, null, Direction.Axis.X),
    TOP_Y(SlabType.TOP, null, Direction.Axis.Y),
    TOP_Z(SlabType.TOP, null, Direction.Axis.Z),
    DOUBLE_BX_TX(SlabType.DOUBLE, Direction.Axis.X, Direction.Axis.X),
    DOUBLE_BX_TY(SlabType.DOUBLE, Direction.Axis.X, Direction.Axis.Y),
    DOUBLE_BX_TZ(SlabType.DOUBLE, Direction.Axis.X, Direction.Axis.Z),
    DOUBLE_BY_TX(SlabType.DOUBLE, Direction.Axis.Y, Direction.Axis.X),
    DOUBLE_BY_TY(SlabType.DOUBLE, Direction.Axis.Y, Direction.Axis.Y),
    DOUBLE_BY_TZ(SlabType.DOUBLE, Direction.Axis.Y, Direction.Axis.Z),
    DOUBLE_BZ_TX(SlabType.DOUBLE, Direction.Axis.Z, Direction.Axis.X),
    DOUBLE_BZ_TY(SlabType.DOUBLE, Direction.Axis.Z, Direction.Axis.Y),
    DOUBLE_BZ_TZ(SlabType.DOUBLE, Direction.Axis.Z, Direction.Axis.Z);

    private final String name;
    private final SlabType slabType;
    private final Direction.Axis bottomAxis, topAxis;
    PillarSlabType(SlabType slabType, @Nullable Direction.Axis bottomAxis, @Nullable Direction.Axis topAxis) {
        this.name = switch (slabType) {
            case BOTTOM -> String.format("%s_%s", slabType.asString(), bottomAxis.asString());
            case TOP -> String.format("%s_%s", slabType.asString(), topAxis.asString());
            case DOUBLE -> String.format("%s_b%s_t%s", slabType.asString(), bottomAxis.asString(), topAxis.asString());
        };
        this.slabType = slabType;
        this.bottomAxis = bottomAxis;
        this.topAxis = topAxis;
    }

    public static PillarSlabType getTypeFromAxis(Direction.Axis topAxis, Direction.Axis bottomAxis, SlabType type) {
        return switch (type) {
            case TOP -> switch (topAxis) {
                case X -> TOP_X;
                case Y -> TOP_Y;
                case Z -> TOP_Z;
            };
            case BOTTOM -> switch (bottomAxis) {
                case X -> BOTTOM_X;
                case Y -> BOTTOM_Y;
                case Z -> BOTTOM_Z;
            };
            case DOUBLE -> switch (topAxis) {
                case X -> switch (bottomAxis) {
                    case X -> DOUBLE_BX_TX;
                    case Y -> DOUBLE_BY_TX;
                    case Z -> DOUBLE_BZ_TX;
                };
                case Y -> switch (bottomAxis) {
                    case X -> DOUBLE_BX_TY;
                    case Y -> DOUBLE_BY_TY;
                    case Z -> DOUBLE_BZ_TY;
                };
                case Z -> switch (bottomAxis) {
                    case X -> DOUBLE_BX_TZ;
                    case Y -> DOUBLE_BY_TZ;
                    case Z -> DOUBLE_BZ_TZ;
                };
            };
        };
    }

    public SlabType getSlabType() {
        return this.slabType;
    }

    public Direction.Axis getBottomAxis() {
        return this.bottomAxis;
    }

    public Direction.Axis getTopAxis() {
        return this.topAxis;
    }

    public String toString() {
        return this.asString();
    }

    public String asString() {
        return this.name;
    }
}