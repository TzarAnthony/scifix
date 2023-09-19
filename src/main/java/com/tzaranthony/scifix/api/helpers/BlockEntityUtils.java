package com.tzaranthony.scifix.api.helpers;

import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import com.tzaranthony.scifix.api.handlers.FluidHandler;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
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
    public static void transferToTank(ItemHandler ih, FluidHandler fh, int inputSlot, int outputSlot, int tankSlot) {
        ItemStack inputStack = ih.getStackInSlot(inputSlot);
        ItemStack outputStack = fh.fillOrEmptyItemUsingTank(inputStack, tankSlot);
        if (inputStack.sameItem(outputStack)) {
            ih.extractItem(inputSlot, 1, false);
            ih.insertItem(outputSlot, outputStack, false);
        }
    }

    public static void transferEnergyToBlock(ItemHandler ih, EnergyHandler eh, int itemSlot) {
        ItemStack battery = ih.getStackInSlot(itemSlot);
        if (battery.isEmpty()) return; //TODO: fix this when I make actual battery items
        int transferred = eh.receiveEnergy(battery.getMaxDamage(), false);
        battery.setDamageValue(battery.getMaxDamage() - transferred);
    }

    public static void transferEnergyFromBlock(ItemHandler ih, EnergyHandler eh, int itemSlot) {
        ItemStack battery = ih.getStackInSlot(itemSlot);
        if (battery.isEmpty()) return; //TODO: fix this when I make actual battery items
        int transferred = eh.extractEnergy(battery.getMaxDamage(), false);
        battery.setDamageValue(battery.getDamageValue() + transferred);
    }

    public static Container createContainer(ItemHandler ih) {
        Container container = new SimpleContainer(ih.getSlots());
        for (int i = 0; i < ih.getSlots(); i++) {
            container.setItem(i, ih.getStackInSlot(i));
        }
        return container;
    }


    public static void dropFluidMultiple(FluidHandler fh, Level level, BlockPos pos) {
        int maxFluid = -1;
        FluidStack current = FluidStack.EMPTY;
        for (int i = 0; i < fh.getTanks(); i++) {
            if (maxFluid < fh.getFluidInTank(i).getAmount()) {
                current = fh.getFluidInTank(i);
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
}