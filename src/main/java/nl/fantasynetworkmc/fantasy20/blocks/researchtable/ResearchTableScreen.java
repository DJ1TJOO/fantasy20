package nl.fantasynetworkmc.fantasy20.blocks.researchtable;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import nl.fantasynetworkmc.fantasy20.Fantasy20;

public class ResearchTableScreen extends ContainerScreen<ResearchTableContainer> {

	private ResearchTableTile tileEntity;
    private ResourceLocation GUI = new ResourceLocation(Fantasy20.MODID, "textures/gui/research_table/gui.png");
	
	public ResearchTableScreen(ResearchTableContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
		super(screenContainer, inv, titleIn);
		tileEntity = getContainer().getTileEntity();
	}
	
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button,
			double mouseXVel, double mouseYVel) {
		return super.mouseDragged(mouseX, mouseY, button, mouseXVel,
				mouseYVel);
	}
	
	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button) {
		return super.mouseReleased(mouseX, mouseY, button);
	}
	
	@Override
	public void mouseMoved(double mouseX, double mouseY) {
		super.mouseMoved(mouseX, mouseY);
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		this.font.drawString(this.title.getFormattedText(), 8.0F, 6.0F, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI);
		int relX = (this.width - this.xSize) / 2;
        int relY = (this.height - this.ySize) / 2;
        this.blit(relX, relY, 0, 0, this.xSize, this.ySize);
       // System.err.println(getContainer().getTileEntity().getCompleted());
        if(tileEntity.getCurrentRecipe() != null) {
        	int k = getCompletedLeft(24);
        	//System.err.println(k);
        	this.blit(relX + 98, relY + 33, 176, 0, k, 17);
        }
	}
	
	private int getCompletedLeft(int pixels) {
		int i = tileEntity.getCompleted();
		return (int)(((double)i / (double)100) * (double)pixels);
	}

}


