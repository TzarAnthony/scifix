package com.tzaranthony.scifix.api.mathAndPhysics;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;

public class Maths {
    public static float MCTemperatureToC(Level level, BlockPos pos) {
        if (level.dimensionType().ultraWarm()) {
            return 70;
        }
        Biome biome = level.getBiome(pos).value();
        float baseTemp = 21.6F * biome.getBaseTemperature() + 2.3F;
        double metersBelowGround = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, pos).getY() - pos.getY();
        if (metersBelowGround > 0) {
            baseTemp += 30F / 100F * metersBelowGround; // this should be 30/km, but im reducing it for MC since lava can be found at 200 km instead of 200 m
        }
        return baseTemp;
    }

    public static float mean(float[] values) {
        float numerator = 0F;
        for (float val : values) numerator += val;
        return numerator / (float) values.length;
    }

    // Returns the temperature required for saturated steam to be produced from a given pressure
    public static float SteamCTokPa(float temp) {
        return (float) Math.pow((temp / 100F), 4F)  * 100F;
    }

    // Returns the pressure required for saturated steam to be produced from a given temperature
    public static float SteamkPaToC(float kPa) {
        return (float) Math.sqrt(Math.sqrt(kPa / 100F)) * 100F;
    }

    // J(Enthalpy) = U(Int Energy) + Pa * m^3
    // J/∆kg(Vapor Enthalpy) = ∆U(Int Energy) + Pa * ∆m^3
    // ∆J(Heat) = J/kg(Vapor Enthalpy) * ∆kg = J/m^3 * ∆m^3
    // J(Heat) = J/(kg*T) * T * kg
    // J/m^3 * ∆m^3 = J/(kg*T) * T * (kg/m^3 * m^3)
    // ∆m^3 = J/(kg*T) * T * kg/m^3 * m^3 / J/m^3
    public static int mbWaterToEvaporate(float tempExcess, float weight) {
        return Math.round(1000F * Constants.WaterHeatCapacity * tempExcess * weight / Constants.WaterEvaporationEnthalpyPerBucket);
    }

    public static float mbWaterTokgSteam(int mb) {
        return Constants.WaterDensityPerBucket * (float) mb / 1000F;
    }

    public static float SteamkgTokPa(float kg, float tempC, float volAvailable) {
        float moles = ((kg*1000F) / Constants.WaterGramsPerMole);
        float vm = volAvailable / moles;
        float rt = ((tempC + Constants.K0C) * Constants.R) / (vm - Constants.bWater);
        float va = (Constants.aWater / (vm * vm));
        return (rt - va) / 1000F;
    }

    public static float CO2kgTokPa(float kg, float tempC, float volAvailable) {
        return kPaRedlichKwong(tempC, volAvailable, ((kg*1000F) / Constants.CO2GramsPerMole), Constants.aCO2, Constants.bCO2);
    }

    public static float HekgTokPa(float kg, float tempC, float volAvailable) {
        return kPaRedlichKwong(tempC, volAvailable, ((kg*1000F) / Constants.HeGramsPerMole), Constants.aHe, Constants.bHe);
    }

    public static float ArkgTokPa(float kg, float tempC, float volAvailable) {
        return kPaRedlichKwong(tempC, volAvailable, ((kg*1000F) / Constants.ArGramsPerMole), Constants.aAr, Constants.bAr);
    }

    public static float NkgTokPa(float kg, float tempC, float volAvailable) {
        return kPaRedlichKwong(tempC, volAvailable, ((kg*1000F) / Constants.NGramsPerMole), Constants.aN, Constants.bN);
    }

    public static float MethanekgTokPa(float kg, float tempC, float volAvailable) {
        return kPaRedlichKwong(tempC, volAvailable, ((kg*1000F) / Constants.MethaneGramsPerMole), Constants.aMethane, Constants.bMethane);
    }

    public static float kPaRedlichKwong(float tempC, float volAvailable, float n, float a, float b) {
        float tempK = tempC + Constants.K0C;
        float vm = volAvailable / n;
        float rt = (tempK * Constants.R) / (vm - b);
        float va = (a / ((float) Math.sqrt(tempK) * vm * (vm + b)));
        return (rt - va) / 1000F;
    }
}