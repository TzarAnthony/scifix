package com.tzaranthony.scifix.api.handlers;

import net.minecraft.core.NonNullList;

import java.util.List;

public class GasFluidHandler extends FluidHandler implements IGasHandler {
    //TODO: separate this and make my own GasStack based off FluidStack but has density and mass
    // gases will always be vented from crafting results.... but what about inputs... Should I make a gas or fluid handler?
    protected String MASS_TAG = "SCIFIX_Gas_Masses";
    protected NonNullList<Float> mass;

    public GasFluidHandler(int capacity, IDirectional.Direction direction) {
        this(NonNullList.withSize(1, capacity), NonNullList.withSize(1, direction));
    }

    public GasFluidHandler(int size, int capacity, IDirectional.Direction direction) {
        this(NonNullList.withSize(size, capacity), NonNullList.withSize(size, direction));
    }

    public GasFluidHandler(List<Integer> capacities, List<IDirectional.Direction> directions) {
        super(capacities, NonNullList.withSize(capacities.size(), fluid -> fluid.getFluid().getAttributes().isGaseous()), directions);
        this.mass = NonNullList.withSize(capacities.size(), 0.0F);
    }

    public void addTanks(int addedSlots, List<Integer> capacities, List<IDirectional.Direction> directions) {
        super.addTanks(capacities, NonNullList.withSize(capacities.size(), fluid -> fluid.getFluid().getAttributes().isGaseous()), directions);
    }

    public float getWeight() {
        return 0;
    }

    public float drainGas(float kg) {
        return 0;
    }
}