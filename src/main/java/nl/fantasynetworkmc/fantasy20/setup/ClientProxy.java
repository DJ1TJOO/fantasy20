package nl.fantasynetworkmc.fantasy20.setup;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;
import nl.fantasynetworkmc.fantasy20.blocks.building.floorframe.FloorFrameRenderer;
import nl.fantasynetworkmc.fantasy20.blocks.building.floorframe.FloorFrameTile;
import nl.fantasynetworkmc.fantasy20.blocks.building.wallframe.WallFrameRenderer;
import nl.fantasynetworkmc.fantasy20.blocks.building.wallframe.WallFrameTile;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.screen.DoorLockScreen;
import nl.fantasynetworkmc.fantasy20.blocks.researchtable.ResearchTableScreen;

public class ClientProxy implements IProxy {

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	@Override
	public PlayerEntity getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	@Override
	public void init() {
		ScreenManager.registerFactory(ModBlocks.RESEARCH_TABLE_CONTAINER, ResearchTableScreen::new);
		ScreenManager.registerFactory(ModBlocks.DOORLOCK_CONTAINER, DoorLockScreen::new);
		
		ClientRegistry.bindTileEntitySpecialRenderer(FloorFrameTile.class, new FloorFrameRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(WallFrameTile.class, new WallFrameRenderer());
	}

}
