package com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing;

import com.tzaranthony.scifix.api.handlers.SRFHandler;
import com.tzaranthony.scifix.api.helpers.Constants;
import com.tzaranthony.scifix.api.properties.ThermalProperties;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ResistanceHeaterBE extends HeatProducingBE {
    protected SRFHandler rfHandler;
    protected static final String RF_INV = "SCIFIX_RF";

    public ResistanceHeaterBE(BlockPos pos, BlockState state) {
        this(pos, state, ThermalProperties.TIER_0_EXCHANGER);
    }

    public ResistanceHeaterBE(BlockPos pos, BlockState state, ThermalProperties properties) {
        super(SBlockEntities.LIQUID_HEATER.get(), pos, state, properties);
        this.itemHandler = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof BucketItem; //TODO: only accept energy items
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if(!level.isClientSide()) {
//                    SPackets.sendToClients(new ItemS2CPacket(this, worldPosition));
                }
            }
        };

        this.rfHandler = new SRFHandler(40000 + properties.getTier() * 10000, 5000 + properties.getTier() * 5000, 0);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (this.rfHandler != null) {
            this.rfHandler.deserializeNBT(tag.getCompound(RF_INV));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.rfHandler != null) {
            tag.put(RF_INV, this.rfHandler.serializeNBT());
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, ResistanceHeaterBE ehBE) {
        boolean startedLit = ehBE.isLit();
        if (startedLit) {
            --ehBE.burnTime;
        }
        if (startedLit || ehBE.rfHandler.getEnergyStored() >= 100) {
            ehBE.rfHandler.useEnergy(100, false); //TODO: check balance
            ehBE.burnTime = 1;
            ehBE.burnDuration = ehBE.burnTime;
            if (ehBE.isLit()) {
                ehBE.heatManager.consumeOrProduceHeat(Constants.ResistanceHeaterTempPerTick, false);
            }
        }

        if (startedLit != ehBE.isLit()) {
            //TODO: make blocks glow --- state = state.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(flhpBE.isLit()));
            level.setBlock(pos, state, 3);
            ehBE.update();
        }

        exchangeHeat(level, pos, state, ehBE);
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