package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.api.helpers.FluidRegistryHelper;
import com.tzaranthony.scifix.api.properties.FluidProperties;
import com.tzaranthony.scifix.api.properties.IFluidProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class SFluids {
    public static final ResourceLocation MOLTEN_STILL = new ResourceLocation(Scifix.MOD_ID, "block/molten_still");
    public static final ResourceLocation MOLTEN_FLOWING = new ResourceLocation(Scifix.MOD_ID, "block/molten_flow");
    public static final ResourceLocation MOLTEN_OVERLAY = new ResourceLocation(Scifix.MOD_ID, "block/molten_overlay");
    public static final DeferredRegister<Fluid> reg = DeferredRegister.create(ForgeRegistries.FLUIDS, Scifix.MOD_ID);

    //TODO: oil -- found in largest pools under the ocean or under deserts, can be found in surface pools: --- config for simple refining?
    //      Crude has different densities based on biome?
    //      Hydrogen sulfide (1%) & ammonia (3%) & purified crude (96%) < crude + hydrogen at high temperature
    //      3% Nat Gas (>25 heat) & 18% (>60 heat) light naphtha & 16% (>140 heat) heavy naphtha & 11% (>232 heat) Kerosene & 21% (>296 heat) light fuel oil & 22% (>373 heat) heavy fuel oil & 9% (>467 heat) residual oil < fractional distillation < purified crude
    //      32% (low temp) 60% (high temp) ethylene & 40% (low temp) 17% (high temp) propylene & 15% butadiene & 4% benzene & 4% residual oils < steam cracking < naphtha (either)
    //      45% natural gas & 29% Cyclopentane (refrigerant), Cyclohexane (if nylon) & 14% xylene & 9% benzene & 3% residual oils < catalytic reaction (Hydrogen Gas (medium) + Platinum Chloride (catalyst)) < heavy naphtha
    //      19% methane & 32% (low temp) 54% (high temp) ethylene & 34% (low temp) 12% (high temp) propylene & 11% hydrogen & 4% benzene < steam cracking < natural gas
    //      150 mb ethylene oxide < catalytic reaction (ground silver) < 50 mb ethylene + 100 mb oxygen
    //      100 mb ethylene glycol < catalytic reaction (any acid) < 50 water + 50 ethylene oxide
    //      100 mb methanol < catalytic reaction (powdered copper-alumina) < 100 mb carbon monoxide + 250 mb hydrogen
    //      100 mb acetic acid < catalytic reaction (powdered nickel tetracarbonyl) < 150 mb carbon monoxide + 100 mb methanol
    //      1 terephthalic acid + 100 mb water < catalytic reaction (powdered cobalt) < 150 mb xylene + 150 mb acetic acid
    //      400 mb acrylonitrile + 100 mb water < catalytic reaction (gallium-antimony oxide) < 710 mb propylene + 290 mb ammonia + 100 mb oxygen (gas)
    //      100 mb carbon monoxide + 100 mb hydrogen < catalytic reaction (powdered nickle) < 100 mb methane + 100 mb water
    //      150 mb carbon dioxide + 50 mb hydrogen < catalytic reaction (crushed iron ore) < 100 mb carbon monoxide + 100 mb water
    //      300 mb hydrogen + 1 graphite powder < catalytic reaction (powdered nickle & alumina) < 500 mb methane
    //      200 mb ethylbenzene < catalytic reaction (aluminum chloride) < 270 mb ethylene (gas) + 100 mb benzene
    //      100 mb styrene & 50 mb hydrogen < catalytic reaction (crushed iron ore) < 100 mb ethylbenzen
    //      100 mb acrylic acid & 100 mb water < steam cracking < 100 mb propylene
    //      100 mb hydroxyethyl acrylate & 100 mb water < catalytic reaction (ground titanium) < 50 mb acrylic acid + 50 mb ethylene oxide
    public static final MultiFluidRegistry CRUDE_OIL = new MultiFluidRegistry("crude_oil", FluidProperties.CRUDE_OIL, 2);
    public static final MultiFluidRegistry PURIFIED_CRUDE_OIL = new MultiFluidRegistry("purified_crude_oil", FluidProperties.PURIFIED_CRUDE_OIL, 2);
    public static final MultiFluidRegistry LIGHT_NAPHTHA = new MultiFluidRegistry("light_naphtha", FluidProperties.LIGHT_NAPHTHA);
    public static final MultiFluidRegistry HEAVY_NAPHTHA = new MultiFluidRegistry("heavy_naphtha", FluidProperties.HEAVY_NAPHTHA);
    public static final MultiFluidRegistry KEROSENE = new MultiFluidRegistry("kerosene", FluidProperties.KEROSENE);
    public static final MultiFluidRegistry LIGHT_FUEL_OIL = new MultiFluidRegistry("light_fuel_oil", FluidProperties.LIGHT_FUEL_OIL);
    public static final MultiFluidRegistry HEAVY_FUEL_OIL = new MultiFluidRegistry("heavy_fuel_oil", FluidProperties.HEAVY_FUEL_OIL);
    public static final MultiFluidRegistry RESIDUAL_OIL = new MultiFluidRegistry("residual_oil", FluidProperties.RESIDUAL_OIL, 2);
    public static final MultiFluidRegistry BENZENE = new MultiFluidRegistry("benzene", FluidProperties.BENZENE);
    public static final MultiFluidRegistry XYLENE = new MultiFluidRegistry("xylene", FluidProperties.XYLENE);
    public static final MultiFluidRegistry CYCLOHEXANE = new MultiFluidRegistry("cyclohexane", FluidProperties.CYCLOHEXANE);
    public static final MultiFluidRegistry CYCLOPENTANE = new MultiFluidRegistry("cyclopentane", FluidProperties.CYCLOPENTANE);
    public static final MultiFluidRegistry ETHYLENE_GLYCOL = new MultiFluidRegistry("ethylene_glycol", FluidProperties.ETHYLENE_GLYCOL);
    public static final MultiFluidRegistry METHANOL = new MultiFluidRegistry("methanol", FluidProperties.METHANOL);
    public static final MultiFluidRegistry ACETIC_ACID = new MultiFluidRegistry("acetic_acid", FluidProperties.ACETIC_ACID);
    public static final MultiFluidRegistry ACRYLONITRILE = new MultiFluidRegistry("acrylonitrile", FluidProperties.ACRYLONITRILE);
    public static final MultiFluidRegistry ETHYLBENZENE = new MultiFluidRegistry("ethylbenzene", FluidProperties.ETHYLBENZENE);
    public static final MultiFluidRegistry STYRENE = new MultiFluidRegistry("styrene", FluidProperties.STYRENE);
    public static final MultiFluidRegistry ACRYLIC_ACID = new MultiFluidRegistry("acrylic_acid", FluidProperties.ACRYLIC_ACID);
    public static final MultiFluidRegistry HYDROXYETHYL_ACRYLATE = new MultiFluidRegistry("hydroxyethyl_acrylate", FluidProperties.HYDROXYETHYL_ACRYLATE);
    public static final MultiFluidRegistry METHANE = new MultiFluidRegistry("methane", false, FluidProperties.METHANE);
    public static final MultiFluidRegistry ETHYLENE = new MultiFluidRegistry("ethylene", false, FluidProperties.ETHYLENE);
    public static final MultiFluidRegistry PROPYLENE = new MultiFluidRegistry("propylene", false, FluidProperties.PROPYLENE);
    public static final MultiFluidRegistry BUTADIENE = new MultiFluidRegistry("butadiene", false, FluidProperties.BUTADIENE);
    public static final MultiFluidRegistry ETHYLENE_OXIDE = new MultiFluidRegistry("ethylene_oxide", false, FluidProperties.ETHYLENE_OXIDE);


    //TODO: aluminium refining: check ratios
    //      100 mb salt brine < 120 heat mixing < 300 mb water
    //      100 mb chlorine gas < electrolysis < 250 mb salt brine
    //      600 mb red mud & 4 aluminum hydroxide < 200 heat mixing < any aluminum ore + 400 mb sodium hydroxide
    //      500 mb molten alumina brine < alloy < fluorite + alumina
    //      100 mb carbon dioxide gas < electrolysis < molten alumina brine
    //TODO: Ammonia production
    //      liquid air < pressurize with cooling < nothing?
    //      78% nitrogen & 21% oxygen & 1% argon? & 1% water < fractional distillation < liquid air
    //      100 mb ammonia < catalytic reaction (powdered iron ore) < 210 mb hydrogen (gas) + 100 mb nitrogen
    public static final MultiFluidRegistry SALT_BRINE = new MultiFluidRegistry("salt_brine", FluidProperties.SALT_BRINE);
    public static final MultiFluidRegistry RED_MUD = new MultiFluidRegistry("red_mud", FluidProperties.RED_MUD);
    public static final MultiFluidRegistry MOLTEN_ALUMINA_BRINE = new MultiFluidRegistry("molten_alumina_brine", false, FluidProperties.MOLTEN_ALUMINA_BRINE);
    public static final MultiFluidRegistry LIQUID_AIR = new MultiFluidRegistry("liquid_air", false, FluidProperties.LIQUID_AIR);
    public static final MultiFluidRegistry HYDROGEN = new MultiFluidRegistry("hydrogen", false, FluidProperties.HYDROGEN);
    public static final MultiFluidRegistry HELIUM = new MultiFluidRegistry("helium", false, FluidProperties.HELIUM);
    public static final MultiFluidRegistry NITROGEN = new MultiFluidRegistry("nitrogen", false, FluidProperties.NITROGEN);
    public static final MultiFluidRegistry OXYGEN = new MultiFluidRegistry("oxygen", false, FluidProperties.OXYGEN);
    public static final MultiFluidRegistry CHLORINE = new MultiFluidRegistry("chlorine", false, FluidProperties.CHLORINE);
    public static final MultiFluidRegistry ARGON = new MultiFluidRegistry("argon", false, FluidProperties.ARGON);
    public static final MultiFluidRegistry STEAM = new MultiFluidRegistry("steam", false, FluidProperties.STEAM);
    public static final MultiFluidRegistry SUPERHEATED_STEAM = new MultiFluidRegistry("superheated_steam", false, FluidProperties.SUPERHEATED_STEAM);
    public static final MultiFluidRegistry AIR = new MultiFluidRegistry("air", false, FluidProperties.AIR);
    public static final MultiFluidRegistry CARBON_MONOXIDE = new MultiFluidRegistry("carbon_monoxide", false, FluidProperties.CARBON_MONOXIDE);
    public static final MultiFluidRegistry CARBON_DIOXIDE = new MultiFluidRegistry("carbon_dioxide", false, FluidProperties.CARBON_DIOXIDE);
    public static final MultiFluidRegistry SULFUR_DIOXIDE = new MultiFluidRegistry("sulfur_dioxide", false, FluidProperties.SULFUR_DIOXIDE);
    public static final MultiFluidRegistry HYDROGEN_SULFIDE = new MultiFluidRegistry("hydrogen_sulfide", false, FluidProperties.HYDROGEN_SULFIDE);
    public static final MultiFluidRegistry AMMONIA = new MultiFluidRegistry("ammonia", false, FluidProperties.AMMONIA);

    //TODO: melting metals in a furnace
    public static final MultiFluidRegistry MOLTEN_IRON = new MultiFluidRegistry("molten_iron", FluidProperties.MOLTEN_IRON, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_COPPER = new MultiFluidRegistry("molten_copper", FluidProperties.MOLTEN_COPPER, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_GOLD = new MultiFluidRegistry("molten_gold", FluidProperties.MOLTEN_GOLD, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_NICKEL = new MultiFluidRegistry("molten_nickel", FluidProperties.MOLTEN_NICKEL, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_COBALT = new MultiFluidRegistry("molten_cobalt", FluidProperties.MOLTEN_COBALT, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_SILVER = new MultiFluidRegistry("molten_silver", FluidProperties.MOLTEN_SILVER, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_ALUMINUM = new MultiFluidRegistry("molten_aluminum", FluidProperties.MOLTEN_ALUMINUM, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_CHROMIUM = new MultiFluidRegistry("molten_chromium", FluidProperties.MOLTEN_CHROMIUM, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_GALLIUM = new MultiFluidRegistry("molten_gallium", FluidProperties.MOLTEN_GALLIUM, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_PLATINUM = new MultiFluidRegistry("molten_platinum", FluidProperties.MOLTEN_PLATINUM, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_STEEL = new MultiFluidRegistry("molten_steel", FluidProperties.MOLTEN_STEEL, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_STAINLESS_STEEL = new MultiFluidRegistry("molten_stainless_steel", FluidProperties.MOLTEN_STAINLESS_STEEL, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_CUPRONICKEL = new MultiFluidRegistry("molten_cupronickel", FluidProperties.MOLTEN_CUPRONICKEL, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);
    public static final MultiFluidRegistry MOLTEN_DYMALLOY = new MultiFluidRegistry("molten_dymalloy", FluidProperties.MOLTEN_DYMALLOY, MOLTEN_STILL, MOLTEN_FLOWING, MOLTEN_OVERLAY);

    // NUCLEAR ENGINEERING & QUANTUM PHYSICS
    //TODO: Beryllium refining: (Adv Production / Nuclear Expansion)
    //      300 mb Hydrofluoric acid < 265 heat mixer < fluorite + 100 mb sulfuric acid + 100 mb water
    //      200 mb Hexafluorosilicic acid & 160 mb water < 150 mixer < 500 mb Hydrofluoric acid + 1 Silicon dioxide (ground quartz)
    //      100 mb Sodium hexafluorosilicate & 100 mb HCl < unheated mixer < 2 salt + 100 mb Hexafluorosilicic acid
    //      1b fluoridated water (4 sodium fluoride) < unheated mixer < 1b water + 4 sodium hydroxide + Sodium Tetrafluoroberyllate
    //      200 mb beryllium chloride & 100 mb carbon monoxide < alloy < 100 mb chlorine gas + Beryllium hydroxide + graphite

    //TODO: Lithium brine: main source of lithium, slow to process in machine better to make brine pool for evaporation (must have sky access and be day and not raining)?


    public static class MultiFluidRegistry extends FluidRegistryHelper {
        public static final ResourceLocation FLUID_STILL = new ResourceLocation(Scifix.MOD_ID, "block/generic_still");
        public static final ResourceLocation FLUID_FLOWING = new ResourceLocation(Scifix.MOD_ID, "block/generic_flow");
        public static final ResourceLocation FLUID_OVERLAY = new ResourceLocation(Scifix.MOD_ID, "block/generic_overlay");
        public static final BlockBehaviour.Properties FLUID_BLOCK = BlockBehaviour.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops();
        public static final Item.Properties BUCKET = new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(Scifix.TAB);

        public MultiFluidRegistry(String name, IFluidProperties properties) {
            this(name, true, properties);
        }

        public MultiFluidRegistry(String name, IFluidProperties properties, int decreasePerBlock) {
            this(name, true, properties, decreasePerBlock, FLUID_STILL, FLUID_FLOWING, FLUID_OVERLAY);
        }

        public MultiFluidRegistry(String name, Boolean bucket, IFluidProperties properties) {
            this(name, bucket, properties, 1, FLUID_STILL, FLUID_FLOWING, FLUID_OVERLAY);
        }

        public MultiFluidRegistry(String name, IFluidProperties properties, ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
            this(name, false, properties, 1, still, flowing, overlay);
        }

        public MultiFluidRegistry(String name, Boolean bucket, IFluidProperties properties, int decreasePerBlock, ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
            super(name, bucket, properties, decreasePerBlock, still, flowing, overlay);
        }

        protected DeferredRegister<Fluid> getFluidRegistry(String type) {
            return reg;
        }

        protected DeferredRegister<Block> getBlockRegistry(String type) {
            return SBlocks.reg;
        }

        protected DeferredRegister<Item> getItemRegistry(String type) {
            return SItems.reg;
        }

        protected BlockBehaviour.Properties getBlockProperties() {
            return FLUID_BLOCK;
        }

        protected Item.Properties getItemProperties() {
            return BUCKET;
        }
    }
}