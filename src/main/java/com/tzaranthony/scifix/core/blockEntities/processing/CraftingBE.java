package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.api.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public abstract class CraftingBE extends BlockEntity {
    protected final RecipeType recipeType;
    protected int progress;
    protected static final String PROG = "SCIFIX_Progress";
    protected int maxTime;
    protected static final String MAX_TM = "SCIFIX_TimeNeeded";
    protected ItemStackHandler itemHandler;
    protected static final String ITEM_INV = "SCIFIX_Items";

    public CraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType) {
        super(type, pos, state);
        this.recipeType = recipeType;
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getInt(PROG);
        this.maxTime = tag.getInt(MAX_TM);
        if (this.itemHandler != null) {
            this.itemHandler.deserializeNBT(tag.getCompound(ITEM_INV));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(PROG, this.progress);
        tag.putInt(MAX_TM, this.maxTime);
        if (this.itemHandler != null) {
            tag.put(ITEM_INV, this.itemHandler.serializeNBT());
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected void update() {
        setChanged();
        getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (itemHandler != null) {
            tag.put(ITEM_INV, itemHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<IItemHandler> itemCap = LazyOptional.of(() -> itemHandler);
    protected Map<Direction, LazyOptional<BlockEntityUtils.SidedItemHandler>> itemDirectionHandler;

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemDirectionHandler.get(facing).cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.itemCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.itemCap = LazyOptional.of(() -> itemHandler);
    }

    public Container createContainer() {
        Container container = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            container.setItem(i, itemHandler.getStackInSlot(i));
        }
        return container;
    }

    public void dropInventory() {
        Containers.dropContents(this.level, this.worldPosition, this.createContainer());
    }
}