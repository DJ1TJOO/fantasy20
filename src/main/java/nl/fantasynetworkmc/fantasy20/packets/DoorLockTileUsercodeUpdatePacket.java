package nl.fantasynetworkmc.fantasy20.packets;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockTile;

public class DoorLockTileUsercodeUpdatePacket {

	public static final int DOORLOCK_TILE_USERCODE_UPDATE = 2;
	
	private final BlockPos pos;
	private final List<Integer> code;
	private final UUID uuid;
	
	public DoorLockTileUsercodeUpdatePacket(BlockPos pos, List<Integer> code, UUID uuid) {
		this.pos = pos;
		this.uuid = uuid;
		this.code = code;
	}
	
	public static void encode(DoorLockTileUsercodeUpdatePacket msg, PacketBuffer buffer) {
		for (int code1 : msg.code) {
			buffer.writeInt(code1);
		}
		buffer.writeBlockPos(msg.pos);
		buffer.writeUniqueId(msg.uuid);
	}
	
	public static DoorLockTileUsercodeUpdatePacket decode(PacketBuffer buffer) {
		List<Integer> code = new ArrayList<Integer>();
		for (int i = 0; i < 5; i++) {
			code.add(buffer.readInt());
		}
		//System.err.println(code);
		return new DoorLockTileUsercodeUpdatePacket(buffer.readBlockPos(), code, buffer.readUniqueId());
	}
	
	public static void handle(DoorLockTileUsercodeUpdatePacket msg, Supplier<NetworkEvent.Context> context) {
		context.get().enqueueWork(() -> {
			ServerPlayerEntity sender = context.get().getSender();
			TileEntity te = sender.getServerWorld().getTileEntity(msg.pos);
			//System.err.println("d");
			if(te instanceof DoorLockTile) {
				//System.err.println(msg.code);
				DoorLockTile dlt = (DoorLockTile) te;
				dlt.getUsercodes().put(msg.uuid, msg.code);
				dlt.markDirty();
				sender.getServerWorld().setTileEntity(msg.pos, dlt);
				sender.getServerWorld().getTileEntity(msg.pos).markDirty();
			}
		});
		context.get().setPacketHandled(true);
	}
}
