package com.tzaranthony.scifix.api.handlers;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class FluidHandler implements IFluidHandler {
    protected String SLOT = "SCIFIX_Fluid_Slot";
    protected String TANK_TAG = "SCIFIX_Fluid_Tanks";
    protected NonNullList<DirectionalMultiFluidTank> tanks;

    public FluidHandler(int capacity, IDirectional.Direction direction) {
        this(NonNullList.withSize(1, capacity), NonNullList.withSize(1, fluid -> true), NonNullList.withSize(1, direction));
    }

    public FluidHandler(int capacity, Predicate<FluidStack> validator, IDirectional.Direction direction) {
        this(NonNullList.withSize(1, capacity), NonNullList.withSize(1, validator), NonNullList.withSize(1, direction));
    }

    public FluidHandler(int size, int capacity, IDirectional.Direction direction) {
        this(NonNullList.withSize(size, capacity), NonNullList.withSize(size, fluid -> true), NonNullList.withSize(size, direction));
    }

    public FluidHandler(List<Integer> capacities, List<IDirectional.Direction> directions) {
        this(capacities, NonNullList.withSize(capacities.size(), fluid -> true), directions);
    }

    public FluidHandler(List<Integer> capacities, List<Predicate<FluidStack>> validators, List<IDirectional.Direction> directions) {
        assert capacities.size() == validators.size() && capacities.size() == directions.size();
        tanks = NonNullList.create();

        for (int i = 0; i < capacities.size(); ++i) {
            tanks.add(new DirectionalMultiFluidTank(capacities.get(i), validators.get(i), directions.get(i)));
        }
    }

    public void addTanks(List<Integer> capacities, List<Predicate<FluidStack>> validators, List<IDirectional.Direction> directions) {
        assert capacities.size() == validators.size() && capacities.size() == directions.size();
        for (int i = 0; i < capacities.size(); ++i) {
            tanks.add(new DirectionalMultiFluidTank(capacities.get(i), validators.get(i), directions.get(i)));
        }
    }

    public void changeTankCapacity(int slot, int newCapacity) {
        tanks.get(slot).setCapacity(newCapacity);
    }

    public int getTanks() {
        return tanks.size();
    }

    @NotNull
    public FluidStack getFluidInTank(int slot) {
        return tanks.get(slot).getFluid();
    }

    @NotNull
    public List<FluidStack> getFluidsInTank(int slot) {
        return tanks.get(slot).getFluids();
    }

    public DirectionalMultiFluidTank getTank(int slot) {
        return tanks.get(slot);
    }

    public int getTankCapacity(int slot) {
        return tanks.get(slot).getCapacity();
    }

    public boolean isFluidValid(int slot, @NotNull FluidStack stack) {
        return tanks.get(slot).isFluidValid(stack);
    }

    public void setFluidsInTank(int tank, List<FluidStack> stack) {
        tanks.get(tank).setFluids(stack);
    }

    public int fill(FluidStack resource, FluidAction action) {
        int fillAmt = 0;
        for (DirectionalMultiFluidTank tank : tanks) {
            fillAmt = tank.fill(resource, action);
            if (fillAmt > 0) {
                if (action == FluidAction.EXECUTE) onContentsChanged();
                break;
            }
        }
        return fillAmt;
    }

    @NotNull
    public FluidStack drain(FluidStack resource, FluidAction action) {
        FluidStack fluid = FluidStack.EMPTY;
        for (DirectionalMultiFluidTank tank : tanks) {
            fluid = tank.drain(resource, action);
            if (!fluid.isEmpty()) {
                if (action == FluidAction.EXECUTE) onContentsChanged();
                break;
            }
        }
        return fluid;
    }

    @NotNull
    public FluidStack drain(int maxDrain, FluidAction action) {
        FluidStack fluid = FluidStack.EMPTY;
        for (DirectionalMultiFluidTank tank : tanks) {
            fluid = tank.drain(maxDrain, action);
            if (!fluid.isEmpty()) {
                if (action == FluidAction.EXECUTE) onContentsChanged();
                break;
            }
        }
        return fluid;
    }

    @NotNull
    public FluidStack consumeFluid(FluidStack resource, FluidAction action) {
        FluidStack fluid = FluidStack.EMPTY;
        for (DirectionalMultiFluidTank tank : tanks) {
            fluid = tank.consumeFluid(resource, action);
            if (!fluid.isEmpty()) {
                if (action == FluidAction.EXECUTE) onContentsChanged();
                break;
            }
        }
        return fluid;
    }

    @NotNull
    public int createFluid(int tank, FluidStack resource, FluidAction action) {
        int amt = this.tanks.get(tank).createFluid(resource, action);
        if (amt > 0) {
            if (action == FluidAction.EXECUTE) onContentsChanged();
        }
        return amt;
    }

    public ItemStack fillOrEmptyItemUsingTank(ItemStack toFill, int slot) {
        ItemStack outputStack = toFill.copy();
        if (!toFill.isEmpty() && toFill.getItem() instanceof BucketItem) {
            if (toFill.is(Items.BUCKET)) {
                outputStack = this.tanks.get(slot).removeFluid(toFill);
            } else {
                outputStack = this.tanks.get(slot).addFluid(toFill);
            }
        }
        return outputStack;
    }

    public CompoundTag serializeNBT() {
        ListTag nbtTagList = new ListTag();
        for (int i = 0; i < tanks.size(); i++) {
            CompoundTag fluidTankTag = new CompoundTag();
            tanks.get(i).writeToNBT(fluidTankTag);
            fluidTankTag.putInt(SLOT, i);
            nbtTagList.add(fluidTankTag);
        }
        CompoundTag nbt = new CompoundTag();
        nbt.put(TANK_TAG, nbtTagList);
        return nbt;
    }

    public void deserializeNBT(CompoundTag nbt) {
        ListTag tagList = nbt.getList(TANK_TAG, Tag.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++) {
            CompoundTag fluidTankTag = tagList.getCompound(i);
            int slot = fluidTankTag.getInt(SLOT);
            if (slot >= 0 && slot < tanks.size()) {
                tanks.get(slot).readFromNBT(fluidTankTag);
            }
        }
    }

    protected void onContentsChanged() {

    }
}