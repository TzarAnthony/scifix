package com.tzaranthony.scifix.core.blocks;

import com.tzaranthony.scifix.api.handlers.BEHelpers.FluidBE;
import com.tzaranthony.scifix.api.handlers.BEHelpers.ItemBE;
import com.tzaranthony.scifix.api.handlers.BEHelpers.XpHoldingBE;
import com.tzaranthony.scifix.api.helpers.BlockEntityUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.VanillaInventoryCodeHooks;

public abstract class BaseBEBlock extends BaseEntityBlock {
    protected BaseBEBlock(Properties properties) {
        super(properties);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, pos, player);
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {}

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state1, boolean huh) {
        if (!state.is(state1.getBlock())) {
            if (level instanceof ServerLevel sLevel) {
                BlockEntity be = level.getBlockEntity(pos); // Maybe one day we'll store the contents and restore it as it was instead of dropping everything
                if (be instanceof ItemBE ibe) ibe.dropInventory(sLevel);
//                if (be instanceof FluidBE fbe) fbe.dropFluids(sLevel); // I decided that we won't drop fluids when breaking a block...
                if (be instanceof XpHoldingBE xpbe) xpbe.dropXp(sLevel);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, state1, huh);
        }
    }
}