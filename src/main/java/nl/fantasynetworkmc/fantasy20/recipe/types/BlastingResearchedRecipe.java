package nl.fantasynetworkmc.fantasy20.recipe.types;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class BlastingResearchedRecipe extends AbstractCookingRecipe {
	
	public BlastingResearchedRecipe(ResourceLocation idIn, String groupIn, Ingredient ingredientIn, ItemStack resultIn, float experienceIn, int cookTimeIn) {
		super(IRecipeType.BLASTING, idIn, groupIn, ingredientIn, resultIn, experienceIn, cookTimeIn);
	}
	
	@Override
	public boolean matches(IInventory inv, World worldIn) {
		
		return this.ingredient.test(inv.getStackInSlot(0));
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipe.BLASTING_RESEARCHED;
	}

   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<BlastingResearchedRecipe> {

		public Serializer() {
			setRegistryName("fantasy20:blasting_researched_recipe");
		}
		
		public BlastingResearchedRecipe read(ResourceLocation recipeId, JsonObject json) {
		      String s = JSONUtils.getString(json, "group", "");
		      JsonElement jsonelement = (JsonElement)(JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient"));
		      Ingredient ingredient = Ingredient.deserialize(jsonelement);
		      //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
		      if (!json.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
		      	ItemStack itemstack;
		      	if (json.get("result").isJsonObject()) itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
		      else {
			      String s1 = JSONUtils.getString(json, "result");
			      ResourceLocation resourcelocation = new ResourceLocation(s1);
			      if(!ForgeRegistries.ITEMS.containsKey(resourcelocation)) {
			    	  throw new IllegalStateException("Item: " + s1 + " does not exist");
			      }
			      itemstack = new ItemStack(ForgeRegistries.ITEMS.getValue(resourcelocation));
			      
		      }
		      float f = JSONUtils.getFloat(json, "experience", 0.0F);
		      int i = JSONUtils.getInt(json, "cookingtime", 0);
		      return new BlastingResearchedRecipe(recipeId, s, ingredient, itemstack, f, i);
		   }

		   public BlastingResearchedRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
		      String s = buffer.readString(32767);
		      Ingredient ingredient = Ingredient.read(buffer);
		      ItemStack itemstack = buffer.readItemStack();
		      float f = buffer.readFloat();
		      int i = buffer.readVarInt();
		      return new BlastingResearchedRecipe(recipeId, s, ingredient, itemstack, f, i);
		   }

		   public void write(PacketBuffer buffer, BlastingResearchedRecipe recipe) {
		      buffer.writeString(recipe.group);
		      recipe.ingredient.write(buffer);
		      buffer.writeItemStack(recipe.result);
		      buffer.writeFloat(recipe.experience);
		      buffer.writeVarInt(recipe.cookTime);
		   }

	}
 

}
