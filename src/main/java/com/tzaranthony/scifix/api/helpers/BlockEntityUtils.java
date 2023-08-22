package com.tzaranthony.scifix.api.helpers;

import com.tzaranthony.scifix.api.handlers.FluidHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockEntityUtils {
    public static void transferToTank(ItemStackHandler itemHandler, FluidHandler fluidHandler, int inputSlot, int outputSlot, int tankSlot) {
        ItemStack inputStack = itemHandler.getStackInSlot(inputSlot);
        ItemStack outputStack = fluidHandler.fillOrEmptyItemUsingTank(inputStack, tankSlot);
        if (inputStack.sameItem(outputStack)) {
            itemHandler.extractItem(inputSlot, 1, false);
            itemHandler.insertItem(outputSlot, outputStack, false);
        }
    }

    public static Container createContainer(IItemHandler handler) {
        Container container = new SimpleContainer(handler.getSlots());
        for (int i = 0; i < handler.getSlots(); i++) {
            container.setItem(i, handler.getStackInSlot(i));
        }
        return container;
    }


    public static void dropFluidMultiple(FluidHandler tanks, Level level, BlockPos pos) {
        int maxFluid = -1;
        FluidStack current = FluidStack.EMPTY;
        for (int i = 0; i < tanks.getTanks(); i++) {
            if (maxFluid < tanks.getFluidInTank(i).getAmount()) {
                current = tanks.getFluidInTank(i);
                maxFluid = current.getAmount();
            }
        }

        if (!current.isEmpty()) {
            dropFluid(current, level, pos);
        }
    }

    public static void dropFluid(FluidStack stack, Level level, BlockPos pos) {
        level.setBlock(pos, stack.getFluid().defaultFluidState().createLegacyBlock(), 11);
    }




    public static class SidedItemHandler implements IItemHandlerModifiable {
        protected final ItemStackHandler inv;
        protected final List<Integer> insertSlots;
        protected final List<Integer> extractSlots;

        public SidedItemHandler(ItemStackHandler inv, List<Integer> insertSlots, List<Integer> extractSlots) {
            this.inv = inv;
            this.insertSlots = insertSlots;
            this.extractSlots = extractSlots;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            SidedItemHandler that = (SidedItemHandler) o;
            return inv.equals(that.inv);
        }

        @Override
        public int hashCode() {
            return inv.hashCode();
        }

        @Override
        public int getSlots() {
            return inv.getSlots();
        }

        @Override
        @Nonnull
        public ItemStack getStackInSlot(int slot) {
            return inv.getStackInSlot(slot);
        }

        @Override
        @Nonnull
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!insertSlots.contains(slot))
                return ItemStack.EMPTY;
            return inv.insertItem(slot, stack, simulate);
        }

        @Override
        public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
            inv.setStackInSlot(slot, stack);
        }

        @Override
        @Nonnull
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (!extractSlots.contains(slot))
                return ItemStack.EMPTY;
            return inv.extractItem(slot, amount, simulate);
        }

        @Override
        public int getSlotLimit(int slot) {
            return inv.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return inv.isItemValid(slot, stack);
        }
    }


    //TODO: since we now have fluid tanks with a specified input/output direction, do we need this? I don't think so
    public static class SidedFluidHandler implements IFluidTank {

        @NotNull
        @Override
        public FluidStack getFluid() {
            return null;
        }

        @Override
        public int getFluidAmount() {
            return 0;
        }

        @Override
        public int getCapacity() {
            return 0;
        }

        @Override
        public boolean isFluidValid(FluidStack stack) {
            return false;
        }

        @Override
        public int fill(FluidStack resource, IFluidHandler.FluidAction action) {
            return 0;
        }

        @NotNull
        @Override
        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            return null;
        }

        @NotNull
        @Override
        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            return null;
        }
    }
}