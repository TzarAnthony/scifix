package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SBlocks {
    public static final DeferredRegister<Block> reg = DeferredRegister.create(ForgeRegistries.BLOCKS, Scifix.MOD_ID);

    // Properties
    public static final BlockBehaviour.Properties ROCK = BlockBehaviour.Properties.of(Material.STONE);
    public static final BlockBehaviour.Properties STONE_ORE = BlockBehaviour.Properties.of(Material.STONE).sound(SoundType.STONE).strength(3.0F, 3.0F);
    public static final BlockBehaviour.Properties DEEPSLATE_ORE = BlockBehaviour.Properties.of(Material.STONE).color(MaterialColor.DEEPSLATE).sound(SoundType.DEEPSLATE).strength(4.5F, 3.0F);
    public static final BlockBehaviour.Properties CRYSTAL = BlockBehaviour.Properties.of(Material.AMETHYST).noOcclusion().sound(SoundType.AMETHYST_CLUSTER).strength(1.5F);
    public static final BlockBehaviour.Properties MACHINE = BlockBehaviour.Properties.of(Material.METAL).sound(SoundType.ANVIL).strength(5.0F, 5.0F);

    // Minerals
    public static final RegistryObject<Block> GRAPHITE = reg.register("graphite", () -> new Block(ROCK.sound(SoundType.BASALT).strength(0.8F))); // 4 graphite chunks
    public static final RegistryObject<Block> FLUORITE_CLUSTER = reg.register("fluorite", () -> new Block(CRYSTAL));
    // Ores
    public static final RegistryObject<Block> ALUMINUM_ORE = registerBlockAndItem("aluminium_ore", () -> new Block(STONE_ORE));
    public static final RegistryObject<Block> DEEPSLATE_ALUMINUM_ORE = registerBlockAndItem("aluminium_ore", () -> new Block(DEEPSLATE_ORE));
    public static final RegistryObject<Block> TITANIUM_ORE = registerBlockAndItem("titanium_ore", () -> new Block(STONE_ORE));
    public static final RegistryObject<Block> DEEPSLATE_TITANIUM_ORE = registerBlockAndItem("titanium_ore", () -> new Block(DEEPSLATE_ORE));

    // Processing Machines //TODO: need some way to upgrade these??? Maybe we go straight to the multibock smelter??? heat resistors & flint/crushing stuff as a component???
    //TODO: works like a regular furnace but on electricity
    public static final RegistryObject<Block> ELECTRIC_FURNACE = registerBlockAndItem("electric_furnace", () -> new Block(MACHINE)); // #X#,YZY,#X# -- # = copper wiring, X = heating resistor 0, Y = redstone, Z = chassis 0
    //TODO: alloys elements using electricity
    public static final RegistryObject<Block> ELECTRIC_BLAST_FURNACE = registerBlockAndItem("electric_blast_furnace", () -> new Block(MACHINE)); // #X#,YZY -- # = blast furnace, X = redstone, Y = graphite, Z = chassis 0
    //TODO: turns raw ore into 2 crushed ores, can output secondary crushed ores (in world crafting?)
    public static final RegistryObject<Block> ORE_CRUSHER = registerBlockAndItem("ore_crusher", () -> new Block(MACHINE)); // #X#,YZY,WWW -- # = flint, X = piston, Y = copper wiring, Z = chassis 0, W = deepslate
    //TODO: turns crushed ore into 2 powdered ore, can output secondary powdered ores (in world crafting?)
    public static final RegistryObject<Block> ROTARY_GRINDER = registerBlockAndItem("rotary_grinder", () -> new Block(MACHINE)); // ###,XYX,ZWZ -- # = flint, X = redstone, Y = chassis 1, Z = copper wiring, W = motor 0
    //TODO: holds liquids (idk how much. 16k mb?)
    public static final RegistryObject<Block> VAT = registerBlockAndItem("vat", () -> new Block(MACHINE)); // #,X -- # = chassis 0, X = cauldron
    //TODO: accepts multiple liquids and solids to output a liquid brine or some other mixture. Sometimes will require heat provided from some source....
    public static final RegistryObject<Block> MIXING_VAT = registerBlockAndItem("mixing_vat", () -> new Block(MACHINE)); // use a mixer on a vat
    //TODO: accepts a liquid and returns multiple solids. Place a condensator above to collect gases
    public static final RegistryObject<Block> ELECTROLYTIC_VAT = registerBlockAndItem("electrolytic_vat", () -> new Block(MACHINE)); // use an electrolysis apparatus on a vat
    //TODO: makes plates from ingots (in world crafting?)
    public static final RegistryObject<Block> PRESS = registerBlockAndItem("press", () -> new Block(MACHINE));


    // Electricity Generation via Steam Machines
    // Heat Generators (hot, do not touch (or stand in the solar beam))
    //TODO: generates 80 heat spread out to adjacent steam generators
    public static final RegistryObject<Block> SOLID_FUEL_HEATER = registerBlockAndItem("solid_fuel_heater", () -> new Block(MACHINE)); // #X#,#Y#,ZZZ -- # = copper ingot, X = furnace, Y = chassis 0, Z = stone
    //TODO: generates 40 heat spread out to adjacent steam generators. Only accepts Blaze and Lava buckets (make tag to add other mods). Fuel last 4x.
    public static final RegistryObject<Block> INDUCTION_HEATER = registerBlockAndItem("induction_heater", () -> new Block(MACHINE)); // #X#,XYX,ZZZ -- # = heating resistor 0, X = copper ingot, Y = chassis 0, Z = graphite
    //TODO: generates 40 base heat, will generate heat at whatever block its linked to face. Beyond 5 blocks away from the target, heat will dissipate at 5 heat per block. Generates -10 heat in cold biomes, and +20 heat in hot biomes.
    // Must have sky access, and must not be raining. Only works during the day
    public static final RegistryObject<Block> SOLAR_REFLECTOR = registerBlockAndItem("solar_reflector", () -> new Block(MACHINE)); // ###,XXX, Y  -- # = glass pane, X = aluminum plate, Y = chassis 1
    //TODO: very inefficient for making steam, requires electricity to generate 20 heat
    public static final RegistryObject<Block> ELECTRIC_HEATER = registerBlockAndItem("electric_heater", () -> new Block(MACHINE)); // ###,XYX,ZZZ -- # = heating resistor 1, X = copper wiring, Y = chassis 1, Z = aluminum ingot
    ////// nuclear reactors? steam pressurized pipes
    // Steam Generators
    //TODO: Requires water and heat to generate steam, will explode if it gets too hot. Fires/Campfires below generate 20 heat, soul fire/campfire generates 40 heat, other heaters see heat generator section. Hot biomes give a base of 5 heat
    //TODO: min 20, max 80 heat, holds 1k mb
    public static final RegistryObject<Block> STEAM_GENERATOR_MK0 = registerBlockAndItem("steam_generator_mk0", () -> new Block(MACHINE)); // #X#,#Y#,### -- # = copper ingot, X = iron trapdoor, Y = chassis 0
    //TODO: min 30, max 120 heat, holds 4k mb
    public static final RegistryObject<Block> STEAM_GENERATOR_MK1 = registerBlockAndItem("steam_generator_mk1", () -> new Block(MACHINE)); // #X#,#Y#,### -- # = cupro-nickel ingot, X = steel trapdoor, Y = chassis 1
    //TODO: min 40, max 160 heat, holds 8k mb
    public static final RegistryObject<Block> STEAM_GENERATOR_MK2 = registerBlockAndItem("steam_generator_mk2", () -> new Block(MACHINE)); // #X#,#Y#,### -- # = diamond, X = aluminum trapdoor, Y = chassis 2
    // Turbine Housing
    //TODO: Generates electricity when it has a turbine inserted and is provided steam. Different levels allow for different stack heights of turbines to generate power or just allow more turbines to be inserted?
    // power output depends on the amount of steam generated. Must be directly above a steam generator
    public static final RegistryObject<Block> TURBINE_HOUSING_MK0 = registerBlockAndItem("turbine_housing_mk0", () -> new Block(MACHINE)); // #X#,YZY,#W# -- # = iron ingot, X = motor 0, Y = copper wiring, Z = chassis 0, W = redstone
    // Steam Condensators
    //TODO: placed above a steam generator to capture steam and turn it back into water. Without this, the water in a steam generator must be replaced. Can be supplied with snow, ice, or heat exchangers to increase efficiency
    // can be stacked on top of each other since each will only create a fixed amount of condensate per tick. More faster in cold biomes. Excess steam will be vented.
    public static final RegistryObject<Block> CONDENSATOR_MK0 = registerBlockAndItem("condensator_mk0", () -> new Block(MACHINE)); // ###,#Y#,#X# -- # = copper ingot, X = iron trapdoor, Y = chassis 0
    public static final RegistryObject<Block> CONDENSATOR_MK1 = registerBlockAndItem("condensator_mk1", () -> new Block(MACHINE)); // ###,#Y#,#X# -- # = cupro-nickel ingot, X = steel trapdoor, Y = chassis 1
    public static final RegistryObject<Block> CONDENSATOR_MK2 = registerBlockAndItem("condensator_mk2", () -> new Block(MACHINE)); // ###,#Y#,#X# -- # = diamond, X = aluminum trapdoor, Y = chassis 2


    // Direct Electricity Generation Machines
    // Wind
    //TODO: these generate electricity based on spin speed, max spin speed depends on the height, biome, motor resistance, and turbine weight
    public static final RegistryObject<Block> WIND_TURBINE_BODY_MK0 = reg.register("wind_turbine_body_mk0", () -> new Block(MACHINE)); // ###,XYZ,### -- # = iron ingot, X = copper wiring, Y = chassis 1, Z = motor 1
    public static final RegistryObject<Block> WIND_TURBINE_BODY_MK1 = reg.register("wind_turbine_body_mk1", () -> new Block(MACHINE));
    public static final RegistryObject<Block> WIND_TURBINE_BODY_MK2 = reg.register("wind_turbine_body_mk2", () -> new Block(MACHINE));
    public static final RegistryObject<Block> WIND_TURBINE_BODY_MK3 = reg.register("wind_turbine_body_mk3", () -> new Block(MACHINE));
    //TODO: adds a weight that reduces speed
    public static final RegistryObject<Block> WIND_TURBINE_MK0 = reg.register("wind_turbine_mk0", () -> new Block(MACHINE)); //  # , X ,# # -- # = blade 0, X = chassis 1
    public static final RegistryObject<Block> WIND_TURBINE_MK1 = reg.register("wind_turbine_mk1", () -> new Block(MACHINE));
    public static final RegistryObject<Block> WIND_TURBINE_MK2 = reg.register("wind_turbine_mk2", () -> new Block(MACHINE));
    public static final RegistryObject<Block> WIND_TURBINE_MK3 = reg.register("wind_turbine_mk3", () -> new Block(MACHINE));
    //Solar

    
    
    
    
    

    // building bocks



    
    
    
    

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block) {
        return registerBlockAndItem(name, block, false, Rarity.COMMON);
    }

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, Rarity rare) {
        return registerBlockAndItem(name, block, false, rare);
    }

    public static RegistryObject<Block> registerBlockAndItem(String name, Supplier<Block> block, boolean fireResistant, Rarity rare) {
        RegistryObject<Block> blockObj = reg.register(name, block);

        Item.Properties props = new Item.Properties().tab(Scifix.TAB).rarity(rare);
        if (fireResistant) {
            props = props.fireResistant();
        }

        Item.Properties finalProps = props;
        SItems.reg.register(name, () -> new BlockItem(blockObj.get(), finalProps));
        return blockObj;
    }
}