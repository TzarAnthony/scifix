package com.tzaranthony.scifix.core.blockEntities.helpers;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaInventoryCodeHooks;

public interface IAutoInserting {
    default ItemStack doForgeInsert(ItemStack stack, Level level, BlockPos pos, Direction dir) {
        return VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), dir)
                .map(destinationResult -> {
                    IItemHandler itemHandler = destinationResult.getKey();
                    if (isFull(itemHandler)) {
                        return stack;
                    } else {
                        ItemStack remainder = putStackInInventoryAllSlots(itemHandler, stack);
                        return remainder;
                    }
                })
                .orElse(stack);
    }

    private static boolean isFull(IItemHandler itemHandler) {
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            ItemStack stackInSlot = itemHandler.getStackInSlot(slot);
            if (stackInSlot.isEmpty() || stackInSlot.getCount() < itemHandler.getSlotLimit(slot)) {
                return false;
            }
        }
        return true;
    }

    private static ItemStack putStackInInventoryAllSlots(IItemHandler destInventory, ItemStack stack) {
        for (int slot = 0; slot < destInventory.getSlots() && !stack.isEmpty(); slot++) {
            if (stack.getCount() > destInventory.insertItem(slot, stack, true).getCount()) {
                ItemStack remainder = destInventory.insertItem(slot, stack, false);
                if (remainder.isEmpty()) {
                    return remainder;
                }
            }
        }
        return stack;
    }
}