package com.tzaranthony.scifix.core.util.tags;

import com.tzaranthony.scifix.Scifix;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class SBlockTags {
    public static final TagKey<Block> FROZEN_WATER = registerBlockTag("frozen_water");

    private static TagKey<Block> registerBlockTag(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Scifix.MOD_ID, name));
    }

    private static TagKey<Block> registerForgeBlockTag(String name) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation("forge", name));
    }
}