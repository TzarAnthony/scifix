package com.tzaranthony.scifix.api.properties;

public interface IFluidProperties {
    int getColor();

    int getTransparency();

    default int getColorWithTransparency() {
        return (this.getColor() & 0x00FFFFFF) | (this.getTransparency() << 24);
    }

    int getDensity();

    int getViscosity();

    int getTemperature();

    int getLuminosity();

    boolean isGas();
}