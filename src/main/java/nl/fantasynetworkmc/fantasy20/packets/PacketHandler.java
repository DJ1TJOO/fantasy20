package nl.fantasynetworkmc.fantasy20.packets;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import nl.fantasynetworkmc.fantasy20.Fantasy20;

public class PacketHandler {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(Fantasy20.MODID, "main"), 
			() -> PROTOCOL_VERSION, 
			PROTOCOL_VERSION::equals, 
			PROTOCOL_VERSION::equals);
	
}
