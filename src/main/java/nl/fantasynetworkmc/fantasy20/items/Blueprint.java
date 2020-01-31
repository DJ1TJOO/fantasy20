package nl.fantasynetworkmc.fantasy20.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import nl.fantasynetworkmc.fantasy20.capabilities.research.CapabilityResearch;

public class Blueprint extends Item {

	public Blueprint(Properties properties) {
		super(properties);
		setRegistryName("fantasy20:blueprint");
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
	       System.err.println("A");
	       if(playerIn.getHeldItem(handIn).getItem() instanceof Blueprint) {
			   System.err.println("a");
	    	   if(!playerIn.getHeldItem(handIn).hasTag()) {
	    		   return super.onItemRightClick(worldIn, playerIn, handIn);
	    	   }
	    	   CompoundNBT tag = playerIn.getHeldItem(handIn).getTag();
	    	   if(!tag.contains("data")) {
	    		   return super.onItemRightClick(worldIn, playerIn, handIn);
	    	   }
	    	   if(!tag.getCompound("data").contains("item")) {
	    		   return super.onItemRightClick(worldIn, playerIn, handIn);
	    	   }
	    	   String itemString = tag.getCompound("data").getString("item");
			   System.err.println("b");
	    	   playerIn.getCapability(CapabilityResearch.RESEARCH_CAPABILITY, playerIn.getHorizontalFacing()).ifPresent(r -> {
	    		   System.err.println("c");
	    		   Item item = ForgeRegistries.ITEMS.getValue(ResourceLocation.create(itemString, ':'));
	    		   System.err.println("d");
	    		   if(!r.getResearched().contains(item)) {
	    			   r.getResearched().add(item);
	    			   playerIn.sendMessage(new StringTextComponent("Je hebt het item " + item.getName()));
	    		   }
	    	   });
	       } 
	    return new ActionResult<>(ActionResultType.PASS, playerIn.getHeldItem(handIn));
		
	}
		
}
