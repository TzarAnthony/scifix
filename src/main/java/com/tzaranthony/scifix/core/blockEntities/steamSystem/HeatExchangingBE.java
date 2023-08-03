package com.tzaranthony.scifix.core.blockEntities.steamSystem;

import com.tzaranthony.scifix.api.handlers.HeatExchanger;
import com.tzaranthony.scifix.api.handlers.IHeatExchanger;
import com.tzaranthony.scifix.api.handlers.SCapabilities;
import com.tzaranthony.scifix.api.helpers.Constants;
import com.tzaranthony.scifix.api.helpers.Maths;
import com.tzaranthony.scifix.core.util.tags.SBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public abstract class HeatExchangingBE extends BlockEntity {
    protected HeatExchanger heatManager;
    protected static final String TEMPERATURE_C = "SCIFIX_Heat";
    protected float atmosphericTemp = 25F;
    protected static final String TEMPERATURE_ATMOSPHERE = "SCIFIX_Biome_Heat";

    public HeatExchangingBE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        this.atmosphericTemp = tag.getFloat(TEMPERATURE_ATMOSPHERE);
        if (this.heatManager != null) {
            this.heatManager.deserializeNBT(tag.getCompound(TEMPERATURE_C));
        }
        this.refreshAtmosphericData();
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putFloat(TEMPERATURE_ATMOSPHERE, this.atmosphericTemp);
        if (this.heatManager != null) {
            tag.put(TEMPERATURE_C, this.heatManager.serializeNBT());
        }
    }

    public void refreshAtmosphericData() {
        this.atmosphericTemp = Maths.MCTemperatureToC(this.level, this.worldPosition);
    }

    public static void exchangeHeat(Level level, BlockPos pos, BlockState state, HeatExchangingBE heBE) {
        if (heBE.heatManager.getTemperature() <= heBE.atmosphericTemp) {
            return;
        }
        if (heBE.heatManager.getTemperature() > Constants.MaxHeatExchangerTemperature) {
            heBE.causeMeltdown(level, pos, state);
            return;
        }
        int airBlocks = 0;
        int waterBlocks = 0;
        int iceBlocks = 0;
        for (Direction direction : Constants.OrderedHeatDirections) {
            BlockPos pos1 = pos.relative(direction);
            BlockState adj = level.getBlockState(pos1);
            if (adj.isAir()) {
                ++airBlocks;
                continue;
            }
            BlockEntity be = level.getBlockEntity(pos);
            if (be != null) {
                LazyOptional<IHeatExchanger> cap = be.getCapability(SCapabilities.HEAT);
                if (cap.isPresent()) {
                    IHeatExchanger otherHE = cap.orElseGet(null);
                    if (otherHE.getTemperature() < heBE.heatManager.getTemperature()) { // other option is to make separate can give / receive heat
                        float tempChange = heBE.heatManager.exchangeHeat(otherHE, false);
                        otherHE.receiveHeat(tempChange);
                    }
                }
                continue;
            }
            boolean phaseChange = heBE.level.random.nextInt(200) == 0;
            if (adj.is(Blocks.WATER)) {
                ++waterBlocks;
                if (phaseChange) heBE.level.removeBlock(pos1, false);
                continue;
            }
            if (adj.is(SBlockTags.FROZEN_WATER)) {
                ++iceBlocks;
                if (phaseChange) meltAdjBlocks(state, heBE.level, pos1);
            }
        }
        float loss = 0.0F;
        if (airBlocks > 0) {
            loss -= airBlocks * heBE.heatManager.calculateTemperatureExchangePerTick(Constants.AirThermalConductivity, Constants.AirHeatCapacity, heBE.atmosphericTemp);
        }
        if (waterBlocks > 0) {
            loss -= waterBlocks * heBE.heatManager.calculateTemperatureExchangePerTick(Constants.WaterThermalConductivity, Constants.WaterHeatCapacity, Constants.WaterBaseTemp);
        }
        if (iceBlocks > 0) {
            loss -= iceBlocks * heBE.heatManager.calculateTemperatureExchangePerTick(Constants.IceThermalConductivity, Constants.IceHeatCapacity, Constants.IceBaseTemp);
        }
        heBE.heatManager.consumeOrProduceHeat(loss, false);
    }

    protected void causeMeltdown(Level level, BlockPos pos, BlockState state) {
        level.setBlockAndUpdate(pos, Blocks.LAVA.defaultBlockState());
        level.neighborChanged(pos, Blocks.LAVA, pos);
    }

    protected static void meltAdjBlocks(BlockState state, Level level, BlockPos pos) {
        //TODO: the affected area really should be expanded... At least for snow layers
        if (state.isSuffocating(level, pos)) {
            level.removeBlock(pos, false);
        } else {
            level.setBlockAndUpdate(pos, Blocks.WATER.defaultBlockState());
            level.neighborChanged(pos, Blocks.WATER, pos);
        }
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected LazyOptional<IHeatExchanger> tmpCap = LazyOptional.of(() -> heatManager);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == SCapabilities.HEAT) {
            return this.tmpCap.cast();
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        tmpCap.invalidate();
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        this.tmpCap = LazyOptional.of(() -> heatManager);
    }
}