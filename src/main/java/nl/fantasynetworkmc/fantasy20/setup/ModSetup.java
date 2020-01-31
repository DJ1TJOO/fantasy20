package nl.fantasynetworkmc.fantasy20.setup;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;
import nl.fantasynetworkmc.fantasy20.blocks.researchtable.ResearchTable;
import nl.fantasynetworkmc.fantasy20.blocks.researchtable.ResearchTableRecipe;
import nl.fantasynetworkmc.fantasy20.capabilities.research.CapabilityResearch;
import nl.fantasynetworkmc.fantasy20.items.ModItems;
import nl.fantasynetworkmc.fantasy20.packets.DoorBlockUpdatePacket;
import nl.fantasynetworkmc.fantasy20.packets.DoorLockTileCodeUpdatePacket;
import nl.fantasynetworkmc.fantasy20.packets.DoorLockTileUsercodeUpdatePacket;
import nl.fantasynetworkmc.fantasy20.packets.PacketHandler;

public class ModSetup {
	
	public static ItemGroup itemGroup = new ItemGroup("Fantasy20") {
		
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModItems.BLUEPRINT);
		}
	};
	
	public void init() {
		CapabilityResearch.register();
		
		PacketHandler.INSTANCE.registerMessage(DoorBlockUpdatePacket.DOOR_BLOCK_UPDATE, DoorBlockUpdatePacket.class, DoorBlockUpdatePacket::encode, DoorBlockUpdatePacket::decode, DoorBlockUpdatePacket::handle);
		PacketHandler.INSTANCE.registerMessage(DoorLockTileCodeUpdatePacket.DOORLOCK_TILE_CODE_UPDATE, DoorLockTileCodeUpdatePacket.class, DoorLockTileCodeUpdatePacket::encode, DoorLockTileCodeUpdatePacket::decode, DoorLockTileCodeUpdatePacket::handle);
		PacketHandler.INSTANCE.registerMessage(DoorLockTileUsercodeUpdatePacket.DOORLOCK_TILE_USERCODE_UPDATE, DoorLockTileUsercodeUpdatePacket.class, DoorLockTileUsercodeUpdatePacket::encode, DoorLockTileUsercodeUpdatePacket::decode, DoorLockTileUsercodeUpdatePacket::handle);
		
		CompoundNBT tag = new CompoundNBT();
		CompoundNBT data = new CompoundNBT();
		data.putString("item", "minecraft:diamond_sword");
		tag.put("data", data);
		ItemStack blueprint = new ItemStack(ModItems.BLUEPRINT, 1);
		blueprint.setTag(tag);
		ResearchTable.recipes.add(new ResearchTableRecipe(blueprint, Items.DIAMOND_SWORD, 125, 10));

		tag = new CompoundNBT();
		data = new CompoundNBT();
		data.putString("item", "fantasy20:doorlock");
		tag.put("data", data);
		blueprint = new ItemStack(ModItems.BLUEPRINT, 1);
		blueprint.setTag(tag);
		ResearchTable.recipes.add(new ResearchTableRecipe(blueprint, ModBlocks.DOORLOCK.asItem(), 10, 10));
	}
	
}
