package com.tzaranthony.scifix.core.container.slots;

import com.tzaranthony.scifix.api.handlers.ItemHandler;
import com.tzaranthony.scifix.api.handlers.XpHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ResultXPSlot extends SResultSlot {
    protected final XpHandler xpHandler;

    public ResultXPSlot(Player player, ItemHandler itemHandler, XpHandler xpHandler, int id, int x, int y) {
        super(player, itemHandler, id, x, y);
        this.xpHandler = xpHandler;
    }

    protected void checkTakeAchievements(ItemStack stack) {
        super.checkTakeAchievements(stack);

        if (this.player instanceof ServerPlayer) {
            this.xpHandler.popXp(((ServerPlayer) this.player).getLevel(), this.player.position());
        }
    }
}