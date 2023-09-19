package com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing;

import com.tzaranthony.scifix.api.handlers.HeatHandler;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
import com.tzaranthony.scifix.core.blockEntities.steamSystem.HeatExchangingBE;
import com.tzaranthony.scifix.api.properties.ThermalProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public abstract class HeatProducingBE extends HeatExchangingBE {
    protected ItemHandler itemHandler;
    protected static final String ITEM_INV = "SCIFIX_Items";
    int burnTime;
    int burnDuration;
    protected final ContainerData dataAccess = new ContainerData() {
        public int get(int index) {
            switch(index) {
                case 0:
                    return HeatProducingBE.this.burnTime;
                case 1:
                    return HeatProducingBE.this.burnDuration;
                default:
                    return 0;
            }
        }

        public void set(int index, int value) {
            switch(index) {
                case 0:
                    HeatProducingBE.this.burnTime = value;
                    break;
                case 1:
                    HeatProducingBE.this.burnDuration = value;
            }

        }
        public int getCount() {
            return 2;
        }
    };

    public HeatProducingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, ThermalProperties properties) {
        super(type, pos, state);
        if (this.level != null) {
            this.refreshAtmosphericData();
        }
        this.heatManager = new HeatHandler(properties.getThermalConductivity(), properties.getSpecificHeat(), this.atmosphericTemp);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (this.itemHandler != null) {
            this.itemHandler.deserializeNBT(tag.getCompound(ITEM_INV));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.itemHandler != null) {
            tag.put(ITEM_INV, this.itemHandler.serializeNBT());
        }
    }

    protected boolean isLit() {
        return this.burnTime > 0;
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

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemCap.cast();
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