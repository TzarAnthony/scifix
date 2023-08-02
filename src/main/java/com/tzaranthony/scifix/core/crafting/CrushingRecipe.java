package com.tzaranthony.scifix.core.crafting;

import com.google.gson.JsonObject;
import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.api.crafting.CraftingHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class CrushingRecipe extends AbstractOreRefiningRecipe {
    public static final ResourceLocation TYPE_ID = new ResourceLocation(Scifix.MOD_ID, "crushing");
    public static final RecipeType<CrushingRecipe> TYPE = RecipeType.register(TYPE_ID.toString());

    public CrushingRecipe(ResourceLocation id, int rf, Ingredient input, ItemStack output, HashMap<ItemStack, Float> secondaryMap) {
        super(id, rf, input, output, secondaryMap);
    }

    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public RecipeType<?> getType() {
        return TYPE;
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<CrushingRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public CrushingRecipe fromJson(ResourceLocation id, JsonObject json) {
            int rf = GsonHelper.getAsInt(json, "rfPerTick", 0);
            Ingredient input = Ingredient.fromJson(GsonHelper.getAsJsonObject(json, "ingredient"));
            json = GsonHelper.getAsJsonObject(json, "result");
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));
            HashMap<ItemStack, Float> secondaryMap = new HashMap<>();
            if (json.has("secondary")) {
                secondaryMap = CraftingHelper.secondaryOutputMapFromJson(GsonHelper.getAsJsonArray(json, "secondary"));
            }
            return new CrushingRecipe(id, rf, input, output, secondaryMap);
        }

        @Nullable
        @Override
        public CrushingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            int rf = buffer.readVarInt();
            Ingredient input = Ingredient.fromNetwork(buffer);
            ItemStack output = buffer.readItem();
            HashMap<ItemStack, Float> secondaryMap = CraftingHelper.secondaryMapFromBuffer(buffer);
            return new CrushingRecipe(id, rf, input, output, secondaryMap);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, CrushingRecipe recipe) {
            buffer.writeVarInt(recipe.getRF());
            recipe.getIngredient().toNetwork(buffer);
            buffer.writeItem(recipe.getResultItem());
        }
    }
}