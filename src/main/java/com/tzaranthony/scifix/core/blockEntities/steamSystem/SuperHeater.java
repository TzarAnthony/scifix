package com.tzaranthony.scifix.core.blockEntities.steamSystem;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SuperHeater extends HeatExchangingBE {
    public SuperHeater(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    protected void setupItemHandler() {

    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return null;
    }

    @Override
    public Component getDisplayName() {
        return null;
    }
}