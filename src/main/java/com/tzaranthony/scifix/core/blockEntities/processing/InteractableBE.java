package com.tzaranthony.scifix.core.blockEntities.processing;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class InteractableBE extends EnergyCraftingBE {
    public InteractableBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType) {
        super(type, pos, state, recipeType);
    }

    protected abstract void playRemoveItemSound();

    protected abstract void playRemoveAllSound();

    protected abstract void playAddItemSound();

    protected abstract int getMaxInputSlots();

    public boolean takeOrAddItem(Player player, ItemStack stack) {
        if (player.isShiftKeyDown()) {
            for (int i = 0; i < this.itemHandler.getSlots(); ++i) {
                ItemStack altStack = this.itemHandler.extractItem(i, this.itemHandler.getStackInSlot(i).getCount(), false);
                if (!altStack.isEmpty()) {
                    addOrPopItem(player, altStack, false);
                }
            }
            this.playRemoveAllSound();
            return true;
        } else if (stack.isEmpty()) {
            for (int i = this.itemHandler.getSlots() - 1; i > -1; --i) {
                ItemStack altStack = this.itemHandler.extractItem(i, this.itemHandler.getStackInSlot(i).getCount(), false);
                if (!altStack.isEmpty()) {
                    addOrPopItem(player, altStack, true);
                    return true;
                }
            }
        } else if (!stack.isEmpty()) {
            for (int i = 0; i < this.getMaxInputSlots(); ++i) {
                if (stack.getCount() > this.itemHandler.insertItem(i, stack, true).getCount()) {
                    ItemStack remainder = this.itemHandler.insertItem(i, stack.copy(), false);
                    stack.setCount(remainder.getCount());
                    this.playAddItemSound();
                    update();
                    return true;
                }
            }
        }
        return false;
    }

    protected void addOrPopItem(Player player, ItemStack stack, boolean sound) {
        player.getInventory().add(stack);
        if (!stack.isEmpty()) {
            this.level.addFreshEntity(new ItemEntity(this.level, player.getX(), player.getY(), player.getZ(), stack));
        }
        if (sound) {
            if (stack.isEmpty()) {
                this.playAddItemSound();
            } else {
                this.playRemoveItemSound();
            }
        }
        this.update();
    }
}