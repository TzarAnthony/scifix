package com.tzaranthony.scifix.api.handlers;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class ItemHandler extends ItemStackHandler {
    protected List<Predicate<ItemStack>> validators;
    protected List<IDirectional.Direction> directions;

    public ItemHandler() {
        this(1);
    }

    public ItemHandler(int capacity) {
        this(capacity, NonNullList.withSize(capacity, IDirectional.Direction.EITHER));
    }

    public ItemHandler(int capacity, List<IDirectional.Direction> directions) {
        this(capacity, NonNullList.withSize(capacity, stack -> true), directions);
    }

    public ItemHandler(int capacity, Predicate<ItemStack> validator) {
        this(capacity, NonNullList.withSize(capacity, validator), NonNullList.withSize(capacity, IDirectional.Direction.EITHER));
    }

    public ItemHandler(int capacity, List<Predicate<ItemStack>> validators, List<IDirectional.Direction> directions) {
        super(capacity);
        assert capacity == validators.size() && capacity == directions.size();
        this.validators = validators;
        this.directions = directions;
    }

    public void setSize(int size) {
        super.setSize(size);
        this.validators = NonNullList.withSize(size, stack -> true);
        this.directions = NonNullList.withSize(size, IDirectional.Direction.EITHER);
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return this.validators.get(slot).test(stack);
    }

    @Override
    @Nonnull
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if (this.directions.get(slot).canInput()) {
            return super.insertItem(slot, stack, simulate);
        }
        return stack;
    }

    @Override
    @Nonnull
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (this.directions.get(slot).canOutput()) {
            return super.extractItem(slot, amount, simulate);
        }
        return ItemStack.EMPTY;
    }

    @Nonnull
    public ItemStack insertItemDirectionless(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return super.insertItem(slot, stack, simulate);
    }

    @Nonnull
    public ItemStack extractItemDirectionless(int slot, int amount, boolean simulate) {
        return super.extractItem(slot, amount, simulate);
    }


    public ItemHandler setAllDirections(IDirectional.Direction direction) {
        if (direction != null) {
            this.directions = NonNullList.withSize(this.stacks.size(), direction);
        }
        return this;
    }

    public ItemHandler setDirections(List<IDirectional.Direction> directions) {
        assert this.directions.size() == directions.size();
        this.directions = directions;
        return this;
    }

    public ItemHandler setDirection(int slot, IDirectional.Direction direction) {
        if (direction != null) {
            this.directions.set(slot, direction);
        }
        return this;
    }

    public ItemHandler setAllValidators(Predicate<ItemStack> validator) {
        if (validator != null) {
            this.validators = NonNullList.withSize(this.stacks.size(), validator);
        }
        return this;
    }

    public ItemHandler setValidators(List<Predicate<ItemStack>> validators) {
        assert this.validators.size() == validators.size();
        this.validators = validators;
        return this;
    }

    public ItemHandler setValidator(int slot, Predicate<ItemStack> validator) {
        if (validator != null) {
            this.validators.set(slot, validator);
        }
        return this;
    }
}