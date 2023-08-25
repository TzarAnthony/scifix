package com.tzaranthony.scifix.core.container.menus;

import com.tzaranthony.scifix.api.handlers.BEHelpers.ItemBE;
import com.tzaranthony.scifix.api.handlers.BEHelpers.XpHoldingBE;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
import com.tzaranthony.scifix.core.container.slots.ItemHandlerSlot;
import com.tzaranthony.scifix.core.container.slots.ResultXPSlot;
import com.tzaranthony.scifix.core.container.slots.SResultSlot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseMenu extends AbstractContainerMenu {
    protected final BlockEntity blockEntity;
    protected final ItemHandler container;
    protected final ContainerData data;
    protected final Level level;
    protected final Inventory inventory;
    protected int minInputId = -1;
    protected int maxInputId;
    protected int maxTopSlotIndex = -1;
    protected int resultXpId;
    protected int resultId;

    protected BaseMenu(@Nullable MenuType<?> type, int id, Inventory inventory, BlockPos pos, ContainerData data) {
        super(type, id);
        this.blockEntity = inventory.player.level.getBlockEntity(pos);
        assert this.blockEntity instanceof ItemBE;
        this.container = ((ItemBE) this.blockEntity).getItemHandler();
        this.data = data;
        this.inventory = inventory;
        this.level = inventory.player.level;
    }

    protected void addInputSlotArray(int startId, int startX, int startY, int cols, int rows) {
        if (this.minInputId == -1) {
            this.minInputId = startId;
        }
        for (int i = 0; i < rows; ++i) {
            for (int j = 0; j < cols; ++j) {
                addInputSlot(startId, startX + (18 * j), startY + (18 * i));
                ++startId;
            }
        }
        this.maxInputId = startId - 1;
        updateMaxTopSlot(this.maxTopSlotIndex);
    }

    protected void addInputSlot(int id, int x, int y) {
        this.addSlot(new ItemHandlerSlot(this.container, id, x, y));
        if (this.minInputId == -1) {
            this.minInputId = id;
        }
        this.maxInputId = id;
        updateMaxTopSlot(this.maxTopSlotIndex);
    }

    public void addXPResultSlot(int id, int x, int y) {
        assert this.blockEntity instanceof XpHoldingBE;
        this.addSlot(new ResultXPSlot(this.inventory.player, this.container, ((XpHoldingBE) this.blockEntity).getXpHandler(), id, x, y));
        this.resultXpId = id;
        updateMaxTopSlot(this.resultXpId);
    }

    public void addResultSlot(int id, int x, int y) {
        this.addSlot(new SResultSlot(this.inventory.player, this.container, id, x, y));
        this.resultId = id;
        updateMaxTopSlot(this.resultId);
    }

    protected void addInventory() {
        this.addInventory(8, 84);
    }

    protected void addInventory(int x, int y) {
        // add inventory
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(this.inventory, j + i * 9 + 9, x + j * 18, y + i * 18));
            }
        }
        // add hotbar
        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(this.inventory, k, x + k * 18, y + 58));
        }
    }

    protected void updateMaxTopSlot(int id) {
        this.maxTopSlotIndex = Math.max(id, this.maxTopSlotIndex);
    }

    public ItemStack quickMoveStack(@NotNull Player player, int id) {
        int invSt = this.maxTopSlotIndex + 1;

        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(id);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (id == this.resultId || id == this.resultXpId) {
                if (!this.moveItemStackTo(itemstack1, invSt, 36 + invSt, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (id > invSt) {
                // move from inventory to machine
                ItemStack remainder = tryMoveFromInventory(itemstack1);
                if (remainder.isEmpty()) return remainder;
                // move to hotbar if can't move to machine
                if (id < 28 + invSt) {
                    if (!this.moveItemStackTo(itemstack1, 28 + invSt, 36 + invSt, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (id >= 28 + invSt && id < 36 + invSt && !this.moveItemStackTo(itemstack1, invSt, invSt + 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, invSt, 36 + invSt, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    protected abstract ItemStack tryMoveFromInventory(ItemStack stack);

    @Override
    public boolean stillValid(Player player) {
        return ((ItemBE) this.blockEntity).stillValid(player);
    }
}