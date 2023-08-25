package com.tzaranthony.scifix.api.properties;

public class FluidProperties implements IFluidProperties {
    // Oil
    public static final FluidProperties CRUDE_OIL = new Liquid(984843, 870, 9700);
    public static final FluidProperties PURIFIED_CRUDE_OIL = new Liquid(984843, 870, 9800);
    public static final FluidProperties LIGHT_NAPHTHA = new Liquid(12160070, 190, 750, 88);
    public static final FluidProperties HEAVY_NAPHTHA = new Liquid(10714162, 190, 785, 139);
    public static final FluidProperties KEROSENE = new Liquid(9658418, 210, 810, 1270);
    public static final FluidProperties LIGHT_FUEL_OIL = new Liquid(8145970, 210, 830, 2830);
    public static final FluidProperties HEAVY_FUEL_OIL = new Liquid(6504749, 230, 860, 3300);
    public static final FluidProperties RESIDUAL_OIL = new Liquid(2426635, 980, 8000);

    public static final FluidProperties BENZENE = new Liquid(13677568, 70, 876, 178);
    public static final FluidProperties XYLENE = new Liquid(13677773, 60, 865, 580);
    public static final FluidProperties CYCLOHEXANE = new Liquid(14864548, 100, 779, 1000);
    public static final FluidProperties CYCLOPENTANE = new Liquid(12567778, 100, 751, 440);
    public static final FluidProperties ETHYLENE_GLYCOL = new Liquid(12839379, 60, 1115, 1890);
    public static final FluidProperties METHANOL = new Liquid(14594702, 70, 729, 178);
    public static final FluidProperties ACETIC_ACID = new Liquid(14594773, 80, 1051, 1160);
    public static final FluidProperties ACRYLONITRILE = new Liquid(16762729, 80, 810, 400);
    public static final FluidProperties ETHYLBENZENE = new Liquid(14215067, 70, 866, 678);
    public static final FluidProperties STYRENE = new Liquid(15709796, 70, 909, 696);
    public static final FluidProperties ACRYLIC_ACID = new Liquid(13547254, 70, 1050, 1300);
    public static final FluidProperties HYDROXYETHYL_ACRYLATE = new Liquid(13950188, 170, 1011, 5438);

    // Hydrocarbon Gases
    public static final FluidProperties METHANE = new Gas(11993305, -64);
    public static final FluidProperties ETHYLENE = new Gas(11993305, -11);
    public static final FluidProperties PROPYLENE = new Gas(11993305, 45);
    public static final FluidProperties BUTADIENE = new Gas(11993305, 615);
    public static final FluidProperties ETHYLENE_OXIDE = new Gas(12819682, 882);

    // Other Liquids
    public static final FluidProperties SALT_BRINE = new Liquid(15592667, 1202, 1000);
    public static final FluidProperties RED_MUD = new Liquid(11101023, 1840, 34000);
    public static final FluidProperties MOLTEN_ALUMINA_BRINE = new Molten(3987, 1273);
    public static final FluidProperties LIQUID_AIR = new Liquid(14935787, 40, 870, 1349, 78);

    // Other Gases
    public static final FluidProperties HYDROGEN = new Gas(11993305, -129);
    public static final FluidProperties HELIUM = new Gas(15412992, -107);
    public static final FluidProperties NITROGEN = new Gas(16711900, -12);
    public static final FluidProperties OXYGEN = new Gas(4259735, 3);
    public static final FluidProperties CHLORINE = new Gas(16777081, 19);
    public static final FluidProperties ARGON = new Gas(13500671, 44);
    public static final FluidProperties STEAM = new Gas(11310559, -30, 373);
    public static final FluidProperties SUPERHEATED_STEAM = new Gas(11310559, -40, 383);
    public static final FluidProperties AIR = new Gas(13893604, 0);
    public static final FluidProperties CARBON_MONOXIDE = new Gas(39145, -15);
    public static final FluidProperties CARBON_DIOXIDE = new Gas(4692170, 69);
    public static final FluidProperties SULFUR_DIOXIDE = new Gas(2641886, 134);
    public static final FluidProperties HYDROGEN_SULFIDE = new Gas(2641886, 7);
    public static final FluidProperties AMMONIA = new Gas(12210688, -56);

    // Molten Metals
    public static final FluidProperties MOLTEN_IRON = new Molten(6980, 1811);
    public static final FluidProperties MOLTEN_COPPER = new Molten(8020, 1358);
    public static final FluidProperties MOLTEN_GOLD = new Molten(17310, 1337);
    public static final FluidProperties MOLTEN_NICKEL = new Molten(7810, 1728);
    public static final FluidProperties MOLTEN_COBALT = new Molten(8860, 1768);
    public static final FluidProperties MOLTEN_SILVER = new Molten(9320, 1235);
    public static final FluidProperties MOLTEN_ALUMINUM = new Molten(2375, 934);
    public static final FluidProperties MOLTEN_CHROMIUM = new Molten(6300, 2180);
    public static final FluidProperties MOLTEN_GALLIUM = new Molten(6095, 303);
    public static final FluidProperties MOLTEN_PLATINUM = new Molten(19770, 2041);
    public static final FluidProperties MOLTEN_STEEL = new Molten(7040, 1644);
    public static final FluidProperties MOLTEN_STAINLESS_STEEL = new Molten(7720, 1813);
    public static final FluidProperties MOLTEN_CUPRONICKEL = new Molten(7930, 1240);
    public static final FluidProperties MOLTEN_DYMALLOY = new Molten(8740, 1050);

    public static class Liquid extends FluidProperties {
        Liquid(int color, int density, int viscosity) {
            super(color, -1, density, viscosity, 298, 0, false);
        }

        Liquid(int color, int transparency, int density, int viscosity) {
            super(color, transparency, density, viscosity, 298, 0, false);
        }

        Liquid(int color, int transparency, int density, int viscosity, int temperature) {
            super(color, transparency, density, viscosity, temperature, 0, false);
        }
    }

    public static class Gas extends FluidProperties {
        Gas(int color, int density) {
            super(color, 10, density, 10, 298, 0, true);
        }

        Gas(int color, int density, int temperature) {
            super(color, 10, density, 10, temperature, 0, true);
        }
    }

    public static class Molten extends FluidProperties {
        Molten(int density, int temperature) {
            super(16776104, -1, density, 780, temperature, 15, false);
        }
    }

    private final int color;
    private final int transparency;
    private final int density;
    private final int viscosity;
    private final int temperature;
    private final int luminosity;
    private final boolean isGas;

    FluidProperties(int color, int transparency, int density, int viscosity, int temperature, int luminosity, boolean isGas) {
        this.color = color;
        this.transparency = transparency;
        this.density = density;
        this.viscosity = viscosity;
        this.temperature = temperature;
        this.luminosity = luminosity;
        this.isGas = isGas;
    }

    public int getColor() {
        return this.color;
    }

    public int getTransparency() {
        return this.transparency;
    }

    public int getDensity() {
        return this.density;
    }

    public int getViscosity() {
        return this.viscosity;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public int getLuminosity() {
        return this.luminosity;
    }

    public boolean isGas() {
        return this.isGas;
    }
}