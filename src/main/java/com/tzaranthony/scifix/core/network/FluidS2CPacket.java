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
import java.util.stream.Collectors;

public class FluidS2CPacket {
    private FluidHandler handler;
    private List<Integer> capacities;
    private List<Boolean> canInsert;
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
        List<IDirectional.Direction> directions = (buf.readCollection(ArrayList::new, FriendlyByteBuf::readBoolean).stream().map(e -> e? IDirectional.Direction.INPUT : IDirectional.Direction.OUTPUT)).collect(Collectors.toList());
        this.handler = new FluidHandler(this.capacities, directions);
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            List<FluidStack> fluids = buf.readCollection(ArrayList::new, FriendlyByteBuf::readFluidStack);
            this.handler.setFluidsInTank(i, fluids);
        }
        this.pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        Collection<Integer> capacities = new ArrayList<>();
        Collection<Collection<FluidStack>> allFluids = new ArrayList<>();
        Collection<Boolean> insertable = new ArrayList<>();
        for(int i = 0; i < this.handler.getTanks(); i++) {
            capacities.add(this.handler.getTankCapacity(i));
            allFluids.add(this.handler.getFluidsInTank(i));
            insertable.add(this.handler.getTank(i).getDirection().canInput());
        }
        buf.writeCollection(capacities, FriendlyByteBuf::writeInt);
        buf.writeCollection(insertable, FriendlyByteBuf::writeBoolean);
        buf.writeInt(allFluids.size());
        for (Collection<FluidStack> fluids : allFluids) {
            buf.writeCollection(fluids, FriendlyByteBuf::writeFluidStack);
        }
        buf.writeBlockPos(this.pos);
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