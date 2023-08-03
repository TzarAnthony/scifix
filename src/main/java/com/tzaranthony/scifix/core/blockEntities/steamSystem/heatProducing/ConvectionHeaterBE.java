package com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing;

import com.tzaranthony.scifix.api.helpers.Constants;
import com.tzaranthony.scifix.api.properties.ThermalProperties;
import com.tzaranthony.scifix.core.util.tags.SItemTags;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class ConvectionHeaterBE extends FurnaceLikeHeaterBE {
    public ConvectionHeaterBE(BlockPos pos, BlockState state) {
        this(pos, state, ThermalProperties.TIER_0_EXCHANGER);
    }

    public ConvectionHeaterBE(BlockPos pos, BlockState state, ThermalProperties properties) {
        super(SBlockEntities.CONVECTION_HEATER.get(), pos, state, properties);
        this.itemHandler = new ItemStackHandler(1) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return isFuel(stack) && stack.is(SItemTags.CONVECTION_FUEL);
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if(!level.isClientSide()) {
//                    SPackets.sendToClients(new ItemS2CPacket(this, worldPosition));
                }
            }
        };
    }

    @Override
    public int getBurnTime(ItemStack stack) {
        return 4 * super.getBurnTime(stack);
    }

    @Override
    public float getTempIncreasePerTick(ItemStack stack) {
        return Constants.ConvectionHeaterTempPerTick;
    }
}