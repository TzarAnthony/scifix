package com.tzaranthony.scifix.core.container.slots;

import com.tzaranthony.scifix.api.handlers.ItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class ItemHandlerSlot extends SlotItemHandler {
    public ItemHandlerSlot(ItemHandler itemHandler, int id, int x, int y) {
        super(itemHandler, id, x, y);
    }

    @Override
    public boolean mayPickup(Player playerIn) {
        return !this.getHandler().extractItemDirectionless(this.getSlotIndex(), 1, true).isEmpty();
    }

    @Override
    @Nonnull
    public ItemStack remove(int amount) {
        return this.getHandler().extractItemDirectionless(this.getSlotIndex(), amount, false);
    }

    public ItemHandler getHandler() {
        return (ItemHandler) super.getItemHandler();
    }
}