package com.tzaranthony.scifix.core.blockEntities.processing.machines.oreRefining;

import com.tzaranthony.scifix.api.helpers.IAutoInserting;
import com.tzaranthony.scifix.api.helpers.IAutoSucking;
import com.tzaranthony.scifix.core.blockEntities.processing.BulkCraftingBE;
import com.tzaranthony.scifix.core.crafting.AbstractOreRefiningRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;

public abstract class OreRefiningBE extends BulkCraftingBE implements IAutoInserting, IAutoSucking {
    private final VoxelShape INSIDE = Block.box(2.0D, 11.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private final VoxelShape ABOVE = Block.box(0.0D, 16.0D, 0.0D, 16.0D, 32.0D, 16.0D);
    private final VoxelShape SUCK = Shapes.or(INSIDE, ABOVE);
    protected int cooldownTime = -1;

    public OreRefiningBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType, int tier) {
        super(type, pos, state, recipeType, tier, false);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.loadCooldown(tag);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        this.saveCooldown(tag);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, OreRefiningBE oBE) {
        //TODO: add client side animation
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, OreRefiningBE oBE) {
        if (processTick(level, pos, state, oBE) || IAutoSucking.pickupTick(level, oBE)) {
            oBE.update();
            setChanged(level, pos, state);
        }
    }

    protected boolean shouldCraft() {
        return !this.itemHandler.getStackInSlot(0).isEmpty() && this.rfHandler.getEnergyStored() > 0;
    }

    protected boolean canCraft(Object recipe) {
        AbstractOreRefiningRecipe recipeOR = (AbstractOreRefiningRecipe) recipe;
        int consumed = this.rfHandler.useEnergy(recipeOR.getRF() * this.processSize, false);
        return consumed == recipeOR.getRF() * this.processSize;
    }

    protected void craft(Object recipe) {
        AbstractOreRefiningRecipe recipeOR = (AbstractOreRefiningRecipe) recipe;
        int craftAmount = Math.min(this.processSize, this.itemHandler.getStackInSlot(0).getCount());
        this.itemHandler.extractItemDirectionless(0, craftAmount, false);
        ItemStack output = recipeOR.assemble();
        output.setCount(craftAmount * output.getCount());
        this.outputOrDropResult(output);
        HashMap<ItemStack, Float> outputMap = recipeOR.getSecondaryProbMap();
        for (ItemStack stack: outputMap.keySet()) {
            int secondaryAmount = 0;
            for (int i = 0; i < craftAmount; ++i) {
                if (outputMap.get(stack) >= this.level.random.nextFloat()) {
                    secondaryAmount += stack.getCount();
                }
            }
            ItemStack secondary = stack.copy();
            secondary.setCount(secondaryAmount);
            this.outputOrDropResult(secondary);
        }
    }

    protected boolean outputOrDropResult(ItemStack stack) {
        stack = this.doForgeInsert(stack, this.level, this.worldPosition.below(), Direction.UP);
        if (stack.isEmpty()) return true;
        return this.level.addFreshEntity(new ItemEntity(this.level, this.getCenterX(), this.worldPosition.getY() - 0.6D, this.getCenterZ(), stack));
    }

    public int getMinInputSlots() {
        return 0;
    }

    public int getMaxInputSlots() {
        return this.itemHandler.getSlots();
    }

    public ItemStack putItemStack(int slot, ItemStack stack, boolean simulate) {
        return this.itemHandler.insertItem(slot, stack, simulate);
    }

    public void setCooldown(int i) {
        this.cooldownTime = i;
    }

    public int getCooldown() {
        return this.cooldownTime;
    }

    public VoxelShape getSuckShape() {
        return SUCK;
    }

    public double getCenterX() {
        return this.worldPosition.getX() + 0.5D;
    }

    public double getCenterY() {
        return this.worldPosition.getY() + 0.5D;
    }

    public double getCenterZ() {
        return this.worldPosition.getZ() + 0.5D;
    }

    public void resetTransferCooldown() {
        this.cooldownTime = 10;
    }
}