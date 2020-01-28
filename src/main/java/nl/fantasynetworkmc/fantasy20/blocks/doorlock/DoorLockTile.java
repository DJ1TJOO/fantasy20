package nl.fantasynetworkmc.fantasy20.blocks.doorlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;

public class DoorLockTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {
	
	private UUID owner;
	private List<Integer> code;
	private HashMap<UUID, List<Integer>> usercodes;
	
	public DoorLockTile() {
		super(ModBlocks.DOORLOCK_TILE);
		//this.owner = UUID.fromString(this.getTileData().getString("playerName"));
		this.owner = UUID.randomUUID();
		usercodes = new HashMap<UUID, List<Integer>>();
		List<Integer> code = new ArrayList<Integer>();
		code.add(0);
		code.add(1);
		code.add(2);
		code.add(3);
		code.add(4);
		this.code = code;
		markDirty();
	}

	@Override
	public void tick() {
		if(world.isRemote) {//client
			//Fantasy20.LOGGER.info("ResearchTableTile.tick-client");
		} else {//server
			//Fantasy20.LOGGER.info("ResearchTableTile.tick-server" + " " + toggleDoorServer);
		}
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
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		read(pkt.getNbtCompound());
		super.onDataPacket(net, pkt);
	}
	
	@Override
	public void read(CompoundNBT tag) {
		CompoundNBT data = tag.getCompound("data");
		this.owner = data.getUniqueId("owner");
		
		CompoundNBT code = data.getCompound("code");
		this.code = new ArrayList<Integer>();
		for (String number : code.keySet()) {
			this.code.add(code.getInt(number));
		}
		
		this.usercodes = new HashMap<UUID, List<Integer>>();
		CompoundNBT usercodes = data.getCompound("usercodes");
		//System.err.println("read usercodes");
		for (String key : usercodes.keySet()) {
			UUID uuid = UUID.fromString(key);
			UUID uuidKey = usercodes.getCompound(key).getUniqueId("key");
			//System.err.println(key + " - " + uuid.toString() + " - " + uuidKey.toString());
			if(!uuid.equals(uuidKey)) {
				continue;
			}
			//System.err.println("d");
			CompoundNBT usercode = usercodes.getCompound(key).getCompound("value");
			List<Integer> usercodeList = new ArrayList<Integer>();
			for (String number : usercode.keySet()) {
				usercodeList.add(usercode.getInt(number));
			}
			this.usercodes.put(uuid, usercodeList);
		}
		//System.err.println(this.code.toString());
		super.read(tag);
	}

	@Override
	public CompoundNBT write(CompoundNBT tag) {
		CompoundNBT data = new CompoundNBT();
		data.putUniqueId("owner", owner);
		CompoundNBT code = new CompoundNBT();
		for (int i = 0; i < this.code.size(); i++) {
			code.putInt("" + i, this.code.get(i));
		}
		data.put("code", code);
		CompoundNBT usercodes = new CompoundNBT();
		//System.err.println("write usercodes");
		for (UUID uuid : this.usercodes.keySet()) {
			//System.err.println(uuid.toString());
			CompoundNBT uuidNbt = new CompoundNBT();
			CompoundNBT usercode = new CompoundNBT();
			for (int i = 0; i < this.usercodes.get(uuid).size(); i++) {
				usercode.putInt("" + i, this.usercodes.get(uuid).get(i));
			}
			uuidNbt.put("value", usercode);
			uuidNbt.putUniqueId("key", uuid);
			usercodes.put(uuid.toString(), uuidNbt);
		}
		data.put("usercodes", usercodes);
		tag.put("data", data);
		return super.write(tag);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		
		return super.getCapability(cap, side);
	}


	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new DoorLockContainer(id, world, pos, playerInventory, playerEntity);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Doorlock");
	}
	
	public UUID getOwner() {
		return owner;
	}

	public void setOwner(UUID owner) {
		this.owner = owner;
		markDirty();
	}

	public List<Integer> getCode() {
		return code;
	}

	public void setCode(List<Integer> code) {
		this.code = code;
		markDirty();
	}

	public DoorLock getDoorlock() {
		return getPos() == null ? null : (DoorLock)getWorld().getBlockState(getPos()).getBlock();
	}

	public HashMap<UUID, List<Integer>> getUsercodes() {
		return usercodes;
	}

	public void setUsercodes(HashMap<UUID, List<Integer>> usercodes) {
		this.usercodes = usercodes;
	}
	
}
