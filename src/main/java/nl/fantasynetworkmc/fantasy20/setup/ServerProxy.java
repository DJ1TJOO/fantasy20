package nl.fantasynetworkmc.fantasy20.setup;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements IProxy {

	@Override
	public World getClientWorld() {
		throw new IllegalStateException("Client only");
	}

	@Override
	public PlayerEntity getClientPlayer() {
		throw new IllegalStateException("Client only");
	}

	@Override
	public void init() {
	}

}
