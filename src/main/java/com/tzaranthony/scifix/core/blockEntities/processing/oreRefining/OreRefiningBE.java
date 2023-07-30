package com.tzaranthony.scifix.core.blockEntities.processing.oreRefining;

import com.tzaranthony.scifix.core.blockEntities.helpers.IAutoInserting;
import com.tzaranthony.scifix.core.blockEntities.helpers.IAutoSucking;
import com.tzaranthony.scifix.core.blockEntities.processing.MultiCraftingBE;
import com.tzaranthony.scifix.core.crafting.AbstractOreRefiningRecipe;
import com.tzaranthony.scifix.core.crafting.RfRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;

public abstract class OreRefiningBE extends MultiCraftingBE implements IAutoInserting, IAutoSucking {
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

    protected int getTierMaxTimeReduction(int tier) {
        return 30 * tier;
    }

    protected int getTierCapacity(int tier) {
        return 1 + tier * 2;
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, OreRefiningBE oBE) {
        //TODO: add client side animation
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, OreRefiningBE oBE) {
        if (processTick(level, pos, state, oBE) || IAutoSucking.pickupTick(level, oBE)) {
            oBE.update(); //TODO: make sure the capability only accept valid items
            setChanged(level, pos, state);
        }
    }

    protected boolean shouldCraft() {
        if (this.rfHandler.getEnergyStored() == 0) return false;
        for (int i = 0; i < this.itemHandler.getSlots(); ++i) {
            if (!this.itemHandler.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    protected boolean canCraft(int i, RfRecipe recipeRF) {
        AbstractOreRefiningRecipe recipe = (AbstractOreRefiningRecipe) recipeRF;
        int consumed = this.rfHandler.useEnergy(recipe.getRF(), false);
        return consumed == recipe.getRF();
    }

    protected void craft(int i, RfRecipe recipeRF) {
        AbstractOreRefiningRecipe recipe = (AbstractOreRefiningRecipe) recipeRF;
        this.itemHandler.extractItem(i, 1, false);
        this.outputOrDropResult(recipe.assemble());
        HashMap<ItemStack, Float> outputMap = recipe.getSecondaryProbMap();
        for (ItemStack stack: outputMap.keySet()) {
            if (outputMap.get(stack) >= this.level.random.nextFloat()) {
                this.outputOrDropResult(stack.copy());
            }
        }
    }

    protected boolean outputOrDropResult(ItemStack stack) {
        stack = this.doForgeInsert(stack, this.level, this.worldPosition.below(), Direction.UP);
        if (stack.isEmpty()) return true;
        return this.level.addFreshEntity(new ItemEntity(this.level, this.getCenterX(), this.worldPosition.getY() - 0.3D, this.getCenterZ(), stack));
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

    protected void playRemoveItemSound() {
        this.level.playSound((Player) null, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 1.0F);
    }

    protected void playRemoveAllSound() {
        this.level.playSound((Player) null, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.2F, 1.0F);
    }

    protected void playAddItemSound() {
        this.level.playSound((Player) null, this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 0.2F, 1.0F);
    }
}