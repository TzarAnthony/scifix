package com.tzaranthony.scifix.core.blockEntities.processing;

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

public abstract class MultiCraftingBE extends InteractableBE {
    protected Integer tier;
    protected static final String TIER_VAL = "SCIFIX_Machine_Tier";
    protected List<Integer> progressList;
    protected static final String PROG_LIST = "SCIFIX_Progress_List";
    protected boolean decays;
    protected List<RfRecipe> cachedRecipes;

    public MultiCraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType, int tier, boolean decays) {
        super(type, pos, state, recipeType);
        this.decays = decays;
        this.setTier(tier);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains(TIER_VAL)) {
            this.tier = tag.getInt(TIER_VAL);
        }
        int capacity = tag.getInt(PROG_LIST);
        this.progressList = NonNullList.withSize(capacity, 0);
        for (int i = 0; i < this.progressList.size(); ++i) {
            this.progressList.set(i, tag.getInt(PROG_LIST + i));
        }
        this.cachedRecipes = Arrays.asList((new RfRecipe[capacity]));
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(TIER_VAL, this.tier);
        tag.putInt(PROG_LIST, this.progressList.size());
        for (int i = 0; i < this.progressList.size(); ++i) {
            tag.putInt(PROG_LIST + i, this.progressList.get(i));
        }
    }

    public abstract void setTier(int tier);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return itemCap.cast(); //TODO: will this work for the furnaces?
        }
        return super.getCapability(capability, facing);
    }

    protected abstract int getTierMaxTimeReduction(int tier);

    protected abstract int getTierCapacity(int tier);

    public static boolean processTick(Level level, BlockPos pos, BlockState state, MultiCraftingBE tcBE) {
        boolean flag = false;
        if (tcBE.itemHandler != null && tcBE.shouldCraft()) {
            for (int i = 0; i < tcBE.itemHandler.getSlots(); ++i) {
                flag = flag || tcBE.tickSlot(level, pos, tcBE, i);
            }
            if (flag && level.getGameTime() % 80L == 0L) {
                tcBE.playProcessingSound(pos);
            }
        } else if (tcBE.decays && tcBE.progressList.stream().anyMatch((j) -> j > 0)) {
            for (int i = 0; i < tcBE.progressList.size(); ++i) {
                tcBE.progressList.set(i, Mth.clamp(tcBE.progressList.get(i) - 2, 0, tcBE.maxTime));
            }
        }
        return flag;
    }

    protected abstract boolean shouldCraft();

    protected boolean tickSlot(Level level, BlockPos pos, MultiCraftingBE tcBE, int i) {
        boolean flag = false;
        ItemStack stack = tcBE.itemHandler.getStackInSlot(i);
        if (!stack.isEmpty()) {
            RfRecipe recipe = tcBE.cachedRecipes.get(i);
            if (recipe == null) {
                Container container = new SimpleContainer(1);
                container.setItem(0, stack.copy());
                recipe = (RfRecipe) level.getRecipeManager().getRecipeFor(tcBE.recipeType, container, level).orElse(null);
                tcBE.cachedRecipes.set(i, recipe);
            }

            if (recipe != null && tcBE.canCraft(i, recipe)) {
                int nProg = tcBE.progressList.get(i) + 1;
                // should I support changing the max time in recipes?
                if (nProg >= tcBE.maxTime) {
                    nProg = 0;
                    tcBE.craft(i, recipe);
                    flag = true;
                }
                tcBE.progressList.set(i, nProg);
            }
        }
        return flag;
    }

    protected abstract boolean canCraft(int i, RfRecipe recipe);

    protected abstract void craft(int i, RfRecipe recipe);

    protected abstract void playProcessingSound(BlockPos pos);

    protected void playSound(BlockPos pos, SoundEvent sound, float volume, float pitch) {
        this.level.playSound((Player) null, pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.BLOCKS, volume, pitch);
    }
}