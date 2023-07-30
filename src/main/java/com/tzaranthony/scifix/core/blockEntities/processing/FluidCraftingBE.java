package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.core.blockEntities.CraftingBE;
import com.tzaranthony.scifix.core.container.handlers.SFluidTankHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class FluidCraftingBE extends CraftingBE {
    protected SFluidTankHandler fluidHandler;
    protected final String FLUIDINV = "Fluids";

    public FluidCraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType) {
        super(type, pos, state, recipeType);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (fluidHandler != null) {
            fluidHandler.deserializeNBT(tag.getCompound(FLUIDINV));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (fluidHandler != null) {
            tag.put(FLUIDINV, fluidHandler.serializeNBT());
        }
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (fluidHandler != null) {
            tag.put(FLUIDINV, fluidHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<? extends SFluidTankHandler> fluidCap = LazyOptional.of(() -> fluidHandler);
    protected Map<Direction, LazyOptional<SidedFluidHandler>> fluidDirectionHandler;

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) { //TODO: is this correct?
            return this.fluidDirectionHandler.get(facing).cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fluidCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.fluidCap = LazyOptional.of(() -> fluidHandler);
    }

    //TODO: fix this with the fluid handler implementation
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

    @Override
    public void dropInventory() {
        super.dropInventory();
        this.dropFluid();
    }

    public void dropFluid() {
        int maxFluid = -1;
        FluidStack current = FluidStack.EMPTY;
        for (int i = 0; i < fluidHandler.getTanks(); i++) {
            if (maxFluid < fluidHandler.getFluidInTank(i).getAmount()) {
                current = fluidHandler.getFluidInTank(i);
                maxFluid = current.getAmount();
            }
        }

        if (!current.isEmpty()) {
            this.level.setBlock(this.worldPosition, current.getFluid().defaultFluidState().createLegacyBlock(), 11);
        }
    }
}