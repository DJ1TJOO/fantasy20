package nl.fantasynetworkmc.fantasy20.packets;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import nl.fantasynetworkmc.fantasy20.blocks.researchtable.ResearchTableTile;

public class ResearchTableCompletedUpdatePacket {

	public static final int RESEARCH_TABLE_COMPLETED_UPDATE = 3;
	
	private final BlockPos pos;
	private final int completed;
	
	public ResearchTableCompletedUpdatePacket(BlockPos pos, int completed) {
		this.pos = pos;
		this.completed = completed;
	}
	
	public static void encode(ResearchTableCompletedUpdatePacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.pos);
		buffer.writeByte((byte)msg.completed);
	}
	
	public static ResearchTableCompletedUpdatePacket decode(PacketBuffer buffer) {
		return new ResearchTableCompletedUpdatePacket(buffer.readBlockPos(), buffer.readUnsignedByte());
	}
	
	public static void handle(ResearchTableTile msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
//			TileEntity te = **WORLD**.getTileEntity(msg.pos);
//			if(te instanceof ResearchTableTile) {
//				ResearchTableTile dtt = (ResearchTableTile) te;
//				
//			}
		});
		context.get().setPacketHandled(true);
	}
	
}
