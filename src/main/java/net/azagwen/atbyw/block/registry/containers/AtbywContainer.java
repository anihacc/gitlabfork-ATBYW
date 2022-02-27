package net.azagwen.atbyw.block.registry.containers;

import java.util.ArrayList;

public class AtbywContainer <T> extends ArrayList<T> {
    public final String name;
    public final String type;

    public AtbywContainer(String name, String type) {
        this.name = name;
        this.type = type;
    }
}
