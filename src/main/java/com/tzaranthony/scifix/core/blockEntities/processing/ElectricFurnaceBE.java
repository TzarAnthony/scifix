package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.api.handlers.IDirectional;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ElectricFurnaceBE extends BulkCraftingBE {
    public ElectricFurnaceBE(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public ElectricFurnaceBE(BlockPos pos, BlockState state, int tier) {
        super(SBlockEntities.E_FURNACE.get(), pos, state, RecipeType.SMELTING, tier, true);
        this.itemHandler.setSize(2);
        this.itemHandler.setDirections(List.of(IDirectional.Direction.INPUT, IDirectional.Direction.OUTPUT));
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, ElectricFurnaceBE efBE) {
        //TODO: add client side animation
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ElectricFurnaceBE efBE) {
        if (processTick(level, pos, state, efBE)) {
            efBE.update();
            setChanged(level, pos, state);
        }
    }

    protected boolean shouldCraft() {
        return !this.itemHandler.getStackInSlot(0).isEmpty() && this.rfHandler.getEnergyStored() > 0;
    }

    protected boolean canCraft(Object recipe) {
        ItemStack simStack = this.itemHandler.createItem(1, ((SmeltingRecipe) recipe).getResultItem().copy(), true);
        return simStack.isEmpty() && 50 <= this.rfHandler.useEnergy(50 * this.tier, false);
    }

    protected void craft(Object recipe) {
        SmeltingRecipe recipeS = (SmeltingRecipe) recipe;
        int craftAmount = Math.min(this.processSize, this.itemHandler.getStackInSlot(0).getCount());
        ItemStack output = recipeS.getResultItem().copy();
        int recipeAmt = output.getCount();
        output.setCount(craftAmount * recipeAmt);
        ItemStack simStack = this.itemHandler.createItem(1, output, true);
        if (!simStack.isEmpty()) {
            craftAmount -= Math.floor(simStack.getCount() / recipeAmt);
            output.setCount(craftAmount * recipeAmt);
        }
        this.itemHandler.consumeItem(0, craftAmount, false);
        this.itemHandler.createItem(1, output, false);
    }

    protected void playProcessingSound(BlockPos pos) {

    }
}