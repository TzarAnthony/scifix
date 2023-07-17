package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SItems {
    public static final DeferredRegister<Item> reg = DeferredRegister.create(ForgeRegistries.ITEMS, Scifix.MOD_ID);

    // Item Properties
    private static final Item.Properties STANDARD = new Item.Properties().tab(Scifix.TAB);

    // Minerals
    public static final RegistryObject<Item> GRAPHITE_CHUNK = reg.register("graphite_chunk", () -> new Item(STANDARD));
    public static final RegistryObject<Item> FLUORITE = reg.register("fluorite", () -> new Item(STANDARD)); // mining, or from electrolysis of alumina brine using carbon electrodes
    public static final RegistryObject<Item> SODIUM_HYDROXIDE = reg.register("sodium_hydroxide", () -> new Item(STANDARD)); // electrolysis of salt brine using carbon electrodes
    public static final RegistryObject<Item> ALUMINA = reg.register("alumina", () -> new Item(STANDARD)); // 4x from crushed or powdered aluminum ore + sodium hydroxide in a heated mixer or in a blast furnace?
    public static final RegistryObject<Item> SLAG = reg.register("slag", () -> new Item(STANDARD)); // electrolysis of red mud using carbon electrodes --- somehow splits into clay and iron?
    public static final RegistryObject<Item> COPPER_SULFATE = reg.register("copper_sulfate", () -> new Item(STANDARD)); // 2x alloy any copper ore with blaze powder and sometimes slag (gives sulfur dioxide if ingotified in a smeltery)
    public static final RegistryObject<Item> IRON_SULFATE = reg.register("copper_sulfate", () -> new Item(STANDARD)); // 2x alloy any iron ore with blaze powder and 25% chance nickel instead (gives sulfur dioxide if ingotified in a smeltery)
    public static final RegistryObject<Item> NICKEL_SULFATE = reg.register("copper_sulfate", () -> new Item(STANDARD)); // alloy any iron ore with blaze powder (gives sulfur dioxide if ingotified in a smeltery)
    
    // Ores
    public static final RegistryObject<Item> RAW_ALUMINUM = reg.register("raw_aluminum", () -> new Item(STANDARD));

    public static final RegistryObject<Item> CRUSHED_IRON = reg.register("crushed_iron", () -> new Item(STANDARD)); // 2x crush raw ore
    public static final RegistryObject<Item> CRUSHED_COPPER = reg.register("crushed_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_COLD = reg.register("crushed_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_ALUMINUM = reg.register("crushed_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_NICKEL = reg.register("crushed_nickel", () -> new Item(STANDARD)); // 25% change from crushing iron

    public static final RegistryObject<Item> POWDERED_IRON = reg.register("powdered_iron", () -> new Item(STANDARD)); // 2x grind crushed ore
    public static final RegistryObject<Item> POWDERED_COPPER = reg.register("powdered_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> POWDERED_GOLD = reg.register("powdered_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> POWDERED_ALUMINUM = reg.register("powdered_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> POWDERED_NICKEL = reg.register("powdered_nickel", () -> new Item(STANDARD)); //25% chance from powdering iron
    
    // Ingots
    public static final RegistryObject<Item> INGOT_STEEL = reg.register("ingot_steel", () -> new Item(STANDARD)); // 1 iron, 2 coal
    public static final RegistryObject<Item> INGOT_ZINC = reg.register("ingot_zinc", () -> new Item(STANDARD)); //idk about this one
    public static final RegistryObject<Item> INGOT_TIN = reg.register("ingot_tin", () -> new Item(STANDARD)); //idk about this one
    public static final RegistryObject<Item> INGOT_ALUMINUM = reg.register("ingot_aluminum", () -> new Item(STANDARD)); // alloy any aluminium ore with coal or from electrolysis of alumina brine
    public static final RegistryObject<Item> INGOT_GALLIUM = reg.register("ingot_gallium", () -> new Item(STANDARD)); // electrolysis of red mud using carbon electrodes
    public static final RegistryObject<Item> INGOT_NICKEL = reg.register("ingot_nickel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> INGOT_CUPRO_NICKEL = reg.register("ingot_cupro_nickel", () -> new Item(STANDARD)); // 3x alloy 2 copper and nickel
    public static final RegistryObject<Item> INGOT_COBALT = reg.register("ingot_cobalt", () -> new Item(STANDARD)); //idk about this one
    public static final RegistryObject<Item> INGOT_TITANIUM = reg.register("ingot_titanium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> INGOT_TUNGSTEN = reg.register("ingot_tungsten", () -> new Item(STANDARD)); // some kind of refining process with chlorine
    public static final RegistryObject<Item> INGOT_URANIUM = reg.register("ingot_uranium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> INGOT_THORIUM = reg.register("ingot_thorium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> INGOT_PLUTONIUM = reg.register("ingot_plutonium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> INGOT_PLATINUM = reg.register("ingot_platinum", () -> new Item(STANDARD)); //idk about this one
    public static final RegistryObject<Item> INGOT_IRIDIUM = reg.register("ingot_iridium", () -> new Item(STANDARD)); //idk about this one
    public static final RegistryObject<Item> INGOT_OSMIUM = reg.register("ingot_osmium", () -> new Item(STANDARD)); //idk about this one

    // Plates
    public static final RegistryObject<Item> PLATE_IRON = reg.register("plate_iron", () -> new Item(STANDARD)); // pressed iron ingot
    public static final RegistryObject<Item> PLATE_COPPER = reg.register("plate_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_GOLD = reg.register("plate_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_STEEL = reg.register("plate_steel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_ALUMINUM = reg.register("plate_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_NICKEL = reg.register("plate_nickel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_CUPRO_NICKEL = reg.register("plate_cupro_nickel", () -> new Item(STANDARD));

    // Mechanical Components
    public static final RegistryObject<Item> CHASSIS_MK0 = reg.register("chassis_mk0", () -> new Item(STANDARD)); // #X#,X X,#X# -- # = iron ingot, X = iron nugget
    public static final RegistryObject<Item> CHASSIS_MK1 = reg.register("chassis_mk1", () -> new Item(STANDARD)); // #X#,X X,#X# -- # = steel ingot, X = iron nugget
    public static final RegistryObject<Item> CHASSIS_MK2 = reg.register("chassis_mk2", () -> new Item(STANDARD)); // #X#,X X,#X# -- # = plastic, X = aluminum ingot
    public static final RegistryObject<Item> CHASSIS_MK3 = reg.register("chassis_mk3", () -> new Item(STANDARD)); // #X#,X X,#X# -- # = iron ingot, X = iron nugget
    public static final RegistryObject<Item> CHASSIS_MK4 = reg.register("chassis_mk4", () -> new Item(STANDARD)); // #X#,X X,#X# -- # = iron ingot, X = iron nugget
    public static final RegistryObject<Item> CHASSIS_MK5 = reg.register("chassis_mk5", () -> new Item(STANDARD)); // #X#,X X,#X# -- # = iron ingot, X = iron nugget

    //TODO: these need durability
    public static final RegistryObject<Item> TURBINE_MK0 = reg.register("turbine_mk0", () -> new Item(STANDARD)); //  # ,###, #  -- # = iron ingot
    public static final RegistryObject<Item> TURBINE_MK1 = reg.register("turbine_mk1", () -> new Item(STANDARD)); //  # ,###, #  -- # = steel ingot
    public static final RegistryObject<Item> TURBINE_MK2 = reg.register("turbine_mk2", () -> new Item(STANDARD)); //  # ,###, #  -- # = aluminum ingot
    public static final RegistryObject<Item> TURBINE_MK3 = reg.register("turbine_mk3", () -> new Item(STANDARD)); //  # ,###, #  -- # = titanium ingot //Alloy?
    public static final RegistryObject<Item> TURBINE_MK4 = reg.register("turbine_mk4", () -> new Item(STANDARD)); //  # ,#X#, #  -- # = carbon fiber, X = titanium ingot

    //TODO: these need durability?
    public static final RegistryObject<Item> HEAT_EXCHANGER_MK0 = reg.register("heat_exchanger_mk0", () -> new Item(STANDARD)); // 4 copper plates in a metal former
    public static final RegistryObject<Item> HEAT_EXCHANGER_MK1 = reg.register("heat_exchanger_mk1", () -> new Item(STANDARD)); // 2 copper plates and 2 cupro-nickel plates in a metal former

    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK0 = reg.register("wind_turbine_blade_mk0", () -> new Item(STANDARD)); //   #, #X,#XX -- # = wood, X = wool
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK1 = reg.register("wind_turbine_blade_mk1", () -> new Item(STANDARD)); // wood and plastic
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK2 = reg.register("wind_turbine_blade_mk2", () -> new Item(STANDARD)); // fiber glass and carbon fiber?
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK3 = reg.register("wind_turbine_blade_mk3", () -> new Item(STANDARD));

    // Electrical Components
    // should i make cables work like leads to link blocks? too many makes too much client lag?
    public static final RegistryObject<Item> WIRE_MK0 = reg.register("wire_copper", () -> new Item(STANDARD)); // 3 copper ingot (needs insulation?)
    public static final RegistryObject<Item> WIRE_MK1 = reg.register("wire_gold", () -> new Item(STANDARD)); // 3 copper ingot (needs insulation?)
    public static final RegistryObject<Item> WIRE_MK2 = reg.register("wire_fiber", () -> new Item(STANDARD)); // 2 fiber optic cables
    public static final RegistryObject<Item> WIRE_MK3 = reg.register("wire_nano", () -> new Item(STANDARD)); // 9 carbon nanotubes
    public static final RegistryObject<Item> WIRE_MK4 = reg.register("wire_super", () -> new Item(STANDARD));

    public static final RegistryObject<Item> COIL_MK0 = reg.register("coil_copper", () -> new Item(STANDARD)); // 3 copper wiring
    public static final RegistryObject<Item> COIL_MK1 = reg.register("coil_gold", () -> new Item(STANDARD)); // 3 gold wiring
    public static final RegistryObject<Item> COIL_MK2 = reg.register("coil_fiber", () -> new Item(STANDARD));
    public static final RegistryObject<Item> COIL_MK3 = reg.register("coil_nano", () -> new Item(STANDARD));
    public static final RegistryObject<Item> COIL_MK4 = reg.register("coil_super", () -> new Item(STANDARD));

    public static final RegistryObject<Item> HEATING_RESISTOR_MK0 = reg.register("heating_resistor_mk0", () -> new Item(STANDARD)); // copper ingot + graphite + copper ingot = 2
    public static final RegistryObject<Item> HEATING_RESISTOR_MK1 = reg.register("heating_resistor_mk1", () -> new Item(STANDARD)); // cupro-nickel
    public static final RegistryObject<Item> HEATING_RESISTOR_MK2 = reg.register("heating_resistor_mk2", () -> new Item(STANDARD)); // silicon carbide
    public static final RegistryObject<Item> HEATING_RESISTOR_MK3 = reg.register("heating_resistor_mk3", () -> new Item(STANDARD)); // tungsten

    public static final RegistryObject<Item> MOTOR_MK0 = reg.register("motor_mk0", () -> new Item(STANDARD)); // copper coil + iron ingot + redstone
    public static final RegistryObject<Item> MOTOR_MK1 = reg.register("motor_mk1", () -> new Item(STANDARD));
    public static final RegistryObject<Item> MOTOR_MK2 = reg.register("motor_mk2", () -> new Item(STANDARD));
    public static final RegistryObject<Item> MOTOR_MK3 = reg.register("motor_mk3", () -> new Item(STANDARD));

    public static final RegistryObject<Item> ELECTRODES_CARBON = reg.register("electrodes_carbon", () -> new Item(STANDARD));
    
    // maybe ill make these have their own block like in create?
    public static final RegistryObject<Item> MIXER = reg.register("mixer", () -> new Item(STANDARD)); // #X#,YZY, W  -- # = copper wiring, X = motor 0, Y = redstone, Z = chassis 1, W = steel ingot
    public static final RegistryObject<Item> ELECTROLYSIS_APPARATUS = reg.register("electrolysis_apparatus", () -> new Item(STANDARD)); // #Y#,#Y#,#Y# -- # = gold wiring, X = redstone, Y = chassis 1
}