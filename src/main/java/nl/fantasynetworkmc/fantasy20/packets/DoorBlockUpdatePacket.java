package nl.fantasynetworkmc.fantasy20.packets;


import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockTile;

public class DoorBlockUpdatePacket {

	public static final int DOOR_BLOCK_UPDATE = 0;
	
	private final BlockPos pos;
	private final boolean open;
	
	public DoorBlockUpdatePacket(BlockPos pos, boolean open) {
		this.pos = pos;
		this.open = open;
	}
	
	public static void encode(DoorBlockUpdatePacket msg, PacketBuffer buffer) {
		buffer.writeBlockPos(msg.pos);
		buffer.writeBoolean(msg.open);
	}
	
	public static DoorBlockUpdatePacket decode(PacketBuffer buffer) {
		return new DoorBlockUpdatePacket(buffer.readBlockPos(), buffer.readBoolean());
	}
	
	public static void handle(DoorBlockUpdatePacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity sender = context.get().getSender();
			TileEntity te = sender.getServerWorld().getTileEntity(msg.pos);
			if(te instanceof DoorLockTile) {
				DoorLockTile dlt = (DoorLockTile) te;
				dlt.getDoorlock().toggleDoor(sender.getServerWorld(), msg.pos, msg.open);
			}
		});
		context.get().setPacketHandled(true);
	}
}
