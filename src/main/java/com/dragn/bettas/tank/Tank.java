package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.network.NetworkManager;
import com.dragn.bettas.network.RemoveDecorRequest;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.FishBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.*;

public class Tank extends Block implements IWaterLoggable {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final BooleanProperty DOWN = BlockStateProperties.DOWN;
    private static final BooleanProperty NORTH_UP = BooleanProperty.create("north_up");
    private static final BooleanProperty EAST_UP = BooleanProperty.create("east_up");
    private static final BooleanProperty SOUTH_UP = BooleanProperty.create("south_up");
    private static final BooleanProperty WEST_UP = BooleanProperty.create("west_up");
    private static final BooleanProperty NORTH_EAST = BooleanProperty.create("north_east");
    private static final BooleanProperty NORTH_WEST = BooleanProperty.create("north_west");
    private static final BooleanProperty SOUTH_EAST = BooleanProperty.create("south_east");
    private static final BooleanProperty SOUTH_WEST = BooleanProperty.create("south_west");

    private static final VoxelShape NORTH_VOXEL = VoxelShapes.or(Block.box(0, 0, 0, 16, 16, 0.5), Block.box(0.5, 15.5, 0, 15.5, 16, 0.5));
    private static final VoxelShape EAST_VOXEL = VoxelShapes.or(Block.box(15.5, 0, 0, 16, 16, 16), Block.box(15.5, 15.5, 0.5, 16, 16, 15.5));
    private static final VoxelShape SOUTH_VOXEL = VoxelShapes.or(Block.box(0, 0, 15.5, 16, 16, 16), Block.box(0.5, 15.5, 15.5, 15.5, 16, 16));
    private static final VoxelShape WEST_VOXEL = VoxelShapes.or(Block.box(0, 0, 0, 0.5, 16, 16), Block.box(0, 15.5, 0.5, 0.5, 16, 15.5));
    private static final VoxelShape DOWN_VOXEL = Block.box(-0.05, -0.05, -0.05, 16.1, 0.5, 16.1);

    private static final ArrayList<String> DECOR_ITEMS = new ArrayList<>(Arrays.asList("big_log_item", "filter_item", "heater_item", "large_rock_item", "medium_rock_item", "small_log_item", "small_rock_item", "kelp", "seagrass", "sand"));


    public Tank() {
        super(AbstractBlock.Properties.of(Material.GLASS).noOcclusion().strength(0.7F));
        registerDefaultState(getStateDefinition().any()
                .setValue(WATERLOGGED, false)
                .setValue(NORTH_UP, false)
                .setValue(EAST_UP, false)
                .setValue(SOUTH_UP, false)
                .setValue(WEST_UP, false)
                .setValue(NORTH_EAST, false)
                .setValue(NORTH_WEST, false)
                .setValue(SOUTH_EAST, false)
                .setValue(SOUTH_WEST, false)
                .setValue(DOWN, false)
        );
    }

    @Override
    public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
        boolean adjacent = state2.is(this);
        boolean up = state1.getValue(NORTH_UP) && state1.getValue(EAST_UP) && state1.getValue(SOUTH_UP) && state1.getValue(WEST_UP);
        switch(direction) {
            case UP:
                boolean north = adjacent || (state1.getValue(NORTH_EAST) && state1.getValue(NORTH_WEST));
                boolean east =  adjacent || (state1.getValue(NORTH_EAST) && state1.getValue(SOUTH_EAST));
                boolean south = adjacent || (state1.getValue(SOUTH_EAST) && state1.getValue(SOUTH_WEST));
                boolean west =  adjacent || (state1.getValue(NORTH_WEST) && state1.getValue(SOUTH_WEST));
                return state1.setValue(NORTH_UP, north).setValue(EAST_UP, east).setValue(SOUTH_UP, south).setValue(WEST_UP, west);
            case NORTH:
                return state1.setValue(NORTH_UP, up || adjacent).setValue(NORTH_EAST, adjacent).setValue(NORTH_WEST, adjacent);
            case EAST:
                return state1.setValue(EAST_UP, up || adjacent).setValue(NORTH_EAST, adjacent).setValue(SOUTH_EAST, adjacent);
            case SOUTH:
                return state1.setValue(SOUTH_UP, up || adjacent).setValue(SOUTH_EAST, adjacent).setValue(SOUTH_WEST, adjacent);
            case WEST:
                return state1.setValue(WEST_UP, up || adjacent).setValue(NORTH_WEST, adjacent).setValue(SOUTH_WEST, adjacent);
            case DOWN:
                return state1.setValue(DOWN, adjacent);
        }
        return super.updateShape(state1, direction, state2, world, pos1, pos2);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader iBlockReader, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = VoxelShapes.empty();
        if(!iBlockReader.getBlockState(pos.north()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, NORTH_VOXEL, IBooleanFunction.OR);
        }
        if(!iBlockReader.getBlockState(pos.east()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, EAST_VOXEL, IBooleanFunction.OR);
        }
        if(!iBlockReader.getBlockState(pos.south()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, SOUTH_VOXEL, IBooleanFunction.OR);
        }
        if(!iBlockReader.getBlockState(pos.west()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, WEST_VOXEL, IBooleanFunction.OR);
        }
        if(!iBlockReader.getBlockState(pos.below()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, DOWN_VOXEL, IBooleanFunction.OR);
        }
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockPos pos = context.getClickedPos();
        boolean up = context.getLevel().getBlockState(pos.above()).getBlock().is(this);
        boolean north = context.getLevel().getBlockState(pos.north()).getBlock().is(this);
        boolean east = context.getLevel().getBlockState(pos.east()).getBlock().is(this);
        boolean south = context.getLevel().getBlockState(pos.south()).getBlock().is(this);
        boolean west = context.getLevel().getBlockState(pos.west()).getBlock().is(this);
        boolean down = context.getLevel().getBlockState(pos.below()).getBlock().is(this);

        return this.defaultBlockState()
                .setValue(WATERLOGGED, context.getLevel().getFluidState(pos).getType() == Fluids.WATER)
                .setValue(NORTH_UP, up).setValue(EAST_UP, up).setValue(SOUTH_UP, up).setValue(WEST_UP, up)
                .setValue(NORTH_UP, up).setValue(NORTH_EAST, north).setValue(NORTH_WEST, north)
                .setValue(EAST_UP, up).setValue(NORTH_EAST, east).setValue(SOUTH_EAST, east)
                .setValue(SOUTH_UP, up).setValue(SOUTH_EAST, south).setValue(SOUTH_WEST, south)
                .setValue(WEST_UP, up).setValue(NORTH_WEST, west).setValue(SOUTH_WEST, west)
                .setValue(DOWN, down);

    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        return Collections.singletonList(this.asItem().getDefaultInstance());
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if(!world.isClientSide) {
            String name = player.getItemInHand(hand).getItem().getRegistryName().getPath();
            TankTile tankTile = (TankTile) world.getBlockEntity(pos);

            if(DECOR_ITEMS.contains(name)) {
                if(tankTile.decor.put(name, player.getDirection().getCounterClockWise()) == null) {
                    player.getItemInHand(hand).shrink(1);
                }
                world.sendBlockUpdated(pos, state, state, 3);
                return ActionResultType.CONSUME;
            } else if(hand == Hand.MAIN_HAND && name.equals("air") && tankTile.decor.size() > 0) {
                Map.Entry<String, Direction> decor = (Map.Entry<String, Direction>) tankTile.decor.entrySet().toArray()[tankTile.decor.size() - 1];
                tankTile.decor.remove(decor.getKey());
                NetworkManager.INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> (Chunk) world.getChunk(pos)), new RemoveDecorRequest(decor.getKey(), pos));
                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, decorMap(decor.getKey())));

                world.sendBlockUpdated(pos, state, state, 3);
                return ActionResultType.SUCCESS;
            }
        }
        return super.use(state, world, pos, player, hand, blockRayTraceResult);
    }

    public static ItemStack decorMap(String decor) {
        switch(decor) {
            case "big_log_item":
                return BettasMain.BIG_LOG_ITEM.get().getDefaultInstance();
            case "filter_item":
                return BettasMain.FILTER_ITEM.get().getDefaultInstance();
            case "heater_item":
                return BettasMain.HEATER_ITEM.get().getDefaultInstance();
            case "large_rock_item":
                return BettasMain.LARGE_ROCK_ITEM.get().getDefaultInstance();
            case "medium_rock_item":
                return BettasMain.MEDIUM_ROCK_ITEM.get().getDefaultInstance();
            case "small_log_item":
                return BettasMain.SMALL_LOG_ITEM.get().getDefaultInstance();
            case "small_rock_item":
                return BettasMain.SMALL_ROCK_ITEM.get().getDefaultInstance();
            case "seagrass":
                return Items.SEAGRASS.getDefaultInstance();
            case "kelp":
                return Items.KELP.getDefaultInstance();
            case "sand":
                return Items.SAND.getDefaultInstance();
            default:
                return Items.AIR.getDefaultInstance();
        }
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return BettasMain.TANK_TILE.get().create();
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, NORTH_UP, EAST_UP, SOUTH_UP, WEST_UP, NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST, DOWN);
    }
}
