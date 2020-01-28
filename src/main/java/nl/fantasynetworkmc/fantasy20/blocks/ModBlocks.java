package nl.fantasynetworkmc.fantasy20.blocks;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;
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

public class ModBlocks {
	@ObjectHolder("fantasy20:research_table")
	public static ResearchTable RESEARCH_TABLE;
	@ObjectHolder("fantasy20:research_table")
	public static TileEntityType<ResearchTableTile> RESEARCH_TABLE_TILE;
	@ObjectHolder("fantasy20:research_table")
	public static ContainerType<ResearchTableContainer> RESEARCH_TABLE_CONTAINER;

	@ObjectHolder("fantasy20:doorlock")
	public static DoorLock DOORLOCK;
	@ObjectHolder("fantasy20:doorlock")
	public static TileEntityType<DoorLockTile> DOORLOCK_TILE;
	@ObjectHolder("fantasy20:doorlock")
	public static ContainerType<DoorLockContainer> DOORLOCK_CONTAINER;

	@ObjectHolder("fantasy20:metal_ore")
	public static MetalOre METAL_ORE;
	
	@ObjectHolder("fantasy20:floor_frame")
	public static FloorFrame FLOOR_FRAME;
	@ObjectHolder("fantasy20:floor_frame")
	public static TileEntityType<FloorFrameTile> FLOOR_FRAME_TILE;
	@ObjectHolder("fantasy20:wall_frame")
	public static WallFrame WALL_FRAME;
	@ObjectHolder("fantasy20:wall_frame")
	public static TileEntityType<WallFrameTile> WALL_FRAME_TILE;
}
