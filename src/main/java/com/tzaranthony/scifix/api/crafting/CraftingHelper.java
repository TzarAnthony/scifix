package com.tzaranthony.scifix.api.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipe;

import java.util.HashMap;

public class CraftingHelper {
    public static float getProbability(JsonObject json, String str) {
        float prob = GsonHelper.getAsFloat(json, str, 0);
        return prob > 1 ? prob / 100 : prob;
    }

    public static HashMap<ItemStack, Float> secondaryOutputMapFromJson(JsonArray array) {
        HashMap<ItemStack, Float> secondaryMap = new HashMap<>();
        for (int i = 0; i < array.size(); ++i) {
            JsonObject json = array.get(i).getAsJsonObject();
            float prob = CraftingHelper.getProbability(json, "prob");
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            secondaryMap.put(output, prob);
        }
        return secondaryMap;
    }

    public static NonNullList<Ingredient> ingsFromJson(JsonArray array) {
        NonNullList<Ingredient> nonnulllist = NonNullList.create();
        for (int i = 0; i < array.size(); ++i) {
            Ingredient ingredient = Ingredient.fromJson(array.get(i));
            if (net.minecraftforge.common.ForgeConfig.SERVER.skipEmptyShapelessCheck.get() || !ingredient.isEmpty()) {
                nonnulllist.add(ingredient);
            }
        }
        return nonnulllist;
    }

    public static NonNullList<SizedIngredient> sizedIngsFromJson(JsonArray array) {
        NonNullList<SizedIngredient> nonnulllist = NonNullList.create();
        for (int i = 0; i < array.size(); ++i) {
            SizedIngredient ingredient = SizedIngredient.fromJson(array.get(i).getAsJsonObject());
            if (net.minecraftforge.common.ForgeConfig.SERVER.skipEmptyShapelessCheck.get() || !ingredient.isEmpty()) {
                nonnulllist.add(ingredient);
            }
        }
        return nonnulllist;
    }

    public static void secondaryMapToBuffer(HashMap<ItemStack, Float> secondaryProbMap, FriendlyByteBuf buffer) {
        buffer.writeInt(secondaryProbMap.size());
        for (ItemStack stack : secondaryProbMap.keySet()) {
            buffer.writeFloat(secondaryProbMap.get(stack));
            buffer.writeItem(stack);
        }
    }

    public static HashMap<ItemStack, Float> secondaryMapFromBuffer(FriendlyByteBuf buffer) {
        HashMap<ItemStack, Float> secondaryProbMap = new HashMap<>();
        int secondSize = buffer.readInt();
        for (int i = 0; i < secondSize; ++i) {
            float prob = buffer.readFloat();
            ItemStack stack = buffer.readItem();
            secondaryProbMap.put(stack, prob);
        }
        return secondaryProbMap;
    }
}