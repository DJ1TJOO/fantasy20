package nl.fantasynetworkmc.fantasy20.capabilities.research;

import java.util.List;

import net.minecraft.item.Item;

public interface IResearchCapability {

	List<Item> getResearched();
	
	void setResearched(List<Item> researched);
	
}
