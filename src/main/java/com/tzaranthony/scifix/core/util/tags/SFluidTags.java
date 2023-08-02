package com.tzaranthony.scifix.core.util.tags;

import com.tzaranthony.scifix.Scifix;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class SFluidTags {
    public static final TagKey<Fluid> COMBUSTIBLE = registerBlockTag("combustible");

    private static TagKey<Fluid> registerBlockTag(String name) {
        return TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation(Scifix.MOD_ID, name));
    }

    private static TagKey<Fluid> registerForgeBlockTag(String name) {
        return TagKey.create(Registry.FLUID_REGISTRY, new ResourceLocation("forge", name));
    }
}