package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.api.handlers.FluidHandler;
import com.tzaranthony.scifix.api.helpers.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class FluidCraftingBE extends CraftingBE {
    protected FluidHandler fluidHandler;
    protected final String FLUID_INV = "SCIFIX_Fluids";

    public FluidCraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType) {
        super(type, pos, state, recipeType);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (this.fluidHandler != null) {
            this.fluidHandler.deserializeNBT(tag.getCompound(FLUID_INV));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.fluidHandler != null) {
            tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
        }
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (this.fluidHandler != null) {
            tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<? extends FluidHandler> fluidCap = LazyOptional.of(() -> this.fluidHandler);
    protected Map<Direction, LazyOptional<BlockEntityUtils.SidedFluidHandler>> fluidDirectionHandler;

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
        this.fluidCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.fluidCap = LazyOptional.of(() -> this.fluidHandler);
    }

    @Override
    public void dropInventory() {
        super.dropInventory();
        BlockEntityUtils.dropFluidMultiple(this.fluidHandler, this.level, this.worldPosition);
    }
}