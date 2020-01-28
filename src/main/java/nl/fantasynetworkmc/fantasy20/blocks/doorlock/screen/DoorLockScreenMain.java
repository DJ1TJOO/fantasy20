package nl.fantasynetworkmc.fantasy20.blocks.doorlock.screen;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import nl.fantasynetworkmc.fantasy20.Fantasy20;
import nl.fantasynetworkmc.fantasy20.blocks.ScreenAction;
import nl.fantasynetworkmc.fantasy20.blocks.ScreenButton;
import nl.fantasynetworkmc.fantasy20.blocks.ScreenState;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockTile;
import nl.fantasynetworkmc.fantasy20.packets.DoorBlockUpdatePacket;
import nl.fantasynetworkmc.fantasy20.packets.DoorLockTileUsercodeUpdatePacket;
import nl.fantasynetworkmc.fantasy20.packets.PacketHandler;

public class DoorLockScreenMain extends ScreenState<DoorLockScreen> {

	private ResourceLocation GUI = new ResourceLocation(Fantasy20.MODID, "textures/gui/doorlock/gui.png");

    private List<Integer> current_code = new ArrayList<Integer>();
	
    private boolean mouseLeftDown = false;
	private boolean wrongPassword = false;
	private boolean notOwner = false;
    
	public DoorLockScreenMain(DoorLockScreen dls, FontRenderer font) {
		super(dls, font);
		getButtons().add(new ScreenButton<DoorLockScreen>(20, 64, 18, 18, 176, 18, "lock", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				TileEntity t1 = getSc().getTileEntity().getWorld().getTileEntity(getSc().getTileEntity().getPos().add(0, -1, 0));
				if(t1 instanceof DoorLockTile) {
					DoorLockTile t2 = (DoorLockTile) t1;
					if(getCurrent_code().equals(t2.getCode())) {
						setWrongPassword(false);
						PacketHandler.INSTANCE.sendToServer(new DoorBlockUpdatePacket(getSc().getTileEntity().getPos(), false));
						if(t2.getUsercodes().containsKey(dls.getContainer().getPlayerEntity().getUniqueID())) {
							if(!t2.getUsercodes().get(dls.getContainer().getPlayerEntity().getUniqueID()).equals(t2.getCode())) {
								PacketHandler.INSTANCE.sendToServer(new DoorLockTileUsercodeUpdatePacket(t2.getPos(), t2.getCode(), dls.getContainer().getPlayerEntity().getUniqueID()));
								t2.getUsercodes().put(dls.getContainer().getPlayerEntity().getUniqueID(), t2.getCode());
								t2.markDirty();
							}
						} else {	
							PacketHandler.INSTANCE.sendToServer(new DoorLockTileUsercodeUpdatePacket(t2.getPos(), t2.getCode(), dls.getContainer().getPlayerEntity().getUniqueID()));
							t2.getUsercodes().put(dls.getContainer().getPlayerEntity().getUniqueID(), t2.getCode());
							t2.markDirty();
						}
						//System.err.println(getDls().getTileEntity().getCode());
						Fantasy20.proxy.getClientPlayer().closeScreen();
					} else {
						setWrongPassword(true);
					}
				} else {
					if(getCurrent_code().equals(getSc().getContainer().getTileEntity().getCode())) {
						setWrongPassword(false);
						PacketHandler.INSTANCE.sendToServer(new DoorBlockUpdatePacket(getSc().getTileEntity().getPos(), false));
						if(getSc().getContainer().getTileEntity().getUsercodes().containsKey(dls.getContainer().getPlayerEntity().getUniqueID())) {
							if(!getSc().getContainer().getTileEntity().getUsercodes().get(dls.getContainer().getPlayerEntity().getUniqueID()).equals(getSc().getContainer().getTileEntity().getCode())) {
								PacketHandler.INSTANCE.sendToServer(new DoorLockTileUsercodeUpdatePacket(getSc().getContainer().getTileEntity().getPos(), getSc().getContainer().getTileEntity().getCode(), dls.getContainer().getPlayerEntity().getUniqueID()));
								getSc().getContainer().getTileEntity().getUsercodes().put(dls.getContainer().getPlayerEntity().getUniqueID(), getSc().getContainer().getTileEntity().getCode());
								getSc().getContainer().getTileEntity().markDirty();
							}
						} else {						
							PacketHandler.INSTANCE.sendToServer(new DoorLockTileUsercodeUpdatePacket(getSc().getContainer().getTileEntity().getPos(), getSc().getContainer().getTileEntity().getCode(), dls.getContainer().getPlayerEntity().getUniqueID()));
							getSc().getContainer().getTileEntity().getUsercodes().put(dls.getContainer().getPlayerEntity().getUniqueID(), getSc().getContainer().getTileEntity().getCode());
							getSc().getContainer().getTileEntity().markDirty();
						}
						//System.err.println(getDls().getTileEntity().getCode());
						Fantasy20.proxy.getClientPlayer().closeScreen();
					} else {
						setWrongPassword(true);
					}
				}
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(134, 64, 18, 18, 176, 18, "unlock", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				TileEntity t1 = getSc().getTileEntity().getWorld().getTileEntity(getSc().getTileEntity().getPos().add(0, -1, 0));
				if(t1 instanceof DoorLockTile) {
					DoorLockTile t2 = (DoorLockTile) t1;
					if(getCurrent_code().equals(t2.getCode())) {
						setWrongPassword(false);
						PacketHandler.INSTANCE.sendToServer(new DoorBlockUpdatePacket(getSc().getContainer().getTileEntity().getPos(), true));
						if(t2.getUsercodes().containsKey(dls.getContainer().getPlayerEntity().getUniqueID())) {
							if(!t2.getUsercodes().get(dls.getContainer().getPlayerEntity().getUniqueID()).equals(t2.getCode())) {
								PacketHandler.INSTANCE.sendToServer(new DoorLockTileUsercodeUpdatePacket(t2.getPos(), t2.getCode(), dls.getContainer().getPlayerEntity().getUniqueID()));
								t2.getUsercodes().put(dls.getContainer().getPlayerEntity().getUniqueID(), t2.getCode());
								t2.markDirty();
							}
						} else {								
							PacketHandler.INSTANCE.sendToServer(new DoorLockTileUsercodeUpdatePacket(t2.getPos(), t2.getCode(), dls.getContainer().getPlayerEntity().getUniqueID()));
							t2.getUsercodes().put(dls.getContainer().getPlayerEntity().getUniqueID(), t2.getCode());
							t2.markDirty();
						}
						Fantasy20.proxy.getClientPlayer().closeScreen();
					} else {
						setWrongPassword(true);
					}
				} else {
					if(getCurrent_code().equals(getSc().getContainer().getTileEntity().getCode())) {
						setWrongPassword(false);
						PacketHandler.INSTANCE.sendToServer(new DoorBlockUpdatePacket(getSc().getContainer().getTileEntity().getPos(), true));
						if(getSc().getContainer().getTileEntity().getUsercodes().containsKey(dls.getContainer().getPlayerEntity().getUniqueID())) {
							if(!getSc().getContainer().getTileEntity().getUsercodes().get(dls.getContainer().getPlayerEntity().getUniqueID()).equals(getSc().getContainer().getTileEntity().getCode())) {
								PacketHandler.INSTANCE.sendToServer(new DoorLockTileUsercodeUpdatePacket(getSc().getContainer().getTileEntity().getPos(), getSc().getContainer().getTileEntity().getCode(), dls.getContainer().getPlayerEntity().getUniqueID()));
								getSc().getContainer().getTileEntity().getUsercodes().put(dls.getContainer().getPlayerEntity().getUniqueID(), getSc().getContainer().getTileEntity().getCode());
								getSc().getContainer().getTileEntity().markDirty();
							}
						} else {								
							PacketHandler.INSTANCE.sendToServer(new DoorLockTileUsercodeUpdatePacket(getSc().getContainer().getTileEntity().getPos(), getSc().getContainer().getTileEntity().getCode(), dls.getContainer().getPlayerEntity().getUniqueID()));
							getSc().getContainer().getTileEntity().getUsercodes().put(dls.getContainer().getPlayerEntity().getUniqueID(), getSc().getContainer().getTileEntity().getCode());
							getSc().getContainer().getTileEntity().markDirty();
						}
						Fantasy20.proxy.getClientPlayer().closeScreen();
					} else {
						setWrongPassword(true);
					}
				}
			}
		}));
		getButtons().add(new ScreenButton<DoorLockScreen>(20, 102, 18, 18, 176, 18, "newcode", getSc(), new ScreenAction<DoorLockScreen>() {
			@Override
			public void execute(DoorLockScreen dls) {
				try {
					TileEntity t1 = getSc().getTileEntity().getWorld().getTileEntity(getSc().getTileEntity().getPos().add(0, -1, 0));
					if(t1 instanceof DoorLockTile) {
						if(Fantasy20.proxy.getClientPlayer().getUniqueID().equals(((DoorLockTile) t1).getOwner())) {
							setNotOwner(false);
							if(getCurrent_code().equals(((DoorLockTile) t1).getCode())) {
								setWrongPassword(false);
								getSc().setCurrentScreen(getSc().getChangeCodeScreen());
							} else {
								setWrongPassword(true);
							}
						} else {
							setNotOwner(true);
						}
					} else {

						if(Fantasy20.proxy.getClientPlayer().getUniqueID().equals(dls.getTileEntity().getOwner())) {
							setNotOwner(false);
							if(getCurrent_code().equals(getSc().getContainer().getTileEntity().getCode())) {
								setWrongPassword(false);
								getSc().setCurrentScreen(getSc().getChangeCodeScreen());
							} else {
								setWrongPassword(true);
							}
						} else {
							setNotOwner(true);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					setNotOwner(true);
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
		if(wrongPassword) {
			this.getFont().drawString("Verkeerd wachtwoord", 45.0F, -10.0F, 0xFF0000);
		}
		if(notOwner) {
			this.getFont().drawString("Je moet owner zijn", 45.0F, -10.0F, 0xFF0000);
		}
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
		//int relX = (this.width - this.xSize) / 2;
        //int relY = (this.height - this.ySize) / 2;
	}
    
	public List<Integer> getCurrent_code() {
		return current_code;
	}

	public void setCurrent_code(List<Integer> current_code) {
		this.current_code = current_code;
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

	public boolean isWrongPassword() {
		return wrongPassword;
	}

	public void setWrongPassword(boolean wrongPassword) {
		this.wrongPassword = wrongPassword;
	}

	public boolean isNotOwner() {
		return notOwner;
	}

	public void setNotOwner(boolean notOwner) {
		this.notOwner = notOwner;
	}

}
