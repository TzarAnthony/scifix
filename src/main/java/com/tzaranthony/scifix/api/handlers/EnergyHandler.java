package com.tzaranthony.scifix.api.handlers;

import com.tzaranthony.scifix.core.network.EnergyS2CPacket;
import com.tzaranthony.scifix.registries.SPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyHandler extends EnergyStorage {
    public EnergyHandler(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    @Override
    public int receiveEnergy(int maxInputAmt, boolean simulate) {
        int i = super.receiveEnergy(maxInputAmt, simulate);
        return i;
    }

    @Override
    public int extractEnergy(int maxOutputAmt, boolean simulate) {
        int i = super.extractEnergy(maxOutputAmt, simulate);
        return i;
    }

    /**
    * For consuming energy via machine processes.
    * */
    public int useEnergy(int consumeAmt, boolean simulate) {
        int rfUsed = Math.min(this.energy, consumeAmt);
        if (!simulate)
            this.energy -= rfUsed;
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

    public void syncClient(Level level, BlockPos pos) {
        if (!level.isClientSide()) SPackets.sendToClients(new EnergyS2CPacket(this, pos));
    }
}