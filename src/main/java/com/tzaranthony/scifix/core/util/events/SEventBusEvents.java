package com.tzaranthony.scifix.core.util.events;

import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.core.crafting.CrushingRecipe;
import com.tzaranthony.scifix.core.crafting.GrindingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Scifix.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SEventBusEvents {
    @SubscribeEvent
    public static void registerRecipeTypes(final RegistryEvent.Register<RecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, CrushingRecipe.TYPE_ID, CrushingRecipe.TYPE);
        Registry.register(Registry.RECIPE_TYPE, GrindingRecipe.TYPE_ID, GrindingRecipe.TYPE);
    }
}