package com.tzaranthony.scifix.api.helpers;

import com.tzaranthony.scifix.api.properties.IFluidProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public abstract class FluidRegistryHelper {
    private ForgeFlowingFluid.Properties makeProperties(IFluidProperties properties, int decreasePerBlock, ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
        FluidAttributes.Builder builder = FluidAttributes.builder(still, flowing).overlay(overlay)
                .color(properties.getColorWithTransparency())
                .density(properties.getDensity())
                .viscosity(properties.getViscosity())
                .temperature(properties.getTemperature())
                .luminosity(properties.getLuminosity());
        if (properties.isGas()) builder = builder.gaseous();
        return new ForgeFlowingFluid.Properties(fluid_source, fluid_flowing, builder)
                .levelDecreasePerBlock(decreasePerBlock)
                .block(fluid_block)
                .bucket(fluid_bucket);
    }

    private static RegistryObject<ForgeFlowingFluid.Source> fluid_source;
    private static RegistryObject<ForgeFlowingFluid.Flowing> fluid_flowing;
    private static RegistryObject<LiquidBlock> fluid_block;
    private static RegistryObject<BucketItem> fluid_bucket;

    public FluidRegistryHelper(String name, Boolean bucket, IFluidProperties properties, int decreasePerBlock, ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
        ForgeFlowingFluid.Properties FluidProps = makeProperties(properties, decreasePerBlock, still, flowing, overlay);

        fluid_source = getFluidRegistry("fluid").register(name, () -> new ForgeFlowingFluid.Source(FluidProps));
        fluid_flowing = getFluidRegistry("fluid").register(name + "_flowing", () -> new ForgeFlowingFluid.Flowing(FluidProps));
        fluid_block = getBlockRegistry("block").register(name + "_block", () -> new LiquidBlock(fluid_source, getBlockProperties()));
        fluid_bucket = getItemRegistry("item").register(name + "_bucket", () -> bucket ? new BucketItem(fluid_source, getItemProperties()) : new BucketItem(Fluids.EMPTY, getItemProperties()));
    }

    public RegistryObject<ForgeFlowingFluid.Source> getFluid_source() {
        return fluid_source;
    }

    public RegistryObject<ForgeFlowingFluid.Flowing> getFluid_flowing() {
        return fluid_flowing;
    }

    public RegistryObject<LiquidBlock> getFluid_block() {
        return fluid_block;
    }

    public RegistryObject<BucketItem> getFluid_bucket() {
        return fluid_bucket;
    }

    protected abstract DeferredRegister<Fluid> getFluidRegistry(String type);

    protected abstract DeferredRegister<Block> getBlockRegistry(String type);

    protected abstract DeferredRegister<Item> getItemRegistry(String type);

    protected abstract BlockBehaviour.Properties getBlockProperties();

    protected abstract Item.Properties getItemProperties();
}