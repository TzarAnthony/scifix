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
    private static final Item.Properties FIRE_RESISTANT = new Item.Properties().tab(Scifix.TAB).fireResistant();


    // Tool Stuff
    //TODO: Give a manual on login, can place it in a drafting table to make like a ponder system, but for lower tier components? upgrade to cpu later?
    public static final RegistryObject<Item> MANUAL = reg.register("manual", () -> new Item(STANDARD));
    // metal working tools?
    // something to read steam pressure, rf, portal particles, etc. without a menu
    // Should I make like a concrete hose thing, so you can hold a couple of thousand blocks of concrete to make reinforced concrete, and also you can paint it?


    // Minerals
    public static final RegistryObject<Item> GRAPHITE_CHUNK = reg.register("graphite_chunk", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> FLUORITE = reg.register("fluorite", () -> new Item(STANDARD)); // mining, or from electrolysis of alumina brine using carbon electrodes
    // coal coke?
    public static final RegistryObject<Item> SLAG = reg.register("slag", () -> new Item(STANDARD)); // electrolysis of red mud using carbon electrodes --- somehow splits into clay and iron?
    public static final RegistryObject<Item> COPPER_SULFATE = reg.register("copper_sulfate", () -> new Item(STANDARD)); // 2x alloy any copper ore with blaze powder and 25% chance cobalt instead and sometimes slag (gives sulfur dioxide if ingotified in a smeltery)
    public static final RegistryObject<Item> IRON_SULFATE = reg.register("iron_sulfate", () -> new Item(STANDARD)); // 2x alloy any iron ore with blaze powder and 25% chance nickel instead (gives sulfur dioxide if ingotified in a smeltery)
    public static final RegistryObject<Item> NICKEL_SULFATE = reg.register("nickel_sulfate", () -> new Item(STANDARD)); // alloy any iron/nickel ore with blaze powder (gives sulfur dioxide if ingotified in a smeltery)
    public static final RegistryObject<Item> COBALT_SULFATE = reg.register("cobalt_sulfate", () -> new Item(STANDARD)); // alloy any copper/cobalt ore with blaze powder (gives sulfur dioxide if ingotified in a smeltery)
    // Aluminium Production //TODO: check ratios
    public static final RegistryObject<Item> SODIUM_HYDROXIDE = reg.register("sodium_hydroxide", () -> new Item(STANDARD)); // < electrolysis < 250 mb salt brine
    public static final RegistryObject<Item> ALUMINUM_HYDROXIDE = reg.register("aluminum_hydroxide", () -> new Item(STANDARD)); // 4x < 200 heat mixing < any aluminum ore + sodium hydroxide
    public static final RegistryObject<Item> ALUMINUM_OXIDE = reg.register("aluminum_oxide", () -> new Item(STANDARD)); // .5x (alumina) < furnace < aluminum hydroxide
    // Beryllium Production
    public static final RegistryObject<Item> CALCIUM_SULFATE = reg.register("calcium_sulfate", () -> new Item(STANDARD)); // 300 mb Hydrofluoric acid & Calcium Sulfate < 265 heat mixer < fluorite + 100 mb sulfuric acid + 100 mb water
    public static final RegistryObject<Item> SODIUM_TETRAFLUOROBERYLLATE = reg.register("sodium_tetrafluoroberyllate", () -> new Item(STANDARD)); // 3 Sodium Tetrafluoroberyllate & 2 Alumina & 1 Powdered Quartz < alloy < 3 powdered emerald & 400 mb Sodium hexafluorosilicate
    public static final RegistryObject<Item> BERYLLIUM_HYDROXIDE = reg.register("beryllium_hydroxide", () -> new Item(STANDARD)); // Beryllium hydroxide & 1b fluoridated water (4 sodium fluoride) < 150 heat mixer < 1b water + 4 sodium hydroxide + Sodium Tetrafluoroberyllate
    // blaze for sulfur, ghast tears for Potassium Salt & Salt, ender pearls for ???, phantom membranes for ???, ---- netherrack for sulfur/potassium?


    // Petroleum Products
    public static final RegistryObject<Item> PET_PELLETS = reg.register("pet_pellets", () -> new Item(STANDARD)); // 3 PET pellets & 50 mb Water < catalytic reaction (titanium chloride) < 200 mb ethylene glycol + 200 mb terephthalic acid (liquid)
    public static final RegistryObject<Item> PP_PELLETS = reg.register("pp_pellets", () -> new Item(STANDARD)); // PP pellets < catalytic reaction (titanium sheet) < 300 mb propylene
    public static final RegistryObject<Item> URETHANE_ACRYLIC_RESIN = reg.register("urethane_acrylic_resin", () -> new Item(STANDARD)); // < mixing < 300 mb ethylene glycol + 200 mb acrylonitrile + 500 mb hydroxyethyl acrylate
    public static final RegistryObject<Item> PAN_RESIN = reg.register("pan_resin", () -> new Item(STANDARD)); // PAN resin + 1.5 b methanol < 50 heat mixing < 1 b acrylonitrile + 2 b methanol
    public static final RegistryObject<Item> PAN_FILAMENTS = reg.register("pan_filaments", () -> new Item(STANDARD)); // PAN filaments < unheated mixing < PAN resin
    public static final RegistryObject<Item> CARBON_FIBER_FILAMENTS = reg.register("carbon_fiber_filaments", () -> new Item(STANDARD)); // Carbon fiber filaments < furnace < PAN filament
    // carbon fiber plates and other stuff?
    public static final RegistryObject<Item> BITUMEN = reg.register("bitumen", () -> new Item(STANDARD)); // furnace < residual oils
    public static final RegistryObject<Item> PETCOKE = reg.register("petcoke", () -> new Item(STANDARD)); // arc furnace < asphalt
    public static final RegistryObject<Item> PLASTIC_SHEET = reg.register("plastic_sheets", () -> new Item(STANDARD)); // furnace < PET pellets || PP pellets
    public static final RegistryObject<Item> PLASTIC_TUBE = reg.register("plastic_sheets", () -> new Item(STANDARD)); // extrude plastic sheet
    public static final RegistryObject<Item> RUBBER = reg.register("rubber", () -> new Item(STANDARD)); // catalytic reaction (potassium persulfate (catalyst) + iron salt (catalyst)) < 100 mb butadiene + 50 mb styrene
    public static final RegistryObject<Item> THERMAL_RUBBER = reg.register("thermal_rubber", () -> new Item(STANDARD)); // catalytic reaction (potassium persulfate (catalyst) + iron salt (catalyst)) < 100 mb butadiene + 50 mb styrene + 1 aluminum hydroxide
    // Polyester / Nylon fabric ?
    // Kerosene lubricant? graphite lubricant?

    // Ores
    public static final RegistryObject<Item> RAW_ALUMINUM = reg.register("raw_aluminum", () -> new Item(STANDARD));

    public static final RegistryObject<Item> CRUSHED_IRON = reg.register("crushed_iron", () -> new Item(STANDARD)); // 2x crush raw ore + 20% gravel ---- dusts tag?
    public static final RegistryObject<Item> CRUSHED_COPPER = reg.register("crushed_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_GOLD = reg.register("crushed_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_EMERALD = reg.register("crushed_emerald", () -> new Item(STANDARD)); // does not double?
    public static final RegistryObject<Item> CRUSHED_QUARTZ = reg.register("crushed_emerald", () -> new Item(STANDARD)); // does not double?
    public static final RegistryObject<Item> CRUSHED_NICKEL = reg.register("crushed_nickel", () -> new Item(STANDARD)); // 25% change from crushing iron
    public static final RegistryObject<Item> CRUSHED_COBALT = reg.register("crushed_cobalt", () -> new Item(STANDARD)); // 5% change from crushing copper
    public static final RegistryObject<Item> CRUSHED_LEAD = reg.register("crushed_lead", () -> new Item(STANDARD)); // can also give arsenic? silver? bismuth? antimony?
    public static final RegistryObject<Item> CRUSHED_ALUMINUM = reg.register("crushed_aluminum", () -> new Item(STANDARD));

    public static final RegistryObject<Item> POWDERED_IRON = reg.register("powdered_iron", () -> new Item(STANDARD)); // 2x grind crushed ore + 20% sand
    public static final RegistryObject<Item> POWDERED_COPPER = reg.register("powdered_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> POWDERED_GOLD = reg.register("powdered_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> POWDERED_EMERALD = reg.register("powdered_emerald", () -> new Item(STANDARD)); // does not double?
    public static final RegistryObject<Item> POWDERED_QUARTZ = reg.register("powdered_quartz", () -> new Item(STANDARD)); // does not double?
    public static final RegistryObject<Item> POWDERED_GRAPHITE = reg.register("powdered_graphite", () -> new Item(STANDARD)); // from graphite chunk
    public static final RegistryObject<Item> POWDERED_NICKEL = reg.register("powdered_nickel", () -> new Item(STANDARD)); //25% chance from powdering iron
    public static final RegistryObject<Item> POWDERED_COBALT = reg.register("powdered_cobalt", () -> new Item(STANDARD)); // 5% change from powdering copper
    public static final RegistryObject<Item> POWDERED_LEAD = reg.register("powdered_lead", () -> new Item(STANDARD)); // can also give arsenic? silver? bismuth? antimony?
    public static final RegistryObject<Item> POWDERED_ALUMINUM = reg.register("powdered_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> POWDERED_TITANIUM = reg.register("powdered_titanium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> POWDERED_ZIRCONIUM = reg.register("powdered_zirconium", () -> new Item(STANDARD));

    // Ingots
    public static final RegistryObject<Item> INGOT_NICKEL = reg.register("ingot_nickel", () -> new Item(STANDARD)); // from iron
    public static final RegistryObject<Item> INGOT_COBALT = reg.register("ingot_cobalt", () -> new Item(STANDARD)); // from copper
    public static final RegistryObject<Item> INGOT_ZINC = reg.register("ingot_zinc", () -> new Item(STANDARD)); //idk about this one, maybe need for corrosion resistance for steel exposed to water / steam, valves and what not? https://en.wikipedia.org/wiki/Zinc#Applications
    public static final RegistryObject<Item> INGOT_LEAD = reg.register("ingot_lead", () -> new Item(STANDARD));
    public static final RegistryObject<Item> INGOT_ALUMINUM = reg.register("ingot_aluminum", () -> new Item(STANDARD)); // alloy any aluminium ore with coal or 2x < electrolysis < molten alumina brine
    public static final RegistryObject<Item> INGOT_GALLIUM = reg.register("ingot_gallium", () -> new Item(STANDARD)); // electrolysis of red mud using carbon electrodes
    public static final RegistryObject<Item> INGOT_TITANIUM = reg.register("ingot_titanium", () -> new Item(STANDARD)); // https://en.wikipedia.org/wiki/Titanium#Production
    public static final RegistryObject<Item> INGOT_ZIRCONIUM = reg.register("ingot_zirconium", () -> new Item(FIRE_RESISTANT)); // secondary output from titanium (used for reactor shielding and heat shielding)
    public static final RegistryObject<Item> INGOT_TUNGSTEN = reg.register("ingot_tungsten", () -> new Item(FIRE_RESISTANT)); // need production process ??????
    public static final RegistryObject<Item> INGOT_NIOBIUM = reg.register("ingot_niobium", () -> new Item(FIRE_RESISTANT)); // need production process ???????
    public static final RegistryObject<Item> INGOT_BERYLLIUM = reg.register("ingot_beryllium", () -> new Item(STANDARD)); // beryllium ingot & 100 mb chlorine gas & 100 mb water < electrolysis < 200 mb Beryllium chloride
    public static final RegistryObject<Item> INGOT_URANIUM = reg.register("ingot_uranium", () -> new Item(STANDARD)); // need production process
    public static final RegistryObject<Item> INGOT_THORIUM = reg.register("ingot_thorium", () -> new Item(FIRE_RESISTANT)); // need production process
    public static final RegistryObject<Item> INGOT_PLUTONIUM = reg.register("ingot_plutonium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> INGOT_PLATINUM = reg.register("ingot_platinum", () -> new Item(FIRE_RESISTANT)); // need production process (rarely from iron / copper https://en.wikipedia.org/wiki/Platinum#Occurrence)
    // Alloys
    public static final RegistryObject<Item> INGOT_STEEL = reg.register("ingot_steel", () -> new Item(STANDARD)); // 1 iron, 2 coal or 1 petcoke
    public static final RegistryObject<Item> INGOT_CUPRO_NICKEL = reg.register("ingot_cupro_nickel", () -> new Item(FIRE_RESISTANT)); // 3x alloy 2 copper and nickel
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE = reg.register("tungsten_carbide", () -> new Item(FIRE_RESISTANT)); // alloy tungsten ingot with 2 graphite chunks --- fuel cells need this?


    // Mold --- maybe change this to be 1 item with NBT data
    public static final RegistryObject<Item> MOLD = reg.register("mold", () -> new Item(FIRE_RESISTANT)); // 2x #XY,#ZY,#XY -- # = steel plate, X = sand, Y = graphite powder, Z = PAN resin
    public static final RegistryObject<Item> MOLD_INGOT = reg.register("mold_ingot", () -> new Item(FIRE_RESISTANT)); // mold + ingot
    public static final RegistryObject<Item> MOLD_CABLE = reg.register("mold_cable", () -> new Item(FIRE_RESISTANT)); // mold + cable
    public static final RegistryObject<Item> MOLD_PLATE = reg.register("mold_plate", () -> new Item(FIRE_RESISTANT)); // mold + plate
    public static final RegistryObject<Item> MOLD_ROD = reg.register("mold_rod", () -> new Item(FIRE_RESISTANT)); // mold + rod
    public static final RegistryObject<Item> MOLD_TUBE = reg.register("mold_tube", () -> new Item(FIRE_RESISTANT)); // mold + tube
    public static final RegistryObject<Item> MOLD_GEAR = reg.register("mold_gear", () -> new Item(FIRE_RESISTANT)); // mold + gear
    public static final RegistryObject<Item> MOLD_BLOCK = reg.register("mold_block", () -> new Item(FIRE_RESISTANT)); // mold + gear


    // Mechanical Components
    //TODO: upgrade a machine by using a higher tier chassis on it
    public static final RegistryObject<Item> CHASSIS_MK0 = reg.register("chassis_mk0", () -> new Item(STANDARD)); // #X#,#Y# -- # = iron plate, X = frame, Y = battery? capacitor?
    public static final RegistryObject<Item> CHASSIS_MK1 = reg.register("chassis_mk1", () -> new Item(STANDARD)); // #X#,#Y# -- # = steel plate, X = frame, Y = battery
    public static final RegistryObject<Item> CHASSIS_MK2 = reg.register("chassis_mk2", () -> new Item(STANDARD)); // #X#,#Y#,#Z# -- # = plastic, X = frame, Y = battery, Z = circuit
    public static final RegistryObject<Item> CHASSIS_MK3 = reg.register("chassis_mk3", () -> new Item(STANDARD)); // X#X,YYY,ZZZ -- # = frame, X = battery, Y = circuit, Z = titanium plate
    // should I make a chassis assembly unit that can do "large" crafting for the higher tier chassis

    //TODO: these need durability and different speed/pressure maximums
    public static final RegistryObject<Item> TURBINE_MK0 = reg.register("turbine_mk0", () -> new Item(STANDARD)); //  # ,#X#, #  -- # = iron plate, X = iron rod
    public static final RegistryObject<Item> TURBINE_MK1 = reg.register("turbine_mk1", () -> new Item(STANDARD)); //  # ,XYX, #  -- # = steel plate, X = nickle plate, Y = steel rod
    public static final RegistryObject<Item> TURBINE_MK2 = reg.register("turbine_mk2", () -> new Item(STANDARD)); //  # ,###, #  -- # = aluminum ingot
    public static final RegistryObject<Item> TURBINE_MK3 = reg.register("turbine_mk3", () -> new Item(STANDARD)); //  # ,###, #  -- # = titanium ingot //Alloy?
    public static final RegistryObject<Item> TURBINE_MK4 = reg.register("turbine_mk4", () -> new Item(STANDARD)); //  # ,#X#, #  -- # = carbon fiber, X = titanium ingot

    public static final RegistryObject<Item> HEAT_EXCHANGER_MK0 = reg.register("heat_exchanger_mk0", () -> new Item(STANDARD)); // #X,#X,#X -- # = copper plates, X = copper tube
    public static final RegistryObject<Item> HEAT_EXCHANGER_MK1 = reg.register("heat_exchanger_mk1", () -> new Item(STANDARD)); // X#X,X#X,X#X -- # = cupro-nickel plates, X = cupro-nickel tube
    public static final RegistryObject<Item> HEAT_EXCHANGER_MK2 = reg.register("heat_exchanger_mk2", () -> new Item(STANDARD)); // #X#,YYY,#X# -- # = thermal gasket, X = cupro-nickel tube, Y = heat exchanger 1

    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK0 = reg.register("wind_turbine_blade_mk0", () -> new Item(STANDARD)); //   #, #X,#XX -- # = wood, X = wool
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK1 = reg.register("wind_turbine_blade_mk1", () -> new Item(STANDARD)); // wood and plastic
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK2 = reg.register("wind_turbine_blade_mk2", () -> new Item(STANDARD)); // fiber glass and carbon fiber?
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK3 = reg.register("wind_turbine_blade_mk3", () -> new Item(STANDARD));

    public static final RegistryObject<Item> GEAR_BOX_MK0 = reg.register("gear_box_mk0", () -> new Item(STANDARD)); // #X#,X#,YYY -- # = gear, X = rod, Y = plate
    public static final RegistryObject<Item> GEAR_BOX_MK1 = reg.register("gear_box_mk1", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_BOX_MK2 = reg.register("gear_box_mk2", () -> new Item(STANDARD));

    public static final RegistryObject<Item> GASKET = reg.register("gasket", () -> new Item(STANDARD)); // X#X,# #,X#X -- # = rubber, X = aluminum plate
    public static final RegistryObject<Item> THERMAL_GASKET = reg.register("thermal_gasket", () -> new Item(STANDARD)); // X#X,# #,X#X -- # = thermal rubber, X = aluminum plate
    public static final RegistryObject<Item> VALVE = reg.register("valve", () -> new Item(STANDARD)); // # , X ,ZYZ -- # = motor, X = ??? rod, Y = gasket, Z = ??? pipe/tube
    public static final RegistryObject<Item> THERMAL_VALVE = reg.register("thermal_valve", () -> new Item(STANDARD)); // # , X ,ZYZ -- # = motor, X = ??? rod, Y = thermal gasket, Z = ??? pipe/tube

    //TODO: generates 60 base heat, -10 heat in cold biomes, and +20 heat in hot biomes.
    public static final RegistryObject<Item> MIRROR_MK0 = reg.register("mirror_mk0", () -> new Item(STANDARD)); // 2x ###,XXX,### -- # = glass pane, X = copper plate
    public static final RegistryObject<Item> MIRROR_MK1 = reg.register("mirror_mk1", () -> new Item(STANDARD)); // 2x ###,XXX,### -- # = glass pane, X = aluminum plate
    public static final RegistryObject<Item> MIRROR_MK2 = reg.register("mirror_mk2", () -> new Item(STANDARD)); // 2x ###,XXX,YZY -- # = glass pane, X = silver plate, Y = rubber, Z = steel plate


    // Electrical Components
    // should i make cables work like leads to link blocks? too many makes too much client lag?
    public static final RegistryObject<Item> WIRE_MK0 = reg.register("wire_mk0", () -> new Item(STANDARD)); // 3 copper ingot (needs insulation?)
    public static final RegistryObject<Item> WIRE_MK1 = reg.register("wire_mk1", () -> new Item(STANDARD)); // 3 gold ingot (needs insulation?)
    public static final RegistryObject<Item> WIRE_MK2 = reg.register("wire_mk2", () -> new Item(STANDARD)); // ###,#X#,### -- # = optical fiber 2, X = Urethane Acrylic Resin
    public static final RegistryObject<Item> WIRE_MK3 = reg.register("wire_mk3", () -> new Item(STANDARD)); // #X#,XXX,#X# -- # = plastic / rubber 2, X = nanotube cluster
    public static final RegistryObject<Item> WIRE_MK4 = reg.register("wire_mk4", () -> new Item(STANDARD)); // something super conductive

    public static final RegistryObject<Item> OPTICAL_FIBER = reg.register("optical_fiber", () -> new Item(STANDARD)); // 24x only made in an extruder -- 1 glass
    public static final RegistryObject<Item> NANOTUBE_CLUSTER = reg.register("nanotube_cluster", () -> new Item(STANDARD)); // ###,###,### -- # = carbon nanotube

    public static final RegistryObject<Item> SOLENOID_MK0 = reg.register("solenoid_copper", () -> new Item(STANDARD)); // 6 copper wires vertically
    public static final RegistryObject<Item> SOLENOID_MK1 = reg.register("solenoid_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> SOLENOID_MK2 = reg.register("solenoid_fiber", () -> new Item(STANDARD));
    public static final RegistryObject<Item> SOLENOID_MK3 = reg.register("solenoid_nano", () -> new Item(STANDARD));
    public static final RegistryObject<Item> SOLENOID_MK4 = reg.register("solenoid_super", () -> new Item(STANDARD));

    // https://en.wikipedia.org/wiki/Refractory
    public static final RegistryObject<Item> HEATING_COIL_MK0 = reg.register("heating_coil_mk0", () -> new Item(STANDARD)); // copper ingot + graphite + copper ingot = 2
    public static final RegistryObject<Item> HEATING_COIL_MK1 = reg.register("heating_coil_mk1", () -> new Item(STANDARD)); // cupro-nickel
    public static final RegistryObject<Item> HEATING_COIL_MK2 = reg.register("heating_coil_mk2", () -> new Item(STANDARD)); // silicon carbide
    public static final RegistryObject<Item> HEATING_COIL_MK3 = reg.register("heating_coil_mk3", () -> new Item(STANDARD)); // tungsten

    public static final RegistryObject<Item> MOTOR_MK0 = reg.register("motor_mk0", () -> new Item(STANDARD)); //  # ,XYX, #  -- # = iron ingot, X = copper coil, Y = redstone
    public static final RegistryObject<Item> MOTOR_MK1 = reg.register("motor_mk1", () -> new Item(STANDARD));
    public static final RegistryObject<Item> MOTOR_MK2 = reg.register("motor_mk2", () -> new Item(STANDARD));
    public static final RegistryObject<Item> MOTOR_MK3 = reg.register("motor_mk3", () -> new Item(STANDARD));

    public static final RegistryObject<Item> ELECTRODES_CARBON = reg.register("electrodes_carbon", () -> new Item(STANDARD));

    public static final RegistryObject<Item> ELECTROMAGNET_MK0 = reg.register("electromagnet_mk0", () -> new Item(STANDARD)); //  # ,XYX, #  -- # = iron rod, X = copper coil, Y = redstone
    public static final RegistryObject<Item> ELECTROMAGNET_MK1 = reg.register("electromagnet_mk1", () -> new Item(STANDARD)); // nanoperm?, permalloy (4 Ni : 1 Fe), Niobium–titanium?
    public static final RegistryObject<Item> ELECTROMAGNET_MK2 = reg.register("electromagnet_mk2", () -> new Item(STANDARD));

    // maybe ill make these have their own block like in create?
    public static final RegistryObject<Item> MIXER = reg.register("mixer", () -> new Item(STANDARD)); // #X#,YZY, W  -- # = copper wiring, X = motor 0, Y = redstone, Z = chassis 1, W = steel ingot
    public static final RegistryObject<Item> ELECTROLYSIS_APPARATUS = reg.register("electrolysis_apparatus", () -> new Item(STANDARD)); // #Y#,#Y#,#Y# -- # = gold wiring, X = redstone, Y = chassis 1


    // Basic Parts --- maybe I will add a metal working kit to craft these -- also I'll probably change this to make a function create these
    // Plates //TODO: rolled ingot || 2x ###
    public static final RegistryObject<Item> PLATE_IRON = reg.register("plate_iron", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_COPPER = reg.register("plate_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_GOLD = reg.register("plate_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_NICKEL = reg.register("plate_nickel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_COBALT = reg.register("plate_cobalt", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_ALUMINUM = reg.register("plate_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_GALLIUM = reg.register("plate_gallium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_TITANIUM = reg.register("plate_titanium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_ZIRCONIUM = reg.register("plate_zirconium", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> PLATE_TUNGSTEN = reg.register("plate_tungsten", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> PLATE_NIOBIUM  = reg.register("plate_niobium", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> PLATE_BERYLLIUM = reg.register("plate_beryllium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_PLATINUM = reg.register("plate_platinum", () -> new Item(FIRE_RESISTANT));
    // Alloys
    public static final RegistryObject<Item> PLATE_STEEL = reg.register("plate_steel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> PLATE_CUPRO_NICKEL = reg.register("plate_cupro_nickel", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> PLATE_TUNGSTEN_CARBIDE = reg.register("plate_tungsten_carbide", () -> new Item(FIRE_RESISTANT));

    // Rods //TODO: extruded ingot || 2x #,#,#
    public static final RegistryObject<Item> ROD_IRON = reg.register("rod_iron", () -> new Item(STANDARD)); // extruded iron ingot
    public static final RegistryObject<Item> ROD_COPPER = reg.register("rod_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_GOLD = reg.register("rod_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_NICKEL = reg.register("rod_nickel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_COBALT = reg.register("rod_cobalt", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_ALUMINUM = reg.register("rod_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_GALLIUM = reg.register("rod_gallium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_TITANIUM = reg.register("rod_titanium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_ZIRCONIUM = reg.register("rod_zirconium", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> ROD_TUNGSTEN = reg.register("rod_tungsten", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> ROD_NIOBIUM  = reg.register("rod_niobium", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> ROD_BERYLLIUM = reg.register("rod_beryllium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_PLATINUM = reg.register("rod_platinum", () -> new Item(FIRE_RESISTANT));
    // Alloys
    public static final RegistryObject<Item> ROD_STEEL = reg.register("rod_steel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ROD_CUPRO_NICKEL = reg.register("rod_cupro_nickel", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> ROD_TUNGSTEN_CARBIDE = reg.register("rod_tungsten_carbide", () -> new Item(FIRE_RESISTANT));

    // Tubes //TODO: rolled plate || 2x #,#,#
    public static final RegistryObject<Item> TUBE_IRON = reg.register("tube_iron", () -> new Item(STANDARD)); // rolled iron ingot
    public static final RegistryObject<Item> TUBE_COPPER = reg.register("tube_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_GOLD = reg.register("tube_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_NICKEL = reg.register("tube_nickel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_COBALT = reg.register("tube_cobalt", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_ALUMINUM = reg.register("tube_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_GALLIUM = reg.register("tube_gallium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_TITANIUM = reg.register("tube_titanium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_ZIRCONIUM = reg.register("tube_zirconium", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> TUBE_TUNGSTEN = reg.register("tube_tungsten", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> TUBE_NIOBIUM  = reg.register("tube_niobium", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> TUBE_BERYLLIUM = reg.register("tube_beryllium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_PLATINUM = reg.register("tube_platinum", () -> new Item(FIRE_RESISTANT));
    // Alloys
    public static final RegistryObject<Item> TUBE_STEEL = reg.register("tube_steel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> TUBE_CUPRO_NICKEL = reg.register("tube_cupro_nickel", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> TUBE_TUNGSTEN_CARBIDE = reg.register("tube_tungsten_carbide", () -> new Item(FIRE_RESISTANT));

    // Gears //TODO: pressed ingot || # ,# #, #
    public static final RegistryObject<Item> GEAR_IRON = reg.register("gear_iron", () -> new Item(STANDARD)); // pressed iron ingot
    public static final RegistryObject<Item> GEAR_COPPER = reg.register("gear_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_GOLD = reg.register("gear_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_NICKEL = reg.register("gear_nickel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_COBALT = reg.register("gear_cobalt", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_ALUMINUM = reg.register("gear_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_GALLIUM = reg.register("gear_gallium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_TITANIUM = reg.register("gear_titanium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_ZIRCONIUM = reg.register("gear_zirconium", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> GEAR_TUNGSTEN = reg.register("gear_tungsten", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> GEAR_NIOBIUM  = reg.register("gear_niobium", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> GEAR_BERYLLIUM = reg.register("gear_beryllium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_PLATINUM = reg.register("gear_platinum", () -> new Item(FIRE_RESISTANT));
    // Alloys
    public static final RegistryObject<Item> GEAR_STEEL = reg.register("gear_steel", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_CUPRO_NICKEL = reg.register("gear_cupro_nickel", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> GEAR_TUNGSTEN_CARBIDE = reg.register("gear_tungsten_carbide", () -> new Item(FIRE_RESISTANT));
}