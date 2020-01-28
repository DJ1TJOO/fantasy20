package nl.fantasynetworkmc.fantasy20.blocks.building.wallframe;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import nl.fantasynetworkmc.fantasy20.FloorTypes;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;

public class WallFrameTile extends TileEntity {

	private FloorTypes floor_type;
	
	public WallFrameTile() {
		super(ModBlocks.FLOOR_FRAME_TILE);
		floor_type = FloorTypes.NONE;
	}
	
	@Override
	public CompoundNBT getUpdateTag() {
		CompoundNBT tag = new CompoundNBT();
		return write(tag);
	}
	
	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		CompoundNBT tag = new CompoundNBT();
		write(tag);
		return new SUpdateTileEntityPacket(getPos(), 0, tag);
	}
	
	@Override
	public void read(CompoundNBT tag) {
		CompoundNBT data = tag.getCompound("data");
		floor_type = FloorTypes.valueOf(data.getString("type"));
		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		CompoundNBT data = new CompoundNBT();
		data.putString("type", floor_type.toString());
		tag.put("data", data);
		return super.write(tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		read(pkt.getNbtCompound());
		super.onDataPacket(net, pkt);
	}

	public FloorTypes getFloorType() {
		return floor_type;
	}

	public void setFloorType(FloorTypes type) {
		this.floor_type = type;
	}

}
