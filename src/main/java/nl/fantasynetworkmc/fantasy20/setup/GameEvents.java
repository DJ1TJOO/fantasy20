package nl.fantasynetworkmc.fantasy20.setup;

import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nl.fantasynetworkmc.fantasy20.Fantasy20;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLock;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockTile;
import nl.fantasynetworkmc.fantasy20.blocks.researchtable.ResearchTable;
import nl.fantasynetworkmc.fantasy20.capabilities.research.CapabilityResearchProvider;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.FORGE)
public class GameEvents {
	
    @SubscribeEvent
    public static void onBreakEvent(BlockEvent.BreakEvent event) {
    	Block block = event.getState().getBlock();
    	if(block instanceof DoorLock) {
			//System.err.println(((DoorLockTile) event.getWorld().getTileEntity(event.getPos())).getOwner());
			//System.err.println(Fantasy20.proxy.getClientPlayer().getUniqueID());
			//System.err.println(event.getPlayer().getUniqueID());
			TileEntity t1 = event.getWorld().getTileEntity(event.getPos());
			TileEntity t2 = event.getWorld().getTileEntity(event.getPos().add(0, -1, 0));
    		if(t1 instanceof DoorLockTile && !(t2 instanceof DoorLockTile)) {
    			if(!((DoorLockTile) t1).getOwner().equals(event.getPlayer().getUniqueID())) {
        			event.setCanceled(true);
        		}
    		} else if(t1 instanceof DoorLockTile && t2 instanceof DoorLockTile) {
    			if(!((DoorLockTile) t1).getOwner().equals(event.getPlayer().getUniqueID()) && !((DoorLockTile) t2).getOwner().equals(event.getPlayer().getUniqueID())) {
        			event.setCanceled(true);
        		}
    		}
    	} else {
			TileEntity t1 = event.getWorld().getTileEntity(event.getPos().add(0, 1, 0));
			if(t1 instanceof DoorLockTile) {
    			if(!((DoorLockTile) t1).getOwner().equals(event.getPlayer().getUniqueID())) {
        			event.setCanceled(true);
        		}
    		} 
    	}
    }
	
	@SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Entity> event){
    	//Fantasy20.LOGGER.info("attachCapabilities" + event.getObject().getEntityString());
        if(event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(Fantasy20.MODID, "capability_research"), new CapabilityResearchProvider());
        }
    }
    
    @SubscribeEvent
    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
       if(event.isCanceled()) {
    	   return;
       }
       event.getPlayer().getCapability(CapabilityResearchProvider.RESEARCH_CAPABILITY, null).ifPresent(r -> {
    	   System.err.println("dd");
    	   if(ResearchTable.getRecipe(event.getCrafting()) != null) {
    		   if(!r.getResearched().contains(event.getCrafting().getItem())) {
    			   event.getPlayer().sendMessage(new StringTextComponent("Je hebt het item " + event.getCrafting().getDisplayName().getFormattedText() + " nog niet geresearched!"));
    			   event.setCanceled(true);
    		   }
    	   }
       });
       //event.getPlayer()
    }
    
    @SubscribeEvent
    public static void openGui(GuiOpenEvent event) {
       if(event.getGui() instanceof MainMenuScreen) {
    	 //  MainMenuScreen main = (MainMenuScreen)event.getGui();
    	 //  event.setGui(new CustomMainScreen(main));
       }
	   //System.err.println("GuiOpenEvent FIRED: " + event.getGui().getTitle());
    }
}
