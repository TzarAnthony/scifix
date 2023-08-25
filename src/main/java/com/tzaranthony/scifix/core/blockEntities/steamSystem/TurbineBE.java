package com.tzaranthony.scifix.core.blockEntities.steamSystem;

import com.tzaranthony.scifix.api.handlers.FluidHandler;
import com.tzaranthony.scifix.api.handlers.GasFluidHandler;
import com.tzaranthony.scifix.api.handlers.IDirectional;
import com.tzaranthony.scifix.api.helpers.BlockEntityUtils;
import com.tzaranthony.scifix.api.helpers.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class TurbineBE extends BlockEntity {
    protected FluidHandler fluidHandler;
    protected final String FLUID_INV = "SCIFIX_Fluids";
    protected float waterTemp = Constants.WaterBaseTemp;
    protected final String WATER_TEMP = "SCIFIX_Water_Temperature";
    protected boolean ventSteam = false;
    protected float storedSteam = 0;
    protected final String STEAM_KG = "SCIFIX_Steam_Amt";
    protected float tier = 0;
    protected final String TIER_VAL = "SCIFIX_Tier";

    public TurbineBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.fluidHandler = new GasFluidHandler(10000, IDirectional.Direction.EITHER);
        // add if water is added do a weighted average of the temperature
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (this.fluidHandler != null) {
            this.fluidHandler.deserializeNBT(tag.getCompound(FLUID_INV));
        }
        this.waterTemp = tag.getFloat(WATER_TEMP);
        this.storedSteam = tag.getFloat(STEAM_KG);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (this.fluidHandler != null) {
            tag.put(FLUID_INV, this.fluidHandler.serializeNBT());
        }
        tag.putFloat(WATER_TEMP, this.waterTemp);
        tag.putFloat(STEAM_KG, this.storedSteam);
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
    }

    protected void update() {
        setChanged();
        getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        if (fluidHandler != null) {
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
}