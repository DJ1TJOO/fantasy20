package nl.fantasynetworkmc.fantasy20.blocks.building.wallframe;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import nl.fantasynetworkmc.fantasy20.PanelTypes;
import nl.fantasynetworkmc.fantasy20.items.ModItems;
import nl.fantasynetworkmc.fantasy20.items.building.frame.panels.MetalPanel;
import nl.fantasynetworkmc.fantasy20.items.building.frame.panels.StonePanel;
import nl.fantasynetworkmc.fantasy20.items.building.frame.panels.WoodenPanel;

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
					if(te.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING)) {
						if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH)) {
							GlStateManager.scaled(2, 2, 0.5);
							GlStateManager.translated(0.375, 0.25, 0.05);
							GlStateManager.rotated(90f,0, 0, 1);
							ItemStack stack = new ItemStack(ModItems.WOODEN_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(WoodenPanel.ROTATE_90, true);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH)) {
							GlStateManager.scaled(2, 2, 0.5);
							GlStateManager.translated(0.375, 0.25, 1.94);
							GlStateManager.rotated(90f,0, 0, 1);
							ItemStack stack = new ItemStack(ModItems.WOODEN_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(WoodenPanel.ROTATE_90, true);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.WEST)) {
							GlStateManager.scaled(0.5, 2, 2);
							GlStateManager.translated(1.94, 0.125, 0.25);
							GlStateManager.rotated(90f,0, 1, 0);
							ItemStack stack = new ItemStack(ModItems.WOODEN_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(WoodenPanel.ROTATE_90, false);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.EAST)) {
							GlStateManager.scaled(0.5, 2, 2);
							GlStateManager.translated(0.05, 0.125, 0.25);
							GlStateManager.rotated(90f,0, 1, 0);
							ItemStack stack = new ItemStack(ModItems.WOODEN_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(WoodenPanel.ROTATE_90, false);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		}
					}
				}
				GlStateManager.popMatrix();
				break;
			case STONE:
				GlStateManager.pushMatrix();
				{
					GlStateManager.translated(x, y, z);
					if(te.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING)) {
						if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH)) {
							GlStateManager.scaled(2, 2, 0.5);
							GlStateManager.translated(0.375, 0.25, 0.05);
							GlStateManager.rotated(90f,0, 0, 1);
							ItemStack stack = new ItemStack(ModItems.STONE_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(StonePanel.ROTATE_90, true);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH)) {
							GlStateManager.scaled(2, 2, 0.5);
							GlStateManager.translated(0.375, 0.25, 1.94);
							GlStateManager.rotated(90f,0, 0, 1);
							ItemStack stack = new ItemStack(ModItems.STONE_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(StonePanel.ROTATE_90, true);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.WEST)) {
							GlStateManager.scaled(0.5, 2, 2);
							GlStateManager.translated(1.94, 0.125, 0.25);
							GlStateManager.rotated(90f,0, 1, 0);
							ItemStack stack = new ItemStack(ModItems.STONE_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(StonePanel.ROTATE_90, false);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.EAST)) {
							GlStateManager.scaled(0.5, 2, 2);
							GlStateManager.translated(0.05, 0.125, 0.25);
							GlStateManager.rotated(90f,0, 1, 0);
							ItemStack stack = new ItemStack(ModItems.STONE_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(StonePanel.ROTATE_90, false);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		}
					}
				}
				GlStateManager.popMatrix();
				break;
			case METAL:
				GlStateManager.pushMatrix();
				{
					GlStateManager.translated(x, y, z);
					if(te.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING)) {
						if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH)) {
							GlStateManager.scaled(2, 2, 0.5);
							GlStateManager.translated(0.375, 0.25, 0.05);
							GlStateManager.rotated(90f,0, 0, 1);
							ItemStack stack = new ItemStack(ModItems.METAL_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(MetalPanel.ROTATE_90, true);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH)) {
							GlStateManager.scaled(2, 2, 0.5);
							GlStateManager.translated(0.375, 0.25, 1.94);
							GlStateManager.rotated(90f,0, 0, 1);
							ItemStack stack = new ItemStack(ModItems.METAL_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(MetalPanel.ROTATE_90, true);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.WEST)) {
							GlStateManager.scaled(0.5, 2, 2);
							GlStateManager.translated(1.94, 0.125, 0.25);
							GlStateManager.rotated(90f,0, 1, 0);
							ItemStack stack = new ItemStack(ModItems.METAL_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(MetalPanel.ROTATE_90, false);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		} else if(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.EAST)) {
							GlStateManager.scaled(0.5, 2, 2);
							GlStateManager.translated(0.05, 0.125, 0.25);
							GlStateManager.rotated(90f,0, 1, 0);
							ItemStack stack = new ItemStack(ModItems.METAL_PANEL);
							CompoundNBT tag = new CompoundNBT();
							tag.putBoolean(MetalPanel.ROTATE_90, false);
							stack.setTag(tag);
							Minecraft.getInstance().getItemRenderer().renderItem(stack, TransformType.GROUND);
			    		}
					}
				}
				GlStateManager.popMatrix();
				break;

			default:
				break;
			}
		}
	}
	
}
