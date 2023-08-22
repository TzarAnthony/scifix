package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import com.tzaranthony.scifix.core.crafting.RfRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public abstract class BulkCraftingBE extends CraftingBE {
    protected Integer tier;
    protected static final String TIER_VAL = "SCIFIX_Machine_Tier";
    protected Integer processSize;
    protected static final String SIZE_VAL = "SCIFIX_Process_Stack_Size";
    protected boolean decays;

    public BulkCraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType, int tier, boolean decays) {
        super(type, pos, state, recipeType);
        this.decays = decays;
        this.tier = tier;
        this.maxTime = 200 - getTierMaxTimeReduction(tier);
        this.processSize = getTierCapacity(tier);
        this.rfHandler = new EnergyHandler(40000 + 10000 * tier, 500 + 500 * tier, 0);
        this.rfHandler.setEnergy(this.rfHandler.getMaxEnergyStored()); //TODO: remove this after testing
    }

    protected int getTierMaxTimeReduction(int tier) {
        return 30 * tier;
    }

    protected int getTierCapacity(int tier) {
        int x = (int) Math.pow(2, tier);
        return x + Math.min(tier, 1) * x;
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TIER_VAL)) {
            this.tier = tag.getInt(TIER_VAL);
        }
        this.processSize = tag.getInt(SIZE_VAL);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(TIER_VAL, this.tier);
        tag.putInt(SIZE_VAL, this.processSize);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemCap.cast(); //TODO: will this work for the furnaces?
        }
        return super.getCapability(capability, facing);
    }

    public static boolean processTick(Level level, BlockPos pos, BlockState state, BulkCraftingBE bcBE) {
        boolean flag = false;
        if (bcBE.itemHandler != null && bcBE.shouldCraft()) {
            Container container = bcBE.createContainer();
            Object recipe = level.getRecipeManager().getRecipeFor(bcBE.recipeType, container, level).orElse(null);
            if (recipe != null && bcBE.canCraft(recipe)) {
                ++bcBE.progress;
                //TODO: should I support changing the max time in recipes?
                if (bcBE.progress >= bcBE.maxTime) {
                    bcBE.progress = 0;
                    bcBE.craft(recipe);
                    flag = true;
                }
            }
            if (flag && level.getGameTime() % 80L == 0L) {
                bcBE.playProcessingSound(pos);
            }
        } else if (bcBE.decays && bcBE.progress > 0) {
            bcBE.progress = Mth.clamp(bcBE.progress - 2, 0, bcBE.maxTime);
        }
        return flag;
    }

    protected abstract boolean shouldCraft();

    protected abstract boolean canCraft(Object recipe);

    protected abstract void craft(Object recipe);

    protected abstract void playProcessingSound(BlockPos pos);

    protected void playSound(BlockPos pos, SoundEvent sound, float volume, float pitch) {
        this.level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.BLOCKS, volume, pitch);
    }
}