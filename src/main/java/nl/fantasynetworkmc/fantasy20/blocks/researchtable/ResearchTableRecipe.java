package nl.fantasynetworkmc.fantasy20.blocks.researchtable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ResearchTableRecipe {

	private ItemStack resultBlueprint;
	private Item inputItem;
	private int scrap, time;
	
	public ResearchTableRecipe(ItemStack resultBlueprint, Item inputItem, int scrap, int time) {
		this.resultBlueprint = resultBlueprint;
		this.inputItem = inputItem;
		this.scrap = scrap;
		this.time = time;
	}
	
	public ResearchTableRecipe copy() {
		return new ResearchTableRecipe(getResultBlueprint().copy(), getInputItem(), getScrap(), getTime());
	}
	
	public CompoundNBT serialize() {
		CompoundNBT data = new CompoundNBT();
		CompoundNBT resultblueprint = new CompoundNBT();
		resultblueprint.put("nbt", this.resultBlueprint.serializeNBT());
		resultblueprint.putInt("count", this.resultBlueprint.getCount());
		resultblueprint.putInt("item", Item.getIdFromItem(this.resultBlueprint.getItem()));
		data.put("resultblueprint", resultblueprint);
		data.putInt("inputitem", Item.getIdFromItem(inputItem));
		data.putInt("scrap", scrap);
		data.putInt("time", time);
		return data;
	}
	
	public static ResearchTableRecipe deserialize(CompoundNBT data) {
		CompoundNBT resultblueprint = data.getCompound("resultblueprint");
		ItemStack resultBlueprint = new ItemStack(Item.getItemById(resultblueprint.getInt("item")), resultblueprint.getInt("count"));
		resultBlueprint.deserializeNBT(resultblueprint.getCompound("nbt"));
		Item inputItem = Item.getItemById(data.getInt("inputitem"));
		int scrap = data.getInt("scrap");
		int time = data.getInt("time");
		return new ResearchTableRecipe(resultBlueprint, inputItem, scrap, time);
	}

	public ItemStack getResultBlueprint() {
		return resultBlueprint;
	}

	public void setResultBlueprint(ItemStack resultBlueprint) {
		this.resultBlueprint = resultBlueprint;
	}

	public Item getInputItem() {
		return inputItem;
	}

	public void setInputItem(Item inputItem) {
		this.inputItem = inputItem;
	}

	public int getScrap() {
		return scrap;
	}

	public void setScrap(int scrap) {
		this.scrap = scrap;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ResearchTableRecipe [resultBlueprint=" + resultBlueprint + ", inputItem=" + inputItem + ", scrap="
				+ scrap + ", time=" + time + "]";
	}
	
}
