package nl.fantasynetworkmc.fantasy20.blocks.researchtable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;

public class ResearchTableContainer extends Container {

	private ResearchTableTile tileEntity;
	private PlayerEntity playerEntity;
    private IItemHandler playerInventory;

	public ResearchTableContainer(int id, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		super(ModBlocks.RESEARCH_TABLE_CONTAINER, id);
		tileEntity = (ResearchTableTile) world.getTileEntity(pos);
		this.playerEntity = playerEntity;
        this.playerInventory = new InvWrapper(playerInventory);
        
		//tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
        tileEntity.getHandler().ifPresent(h -> {
            addSlot(new SlotItemHandler(h, tileEntity.INPUT_SLOT, 19, 33));
            addSlot(new SlotItemHandler(h, tileEntity.INPUT_SLOT_2, 55, 33));
            addSlot(new SlotItemHandler(h, tileEntity.OUTPUT_SLOT, 137, 33));
        });
        //});
        layoutPlayerInventorySlots(8, 83);
	}

	@Override
	public boolean canInteractWith(PlayerEntity player) {
		return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()), playerEntity, ModBlocks.RESEARCH_TABLE);
	}
	
	 private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0 ; i < amount ; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0 ; j < verAmount ; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }
	
	public ResearchTableTile getTileEntity() {
		return tileEntity;
	}

	public void setTileEntity(ResearchTableTile tileEntity) {
		this.tileEntity = tileEntity;
	}
}
