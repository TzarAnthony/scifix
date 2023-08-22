package com.tzaranthony.scifix.api.handlers;

public interface IHeatHandler {
    float calculateTemperatureExchangePerTick(float otherThermalConductivity, float otherHeatCapacity, float otherTemperature);

    float exchangeHeat(float otherThermalConductivity, float otherHeatCapacity, float otherTemperature, boolean simulate);

    default float exchangeHeat(IHeatHandler otherHeatExchanger, boolean simulate) {
        return exchangeHeat(otherHeatExchanger.getThermalConductivity(), otherHeatExchanger.getHeatCapacity(), otherHeatExchanger.getTemperature(), simulate);
    }

    float receiveHeat(float heatHtoC);

    float getTemperature();

    float getThermalConductivity();

    float getHeatCapacity();
}