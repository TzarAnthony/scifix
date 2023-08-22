package com.tzaranthony.scifix.api.helpers;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.stream.Collectors;

public interface IAutoSucking {
    String TRANSFER = "SCIFIX_Transfer_Cooldown";

    default void loadCooldown(CompoundTag tag) {
        this.setCooldown(tag.getInt(TRANSFER));
    }

    default void saveCooldown(CompoundTag tag) {
        tag.putInt(TRANSFER, this.getCooldown());
    }

    default boolean checkAdditionalPickupConditions() {
        return true;
    }

    default List<ItemEntity> getItemsInArea(Level level, IAutoSucking asBE) {
        return asBE.getSuckShape().toAabbs().stream().flatMap((p_155558_) -> {
            return level.getEntitiesOfClass(ItemEntity.class, p_155558_.move(asBE.getCenterX() - 0.5D, asBE.getCenterY() - 0.5D, asBE.getCenterZ() - 0.5D), EntitySelector.ENTITY_STILL_ALIVE).stream();
        }).collect(Collectors.toList());
    }

    static boolean pickupTick(Level level, IAutoSucking asBE) {
        boolean flag = false;
        if (asBE.getCooldown() <= 0 && asBE.checkAdditionalPickupConditions()) {
            List<ItemEntity> items = asBE.getItemsInArea(level, asBE);
            if (!items.isEmpty()) {
                for (ItemEntity item : items) {
                    if (asBE.isValidItem(item.getItem()) && !item.isRemoved() && !item.hasPickUpDelay()) {
                        for (int i = asBE.getMinInputSlots(); i < asBE.getMaxInputSlots(); ++i) {
                            if (item.getItem().getCount() > asBE.putItemStack(i, item.getItem(), true).getCount()) {
                                ItemStack remainder = asBE.putItemStack(i, item.getItem(), false);
                                if (!remainder.isEmpty()) {
                                    item.setItem(remainder);
                                } else {
                                    item.discard();
                                    break;
                                }
                                flag = true;
                            }
                        }
                    }
                }
            }
            asBE.resetTransferCooldown();
        } else {
            asBE.setCooldown(asBE.getCooldown() - 1);
        }

        return flag;
    }

    default boolean isValidItem(ItemStack item) {
        return true;
    }

    int getMinInputSlots();

    int getMaxInputSlots();

    ItemStack putItemStack(int slot, ItemStack stack, boolean simulate);

    void setCooldown(int i);

    int getCooldown();

    VoxelShape getSuckShape();

    double getCenterX();

    double getCenterY();

    double getCenterZ();

    void resetTransferCooldown();
}