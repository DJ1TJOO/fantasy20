package nl.fantasynetworkmc.fantasy20.blocks.building.wallframe;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import nl.fantasynetworkmc.fantasy20.PanelTypes;
import nl.fantasynetworkmc.fantasy20.blocks.ModBlocks;
import nl.fantasynetworkmc.fantasy20.items.ModItems;

public class WallFrame extends Block {

    public static final BooleanProperty CONNECTED_NORTH = BooleanProperty.create("connected_north");
    public static final BooleanProperty CONNECTED_SOUTH = BooleanProperty.create("connected_south");
    public static final BooleanProperty CONNECTED_WEST = BooleanProperty.create("connected_west");
    public static final BooleanProperty CONNECTED_EAST = BooleanProperty.create("connected_east");
    public static final BooleanProperty CONNECTED_UP = BooleanProperty.create("connected_up");
    public static final BooleanProperty CONNECTED_DOWN = BooleanProperty.create("connected_down");
    public static final IntegerProperty FRAME_TYPE = IntegerProperty.create("frame_type", 0, PanelTypes.values().length - 1);
    	
	public WallFrame() {
		super(Properties.create(Material.WOOD)
				.hardnessAndResistance(2, 10)
				.sound(SoundType.WOOD)
				.harvestTool(ToolType.AXE)
				.harvestLevel(0));
		setRegistryName("fantasy20:wall_frame");
		this.setDefaultState(this.getDefaultState().with(CONNECTED_UP, Boolean.FALSE).with(CONNECTED_DOWN, Boolean.FALSE).with(CONNECTED_EAST, Boolean.FALSE).with(CONNECTED_WEST, Boolean.FALSE).with(CONNECTED_SOUTH, Boolean.FALSE).with(CONNECTED_NORTH, Boolean.FALSE).with(FRAME_TYPE, PanelTypes.WOODEN.getValue()));
		
	}
	
	@Override
	public int getHarvestLevel(BlockState state) {
		if(state.has(FRAME_TYPE)) {
			switch (state.get(FRAME_TYPE)) {
				case 1: //wooden
					return 0;
				case 2: //stone
					return 1;
				case 3: //metal
					return 3;

				default:
					break;
			}
		}
		return 0;
	}
	
	@Override
	public SoundType getSoundType(BlockState state) {
		if(state.has(FRAME_TYPE)) {
			switch (state.get(FRAME_TYPE)) {
				case 1: //wooden
					return SoundType.WOOD;
				case 2: //stone
					return SoundType.STONE;
				case 3: //metal
					return SoundType.METAL;

				default:
					break;
			}
		}
		return SoundType.WOOD;
	}
	
	@Override
	public ToolType getHarvestTool(BlockState state) {
		if(state.has(FRAME_TYPE)) {
			switch (state.get(FRAME_TYPE)) {
				case 1: //wooden
					return ToolType.AXE;
				case 2: //stone
					return ToolType.PICKAXE;
				case 3: //metal
					return ToolType.PICKAXE;

				default:
					break;
			}
		}
		return ToolType.AXE;
	}
	
	@Override
	public float getBlockHardness(BlockState state, IBlockReader worldIn, BlockPos pos) {
		if(state.has(FRAME_TYPE)) {
			switch (state.get(FRAME_TYPE)) {
				case 1: //wooden
					return 10;
				case 2: //stone
					return 20f;
				case 3: //metal
					return 50;

				default:
					break;
			}
		}
		return 2;
	}
	
	@Override
	public float getExplosionResistance(BlockState state, IWorldReader world, BlockPos pos, Entity exploder,
			Explosion explosion) {
		//18000000 | bedrock
		if(state.has(FRAME_TYPE)) {
			switch (state.get(FRAME_TYPE)) {
				case 1: //wooden
					return 18000000;
				case 2: //stone
					return 19000000;
				case 3: //metal
					return 20000000;

				default:
					break;
			}
		}
		return 18000000;
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(new IProperty[] {CONNECTED_UP, CONNECTED_DOWN, CONNECTED_NORTH, CONNECTED_SOUTH, CONNECTED_WEST, CONNECTED_EAST, FRAME_TYPE});
		builder.add(BlockStateProperties.HORIZONTAL_FACING);
	}
	
	@Override
	public BlockState getExtendedState(BlockState state, IBlockReader world, BlockPos pos) {
		return state.with(CONNECTED_UP, isSideConnectable(world, pos, Direction.UP, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_DOWN, isSideConnectable(world, pos, Direction.DOWN, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_EAST, isSideConnectable(world, pos, Direction.EAST, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_WEST, isSideConnectable(world, pos, Direction.WEST, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_SOUTH, isSideConnectable(world, pos, Direction.SOUTH, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_NORTH, isSideConnectable(world, pos, Direction.NORTH, state.get(BlockStateProperties.HORIZONTAL_FACING)));
	}
	
	@Override
	public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn,
			BlockRayTraceResult hit) {
		worldIn.setBlockState(pos, state.with(CONNECTED_UP, isSideConnectable(worldIn, pos, Direction.UP, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_DOWN, isSideConnectable(worldIn, pos, Direction.DOWN, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_EAST, isSideConnectable(worldIn, pos, Direction.EAST, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_WEST, isSideConnectable(worldIn, pos, Direction.WEST, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_SOUTH, isSideConnectable(worldIn, pos, Direction.SOUTH, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_NORTH, isSideConnectable(worldIn, pos, Direction.NORTH, state.get(BlockStateProperties.HORIZONTAL_FACING))));
		if(player.isSneaking() && player.getHeldItem(handIn).getItem().equals(Items.AIR)) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof WallFrameTile) {
				WallFrameTile te2 = (WallFrameTile) te;
				if(!te2.getPanelType().equals(PanelTypes.NONE)) {
					switch (te2.getPanelType()) {
					case WOODEN:
						spawnAsEntity(worldIn, pos, new ItemStack(ModItems.WOODEN_PANEL, 1));
						break;
					case STONE:
						spawnAsEntity(worldIn, pos, new ItemStack(ModItems.STONE_PANEL, 1));
						break;
					case METAL:
						spawnAsEntity(worldIn, pos, new ItemStack(ModItems.METAL_PANEL, 1));
						break;

					default:
						break;
					}
					te2.setPanelType(PanelTypes.NONE);
				}
			}
		}
		if(player.getHeldItem(handIn).getItem().equals(ModItems.WOODEN_PANEL)) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof WallFrameTile) {
				WallFrameTile te2 = (WallFrameTile) te;
				List<WallFrameTile> list = new ArrayList<WallFrameTile>();
				list = getNeighboringTiles(te2, list);
				if(te2.getPanelType().equals(PanelTypes.NONE)) {
					if(player.getHeldItem(handIn).getCount() > 0) {
						te2.setPanelType(PanelTypes.WOODEN);
						te2.markDirty();
						player.getHeldItem(handIn).shrink(1);
					}
				}
				for (WallFrameTile woodenFloorFrameTile : list) {
					if(woodenFloorFrameTile.getPanelType().equals(PanelTypes.NONE)) {
						if(player.getHeldItem(handIn).getCount() > 0) {
							woodenFloorFrameTile.setPanelType(PanelTypes.WOODEN);
							woodenFloorFrameTile.markDirty();
							player.getHeldItem(handIn).shrink(1);
						}
					}
				}
			}
		} else if(player.getHeldItem(handIn).getItem().equals(ModItems.STONE_PANEL)) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof WallFrameTile) {
				WallFrameTile te2 = (WallFrameTile) te;
				List<WallFrameTile> list = new ArrayList<WallFrameTile>();
				list = getNeighboringTiles(te2, list);
				if(te2.getPanelType().equals(PanelTypes.NONE)) {
					if(player.getHeldItem(handIn).getCount() > 0) {
						te2.setPanelType(PanelTypes.STONE);
						te2.markDirty();
						player.getHeldItem(handIn).shrink(1);
					}
				}
				for (WallFrameTile woodenFloorFrameTile : list) {
					if(woodenFloorFrameTile.getPanelType().equals(PanelTypes.NONE)) {
						if(player.getHeldItem(handIn).getCount() > 0) {
							woodenFloorFrameTile.setPanelType(PanelTypes.STONE);
							woodenFloorFrameTile.markDirty();
							player.getHeldItem(handIn).shrink(1);
						}
					}
				}
			}
		} else if(player.getHeldItem(handIn).getItem().equals(ModItems.METAL_PANEL)) {
			TileEntity te = worldIn.getTileEntity(pos);
			if(te instanceof WallFrameTile) {
				WallFrameTile te2 = (WallFrameTile) te;
				List<WallFrameTile> list = new ArrayList<WallFrameTile>();
				list = getNeighboringTiles(te2, list);
				if(te2.getPanelType().equals(PanelTypes.NONE)) {
					if(player.getHeldItem(handIn).getCount() > 0) {
						te2.setPanelType(PanelTypes.METAL);
						te2.markDirty();
						player.getHeldItem(handIn).shrink(1);
					}
				}
				for (WallFrameTile woodenFloorFrameTile : list) {
					if(woodenFloorFrameTile.getPanelType().equals(PanelTypes.NONE)) {
						if(player.getHeldItem(handIn).getCount() > 0) {
							woodenFloorFrameTile.setPanelType(PanelTypes.METAL);
							woodenFloorFrameTile.markDirty();
							player.getHeldItem(handIn).shrink(1);
						}
					}
				}
			}
		} else if(player.getHeldItem(handIn).getItem().equals(ModItems.STONE_FRAME_UPGRADE)) {
			if(state.has(FRAME_TYPE)) {
				if(state.get(FRAME_TYPE) == PanelTypes.NONE.getValue()) {
					state = state.with(FRAME_TYPE, PanelTypes.STONE.getValue());
					player.getHeldItem(handIn).shrink(1);
					worldIn.setBlockState(pos, state);
				}
			}
		} else if(player.getHeldItem(handIn).getItem().equals(ModItems.METAL_FRAME_UPGRADE)) {
			if(state.has(FRAME_TYPE)) {
				if(state.get(FRAME_TYPE) == PanelTypes.NONE.getValue()) {
					state = state.with(FRAME_TYPE, PanelTypes.METAL.getValue());
					player.getHeldItem(handIn).shrink(1);
					worldIn.setBlockState(pos, state);
				}
			}
		}
		return true;
	}
	
	@Override
	public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		//System.err.println("a");
		if(worldIn.isRemote) {
			return;
		}
		//System.err.println("b");
		if(state.getBlock() instanceof WallFrame && newState.getBlock() instanceof WallFrame) {
			return;
		}
		//System.err.println("c");
		TileEntity te = worldIn.getTileEntity(pos);
		//System.err.println(te.getType().getRegistryName());
		if(te instanceof WallFrameTile) {
			//System.err.println("d");
			WallFrameTile te2 = (WallFrameTile) te;
			switch (te2.getPanelType()) {
			case WOODEN:
				spawnAsEntity(worldIn, pos, new ItemStack(ModItems.WOODEN_PANEL, 1));
				break;
			case STONE:
				spawnAsEntity(worldIn, pos, new ItemStack(ModItems.STONE_PANEL, 1));
				break;
			case METAL:
				spawnAsEntity(worldIn, pos, new ItemStack(ModItems.METAL_PANEL, 1));
				break;

			default:
				break;
			}
		}
		//System.err.println("e");
		if(state.getBlock() instanceof WallFrame && !(newState.getBlock() instanceof WallFrame)) {
			//System.err.println("f");
			TileEntity te3 = worldIn.getTileEntity(pos);
			if(te3 instanceof WallFrameTile) {
				//System.err.println("g");
				worldIn.removeTileEntity(pos);
			}
		}
	}
	
	@Override
	public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, TileEntity te,
			ItemStack stack) {
		super.harvestBlock(worldIn, player, pos, state, te, stack);
	}
	
	private List<WallFrameTile> getNeighboringTiles(WallFrameTile te, List<WallFrameTile> list) {
		TileEntity north = te.getWorld().getTileEntity(te.getPos().north());
		TileEntity south = te.getWorld().getTileEntity(te.getPos().south());
		TileEntity west = te.getWorld().getTileEntity(te.getPos().west());
		TileEntity east = te.getWorld().getTileEntity(te.getPos().east());
		TileEntity up = te.getWorld().getTileEntity(te.getPos().up());
		TileEntity down = te.getWorld().getTileEntity(te.getPos().down());
		if(north instanceof WallFrameTile && north.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING) && north.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING))) {
			if(!list.contains((WallFrameTile) north)) {
				list.add((WallFrameTile) north);
				list.addAll(getNeighboringTiles((WallFrameTile)north, list));
			}
		}
		if(south instanceof WallFrameTile && south.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING) && south.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING))) {
			if(!list.contains((WallFrameTile) south)) {
				list.add((WallFrameTile) south);
				list.addAll(getNeighboringTiles((WallFrameTile)south, list));
			}
		}
		if(west instanceof WallFrameTile && west.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING) && west.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING))) {
			if(!list.contains((WallFrameTile) west)) {
				list.add((WallFrameTile) west);
				list.addAll(getNeighboringTiles((WallFrameTile)west, list));
			}
		}
		if(east instanceof WallFrameTile && east.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING) && east.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING))) {
			if(!list.contains((WallFrameTile) east)) {
				list.add((WallFrameTile) east);
				list.addAll(getNeighboringTiles((WallFrameTile)east, list));
			}
		}
		if(up instanceof WallFrameTile && up.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING) && up.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING))) {
			if(!list.contains((WallFrameTile) up)) {
				list.add((WallFrameTile) up);
				list.addAll(getNeighboringTiles((WallFrameTile)up, list));
			}
		}
		if(down instanceof WallFrameTile && down.getBlockState().has(BlockStateProperties.HORIZONTAL_FACING) && down.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING).equals(te.getBlockState().get(BlockStateProperties.HORIZONTAL_FACING))) {
			if(!list.contains((WallFrameTile) down)) {
				list.add((WallFrameTile) down);
				list.addAll(getNeighboringTiles((WallFrameTile)down, list));
			}
		}
		return list;
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new WallFrameTile();
	}
	
	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {		
		state = state.with(CONNECTED_UP, isSideConnectable(world, pos, Direction.UP, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_DOWN, isSideConnectable(world, pos, Direction.DOWN, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_EAST, isSideConnectable(world, pos, Direction.EAST, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_WEST, isSideConnectable(world, pos, Direction.WEST, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_SOUTH, isSideConnectable(world, pos, Direction.SOUTH, state.get(BlockStateProperties.HORIZONTAL_FACING))).with(CONNECTED_NORTH, isSideConnectable(world, pos, Direction.NORTH, state.get(BlockStateProperties.HORIZONTAL_FACING)));
		world.setBlockState(pos, state);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
	    Direction direction = context.getPlacementHorizontalFacing().getOpposite();
		context.getWorld().notifyNeighborsOfStateChange(context.getPos(), this);
		return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, direction).with(CONNECTED_UP, isSideConnectable(context.getWorld(), context.getPos(), Direction.UP, direction)).with(CONNECTED_DOWN, isSideConnectable(context.getWorld(), context.getPos(), Direction.DOWN, direction)).with(CONNECTED_EAST, isSideConnectable(context.getWorld(), context.getPos(), Direction.EAST, direction)).with(CONNECTED_WEST, isSideConnectable(context.getWorld(), context.getPos(), Direction.WEST, direction)).with(CONNECTED_SOUTH, isSideConnectable(context.getWorld(), context.getPos(), Direction.SOUTH, direction)).with(CONNECTED_NORTH, isSideConnectable(context.getWorld(), context.getPos(), Direction.NORTH, direction));
	}
	
	private boolean isSideConnectable (IBlockReader world, BlockPos pos, Direction side, Direction facing) {
        final BlockState state = world.getBlockState(pos.offset(side));
        if(state == null) {
        	return false;
        }
        if(state.getBlock() != this) {
        	return false;
        }
        if(!state.get(BlockStateProperties.HORIZONTAL_FACING).equals(facing)) {
        	return false;
        }
        return true;
    }

	private VoxelShape generateShape(BlockState state, IBlockReader reader, BlockPos pos) {
	    List<VoxelShape> shapes = new ArrayList<>();
	   // System.err.println("east " + state.get(CONNECTED_EAST) + " west " + state.get(CONNECTED_WEST) + " south " + state.get(CONNECTED_SOUTH) + " north " + state.get(CONNECTED_NORTH));
	    TileEntity te = reader.getTileEntity(pos);
	    if(te instanceof WallFrameTile) {
	    	WallFrameTile te2 = (WallFrameTile)te;
	    	if(!te2.getPanelType().equals(PanelTypes.NONE)) {
	    	    if(state.has(BlockStateProperties.HORIZONTAL_FACING)) {
		    		if(state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH)) {
			    		shapes.add(Block.makeCuboidShape(0, 0, 0.125, 16, 16, 0.875));
		    		} else if(state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH)) {
			    		shapes.add(Block.makeCuboidShape(16, 16, 15.125, 0, 0, 15.875));
		    		} else if(state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.WEST)) {
			    		shapes.add(Block.makeCuboidShape(15.125, 16, 16, 15.875, 0, 0));
		    		} else if(state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.EAST)) {
			    		shapes.add(Block.makeCuboidShape(0.875, 16, 16, 0.125, 0, 0));
		    		}
	    		}
	    	}
	    }
	    if(state.has(BlockStateProperties.HORIZONTAL_FACING)) {
		    if(state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH)) {
			    if(!state.has(CONNECTED_UP) || !state.get(CONNECTED_UP)) {
				    shapes.add(Block.makeCuboidShape(16, 16, 1, 0, 15, 0)); // up
			    }
			    if(!state.has(CONNECTED_DOWN) || !state.get(CONNECTED_DOWN)) {
			    	shapes.add(Block.makeCuboidShape(16, 1, 1, 0, 0, 0)); // down
			    }
			    if(!state.has(CONNECTED_EAST) || !state.get(CONNECTED_EAST)) {
				    shapes.add(Block.makeCuboidShape(16, 0, 0, 15, 16, 1)); // east
			    }
			    if(!state.has(CONNECTED_WEST) || !state.get(CONNECTED_WEST)) {
				    shapes.add(Block.makeCuboidShape(0, 0, 0, 1, 16, 1)); // west
			    }
		    } else if(state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH)) {
		    	if(!state.has(CONNECTED_UP) || !state.get(CONNECTED_UP)) {
				    shapes.add(Block.makeCuboidShape(16, 16, 16, 0, 15, 15)); // up
			    }
			    if(!state.has(CONNECTED_DOWN) || !state.get(CONNECTED_DOWN)) {
			    	shapes.add(Block.makeCuboidShape(0, 0, 15, 16, 1, 16)); // down
			    }
			    if(!state.has(CONNECTED_EAST) || !state.get(CONNECTED_EAST)) {
				    shapes.add(Block.makeCuboidShape(15, 0, 15, 16, 16, 16)); // east
			    }
			    if(!state.has(CONNECTED_WEST) || !state.get(CONNECTED_WEST)) {
				    shapes.add(Block.makeCuboidShape(1, 0, 15, 0, 16, 16)); // west
			    }
		    } else if(state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.WEST)) {
		    	if(!state.has(CONNECTED_UP) || !state.get(CONNECTED_UP)) {
				    shapes.add(Block.makeCuboidShape(16, 16, 16, 15, 15, 0)); // up
			    }
			    if(!state.has(CONNECTED_DOWN) || !state.get(CONNECTED_DOWN)) {
			    	shapes.add(Block.makeCuboidShape(15, 0, 0, 16, 1, 16)); // down
			    }
			    if(!state.has(CONNECTED_SOUTH) || !state.get(CONNECTED_SOUTH)) {
				    shapes.add(Block.makeCuboidShape(15, 0, 15, 16, 16, 16)); // south
			    }
			    if(!state.has(CONNECTED_NORTH) || !state.get(CONNECTED_NORTH)) {
				    shapes.add(Block.makeCuboidShape(15, 0, 1, 16, 16, 0)); // north
			    }
		    } else if(state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.EAST)) {
		    	if(!state.has(CONNECTED_UP) || !state.get(CONNECTED_UP)) {
				    shapes.add(Block.makeCuboidShape(1, 16, 16, 0, 15, 0)); // up
			    }
			    if(!state.has(CONNECTED_DOWN) || !state.get(CONNECTED_DOWN)) {
			    	shapes.add(Block.makeCuboidShape(0, 0, 0, 1, 1, 16)); // down
			    }
			    if(!state.has(CONNECTED_SOUTH) || !state.get(CONNECTED_SOUTH)) {
				    shapes.add(Block.makeCuboidShape(0, 0, 15, 1, 16, 16)); // south
			    }
			    if(!state.has(CONNECTED_NORTH) || !state.get(CONNECTED_NORTH)) {
				    shapes.add(Block.makeCuboidShape(0, 0, 1, 1, 16, 0)); // north
			    }
		    }
	    }
//	    if((!state.has(CONNECTED_SOUTH) || !state.get(CONNECTED_SOUTH)) && (!state.has(BlockStateProperties.HORIZONTAL_FACING) || (!state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH) && !state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH)))) {
//		    shapes.add(Block.makeCuboidShape(1, 0, 15, 15, 1, 16)); // south
//	    }
//	    if((!state.has(CONNECTED_NORTH) || !state.get(CONNECTED_NORTH)) && (!state.has(BlockStateProperties.HORIZONTAL_FACING) || (!state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.NORTH) && !state.get(BlockStateProperties.HORIZONTAL_FACING).equals(Direction.SOUTH)))) {
//		    shapes.add(Block.makeCuboidShape(1, 0, 0, 15, 1, 1)); // north
//	    }

	    VoxelShape result = VoxelShapes.empty();
	    for(VoxelShape shape : shapes)
	    {
	        result = VoxelShapes.combine(result, shape, IBooleanFunction.OR);
	    }
	    return result.simplify();
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
	    return generateShape(state, reader, pos);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
	    return generateShape(state, reader, pos);
	}
	
	@Override
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		worldIn.addEntity(new ItemEntity(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.FLOOR_FRAME, 1)));
	}

	@Override
	public boolean isSolid(BlockState state) {
		return false;
	}
	
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.SOLID;
	}
	
}
