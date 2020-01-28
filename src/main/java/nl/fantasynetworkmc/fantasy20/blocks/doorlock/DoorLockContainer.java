package nl.fantasynetworkmc.fantasy20.blocks.doorlock;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;

public class DoorLockContainer extends Container {

	private DoorLockTile tileEntity;
	private PlayerEntity playerEntity;

	public DoorLockContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		super(ModBlocks.DOORLOCK_CONTAINER, id);
		tileEntity = (DoorLockTile) world.getTileEntity(pos);
		this.setPlayerEntity(playerEntity);
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), getPlayerEntity(), ModBlocks.DOORLOCK);
	}
	
	@Override
	public ItemStack transferStackInSlot(PlayerEntity player, int index) {
		ItemStack returnStack = ItemStack.EMPTY;
		final Slot slot = this.inventorySlots.get(index);
		if (slot != null && slot.getHasStack()) {
			final ItemStack slotStack = slot.getStack();
			returnStack = slotStack.copy();

			final int containerSlots = this.inventorySlots.size() - player.inventory.mainInventory.size();
			if (index < containerSlots) {
				if (!mergeItemStack(slotStack, containerSlots, this.inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(slotStack, 0, containerSlots, false)) {
				return ItemStack.EMPTY;
			}
			if (slotStack.getCount() == 0) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if (slotStack.getCount() == returnStack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(player, slotStack);
		}
		return returnStack;
	}
	
	public DoorLockTile getTileEntity() {
		return tileEntity;
	}

	public void setTileEntity(DoorLockTile tileEntity) {
		this.tileEntity = tileEntity;
	}

	public PlayerEntity getPlayerEntity() {
		return playerEntity;
	}

	public void setPlayerEntity(PlayerEntity playerEntity) {
		this.playerEntity = playerEntity;
	}
}
