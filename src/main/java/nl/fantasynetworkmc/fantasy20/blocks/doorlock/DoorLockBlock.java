package nl.fantasynetworkmc.fantasy20.blocks.doorlock;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoorHingeSide;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class DoorLockBlock extends Block {
	public static final DirectionProperty FACING;
    public static final BooleanProperty OPEN;
    public static final EnumProperty<DoorHingeSide> HINGE;
    public static final EnumProperty<DoubleBlockHalf> HALF;
    protected static final VoxelShape SOUTH_AABB;
    protected static final VoxelShape NORTH_AABB;
    protected static final VoxelShape WEST_AABB;
    protected static final VoxelShape EAST_AABB;
    
    protected DoorLockBlock(final Block.Properties p_i48413_1_) {
        super(p_i48413_1_);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateContainer.getBaseState()).with((IProperty)DoorBlock.FACING, (Comparable)Direction.NORTH)).with((IProperty)DoorBlock.OPEN, (Comparable)false)).with((IProperty)DoorBlock.HINGE, (Comparable)DoorHingeSide.LEFT)).with((IProperty)DoorBlock.HALF, (Comparable)DoubleBlockHalf.LOWER)));
    }
    
    public VoxelShape getShape(final BlockState p_220053_1_, final IBlockReader p_220053_2_, final BlockPos p_220053_3_, final ISelectionContext p_220053_4_) {
        final Direction lvt_5_1_ = (Direction)p_220053_1_.get((IProperty)DoorBlock.FACING);
        final boolean lvt_6_1_ = !(boolean)p_220053_1_.get((IProperty)DoorBlock.OPEN);
        final boolean lvt_7_1_ = p_220053_1_.get((IProperty)DoorBlock.HINGE) == DoorHingeSide.RIGHT;
        switch (DoorLockBlock.DoorBlock$1.field_185789_a[lvt_5_1_.ordinal()]) {
            default: {
                return lvt_6_1_ ? DoorLockBlock.EAST_AABB : (lvt_7_1_ ? DoorLockBlock.NORTH_AABB : DoorLockBlock.SOUTH_AABB);
            }
            case 2: {
                return lvt_6_1_ ? DoorLockBlock.SOUTH_AABB : (lvt_7_1_ ? DoorLockBlock.EAST_AABB : DoorLockBlock.WEST_AABB);
            }
            case 3: {
                return lvt_6_1_ ? DoorLockBlock.WEST_AABB : (lvt_7_1_ ? DoorLockBlock.SOUTH_AABB : DoorLockBlock.NORTH_AABB);
            }
            case 4: {
                return lvt_6_1_ ? DoorLockBlock.NORTH_AABB : (lvt_7_1_ ? DoorLockBlock.WEST_AABB : DoorLockBlock.EAST_AABB);
            }
        }
    }
    
    @SuppressWarnings("deprecation")
	public BlockState updatePostPlacement(final BlockState p_196271_1_, final Direction p_196271_2_, final BlockState p_196271_3_, final IWorld p_196271_4_, final BlockPos p_196271_5_, final BlockPos p_196271_6_) {
        final DoubleBlockHalf lvt_7_1_ = (DoubleBlockHalf)p_196271_1_.get((IProperty)DoorBlock.HALF);
        if (p_196271_2_.getAxis() == Direction.Axis.Y && lvt_7_1_ == DoubleBlockHalf.LOWER == (p_196271_2_ == Direction.UP)) {
            if (p_196271_3_.getBlock() == this && p_196271_3_.get((IProperty)DoorBlock.HALF) != lvt_7_1_) {
                return (BlockState)((BlockState)((BlockState)((BlockState)p_196271_1_.with((IProperty)DoorBlock.FACING, p_196271_3_.get((IProperty)DoorBlock.FACING))).with((IProperty)DoorBlock.OPEN, p_196271_3_.get((IProperty)DoorBlock.OPEN))).with((IProperty)DoorBlock.HINGE, p_196271_3_.get((IProperty)DoorBlock.HINGE)));
            }
            return Blocks.AIR.getDefaultState();
        }
        else {
            if (lvt_7_1_ == DoubleBlockHalf.LOWER && p_196271_2_ == Direction.DOWN && !p_196271_1_.isValidPosition((IWorldReader)p_196271_4_, p_196271_5_)) {
                return Blocks.AIR.getDefaultState();
            }
            return super.updatePostPlacement(p_196271_1_, p_196271_2_, p_196271_3_, p_196271_4_, p_196271_5_, p_196271_6_);
        }
    }
    
    public void harvestBlock(final World p_180657_1_, final PlayerEntity p_180657_2_, final BlockPos p_180657_3_, final BlockState p_180657_4_, @Nullable final TileEntity p_180657_5_, final ItemStack p_180657_6_) {
        super.harvestBlock(p_180657_1_, p_180657_2_, p_180657_3_, Blocks.AIR.getDefaultState(), p_180657_5_, p_180657_6_);
    }
    
    public void onBlockHarvested(final World p_176208_1_, final BlockPos p_176208_2_, final BlockState p_176208_3_, final PlayerEntity p_176208_4_) {
        final DoubleBlockHalf lvt_5_1_ = (DoubleBlockHalf)p_176208_3_.get((IProperty)DoorBlock.HALF);
        final BlockPos lvt_6_1_ = (lvt_5_1_ == DoubleBlockHalf.LOWER) ? p_176208_2_.up() : p_176208_2_.down();
        final BlockState lvt_7_1_ = p_176208_1_.getBlockState(lvt_6_1_);
        if (lvt_7_1_.getBlock() == this && lvt_7_1_.get((IProperty)DoorBlock.HALF) != lvt_5_1_) {
            p_176208_1_.setBlockState(lvt_6_1_, Blocks.AIR.getDefaultState(), 35);
            p_176208_1_.playEvent(p_176208_4_, 2001, lvt_6_1_, Block.getStateId(lvt_7_1_));
            final ItemStack lvt_8_1_ = p_176208_4_.getHeldItemMainhand();
            if (!p_176208_1_.isRemote && !p_176208_4_.isCreative()) {
                Block.spawnDrops(p_176208_3_, p_176208_1_, p_176208_2_, (TileEntity)null, (Entity)p_176208_4_, lvt_8_1_);
                Block.spawnDrops(lvt_7_1_, p_176208_1_, lvt_6_1_, (TileEntity)null, (Entity)p_176208_4_, lvt_8_1_);
            }
        }
        super.onBlockHarvested(p_176208_1_, p_176208_2_, p_176208_3_, p_176208_4_);
    }
    
	public boolean allowsMovement(final BlockState p_196266_1_, final IBlockReader p_196266_2_, final BlockPos p_196266_3_, final PathType p_196266_4_) {
        switch (DoorLockBlock.DoorBlock$1.field_210338_b[p_196266_4_.ordinal()]) {
            case 1: {
                return (boolean)p_196266_1_.get((IProperty)DoorBlock.OPEN);
            }
            case 2: {
                return false;
            }
            case 3: {
                return (boolean)p_196266_1_.get((IProperty)DoorBlock.OPEN);
            }
            default: {
                return false;
            }
        }
    }
    
    private int getCloseSound() {
        return (this.material == Material.IRON) ? 1011 : 1012;
    }
    
    private int getOpenSound() {
        return (this.material == Material.IRON) ? 1005 : 1006;
    }
    
    @Nullable
    public BlockState getStateForPlacement(final BlockItemUseContext p_196258_1_) {
        final BlockPos lvt_2_1_ = p_196258_1_.getPos();
        if (lvt_2_1_.getY() < 255 && p_196258_1_.getWorld().getBlockState(lvt_2_1_.up()).isReplaceable(p_196258_1_)) {
            return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.getDefaultState().with((IProperty)DoorBlock.FACING, (Comparable)p_196258_1_.getPlacementHorizontalFacing())).with((IProperty)DoorBlock.HINGE, (Comparable)this.getHingeSide(p_196258_1_)).with((IProperty)DoorBlock.OPEN, (Comparable)false)).with((IProperty)DoorBlock.HALF, (Comparable)DoubleBlockHalf.LOWER)));
        }
        return null;
    }
    
    public void onBlockPlacedBy(final World p_180633_1_, final BlockPos p_180633_2_, final BlockState p_180633_3_, final LivingEntity p_180633_4_, final ItemStack p_180633_5_) {
        p_180633_1_.setBlockState(p_180633_2_.up(), (BlockState)p_180633_3_.with((IProperty)DoorBlock.HALF, (Comparable)DoubleBlockHalf.UPPER), 3);
    }
    
    private DoorHingeSide getHingeSide(final BlockItemUseContext p_208073_1_) {
        final IBlockReader lvt_2_1_ = (IBlockReader)p_208073_1_.getWorld();
        final BlockPos lvt_3_1_ = p_208073_1_.getPos();
        final Direction lvt_4_1_ = p_208073_1_.getPlacementHorizontalFacing();
        final BlockPos lvt_5_1_ = lvt_3_1_.up();
        final Direction lvt_6_1_ = lvt_4_1_.rotateYCCW();
        final BlockPos lvt_7_1_ = lvt_3_1_.offset(lvt_6_1_);
        final BlockState lvt_8_1_ = lvt_2_1_.getBlockState(lvt_7_1_);
        final BlockPos lvt_9_1_ = lvt_5_1_.offset(lvt_6_1_);
        final BlockState lvt_10_1_ = lvt_2_1_.getBlockState(lvt_9_1_);
        final Direction lvt_11_1_ = lvt_4_1_.rotateY();
        final BlockPos lvt_12_1_ = lvt_3_1_.offset(lvt_11_1_);
        final BlockState lvt_13_1_ = lvt_2_1_.getBlockState(lvt_12_1_);
        final BlockPos lvt_14_1_ = lvt_5_1_.offset(lvt_11_1_);
        final BlockState lvt_15_1_ = lvt_2_1_.getBlockState(lvt_14_1_);
        final int lvt_16_1_ = (lvt_8_1_.func_224756_o(lvt_2_1_, lvt_7_1_) ? -1 : 0) + (lvt_10_1_.func_224756_o(lvt_2_1_, lvt_9_1_) ? -1 : 0) + (lvt_13_1_.func_224756_o(lvt_2_1_, lvt_12_1_) ? 1 : 0) + (lvt_15_1_.func_224756_o(lvt_2_1_, lvt_14_1_) ? 1 : 0);
        final boolean lvt_17_1_ = lvt_8_1_.getBlock() == this && lvt_8_1_.get((IProperty)DoorBlock.HALF) == DoubleBlockHalf.LOWER;
        final boolean lvt_18_1_ = lvt_13_1_.getBlock() == this && lvt_13_1_.get((IProperty)DoorBlock.HALF) == DoubleBlockHalf.LOWER;
        if ((lvt_17_1_ && !lvt_18_1_) || lvt_16_1_ > 0) {
            return DoorHingeSide.RIGHT;
        }
        if ((lvt_18_1_ && !lvt_17_1_) || lvt_16_1_ < 0) {
            return DoorHingeSide.LEFT;
        }
        final int lvt_19_1_ = lvt_4_1_.getXOffset();
        final int lvt_20_1_ = lvt_4_1_.getZOffset();
        final Vec3d lvt_21_1_ = p_208073_1_.getHitVec();
        final double lvt_22_1_ = lvt_21_1_.x - lvt_3_1_.getX();
        final double lvt_24_1_ = lvt_21_1_.z - lvt_3_1_.getZ();
        return ((lvt_19_1_ < 0 && lvt_24_1_ < 0.5) || (lvt_19_1_ > 0 && lvt_24_1_ > 0.5) || (lvt_20_1_ < 0 && lvt_22_1_ > 0.5) || (lvt_20_1_ > 0 && lvt_22_1_ < 0.5)) ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT;
    }
    
    public boolean onBlockActivated(BlockState p_220051_1_, final World p_220051_2_, final BlockPos p_220051_3_, final PlayerEntity p_220051_4_, final Hand p_220051_5_, final BlockRayTraceResult p_220051_6_) {
        if (this.material == Material.IRON) {
            return false;
        }
        p_220051_1_ = (BlockState)p_220051_1_.cycle((IProperty)DoorBlock.OPEN);
        p_220051_2_.setBlockState(p_220051_3_, p_220051_1_, 10);
        p_220051_2_.playEvent(p_220051_4_, ((boolean)p_220051_1_.get((IProperty)DoorBlock.OPEN)) ? this.getOpenSound() : this.getCloseSound(), p_220051_3_, 0);
        return true;
    }
    
    public void toggleDoor(final World p_176512_1_, final BlockPos p_176512_2_, final boolean p_176512_3_) {
        final BlockState lvt_4_1_ = p_176512_1_.getBlockState(p_176512_2_);
        if (lvt_4_1_.getBlock() != this || (boolean)lvt_4_1_.get((IProperty)DoorBlock.OPEN) == p_176512_3_) {
            return;
        }
        p_176512_1_.setBlockState(p_176512_2_, (BlockState)lvt_4_1_.with((IProperty)DoorBlock.OPEN, (Comparable)p_176512_3_), 10);
        this.playSound(p_176512_1_, p_176512_2_, p_176512_3_);
    }
    
    public void neighborChanged(final BlockState p_220069_1_, final World p_220069_2_, final BlockPos p_220069_3_, final Block p_220069_4_, final BlockPos p_220069_5_, final boolean p_220069_6_) {
       
    }
    
    public boolean isValidPosition(final BlockState p_196260_1_, final IWorldReader p_196260_2_, final BlockPos p_196260_3_) {
        final BlockPos lvt_4_1_ = p_196260_3_.down();
        final BlockState lvt_5_1_ = p_196260_2_.getBlockState(lvt_4_1_);
        if (p_196260_1_.get((IProperty)DoorBlock.HALF) == DoubleBlockHalf.LOWER) {
            return lvt_5_1_.func_224755_d((IBlockReader)p_196260_2_, lvt_4_1_, Direction.UP);
        }
        return lvt_5_1_.getBlock() == this;
    }
    
    private void playSound(final World p_196426_1_, final BlockPos p_196426_2_, final boolean p_196426_3_) {
        p_196426_1_.playEvent((PlayerEntity)null, p_196426_3_ ? this.getOpenSound() : this.getCloseSound(), p_196426_2_, 0);
    }
    
    public PushReaction getPushReaction(final BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }
    
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
    
    public BlockState rotate(final BlockState p_185499_1_, final Rotation p_185499_2_) {
        return (BlockState)p_185499_1_.with((IProperty)DoorBlock.FACING, (Comparable)p_185499_2_.rotate((Direction)p_185499_1_.get((IProperty)DoorBlock.FACING)));
    }
    
    public BlockState mirror(final BlockState p_185471_1_, final Mirror p_185471_2_) {
        if (p_185471_2_ == Mirror.NONE) {
            return p_185471_1_;
        }
        return (BlockState)p_185471_1_.rotate(p_185471_2_.toRotation((Direction)p_185471_1_.get((IProperty)DoorBlock.FACING))).cycle((IProperty)DoorBlock.HINGE);
    }
    
    @OnlyIn(Dist.CLIENT)
    public long getPositionRandom(final BlockState p_209900_1_, final BlockPos p_209900_2_) {
        return MathHelper.getCoordinateRandom(p_209900_2_.getX(), p_209900_2_.down((int)((p_209900_1_.get((IProperty)DoorBlock.HALF) != DoubleBlockHalf.LOWER) ? 1 : 0)).getY(), p_209900_2_.getZ());
    }
    
    protected void fillStateContainer(final StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(new IProperty[] { (IProperty)DoorBlock.HALF, (IProperty)DoorBlock.FACING, (IProperty)DoorBlock.OPEN, (IProperty)DoorBlock.HINGE});
    }
    
    static {
        FACING = HorizontalBlock.HORIZONTAL_FACING;
        OPEN = BlockStateProperties.OPEN;
        HINGE = BlockStateProperties.DOOR_HINGE;
        HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
        SOUTH_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
        NORTH_AABB = Block.makeCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
        WEST_AABB = Block.makeCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
        EAST_AABB = Block.makeCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
    }
    
    static class DoorBlock$1 {
        private static int[] field_210338_b;
		private static int[] field_185789_a;

		static {
            DoorBlock$1.field_210338_b = new int[PathType.values().length];
            try {
                DoorBlock$1.field_210338_b[PathType.LAND.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                DoorBlock$1.field_210338_b[PathType.WATER.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
            try {
                DoorBlock$1.field_210338_b[PathType.AIR.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError3) {}
            DoorBlock$1.field_185789_a = new int[Direction.values().length];
            try {
                DoorBlock$1.field_185789_a[Direction.EAST.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError4) {}
            try {
                DoorBlock$1.field_185789_a[Direction.SOUTH.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError5) {}
            try {
                DoorBlock$1.field_185789_a[Direction.WEST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError6) {}
            try {
                DoorBlock$1.field_185789_a[Direction.NORTH.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError7) {}
        }
    }
}
