package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.api.handlers.BEHelpers.HeatBE;
import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import com.tzaranthony.scifix.api.handlers.HeatHandler;
import com.tzaranthony.scifix.api.handlers.IHeatHandler;
import com.tzaranthony.scifix.api.handlers.SCapabilities;
import com.tzaranthony.scifix.api.helpers.Maths;
import com.tzaranthony.scifix.api.properties.ThermalProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public abstract class HeatCraftingBE extends CraftingBE implements HeatBE {
    protected HeatHandler heatHandler;
    protected static final String HEAT_INV = "SCIFIX_Heat";

    public HeatCraftingBE(BlockEntityType<?> type, ThermalProperties properties, BlockPos pos, BlockState state, RecipeType recipeType) {
        super(type, pos, state, recipeType);
        this.heatHandler = new HeatHandler(properties.getSpecificHeat(), properties.getThermalConductivity(), Maths.MCTemperatureToC(this.level, this.getBlockPos()));
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.heatHandler.deserializeNBT(tag.get(HEAT_INV));
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(HEAT_INV, this.heatHandler.serializeNBT());
    }

    public void setEnergyHandler(HeatHandler hh) {
        this.heatHandler = hh;
    }

    public HeatHandler getEnergyHandler() {
        return this.heatHandler;
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (this.heatHandler != null) {
            tag.put(HEAT_INV, this.heatHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<IHeatHandler> hCap = LazyOptional.of(() -> heatHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == SCapabilities.HEAT) {
            return this.hCap.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        hCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.hCap = LazyOptional.of(() -> heatHandler);
    }
}