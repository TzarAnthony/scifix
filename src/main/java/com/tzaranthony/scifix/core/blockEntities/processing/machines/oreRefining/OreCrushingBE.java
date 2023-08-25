package com.tzaranthony.scifix.core.blockEntities.processing.machines.oreRefining;

import com.tzaranthony.scifix.api.handlers.IDirectional;
import com.tzaranthony.scifix.core.container.menus.CrusherGrinderMenu;
import com.tzaranthony.scifix.core.crafting.CrushingRecipe;
import com.tzaranthony.scifix.core.util.tags.SItemTags;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class OreCrushingBE extends OreRefiningBE {
    public OreCrushingBE(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public OreCrushingBE(BlockPos pos, BlockState state, int tier) {
        super(SBlockEntities.CRUSHER.get(), pos, state, CrushingRecipe.TYPE, tier);
    }

    protected void setupItemHandler() {
        this.itemHandler = this.itemHandler.setAllValidators(stack -> stack.is(SItemTags.CRUSHABLE));
        this.itemHandler = this.itemHandler.setAllDirections(IDirectional.Direction.INPUT);
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return new CrusherGrinderMenu(id, inv, this.getBlockPos(), this.dataAccess);
    }

    @Override
    public Component getDisplayName() {
        return new TranslatableComponent("container.scifix.crusher");
    }

    public boolean isValidItem(ItemStack item) {
        return item.is(SItemTags.CRUSHABLE);
    }

    protected void playProcessingSound(BlockPos pos) {
        playSound(pos, SoundEvents.ANVIL_LAND, 0.10F, 1.0F);
    }
}