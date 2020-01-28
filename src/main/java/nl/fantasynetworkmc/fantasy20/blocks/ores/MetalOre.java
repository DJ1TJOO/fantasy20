package nl.fantasynetworkmc.fantasy20.blocks.ores;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MetalOre extends Block {

	public MetalOre() {
		super(Properties.create(Material.EARTH)
				.sound(SoundType.STONE)
				.hardnessAndResistance(3, 15)
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(3));
		setRegistryName("fantasy20:metal_ore");
	}
	
}
