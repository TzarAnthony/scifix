package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SItems {
    public static final DeferredRegister<Item> reg = DeferredRegister.create(ForgeRegistries.ITEMS, Scifix.MOD_ID);

    // Item Properties
    private static final Item.Properties STANDARD = new Item.Properties().tab(Scifix.TAB).stacksTo(64);
    private static final Item.Properties FIRE_RESISTANT = new Item.Properties().tab(Scifix.TAB).fireResistant().stacksTo(64);
    private static final Item.Properties TOOL = new Item.Properties().tab(Scifix.TAB).stacksTo(1);


    // Tool Stuff
    //TODO: Give a manual on login, can place it in a drafting table to make like a ponder system, but for lower tier components? upgrade to cpu later?
    public static final RegistryObject<Item> MANUAL = reg.register("manual", () -> new Item(TOOL));
    public static final RegistryObject<Item> METAL_WORKING_ROD = reg.register("metal_working_rod", () -> new Item(TOOL.durability(50))); // #X,#X, -- # = iron nugget, X = stick
    public static final RegistryObject<Item> METAL_WORKING_HAMMER = reg.register("metal_working_hammer", () -> new Item(TOOL.durability(100)));// ##, X, Y -- # = iron ingot, X = metal working rod, Y = stick
    public static final RegistryObject<Item> METAL_WORKING_SAW = reg.register("metal_working_saw", () -> new Item(TOOL.durability(100)));// #X#,YYY -- # = stick, X = iron rod, Y = iron nugget
    public static final RegistryObject<Item> METAL_WORKING_TOOLKIT = reg.register("metal_working_toolkit", () -> new Item(TOOL.durability(600))); // # X, Y ,Z  W -- # = shears, X = hammer, Y = chest, Z = metal working rod, W = metal working saw
    // mining explosives?
    // something to read steam pressure, rf, portal particles, etc. without a menu
    // Should I make like a concrete hose thing, so you can hold a couple of thousand blocks of concrete to make reinforced concrete, and also you can paint it?


    // Minerals
    public static final RegistryObject<Item> GRAPHITE_CHUNK = reg.register("graphite_chunk", () -> new Item(FIRE_RESISTANT));
    public static final RegistryObject<Item> FLUORITE = reg.register("fluorite", () -> new Item(STANDARD)); // mining, or from electrolysis of alumina brine using carbon electrodes
    // coal coke?
    public static final RegistryObject<Item> SLAG = reg.register("slag", () -> new Item(STANDARD)); // electrolysis of red mud using carbon electrodes --- somehow splits into clay and iron?
    // Gold Production --- https://en.wikipedia.org/wiki/Gold#Extraction_and_refining
    // Netherite Production --- grinding > shredded netherite scrap? -- shredded scrap > some kind of refining process... magnetic separation? dissolving?
    // Aluminium Production
    public static final RegistryObject<Item> SODIUM_HYDROXIDE = reg.register("sodium_hydroxide", () -> new Item(STANDARD)); // < electrolysis < 250 mb salt brine
    public static final RegistryObject<Item> ALUMINUM_HYDROXIDE = reg.register("aluminum_hydroxide", () -> new Item(STANDARD)); // 4x < 200 heat mixing < any aluminum ore + sodium hydroxide
    public static final RegistryObject<Item> ALUMINUM_OXIDE = reg.register("aluminum_oxide", () -> new Item(STANDARD)); // .5x (alumina) < furnace < aluminum hydroxide
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
    public static final RegistryObject<Item> PLASTIC_TUBE = reg.register("plastic_tube", () -> new Item(STANDARD)); // extrude plastic sheet
    public static final RegistryObject<Item> RUBBER = reg.register("rubber", () -> new Item(STANDARD)); // catalytic reaction (potassium persulfate (catalyst) + iron salt (catalyst)) < 100 mb butadiene + 50 mb styrene
    public static final RegistryObject<Item> THERMAL_RUBBER = reg.register("thermal_rubber", () -> new Item(STANDARD)); // catalytic reaction (potassium persulfate (catalyst) + iron salt (catalyst)) < 100 mb butadiene + 50 mb styrene + 1 aluminum hydroxide
    // rubber sheet needed for presses
    // Polyester / Nylon fabric ? -- could make backpacks
    // Kerosene lubricant? graphite lubricant?

    // Mold --- maybe change this to be 1 item with NBT data
    public static final RegistryObject<Item> MOLD = reg.register("mold", () -> new Item(FIRE_RESISTANT)); // 2x #XY,#ZY,#XY -- # = steel plate, X = sand, Y = graphite powder, Z = PAN resin
    public static final RegistryObject<Item> MOLD_INGOT = reg.register("mold_ingot", () -> new Item(FIRE_RESISTANT)); // mold + ingot
    public static final RegistryObject<Item> MOLD_SHEET = reg.register("mold_sheet", () -> new Item(FIRE_RESISTANT)); // mold + pressure plate
    public static final RegistryObject<Item> MOLD_BEAM = reg.register("mold_beam", () -> new Item(FIRE_RESISTANT)); // mold + beam (larger than a rod)
    public static final RegistryObject<Item> MOLD_TUBE = reg.register("mold_tube", () -> new Item(FIRE_RESISTANT)); // mold + tube
    public static final RegistryObject<Item> MOLD_GEAR = reg.register("mold_gear", () -> new Item(FIRE_RESISTANT)); // mold + gear
    public static final RegistryObject<Item> MOLD_BLOCK = reg.register("mold_block", () -> new Item(FIRE_RESISTANT)); // mold + block


    // Mechanical Components
    //TODO: upgrade a machine by using a higher tier chassis on it
    public static final RegistryObject<Item> CHASSIS_MK0 = reg.register("chassis_mk0", () -> new Item(STANDARD)); // #X#,#Y# -- # = iron plate, X = frame, Y = battery? capacitor?
    public static final RegistryObject<Item> CHASSIS_MK1 = reg.register("chassis_mk1", () -> new Item(STANDARD)); // #X#,#Y# -- # = steel plate, X = frame, Y = battery
    public static final RegistryObject<Item> CHASSIS_MK2 = reg.register("chassis_mk2", () -> new Item(STANDARD)); // #X#,#Y#,#Z# -- # = plastic, X = frame, Y = battery, Z = circuit
    public static final RegistryObject<Item> CHASSIS_MK3 = reg.register("chassis_mk3", () -> new Item(STANDARD)); // X#X,YYY,ZZZ -- # = frame, X = battery, Y = circuit, Z = titanium plate
    // should I make a chassis assembly unit that can do "large" crafting for the higher tier chassis????

    //TODO: these need durability and different speed/pressure maximums
    public static final RegistryObject<Item> TURBINE_MK0 = reg.register("turbine_mk0", () -> new Item(STANDARD)); // #X#,#Y#,#X# -- # = iron plate, X = iron gear, Y = iron rod
    public static final RegistryObject<Item> TURBINE_MK1 = reg.register("turbine_mk1", () -> new Item(STANDARD)); // #X#,#Y#,#X# -- # = nickel plate, X = steel gear, Y = steel rod
    public static final RegistryObject<Item> TURBINE_MK2 = reg.register("turbine_mk2", () -> new Item(STANDARD)); // #X#,#Y#,#X# -- # = stainless steel plate, X = stainless steel gear, Y = stainless steel rod
    public static final RegistryObject<Item> TURBINE_MK3 = reg.register("turbine_mk3", () -> new Item(STANDARD)); // #X#,#Y#,#X# -- # = chromium-nickel plate, X = stainless steel gear, Y = stainless steel rod
    public static final RegistryObject<Item> TURBINE_MK4 = reg.register("turbine_mk4", () -> new Item(STANDARD)); // #X#,#Y#,#X# -- # = chromium-nickel plate, X = chromium-nickel gear, Y = stainless steel rod

    //TODO: I might need to give these thermal conductivity ratings
    public static final RegistryObject<Item> HEAT_EXCHANGER_MK0 = reg.register("heat_exchanger_mk0", () -> new Item(STANDARD)); // #X,#X,#X -- # = copper plate, X = copper tube
    public static final RegistryObject<Item> HEAT_EXCHANGER_MK1 = reg.register("heat_exchanger_mk1", () -> new Item(STANDARD)); // X#X,X#X,X#X -- # = copper plate, X = cupro-nickel tube
    public static final RegistryObject<Item> HEAT_EXCHANGER_MK2 = reg.register("heat_exchanger_mk2", () -> new Item(STANDARD)); // #X#,YYY,#X# -- # = thermal gasket, X = cupro-nickel tube, Y = heat exchanger 1
    public static final RegistryObject<Item> HEAT_EXCHANGER_MK3 = reg.register("heat_exchanger_mk3", () -> new Item(STANDARD)); // #X#,#X#,#X# -- # = dymalloy, X = heat exchanger 2

    public static final RegistryObject<Item> GEAR_BOX_MK0 = reg.register("gear_box_mk0", () -> new Item(STANDARD)); // #X#,X#,YYY -- # = gear, X = rod, Y = plate
    public static final RegistryObject<Item> GEAR_BOX_MK1 = reg.register("gear_box_mk1", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GEAR_BOX_MK2 = reg.register("gear_box_mk2", () -> new Item(STANDARD));

    public static final RegistryObject<Item> GASKET = reg.register("gasket", () -> new Item(STANDARD)); // X#X,# #,X#X -- # = rubber, X = aluminum plate -- can be done cheaper in a 3 stage press, aluminum, rubber, combiner
    public static final RegistryObject<Item> THERMAL_GASKET = reg.register("thermal_gasket", () -> new Item(STANDARD)); // X#X,# #,X#X -- # = thermal rubber, X = aluminum plate --- can be pressed as above
    public static final RegistryObject<Item> VALVE = reg.register("valve", () -> new Item(STANDARD)); // # , X ,ZYZ -- # = motor, X = ??? rod, Y = gasket, Z = ??? pipe/tube
    public static final RegistryObject<Item> THERMAL_VALVE = reg.register("thermal_valve", () -> new Item(STANDARD)); // # , X ,ZYZ -- # = motor, X = ??? rod, Y = thermal gasket, Z = ??? pipe/tube

    //TODO: generates 60 base heat, -10 heat in cold biomes, and +20 heat in hot biomes.
    public static final RegistryObject<Item> MIRROR_MK0 = reg.register("mirror_mk0", () -> new Item(STANDARD)); // 2x ###,XXX,### -- # = glass pane, X = copper plate
    public static final RegistryObject<Item> MIRROR_MK1 = reg.register("mirror_mk1", () -> new Item(STANDARD)); // 2x ###,XXX,### -- # = glass pane, X = aluminum plate
    public static final RegistryObject<Item> MIRROR_MK2 = reg.register("mirror_mk2", () -> new Item(STANDARD)); // 2x ###,XXX,YZY -- # = glass pane, X = silver plate, Y = rubber, Z = steel plate

    // Advanced Production expansion
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK0 = reg.register("wind_turbine_blade_mk0", () -> new Item(STANDARD)); //   #, #X,#XX -- # = wood, X = wool
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK1 = reg.register("wind_turbine_blade_mk1", () -> new Item(STANDARD)); // titanium and plastic?
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK2 = reg.register("wind_turbine_blade_mk2", () -> new Item(STANDARD)); // engineered wood and fiberglass?
    public static final RegistryObject<Item> WIND_TURBINE_BLADE_MK3 = reg.register("wind_turbine_blade_mk3", () -> new Item(STANDARD)); // engineered wood and carbon fiber


    // Electrical Components
    // should i make cables work like leads to link blocks? too many makes too much client lag?
    public static final RegistryObject<Item> WIRE_MK0 = reg.register("wire_copper", () -> new Item(STANDARD)); //2x -- 3 copper ingot + shears (needs insulation?)
    public static final RegistryObject<Item> WIRE_MK1 = reg.register("wire_gold_tipped", () -> new Item(STANDARD)); // 1 gold ingot, 2 copper ingot + shears (needs insulation?) (or 2 copper wire + gold ingot)
    public static final RegistryObject<Item> WIRE_MK2 = reg.register("wire_fiber", () -> new Item(STANDARD)); //8x -- ###,#X#,### -- # = optical fiber 2, X = Urethane Acrylic Resin
    public static final RegistryObject<Item> WIRE_MK3 = reg.register("wire_nano", () -> new Item(STANDARD)); //5x -- #X#,XXX,#X# -- # = plastic / rubber 2, X = nanotube cluster
    public static final RegistryObject<Item> WIRE_MK4 = reg.register("wire_super", () -> new Item(STANDARD)); // something super conductive

    public static final RegistryObject<Item> OPTICAL_FIBER = reg.register("optical_fiber", () -> new Item(STANDARD)); // 24x only made in an extruder -- 1 glass
    public static final RegistryObject<Item> NANOTUBE_CLUSTER = reg.register("nanotube_cluster", () -> new Item(STANDARD)); // ###,###,### -- # = carbon nanotube

    public static final RegistryObject<Item> SOLENOID_MK0 = reg.register("solenoid_copper", () -> new Item(STANDARD)); // 6 copper wires vertically
    public static final RegistryObject<Item> SOLENOID_MK1 = reg.register("solenoid_gold_tipped", () -> new Item(STANDARD));
    public static final RegistryObject<Item> SOLENOID_MK3 = reg.register("solenoid_nano", () -> new Item(STANDARD));
    public static final RegistryObject<Item> SOLENOID_MK4 = reg.register("solenoid_super", () -> new Item(STANDARD));

    // https://en.wikipedia.org/wiki/Joule_heating#Materials_synthesis,_recovery_and_processing
    // https://en.wikipedia.org/wiki/Resistance_wire
    // https://en.wikipedia.org/wiki/Heating_element
    public static final RegistryObject<Item> HEATING_COIL_MK0 = reg.register("heating_coil_mk0", () -> new Item(STANDARD)); // copper ingot + graphite + copper ingot = 2
    public static final RegistryObject<Item> HEATING_COIL_MK1 = reg.register("heating_coil_mk1", () -> new Item(STANDARD)); // cupronickel
    public static final RegistryObject<Item> HEATING_COIL_MK2 = reg.register("heating_coil_mk2", () -> new Item(STANDARD)); // silicon carbide
    public static final RegistryObject<Item> HEATING_COIL_MK3 = reg.register("heating_coil_mk3", () -> new Item(STANDARD)); // tungsten

    public static final RegistryObject<Item> MOTOR_MK0 = reg.register("motor_mk0", () -> new Item(STANDARD)); //  # ,XYX, #  -- # = iron rod, X = copper coil, Y = redstone
    public static final RegistryObject<Item> MOTOR_MK1 = reg.register("motor_mk1", () -> new Item(STANDARD));
    public static final RegistryObject<Item> MOTOR_MK2 = reg.register("motor_mk2", () -> new Item(STANDARD));
    public static final RegistryObject<Item> MOTOR_MK3 = reg.register("motor_mk3", () -> new Item(STANDARD));

    public static final RegistryObject<Item> ELECTRODES_COPPER = reg.register("electrodes_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ELECTRODES_GOLD = reg.register("electrodes_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ELECTRODES_CARBON = reg.register("electrodes_carbon", () -> new Item(STANDARD));

    public static final RegistryObject<Item> ELECTROMAGNET_COIL_MK0 = reg.register("electromagnet_coil_mk0", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ELECTROMAGNET_COIL_MK1 = reg.register("electromagnet_coil_mk1", () -> new Item(STANDARD));
    public static final RegistryObject<Item> ELECTROMAGNET_COIL_MK2 = reg.register("electromagnet_coil_mk2", () -> new Item(STANDARD));

    // maybe ill make these have their own block like in create?, then they'll need to be block items
    public static final RegistryObject<Item> MIXER = reg.register("mixer", () -> new Item(STANDARD)); // #X#,YZY, W  -- # = copper wiring, X = motor 0, Y = redstone, Z = chassis 1, W = steel ingot
    public static final RegistryObject<Item> ELECTROLYZER = reg.register("electrolyzer", () -> new Item(STANDARD)); // #Y#,#Y#,#Y# -- # = gold wiring, X = redstone, Y = chassis 1


    // Ores
    public static final RegistryObject<Item> RAW_ALUMINUM = reg.register("raw_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> RAW_CHROMIUM = reg.register("raw_chromium", () -> new Item(STANDARD));


    // 30% chance of gravel
    // coal ore > coal
    public static final RegistryObject<Item> CRUSHED_IRON = reg.register("crushed_iron", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_COPPER = reg.register("crushed_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_GOLD = reg.register("crushed_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_LAPIS = reg.register("crushed_lapis", () -> new Item(STANDARD)); // does fortune 2?
    public static final RegistryObject<Item> CRUSHED_REDSTONE = reg.register("crushed_redstone", () -> new Item(STANDARD)); // does fortune 2?
    public static final RegistryObject<Item> CRUSHED_EMERALD = reg.register("crushed_emerald", () -> new Item(STANDARD)); // does not double?
    public static final RegistryObject<Item> CRUSHED_DIAMOND = reg.register("crushed_diamond", () -> new Item(STANDARD)); // does not double?
    public static final RegistryObject<Item> CRUSHED_QUARTZ = reg.register("crushed_quartz", () -> new Item(STANDARD)); // does not double?
    public static final RegistryObject<Item> CRUSHED_NICKEL = reg.register("crushed_nickel", () -> new Item(STANDARD)); // 30% change from crushing iron
    public static final RegistryObject<Item> CRUSHED_SILVER = reg.register("crushed_silver", () -> new Item(STANDARD)); // 20% chance from crushing gold
    public static final RegistryObject<Item> CRUSHED_ALUMINUM = reg.register("crushed_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_CHROMIUM = reg.register("crushed_chromium", () -> new Item(STANDARD));


    // Ground Ores -- 30% chance of sand -- I need to make all of these non-reservable, like aluminum ingot can't be turned back into ground aluminum
    //  Can I still include them in the dusts tag? (probably not)
    //  Do I need to make a separate thing for ground ingots? (probably yes)
    //  Should I instead make the grinder add an NBT tag if its a ore vs an ingot?
    // gravel > sand, blaze rods > blaze powder, glowstone > glowstone dust, crushed redstone > redstone dust
    public static final RegistryObject<Item> GROUND_CHARCOAL = reg.register("ground_charcoal", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GROUND_COAL = reg.register("ground_coal", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GROUND_IRON = reg.register("ground_iron", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GROUND_COPPER = reg.register("ground_copper", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GROUND_GOLD = reg.register("ground_gold", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GROUND_EMERALD = reg.register("ground_emerald", () -> new Item(STANDARD)); // does not double?
    public static final RegistryObject<Item> GROUND_QUARTZ = reg.register("ground_quartz", () -> new Item(STANDARD)); // does not double? maybe allow for amethyst too and call this SiO2?
    public static final RegistryObject<Item> GROUND_GRAPHITE = reg.register("ground_graphite", () -> new Item(STANDARD)); // from graphite chunk -- why? Lubrication? Doesn't work when exposed to air...
    public static final RegistryObject<Item> GROUND_NICKEL = reg.register("ground_nickel", () -> new Item(STANDARD)); // 30% chance from powdering iron
    public static final RegistryObject<Item> GROUND_COBALT = reg.register("ground_cobalt", () -> new Item(STANDARD)); // 5% chance from electrolyzing ground copper in solution (returns the same amount of copper) -- https://en.wikipedia.org/wiki/Cobalt_extraction#Recovery_from_copper-cobalt_sulfide_concentrates
    public static final RegistryObject<Item> GROUND_SILVER = reg.register("ground_silver", () -> new Item(STANDARD)); // 20% chance from crushing gold
    public static final RegistryObject<Item> GROUND_ALUMINUM = reg.register("ground_aluminum", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GROUND_CHROMIUM = reg.register("ground_chromium", () -> new Item(STANDARD));

    // Ingots
    // iron -- co & co2
    // copper -- so2 --- can further refine to get other metals? https://en.wikipedia.org/wiki/Copper_extraction#Electrolysis
    // gold -- co2?
    public static final RegistryObject<Item> INGOT_NICKEL = reg.register("ingot_nickel", () -> new Item(STANDARD)); // co & co2 & so2
    public static final RegistryObject<Item> INGOT_COBALT = reg.register("ingot_cobalt", () -> new Item(STANDARD)); // commonly from copper / nickel electrorefining
    public static final RegistryObject<Item> INGOT_SILVER = reg.register("ingot_silver", () -> new Item(STANDARD)); // 20% chance from crushing gold
    public static final RegistryObject<Item> INGOT_ALUMINUM = reg.register("ingot_aluminum", () -> new Item(STANDARD)); // alloy any aluminium ore with coal or 2x < electrolysis < molten alumina brine
    public static final RegistryObject<Item> INGOT_GALLIUM = reg.register("ingot_gallium", () -> new Item(STANDARD)); // electrolysis of red mud using carbon electrodes
    public static final RegistryObject<Item> INGOT_CHROMIUM = reg.register("ingot_chromium", () -> new Item(STANDARD)); // https://en.wikipedia.org/wiki/Chromium#Production
    // magnesium?
    public static final RegistryObject<Item> INGOT_PLATINUM = reg.register("ingot_platinum", () -> new Item(FIRE_RESISTANT)); //need production process (rarely from copper / nickel electrorefining https://en.wikipedia.org/wiki/Platinum#Occurrence)
    public static final RegistryObject<Item> INGOT_STEEL = reg.register("ingot_steel", () -> new Item(STANDARD)); // 1 iron, 2 coal or 1 petcoke -- add an inefficient way for electric furnaces 2 iron 2 coal for 1 steel -- co & co2 & so2
    public static final RegistryObject<Item> INGOT_STAINLESS_STEEL = reg.register("ingot_stainless_steel", () -> new Item(STANDARD)); // 7:2:1 steel:chromium:nickel
    // silicon steel for magnets (motors/generators)
    public static final RegistryObject<Item> INGOT_CUPRONICKEL = reg.register("ingot_cupronickel", () -> new Item(FIRE_RESISTANT)); // alloy 7:3 copper:nickel
    // https://en.wikipedia.org/wiki/Aluminium%E2%80%93silicon_alloys#Applications
    public static final RegistryObject<Item> INGOT_DYMALLOY = reg.register("ingot_dymalloy", () -> new Item(FIRE_RESISTANT)); // 6:4 synth diamond dust:CuSil (1:4 copper:silver) (deoxygenated atmosphere)


    public static final HashMap<String, Item.Properties> metalsList = new HashMap<>() {{
        put("copper", STANDARD);
        put("iron", STANDARD);
        put("gold", STANDARD);
        put("netherite", FIRE_RESISTANT);
        put("nickel", STANDARD);
        put("cobalt", STANDARD);
        put("aluminum", STANDARD);
        put("gallium", STANDARD);
        put("chromium", STANDARD);
        put("platinum", FIRE_RESISTANT);
        put("steel", STANDARD);
        put("stainless_steel", FIRE_RESISTANT);
        put("cupronickel", FIRE_RESISTANT);
        put("dymalloy", FIRE_RESISTANT);
//        Nuclear
//        put("tungsten", FIRE_RESISTANT);
//        put("niobium", FIRE_RESISTANT);
//        put("beryllium", STANDARD);
//        put("tungsten_carbide", FIRE_RESISTANT);
//        put("lead", STANDARD);
//        Bio & Cyber
//        put("titanium", STANDARD);
//        put("zirconium", FIRE_RESISTANT);
    }};

    // Basic Parts --- maybe I will add a metal working kit to craft these -- also I'll probably change this to make a function create these
    static {
        initParts();
    }

    private static void initParts() {
        for (String metal: metalsList.keySet()) {
            reg.register("plate_" + metal, () -> new Item(metalsList.get(metal))); // rolled ingot || hammer
            reg.register("rod_" + metal, () -> new Item(metalsList.get(metal))); // extruded ingot || saw
            reg.register("tube_" + metal, () -> new Item(metalsList.get(metal))); // rolled plate || rod
            reg.register("gear_" + metal, () -> new Item(metalsList.get(metal))); // 2 extruded ingots || # ,# #, #
            reg.register("sheet_" + metal, () -> new Item(metalsList.get(metal))); // casting
            reg.register("heavy_plate_" + metal, () -> new Item(metalsList.get(metal))); // casting
            reg.register("beam_" + metal, () -> new Item(metalsList.get(metal))); // casting
        }
    }


    // NUCLEAR ENGINEERING & QUANTUM PHYSICS
    public static final RegistryObject<Item> CALCIUM_SULFATE = reg.register("calcium_sulfate", () -> new Item(STANDARD)); // 300 mb Hydrofluoric acid & Calcium Sulfate < 265 heat mixer < fluorite + 100 mb sulfuric acid + 100 mb water
    public static final RegistryObject<Item> SODIUM_TETRAFLUOROBERYLLATE = reg.register("sodium_tetrafluoroberyllate", () -> new Item(STANDARD)); // 3 Sodium Tetrafluoroberyllate & 2 Alumina & 1 Powdered Quartz < alloy < 3 powdered emerald & 400 mb Sodium hexafluorosilicate
    public static final RegistryObject<Item> BERYLLIUM_HYDROXIDE = reg.register("beryllium_hydroxide", () -> new Item(STANDARD)); // Beryllium hydroxide & 1b fluoridated water (4 sodium fluoride) < 150 heat mixer < 1b water + 4 sodium hydroxide + Sodium Tetrafluoroberyllate

    public static final RegistryObject<Item> MOLD_HEAVY_PLATE = reg.register("mold_heavy_plate", () -> new Item(FIRE_RESISTANT)); // mold + heavy plate -- for reactor shielding

    public static final RegistryObject<Item> RAW_TUNGSTEN = reg.register("raw_tungsten", () -> new Item(STANDARD));
    public static final RegistryObject<Item> RAW_LEAD = reg.register("raw_lead", () -> new Item(STANDARD));
    public static final RegistryObject<Item> RAW_URANIUM = reg.register("raw_uranium", () -> new Item(STANDARD));

    public static final RegistryObject<Item> CRUSHED_LEAD = reg.register("crushed_lead", () -> new Item(STANDARD)); // can also give arsenic? silver? bismuth? antimony?

    public static final RegistryObject<Item> GROUND_LEAD = reg.register("ground_lead", () -> new Item(STANDARD)); // can also give arsenic? silver? bismuth? antimony?

    public static final RegistryObject<Item> INGOT_TUNGSTEN = reg.register("ingot_tungsten", () -> new Item(FIRE_RESISTANT)); // need production process ??????
    public static final RegistryObject<Item> INGOT_NIOBIUM = reg.register("ingot_niobium", () -> new Item(FIRE_RESISTANT)); // need production process ???????
    public static final RegistryObject<Item> INGOT_BERYLLIUM = reg.register("ingot_beryllium", () -> new Item(STANDARD)); // beryllium ingot & 100 mb chlorine gas & 100 mb water < electrolysis < 200 mb Beryllium chloride
    // https://en.wikipedia.org/wiki/Magnox_(alloy)
    public static final RegistryObject<Item> TUNGSTEN_CARBIDE = reg.register("tungsten_carbide", () -> new Item(FIRE_RESISTANT)); // alloy tungsten ingot with 2 graphite chunks --- fuel cells need this?
    public static final RegistryObject<Item> INGOT_LEAD = reg.register("ingot_lead", () -> new Item(STANDARD));
    public static final RegistryObject<Item> INGOT_URANIUM = reg.register("ingot_uranium", () -> new Item(STANDARD)); // need production process
    public static final RegistryObject<Item> INGOT_THORIUM = reg.register("ingot_thorium", () -> new Item(FIRE_RESISTANT)); // need production process
    public static final RegistryObject<Item> INGOT_PLUTONIUM = reg.register("ingot_plutonium", () -> new Item(STANDARD));
    // Vanadium steel -- replaces nickel at 2%:10% vanadium:nickel conversion




    // CYBERNETICS & BIOENGINEERING
    public static final RegistryObject<Item> RAW_TITANIUM = reg.register("raw_titanium", () -> new Item(STANDARD));

    public static final RegistryObject<Item> CRUSHED_TITANIUM = reg.register("crushed_titanium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> CRUSHED_ZIRCONIUM = reg.register("crushed_zirconium", () -> new Item(STANDARD));

    public static final RegistryObject<Item> GROUND_TITANIUM = reg.register("ground_titanium", () -> new Item(STANDARD));
    public static final RegistryObject<Item> GROUND_ZIRCONIUM = reg.register("ground_zirconium", () -> new Item(STANDARD));

    public static final RegistryObject<Item> INGOT_TITANIUM = reg.register("ingot_titanium", () -> new Item(STANDARD)); // https://en.wikipedia.org/wiki/Titanium#Production ---- might remove this until the bio expansion
    public static final RegistryObject<Item> INGOT_ZIRCONIUM = reg.register("ingot_zirconium", () -> new Item(FIRE_RESISTANT)); // secondary output from titanium (used for reactor shielding and heat shielding)




    // DEFENSE INITIATIVE
    // https://en.wikipedia.org/wiki/Lockalloy
}