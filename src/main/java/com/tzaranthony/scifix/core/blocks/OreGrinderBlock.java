package com.tzaranthony.scifix.core.blocks;

import com.tzaranthony.scifix.core.blockEntities.processing.machines.oreRefining.OreGrindingBE;
import com.tzaranthony.scifix.core.blockEntities.processing.machines.oreRefining.OreRefiningBE;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

public class OreGrinderBlock extends BaseBEBlock {
    protected final int tier;

    public OreGrinderBlock(Properties properties, int tier) {
        super(properties);
        this.tier = tier;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        OreGrindingBE crusher = new OreGrindingBE(pos, state, this.tier);
        return crusher;
    }

    @Override
    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof OreGrindingBE) {
            NetworkHooks.openGui((ServerPlayer) player, (OreGrindingBE) blockentity, pos);
        }
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, SBlockEntities.GRINDER.get(), level.isClientSide ? OreRefiningBE::clientTick : OreRefiningBE::serverTick);
    }
}