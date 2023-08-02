package com.tzaranthony.scifix.core.util.tags;

import com.tzaranthony.scifix.Scifix;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SItemTags {
    public static final TagKey<Item> CRUSHABLE = registerItemTag("crushable");
    public static final TagKey<Item> GRINDABLE = registerItemTag("grindable");
    public static final TagKey<Item> INGOTS = registerItemTag("ingots");
    public static final TagKey<Item> CONVECTION_FUEL = registerItemTag("convection_fuel");

    private static TagKey<Item> registerItemTag(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(Scifix.MOD_ID, name));
    }

    private static TagKey<Item> registerForgeItemTag(String name) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation("forge", name));
    }
}