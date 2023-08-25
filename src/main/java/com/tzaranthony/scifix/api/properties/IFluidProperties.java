package com.tzaranthony.scifix.api.properties;

public interface IFluidProperties {
    int getColor();

    int getTransparency();

    default int getColorWithTransparency() {
        return this.getTransparency() > 0 ? ((this.getColor() & 0x00FFFFFF) | (this.getTransparency() << 24)) : this.getColor();
    }

    int getDensity();

    int getViscosity();

    int getTemperature();

    int getLuminosity();

    boolean isGas();
}