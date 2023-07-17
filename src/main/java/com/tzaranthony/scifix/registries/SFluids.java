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

    //TODO: oil -- found in largest pools under the ocean or under deserts, can be found in surface pools
    //TODO: salt brine -- 3 water : 1 brine in a heated mixer
    //TODO: red mud -- crushed or powdered aluminum ore + sodium hydroxide in a heated mixer or in a blast furnace?
    //TODO: alumina brine -- fluorite + alumina in a heated mixer
    //TODO: chlorine gas -- electrolysis of salt brine using carbon electrodes
    //TODO: carbon monoxide gas -- electrolysis of alumina brine using carbon electrodes



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