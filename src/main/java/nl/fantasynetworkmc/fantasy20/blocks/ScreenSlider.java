package nl.fantasynetworkmc.fantasy20.blocks;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;

public class ScreenSlider {

	private int scrollLocation = 0, maxScrollLocation = 29, x = 0, y = 0, width = 0, height = 0, textureX = 0, textureY = 0, textureWidth = 0, textureHeight = 0;
	private boolean scrollUsable = false;
	private boolean mouseLeftDown = false;
	
	public ScreenSlider(int maxScrollLocation, int x, int y, int width, int height, int textureX, int textureY,
			int textureWidth, int textureHeight) {
		this.maxScrollLocation = maxScrollLocation;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.textureX = textureX;
		this.textureY = textureY;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
	}

	public int getScrollLocation() {
		return scrollLocation;
	}
	
	public void setScrollLocation(int scrollLocation) {
		this.scrollLocation = scrollLocation;
	}
	
	public boolean isScrollUsable() {
		return scrollUsable;
	}
	
	public void setScrollUsable(boolean scrollUsable) {
		this.scrollUsable = scrollUsable;
	}
	
	public boolean isMouseLeftDown() {
		return mouseLeftDown;
	}
	
	public void setMouseLeftDown(boolean mouseLeftDown) {
		this.mouseLeftDown = mouseLeftDown;
	}
	
	public void mouseClicked(double mouseX, double mouseY, int button) {
		//System.err.println(button);
		if(button == 0) {//left
			mouseLeftDown = true;
		}
		if(button == 2) {//middel
					
		}
		if(button == 1) {//right
			
		}
	}
	
	public void mouseReleased(double mouseX, double mouseY, int button) {
		if(button == 0) {//left
			mouseLeftDown = false;
		}
		if(button == 2) {//middel
					
		}
		if(button == 1) {//right
			
		}
	}
	
	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY, double width, double height, double xSize, double ySize, ContainerScreen<?> conScreen) {
		int relX = (int) ((width - xSize) / 2);
        int relY = (int) ((height - ySize) / 2);
		if(isScrollUsable()) {
			conScreen.blit(relX + this.x, relY + this.y + getScrollLocation(), getTextureX(), getTextureY(), getTextureWidth(), getTextureHeight());
        } else {
        	conScreen.blit(relX + this.x, relY + this.y + getScrollLocation(), getTextureX(), getTextureY(), getTextureWidth(), getTextureHeight());
        }
	}
	
	public void mouseMoved(double mouseX, double mouseY, double width, double height, double xSize, double ySize) {
		int relX = (int) ((width - xSize) / 2);
        int relY = (int) ((height - ySize) / 2);
		if(scrollUsable && mouseLeftDown && isOver(mouseX, mouseY, relX + this.x, relY + this.y + scrollLocation, this.width, this.height)) {
			scrollLocation = (int) (mouseY - relY - 19*1.5);
			scrollLocation = clamp(scrollLocation, 0, getMaxScrollLocation());
		}
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

	public int getMaxScrollLocation() {
		return maxScrollLocation;
	}

	public void setMaxScrollLocation(int maxScrollLocation) {
		this.maxScrollLocation = maxScrollLocation;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getTextureX() {
		return textureX;
	}

	public void setTextureX(int textureX) {
		this.textureX = textureX;
	}

	public int getTextureY() {
		return textureY;
	}

	public void setTextureY(int textureY) {
		this.textureY = textureY;
	}

	public int getTextureWidth() {
		return textureWidth;
	}

	public void setTextureWidth(int textureWidth) {
		this.textureWidth = textureWidth;
	}

	public int getTextureHeight() {
		return textureHeight;
	}

	public void setTextureHeight(int textureHeight) {
		this.textureHeight = textureHeight;
	}
	 
}
