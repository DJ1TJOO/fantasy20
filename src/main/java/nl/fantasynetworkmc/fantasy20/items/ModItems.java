package nl.fantasynetworkmc.fantasy20.items;

import net.minecraftforge.registries.ObjectHolder;
import nl.fantasynetworkmc.fantasy20.items.building.frame.panels.MetalPanel;
import nl.fantasynetworkmc.fantasy20.items.building.frame.panels.StonePanel;
import nl.fantasynetworkmc.fantasy20.items.building.frame.panels.WoodenPanel;
import nl.fantasynetworkmc.fantasy20.items.building.frame.upgrades.MetalFrameUpgrade;
import nl.fantasynetworkmc.fantasy20.items.building.frame.upgrades.StoneFrameUpgrade;

public class ModItems {

	@ObjectHolder("fantasy20:scrap")
	public static Scrap SCRAP;
	
	@ObjectHolder("fantasy20:blueprint")
	public static Blueprint BLUEPRINT;

	@ObjectHolder("fantasy20:metal")
	public static Metal METAL;
	
	@ObjectHolder("fantasy20:wooden_panel")
	public static WoodenPanel WOODEN_PANEL;
	@ObjectHolder("fantasy20:stone_panel")
	public static StonePanel STONE_PANEL;
	@ObjectHolder("fantasy20:metal_panel")
	public static MetalPanel METAL_PANEL;
	
	@ObjectHolder("fantasy20:stone_frame_upgrade")
	public static StoneFrameUpgrade STONE_FRAME_UPGRADE;
	@ObjectHolder("fantasy20:metal_frame_upgrade")
	public static MetalFrameUpgrade METAL_FRAME_UPGRADE;
}
