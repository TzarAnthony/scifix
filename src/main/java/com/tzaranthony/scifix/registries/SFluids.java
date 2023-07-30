package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SFluids {
    public static final ResourceLocation FLUID_STILL = new ResourceLocation(Scifix.MOD_ID, "block/potion_still");
    public static final ResourceLocation FLUID_FLOWING = new ResourceLocation(Scifix.MOD_ID, "block/potion_flow");
    public static final ResourceLocation FLUID_OVERLAY = new ResourceLocation(Scifix.MOD_ID, "block/potion_overlay");
    public static final DeferredRegister<Fluid> reg = DeferredRegister.create(ForgeRegistries.FLUIDS, Scifix.MOD_ID);

    private static final Item.Properties BUCKET = new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1).tab(Scifix.TAB);
    private static final BlockBehaviour.Properties LIQUID = BlockBehaviour.Properties.of(Material.LAVA).noCollission().strength(100.0F).noDrops();

    //TODO: steam

    //TODO: aluminium refining: check ratios
    //      100 mb salt brine < 120 heat mixing < 300 mb water
    //      100 mb chlorine gas < electrolysis < 250 mb salt brine
    //      600 mb red mud & 4 aluminum hydroxide < 200 heat mixing < any aluminum ore + 400 mb sodium hydroxide
    //      500 mb molten alumina brine < alloy < fluorite + alumina
    //      100 mb carbon dioxide gas < electrolysis < molten alumina brine

    // might make multi-stage catalyst reactors to cut down on these. Maybe as an upgrade --- config for simple petroleum refining?
    //TODO: oil -- found in largest pools under the ocean or under deserts, can be found in surface pools:
    //      Crude has different densities based on biome?
    //      Hydrogen sulfide (1%) & ammonia (3%) & purified crude (96%) < crude + hydrogen at high temperature
    //      3% Nat Gas (>25 heat) & 18% (>60 heat) light naphtha & 16% (>140 heat) heavy naphtha & 11% (>232 heat) Kerosene & 21% (>296 heat) light fuel oil & 22% (>373 heat) heavy fuel oil & 9% (>467 heat) residual oil < fractional distillation < purified crude
    //      32% (low temp) 60% (high temp) ethylene & 40% (low temp) 17% (high temp) propylene & 15% butadiene & 4% benzene & 4% residual oils < steam cracking < naphtha (either)
    //      45% natural gas & 29% Cyclopentane (refrigerant), Cyclohexane (if nylon) & 14% xylene & 9% benzene & 3% residual oils < catalytic reaction (Hydrogen Gas (medium) + Platinum Chloride (catalyst)) < heavy naphtha
    //      19% fuel gas & 32% (low temp) 54% (high temp) ethylene & 34% (low temp) 12% (high temp) propylene & 11% hydrogen & 4% benzene < steam cracking < natural gas
    //      150 mb ethylene oxide < catalytic reaction (ground silver) < 50 mb ethylene + 100 mb oxygen
    //      100 mb ethylene glycol < catalytic reaction (any acid) < 50 water + 50 ethylene oxide
    //      100 mb methanol < catalytic reaction (powdered copper-alumina) < 100 mb carbon monoxide + 250 mb hydrogen
    //      100 mb acetic acid < catalytic reaction (powdered nickel tetracarbonyl) < 150 mb carbon monoxide + 100 mb methanol
    //      200 mb terephthalic acid + 100 mb water < catalytic reaction (powdered cobalt) < 150 mb xylene + 150 mb acetic acid
    //      400 mb acrylonitrile + 100 mb water < catalytic reaction (gallium-antimony oxide) < 710 mb propylene + 290 mb ammonia + 100 mb oxygen (gas)
    //      100 mb carbon monoxide + 100 mb hydrogen < catalytic reaction (powdered nickle) < 100 mb fuel gas + 100 mb water
    //      150 mb carbon dioxide + 50 mb hydrogen < catalytic reaction (crushed iron ore) < 100 mb carbon monoxide + 100 mb water
    //      300 mb hydrogen + 1 graphite powder < catalytic reaction (powdered nickle & alumina) < 500 mb fuel gas
    //      200 mb ethylbenzene < catalytic reaction (aluminum chloride) < 270 mb ethylene (gas) + 100 mb benzene
    //      100 mb styrene & 50 mb hydrogen < catalytic reaction (crushed iron ore) < 100 mb ethylbenzen
    //      100 mb acrylic acid & 100 mb water < steam cracking < 100 mb propylene
    //      100 mb hydroxyethyl acrylate & 100 mb water < catalytic reaction (ground titanium) < 50 mb acrylic acid + 50 mb ethylene oxide

    //TODO: Ammonia production
    //      liquid air < pressurize with cooling < nothing?
    //      78% nitrogen & 21% oxygen & 1% argon? & 1% water < fractional distillation < liquid air
    //      100 mb ammonia < catalytic reaction (powdered iron ore) < 210 mb hydrogen (gas) + 100 mb nitrogen

    //TODO: Beryllium refining: (Adv Production / Nuclear Expansion)
    //      300 mb Hydrofluoric acid < 265 heat mixer < fluorite + 100 mb sulfuric acid + 100 mb water
    //      200 mb Hexafluorosilicic acid & 160 mb water < 150 mixer < 500 mb Hydrofluoric acid + 1 Silicon dioxide (ground quartz)
    //      100 mb Sodium hexafluorosilicate & 100 mb HCl < unheated mixer < 2 salt + 100 mb Hexafluorosilicic acid
    //      1b fluoridated water (4 sodium fluoride) < unheated mixer < 1b water + 4 sodium hydroxide + Sodium Tetrafluoroberyllate
    //      200 mb beryllium chloride & 100 mb carbon monoxide < alloy < 100 mb chlorine gas + Beryllium hydroxide + graphite

    //TODO: Lithium brine: main source of lithium, slow to process in machine better to make brine pool for evaporation (must have sky access and be day and not raining)?



//    public static final RegistryObject<FlowingFluid> MUNDANE_FLUID = reg.register("mundane_potion",  () -> new ForgeFlowingFluid.Source(SFluids.MUNDANE_PROPERTIES));
//    public static final RegistryObject<FlowingFluid> MUNDANE_FLOWING = reg.register("mundane_potion_flowing",  () -> new ForgeFlowingFluid.Flowing(SFluids.MUNDANE_PROPERTIES));
//    protected static final ForgeFlowingFluid.Properties MUNDANE_PROPERTIES = properties(Potions.MUNDANE, () -> MUNDANE_FLUID.get(), () -> MUNDANE_FLOWING.get()
//            , () -> SFluids.MUNDANE_BLOCK.get(), () -> SFluids.MUNDANE_BUCKET.get(),500, 400, 0, 0);
//    public static final RegistryObject<LiquidBlock> MUNDANE_BLOCK = SBlocks.reg.register("mundane_potion_block", () -> new LiquidBlock(SFluids.MUNDANE_FLUID, LIQUID));
//    public static final RegistryObject<Item> MUNDANE_BUCKET = SItems.reg.register("mundane_potion_bucket", () -> new BucketItem(SFluids.MUNDANE_FLUID, BUCKET));



    private static ForgeFlowingFluid.Properties properties(Potion potion, Supplier<FlowingFluid> source, Supplier<FlowingFluid> flowing, Supplier<LiquidBlock> block, Supplier<Item> bucket, int DenseAdd, int ViscAdd, int TempAdd, int LghtAdd) {
        int color = setTransparency(PotionUtils.getColor(potion), 190);
        return new ForgeFlowingFluid.Properties(source, flowing,
                FluidAttributes.builder(FLUID_STILL, FLUID_FLOWING)
                        .overlay(FLUID_OVERLAY)
                        .color(color)
                        .density(1000 + DenseAdd)
                        .viscosity(1000 + ViscAdd)
                        .temperature(300 + TempAdd)
                        .luminosity(0 + LghtAdd))
                .levelDecreasePerBlock(2)
                .block(block)
                .bucket(bucket);
    }

    public static int setTransparency(int color, int alpha) {
        return (color & 0x00FFFFFF) | (alpha << 24);
    }
}