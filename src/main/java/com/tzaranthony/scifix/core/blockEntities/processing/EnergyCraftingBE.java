package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.core.blockEntities.CraftingBE;
import com.tzaranthony.scifix.core.container.handlers.SRFHandler;
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
import java.util.Map;

public abstract class EnergyCraftingBE extends CraftingBE {
    protected SRFHandler rfHandler;
    protected static final String RF_INV = "SCIFIX_RF";

    public EnergyCraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType) {
        super(type, pos, state, recipeType);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (this.rfHandler != null) {
            this.rfHandler.setEnergy(tag.getInt(RF_INV));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.rfHandler != null) {
            tag.putInt(RF_INV, this.rfHandler.getEnergyStored());
        }
    }


    protected LazyOptional<IEnergyStorage> rfCap = LazyOptional.of(() -> rfHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return this.rfCap.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        rfCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.rfCap = LazyOptional.of(() -> rfHandler);
    }
}