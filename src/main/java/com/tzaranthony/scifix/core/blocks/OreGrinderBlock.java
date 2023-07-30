package com.tzaranthony.scifix.core.blocks;

import com.tzaranthony.scifix.core.blockEntities.processing.oreRefining.OreGrindingBE;
import com.tzaranthony.scifix.core.blockEntities.processing.oreRefining.OreRefiningBE;
import com.tzaranthony.scifix.core.util.tags.SItemTags;
import com.tzaranthony.scifix.registries.SBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class OreGrinderBlock extends CraftingBlock {
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

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        ItemStack stack = player.getItemInHand(hand);
        if ((stack.is(SItemTags.GRINDABLE) || stack.isEmpty()) && blockentity instanceof OreGrindingBE griBE) {
            griBE.takeOrAddItem(player, stack);
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, SBlockEntities.GRINDER.get(), level.isClientSide ? OreRefiningBE::clientTick : OreRefiningBE::serverTick);
    }
}