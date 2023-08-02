package com.tzaranthony.scifix.api.properties;

import com.tzaranthony.scifix.api.mathAndPhysics.Constants;
import com.tzaranthony.scifix.api.mathAndPhysics.Maths;

public enum ThermalProperties {
    TIER_0_EXCHANGER(0, Maths.mean(new float[]{Constants.CopperOxideThermalConductivity, Constants.CopperThermalConductivity})
            , Maths.mean(new float[]{Constants.CopperOxideHeatCapacity, Constants.CopperHeatCapacity})),

    TIER_1_EXCHANGER(1, Maths.mean(new float[]{Constants.CupronickelThermalConductivity, Constants.CopperThermalConductivity})
            , Maths.mean(new float[]{Constants.CupronickelHeatCapacity, Constants.CopperHeatCapacity})),

    TIER_2_EXCHANGER(2, Maths.mean(new float[]{Constants.CupronickelThermalConductivity, Constants.CopperThermalConductivity, Constants.CopperThermalConductivity})
            , Maths.mean(new float[]{Constants.CupronickelHeatCapacity, Constants.CopperHeatCapacity, Constants.CopperHeatCapacity})),

    TIER_3_EXCHANGER(3, Maths.mean(new float[]{Constants.CupronickelThermalConductivity, Constants.CopperThermalConductivity, Constants.DiamondThermalConductivity, Constants.SilverThermalConductivity})
            , Maths.mean(new float[]{Constants.CupronickelHeatCapacity, Constants.CopperHeatCapacity, Constants.DiamondHeatCapacity, Constants.SilverHeatCapacity})),

    GRAPHITE(0, Constants.GraphiteThermalConductivity, Constants.GraphiteHeatCapacity),

    COPPER(0, Constants.CopperThermalConductivity, Constants.CopperHeatCapacity),

    ALUMINUM(1, Constants.AluminumThermalConductivity, Constants.AluminumHeatCapacity),

    SILVER(2, Constants.SilverThermalConductivity, Constants.SilverHeatCapacity);


    private final int tier;
    private final float thermalConductivity;
    private final float specificHeat;

    ThermalProperties(int tier, float thermalConductivity, float specificHeat) {
        this.tier = tier;
        this.thermalConductivity = thermalConductivity;
        this.specificHeat = specificHeat;
    }

    public int getTier() {
        return this.tier;
    }

    public float getThermalConductivity() {
        return thermalConductivity;
    }

    public float getSpecificHeat() {
        return specificHeat;
    }
}