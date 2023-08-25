package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.core.blockEntities.processing.machines.ElectricFurnaceBE;
import com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing.ConvectionHeaterBE;
import com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing.LiquidFuelHeaterBE;
import com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing.ResistanceHeaterBE;
import com.tzaranthony.scifix.core.blockEntities.steamSystem.heatProducing.SolidFuelHeaterBE;
import com.tzaranthony.scifix.core.blockEntities.processing.machines.oreRefining.OreCrushingBE;
import com.tzaranthony.scifix.core.blockEntities.processing.machines.oreRefining.OreGrindingBE;
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

    public static final RegistryObject<BlockEntityType<ElectricFurnaceBE>> E_FURNACE = reg.register("e_furnace",
            () -> BlockEntityType.Builder.of(ElectricFurnaceBE::new, SBlocks.ELECTRIC_FURNACE_MK0.get(), SBlocks.ELECTRIC_FURNACE_MK1.get(), SBlocks.ELECTRIC_FURNACE_MK2.get(), SBlocks.ELECTRIC_FURNACE_MK3.get()).build(null));


    public static final RegistryObject<BlockEntityType<SolidFuelHeaterBE>> SOLID_HEATER = reg.register("solid_heater",
            () -> BlockEntityType.Builder.of(SolidFuelHeaterBE::new, SBlocks.SOLID_FUEL_HEATER_MK0.get(), SBlocks.SOLID_FUEL_HEATER_MK1.get(), SBlocks.SOLID_FUEL_HEATER_MK2.get(), SBlocks.SOLID_FUEL_HEATER_MK3.get()).build(null));
    public static final RegistryObject<BlockEntityType<ConvectionHeaterBE>> CONVECTION_HEATER = reg.register("convection_heater",
            () -> BlockEntityType.Builder.of(ConvectionHeaterBE::new, SBlocks.CONVECTION_HEATER_MK0.get(), SBlocks.CONVECTION_HEATER_MK1.get(), SBlocks.CONVECTION_HEATER_MK2.get(), SBlocks.CONVECTION_HEATER_MK3.get()).build(null));
    public static final RegistryObject<BlockEntityType<LiquidFuelHeaterBE>> LIQUID_HEATER = reg.register("liquid_heater",
            () -> BlockEntityType.Builder.of(LiquidFuelHeaterBE::new, SBlocks.LIQUID_FUEL_HEATER_MK0.get(), SBlocks.LIQUID_FUEL_HEATER_MK1.get(), SBlocks.LIQUID_FUEL_HEATER_MK2.get(), SBlocks.LIQUID_FUEL_HEATER_MK3.get()).build(null));
    public static final RegistryObject<BlockEntityType<ResistanceHeaterBE>> RESISTANCE_HEATER = reg.register("resistance_heater",
            () -> BlockEntityType.Builder.of(ResistanceHeaterBE::new, SBlocks.RESISTANCE_HEATER_MK0.get(), SBlocks.RESISTANCE_HEATER_MK1.get(), SBlocks.RESISTANCE_HEATER_MK2.get(), SBlocks.RESISTANCE_HEATER_MK3.get()).build(null));
}