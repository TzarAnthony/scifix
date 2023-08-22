package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.api.handlers.BEHelpers.EnergyBE;
import com.tzaranthony.scifix.api.handlers.BEHelpers.ItemBE;
import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
import com.tzaranthony.scifix.api.helpers.BlockEntityUtils;
import com.tzaranthony.scifix.core.network.ItemS2CPacket;
import com.tzaranthony.scifix.core.util.tags.SItemTags;
import com.tzaranthony.scifix.registries.SPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

public abstract class CraftingBE extends BlockEntity implements ItemBE, EnergyBE {
    protected final RecipeType recipeType;
    protected int progress;
    protected static final String PROG = "SCIFIX_Progress";
    protected int maxTime;
    protected static final String MAX_TM = "SCIFIX_TimeNeeded";
    protected ItemHandler itemHandler;
    protected static final String ITEM_INV = "SCIFIX_Items";
    protected EnergyHandler rfHandler;
    protected static final String RF_INV = "SCIFIX_RF";
//    protected final ContainerData dataAccess = new ContainerData() {
//        public int get(int p_58431_) {
//            switch(p_58431_) {
//                case 0:
//                    return CraftingBE.this.progress;
//                case 1:
//                    return CraftingBE.this.maxTime;
//                default:
//                    return 0;
//            }
//        }
//
//        public void set(int p_58433_, int p_58434_) {
//            switch(p_58433_) {
//                case 1:
//                    CraftingBE.this.progress = p_58434_;
//                    break;
//                case 0:
//                    CraftingBE.this.maxTime = p_58434_;
//            }
//        }
//    };

    public CraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType) {
        super(type, pos, state);
        this.recipeType = recipeType;
        this.itemHandler = new ItemHandler() {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if(!level.isClientSide()) {
                    //TODO: packet syncing
                    SPackets.sendToClients(new ItemS2CPacket(this, worldPosition));
                }
            }
        };
        this.rfHandler = new EnergyHandler(40000, 500, 0);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.progress = tag.getInt(PROG);
        this.maxTime = tag.getInt(MAX_TM);
        this.itemHandler.deserializeNBT(tag.getCompound(ITEM_INV));
        this.rfHandler.deserializeNBT(tag.getCompound(RF_INV));
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt(PROG, this.progress);
        tag.putInt(MAX_TM, this.maxTime);
        tag.put(ITEM_INV, this.itemHandler.serializeNBT());
        tag.put(RF_INV, this.rfHandler.serializeNBT());
    }

    @Override
    public void setEnergyHandler(EnergyHandler rfh) {
        this.rfHandler = rfh;
    }

    public void setItemHandler(ItemHandler itemHandler) {
        this.itemHandler = itemHandler;
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
        if (this.rfHandler != null) {
            tag.put(RF_INV, this.rfHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<IItemHandler> itemCap = LazyOptional.of(() -> itemHandler);
    protected Map<Direction, LazyOptional<BlockEntityUtils.SidedItemHandler>> itemDirectionHandler;
    protected LazyOptional<IEnergyStorage> rfCap = LazyOptional.of(() -> rfHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemDirectionHandler.get(facing).cast();
        }
        if (capability == CapabilityEnergy.ENERGY) {
            return this.rfCap.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.itemCap.invalidate();
        rfCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.itemCap = LazyOptional.of(() -> itemHandler);
        this.rfCap = LazyOptional.of(() -> rfHandler);
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