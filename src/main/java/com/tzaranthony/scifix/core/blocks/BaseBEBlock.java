package com.tzaranthony.scifix.core.blocks;

import com.tzaranthony.scifix.api.BlockEntityUtils;
import com.tzaranthony.scifix.core.blockEntities.processing.CraftingBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.VanillaInventoryCodeHooks;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Optional;

public abstract class BaseBEBlock extends BaseEntityBlock {
    protected BaseBEBlock(Properties properties) {
        super(properties);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state1, boolean huh) {
        if (!state.is(state1.getBlock())) {
            if (level instanceof ServerLevel) {
                VanillaInventoryCodeHooks.getItemHandler(level, pos.getX(), pos.getY(), pos.getZ(), Direction.DOWN)
                        .map(itemHandlerResult -> {
                            IItemHandler handler = itemHandlerResult.getKey();
                            BlockEntityUtils.createContainer(handler);
                            return null;
                        });
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, state1, huh);
        }
    }
}