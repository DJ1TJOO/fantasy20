package nl.fantasynetworkmc.fantasy20.items.panels;

import javax.annotation.Nullable;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import nl.fantasynetworkmc.fantasy20.Fantasy20;

public class StonePanel extends Item {
	public static final String ROTATE_90 = "rotate_90";

	public StonePanel(Properties properties) {
		super(properties);
		setRegistryName("fantasy20:stone_panel");
		addPropertyOverride(new ResourceLocation(Fantasy20.MODID, "rotate_90"), new IItemPropertyGetter() {
			
			@Override
			public float call(ItemStack stack, World world, LivingEntity entity) {
				return StonePanel.getPropertyRotate90(stack, entity);
			}
		});
	}
	
	public static float getPropertyRotate90 (ItemStack stack, @Nullable LivingEntity entityIn) {
        if (!stack.isEmpty() && stack.getItem() instanceof StonePanel)
            return StonePanel.rotate90(stack) ? 1.f : 0.f; // 0 - not empty
        return 0.f;
    }

    public static boolean rotate90(ItemStack stack) {
        if (stack.hasTag()) {
            CompoundNBT nbt = stack.getTag();
            return nbt.contains(ROTATE_90) && nbt.getBoolean(ROTATE_90);
        }
        return false;
    }
	
}
