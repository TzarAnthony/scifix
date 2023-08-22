package com.tzaranthony.scifix.core.network;

import com.tzaranthony.scifix.api.handlers.BEHelpers.EnergyBE;
import com.tzaranthony.scifix.api.handlers.EnergyHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergyS2CPacket {
    private EnergyHandler handler;
    private int energy;
    private int capacity;
    private BlockPos pos;

    public EnergyS2CPacket(EnergyHandler handler, BlockPos pos) {
        this.handler = handler;
        this.energy = this.handler.getEnergyStored();
        this.capacity = this.handler.getCapacity();
        this.pos = pos;
    }

    public EnergyS2CPacket(FriendlyByteBuf buf) {
        read(buf);
    }

    public void read(FriendlyByteBuf buf) {
        this.energy = buf.readInt();
        this.capacity = buf.readInt();
        this.handler = new EnergyHandler(this.capacity, 0, 0);
        this.handler.setEnergy(this.energy);
        this.pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.energy);
        buf.writeInt(this.capacity);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof EnergyBE blockEntity) {
                blockEntity.setEnergyHandler(this.handler);
            }
        });
        return true;
    }
}