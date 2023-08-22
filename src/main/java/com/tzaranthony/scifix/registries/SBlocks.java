package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.api.properties.ThermalProperties;
import com.tzaranthony.scifix.core.blocks.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public class SBlocks {
    public static final DeferredRegister<Block> reg = DeferredRegister.create(ForgeRegistries.BLOCKS, Scifix.MOD_ID);

    // Properties
    public static final BlockBehaviour.Properties ROCK = BlockBehaviour.Properties.of(Material.STONE).requiresCorrectToolForDrops();
    public static final BlockBehaviour.Properties STONE = ROCK.sound(SoundType.STONE).strength(3.0F, 3.0F);
    public static final BlockBehaviour.Properties ORE = ROCK.sound(SoundType.METAL).strength(4.0F, 4.0F);
    public static final BlockBehaviour.Properties DEEPSLATE = ROCK.color(MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE).strength(4.5F, 3.0F);
    public static final BlockBehaviour.Properties CRYSTAL = BlockBehaviour.Properties.of(Material.AMETHYST).noOcclusion().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F);
    public static final BlockBehaviour.Properties METAL_BASE = BlockBehaviour.Properties.of(Material.METAL).requiresCorrectToolForDrops();
    public static final BlockBehaviour.Properties LIGHT_METAL = METAL_BASE.strength(3.0F, 3.0F).sound(SoundType.COPPER);
    public static final BlockBehaviour.Properties METAL = METAL_BASE.strength(5.0F, 6.0F).sound(SoundType.LANTERN);
    public static final BlockBehaviour.Properties HEAVY_METAL = METAL_BASE.strength(8.0F, 6.0F).sound(SoundType.NETHERITE_BLOCK);
    public static final BlockBehaviour.Properties LIGHT_MACHINE = METAL_BASE.sound(SoundType.COPPER).strength(2.0F, 2.0F);
    public static final BlockBehaviour.Properties MACHINE = METAL_BASE.sound(SoundType.LANTERN).strength(5.0F, 5.0F);
    public static final BlockBehaviour.Properties ADV_MACHINE = METAL_BASE.sound(SoundType.NETHERITE_BLOCK).strength(5.0F, 5.0F);
    public static final BlockBehaviour.Properties HEAVY_MACHINE = METAL_BASE.sound(SoundType.ANVIL).strength(8.0F, 6.0F);
    public static final BlockBehaviour.Properties GLASS = BlockBehaviour.Properties.of(Material.GLASS).sound(SoundType.GLASS).noOcclusion()
            .isValidSpawn(SBlocks::never).isRedstoneConductor(SBlocks::never).isSuffocating(SBlocks::never).isViewBlocking(SBlocks::never);
    public static final BlockBehaviour.Properties PAPER = BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD);
    public static final BlockBehaviour.Properties PLASTIC = BlockBehaviour.Properties.of(Material.SHULKER_SHELL).sound(SoundType.STONE).strength(1.5F, 4.0F);

    private static Boolean never(BlockState p_50779_, BlockGetter p_50780_, BlockPos p_50781_, EntityType<?> p_50782_) {
        return (boolean)false;
    }
    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    // Minerals --- maybe i'll add an option for a total overhaul of ore generation or at least for my ores
    public static final RegistryObject<Block> GRAPHITE = registerBlockAndFireProofItem("graphite", () -> new Block(ROCK.sound(SoundType.BASALT).strength(0.8F))); // drops 4 graphite chunks, no fortune, like clay
    public static final RegistryObject<Block> FLUORITE_CLUSTER = registerBlockAndItem("fluorite_cluster", () -> new Block(CRYSTAL));
    // Monazite to give thorium and neodymium and lanthanum?
    // Ores
    //TODO: generates in large formations, generates more frequently in hot biomes, especially badlands and jungles
    public static final RegistryObject<Block> ALUMINUM_ORE = registerBlockAndItem("aluminium_ore", () -> new Block(STONE)); // uncommon, but massive veins/blobs, near the surface
    public static final RegistryObject<Block> DEEPSLATE_ALUMINUM_ORE = registerBlockAndItem("deepslate_aluminium_ore", () -> new Block(DEEPSLATE)); // ????
    //TODO: found in thin striations deep underground
    public static final RegistryObject<Block> CHROMIUM_ORE = registerBlockAndItem("chromium_ore", () -> new Block(STONE)); //
    public static final RegistryObject<Block> DEEPSLATE_CHROMIUM_ORE = registerBlockAndItem("deepslate_chromium_ore", () -> new Block(DEEPSLATE)); //


    // Processing Machines!
    // Basic Production
    public static final RegistryObject<Block> ORE_CRUSHER_MK0 = registerBlockAndItem("ore_crusher_mk0", () -> new OreCrusherBlock(HEAVY_MACHINE, 0)); // #X#,YZY,W W -- # = copper wiring, X = hopper, Y = piston, Z = chassis 0, W = iron plate
    public static final RegistryObject<Block> ORE_CRUSHER_MK1 = registerBlockAndItem("ore_crusher_mk1", () -> new OreCrusherBlock(HEAVY_MACHINE, 1)); // chassis 0 > chassis 1, hopper > crusher 1, iron > steel plate
    public static final RegistryObject<Block> ORE_CRUSHER_MK2 = registerBlockAndItem("ore_crusher_mk2", () -> new OreCrusherBlock(HEAVY_MACHINE, 2)); // hardened steel plate
    public static final RegistryObject<Block> ORE_CRUSHER_MK3 = registerBlockAndItem("ore_crusher_mk3", () -> new OreCrusherBlock(HEAVY_MACHINE, 4)); // tungsten plate

    public static final RegistryObject<Block> ORE_GRINDER_MK0 = registerBlockAndItem("ore_grinder_mk0", () -> new OreGrinderBlock(HEAVY_MACHINE, 0)); // #X#,YZY,WVW -- # = steel plate, X = hopper, Y = steel gear, Z = chassis 1, W = motor, V = gearbox 1
    public static final RegistryObject<Block> ORE_GRINDER_MK1 = registerBlockAndItem("ore_grinder_mk1", () -> new OreGrinderBlock(HEAVY_MACHINE, 2));
    public static final RegistryObject<Block> ORE_GRINDER_MK2 = registerBlockAndItem("ore_grinder_mk2", () -> new OreGrinderBlock(HEAVY_MACHINE, 4));

    public static final RegistryObject<Block> ELECTRIC_FURNACE_MK0 = registerBlockAndItem("electric_furnace_mk0", () -> new ElectricFurnaceBlock(MACHINE, 0)); // #X#,YZY,WWW -- # = heating coil, X = cable, Y = redstone, Z = chassis 0, W = graphite
    public static final RegistryObject<Block> ELECTRIC_FURNACE_MK1 = registerBlockAndItem("electric_furnace_mk1", () -> new ElectricFurnaceBlock(MACHINE, 1));
    public static final RegistryObject<Block> ELECTRIC_FURNACE_MK2 = registerBlockAndItem("electric_furnace_mk2", () -> new ElectricFurnaceBlock(MACHINE, 2));
    public static final RegistryObject<Block> ELECTRIC_FURNACE_MK3 = registerBlockAndItem("electric_furnace_mk3", () -> new ElectricFurnaceBlock(MACHINE, 3));
    //TODO: holds liquids (idk how much. 16k mb?), size upgrades
    public static final RegistryObject<Block> VAT_MK0 = registerBlockAndItem("vat_mk0", () -> new Block(MACHINE)); // #,X -- # = chassis 0, X = cauldron
    public static final RegistryObject<Block> VAT_MK1 = registerBlockAndItem("vat_mk1", () -> new Block(MACHINE));
    public static final RegistryObject<Block> VAT_MK2 = registerBlockAndItem("vat_mk2", () -> new Block(MACHINE));
    //TODO: accepts multiple liquids and solids to output a liquid brine or some other mixture. Sometimes will require heat provided from some source (config for heat tolerance?)
    public static final RegistryObject<Block> MIXING_VAT_MK0 = registerBlockAndItem("mixing_vat_mk0", () -> new Block(MACHINE)); // use a mixer on a vat --- needs to hold enough to fill at least 1 cast, probably several
    public static final RegistryObject<Block> MIXING_VAT_MK1 = registerBlockAndItem("mixing_vat_mk1", () -> new Block(MACHINE));
    public static final RegistryObject<Block> MIXING_VAT_MK2 = registerBlockAndItem("mixing_vat_mk2", () -> new Block(MACHINE));
    //TODO: accepts a liquid and returns multiple solids. Place a condenser above to collect gases
    public static final RegistryObject<Block> ELECTROLYTIC_VAT_MK0 = registerBlockAndItem("electrolytic_vat_mk0", () -> new Block(MACHINE)); // use an electrolysis apparatus on a vat
    public static final RegistryObject<Block> ELECTROLYTIC_VAT_MK1 = registerBlockAndItem("electrolytic_vat_mk1", () -> new Block(MACHINE));
    public static final RegistryObject<Block> ELECTROLYTIC_VAT_MK2 = registerBlockAndItem("electrolytic_vat_mk2", () -> new Block(MACHINE));

    // Intermediate Production --- these currently can't be sped up, should they be able to be sped up?
    //TODO: makes plates or tubes or cables or rods from ingots given a specified mold (automatically matches conveyor direction? must be placed above a conveyor?)
    public static final RegistryObject<Block> MILLING_MACHINE = registerBlockAndItem("milling_machine", () -> new Block(ADV_MACHINE)); // #X#,YZY,WWW -- # = aluminum ingot, X = electric furnace, Y = piston, Z = chassis 1, W = steel ingot
    // Fluid injector to inject fluids into buckets and what not...
    //TODO: compresses gasses and liquids
    public static final RegistryObject<Block> COMPRESSOR = registerBlockAndItem("compressor", () -> new Block(ADV_MACHINE)); //
    //TODO: evaporates composite liquids into gases to be collected by condensers. Optionally, water can be added to crack the oils
    public static final RegistryObject<Block> FRACTIONATOR = registerBlockAndItem("fractinator", () -> new Block(ADV_MACHINE)); // lots of steel
    //TODO: can react various liquids, gases, and solids over a catalyst to produce solids, and liquids (gases must be captured with condensors) (require pressue and heat setting?)
    public static final RegistryObject<Block> CATALYTIC_REACTOR = registerBlockAndItem("catalytic_reactor", () -> new Block(ADV_MACHINE));

    // Wood Production
    // sawmill?
    // wood chips -- chipwood, plywood, particle board -- mulch? Fertilizer?
    // engineered wood planks and plastic (glue) (doesn't burn?)
    // wood pulp, paper, and cardboard
    // Flour and bread?

    // Advanced Production
    // Chip production
    //      silicon refining: https://en.wikipedia.org/wiki/Silicon
    //      CDV for advance circuit and material manufacturing, maybe needs a redstone blueprint design? -- semi-conductor manufacturing https://en.wikipedia.org/wiki/Semiconductor
    //      Lithograph?
    // https://en.wikipedia.org/wiki/Diamond_anvil_cell (useful if we're making lonsdaleite or synth diamond)?? https://en.wikipedia.org/wiki/Synthetic_diamond#Manufacturing_technologies
    // Laser / water etching for tiny components? like micro gears or redstone or intermediate circuits? (CNC - requires computers to work?)
    // Assembly line autocrafting -- makes welding / hammering noises? needs a CPU to control then an assembler for each item in the recipe (hold 1 type each), assemblers must be in-line, make a custom recipe, outputs from the last assembler (deconstruct a crafting recipe to be linear then go through an assembly line, ignore blanks) --- (provide a way for other large crafting recipes to be converted?)
    // oil/ore exploration computers: https://en.wikipedia.org/wiki/Hydrocarbon_exploration#Exploration_methods (also scans for ores??, need some way to display a map if its a total read, otherwise just display targeted ores

    //TODO: Foundry: multiblock -- default organized by density?, will alloy liquids automatically. Make a tag for valid furnace bricks. include graphite and maybe regular bricks? Need heat ratings?
    //      Furnace Heat -- Must get to 1500+ heat per base block to work, heat can be provided to the bottom or sides (can inject preheated gases to increase heat?)
    //      Special Furnaces:
    //          Electric arc: fastest in large batch bursts, but needs to replace electrodes over time, electrodes must be cooled --- need something to plug the electrodes into
    //          Electromagnetic induction coils: fastest for consistent use, has a warm up time, coils must be cooled, needs semi-conductive bricks (quartz and graphite)
    //TODO: used to input stuff to melt automatically (glows when furnace is active)
    public static final RegistryObject<Block> FURNACE_PORT = registerBlockAndFireProofItem("furnace_port", () -> new Block(MACHINE)); // #,# #,### -- graphite chunk (or brick)
    //TODO: used to input liquids or gasses for alloying
    public static final RegistryObject<Block> FURNACE_INJECTOR = registerBlockAndFireProofItem("furnace_injector", () -> new Block(MACHINE)); // ###,YXY,### -- # = graphite chunk (or brick), X = valve, Y = pipe
    //TODO: outputs molten metal into containers from the furnace when given a redstone signal, can be more than one, but they must be in the bottom layer
    public static final RegistryObject<Block> FURNACE_TAP = registerBlockAndFireProofItem("furnace_tap", () -> new Block(MACHINE)); // ###,#X#,# # -- # = graphite chunk (or brick), X = valve
    //TODO: for catching and outputting slag and other solids
    public static final RegistryObject<Block> SLAG_PAN = registerBlockAndFireProofItem("slag_pan", () -> new Block(MACHINE)); // ###,XXX -- # = graphite chunk (or brick), X = iron ingot
    //TODO: used to store molten liquids without them cooling
    public static final RegistryObject<Block> INSULATED_VAT = registerBlockAndFireProofItem("insulated_vat", () -> new Block(MACHINE)); // ###,#X#,# # -- # = graphite chunk (or brick), X = vat
    //TODO: stores furnace data, can be upgraded to a furnace controller
    public static final RegistryObject<Block> FURNACE_CORE = registerBlockAndFireProofItem("furnace_core", () -> new Block(MACHINE));
    //TODO: allows viewing -- requires computers
    public static final RegistryObject<Block> FURNACE_CONTROLLER = registerBlockAndFireProofItem("furnace_controller", () -> new Block(MACHINE));
    //// Should I replace this with sheet metal that is pressed into shapes? -- https://youtu.be/GDzBE6vz5r0 --- maybe we make a new "heavy" class for plates and gears that require molds and regular ones can be pressed
    //// dye press produces 4 per 1/2 second
    //TODO: shaped mold x8, a block that can cast up to 8 stacks each of a given shape at a time from liquid metal, can be stacked and fills from bottom to top
    public static final RegistryObject<Block> MULTI_DIE_CAST = registerBlockAndFireProofItem("multi_die_cast", () -> new Block(MACHINE)); // 8 molds (4 if block mold)


    // Electricity Generation
    // Heat Generators (hot, do not touch (or stand in the solar beam)) -- need a tag or interface or both for these
    public static final RegistryObject<Block> SOLID_FUEL_HEATER_MK0 = registerBlockAndItem("solid_fuel_heater_mk0", () -> new SolidFuelHeaterBlock(MACHINE, ThermalProperties.TIER_0_EXCHANGER)); // #X#,#Y#,ZZZ -- # = copper heat exchanger, X = furnace, Y = chassis 0, Z = graphite
    public static final RegistryObject<Block> SOLID_FUEL_HEATER_MK1 = registerBlockAndItem("solid_fuel_heater_mk1", () -> new SolidFuelHeaterBlock(MACHINE, ThermalProperties.TIER_1_EXCHANGER));
    public static final RegistryObject<Block> SOLID_FUEL_HEATER_MK2 = registerBlockAndItem("solid_fuel_heater_mk2", () -> new SolidFuelHeaterBlock(MACHINE, ThermalProperties.TIER_2_EXCHANGER));
    public static final RegistryObject<Block> SOLID_FUEL_HEATER_MK3 = registerBlockAndItem("solid_fuel_heater_mk3", () -> new SolidFuelHeaterBlock(MACHINE, ThermalProperties.TIER_3_EXCHANGER));

    public static final RegistryObject<Block> CONVECTION_HEATER_MK0 = registerBlockAndItem("convection_heater_mk0", () -> new ConvectionHeaterBlock(MACHINE, ThermalProperties.TIER_0_EXCHANGER)); // ###,XYX,ZZZ -- # = copper heat exchanger, X = iron plate, Y = chassis 0, Z = graphite
    public static final RegistryObject<Block> CONVECTION_HEATER_MK1 = registerBlockAndItem("convection_heater_mk1", () -> new ConvectionHeaterBlock(MACHINE, ThermalProperties.TIER_1_EXCHANGER));
    public static final RegistryObject<Block> CONVECTION_HEATER_MK2 = registerBlockAndItem("convection_heater_mk2", () -> new ConvectionHeaterBlock(MACHINE, ThermalProperties.TIER_2_EXCHANGER));
    public static final RegistryObject<Block> CONVECTION_HEATER_MK3 = registerBlockAndItem("convection_heater_mk3", () -> new ConvectionHeaterBlock(MACHINE, ThermalProperties.TIER_3_EXCHANGER));
    //TODO: Different sizes have different tank sizes
    //      Hydrogen: generates 2200 base heat per 100 mb, lasts .5x
    //      Methane: generates 1900 base heat per 100 mb, lasts .5x
    //      Kerosene: generates 2000 base heat per 100 mb, lasts .5x
    //      Light Fuel: generates 2100 base heat per 100 mb, lasts 1x
    //      Heavy Fuel: generates 2100 base heat per 100 mb, lasts 2x
    public static final RegistryObject<Block> LIQUID_FUEL_HEATER_MK0 = registerBlockAndItem("liquid_fuel_heater_mk0", () -> new LiquidFuelHeaterBlock(MACHINE, ThermalProperties.TIER_0_EXCHANGER)); // ###,XYX,ZWZ -- # = cupronickel plate, X = tank, Y = solid_fuel_heater, Z = steel plate, W = chassis 1
    public static final RegistryObject<Block> LIQUID_FUEL_HEATER_MK1 = registerBlockAndItem("liquid_fuel_heater_mk1", () -> new LiquidFuelHeaterBlock(MACHINE, ThermalProperties.TIER_1_EXCHANGER));
    public static final RegistryObject<Block> LIQUID_FUEL_HEATER_MK2 = registerBlockAndItem("liquid_fuel_heater_mk2", () -> new LiquidFuelHeaterBlock(MACHINE, ThermalProperties.TIER_2_EXCHANGER));
    public static final RegistryObject<Block> LIQUID_FUEL_HEATER_MK3 = registerBlockAndItem("liquid_fuel_heater_mk3", () -> new LiquidFuelHeaterBlock(MACHINE, ThermalProperties.TIER_3_EXCHANGER));

    public static final RegistryObject<Block> RESISTANCE_HEATER_MK0 = registerBlockAndItem("resistance_heater_mk0", () -> new ResistanceHeaterBlock(MACHINE, ThermalProperties.TIER_0_EXCHANGER)); // #X#,XYX,ZZZ -- # = cupronickel plate 1, X = copper wiring, Y = chassis 1, Z = aluminum plate
    public static final RegistryObject<Block> RESISTANCE_HEATER_MK1 = registerBlockAndItem("resistance_heater_mk1", () -> new ResistanceHeaterBlock(MACHINE, ThermalProperties.TIER_1_EXCHANGER));
    public static final RegistryObject<Block> RESISTANCE_HEATER_MK2 = registerBlockAndItem("resistance_heater_mk2", () -> new ResistanceHeaterBlock(MACHINE, ThermalProperties.TIER_2_EXCHANGER));
    public static final RegistryObject<Block> RESISTANCE_HEATER_MK3 = registerBlockAndItem("resistance_heater_mk3", () -> new ResistanceHeaterBlock(MACHINE, ThermalProperties.TIER_3_EXCHANGER));
    //TODO: Upgradable - chassis, mirror. Targets generate heat at linked block with a heat beam. Beyond 6 blocks away from the target, heat will dissipate at 5 heat per block. Must have sky access, and must not be raining. Only works during the day.
    public static final RegistryObject<Block> SOLAR_REFLECTOR_MK0 = registerBlockAndItem("solar_reflector_mk0", () -> new Block(MACHINE)); // ###,XXX, Y  -- # = mirror, X = cable, Y = chassis
    public static final RegistryObject<Block> SOLAR_REFLECTOR_MK1 = registerBlockAndItem("solar_reflector_mk1", () -> new Block(MACHINE));
    public static final RegistryObject<Block> SOLAR_REFLECTOR_MK2 = registerBlockAndItem("solar_reflector_mk2", () -> new Block(MACHINE));
    //TODO: much more efficient tha regular electric heater but only works on furnaces. Generates 320 heat. You can touch this one (if you're not cybernetic)
    public static final RegistryObject<Block> ELECTROMAGNET_MK0 = registerBlockAndItem("electromagnet_mk0", () -> new Block(MACHINE)); // #X#,#Y#,#X# -- # = aluminum plate, X = nanoperm?, permalloy (4 Ni : 1 Fe),?, Y = chassis 2
    public static final RegistryObject<Block> ELECTROMAGNET_MK1 = registerBlockAndItem("electromagnet_mk1", () -> new Block(MACHINE)); // Niobium–titanium coil
    public static final RegistryObject<Block> ELECTROMAGNET_MK2 = registerBlockAndItem("electromagnet_mk2", () -> new Block(MACHINE)); // ????

    ///// geothermal?????
    // Steam Generators
    //TODO: Requires water and heat to generate steam, will explode if it gets too hot. Fires/Campfires below generate 12 heat per tick, soul fire/campfire generates 24 heat per tick
    //      mk0: holds 4k mb water
    //      mk1: holds 8k mb water
    //      mk2: holds 16k mb water
    //      mk3: holds 32k mb water
    // Formula for pressure -- 0.61078 * e^((17.27 * x) / (x + 237.3)) = kPa (maybe make this function smoother) -- can only output steam from the top
    // I kinda wanna make these a multiblock
    public static final RegistryObject<Block> STEAM_BOILER_MK0 = registerBlockAndItem("steam_boiler_mk0", () -> new Block(MACHINE)); // WXW,YZY,### -- # = heat exchanger, X = iron trapdoor, Y = redstone, Z = chassis 0, W = cauldron
    public static final RegistryObject<Block> STEAM_BOILER_MK1 = registerBlockAndItem("steam_boiler_mk1", () -> new Block(MACHINE)); // #X#,YZY,### -- # = cupronickel heat exchanger, X = steel trapdoor, Y = redstone, Z = chassis 1
    public static final RegistryObject<Block> STEAM_BOILER_MK2 = registerBlockAndItem("steam_boiler_mk2", () -> new Block(MACHINE));
    public static final RegistryObject<Block> STEAM_BOILER_MK3 = registerBlockAndItem("steam_boiler_mk3", () -> new Block(MACHINE));
    // Turbine Housing
    //TODO: Generates electricity when it has a turbine inserted and is provided steam / CO2 (steam must enter and exit from opposite sides). Should I add a turbine weight variable for power remaining kPa
    //      mk0: max +16 MRf / tick -- 900 - 400 kPa > extracts 400 - 100 kPa ----- 0.04 MRf / kPa t
    //      mk1: max +150 MRf (150 MRf) / tick -- 3200 - 1200 kPa > extracts 2500 - 1200 kPa ----- 0.06 MRf / kPa t
    //      mk2: max +300 MRf (450 MRf) / tick -- 6000 - 3200 kPa > extracts 2800 - 1500 kPa ----- 0.1 MRf / kPa t
    //      mk3: max +600 MRf (1.05 GRf) / tick -- 10000 - 6000 kPa > extracts 4000 - 2800 kPa ----- 0.15 kRf / kPa t
    //      mk4: max +950 MRf (2.0 GRf) / tick -- 16000 - 10000 kPa > extracts 6000 - 4000 kPa ----- 0.159 kRf / kPa t
    //      Gas: max +900 MRf (+2.9 GRf) / tick -- 28000 - 16000 kPa > extracts 18000 - 13000 kPa ----- 500 kRf / kPa t
    // Steam must be super heated (same pressure, higher temperature (+10 C min)) ---- turbine 1 should output saturated steam
    public static final RegistryObject<Block> TURBINE_HOUSING_MK0 = registerBlockAndItem("turbine_housing_mk0", () -> new Block(MACHINE)); // #X#,YZY,#X# -- # = iron plate, X = motor, Y = redstone, Z = chassis 0
    //TODO: these need a superheater -- https://en.wikipedia.org/wiki/Superheated_steam -- https://en.wikipedia.org/wiki/Superheater
    public static final RegistryObject<Block> TURBINE_HOUSING_MK1 = registerBlockAndItem("turbine_housing_mk1", () -> new Block(MACHINE)); // low pressure, multiblock 3x3x2
    public static final RegistryObject<Block> TURBINE_HOUSING_MK2 = registerBlockAndItem("turbine_housing_mk2", () -> new Block(MACHINE)); // intermediate pressure, multiblock? 2x2x2? maybe still make it 3x3x2, but make it shrink the corners to stairs
    public static final RegistryObject<Block> TURBINE_HOUSING_MK3 = registerBlockAndItem("turbine_housing_mk3", () -> new Block(MACHINE)); // high pressure, single block
    public static final RegistryObject<Block> TURBINE_HOUSING_MK4 = registerBlockAndItem("turbine_housing_mk4", () -> new Block(MACHINE)); // extreme pressure, single block
    // TODO: fuel turbine generator (only works with compressed nat. gas or hydrogen to produce CO2 or H2O)
    // Steam Condensers
    //TODO: turns gas back into liquid, output will be any side not acting as an input. Without this, the water from a steam generator must be replaced. Can be supplied with snow, ice, or heat exchangers to increase efficiency
    // can be stacked on top of each other since each will only create a fixed amount of condensate per tick. More faster in cold biomes. Excess steam will be vented. Can add trays to catch condensed fluids instead of passing them down
    // can capture other gas outputs like from fractional distillation or smelter gasses
    public static final RegistryObject<Block> CONDENSER_MK0 = registerBlockAndItem("condenser_mk0", () -> new Block(MACHINE)); // #X#,#Y#,#X# -- # = copper heat exchanger, X = iron trapdoor, Y = chassis 0
    public static final RegistryObject<Block> CONDENSER_MK1 = registerBlockAndItem("condenser_mk1", () -> new Block(MACHINE)); // #X#,#Y#,#X# -- # = cupronickel heat exchanger, X = steel trapdoor, Y = chassis 1
    public static final RegistryObject<Block> CONDENSER_MK2 = registerBlockAndItem("condenser_mk2", () -> new Block(MACHINE)); // #X#,#Y#,#X# -- # = diamond heat exchanger, X = aluminum trapdoor, Y = chassis 2
    public static final RegistryObject<Block> CONDENSER_MK3 = registerBlockAndItem("condenser_mk3", () -> new Block(MACHINE));

    // Direct Electricity Generation Machines
    // Wind
    //TODO: these generate electricity based on spin speed, max spin speed depends on the height, biome, motor resistance, and turbine weight (can be placed vertically or horizontally)
    public static final RegistryObject<Block> WIND_TURBINE_BODY_MK0 = registerBlockAndItem("wind_turbine_body_mk0", () -> new Block(MACHINE)); // ###,XXZ,#Y# -- # = steel plate, X = gear box, Y = chassis 1, Z = motor 1
    public static final RegistryObject<Block> WIND_TURBINE_BODY_MK1 = registerBlockAndItem("wind_turbine_body_mk1", () -> new Block(MACHINE));
    public static final RegistryObject<Block> WIND_TURBINE_BODY_MK2 = registerBlockAndItem("wind_turbine_body_mk2", () -> new Block(MACHINE));
    public static final RegistryObject<Block> WIND_TURBINE_BODY_MK3 = registerBlockAndItem("wind_turbine_body_mk3", () -> new Block(MACHINE));
    //TODO: adds a weight that reduces speed --- shapes? normal (loud)? vertical (moderate)? toroidal (quiet)?
    //      mk0: +2 MRF
    //      mk1: +4 MRF
    //      mk2: +7 MRF
    //      mk3: +18 MRF
    public static final RegistryObject<Block> WIND_TURBINE_MK0 = registerBlockAndItem("wind_turbine_mk0", () -> new Block(MACHINE)); //  # ,YXY,# # -- # = blade 0, X = chassis 1, Y = iron rod
    public static final RegistryObject<Block> WIND_TURBINE_MK1 = registerBlockAndItem("wind_turbine_mk1", () -> new Block(MACHINE));
    public static final RegistryObject<Block> WIND_TURBINE_MK2 = registerBlockAndItem("wind_turbine_mk2", () -> new Block(MACHINE));
    public static final RegistryObject<Block> WIND_TURBINE_MK3 = registerBlockAndItem("wind_turbine_mk3", () -> new Block(MACHINE));
    // Solar
    // https://en.wikipedia.org/wiki/Solar_panel
    // https://youtu.be/OYzCq8YWAHw


    // Moving Stuff
    // Fluid transport & storage
    //TODO: allows steam to freely pass through to a higher block without loss of heat
    public static final RegistryObject<Block> STEAM_DUCT = registerBlockAndItem("steam_duct", () -> new Block(MACHINE));
    //TODO: some place to store gases
    //TODO: pipes, different held volume and transfer rate
    public static final RegistryObject<Block> PIPE_MK0 = registerBlockAndItem("pipe_mk0", () -> new Block(LIGHT_MACHINE)); // ### -- # = copper tube
    public static final RegistryObject<Block> PIPE_MK1 = registerBlockAndItem("pipe_mk1", () -> new Block(LIGHT_MACHINE)); // steel
    public static final RegistryObject<Block> PIPE_MK2 = registerBlockAndItem("pipe_mk2", () -> new Block(LIGHT_MACHINE)); // extruded plastic or 6 plastic sheets?
    public static final RegistryObject<Block> PIPE_MK3 = registerBlockAndItem("pipe_mk3", () -> new Block(LIGHT_MACHINE)); // idk, something high pressure
    public static final RegistryObject<Block> PIPE_MK4 = registerBlockAndItem("pipe_mk4", () -> new Block(LIGHT_MACHINE)); // idk, something even higher pressure
    public static final RegistryObject<Block> PIPE_MK5 = registerBlockAndItem("pipe_mk5", () -> new Block(LIGHT_MACHINE)); // idk, something even greater higher pressure
    //TODO: pressure valve pipe - releases gases when they reach a specific pressure
    //TODO: fluid tanks?
    //TODO: pump, different tiers? slurry injectors for fracking?

    // Item transport & storage
    //TODO: tiers increase max speed and capacity? -- use a frame on a conveyor to be able to add modifications to it like extraction or filters. shift click to remove
    public static final RegistryObject<Block> CONVEYOR_BELT_MK0 = registerBlockAndItem("conveyor_belt_mk0", () -> new Block(LIGHT_MACHINE)); // 2x ###,XYX -- # = paper? kelp? idk..., X = iron rod, Y = motor 0
    public static final RegistryObject<Block> CONVEYOR_BELT_MK1 = registerBlockAndItem("conveyor_belt_mk1", () -> new Block(LIGHT_MACHINE)); // 2x ###,XYX -- # = rubber, X = aluminum plate, X = aluminum rod, Y = motor 1
    public static final RegistryObject<Block> CONVEYOR_BELT_MK2 = registerBlockAndItem("conveyor_belt_mk2", () -> new Block(LIGHT_MACHINE)); // 2x ###,XXX,YZY -- # = thermal rubber, X = aluminum plate, Y = titanium rod, z = motor 2
    //TODO: cardboard boxes if i'm taking it from the other mod...
    public static final RegistryObject<Block> CARDBOARD_BOX = registerBlockAndItem("cardboard_box", () -> new Block(PAPER));
    public static final RegistryObject<Block> SHIPPING_PALLET = registerBlockAndItem("shipping_pallet", () -> new Block(PAPER));
    //TODO: Plastic storage containers (fluids too?): plastic sheets + other stuff
    public static final RegistryObject<Block> PLASTIC_BOX = registerBlockAndItem("plastic_box", () -> new Block(PLASTIC));

    // Energy transport & storage
    //TODO: create cable capacity limits -- need cables that can handle millions of rf/t. I think ima make these like satisfacotry where they need connectors and hang -- # cables^2 >= dist^2 -- create render like create rails
    public static final RegistryObject<Block> CABLE_MK0 = reg.register("cable_mk0", () -> new Block(LIGHT_MACHINE)); // 6 wire mk 0 horizontally
    public static final RegistryObject<Block> CABLE_MK0_5 = reg.register("cable_mk0_5", () -> new Block(LIGHT_MACHINE)); // ###,XXX,### -- # = plastic / rubber, X = cable 0
    public static final RegistryObject<Block> CABLE_MK1 = reg.register("cable_mk1", () -> new Block(LIGHT_MACHINE)); // 6 wires mk 1 horizontally
    public static final RegistryObject<Block> CABLE_MK1_5 = reg.register("cable_mk1_5", () -> new Block(LIGHT_MACHINE)); // ###,XXX,### -- # = plastic / rubber, X = cable 1
    public static final RegistryObject<Block> CABLE_MK2 = reg.register("cable_mk2", () -> new Block(LIGHT_MACHINE)); // 9 wire 2
    public static final RegistryObject<Block> CABLE_MK3 = reg.register("cable_mk3", () -> new Block(LIGHT_MACHINE)); // 9 wire 3
    public static final RegistryObject<Block> CABLE_MK4 = reg.register("cable_mk4", () -> new Block(LIGHT_MACHINE)); // wires + coolant
    //TODO: batteries -- slow input, slow output, much larger overall storage --- lead-acid -> lithium ion & nickle hydrogen (platinum)?
    public static final RegistryObject<Block> BATTERY_BANK_MK0 = registerBlockAndItem("battery_bank_mk0", () -> new Block(LIGHT_MACHINE)); // ###, X ,### -- # = battery, X = frame
    public static final RegistryObject<Block> BATTERY_BANK_MK1 = registerBlockAndItem("battery_bank_mk1", () -> new Block(LIGHT_MACHINE));
    public static final RegistryObject<Block> BATTERY_BANK_MK2 = registerBlockAndItem("battery_bank_mk2", () -> new Block(LIGHT_MACHINE));
    public static final RegistryObject<Block> BATTERY_BANK_MK3 = registerBlockAndItem("battery_bank_mk3", () -> new Block(LIGHT_MACHINE));
    //TODO: capacitor banks -- fast input, fast output, has a decay time
    public static final RegistryObject<Block> CAPACITOR_BANK_MK0 = registerBlockAndItem("capacitor_bank_mk0", () -> new Block(LIGHT_MACHINE)); // ###,#X#,### -- # = capacitor, X = frame
    public static final RegistryObject<Block> CAPACITOR_BANK_MK1 = registerBlockAndItem("capacitor_bank_mk1", () -> new Block(LIGHT_MACHINE));
    public static final RegistryObject<Block> CAPACITOR_BANK_MK2 = registerBlockAndItem("capacitor_bank_mk2", () -> new Block(LIGHT_MACHINE));
    public static final RegistryObject<Block> CAPACITOR_BANK_MK3 = registerBlockAndItem("capacitor_bank_mk3", () -> new Block(LIGHT_MACHINE));
    // wireless charging


    // Redstone
    // higher push limit pistons?
    // specialized skulk sensors for when this is in 1.19? specific player actions, mob actions, block actions


    // Enchanting
    // adding ???
    // removing ???
    // storing XP ???


    // misc
    // programmable mining / harvesting drones (more programmable if you have CC Tweaked?)


    // Parts
    public static final RegistryObject<Block> FRAME_MK0 = registerBlockAndItem("frame_mk0", () -> new Block(LIGHT_MACHINE)); // #X#,X X,#X# -- # = iron plate, X = iron rod
    public static final RegistryObject<Block> FRAME_MK1 = registerBlockAndItem("frame_mk1", () -> new Block(LIGHT_MACHINE)); // #X#,X X,#X# -- # = steel plate, X = iron rod
    public static final RegistryObject<Block> FRAME_MK2 = registerBlockAndItem("frame_mk2", () -> new Block(LIGHT_MACHINE)); // #X#,X X,#X# -- # = plastic, X = aluminum rod
    public static final RegistryObject<Block> FRAME_MK3 = registerBlockAndItem("frame_mk3", () -> new Block(LIGHT_MACHINE)); // #X#,X X,#X# -- # = plastic, X = titanium rod
    public static final RegistryObject<Block> LAMINATED_GLASS = registerBlockAndItem("laminated_glass", () -> new Block(GLASS.strength(3.0F, 400.0F))); // #X#,X#X,#X# -- # = glass pane, X = plastic
    public static final RegistryObject<Block> ALUMINUM_GLASS = registerBlockAndItem("aluminum_glass", () -> new Block(GLASS.strength(5.0F, 400.0F))); // alloy < 2 aluminum ingots + 2 alumina + 500 mb nitrogen
    public static final RegistryObject<Block> LAMINATED_ALUMINUM_GLASS = registerBlockAndItem("laminated_aluminum_glass", () -> new Block(GLASS.strength(10.0F, 1200.0F))); // #X#,X#X,#X# -- # = aluminum glass pane, X = plastic


    // building bocks
    //TODO: emits particles most will be based on the block/item provided: bubbles, portal, reverse portal, spore blossom, lava pops... Probably won't need rf
    public static final RegistryObject<Block> AMBIENT_PARTICLE_GENERATOR = registerBlockAndItem("ambient_particle_generator", () -> new Block(STONE));
    public static final RegistryObject<Block> ASPHALT = registerBlockAndItem("asphalt", () -> new Block(STONE)); //2x - #X,X# -- # = bitumen, X = gravel or X#X,X#X,X#X X = slag
    //TODO: freezes stuff, hurts on contact
    public static final RegistryObject<Block> DRY_ICE = registerBlockAndItem("dry_ice", () -> new Block(ROCK.sound(SoundType.CALCITE))); // carbon dioxide in a chilled pressure chamber
    // https://en.wikipedia.org/wiki/Refractory
    public static final RegistryObject<Block> POLISHED_GRAPHITE = registerBlockAndFireProofItem("polished_graphite", () -> new Block(ROCK.sound(SoundType.BASALT))); // stone cut graphtie || 4 graphite in table -- probably other textures too
    public static final RegistryObject<Block> GRAPHITE_BRICKS = registerBlockAndFireProofItem("graphite_bricks", () -> new Block(MACHINE)); // ##,## -- graphite chunk --- stone cutting graphite
    // Boron Nitride bricks (and heat exchangers)?
    // LED Stuff? Lanterns? Strips? Floodlights?
    // Shelving for items / Shelving for books
    // replace fluorescent light with LED?
    // Stained-glass of lead + stained-glass = new stained-glass with glazed terracotta patterns
    // display stand? like a pedestal?


    // Compacted Blocks
    public static final RegistryObject<Block> BLOCK_FLUORITE = registerBlockAndItem("block_fluorite", () -> new Block(CRYSTAL));
    public static final RegistryObject<Block> BLOCK_RAW_LEAD = registerBlockAndItem("block_raw_lead", () -> new Block(ORE));
    public static final RegistryObject<Block> BLOCK_RAW_ALUMINUM = registerBlockAndItem("block_raw_aluminum", () -> new Block(ORE));

    public static final RegistryObject<Block> BLOCK_NICKEL = registerBlockAndItem("block_nickel", () -> new Block(METAL));
    public static final RegistryObject<Block> BLOCK_COBALT = registerBlockAndItem("block_cobalt", () -> new Block(METAL));
    public static final RegistryObject<Block> BLOCK_ALUMINUM = registerBlockAndItem("block_aluminum", () -> new Block(LIGHT_METAL));
    public static final RegistryObject<Block> BLOCK_GALLIUM = registerBlockAndItem("block_gallium", () -> new Block(LIGHT_METAL));
    public static final RegistryObject<Block> BLOCK_PLATINUM = registerBlockAndFireProofItem("block_platinum", () -> new Block(METAL));
    public static final RegistryObject<Block> BLOCK_STEEL = registerBlockAndItem("block_steel", () -> new Block(HEAVY_METAL));
    public static final RegistryObject<Block> BLOCK_CUPRONICKEL = registerBlockAndFireProofItem("block_cupronickel", () -> new Block(METAL));





    // NUCLEAR ENGINEERING & QUANTUM PHYSICS
    //TODO: generates in standard clusters deep underground
    public static final RegistryObject<Block> LEAD_ORE = registerBlockAndItem("lead_ore", () -> new Block(STONE));
    public static final RegistryObject<Block> DEEPSLATE_LEAD_ORE = registerBlockAndItem("deepslate_lead_ore", () -> new Block(DEEPSLATE));
    //TODO: https://en.wikipedia.org/wiki/Carnotite (also provides vanadium for high strength steel)
    public static final RegistryObject<Block> URANIUM_ORE = registerBlockAndItem("uranium_ore", () -> new Block(STONE));
    public static final RegistryObject<Block> DEEPSLATE_URANIUM_ORE = registerBlockAndItem("deepslate_uranium_ore", () -> new Block(DEEPSLATE));

    //TODO: singularity storage configured to store an infinite amount of up to 4 items, 1 fluid, or energy -- can set specific output sides for each, no NBT allowed, requires energy to extract items/fluids
    public static final RegistryObject<Block> SINGULARITY_STORAGE = registerBlockAndFireProofItem("singularity_storage", () -> new Block(HEAVY_MACHINE));
    // something to keep the singularity in place
    // antimatter capacitor
    // probably need heat exchanging pressurized pipes? Definitely needs coolant systems

    // fission reactors:
    //      Management Computer: sets and monitors control rod insertion, fuel status, coolant temperatures, power generation, and waste generation
    //      Control Rods: boron? boron carbide? cadmium? silver? titanium diboride? zirconium diboride? -- https://en.wikipedia.org/wiki/Control_rod -- this can definitely be a design decision / puzzle
    //      Fuels: https://en.wikipedia.org/wiki/Nuclear_reactor#Nuclear_fuel_cycle
    //          Fuel "rods" for solid fuel reactors -- ~44 rod clusters (225 rods each) are used to store pellets -- https://en.wikipedia.org/wiki/Nuclear_fuel
    //          Uranium: may require enrichment from U238 > U235 or from Th232 > U233, measured in pct 233 or pct 235
    //          Plutonium: needs breeder to create it -- measured in pct Pu240
    //          Thorium: needs plutonium (other high neutron helper materials) in the fuel mix to start the reactor https://en.wikipedia.org/wiki/Thorium-based_nuclear_power
    //          Spent/Recycled Uranium:
    //              Plutonium MOX:
    //              Thorium MOX:
    //      Moderators: https://en.wikipedia.org/wiki/Nuclear_reactor#Reactor_types
    //          Graphite: core must be constructed out of nuclear grade graphite (99.9999% pure) https://en.wikipedia.org/wiki/Nuclear_graphite, U235:U238 <1% (does not require enrichment), Pu239:Pu240 <19%
    //          Light water: core must be flooded with water, requires enrichment, U235:U238 3%<5%, Pu239:Pu240 7%<19%, create deuterium water
    //          Heavy water: core must be flooded with heavy water, U235:U238 <1% (does not require enrichment), Pu239:Pu240 <19%, create tritium water ---- how to make heavy water?
    //      Coolants:
    //          Boiling Water: 25% efficiency, the moderator water turns into steam for power generation, more complicated cooling system and a larger area than pressurized water
    //          Pressurized Water: 30% efficiency, the moderator water is also used to transfer heat, and is under pressure to prevent boiling
    //          Supercritial Water: 35% efficiency, directly generates power using the supercritical water, similar set up to pressurized water but runs at higher temperature, does not need pumps
    //          Gas (CO2 or He): 40% efficiency, can only use graphite moderators, CO2 may product carbon monoxide -- https://en.wikipedia.org/wiki/Gas-cooled_reactor
    //          Molten Lead: 40% efficiency, can only use graphite moderators, can use electromagnetic pumps or passively cycling cool via air exposure, reduces nuclear waste, requires a high temperature turbine,
    //              requires more insulated and structurally sound pipes, needs better corrosion protection
    //          Molten Salt (NaF + fuel): 40% efficiency, can only use graphite moderators, mixes molten salt with the reactor fuel, can passively cool via air exposure, requires a high temperature turbine,
    //              requires more insulated pipes, needs better corrosion protection, the graphite core will be deteriorate over time
    //      Reactor Types:
    //          Thermal:
    //              Solid Fuel: reactor must be shut down to add new fuel
    //                  Uranium (see moderator): lots of radioactive waste but low grade
    //                  Plutonium (see moderator): lots of radioactive waste but low grade
    //              Liquid Salt Fuel: can refuel while operating. Needs graphite moderators. Melting plug can be added to the cycling or heat exchange line in case of meltdown
    //                  U235:U238 20%<50%: creates plutonium
    //                  (U233:U232 <95% && (U235:U238 30%<50% || Pu239:Pu240 7%<19%) ??%<??% & Th232 ??%<??%: requires an initializer (plutonium?). less radioactive waste but higher grade, so it needs more shielding
    //                      Creates Pa233 which must be cycled out until it decays to U233 which should be re-added to the fuel mix (or skip and just do U233:U232 <98%?)
    //                      https://en.wikipedia.org/wiki/Liquid_fluoride_thorium_reactor
    //          Fast: requires significant enrichment, cannot use light water as moderator or coolant and graphite moderators
    //              Solid Fuel: reactor must be shut down to add new fuel
    //                  U235:U238 20%<50%: Plutonium Breeding, less radioactive waste but higher grade
    //                  Plutonium fast???
    //                  Thorium fast???
    //              Liquid Salt Fuel: can refuel while operating. Melting plug can be added to the cycling or heat exchange line in case of meltdown
    //                  U235:U238 20%<50%: creates plutonium
    //                  Plutonium fast???
    //                  Thorium fast????
    // fusion reactors: need a way to vent out reacted gasses --- more than 25m rf/t
    //      Fusion control computer system (quantum classical combination? quantum more efficient?)
    //      Shielding and what not will be damaged over time
    //      Fuels -- have different running temperatures required:
    //          tritium & deuterium --- requires 60m degrees min, 800m is the cutoff for energy gains --- produces a lot of heat a medium amount of electromagnetic power --- results in He4 & 100% radioactive waste.
    //              A Li6 (maybe beryllium alloy) shielding blanket can be added to use less tritium
    //          deuterium & deuterium --- requires 100m degrees min --- produces a large amount of heat and a small amount of electromagnetic power --- results in He3 & 100% radioactive waste
    //          deuterium & helium 3 --- requires 300m degrees min --- produces a small amount of heat and a moderate amount of electromagnetic power --- results in He4 & 25% radioactive waste
    //          hydrogen & boron? --- requires 1000m degrees min --- produces extreme heat power --- results in 3x He4 & 0% radioactive waste
    //      Reactor Types:
    //          Fusion-Fission combo? make the walls out of thorium for heat generation?
    //          Inertial confinement: burst power, burst power consumption, medium space requirement, must manufacture fuel capsules (plastic encased fuel mix), from heat, piezoelectric pressure, and/or induction
    //              Laser: neodymium crystals > KDP crustal > focusing beam --- make it a puzzle to determine what focusing crystals to use in what order --- different light sources (singlet o2)?
    //              Magnetic: electromagnets -- more expensive
    //          Magnetic confinement: electromagnetic reactors use 10x power for main vs aux electromagnets. Need tungsten divertor vents at bottom and top? Maybe carbon or beryllium tiles. Must be washed with helium after shutdown
    //              Tokamak: sustained power, sustained power consumption, large space requirement, can generate from heat. Need extra injectors to maintain the plasma
    //              Stellarator: twisty tokamak, doesn't require injectors to maintain plasma
    //              Linear: middle ground, burst power consumption, small space requirement, can generate magnetically or from heat. Need injectors on both ends
    // Coolant system liquid > heat exchanger > liquid, also needed for magnets or lasers in fusion ---- fusion https://www.youtube.com/watch?v=ZHmHBMaS6Sw

    // Particle Accelerators
    // Cyclotron -- antimatter (needs magnetic storage container, magnetic field and hull integrity can decay, maybe need an AI chip to fight against magnetic flux), singularities, or regular matter creation
    // Fusion Powered Accelerator https://youtu.be/UtfUeip4vyA

    // Portal Tech
    // Portal weavers? use pure liquid ender to make portal frame then needs to be supplied PP
    //TODO: rift generator - can generate rifts to any location/dimension set by a dialing device (can be used remotely?):
    //      Size determines the amount of RF used to generate and sustain the rift
    //      Opening a rift costs 5x the sustain rate
    //      Distance determines how many portal particles harvesters are needed to harvest particles from nether portals. Every 10,000 block x,z area needs 1 portal particle provider
    //      To sustain a change dimensions, 6 (12) portal particle harvesters must be used?
    public static final RegistryObject<Block> RIFT_GENERATOR = registerBlockAndItem("rift_generator", () -> new Block(MACHINE));
    public static final RegistryObject<Block> RIFT = registerBlockAndItem("rift", () -> new Block(MACHINE)); // triple check thrownenderpearl
    //TODO: NEED SOMETHING TO TRANSPORT THE PARTICLES
    //TODO: used to store portal particles and provide them to a rift generator or another portal particle provider
    public static final RegistryObject<Block> PP_PROVIDER = registerBlockAndItem("pp_provider", () -> new Block(MACHINE));
    //TODO: use rf to collect portal particles from a nether (low particles) or end portal (high particles)
    public static final RegistryObject<Block> PP_HARVESTER = registerBlockAndItem("pp_harvester", () -> new Block(MACHINE));
    //TODO: consumes rf and ender pearls or chorus fruit to generate portal particles (idk how this should be balanced vs the harvester, probably medium generation but higher rf consumption)
    public static final RegistryObject<Block> PP_SYNTHESIZER = registerBlockAndItem("pp_synthesizer", () -> new Block(MACHINE));

    // Nanotechnology -- needs config
    //      matter disassembler > storage somewhere... > request > replicator?
    //      teleporters
    //      nano armor
    //          camo
    //          immortality
    //          teleportation
    //          general more power -- mining speed, attack strength
    //          flight
    //          item magnet
    //      defensive nanobots?
    //      nanobot quarry
    //      camo block

    // core drill -- pulls ores out of the planet's core, must have access to bedrock below y -60 (maybe lava on bedrock)
    // ender pearl / chorus fruits > quantessence or some quantum thing > quantum computers, black holes and other particle accelerator stuff ?????

    //TODO: wither proof
    public static final RegistryObject<Block> BLOCK_RAW_TUNGSTEN = registerBlockAndItem("block_raw_tungsten", () -> new Block(ORE)); // ?
    public static final RegistryObject<Block> BLOCK_RAW_NIOBIUM = registerBlockAndItem("block_raw_niobium", () -> new Block(ORE)); // ?
    public static final RegistryObject<Block> BLOCK_RAW_URANIUM = registerBlockAndItem("block_raw_uranium", () -> new Block(ORE));
    public static final RegistryObject<Block> BLOCK_RAW_THORIUM = registerBlockAndItem("block_raw_thorium", () -> new Block(ORE));

    //TODO: wither proof
    public static final RegistryObject<Block> BLOCK_TUNGSTEN = registerBlockAndFireProofItem("block_tungsten", () -> new Block(HEAVY_METAL));
    public static final RegistryObject<Block> BLOCK_NIOBIUM = registerBlockAndFireProofItem("block_niobium", () -> new Block(METAL));
    public static final RegistryObject<Block> BLOCK_BERYLLIUM = registerBlockAndItem("block_beryllium", () -> new Block(METAL));
    public static final RegistryObject<Block> BLOCK_LEAD = registerBlockAndItem("block_lead", () -> new Block(HEAVY_METAL));
    public static final RegistryObject<Block> BLOCK_URANIUM = registerBlockAndItem("block_uranium", () -> new Block(HEAVY_METAL));
    public static final RegistryObject<Block> BLOCK_THORIUM = registerBlockAndFireProofItem("block_thorium", () -> new Block(METAL));
    public static final RegistryObject<Block> BLOCK_PLUTONIUM = registerBlockAndItem("block_plutonium", () -> new Block(METAL));
    public static final RegistryObject<Block> BLOCK_TUNGSTEN_CARBIDE = registerBlockAndFireProofItem("block_tungsten_carbide", () -> new Block(METAL));





    // CYBERNETICS & BIOENGINEERING
    //TODO: where to find this? -- https://en.wikipedia.org/wiki/Rutile || https://en.wikipedia.org/wiki/Ilmenite
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlockAndItem("titanium_ore", () -> new Block(STONE));
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlockAndItem("deepslate_titanium_ore", () -> new Block(DEEPSLATE));

    // Bioengineering -- needs config
    // hydroponics -- efficient plant growing? rail auto harvesters?
    // add a block to fossils that can be used for cloning?
    // gene sequencer: sequences gene samples from mobs or fossils (will need a special block, 1 for overworld, 1 for nether -- preserved bone marrow) or plants or fungi
    // need dry ice or liquid nitrogen to store biological / gene samples
    // gene lab -- used to mix different aspects of mob genomes together (needs gene editing proteins CRISPR?)
    // Should I literally just add carbonite to freeze mobs like in star wars LOL
    // Cloning chamber: (some new drop from spawners required to speed it up or make advanced ones?)
    //      Fungi cell -- cloning fungi
    //      Plant cell -- cloning GM plants
    //      Mob cell -- cloning GM mobs
    // Gene Therapy: used to modify living mobs and plants and fungi. Can also be used to modify players
    // bioengineer organs?
    // Mycelium bricks -- grow a tall mushroom next to an ingot mold (can be checkerboard pattern)
    // Bio engineer plants to produce mob drops
    // Something to clean up radiation

    // Cybernetics -- needs config
    // Something to make the cybernetic components -- mostly titanium based
    //      feet: fall damage mitigation
    //      calves: speed
    //      thighs: step height, jump strength
    //      fore arm: strength/mining speed, wrist rockets
    //      upper arm: reach distance, strength/mining speed
    //      body: power plant, bio-energy converter (one for food to rf, one for rf to food), jetpack?, implant backpack, storage system access, ender chest access, shield generator
    //      head: extra xp, portable enchanting, entity eye mod, thermal, night vision
    // Automated surgical chamber: used to replace organs with cybernetic parts

    public static final RegistryObject<Block> BLOCK_RAW_TITANIUM = registerBlockAndItem("block_raw_titanium", () -> new Block(ORE)); // ?

    public static final RegistryObject<Block> BLOCK_TITANIUM = registerBlockAndItem("block_titanium", () -> new Block(HEAVY_METAL));
    public static final RegistryObject<Block> BLOCK_ZIRCONIUM = registerBlockAndFireProofItem("block_zirconium", () -> new Block(METAL));
    //zirconium (zirconia) bricks?





    // DEFENSE INITIATIVE
    // barbed wire
    // cool arrows?
    // missile - requires a nose cone, payload (thermobaric, nuclear, antimatter), body, fuel (sold or liquid), booster, guidance computer, heat shielding (if icbm), detention sequence (impact, timer, altitude / distance)
    // defensive drones





    // DECORATION ADDITIONS
    // merge deco mod as a config? if I do, i'm making slabs, stairs, walls, fences, etc. a generic block the can render any texture like the architecture things
    public static final HashMap<String, Boolean> metalsList = new HashMap<>() {{
        put("copper", false);
        put("gold", false);
        put("netherite", true);
        put("nickel", false);
        put("cobalt", false);
        put("aluminum", false);
        put("gallium", false);
        put("chromium", true);
        put("platinum", true);
        put("steel", false);
        put("cupronickel", true);
        put("tungsten", true);
        put("niobium", true);
        put("beryllium", false);
        put("tungsten_carbide", true);
        put("lead", false);
        put("titanium", false);
        put("zirconium", true);
    }};

    public static final ArrayList<String> colorList = new ArrayList<>() {{
        add("blue");
        add("black");
        add("brown");
        add("cyan");
        add("gray");
        add("green");
        add("light_blue");
        add("light_gray");
        add("lime");
        add("magenta");
        add("orange");
        add("pink");
        add("purple");
        add("red");
        add("white");
        add("yellow");
    }};

    public static final RegistryObject<Block> SIDING_COPPER = registerBlockAndItem("siding_copper", () -> new Block(HEAVY_METAL));
    public static final RegistryObject<Block> SIDING_NICKEL = registerBlockAndItem("siding_nickel", () -> new Block(HEAVY_METAL));
    public static final RegistryObject<Block> SIDING_ALUMINUM = registerBlockAndItem("siding_aluminum", () -> new Block(HEAVY_METAL));
//    static {
//        initTrapdoors();
//        initCompactIronPlates();
//        initCompactSteelPlates();
//    }

//    private static void initTrapdoors() {
//        for (String metal: metalsList.keySet()) {
//            registerBlockAndItem("trapdoor_" + metal, () -> new Block(HEAVY_METAL), metalsList.get(metal), Rarity.COMMON);
//        }
//    }
//
//    private static void initCompactIronPlates() {
//        final RegistryObject<Block> SIDING_IRON = registerBlockAndItem("siding_iron", () -> new Block(HEAVY_METAL));
//        for (String metal: colorList) {
//            registerBlockAndItem("siding_iron_" + metal, () -> new Block(HEAVY_METAL));
//        }
//    }
//
//    private static void initCompactSteelPlates() {
//        final RegistryObject<Block> SIDING_STEEL = registerBlockAndItem("siding_steel", () -> new Block(HEAVY_METAL));
//        for (String metal: colorList) {
//            registerBlockAndItem("siding_steel_" + metal, () -> new Block(HEAVY_METAL));
//        }
//    }








    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block) {
        return registerBlockAndItem(name, block, false, Rarity.COMMON);
    }

    public static RegistryObject<Block> registerBlockAndFireProofItem(String name, Supplier<Block> block) {
        return registerBlockAndItem(name, block, true, Rarity.COMMON);
    }

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, Rarity rare) {
        return registerBlockAndItem(name, block, false, rare);
    }

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, boolean fireResistant, Rarity rare) {
        return registerBlockAndItem(name, block, Scifix.TAB, fireResistant, rare);
    }

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, CreativeModeTab tab, boolean fireResistant, Rarity rare) {
        RegistryObject<Block> blockObj = reg.register(name, block);

        Item.Properties props = new Item.Properties().tab(tab).rarity(rare);
        if (fireResistant) {
            props = props.fireResistant();
        }

        Item.Properties finalProps = props;
        SItems.reg.register(name, () -> new BlockItem(blockObj.get(), finalProps));
        return blockObj;
    }
}