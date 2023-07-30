package com.tzaranthony.scifix.core.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

import java.util.HashMap;

public abstract class AbstractOreRefiningRecipe extends RfRecipe {
    protected final ResourceLocation id;
    protected final int rf;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final HashMap<ItemStack, Float> secondaryMap;

    public AbstractOreRefiningRecipe(ResourceLocation id, int rf, Ingredient input, ItemStack output, HashMap<ItemStack, Float> secondaryMap) {
        this.id = id;
        this.rf = rf;
        this.input = input;
        this.output = output;
        this.secondaryMap = secondaryMap;
    }

    public boolean matches(Container container, Level level) {
        return this.input.test(container.getItem(0));
    }

    public Ingredient getIngredient() {
        return this.input;
    }

    public ItemStack assemble(Container container) {
        return this.output.copy();
    }

    public ItemStack assemble() {
        return this.output.copy();
    }

    public boolean canCraftInDimensions(int w, int h) {
        return true;
    }

    public int getRF() {
        return this.rf;
    }

    public ItemStack getResultItem() {
        return this.output;
    }

    public HashMap<ItemStack, Float> getSecondaryProbMap() {
        return this.secondaryMap;
    }

    public ResourceLocation getId() {
        return this.id;
    }
}