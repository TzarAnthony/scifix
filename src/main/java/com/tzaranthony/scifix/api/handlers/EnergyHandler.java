package com.tzaranthony.scifix.api.handlers;

import net.minecraftforge.energy.EnergyStorage;

public class EnergyHandler extends EnergyStorage {
    protected static final String RF = "SCIFIX_RF";

    public EnergyHandler(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public int receiveEnergy(int wanted, boolean simulate) {
        int i = super.receiveEnergy(wanted, simulate);
        return i;
    }

    @Override
    public int extractEnergy(int wanted, boolean simulate) {
        int i = super.extractEnergy(wanted, simulate);
        return i;
    }

    /**
    * For consuming energy via machine processes.
    * */
    public int useEnergy(int consumeAmt, boolean simulate) {
        int rfUsed = Math.min(this.energy, consumeAmt);
        if (!simulate) {
            this.energy -= rfUsed;
        }
        return rfUsed;
    }

    public int setEnergy(int rf) {
        this.energy = rf;
        return this.energy;
    }

    public void setEnergyData(int capacity, int maxReceive, int maxExtract) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public int getCapacity() {
        return this.capacity;
    }
}