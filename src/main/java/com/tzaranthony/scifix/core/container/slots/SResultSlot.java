package com.tzaranthony.scifix.core.container.slots;

import com.tzaranthony.scifix.api.handlers.ItemHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SResultSlot extends ItemHandlerSlot {
    protected final Player player;
    protected int removeCount;

    public SResultSlot(Player player, ItemHandler itemHandler, int id, int x, int y) {
        super(itemHandler, id, x, y);
        this.player = player;
    }

    public boolean mayPlace(ItemStack stack) {
        return false;
    }

    public void onTake(Player player, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(player, stack);
    }

    public ItemStack remove(int amt) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amt, this.getItem().getCount());
        }
        return super.remove(amt);
    }

    protected void onQuickCraft(ItemStack stack, int amt) {
        this.removeCount += amt;
        this.checkTakeAchievements(stack);
    }

    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        this.removeCount = 0;
        net.minecraftforge.event.ForgeEventFactory.firePlayerSmeltedEvent(this.player, stack);
    }
}