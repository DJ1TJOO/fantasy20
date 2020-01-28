package nl.fantasynetworkmc.fantasy20.items;

import net.minecraft.item.Item;

public class Scrap extends Item {

	public Scrap(Properties properties) {
		super(properties.maxStackSize(1000));
		setRegistryName("fantasy20:scrap");
	}
	
}
