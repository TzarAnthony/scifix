package com.tzaranthony.scifix.core.blocks;

import com.tzaranthony.scifix.core.util.damage.SDamageSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

public abstract class HeatBlock extends BaseEntityBlock {
    protected HeatBlock(Properties properties) {
        super(properties);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!entity.fireImmune() && entity instanceof LivingEntity le) {
            hurtTouchingEntities(le);
        }
        super.entityInside(state, level, pos, entity);
    }

    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        if (!entity.fireImmune() && entity instanceof LivingEntity le) {
            hurtTouchingEntities(le);
        }
        super.stepOn(level, pos, state, entity);
    }

    protected void hurtTouchingEntities(LivingEntity le) {
        float damage = EnchantmentHelper.hasFrostWalker(le) ? 4.0F : 8.0F;
        le.hurt(SDamageSource.EXTREME_TEMP, damage);
        le.setSecondsOnFire(10);
    }
}