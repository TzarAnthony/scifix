package com.tzaranthony.scifix.core.blockEntities.processing.machines;

import com.tzaranthony.scifix.api.handlers.BEHelpers.FluidBE;
import com.tzaranthony.scifix.api.handlers.FluidHandler;
import com.tzaranthony.scifix.api.handlers.IDirectional;
import com.tzaranthony.scifix.api.helpers.BlockEntityUtils;
import com.tzaranthony.scifix.core.blockEntities.processing.BulkCraftingBE;
import com.tzaranthony.scifix.core.crafting.FluidRecipe;
import com.tzaranthony.scifix.core.network.FluidS2CPacket;
import com.tzaranthony.scifix.registries.SPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public abstract class BulkFluidCraftingBE extends BulkCraftingBE implements FluidBE {
    protected FluidHandler fluidHandler;
    protected final String FLUID_INV = "SCIFIX_Fluids";

    public BulkFluidCraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType, int tier, boolean decays) {
        super(type, pos, state, recipeType, tier, decays);

        this.fluidHandler = new FluidHandler(10000, fluid -> fluid.getFluid().isSame(Fluids.WATER), IDirectional.Direction.EITHER) {
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    SPackets.sendToClients(new FluidS2CPacket(this, worldPosition));
                }
            }
        };
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.fluidHandler.deserializeNBT(tag.getCompound(FLUID_INV));
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BulkFluidCraftingBE fBE) {
        BlockEntityUtils.transferToTank(fBE.itemHandler, fBE.fluidHandler, 0, 1, 0);
    }

    public static boolean processTick(Level level, BlockPos pos, BlockState state, BulkFluidCraftingBE bcBE) {
        boolean flag = false;
        if (bcBE.itemHandler != null && bcBE.fluidHandler != null && bcBE.shouldCraft()) {
            Container container = BlockEntityUtils.createContainer(bcBE.itemHandler);
            FluidRecipe recipe = (FluidRecipe) level.getRecipeManager().getRecipeFor(bcBE.recipeType, container, level).orElse(null);
            if (recipe != null && bcBE.canCraft(recipe)) {
                ++bcBE.progress;
                flag = true;
                if (bcBE.progress >= bcBE.maxTime) {
                    bcBE.progress = 0;
                    bcBE.craft(recipe);
                }
            }
            if (flag && level.getGameTime() % 80L == 0L) {
                bcBE.playProcessingSound(pos);
            }
        }
        return flag;
    }

    protected boolean canCraft(Object recipe) {
        return this.canCraft((FluidRecipe) recipe);
    }

    protected void craft(Object recipe) {
        this.craft((FluidRecipe) recipe);
    }

    protected abstract boolean shouldCraft();

    protected abstract boolean canCraft(FluidRecipe recipe);

    protected abstract void craft(FluidRecipe recipe);

    protected abstract void playProcessingSound(BlockPos pos);

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (this.fluidHandler != null) {
            tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<? extends FluidHandler> fluidCap = LazyOptional.of(() -> fluidHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidCap.cast();
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
        this.fluidCap = LazyOptional.of(() -> fluidHandler);
    }

    public void setFluidHandler(FluidHandler fh) {
        this.fluidHandler = fh;
    }

    public FluidHandler getFluidHandler() {
        return this.fluidHandler;
    }
}