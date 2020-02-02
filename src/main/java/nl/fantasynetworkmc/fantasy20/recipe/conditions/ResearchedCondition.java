package nl.fantasynetworkmc.fantasy20.recipe.conditions;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.ICondition;
import nl.fantasynetworkmc.fantasy20.Fantasy20;

public class ResearchedCondition implements ICondition {

    private static final ResourceLocation NAME = new ResourceLocation(Fantasy20.MODID, "researched_condition");
    @SuppressWarnings("unused")
	private final ResourceLocation item;

    public ResearchedCondition(String location) {
        this(new ResourceLocation(location));
    }

    public ResearchedCondition(String namespace, String path) {
        this(new ResourceLocation(namespace, path));
    }

    public ResearchedCondition(ResourceLocation item) {
        this.item = item;
    }
    
	@Override
	public ResourceLocation getID() {
		return NAME;
	}

	@Override
	public boolean test() {
		return false;
	}

}
