package com.tzaranthony.scifix.core.blockEntities.processing.machines;

import com.tzaranthony.scifix.api.handlers.DirectionalMultiFluidTank;
import com.tzaranthony.scifix.api.handlers.IDirectional;
import com.tzaranthony.scifix.core.crafting.FluidRecipe;
import com.tzaranthony.scifix.core.crafting.MixingRecipe;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.List;

public class MixerBE extends BulkFluidCraftingBE {
    public MixerBE(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public MixerBE(BlockPos pos, BlockState state, int tier) {
        super(SBlockEntities.MIXER.get(), pos, state, MixingRecipe.TYPE, tier, false);
    }

    @Override
    protected void setupItemHandler() {
        this.itemHandler.setSize(5);
        this.itemHandler.setDirections(List.of(IDirectional.Direction.INPUT, IDirectional.Direction.INPUT, IDirectional.Direction.INPUT
                , IDirectional.Direction.OUTPUT, IDirectional.Direction.EITHER));
    }

    @Nullable
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return null;
    }

    public Component getDisplayName() {
        return new TranslatableComponent("container.scifix.mixer");
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ElectricFurnaceBE pBE) {
        //TODO: add client side animation
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, MixerBE pBE) {
        if (processTick(level, pos, state, pBE)) {
            pBE.update();
            setChanged(level, pos, state);
        }
    }

    protected boolean shouldCraft() {
        return (!this.itemHandler.getStackInSlot(0).isEmpty() || !this.fluidHandler.getFluidsInTank(0).isEmpty()) && this.rfHandler.getEnergyStored() > 0;
    }

    protected boolean canCraft(FluidRecipe recipe) {;
        ItemStack simStack = this.itemHandler.insertItemDirectionless(3, recipe.getResultItem().copy(), true);
        boolean hasFluids = recipe.fluidsMatch(this.fluidHandler.getTank(0), IFluidHandler.FluidAction.SIMULATE);
        int filledAmt = this.fluidHandler.createFluid(1, recipe.getResultFluid().copy(), IFluidHandler.FluidAction.SIMULATE);
        return simStack.isEmpty() && hasFluids && filledAmt == recipe.getResultFluid().getAmount() && recipe.getRF() * this.processSize == this.rfHandler.useEnergy(recipe.getRF() * this.processSize, false);
    }

    protected void craft(FluidRecipe recipe) {
        ItemStack inOutput = this.itemHandler.getStackInSlot(3);
        int multiplier = Math.min(
                Math.min(
                        Math.min(this.itemHandler.getStackInSlot(0).getCount(), this.itemHandler.getStackInSlot(1).getCount()),
                        Math.min(this.itemHandler.getStackInSlot(2).getCount(), inOutput.getMaxStackSize() - inOutput.getCount())
                ),
                Math.min(recipe.fluidsMatchTimes(this.fluidHandler.getTank(0), this.processSize)
                        , this.fluidHandler.getTank(1).getSpace() / recipe.getResultFluid().getAmount())
        );
        this.itemHandler.extractItemDirectionless(0, multiplier, false);
        this.itemHandler.extractItemDirectionless(1, multiplier, false);
        this.itemHandler.extractItemDirectionless(2, multiplier, false);
        for (int i = 0; i < multiplier; ++i) {
            recipe.fluidsMatch(this.fluidHandler.getTank(0), IFluidHandler.FluidAction.EXECUTE);
            this.fluidHandler.createFluid(1, recipe.getResultFluid().copy(), IFluidHandler.FluidAction.EXECUTE);
            this.itemHandler.insertItemDirectionless(3, recipe.getResultItem().copy(), false);
        }
    }

    @Override
    protected void playProcessingSound(BlockPos pos) {

    }
}