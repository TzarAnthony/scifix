package com.tzaranthony.scifix.core.blockEntities.processing.oreRefining;

import com.tzaranthony.scifix.api.handlers.SRFHandler;
import com.tzaranthony.scifix.core.crafting.GrindingRecipe;
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

public class OreGrindingBE extends OreRefiningBE {
    public OreGrindingBE(BlockPos pos, BlockState state) {
        this(pos, state, 0);
    }

    public OreGrindingBE(BlockPos pos, BlockState state, int tier) {
        super(SBlockEntities.GRINDER.get(), pos, state, GrindingRecipe.TYPE, tier);
    }

    public void setTier(int tier) {
        this.tier = tier;
        this.maxTime = 200 - getTierMaxTimeReduction(tier);
        int capacity = getTierCapacity(tier);
        this.itemHandler = new ItemStackHandler(capacity) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.is(SItemTags.GRINDABLE);
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if(!level.isClientSide()) {
//                    SPackets.sendToClients(new ItemS2CPacket(this, worldPosition));
                }
            }

            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
                ItemStack result = super.extractItem(slot, amount, simulate);
                if (!simulate && this.getStackInSlot(slot).isEmpty() && cachedRecipes.size() > slot) {
                    cachedRecipes.set(slot, null);
                    progressList.set(slot, 0);
                }
                return result;
            }
        };
        this.progressList = NonNullList.withSize(capacity, 0);
        this.cachedRecipes = Arrays.asList((new RfRecipe[capacity]));

        this.rfHandler = new SRFHandler(40000 + 10000 * tier, 500 + 500 * tier, 0);
        this.rfHandler.setEnergy(this.rfHandler.getMaxEnergyStored()); //TODO: remove this after testing

        //TODO: does this work without this?
//        List<Integer> slots = IntStream.range(0, capacity).boxed().collect(Collectors.toList());
//        itemCap = LazyOptional.of(() -> new SidedItemHandler(itemHandler, slots, List.of()));
    }

    public boolean isValidItem(ItemStack item) {
        return item.is(SItemTags.GRINDABLE);
    }

    protected void playProcessingSound(BlockPos pos) {
        playSound(pos, SoundEvents.GRAVEL_BREAK, 0.10F, 1.0F);
    }
}