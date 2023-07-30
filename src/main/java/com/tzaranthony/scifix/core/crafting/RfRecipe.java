package com.tzaranthony.scifix.core.crafting;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class RfRecipe implements Recipe<Container> {

    public abstract ItemStack assemble();

    public abstract int getRF();
}