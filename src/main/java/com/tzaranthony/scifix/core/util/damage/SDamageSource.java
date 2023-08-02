package com.tzaranthony.scifix.core.util.damage;

import net.minecraft.world.damagesource.DamageSource;

public class SDamageSource extends DamageSource {
    public static final SDamageSource EXTREME_TEMP = (SDamageSource) (new SDamageSource("extreme_temp")).bypassArmor().setIsFire();

    public SDamageSource(String name) {
        super(name);
    }
}