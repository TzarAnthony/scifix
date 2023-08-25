package com.tzaranthony.scifix.core.blockEntities.processing;

import com.tzaranthony.scifix.api.handlers.BEHelpers.EnergyBE;
import com.tzaranthony.scifix.api.handlers.BEHelpers.ItemBE;
import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
import com.tzaranthony.scifix.api.helpers.BlockEntityUtils;
import com.tzaranthony.scifix.core.network.EnergyS2CPacket;
import com.tzaranthony.scifix.core.network.ItemS2CPacket;
import com.tzaranthony.scifix.registries.SPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Map;

public abstract class EnergyCraftingBE extends CraftingBE implements EnergyBE {
    protected EnergyHandler rfHandler;
    protected static final String RF_INV = "SCIFIX_RF";

    public EnergyCraftingBE(BlockEntityType<?> type, BlockPos pos, BlockState state, RecipeType recipeType) {
        super(type, pos, state, recipeType);
        this.rfHandler = new EnergyHandler(40000, 500, 0);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.rfHandler.deserializeNBT(tag.get(RF_INV));
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(RF_INV, this.rfHandler.serializeNBT());
    }

    public void setEnergyHandler(EnergyHandler rfh) {
        this.rfHandler = rfh;
    }

    public EnergyHandler getEnergyHandler() {
        return this.rfHandler;
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (this.rfHandler != null) {
            tag.put(RF_INV, this.rfHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<IEnergyStorage> rfCap = LazyOptional.of(() -> rfHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return this.rfCap.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        rfCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.rfCap = LazyOptional.of(() -> rfHandler);
    }
}