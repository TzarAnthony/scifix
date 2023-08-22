package com.tzaranthony.scifix.core.network;

import com.tzaranthony.scifix.api.handlers.BEHelpers.FluidBE;
import com.tzaranthony.scifix.api.handlers.FluidHandler;
import com.tzaranthony.scifix.api.handlers.IDirectional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class FluidS2CPacket {
    private FluidHandler handler;
    private List<Integer> capacities;
    private BlockPos pos;

    public FluidS2CPacket(FluidHandler handler, BlockPos pos) {
        this.handler = handler;
        this.capacities = this.getTankCapacities(this.handler);
        this.pos = pos;
    }

    public FluidS2CPacket(FriendlyByteBuf buf) {
        read(buf);
    }

    public List<Integer> getTankCapacities(FluidHandler fluidTanks) {
        List<Integer> amounts = new ArrayList<Integer>();
        for (int i = 0; i < fluidTanks.getTanks(); ++i) {
            amounts.add(fluidTanks.getTankCapacity(i));
        }
        return amounts;
    }

    public void read(FriendlyByteBuf buf) {
        this.capacities = buf.readCollection(ArrayList::new, FriendlyByteBuf::readInt);
        List<FluidStack> fluids = buf.readCollection(ArrayList::new, FriendlyByteBuf::readFluidStack);
        this.handler = new FluidHandler(this.capacities, NonNullList.withSize(this.capacities.size(), IDirectional.Direction.EITHER));
        for (int i = 0; i < fluids.size(); i++) {
            this.handler.setFluidInTank(i, fluids.get(i));
        }
        this.pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        Collection<Integer> amounts = new ArrayList<>();
        Collection<FluidStack> fluids = new ArrayList<>();
        for(int i = 0; i < handler.getTanks(); i++) {
            amounts.add(handler.getTankCapacity(i));
            fluids.add(handler.getFluidInTank(i));
        }
        buf.writeCollection(amounts, FriendlyByteBuf::writeInt);
        buf.writeCollection(fluids, FriendlyByteBuf::writeFluidStack);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof FluidBE blockEntity) {
                blockEntity.setFluidHandler(this.handler);
            }
        });
        return true;
    }
}