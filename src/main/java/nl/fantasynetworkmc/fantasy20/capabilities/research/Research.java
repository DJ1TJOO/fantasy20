package nl.fantasynetworkmc.fantasy20.capabilities.research;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ForgeRegistries;

public class Research implements IResearchCapability, INBTSerializable<CompoundNBT> {

	private List<Item> researched;
	
	public Research() {
		researched = new ArrayList<Item>();
	}
	
	@Override
	public CompoundNBT serializeNBT() {
		CompoundNBT tag = new CompoundNBT();
        ListNBT researchedNbtList = new ListNBT();
        for (Item item : researched) {
            CompoundNBT itemNbt = new CompoundNBT();
            itemNbt.putString("registryName", item.getRegistryName().toString());
			researchedNbtList.add(itemNbt);
		}
        tag.put("researched", researchedNbtList);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundNBT tag) {
		ListNBT researchedNbtList = tag.getList("Items", Constants.NBT.TAG_COMPOUND);
		researched = new ArrayList<Item>();
		for (int i = 0; i < researchedNbtList.size(); i++) {
			CompoundNBT itemNbt = researchedNbtList.getCompound(i);
			researched.add(ForgeRegistries.ITEMS.getValue(ResourceLocation.create(itemNbt.getString("registryName"), ':')));
		}
	}

	@Override
	public List<Item> getResearched() {
		return researched;	
	}

	@Override
	public void setResearched(List<Item> researched) {
		this.researched = researched;
	}

}
