package com.tzaranthony.scifix.api.handlers;

import net.minecraftforge.energy.EnergyStorage;

public class SRFHandler extends EnergyStorage {
    public SRFHandler(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public int receiveEnergy(int maxInputAmt, boolean simulate) {
        int i = super.receiveEnergy(maxInputAmt, simulate);
        if (i != 0) this.onEnergyChange();
        return i;
    }

    @Override
    public int extractEnergy(int maxOutputAmt, boolean simulate) {
        int i = super.extractEnergy(maxOutputAmt, simulate);
        if (i != 0) this.onEnergyChange();
        return i;
    }

    /**
    * For consuming energy via machine processes.
    * */
    public int useEnergy(int consumeAmt, boolean simulate) {
        int rfUsed = Math.min(this.energy, consumeAmt);
        if (!simulate)
            this.energy -= rfUsed;
            this.onEnergyChange();
        return rfUsed;
    }

    public int setEnergy(int rf) {
        this.energy = rf;
        this.onEnergyChange();
        return this.energy;
    }

    public void onEnergyChange() {

    }
}