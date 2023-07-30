package com.tzaranthony.scifix.core.crafting.helpers;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class SizedIngredient implements Predicate<ItemStack> {
    private final Ingredient ingredient;
    private final int count;
    public static final SizedIngredient EMPTY = new SizedIngredient(Ingredient.EMPTY, 0);

    protected SizedIngredient(Ingredient ingredient, int count) {
        this.ingredient = ingredient;
        this.count = count;
    }

    @Override
    public boolean test(@Nullable ItemStack stack) {
        if (stack == null) {
            return false;
        }
        return stack.getCount() >= this.count && this.ingredient.test(stack);
    }

    public int getCount() {
        return this.count;
    }

    public Ingredient getIngredient() {
        return this.ingredient;
    }

    public List<ItemStack> getAsItemStacks() {
        ItemStack[] stacks = this.ingredient.getItems();
        for (ItemStack stack : stacks) {
            stack.setCount(this.count);
        }
        return List.of(stacks);
    }

    public boolean isEmpty() {
        return this.count == 0 || this.ingredient.isEmpty();
    }

    public final void toNetwork(FriendlyByteBuf buff) {
        buff.writeInt(count);
        this.ingredient.toNetwork(buff);
    }

    public static SizedIngredient fromNetwork(FriendlyByteBuf buff) {
        int count = buff.readInt();
        Ingredient ingredient = Ingredient.fromNetwork(buff);
        return new SizedIngredient(ingredient, count);
    }

    public static SizedIngredient fromJson(JsonObject json) {
        int count = json.has("count") ? GsonHelper.getAsInt(json, "count") : 1;
        Ingredient ing = Ingredient.fromJson(json.get("ing"));
        return new SizedIngredient(ing, count);
    }
}