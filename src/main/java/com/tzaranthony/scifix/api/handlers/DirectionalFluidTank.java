package com.tzaranthony.scifix.api.handlers;

import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Predicate;

public class DirectionalFluidTank extends FluidTank implements IDirectional {
    protected Direction direction;

    public DirectionalFluidTank(int capacity, Direction direction) {
        this(capacity, fluid -> true, direction);
    }

    public DirectionalFluidTank(int capacity, Predicate<FluidStack> validator, Direction direction) {
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
    /**
     * Fill is used when inserting fluids from a different tank
     * */
    public int fill(FluidStack resource, FluidAction action) {
        if (!this.direction.canInput()) {
            return 0;
        }
        return super.fill(resource, action);
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
        return super.drain(resource, action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (!this.direction.canOutput()) {
            return FluidStack.EMPTY;
        }
        return super.drain(maxDrain, action);
    }

    @NotNull
    /**
     * Consume and Create are used for fluid crafting
     * */
    public FluidStack consumeFluid(FluidStack resource, FluidAction action) {
        if (!this.direction.canInput()) {
            return FluidStack.EMPTY;
        }
        return super.drain(resource, action);
    }

    @NotNull
    public int createFluid(FluidStack resource, FluidAction action) {
        if (!this.direction.canOutput()) {
            return 0;
        }
        return super.fill(resource, action);
    }

    @NotNull
    /**
     * Remove and Add are used for bucket operations
     * */
    public ItemStack removeFluid(ItemStack stack) {
        if (super.drain(1000, FluidAction.SIMULATE).getAmount() >= 1000) {
            super.drain(1000, FluidAction.EXECUTE);
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
}