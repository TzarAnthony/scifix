package com.tzaranthony.scifix.core.blockEntities.processing.machines;

import com.tzaranthony.scifix.core.crafting.FluidRecipe;
import com.tzaranthony.scifix.core.crafting.MixingRecipe;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;

public class MixerBE extends BulkFluidCraftingBE {
    public MixerBE(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public MixerBE(BlockPos pos, BlockState state, int tier) {
        super(SBlockEntities.MIXER.get(), pos, state, MixingRecipe.TYPE, tier, false);
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
        ItemStack simStack = this.itemHandler.insertItemDirectionless(1, recipe.getResultItem().copy(), true);
        int filledAmt = this.fluidHandler.createFluid(1, recipe.getResultFluid().copy(), IFluidHandler.FluidAction.SIMULATE);
        return simStack.isEmpty() && filledAmt == recipe.getResultFluid().getAmount() && recipe.getRF() * this.processSize == this.rfHandler.useEnergy(recipe.getRF() * this.processSize, false);
    }

    protected void craft(FluidRecipe recipe) {

    }

    @Override
    protected void playProcessingSound(BlockPos pos) {

    }

    @Override
    protected void setupItemHandler() {

    }
}