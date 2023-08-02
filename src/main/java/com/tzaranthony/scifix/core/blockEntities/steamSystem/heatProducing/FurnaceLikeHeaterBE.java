package com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing;

import com.tzaranthony.scifix.api.properties.ThermalProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public abstract class FurnaceLikeHeaterBE extends HeatProducingBE {
    public FurnaceLikeHeaterBE(BlockEntityType<?> type, BlockPos pos, BlockState state, ThermalProperties properties) {
        super(type, pos, state, properties);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FurnaceLikeHeaterBE flhpBE) {
        boolean startedLit = flhpBE.isLit();
        if (startedLit) {
            --flhpBE.burnTime;
        }

        ItemStack stack = flhpBE.itemHandler.getStackInSlot(0);
        if (startedLit || !stack.isEmpty()) {
            if (!startedLit && isFuel(stack)) {
                flhpBE.burnTime = flhpBE.getBurnTime(stack);
                flhpBE.burnDuration = flhpBE.burnTime;
                flhpBE.itemHandler.extractItem(0, 1, false);
            }
            if (flhpBE.isLit()) {
                flhpBE.heatManager.consumeOrProduceHeat(flhpBE.getTempIncreasePerTick(stack), false);
            }
        }

        if (startedLit != flhpBE.isLit()) {
            //TODO: make blocks glow --- state = state.setValue(AbstractFurnaceBlock.LIT, Boolean.valueOf(flhpBE.isLit()));
            level.setBlock(pos, state, 3);
            flhpBE.update();
        }

        exchangeHeat(level, pos, state, flhpBE);
    }

    protected static boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
    }

    public int getBurnTime(ItemStack stack) {
        return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
    }

    public abstract float getTempIncreasePerTick(ItemStack stack);
}