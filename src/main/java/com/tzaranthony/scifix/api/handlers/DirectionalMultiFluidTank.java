package com.tzaranthony.scifix.api.handlers;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DirectionalMultiFluidTank extends FluidTank implements IDirectional {
    protected Direction direction;
    protected List<FluidStack> fluids = NonNullList.of(FluidStack.EMPTY);
    private static final String FLUID_TAG = "SCIFIX_Fluid_";
    private static final String COUNT_TAG = "SCIFIX_Fluid_Count";
    protected int filledVolume = 0;
    private static final String FILLED_TAG = "SCIFIX_Filled_Amount";

    public DirectionalMultiFluidTank(int capacity, Direction direction) {
        this(capacity, fluid -> true, direction);
    }

    public DirectionalMultiFluidTank(int capacity, Predicate<FluidStack> validator, Direction direction) {
        super(capacity, validator);
        this.direction = direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public FluidStack getFluid() {
        return this.fluids.get(0);
    }

    public FluidStack getFluid(int slot) {
        assert this.fluids.size() > slot;
        return this.fluids.get(slot);
    }

    @Nonnull
    public List<FluidStack> getFluids() {
        return this.fluids;
    }

    public void setFluids(List<FluidStack> fluids) {
        this.fluids = fluids;
        onContentsChanged();
    }

    @Override
    /**
     * Fill is used when inserting fluids from a different tank
     * */
    public int fill(FluidStack resource, FluidAction action) {
        if (!this.direction.canInput()) {
            return 0;
        }
        return this.multiFill(resource, action);
    }

    @Nonnull
    @Override
    /**
     * Drain is used when the fluid that is removed will be added to a different tank
     * */
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (!this.direction.canOutput()) {
            return FluidStack.EMPTY;
        }
        return this.multiDrain(this.getMatchingFluid(resource), resource.getAmount(), action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (!this.direction.canOutput()) {
            return FluidStack.EMPTY;
        }
        return this.multiDrain(0, maxDrain, action);
    }

    @NotNull
    /**
     * Consume and Create are used for fluid crafting
     * */
    public FluidStack consumeFluid(FluidStack resource, FluidAction action) {
        if (!this.direction.canInput()) {
            return FluidStack.EMPTY;
        }
        return this.multiDrain(this.getMatchingFluid(resource), resource.getAmount(), action);
    }

    @NotNull
    public int createFluid(FluidStack resource, FluidAction action) {
        if (!this.direction.canOutput()) {
            return 0;
        }
        return this.multiFill(resource, action);
    }

    @NotNull
    /**
     * Remove and Add are used for bucket operations
     * */
    public ItemStack removeFluid(ItemStack stack) {
        if (this.multiDrain(0, 1000, FluidAction.SIMULATE).getAmount() >= 1000) {
            this.multiDrain(0,1000, FluidAction.EXECUTE);
            return FluidUtil.getFilledBucket(this.fluid);
        }
        return stack;
    }

    @NotNull
    public ItemStack addFluid(ItemStack stack) {
        BucketItem bucket = (BucketItem) stack.getItem();
        FluidStack fluidIn = new FluidStack(bucket.getFluid(), 1000);
        if (this.fill(fluidIn, FluidAction.SIMULATE) >= 1000) {
            super.fill(fluidIn, FluidAction.EXECUTE);
            return new ItemStack(Items.BUCKET);
        }
        return stack;
    }

    /**
     * This is a reformat of the fill function from FluidTank, so it can handle multiple fluids in a tank.
     * Additionally, this method needs to reorder the fluids by density upon inserting a fluid.
     * */
    public int multiFill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || !isFluidValid(resource)) return 0;
        int filledAmt = Math.min(this.capacity - this.filledVolume, resource.getAmount());
        if (action.simulate()) {
            return Math.max(0, filledAmt);
        }

        if (filledAmt > 0) {
            boolean matched = false;
            for (FluidStack fluid : this.fluids) {
                if (fluid.isFluidEqual(resource)) {
                    fluid.grow(filledAmt);
                    matched = true;
                    break;
                }
            }
            if (!matched) {
                this.fluids.add(new FluidStack(resource, filledAmt));
                this.fluids.sort(Comparator.comparingInt(f -> f.getFluid().getAttributes().getDensity()));
                this.fluid = this.fluids.get(0);
            }
            this.filledVolume += filledAmt;
            onContentsChanged();
            return filledAmt;
        }
        return 0;
    }

    @Nonnull
    /**
     * This is a reformat of the drain function from FluidTank, so it can handle multiple fluids in a tank.
     * */
    public FluidStack multiDrain(int slot, int maxDrain, FluidAction action) {
        if (slot == -1) return FluidStack.EMPTY;

        int drained = maxDrain;
        if (this.fluids.get(slot).getAmount() < drained) {
            drained = this.fluids.get(slot).getAmount();
        }

        FluidStack stack = new FluidStack(this.fluids.get(slot), drained);

        if (action.execute() && drained > 0) {
            this.fluids.get(slot).shrink(drained);
            if (this.fluids.get(slot).isEmpty()) this.fluids.remove(slot);
            this.filledVolume -= drained;
            onContentsChanged();
        }
        return stack;
    }

    protected int getMatchingFluid(FluidStack resource) {
        for (int i = 0; i < this.fluids.size(); ++i) {
            FluidStack f = this.fluids.get(i);
            if (f.isFluidEqual(resource)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public FluidTank readFromNBT(CompoundTag tag) {
        this.filledVolume = tag.getInt(FILLED_TAG);
        for (int i = 0; i < tag.getInt(COUNT_TAG); ++i) {
            this.fluids.set(i, FluidStack.loadFluidStackFromNBT(tag.getCompound(FLUID_TAG + i)));
        }
        return super.readFromNBT(tag);
    }

    @Override
    public CompoundTag writeToNBT(CompoundTag tag) {
        tag.putInt(FILLED_TAG, this.filledVolume);
        tag.putInt(COUNT_TAG, this.fluids.size());
        for (int i = 0; i < this.fluids.size(); ++i) {
            tag.put(FLUID_TAG + i, this.fluids.get(i).writeToNBT(new CompoundTag()));
        }
        return super.writeToNBT(tag);
    }
}