package nl.fantasynetworkmc.fantasy20.setup;

import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nl.fantasynetworkmc.fantasy20.Fantasy20;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;
import nl.fantasynetworkmc.fantasy20.blocks.building.floorframe.FloorFrame;
import nl.fantasynetworkmc.fantasy20.blocks.building.floorframe.FloorFrameTile;
import nl.fantasynetworkmc.fantasy20.blocks.building.wallframe.WallFrame;
import nl.fantasynetworkmc.fantasy20.blocks.building.wallframe.WallFrameTile;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLock;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockContainer;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockTile;
import nl.fantasynetworkmc.fantasy20.blocks.ores.MetalOre;
import nl.fantasynetworkmc.fantasy20.blocks.researchtable.ResearchTable;
import nl.fantasynetworkmc.fantasy20.blocks.researchtable.ResearchTableContainer;
import nl.fantasynetworkmc.fantasy20.blocks.researchtable.ResearchTableTile;
import nl.fantasynetworkmc.fantasy20.items.Blueprint;
import nl.fantasynetworkmc.fantasy20.items.Metal;
import nl.fantasynetworkmc.fantasy20.items.Scrap;
import nl.fantasynetworkmc.fantasy20.items.panels.MetalPanel;
import nl.fantasynetworkmc.fantasy20.items.panels.StonePanel;
import nl.fantasynetworkmc.fantasy20.items.panels.WoodenPanel;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegistryEvents {
	
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> e) {
       e.getRegistry().registerAll(
    		   new ResearchTable(),
    		   new DoorLock(),
    		   new FloorFrame(),
    		   new WallFrame(),
    		   new MetalOre());
    }
    @SubscribeEvent
    public static void onItemsRegistry(final RegistryEvent.Register<Item> e) {
    	Item.Properties properties = new Item.Properties();
    	properties.group(ModSetup.itemGroup);
    	e.getRegistry().registerAll(
    		new BlockItem(ModBlocks.RESEARCH_TABLE, properties).setRegistryName("fantasy20:research_table"),
    		new BlockItem(ModBlocks.DOORLOCK, properties).setRegistryName("fantasy20:doorlock"),
    		new BlockItem(ModBlocks.FLOOR_FRAME, properties).setRegistryName("fantasy20:floor_frame"),
    		new BlockItem(ModBlocks.WALL_FRAME, properties).setRegistryName("fantasy20:wall_frame"),
    		new BlockItem(ModBlocks.METAL_ORE, properties).setRegistryName("fantasy20:metal_ore"),
    		new Blueprint(properties),
    		new Metal(properties),
    		new WoodenPanel(properties),
    		new StonePanel(properties),
    		new MetalPanel(properties),
    		new Scrap(properties));
    }
    
    @SubscribeEvent
    public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> e) {
       e.getRegistry().registerAll(
    		   TileEntityType.Builder.create(ResearchTableTile::new, ModBlocks.RESEARCH_TABLE).build(null).setRegistryName("fantasy20:research_table"),
    		   TileEntityType.Builder.create(DoorLockTile::new, ModBlocks.DOORLOCK).build(null).setRegistryName("fantasy20:doorlock"),
    		   TileEntityType.Builder.create(FloorFrameTile::new, ModBlocks.FLOOR_FRAME).build(null).setRegistryName("fantasy20:floor_frame"),
    		   TileEntityType.Builder.create(WallFrameTile::new, ModBlocks.WALL_FRAME).build(null).setRegistryName("fantasy20:wall_frame"));
    }
    
    @SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> e) {
    	e.getRegistry().registerAll(
    			IForgeContainerType.create((windowId, inv, data) -> {
    				BlockPos pos = data.readBlockPos();
    				return new ResearchTableContainer(windowId, Fantasy20.proxy.getClientWorld(), pos, inv, Fantasy20.proxy.getClientPlayer());
    			}).setRegistryName("fantasy20:research_table"),
    			IForgeContainerType.create((windowId, inv, data) -> {
    				BlockPos pos = data.readBlockPos();
    				return new DoorLockContainer(windowId, Fantasy20.proxy.getClientWorld(), pos, inv, Fantasy20.proxy.getClientPlayer());
    			}).setRegistryName("fantasy20:doorlock"));
    }
    
}
