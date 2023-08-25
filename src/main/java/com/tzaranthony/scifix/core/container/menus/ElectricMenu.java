package com.tzaranthony.scifix.core.container.menus;

import com.tzaranthony.scifix.api.handlers.BEHelpers.EnergyBE;
import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public abstract class ElectricMenu extends BaseMenu {
    protected ElectricMenu(@Nullable MenuType<?> type, int id, Inventory inventory, BlockPos pos, ContainerData data) {
        super(type, id, inventory, pos, data);
        assert this.blockEntity instanceof EnergyBE;
    }

    public EnergyHandler getEnergyHandler() {
        return ((EnergyBE) this.blockEntity).getEnergyHandler();
    }
}