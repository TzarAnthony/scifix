package com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing;

import com.tzaranthony.scifix.api.BlockEntityUtils;
import com.tzaranthony.scifix.api.mathAndPhysics.Constants;
import com.tzaranthony.scifix.api.properties.ThermalProperties;
import com.tzaranthony.scifix.core.util.tags.SFluidTags;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class LiquidFuelHeaterBE extends HeatProducingBE {
    protected FluidTank fluidHandler;
    protected final String FLUID_INV = "SCIFIX_Fluids";

    public LiquidFuelHeaterBE(BlockPos pos, BlockState state) {
        this(pos, state, ThermalProperties.TIER_0_EXCHANGER);
    }

    public LiquidFuelHeaterBE(BlockPos pos, BlockState state, ThermalProperties properties) {
        super(SBlockEntities.LIQUID_HEATER.get(), pos, state, properties);
        this.itemHandler = new ItemStackHandler(2) {
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

        this.fluidHandler = new FluidTank(10000, fluid -> fluid.getFluid().is(SFluidTags.COMBUSTIBLE));
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        if (fluidHandler != null) {
            fluidHandler.readFromNBT(tag.getCompound(FLUID_INV));
        }
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (fluidHandler != null) {
            tag.put(FLUID_INV, fluidHandler.writeToNBT(new CompoundTag()));
        }
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, LiquidFuelHeaterBE lqfhBE) {
        BlockEntityUtils.transferToTank(lqfhBE.itemHandler, lqfhBE.fluidHandler, 0, 1);

        boolean startedLit = lqfhBE.isLit();
        if (startedLit) {
            --lqfhBE.burnTime;
        }

        FluidStack stack = lqfhBE.fluidHandler.getFluidInTank(0);
        if (startedLit || (!stack.isEmpty() && stack.getAmount() >= 100)) {
            if (!startedLit) {
                //TODO: It might be best to actually make a recipe for this so I can vary the temps easier
                lqfhBE.burnTime = getBurnTime(stack);
                lqfhBE.burnDuration = lqfhBE.burnTime;
                lqfhBE.fluidHandler.drain(100, IFluidHandler.FluidAction.EXECUTE);
            }
            if (lqfhBE.isLit()) {
                lqfhBE.heatManager.consumeOrProduceHeat(Constants.LiquidFuelHeaterTempPerTick, false);
            }
        }

        if (startedLit != lqfhBE.isLit()) {
            //TODO: make blocks glow --- state = state.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(flhpBE.isLit()));
            level.setBlock(pos, state, 3);
            lqfhBE.update();
        }

        exchangeHeat(level, pos, state, lqfhBE);
    }

    public static int getBurnTime(FluidStack stack) {
        // Hydrogen
        // Natural Gas
        // Jet Fuel / Kerosene?
        // Light Fuel
        // Heavy Fuel
        return 1000;
    }

    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        if (fluidHandler != null) {
            tag.put(FLUID_INV, fluidHandler.writeToNBT(new CompoundTag()));
        }
        return tag;
    }

    protected LazyOptional<? extends FluidTank> fluidCap = LazyOptional.of(() -> fluidHandler);

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