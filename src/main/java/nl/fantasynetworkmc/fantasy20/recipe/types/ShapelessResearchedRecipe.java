package nl.fantasynetworkmc.fantasy20.recipe.types;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class ShapelessResearchedRecipe implements ICraftingRecipe {
	
	private final NonNullList<Ingredient> recipeItems;
	private final ItemStack recipeOutput;
	private final ResourceLocation id;
	private final String group;
	private final boolean isSimple;
	   
	public ShapelessResearchedRecipe(ResourceLocation idIn, String groupIn, ItemStack recipeOutputIn, NonNullList<Ingredient> recipeItemsIn) {
		this.id = idIn;
		this.group = groupIn;
		this.recipeItems = recipeItemsIn;
		this.recipeOutput = recipeOutputIn;
	    this.isSimple = recipeItemsIn.stream().allMatch(Ingredient::isSimple);
	}
	
	public String getGroup() {
		return this.group;
    }

   public NonNullList<Ingredient> getIngredients() {
      return this.recipeItems;
   }
	
	@Override
	public boolean matches(CraftingInventory inv, World worldIn) {
		RecipeItemHelper recipeitemhelper = new RecipeItemHelper();
	      java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
	      int i = 0;

	      for(int j = 0; j < inv.getSizeInventory(); ++j) {
	         ItemStack itemstack = inv.getStackInSlot(j);
	         if (!itemstack.isEmpty()) {
	            ++i;
	            if (isSimple)
	            recipeitemhelper.func_221264_a(itemstack, 1);
	            else inputs.add(itemstack);
	         }
	      }

	      return i == this.recipeItems.size() && (isSimple ? recipeitemhelper.canCraft(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.recipeItems) != null);
	   
	}

	@Override
	public ItemStack getCraftingResult(CraftingInventory inv) {
	      return this.getRecipeOutput().copy();
	}

	@Override
	public boolean canFit(int width, int height) {
	      return width * height >= this.recipeItems.size();
	}

	@Override
	public ItemStack getRecipeOutput() {
	      return this.getOuputRecipe().copy();
	}
	
	public ItemStack getOuputRecipe() {
		return this.recipeOutput;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return ModRecipe.CRAFTING_SHAPED_RESEARCHED;
	}

   @VisibleForTesting
   static String[] shrink(String... toShrink) {
      int i = Integer.MAX_VALUE;
      int j = 0;
      int k = 0;
      int l = 0;

      for(int i1 = 0; i1 < toShrink.length; ++i1) {
         String s = toShrink[i1];
         i = Math.min(i, firstNonSpace(s));
         int j1 = lastNonSpace(s);
         j = Math.max(j, j1);
         if (j1 < 0) {
            if (k == i1) {
               ++k;
            }

            ++l;
         } else {
            l = 0;
         }
      }

      if (toShrink.length == l) {
         return new String[0];
      } else {
         String[] astring = new String[toShrink.length - l - k];

         for(int k1 = 0; k1 < astring.length; ++k1) {
            astring[k1] = toShrink[k1 + k].substring(i, j + 1);
         }

         return astring;
      }
   }

   private static int firstNonSpace(String str) {
      int i;
      for(i = 0; i < str.length() && str.charAt(i) == ' '; ++i) {
         ;
      }

      return i;
   }

   private static int lastNonSpace(String str) {
      int i;
      for(i = str.length() - 1; i >= 0 && str.charAt(i) == ' '; --i) {
         ;
      }

      return i;
   }

   public static ItemStack deserializeItem(JsonObject p_199798_0_) {
      String s = JSONUtils.getString(p_199798_0_, "item");
      if(!ForgeRegistries.ITEMS.containsKey(new ResourceLocation(s))) {
	         throw new JsonSyntaxException("Unknown item '" + s + "'");
      }
      if (p_199798_0_.has("data")) {
         throw new JsonParseException("Disallowed data tag found");
      } else {
         //int i = JSONUtils.getInt(p_199798_0_, "count", 1);
         return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(p_199798_0_, true);
      }
   }
	
   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>>  implements IRecipeSerializer<ShapelessResearchedRecipe> {

		public Serializer() {
			setRegistryName("fantasy20:shapeless_researched_recipe");
		}
		
		public ShapelessResearchedRecipe read(ResourceLocation recipeId, JsonObject json) {
	         String s = JSONUtils.getString(json, "group", "");
	         NonNullList<Ingredient> nonnulllist = readIngredients(JSONUtils.getJsonArray(json, "ingredients"));
	         if (nonnulllist.isEmpty()) {
	            throw new JsonParseException("No ingredients for shapeless recipe");
	         } else if (nonnulllist.size() > ShapedResearchedRecipe.MAX_WIDTH * ShapedResearchedRecipe.MAX_HEIGHT) {
	            throw new JsonParseException("Too many ingredients for shapeless recipe the max is " + (ShapedResearchedRecipe.MAX_WIDTH * ShapedResearchedRecipe.MAX_HEIGHT));
	         } else {
	            ItemStack itemstack = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
	            return new ShapelessResearchedRecipe(recipeId, s, itemstack, nonnulllist);
	         }
	      }

	      private static NonNullList<Ingredient> readIngredients(JsonArray p_199568_0_) {
	         NonNullList<Ingredient> nonnulllist = NonNullList.create();

	         for(int i = 0; i < p_199568_0_.size(); ++i) {
	            Ingredient ingredient = Ingredient.deserialize(p_199568_0_.get(i));
	            if (!ingredient.hasNoMatchingItems()) {
	               nonnulllist.add(ingredient);
	            }
	         }

	         return nonnulllist;
	      }

	      public ShapelessResearchedRecipe read(ResourceLocation recipeId, PacketBuffer buffer) {
	         String s = buffer.readString(32767);
	         int i = buffer.readVarInt();
	         NonNullList<Ingredient> nonnulllist = NonNullList.withSize(i, Ingredient.EMPTY);

	         for(int j = 0; j < nonnulllist.size(); ++j) {
	            nonnulllist.set(j, Ingredient.read(buffer));
	         }

	         ItemStack itemstack = buffer.readItemStack();
	         return new ShapelessResearchedRecipe(recipeId, s, itemstack, nonnulllist);
	      }

	      public void write(PacketBuffer buffer, ShapelessResearchedRecipe recipe) {
	         buffer.writeString(recipe.getGroup());
	         buffer.writeVarInt(recipe.getIngredients().size());

	         for(Ingredient ingredient : recipe.getIngredients()) {
	            ingredient.write(buffer);
	         }

	         buffer.writeItemStack(recipe.getRecipeOutput());
	      }

	}
 

}
