package com.tzaranthony.scifix.core.crafting;

import com.tzaranthony.scifix.api.handlers.DirectionalMultiFluidTank;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public abstract class FluidRecipe extends RfRecipe {
    protected final ResourceLocation id;
    protected final int rf;
    protected final NonNullList<FluidStack> fluidInputs;
    protected final FluidStack fluidOutput;

    public FluidRecipe(ResourceLocation id, int rf, NonNullList<FluidStack> fluidInputs, FluidStack fluidOutput) {
        this.id = id;
        this.rf = rf;
        this.fluidInputs = fluidInputs;
        this.fluidOutput = fluidOutput;
    }

    public int getRF() {
        return this.rf;
    }

    public NonNullList<FluidStack> getFluidIngredients() {
        return this.fluidInputs;
    }

    public FluidStack assembleFluid() {
        return this.fluidOutput.copy();
    }

    public FluidStack getResultFluid() {
        return this.fluidOutput;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public boolean fluidsMatch(DirectionalMultiFluidTank tank, IFluidHandler.FluidAction action) {
        for (int i = 0; i < this.fluidInputs.size(); i++) {
            FluidStack ingStack = this.fluidInputs.get(i);
//            boolean breaker = false;
            FluidStack stack = tank.consumeFluid(ingStack, action);
            if (stack.getAmount() == ingStack.getAmount()) {
                break;
            }
//            for (int j = 0; j < tank.getFluids().size(); ++j) {
//                FluidStack tankStack = tank.getFluids().get(j);
//                if (ingStack.isFluidEqual(tankStack) && tankStack.getAmount() >= ingStack.getAmount()) {
//                    breaker = true;
//                    break;
//                }
//            }
//            if (breaker) break;
            return false;
        }
        return true;
    }

    public int fluidsMatchTimes(DirectionalMultiFluidTank tank, int times) {
        for (int i = 0; i < this.fluidInputs.size(); i++) {
            FluidStack ingStack = this.fluidInputs.get(i);
            FluidStack stack = tank.getFluid();
            if (stack.getAmount() == ingStack.getAmount()) {
                times = Math.min(times, stack.getAmount() / ingStack.getAmount());
            }
        }
        return times;
    }
}