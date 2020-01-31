package nl.fantasynetworkmc.fantasy20.blocks.researchtable;

import java.util.UUID;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;
import nl.fantasynetworkmc.fantasy20.items.ModItems;
import nl.fantasynetworkmc.fantasy20.items.Scrap;

public class ResearchTableTile extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	private LazyOptional<IItemHandler> handler = LazyOptional.of(() -> getCreateHandler());
	public final int INPUT_SLOT = 0; //scrap
	public final int INPUT_SLOT_2 = 1; // item
	public final int OUTPUT_SLOT = 2; // blueprint
	public final int INPUT_SLOT_3 = 3; //scrap
	public final int INPUT_SLOT_4 = 4; //scrap
	public final int INPUT_SLOT_5 = 5; //scrap
	private int completed = 0;
	private ResearchTableRecipe currentRecipe = null;
	private UUID who = null;
	
	public ResearchTableTile() {
		super(ModBlocks.RESEARCH_TABLE_TILE);
	}
	
	int second = 0;
	int timer = 0;
	
	@Override
	public void tick() {
		if(world.isRemote) {//client
			//Fantasy20.LOGGER.info("ResearchTableTile.tick-client");
		} else {//server
			handler.ifPresent(h -> {
				second++;
				if(second > 19) {
					second = 0;
				//Fantasy20.LOGGER.info("ResearchTableTile.tick-server");
				if(currentRecipe == null) {
						if(h.getStackInSlot(INPUT_SLOT).getItem().equals(ModItems.SCRAP) || h.getStackInSlot(INPUT_SLOT_3).getItem().equals(ModItems.SCRAP) || h.getStackInSlot(INPUT_SLOT_5).getItem().equals(ModItems.SCRAP) || h.getStackInSlot(INPUT_SLOT_4).getItem().equals(ModItems.SCRAP)) {
							ResearchTableRecipe recipe = ResearchTable.getRecipe(h.getStackInSlot(INPUT_SLOT_2));
							if(recipe != null) {
								if(h.getStackInSlot(INPUT_SLOT).getCount() + h.getStackInSlot(INPUT_SLOT_3).getCount() + h.getStackInSlot(INPUT_SLOT_4).getCount() + h.getStackInSlot(INPUT_SLOT_5).getCount() >= recipe.getScrap()) {
									second = 0;
									timer = 0;
									completed = 0;
									currentRecipe = recipe.copy();
									markDirty();
									world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
								}
							}
						}
				} else {
						if(h.getStackInSlot(INPUT_SLOT).getCount() + h.getStackInSlot(INPUT_SLOT_3).getCount() + h.getStackInSlot(INPUT_SLOT_4).getCount() + h.getStackInSlot(INPUT_SLOT_5).getCount() < currentRecipe.getScrap() || !h.getStackInSlot(INPUT_SLOT_2).getItem().equals(currentRecipe.getInputItem())) {
							currentRecipe = null;
							second = 0;
							timer = 0;
							completed = 0;
							markDirty();
							world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
						} else {
							timer++;
							//System.err.println(timer/100);
						//	System.err.println(timer/currentRecipe.getTime() * 100);
							if(timer > currentRecipe.getTime()) {
								int left = currentRecipe.getScrap();
								if(h.getStackInSlot(INPUT_SLOT).getCount() > left) {
									h.extractItem(INPUT_SLOT, left, false);
									left -= left;
								} else if(left > 0) {
									int canExtract = Math.min(left, h.getStackInSlot(INPUT_SLOT).getCount());
									h.extractItem(INPUT_SLOT, canExtract, false);
									left -= canExtract;
								}
								if(h.getStackInSlot(INPUT_SLOT_3).getCount() > left) {
									h.extractItem(INPUT_SLOT_3, left, false);
									left -= left;
								} else if(left > 0){
									int canExtract = Math.min(left, h.getStackInSlot(INPUT_SLOT_3).getCount());
									h.extractItem(INPUT_SLOT_3, canExtract, false);
									left -= canExtract;
								}
								if(h.getStackInSlot(INPUT_SLOT_4).getCount() > left) {
									h.extractItem(INPUT_SLOT_4, left, false);
									left -= left;
								} else if(left > 0) {
									int canExtract = Math.min(left, h.getStackInSlot(INPUT_SLOT_4).getCount());
									h.extractItem(INPUT_SLOT_4, canExtract, false);
									left -= canExtract;
								}
								if(h.getStackInSlot(INPUT_SLOT_5).getCount() > left) {
									h.extractItem(INPUT_SLOT_5, left, false);
									left -= left;
								} else if(left > 0) {
									int canExtract = Math.min(left, h.getStackInSlot(INPUT_SLOT_5).getCount());
									h.extractItem(INPUT_SLOT_5, canExtract, false);
									left -= canExtract;
								}
								h.extractItem(INPUT_SLOT_2, 1, false);
								h.insertItem(OUTPUT_SLOT, currentRecipe.getResultBlueprint(), false);
								//System.err.println(" " + currentRecipe.toString());
								
								currentRecipe = null;
								second = 0;
								timer = 0;
								completed = 0;
							}
							if(currentRecipe != null) {
								completed = (int)(((double)((double)timer/(double)currentRecipe.getTime())) * 100);
								//System.err.println(completed);
							}
							//PacketHandler.INSTANCE.send(PacketDistributor.ALL, this.getUpdatePacket());
							markDirty();
							world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), 2);
						}
					}
				}
				});
		}
	}
	
	@Override
	public void handleUpdateTag(CompoundNBT tag) {
		super.handleUpdateTag(tag);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public void read(CompoundNBT tag) {
		CompoundNBT invTag = tag.getCompound("inv");
		handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>)h).deserializeNBT(invTag));
		CompoundNBT data = tag.getCompound("data");
		if(data.contains("currentrecipe")) {
			currentRecipe = ResearchTableRecipe.deserialize(data.getCompound("currentrecipe"));
		} else {
			currentRecipe = null;
		}
		who = data.getUniqueId("who");
		completed = data.getInt("completed");
		super.read(tag);
	}

	@SuppressWarnings("unchecked")
	@Override
	public CompoundNBT write(CompoundNBT tag) {
		handler.ifPresent(h -> {
				CompoundNBT compound = ((INBTSerializable<CompoundNBT>)h).serializeNBT();
				tag.put("inv", compound);
			});
		CompoundNBT data = new CompoundNBT();
		if(currentRecipe != null) {
			data.put("currentrecipe", currentRecipe.serialize());
		}
		if(who != null) {
			data.putUniqueId("who", who);
		}
		data.putInt("completed", completed);
		tag.put("data", data);
		return super.write(tag);
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
//		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
//            return handler.cast();
//        }
		return super.getCapability(cap, side);
	}
	
	private IItemHandler getCreateHandler() {
		return new ItemStackHandler(6) {
			@Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            	if(slot == INPUT_SLOT || slot == INPUT_SLOT_3 || slot == INPUT_SLOT_4 || slot == INPUT_SLOT_5) {
            		if(stack.getItem() instanceof Scrap) {
            			return true;
            		} else {
            			return false;
            		}
            	}
            	if(slot == OUTPUT_SLOT) {
            		//System.err.println("d");
            		if(getCurrentRecipe() != null) {
                		//System.err.println("e");
            			if(stack.equals(getCurrentRecipe().getResultBlueprint())) {
                    		//System.err.println("f");
            				return true;
            			}
            		}
            		return false;
            	}
                return true;
            }
            
            @Override
            public int getSlotLimit(int slot) {
            	if(slot == INPUT_SLOT) {
            		return 1000;
            	}
            	return super.getSlotLimit(slot);
            }
            
            @Override
            public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            	return super.insertItem(slot, stack, simulate);
            }
            
            
            
            @Override
            public ItemStack extractItem(int slot, int amount, boolean simulate) {
            	return super.extractItem(slot, amount, simulate);
            }
		};
	}

	public LazyOptional<IItemHandler> getHandler() {
		return handler;
	}


	@Override
	public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
		return new ResearchTableContainer(id, world, pos, playerInventory, playerEntity);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new StringTextComponent("Research Table");
	}

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		markDirty();
		this.completed = completed;
	}

	public UUID getWho() {
		return who;
	}

	public void setWho(UUID who) {
		markDirty();
		this.who = who;
	}

	public ResearchTableRecipe getCurrentRecipe() {
		return currentRecipe;
	}

	public void setCurrentRecipe(ResearchTableRecipe currentRecipe) {
		this.currentRecipe = currentRecipe;
		markDirty();
	}
	
	
	
}
