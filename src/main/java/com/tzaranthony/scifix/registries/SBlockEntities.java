package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.core.blockEntities.processing.oreRefining.OreCrushingBE;
import com.tzaranthony.scifix.core.blockEntities.processing.oreRefining.OreGrindingBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> reg = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Scifix.MOD_ID);

    public static final RegistryObject<BlockEntityType<OreCrushingBE>> CRUSHER = reg.register("ore_crusher",
            () -> BlockEntityType.Builder.of(OreCrushingBE::new, SBlocks.ORE_CRUSHER_MK0.get(), SBlocks.ORE_CRUSHER_MK1.get(), SBlocks.ORE_CRUSHER_MK2.get(), SBlocks.ORE_CRUSHER_MK3.get()).build(null));

    public static final RegistryObject<BlockEntityType<OreGrindingBE>> GRINDER = reg.register("ore_grinder",
            () -> BlockEntityType.Builder.of(OreGrindingBE::new, SBlocks.ORE_GRINDER_MK0.get(), SBlocks.ORE_GRINDER_MK1.get(), SBlocks.ORE_GRINDER_MK2.get()).build(null));
}