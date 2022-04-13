package net.azagwen.atbyw.block.state;

import net.minecraft.block.enums.SlabType;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

//TODO: add CUT log slabs
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
    @SuppressWarnings("ConstantConditions")
    PillarSlabType(SlabType slabType, @Nullable Direction.Axis bottomAxis, @Nullable Direction.Axis topAxis) {
        this.name = switch (slabType) {
            // Procedurally stitch names together 😎
            case BOTTOM -> String.format("%s_%s", slabType.asString(), bottomAxis.asString());
            case TOP -> String.format("%s_%s", slabType.asString(), topAxis.asString());
            case DOUBLE -> String.format("%s_b%s_t%s", slabType.asString(), bottomAxis.asString(), topAxis.asString());
        };
        this.slabType = slabType;       // Vanilla Slab Type, used as a base for this slab type, with the added Axis support
        this.bottomAxis = bottomAxis;   // Axis used for the bottom half of the slab type
        this.topAxis = topAxis;         // Axis used for the top half of the slab type
    }

    /**
     * Allows to obtain {@link PillarSlabType} from 3 defining parameters.
     *
     * @param topAxis       The top Axis of the desired {@link PillarSlabType}.
     * @param bottomAxis    The bottom Axis of the desired {@link PillarSlabType}.
     * @param slabType      The {@link SlabType} of the desired {@link PillarSlabType}.
     *
     * @return              The {@link PillarSlabType} that matches the input parameters.
     */
    public static PillarSlabType getTypeFromAxis(Direction.Axis topAxis, Direction.Axis bottomAxis, SlabType slabType) {
        var result = (PillarSlabType) null;
        for (var item : PillarSlabType.values()) {
            if (item.topAxis == topAxis && item.bottomAxis == bottomAxis && item.slabType == slabType) {
                result = item;
            }
        }
        return result;
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

    public boolean equals(PillarSlabType pillarSlabType) {
        return pillarSlabType.topAxis == this.topAxis && pillarSlabType.bottomAxis == this.bottomAxis && pillarSlabType.slabType == this.slabType;
    }
}