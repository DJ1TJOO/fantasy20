package nl.fantasynetworkmc.fantasy20.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;

public abstract class ScreenState <T>{
	
	private FontRenderer font;
	private T sc;
	private List<ScreenButton<T>> buttons = new ArrayList<ScreenButton<T>>();
	
	public ScreenState(T sc, FontRenderer font) {
		this.sc = sc;
		this.setFont(font);
	}
	
	public abstract void render(int mouseX, int mouseY, float partialTicks);
	public abstract void drawGuiContainerForegroundLayer(int mouseX, int mouseY);
	public abstract void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY);
	public abstract void mouseDragged(double mouseX, double mouseY, int button,
			double mouseXVel, double mouseYVel);
	public abstract void mouseClicked(double mouseX, double mouseY, int button);
	public abstract void mouseReleased(double mouseX, double mouseY, int button);
	public abstract void mouseMoved(double mouseX, double mouseY);
	
	public T getSc() {
		return sc;
	}
	public void setSc(T sc) {
		this.sc = sc;
	}

	public List<ScreenButton<T>> getButtons() {
		return buttons;
	}

	public void setButtons(List<ScreenButton<T>> buttons) {
		this.buttons = buttons;
	}

	public FontRenderer getFont() {
		return font;
	}

	public void setFont(FontRenderer font) {
		this.font = font;
	}
	
}
