package nl.fantasynetworkmc.fantasy20.capabilities.research;

import java.util.ArrayList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullSupplier;
import net.minecraftforge.registries.ForgeRegistries;

public class CapabilityResearchProvider implements ICapabilitySerializable<INBT>, ICapabilityProvider {

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
		}, CapabilityResearch::new);
    }
    
    private final LazyOptional<CapabilityResearch> holder = LazyOptional.of(CapabilityResearch::new);
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    	//System.err.println("d");
        return cap == RESEARCH_CAPABILITY ? holder.cast() : LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        NonNullSupplier<CapabilityResearch> nonNullSupplier = new NonNullSupplier<CapabilityResearch>() {
            @Nonnull
            @Override
            public CapabilityResearch get() {
                return null;
            }
        };
        return RESEARCH_CAPABILITY.getStorage().writeNBT(RESEARCH_CAPABILITY, holder.orElseGet(nonNullSupplier), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        NonNullSupplier<CapabilityResearch> nonNullSupplier = new NonNullSupplier<CapabilityResearch>() {
            @Nonnull
            @Override
            public CapabilityResearch get() {
                return null;
            }
        };
        RESEARCH_CAPABILITY.getStorage().readNBT(RESEARCH_CAPABILITY, holder.orElseGet(nonNullSupplier), null, nbt);
    }

}
