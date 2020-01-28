package nl.fantasynetworkmc.fantasy20.blocks.building.wallframe;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import nl.fantasynetworkmc.fantasy20.PanelTypes;
import nl.fantasynetworkmc.fantasy20.items.ModItems;

@SuppressWarnings("deprecation")
public class WallFrameRenderer extends TileEntityRenderer<WallFrameTile> {

	@Override
	public void render(WallFrameTile te, double x, double y, double z, float partialTicks,
			int destroyStage) {
		super.render(te, x, y, z, partialTicks, destroyStage);
		if(!te.getPanelType().equals(PanelTypes.NONE)) {
			switch (te.getPanelType()) {
			case WOODEN:
				GlStateManager.pushMatrix();
				{
					GlStateManager.translated(x, y, z);
					GlStateManager.scaled(2, 0.5, 2);
					GlStateManager.translated(0.25, 0.05, 0.125);
					GlStateManager.rotated(90f,1, 0, 0);
					Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.WOODEN_PANEL), TransformType.GROUND);
				}
				GlStateManager.popMatrix();
				break;
			case STONE:
				GlStateManager.pushMatrix();
				{
					GlStateManager.translated(x, y, z);
					GlStateManager.scaled(2, 0.5, 2);
					GlStateManager.translated(0.25, 0.05, 0.125);
					GlStateManager.rotated(90f,1, 0, 0);
					Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.STONE_PANEL), TransformType.GROUND);
				}
				GlStateManager.popMatrix();
				break;
			case METAL:
				GlStateManager.pushMatrix();
				{
					GlStateManager.translated(x, y, z);
					GlStateManager.scaled(2, 0.5, 2);
					GlStateManager.translated(0.25, 0.05, 0.125);
					GlStateManager.rotated(90f,1, 0, 0);
					Minecraft.getInstance().getItemRenderer().renderItem(new ItemStack(ModItems.METAL_PANEL), TransformType.GROUND);
				}
				GlStateManager.popMatrix();
				break;

			default:
				break;
			}
		}
	}
	
}
