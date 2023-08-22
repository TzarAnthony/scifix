package com.tzaranthony.scifix.core.blockEntities.steamSystem;

import com.tzaranthony.scifix.api.handlers.BEHelpers.FluidBE;
import com.tzaranthony.scifix.api.handlers.BEHelpers.ItemBE;
import com.tzaranthony.scifix.api.handlers.FluidHandler;
import com.tzaranthony.scifix.api.handlers.IDirectional;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
import com.tzaranthony.scifix.api.helpers.BlockEntityUtils;
import com.tzaranthony.scifix.api.helpers.Constants;
import com.tzaranthony.scifix.api.helpers.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Containers;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SteamProducingBE extends HeatExchangingBE implements FluidBE, ItemBE {
    protected ItemHandler itemHandler;
    protected static final String ITEM_INV = "SCIFIX_Items";
    protected FluidHandler fluidHandler;
    protected final String FLUID_INV = "SCIFIX_Fluids";
    protected float waterTemp = Constants.WaterBaseTemp;
    protected final String WATER_TEMP = "SCIFIX_Water_Temperature";
    protected boolean ventSteam = false;

    public SteamProducingBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.itemHandler = new ItemHandler(2) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                switch (slot) {
                    case 0:
                        return stack.getItem() instanceof BucketItem;
                    default:
                        return false;
                }
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if(!level.isClientSide()) {
//                    SPackets.sendToClients(new ItemS2CPacket(this, worldPosition));
                }
            }
        };

        this.fluidHandler = new FluidHandler(10000, fluid -> fluid.getFluid().isSame(Fluids.WATER), IDirectional.Direction.EITHER);
        // add if water is added do a weighted average of the temperature
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (this.itemHandler != null) {
            this.itemHandler.deserializeNBT(tag.getCompound(ITEM_INV));
        }
        if (this.fluidHandler != null) {
            this.fluidHandler.deserializeNBT(tag.getCompound(FLUID_INV));
        }
        this.waterTemp = tag.getFloat(WATER_TEMP);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.itemHandler != null) {
            tag.put(ITEM_INV, this.itemHandler.serializeNBT());
        }
        if (this.fluidHandler != null) {
            tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
        }
        tag.putFloat(WATER_TEMP, this.waterTemp);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, SteamProducingBE spBE) {
        if (spBE.ventSteam) {
            double i = pos.getX();
            double j = pos.getY();
            double k = pos.getZ();
            for(int l = 0; l < 8; ++l) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, i + Math.random(), j + Math.random(), k + java.lang.Math.random(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, SteamProducingBE spBE) {
        BlockEntityUtils.transferToTank(spBE.itemHandler, spBE.fluidHandler, 0, 1, 0);

        // water > steam heating
        float b = (float) spBE.fluidHandler.getFluidInTank(0).getAmount() * 1000F;
        float kg = b * Constants.WaterDensityPerBucket;
        float tempChange = spBE.heatManager.exchangeHeat(Constants.WaterThermalConductivity, Constants.WaterHeatCapacity * kg, spBE.waterTemp, false);
        spBE.waterTemp += tempChange;
        float kPa = b * Constants.WaterkPaPerBucket;
        float steamReqTemp = Maths.SteamkPaToC(kPa);
        if (spBE.waterTemp > steamReqTemp) {
            int mbWaterToSteam = Maths.mbWaterToEvaporate(spBE.waterTemp - steamReqTemp, kg);
            spBE.fluidHandler.drain(mbWaterToSteam, IFluidHandler.FluidAction.EXECUTE);
            spBE.waterTemp = steamReqTemp;
            float kgSteam = Maths.mbWaterTokgSteam(mbWaterToSteam);
            // try to pass the steam, if failed, vent the steam
            spBE.ventSteam = true;
            spBE.update();
        }

        exchangeHeat(level, pos, state, spBE);
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
        if (this.fluidHandler != null) {
            tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<IItemHandler> itemCap = LazyOptional.of(() -> itemHandler);
    protected LazyOptional<? extends FluidHandler> fluidCap = LazyOptional.of(() -> fluidHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return this.itemCap.cast();
        }
        if (!this.remove && facing != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidCap.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.itemCap.invalidate();
        this.fluidCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.itemCap = LazyOptional.of(() -> itemHandler);
        this.fluidCap = LazyOptional.of(() -> fluidHandler);
    }

    public void dropInventory() {
        Containers.dropContents(this.level, this.worldPosition, BlockEntityUtils.createContainer(this.itemHandler));
    }

    public void setFluidHandler(FluidHandler fh) {
        this.fluidHandler = fh;
    }

    public void setItemHandler(ItemHandler ish) {
        this.itemHandler = ish;
    }
}