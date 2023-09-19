package com.tzaranthony.scifix.core.crafting;

import com.google.gson.JsonObject;
import com.tzaranthony.scifix.Scifix;
import com.tzaranthony.scifix.api.crafting.CraftingHelper;
import com.tzaranthony.scifix.api.crafting.SizedIngredient;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

public class MixingRecipe extends FluidRecipe {
    public static final ResourceLocation TYPE_ID = new ResourceLocation(Scifix.MOD_ID, "mixing");
    public static final RecipeType<MixingRecipe> TYPE = RecipeType.register(TYPE_ID.toString());
    protected final NonNullList<SizedIngredient> itemInputs;
    protected final ItemStack itemOutput;

    public MixingRecipe(ResourceLocation id, int rf, NonNullList<FluidStack> fluidInputs, NonNullList<SizedIngredient> itemInputs, FluidStack fluidOutput, ItemStack itemOutput) {
        super(id, rf, fluidInputs, fluidOutput);
        this.itemInputs = itemInputs;
        this.itemOutput = itemOutput;
    }

    public boolean matches(Container container, Level level) {
        for (int i = 0; i < this.itemInputs.size(); i++) {
            boolean breaker = false;
            for (int j = 0; j < container.getContainerSize(); ++j) {
                if (this.itemInputs.get(i).test(container.getItem(j))) {
                    breaker = true;
                    break;
                }
            }
            if (breaker) break;
            return false;
        }
        return true;
    }

    public boolean canCraftInDimensions(int x, int y) {
        return true;
    }

    public NonNullList<SizedIngredient> getSizedIngredients() {
        return this.itemInputs;
    }

    public ItemStack assemble(Container container) {
        return this.itemOutput.copy();
    }

    public ItemStack assemble() {
        return this.itemOutput.copy();
    }

    public ItemStack getResultItem() {
        return this.itemOutput;
    }

    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    public RecipeType<?> getType() {
        return TYPE;
    }


    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<MixingRecipe> {
        public static final MixingRecipe.Serializer INSTANCE = new MixingRecipe.Serializer();

        @Override
        public MixingRecipe fromJson(ResourceLocation id, JsonObject json) {
            int rf = GsonHelper.getAsInt(json, "rfPerTick", 0);
            NonNullList<SizedIngredient> itemInputs = CraftingHelper.sizedIngsFromJson(GsonHelper.getAsJsonArray(json, "ingredients"));
            NonNullList<FluidStack> fluidsInputs = CraftingHelper.fluidsFromJson(GsonHelper.getAsJsonArray(json, "fluids"));

            json = GsonHelper.getAsJsonObject(json, "result");
            ItemStack itemOutput = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "itemOutput"));
            FluidStack fluidOutput = CraftingHelper.getFluid(GsonHelper.getAsJsonObject(json, "fluidOutput"));
            return new MixingRecipe(id, rf, fluidsInputs, itemInputs, fluidOutput, itemOutput);
        }

        @Nullable
        @Override
        public MixingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            int rf = buffer.readVarInt();

            int i = buffer.readVarInt();
            NonNullList<SizedIngredient> itemInputs = NonNullList.withSize(i, SizedIngredient.EMPTY);
            for(int k = 0; k < itemInputs.size(); ++k) {
                itemInputs.set(k, SizedIngredient.fromNetwork(buffer));
            }

            int j = buffer.readVarInt();
            NonNullList<FluidStack> fluidsInputs = NonNullList.withSize(j, FluidStack.EMPTY);
            for(int k = 0; k < fluidsInputs.size(); ++k) {
                fluidsInputs.set(k, FluidStack.readFromPacket(buffer));
            }
            ItemStack itemOutput = buffer.readItem();
            FluidStack fluidOutput = buffer.readFluidStack();
            return new MixingRecipe(id, rf, fluidsInputs, itemInputs, fluidOutput, itemOutput);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, MixingRecipe recipe) {
            buffer.writeVarInt(recipe.getRF());

            buffer.writeInt(recipe.getSizedIngredients().size());
            for(SizedIngredient ingredient : recipe.getSizedIngredients()) {
                ingredient.toNetwork(buffer);
            }

            buffer.writeInt(recipe.getFluidIngredients().size());
            for(FluidStack fluid : recipe.getFluidIngredients()) {
                fluid.writeToPacket(buffer);
            }

            buffer.writeItem(recipe.getResultItem());
            recipe.getResultFluid().writeToPacket(buffer);
        }
    }
}