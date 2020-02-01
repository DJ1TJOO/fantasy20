package nl.fantasynetworkmc.fantasy20.recipe.types;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;

public interface ResearchedRecipe extends IRecipe<CraftingInventory> {

	default IRecipeType<?> getType() {
	      return IRecipeType.CRAFTING;
	   }
	
}
