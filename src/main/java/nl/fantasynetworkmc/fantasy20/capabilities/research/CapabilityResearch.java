package nl.fantasynetworkmc.fantasy20.capabilities.research;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.registries.ForgeRegistries;

public class CapabilityResearch {

    @CapabilityInject(IResearchCapability.class)
    public static Capability<IResearchCapability> RESEARCH_CAPABILITY = null;
    
    public static void register() {
    	CapabilityManager.INSTANCE.register(IResearchCapability.class, new Capability.IStorage<IResearchCapability>() {

			@Override
			public INBT writeNBT(Capability<IResearchCapability> capability, IResearchCapability instance,
					Direction side) {
				ListNBT researchedNbtList = new ListNBT();
		        for (Item item : instance.getResearched()) {
		            CompoundNBT itemNbt = new CompoundNBT();
		            itemNbt.putString("registryName", item.getRegistryName().toString());
					researchedNbtList.add(itemNbt);
				}
				return researchedNbtList;
			}

			@Override
			public void readNBT(Capability<IResearchCapability> capability, IResearchCapability instance,
					Direction side, INBT base) {
				ListNBT researchedNbtList = (ListNBT) base;
				instance.setResearched(new ArrayList<Item>());
				for (int i = 0; i < researchedNbtList.size(); i++) {
					CompoundNBT itemNbt = researchedNbtList.getCompound(i);
					instance.getResearched().add(ForgeRegistries.ITEMS.getValue(ResourceLocation.create(itemNbt.getString("registryName"), ':')));
				}
			}
		}, Research::new);
    }

}
