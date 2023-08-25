package com.tzaranthony.scifix.api.handlers.BEHelpers;

import com.tzaranthony.scifix.api.handlers.ItemHandler;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.items.ItemStackHandler;

public interface ItemBE {
    void setItemHandler(ItemHandler items);

    ItemHandler getItemHandler();

    boolean stillValid(Player player);

    void dropInventory(ServerLevel level);
}