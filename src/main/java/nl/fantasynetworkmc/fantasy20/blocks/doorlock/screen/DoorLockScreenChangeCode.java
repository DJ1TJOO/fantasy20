package nl.fantasynetworkmc.fantasy20.blocks.doorlock.screen;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import nl.fantasynetworkmc.fantasy20.Fantasy20;
import nl.fantasynetworkmc.fantasy20.blocks.ScreenAction;
import nl.fantasynetworkmc.fantasy20.blocks.ScreenButton;
import nl.fantasynetworkmc.fantasy20.blocks.ScreenState;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockTile;
import nl.fantasynetworkmc.fantasy20.packets.DoorLockTileCodeUpdatePacket;
import nl.fantasynetworkmc.fantasy20.packets.PacketHandler;

public class DoorLockScreenChangeCode extends ScreenState<DoorLockScreen> {

	private ResourceLocation GUI = new ResourceLocation(Fantasy20.MODID, "textures/gui/doorlock/changecode.png");
	
    private List<Integer> current_code = new ArrayList<Integer>();
    
    private boolean mouseLeftDown = false;
	
	public DoorLockScreenChangeCode(DoorLockScreen dls, FontRenderer font) {
		super(dls, font);
		getButtons().add(new ScreenButton<DoorLockScreen>(20, 64, 18, 18, 176, 18, "back", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				dls.setCurrentScreen(dls.getMainScreen());
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(134, 64, 18, 18, 176, 18, "changecode", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				if(getCurrent_code().size() == 5) {
					dls.getContainer().getTileEntity().setCode(getCurrent_code());
					PacketHandler.INSTANCE.sendToServer(new DoorLockTileCodeUpdatePacket(dls.getContainer().getTileEntity().getPos(), getCurrent_code()));
					dls.getContainer().getTileEntity().markDirty();
					if(dls.getContainer().getTileEntity().getWorld().getTileEntity(dls.getContainer().getTileEntity().getPos().add(0, -1, 0)) instanceof DoorLockTile) {
						DoorLockTile t1 = (DoorLockTile) dls.getContainer().getTileEntity().getWorld().getTileEntity(dls.getContainer().getTileEntity().getPos().add(0, -1, 0));
						t1.setCode(getCurrent_code());
						PacketHandler.INSTANCE.sendToServer(new DoorLockTileCodeUpdatePacket(dls.getContainer().getTileEntity().getPos().add(0, -1, 0), getCurrent_code()));
						t1.markDirty();
					}
					dls.setCurrentScreen(dls.getMainScreen());
				}
			}
		}));
		
		getButtons().add(new ScreenButton<DoorLockScreen>(58, 45, 18, 18, 176, 18, "7", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(7);
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(77, 45, 18, 18, 176, 18, "8", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(8);
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(96, 45, 18, 18, 176, 18, "9", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(9);
			}
		}));
		
		getButtons().add(new ScreenButton<DoorLockScreen>(58, 64, 18, 18, 176, 18, "4", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(4);
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(77, 64, 18, 18, 176, 18, "5", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(5);
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(96, 64, 18, 18, 176, 18, "6", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(6);
			}
		}));
		
		getButtons().add(new ScreenButton<DoorLockScreen>(58, 83, 18, 18, 176, 18, "1", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(1);
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(77, 83, 18, 18, 176, 18, "2", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(2);
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(96, 83, 18, 18, 176, 18, "3", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(3);
			}
		}));
		
		getButtons().add(new ScreenButton<DoorLockScreen>(58, 102, 18, 18, 176, 18, "close", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				Fantasy20.proxy.getClientPlayer().closeScreen();
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(77, 102, 18, 18, 176, 18, "0", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				addCode(0);
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(96, 102, 18, 18, 176, 18, "back", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				removeCode();
			}
		}));
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {

	}

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

	}

	@Override
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_BLEND);
		this.getSc().getMinecraft().getTextureManager().bindTexture(GUI);
		int relX = (this.getSc().width - this.getSc().getXSize()) / 2;
        int relY = (this.getSc().height - this.getSc().getYSize()) / 2;
        this.getSc().blit(relX, relY, 0, 0, this.getSc().getXSize(), this.getSc().getXSize());
        for (ScreenButton<DoorLockScreen> button : getButtons()) {
			if(button.isOver(mouseX - relX, mouseY - relY)) {
		        this.getSc().blit(relX + button.getX(), relY + button.getY(), button.getTextureX(), button.getTextureY(), button.getWidth(), button.getHeight());
		        if(mouseLeftDown) {
		        	mouseLeftDown = false;
		        	button.execute();
		        }
				break;
			}
		}
        int y = 7;
        for (int x = 39; x < current_code.size() * 19 + 39; x += 19) {
        	this.getSc().blit(relX + x, relY + y, 176, 0, 18, 18);
		}
	}

	@Override
	public void mouseDragged(double mouseX, double mouseY, int button, double mouseXVel, double mouseYVel) {

	}

	@Override
	public void mouseClicked(double mouseX, double mouseY, int button) {
		if(button == 0) {//left
			mouseLeftDown = true;
		}
		if(button == 2) {//middel
					
		}
		if(button == 1) {//right
			
		}

	}

	@Override
	public void mouseReleased(double mouseX, double mouseY, int button) {
		if(button == 0) {//left
			mouseLeftDown = false;
		}
		if(button == 2) {//middel
					
		}
		if(button == 1) {//right
			
		}
	}

	@Override
	public void mouseMoved(double mouseX, double mouseY) {

	}
	
	public void addCode(int code) {
		if(this.current_code.size() < 5) {
			this.current_code.add(code);
		}
	}
	
	public void removeCode() {
		if(this.current_code.size() > 0) {
			this.current_code.remove(this.current_code.size() - 1);
		}
	}

	public List<Integer> getCurrent_code() {
		return current_code;
	}

	public void setCurrent_code(List<Integer> current_code) {
		this.current_code = current_code;
	}

}
