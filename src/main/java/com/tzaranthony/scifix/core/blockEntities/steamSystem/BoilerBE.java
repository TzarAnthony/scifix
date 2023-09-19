package com.tzaranthony.scifix.core.blockEntities.steamSystem;

import com.mojang.datafixers.kinds.Const;
import com.tzaranthony.scifix.api.handlers.BEHelpers.FluidBE;
import com.tzaranthony.scifix.api.handlers.BEHelpers.ItemBE;
import com.tzaranthony.scifix.api.handlers.FluidHandler;
import com.tzaranthony.scifix.api.handlers.IDirectional;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
import com.tzaranthony.scifix.api.helpers.BlockEntityUtils;
import com.tzaranthony.scifix.api.helpers.Constants;
import com.tzaranthony.scifix.api.helpers.Maths;
import com.tzaranthony.scifix.core.network.FluidS2CPacket;
import com.tzaranthony.scifix.core.network.ItemS2CPacket;
import com.tzaranthony.scifix.registries.SPackets;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BoilerBE extends HeatExchangingBE implements FluidBE {
    protected FluidHandler fluidHandler;
    protected final String FLUID_INV = "SCIFIX_Fluids";
    protected float waterTemp = Constants.WaterBaseTemp;
    protected final String WATER_TEMP = "SCIFIX_Water_Temperature";
    protected boolean ventSteam = false;

    public BoilerBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        this.fluidHandler = new FluidHandler(10000, fluid -> fluid.getFluid().isSame(Fluids.WATER), IDirectional.Direction.EITHER) {
            @Override
            public int fill(FluidStack resource, FluidAction action) {
                int fillAmt = super.fill(resource, action);
                if (action == FluidAction.EXECUTE && fillAmt > 0) {
                    float cAmt = (float) getFluidInTank(0).getAmount();
                    heatManager.setTemperature((heatManager.getTemperature() * (cAmt - (float) fillAmt) + Constants.WaterBaseTemp * (float) fillAmt) / cAmt);
                }
                return fillAmt;
            }

            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) {
                    SPackets.sendToClients(new FluidS2CPacket(this, worldPosition));
                }
            }
        };
    }

    protected void setupItemHandler() {
        this.itemHandler.setSize(2);
        this.itemHandler.setValidators(List.of(e -> e.getItem() instanceof BucketItem, e -> false));
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.fluidHandler.deserializeNBT(tag.getCompound(FLUID_INV));
        this.waterTemp = tag.getFloat(WATER_TEMP);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
        tag.putFloat(WATER_TEMP, this.waterTemp);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, BoilerBE spBE) {
        if (spBE.ventSteam) {
            double i = pos.getX();
            double j = pos.getY();
            double k = pos.getZ();
            for(int l = 0; l < 8; ++l) {
                level.addParticle(ParticleTypes.LARGE_SMOKE, i + Math.random(), j + Math.random(), k + java.lang.Math.random(), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, BoilerBE spBE) {
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
            spBE.fluidHandler.consumeFluid(new FluidStack(Fluids.WATER, mbWaterToSteam), IFluidHandler.FluidAction.EXECUTE);
            spBE.waterTemp = steamReqTemp;
            float kgSteam = Maths.mbWaterTokgSteam(mbWaterToSteam);
            // try to pass the steam, if failed, vent the steam
            spBE.ventSteam = true;
            spBE.update();
        }

        exchangeHeat(level, pos, state, spBE);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (this.fluidHandler != null) {
            tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
        }
        return tag;
    }

    protected LazyOptional<? extends FluidHandler> fluidCap = LazyOptional.of(() -> fluidHandler);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (!this.remove && facing != null && capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return fluidCap.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        this.fluidCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.fluidCap = LazyOptional.of(() -> fluidHandler);
    }

    public void setFluidHandler(FluidHandler fh) {
        this.fluidHandler = fh;
    }

    public FluidHandler getFluidHandler() {
        return this.fluidHandler;
    }

    //TODO: menu stuff
    protected AbstractContainerMenu createMenu(int id, Inventory inv) {
        return null;
    }

    public Component getDisplayName() {
        return null;
    }
}