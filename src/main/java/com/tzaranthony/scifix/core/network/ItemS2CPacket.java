package com.tzaranthony.scifix.core.network;

import com.tzaranthony.scifix.api.handlers.BEHelpers.ItemBE;
import com.tzaranthony.scifix.api.handlers.ItemHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class ItemS2CPacket {
    private ItemHandler items;
    private BlockPos pos;

    public ItemS2CPacket(ItemHandler items, BlockPos pos) {
        this.items = items;
        this.pos = pos;
    }

    public ItemS2CPacket(FriendlyByteBuf buf) {
        read(buf);
    }

    public void read(FriendlyByteBuf buf) {
        List<ItemStack> collection = buf.readCollection(ArrayList::new, FriendlyByteBuf::readItem);
        items = new ItemHandler(collection.size());
        for (int i = 0; i < collection.size(); i++) {
            items.insertItem(i, collection.get(i), false);
        }

        this.pos = buf.readBlockPos();
    }

    public void write(FriendlyByteBuf buf) {
        Collection<ItemStack> list = new ArrayList<>();
        for(int i = 0; i < items.getSlots(); i++) {
            list.add(items.getStackInSlot(i));
        }

        buf.writeCollection(list, FriendlyByteBuf::writeItem);
        buf.writeBlockPos(pos);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof ItemBE iBE) {
                iBE.setItemHandler(this.items);
            }
        });
        return true;
    }
}