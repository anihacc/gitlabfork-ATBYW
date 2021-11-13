package net.azagwen.atbyw.block.state;

import com.google.common.collect.Maps;
import net.minecraft.util.StringIdentifiable;

import java.util.Map;

public enum IronLadderPiece implements StringIdentifiable {
    ORIGIN("origin", 0),
    LADDER_1("1", 1),
    LADDER_2("2", 2),
    LADDER_3("3", 3),
    LADDER_4("4", 4),
    LADDER_5("5", 5),
    LADDER_6("6", 6),
    LADDER_7("7", 7);

    private final String name;
    private final int level;
    public static final Map<Integer, IronLadderPiece> PIECE_MAP = initPieces();

    IronLadderPiece(String name, int level) {
        this.name = name;
        this.level = level;
    }

    private static Map<Integer, IronLadderPiece> initPieces() {
        var map = Maps.<Integer, IronLadderPiece>newHashMap();
        for (var value : values()) {
            map.put(value.level, value);
        }
        return map;
    }

    public static IronLadderPiece getByLevel(int level) {
        return switch (level) {
            default -> ORIGIN;
            case 1 -> LADDER_1;
            case 2 -> LADDER_2;
            case 3 -> LADDER_3;
            case 4 -> LADDER_4;
            case 5 -> LADDER_5;
            case 6 -> LADDER_6;
            case 7 -> LADDER_7;
        };
    }

    public String toString() {
        return this.asString();
    }

    public String asString() {
        return this.name;
    }

    public int getLevel() {
        return this.level;
    }
}
