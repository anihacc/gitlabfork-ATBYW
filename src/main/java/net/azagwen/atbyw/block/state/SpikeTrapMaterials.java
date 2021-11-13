package net.azagwen.atbyw.block.state;

public enum SpikeTrapMaterials implements SpikeTrapMaterial {
    IRON("iron", 2.0F, 1.0F, 1),
    GOLD("gold", 0.5F, 0.5F, 0),
    DIAMOND("diamond", 3.0F, 3.0F, 2),
    NETHERITE("netherite", 4.0F, 6.0F, 2);

    private final String name;
    private final float DamageValue;
    private final float PushingStrength;
    private final int EffectAmplifier;

    SpikeTrapMaterials(String name, float DamageValue, float PushingStrength, int EffectAmplifier) {
        this.name = name;
        this.DamageValue = DamageValue;
        this.PushingStrength = PushingStrength;
        this.EffectAmplifier = EffectAmplifier;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getDamageValue() {
        return this.DamageValue;
    }

    @Override
    public float getPushingStrength() {
        return this.PushingStrength;
    }

    @Override
    public int getEffectAmplifier() {
        return this.EffectAmplifier;
    }
}
