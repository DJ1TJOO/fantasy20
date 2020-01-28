package nl.fantasynetworkmc.fantasy20.blocks;

public class ScreenButton <T>{

	private int x, y, width, height, textureX, textureY;
	private String name;
	private T sc;
	private ScreenAction<T> action;
	
	public ScreenButton(int x, int y, int width, int height, int textureX, int textureY, String name, T sc, ScreenAction<T> action) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.textureX = textureX;
		this.textureY = textureY;
		this.name = name;
		this.sc = sc;
		this.action = action;
	}
	
	public void execute() {
		action.execute(sc);
	}
	
	public boolean isOver(double mouseX, double mouseY) {
		if(mouseX > x && mouseX < x + width) {
			if(mouseY > y && mouseY < y + height) {
				return true;
			}
		}
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public T getSc() {
		return sc;
	}

	public void setSc(T sc) {
		this.sc = sc;
	}

	public ScreenAction<T> getAction() {
		return action;
	}

	public void setAction(ScreenAction<T> action) {
		this.action = action;
	}
	
}
