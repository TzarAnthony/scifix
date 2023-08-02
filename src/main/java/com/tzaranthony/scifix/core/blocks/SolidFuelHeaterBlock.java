package com.tzaranthony.scifix.core.blocks;

import com.tzaranthony.scifix.api.properties.ThermalProperties;
import com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing.SolidFuelHeaterBE;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class SolidFuelHeaterBlock extends HeatBlock {
    protected final ThermalProperties thermalProperties;

    public SolidFuelHeaterBlock(Properties properties, ThermalProperties thermalProperties) {
        super(properties);
        this.thermalProperties = thermalProperties;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SolidFuelHeaterBE(pos, state, thermalProperties);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        //TODO: open menu
        return InteractionResult.PASS;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, SBlockEntities.SOLID_HEATER.get(), SolidFuelHeaterBE::serverTick);
    }
}