package nl.fantasynetworkmc.fantasy20.blocks.doorlock.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import nl.fantasynetworkmc.fantasy20.blocks.ScreenState;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockContainer;
import nl.fantasynetworkmc.fantasy20.blocks.doorlock.DoorLockTile;

public class DoorLockScreen extends ContainerScreen<DoorLockContainer> {

	private DoorLockTile tileEntity;
    private ScreenState<DoorLockScreen> currentScreen;
    private DoorLockScreenMain mainScreen;
    private DoorLockScreenChangeCode changeCodeScreen;

	public DoorLockScreen(DoorLockContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		tileEntity = getContainer().getTileEntity();
		mainScreen = new DoorLockScreenMain(this, Minecraft.getInstance().fontRenderer);
		changeCodeScreen = new DoorLockScreenChangeCode(this, Minecraft.getInstance().fontRenderer);
		currentScreen = mainScreen;
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button,
			double mouseXVel, double mouseYVel) {
		currentScreen.mouseDragged(mouseX, mouseY, button, mouseXVel, mouseYVel);
		return super.mouseDragged(mouseX, mouseY, button, mouseXVel,
				mouseYVel);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		currentScreen.mouseClicked(mouseX, mouseY, button);
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		currentScreen.mouseReleased(mouseX, mouseY, button);
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		currentScreen.mouseMoved(mouseX, mouseY);
		super.mouseMoved(mouseX, mouseY);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		//this.renderBackground();
		currentScreen.render(mouseX, mouseY, partialTicks);
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		currentScreen.drawGuiContainerForegroundLayer(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		currentScreen.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
	}
	
	public static int clamp(int val, int min, int max) {
	    return Math.max(min, Math.min(max, val));
	}
	
	public boolean isOver(double mx, double my, double x, double y, double width, double height) {
		if(mx > x && mx < x + width) {
			if(my > y && my < y + height) {
				return true;
			}
		}
		return false;
	}

	public DoorLockTile getTileEntity() {
		return tileEntity;
	}

	public void setTileEntity(DoorLockTile tileEntity) {
		this.tileEntity = tileEntity;
	}

	public ScreenState<DoorLockScreen> getCurrentScreen() {
		return currentScreen;
	}

	public void setCurrentScreen(ScreenState<DoorLockScreen> currentScreen) {
		this.currentScreen = currentScreen;
	}

	public DoorLockScreenChangeCode getChangeCodeScreen() {
		return changeCodeScreen;
	}

	public void setChangeCodeScreen(DoorLockScreenChangeCode changeCodeScreen) {
		this.changeCodeScreen = changeCodeScreen;
	}

	public DoorLockScreenMain getMainScreen() {
		return mainScreen;
	}

	public void setMainScreen(DoorLockScreenMain mainScreen) {
		this.mainScreen = mainScreen;
	}

}


