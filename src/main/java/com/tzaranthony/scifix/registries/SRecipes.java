package com.tzaranthony.scifix.registries;

import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.core.crafting.CrushingRecipe;
import com.tzaranthony.scifix.core.crafting.GrindingRecipe;
import com.tzaranthony.scifix.core.crafting.MixingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> reg = DeferredRegister.create(Registry.RECIPE_SERIALIZER_REGISTRY, Scifix.MOD_ID);

    public static final RegistryObject<RecipeSerializer<CrushingRecipe>> CRUSHING = reg.register("crushing", () -> CrushingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<GrindingRecipe>> GRINDING = reg.register("grinding", () -> GrindingRecipe.Serializer.INSTANCE);
    public static final RegistryObject<RecipeSerializer<MixingRecipe>> MIXING = reg.register("mixing", () -> MixingRecipe.Serializer.INSTANCE);
}