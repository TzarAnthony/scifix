package com.tzaranthony.scifix.api.mathAndPhysics;

import net.minecraft.core.Direction;
import org.stringtemplate.v4.misc.Misc;

import java.util.ArrayList;

public class Constants {
    //TODO: check this file for things that should be configurable
    public static final float MetersBetweenMachineBlocks = 0.125F; // 2 pixels -- 2/16th of a meter

    // Thermal Conductivity is W/(m k)
    // Heat Capacity is J/(kg*T) --- J/T is precalculated for solids and assumed as 500 kg unless stated
    // 1 m^3 = 1 b

    // Solids Thermal Properties
    // Thermal Conductivity -- https://www.engineeringtoolbox.com/thermal-conductivity-d_429.html
    // Heat Capacity -- https://owl.oit.umass.edu/departments/Chemistry/appendix/SpecificHeats.html
    public static final float CopperOxideThermalConductivity = 32.9F;
    public static final float CopperOxideHeatCapacity = 265885F;
    public static final float CopperThermalConductivity = 392F;
    public static final float CopperHeatCapacity = 192500F;
    public static final float CupronickelThermalConductivity = 50F; // this is for 10% nickel instead of 30%
    public static final float CupronickelHeatCapacity = 188500F;
    public static final float DiamondThermalConductivity = 3300F; // synthetic, natural is lower (900 - 2425)
    public static final float DiamondHeatCapacity = 259500F;
    public static final float GraphiteThermalConductivity = 168F;
    public static final float GraphiteHeatCapacity = 355500F;
    public static final float AluminumThermalConductivity = 240F;
    public static final float AluminumHeatCapacity = 450000F;
    public static final float SilverThermalConductivity = 420F;
    public static final float SilverHeatCapacity = 118500F;
    public static final float IceThermalConductivity = 2.18F;
    public static final float IceHeatCapacity = 1757430.5F; // assumed at 917

    // Liquids Thermal Properties
    public static final float LeadMoltenThermalConductivity = 18.5F;
    public static final float LeadMoltenHeatCapacity = 140F; // per density bucket
    public static final float ReactorMoltenSaltThermalConductivity = 1.1F;
    public static final float ReactorMoltenSaltHeatCapacity = 2386F; // per density bucket
    public static final float WaterThermalConductivity = 0.54F;
    public static final float WaterHeatCapacity = 4173.4F; // per density bucket

    // Misc Gases Thermal Properties
    // Thermal Conductivity -- https://www.engineeringtoolbox.com/thermal-conductivity-d_429.html
    // Heat Capacity -- https://www.ohio.edu/mechanical/thermo/property_tables/gas/idealGas.html
    public static final float SteamThermalConductivity = 0.0184F;
    public static final float SteamHeatCapacity = 1872.3F; // per density bucket
    public static final float AirThermalConductivity = 0.0262F;
    public static final float AirHeatCapacity = 1005F; // per density block
    public static final float CO2ThermalConductivity = 0.0146F;
    public static final float CO2HeatCapacity = 846F; // per density bucket
    public static final float HeliumThermalConductivity = 0.142F;
    public static final float HeliumHeatCapacity = 5192.6F; // per density bucket
    public static final float NitrogenThermalConductivity = 0.024F;
    public static final float NitrogenHeatCapacity = 1039F; // per density bucket
    public static final float ArgonThermalConductivity = 0.016F;
    public static final float ArgonHeatCapacity = 520.3F; // per density bucket

    // Other Water Properties
    public static final float IceBaseTemp = -10F;
    public static final float WaterBaseTemp = 15F;
    public static final float WaterGramsPerMole = 18.015F;
    public static final float WaterDensityPerBucket = 977.723F;
    public static final float WaterPaPerBucket = 9588.529F; // Pa = 9.807 m/s^2 * 977.723 kg/m^3 * m
    public static final float WaterkPaPerBucket = 9.588529461F;
    public static final float WaterEvaporationEnthalpy = 2256452.955F; // J / kg -- (40.65 kJ/mol / 18.015 g/mol)*1000*1000
    public static final float WaterEvaporationEnthalpyPerBucket = 2206185952.52F; // J / m^3 -- (J/kg) * (kg/m^3)
    public static final float aWater = 5.536F;
    public static final float bWater = 0.03049F;

    // Other Gas Properties
    public static final float CO2GramsPerMole = 44.009F;
    public static final float aCO2 = 3.640F;
    public static final float bCO2 = 0.04267F;
    public static final float HeGramsPerMole = 4.0026F;
    public static final float aHe = 0.0346F;
    public static final float bHe = 0.0238F;
    public static final float ArGramsPerMole = 39.963F;
    public static final float aAr = 1.355F;
    public static final float bAr = 0.03201F;
    public static final float NGramsPerMole = 14.006F;
    public static final float aN = 1.370F;
    public static final float bN = 0.0387F;
    public static final float MethaneGramsPerMole = 16.043F;
    public static final float aMethane = 2.283F;
    public static final float bMethane = 0.04278F;

    // Misc Properties
    public static final float Gravity = 9.807F;
    public static final float AtmostpherekPa = 101.325F;
    public static final float K0C = 273.15F;
    public static final float R = 8.31446261815324F;

    // Machine Constants
    public static final float SolidFuelHeaterTempPerTick = 20.3F;
    public static final float ConvectionHeaterTempPerTick = 10.5F;
    public static final float LiquidFuelHeaterTempPerTick = 30.8F;
    public static final float ResistanceHeaterTempPerTick = 15.0F;
    public static final float MaxHeatExchangerTemperature = 3000F;

    public static final ArrayList<Direction> OrderedHeatDirections = new ArrayList<>() {{
        add(Direction.UP);
        add(Direction.NORTH);
        add(Direction.SOUTH);
        add(Direction.WEST);
        add(Direction.EAST);
        add(Direction.DOWN);
    }};
}