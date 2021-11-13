package net.azagwen.atbyw.main;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public class AtbywSoundEvents {
    public static final SoundEvent SPIKE_TRAP_EXTEND = create("block.spike_trap.extend");
    public static final SoundEvent SPIKE_TRAP_RETRACT = create("block.spike_trap.retract");
    public static final SoundEvent REDSTONE_LANTERN_SHRINK = create("block.redstone_lantern.shrink");

    private static SoundEvent create(String id) {
        return new SoundEvent(AtbywMain.id(id));
    }

    private static void register(SoundEvent soundEvent) {
        Registry.register(Registry.SOUND_EVENT, soundEvent.getId(), soundEvent);
    }

    public static void init() {
        register(SPIKE_TRAP_EXTEND);
        register(SPIKE_TRAP_RETRACT);
        register(REDSTONE_LANTERN_SHRINK);
    }
}
