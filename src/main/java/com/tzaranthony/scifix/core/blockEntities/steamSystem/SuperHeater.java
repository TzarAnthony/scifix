package com.tzaranthony.scifix.core.blockEntities.steamSystem;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SuperHeater extends HeatExchangingBE {
    public SuperHeater(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}