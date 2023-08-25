package com.tzaranthony.scifix.core.container.menus;

import com.tzaranthony.scifix.registries.SMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;

public class CrusherGrinderMenu extends ElectricMenu implements IProcessMenu {
    public static int XPResultSlotId = 1;

    public CrusherGrinderMenu(int id, Inventory inventory, BlockPos pos) {
        this(id, inventory, pos, new SimpleContainerData(2));
    }

    public CrusherGrinderMenu(int id, Inventory inventory, BlockPos pos, ContainerData data) {
        super(SMenus.CRUSHER_GRINDER.get(), id, inventory, pos, data);

        checkContainerSize(inventory, 2);
        checkContainerDataCount(data, 2);

        this.addInputSlot(0, 81, 22);
        this.addInventory(8, 84);
        this.addDataSlots(this.data);
    }

    protected ItemStack tryMoveFromInventory(ItemStack stack) {
        if (!this.moveItemStackTo(stack, this.minInputId, this.maxInputId + 1, false)) {
            return ItemStack.EMPTY;
        }
        return stack;
    }

    public int getProcessProgress() {
        int current = this.data.get(0);
        int max = this.data.get(1);
        return max != 0 && current != 0 ? current * 20 / max : 0;
    }
}