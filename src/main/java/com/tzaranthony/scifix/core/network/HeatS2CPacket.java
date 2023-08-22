package com.tzaranthony.scifix.core.network;

import com.tzaranthony.scifix.api.handlers.BEHelpers.HeatBE;
import com.tzaranthony.scifix.api.handlers.HeatHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class HeatS2CPacket {
    private HeatHandler handler;
    private float temperature;
    private float heatCapacity;
    private float thermalConductivity;
    private BlockPos pos;

    public HeatS2CPacket(HeatHandler handler, BlockPos pos) {
        this.handler = handler;
        this.temperature = this.handler.getTemperature();
        this.heatCapacity = this.handler.getHeatCapacity();
        this.thermalConductivity = this.handler.getThermalConductivity();
        this.pos = pos;
    }

    public HeatS2CPacket(FriendlyByteBuf buf) {
        read(buf);
    }

    public void read(FriendlyByteBuf buf) {
        this.temperature = buf.readFloat();
        this.heatCapacity = buf.readFloat();
        this.thermalConductivity = buf.readFloat();
        this.handler = new HeatHandler(this.heatCapacity, this.thermalConductivity, this.temperature);
        this.pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeFloat(this.temperature);
        buf.writeFloat(this.heatCapacity);
        buf.writeFloat(this.thermalConductivity);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof HeatBE blockEntity) {
                blockEntity.setHeatHandler(this.handler);
            }
        });
        return true;
    }
}