package nl.fantasynetworkmc.fantasy20.blocks.building.floorframe;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import nl.fantasynetworkmc.fantasy20.PanelTypes;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;

public class FloorFrameTile extends TileEntity {

	private PanelTypes panel_type;
	
	public FloorFrameTile() {
		super(ModBlocks.FLOOR_FRAME_TILE);
		panel_type = PanelTypes.NONE;
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
		panel_type = PanelTypes.valueOf(data.getString("type"));
		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		CompoundNBT data = new CompoundNBT();
		data.putString("type", panel_type.toString());
		tag.put("data", data);
		return super.write(tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		read(pkt.getNbtCompound());
		super.onDataPacket(net, pkt);
	}

	public PanelTypes getPanelType() {
		return panel_type;
	}

	public void setPanelType(PanelTypes type) {
		this.panel_type = type;
	}

}
