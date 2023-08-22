package com.tzaranthony.scifix.core.blockEntities.processing.oreRefining;

import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import com.tzaranthony.scifix.api.handlers.IDirectional;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
import com.tzaranthony.scifix.core.crafting.CrushingRecipe;
import com.tzaranthony.scifix.core.crafting.RfRecipe;
import com.tzaranthony.scifix.core.util.tags.SItemTags;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.Arrays;

public class OreCrushingBE extends OreRefiningBE {
    public OreCrushingBE(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public OreCrushingBE(BlockPos pos, BlockState state, int tier) {
        super(SBlockEntities.CRUSHER.get(), pos, state, CrushingRecipe.TYPE, tier);
        this.itemHandler = this.itemHandler.setAllValidators(stack -> stack.is(SItemTags.GRINDABLE));
        this.itemHandler = this.itemHandler.setAllDirections(IDirectional.Direction.INPUT);
    }

    public boolean isValidItem(ItemStack item) {
        return item.is(SItemTags.CRUSHABLE);
    }

    protected void playProcessingSound(BlockPos pos) {
        playSound(pos, SoundEvents.ANVIL_LAND, 0.10F, 1.0F);
    }
}