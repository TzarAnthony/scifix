package com.tzaranthony.scifix.api.handlers;

import com.tzaranthony.scifix.api.mathAndPhysics.Constants;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.util.INBTSerializable;

public class HeatExchanger implements IHeatExchanger, INBTSerializable<Tag> {
    protected float temperature;
    protected float heatCapacity;
    protected float thermalConductivity;

    public HeatExchanger(float heatCapacity, float conductivity, float temperature) {
        this.heatCapacity = heatCapacity;
        this.thermalConductivity = conductivity;
        this.temperature = temperature;
    }

    public float calculateTemperatureExchangePerTick(float otherThermalConductivity, float otherHeatCapacity, float otherTemperature) {
        float conductivity = Math.min(this.thermalConductivity, otherThermalConductivity);
        float heatFlow = this.temperature - otherTemperature;
        float joulesPerTick = -conductivity * (heatFlow/ Constants.MetersBetweenMachineBlocks); // sped up by 20x - this is technically per second
        float degreesPerTick = joulesPerTick / otherHeatCapacity;
        return degreesPerTick;
    }

    public float exchangeHeat(float otherThermalConductivity, float otherHeatCapacity, float otherTemperature, boolean simulate) {
        float temperatureChange = this.calculateTemperatureExchangePerTick(otherThermalConductivity, otherHeatCapacity, otherTemperature);
        if (!simulate) {
            this.temperature += temperatureChange;
            this.onHeatChange();
        }
        return -temperatureChange;
    }

    public float receiveHeat(float heatHtoC) {
        return this.temperature += heatHtoC;
    }

    public float getTemperature() {
        return this.temperature;
    }

    public float getThermalConductivity() {
        return this.thermalConductivity;
    }

    public float getHeatCapacity() {
        return this.heatCapacity;
    }

    public float setTemperature(float temperature) {
        this.temperature = temperature;
        this.onHeatChange();
        return this.temperature;
    }

    public float consumeOrProduceHeat(float temperatureChange, boolean simulate) {
        if (simulate) return this.temperature + temperatureChange;
        this.temperature += temperatureChange;
        this.onHeatChange();
        return this.temperature;
    }

    public Tag serializeNBT() {
        return FloatTag.valueOf(this.temperature);
    }

    public void deserializeNBT(Tag nbt) {
        if (!(nbt instanceof FloatTag tag)) {
            throw new IllegalArgumentException("Can not deserialize temperature from non-int tag");
        }
        this.temperature = tag.getAsInt();
    }

    public void onHeatChange() {
    }
}