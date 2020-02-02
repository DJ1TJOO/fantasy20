package nl.fantasynetworkmc.fantasy20.recipe.types;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.registries.ObjectHolder;

public class ModRecipe {

	@ObjectHolder("fantasy20:shaped_researched_recipe")
	public static IRecipeSerializer<ShapedResearchedRecipe> CRAFTING_SHAPED_RESEARCHED;
	@ObjectHolder("fantasy20:shapeless_researched_recipe")
	public static IRecipeSerializer<ShapelessResearchedRecipe> CRAFTING_SHAPELESS_RESEARCHED;
	@ObjectHolder("fantasy20:blasting_researched_recipe")
	public static IRecipeSerializer<BlastingResearchedRecipe> BLASTING_RESEARCHED;

}
