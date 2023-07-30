package com.tzaranthony.scifix.core.container.handlers;

import net.minecraftforge.energy.EnergyStorage;

public class SRFHandler extends EnergyStorage {
    public SRFHandler(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public int receiveEnergy(int maxInputAmt, boolean simulate) {
        int i = super.receiveEnergy(maxInputAmt, simulate);
        if (i != 0) onEnergyChange();
        return i;
    }

    @Override
    public int extractEnergy(int maxOutputAmt, boolean simulate) {
        int i = super.extractEnergy(maxOutputAmt, simulate);
        if (i != 0) onEnergyChange();
        return i;
    }

    /*
    * For consuming energy via machine processes.
    * */
    public int useEnergy(int maxOutputAmt, boolean simulate) {
        int rfUsed = Math.min(this.energy, maxOutputAmt);
        if (!simulate)
            this.energy -= rfUsed;
        return rfUsed;
    }

    public int setEnergy(int rf) {
        this.energy = rf;
        return this.energy;
    }

    public void onEnergyChange() {

    }
}