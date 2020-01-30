package nl.fantasynetworkmc.fantasy20.blocks.doorlock;

import javax.annotation.Nullable;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import nl.fantasynetworkmc.fantasy20.packets.DoorBlockUpdatePacket;
import nl.fantasynetworkmc.fantasy20.packets.PacketHandler;

public class DoorLock extends DoorLockBlock {

	public DoorLock() {
		super(Properties.create(Material.IRON)
				.sound(SoundType.METAL)
				.hardnessAndResistance(25.0f, 18000000.0f)
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(3)
				.lightValue(0));
		setRegistryName("fantasy20:doorlock");
	}
	
	@Override
	public void onNeighborChange(BlockState state, IWorldReader world, BlockPos pos, BlockPos neighbor) {
		
	}
	
	public PushReaction getPushReaction(final BlockState p_149656_1_) {
        return PushReaction.IGNORE;
    }
	
	@Override
	public boolean canConnectRedstone(BlockState state, IBlockReader world, BlockPos pos, Direction side) {
		return false;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, BlockState p_180633_3_,
			LivingEntity placer, ItemStack p_180633_5_) {
		TileEntity tile = world.getTileEntity(pos);
		//System.err.println("a");
		if(tile instanceof DoorLockTile) {
		//	System.err.println("b");
			DoorLockTile doortile = (DoorLockTile) tile;
			if(placer instanceof PlayerEntity) {
			//	System.err.println("c");
				doortile.setOwner(((PlayerEntity)placer).getUniqueID());
				//System.err.println(doortile.getOwner());
				doortile.markDirty();
			}
		}
		super.onBlockPlacedBy(world, pos, p_180633_3_, placer, p_180633_5_);
	}
	
	@Nullable
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		//Fantasy20.LOGGER.info("ResearchTable.createTileEntity");
		DoorLockTile tile = new DoorLockTile();
		return tile;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos,
			PlayerEntity player, Hand p_220051_5_, BlockRayTraceResult p_220051_6_) {
		if(!worldIn.isRemote) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			if(tileEntity instanceof DoorLockTile) {
				DoorLockTile dlt = (DoorLockTile) tileEntity;
				if(worldIn.getTileEntity(pos.add(0, -1, 0)) instanceof DoorLockTile) {
					dlt = (DoorLockTile) worldIn.getTileEntity(pos.add(0, -1, 0));
				}
				if(player.isSneaking()) {
					if(dlt instanceof INamedContainerProvider) {
						NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) dlt, dlt.getPos());
					}
				} else {
					if(dlt.getUsercodes().containsKey(player.getUniqueID())) {
						if(dlt.getCode().equals(dlt.getUsercodes().get(player.getUniqueID()))) {
							if((boolean)state.get((IProperty)DoorBlock.OPEN)) {
								PacketHandler.INSTANCE.sendToServer(new DoorBlockUpdatePacket(pos, false));
							} else {
								PacketHandler.INSTANCE.sendToServer(new DoorBlockUpdatePacket(pos, true));
							}
						} else {
							dlt.getUsercodes().remove(player.getUniqueID());
							dlt.markDirty();
							NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) dlt, dlt.getPos());
						}
					} else {
						NetworkHooks.openGui((ServerPlayerEntity) player, (INamedContainerProvider) dlt, dlt.getPos());
					}
				}
			}
		}
		return true;
	}

}
