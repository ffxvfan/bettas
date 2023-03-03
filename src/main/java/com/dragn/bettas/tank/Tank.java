package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Tank extends Block implements IWaterLoggable {

    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    private static final BooleanProperty NORTH = BlockStateProperties.NORTH;
    private static final BooleanProperty EAST = BlockStateProperties.EAST;
    private static final BooleanProperty SOUTH = BlockStateProperties.SOUTH;
    private static final BooleanProperty WEST = BlockStateProperties.WEST;

    private final VoxelShape BASE = Block.box(0, 0, 0, 16, 1, 16);

    private final VoxelShape WEST_B = Block.box(0, 0.25, 0, 0.25, 16.25, 16);
    private final VoxelShape EAST_B = Block.box(15.75, 0.25, 0, 16, 16.25, 16);
    private final VoxelShape NORTH_B = Block.box(0.25, 0.25, 0, 15.75, 16.25, 0.25);
    private final VoxelShape SOUTH_B = Block.box(0.25, 0.25, 15.75, 15.75, 16.25, 16);

    private final Stack<Block> decor = new Stack<>();


    public Tank() {
        super(AbstractBlock.Properties.of(Material.GLASS).noOcclusion().strength(0.7F));
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }



    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader iBlockReader, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = BASE;
        if(!iBlockReader.getBlockState(pos.north()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, NORTH_B, IBooleanFunction.OR);
            this.getStateDefinition().any().setValue(NORTH, true);
        }

        if(!iBlockReader.getBlockState(pos.east()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, EAST_B, IBooleanFunction.OR);
            this.getStateDefinition().any().setValue(EAST, true);
        }

        if(!iBlockReader.getBlockState(pos.south()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, SOUTH_B, IBooleanFunction.OR);
            this.getStateDefinition().any().setValue(SOUTH, true);
        }

        if(!iBlockReader.getBlockState(pos.west()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, WEST_B, IBooleanFunction.OR);
            this.getStateDefinition().any().setValue(WEST, true);
        }
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState()
                .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false);
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        Item item = player.getItemInHand(hand).getItem();
        if(!world.isClientSide) {
            TankTile tile = (TankTile) world.getBlockEntity(pos);

            if(item == Blocks.SAND.asItem()) {
                tile.substrate = true;
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(BettasMain.SUBSTRATE.get())) {
                    decor.push(BettasMain.SUBSTRATE.get());
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == BettasMain.BIG_LOG_ITEM.get()) {
                tile.bigLog = true;
                tile.bigLogDir = player.getDirection().getCounterClockWise();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(BettasMain.BIG_LOG.get())) {
                    decor.push(BettasMain.BIG_LOG.get());
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == BettasMain.SMALL_LOG_ITEM.get()) {
                tile.smallLog = true;
                tile.smallLogDir = player.getDirection().getCounterClockWise();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(BettasMain.SMALL_LOG.get())) {
                    decor.push(BettasMain.SMALL_LOG.get());
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == BettasMain.LARGE_ROCK_ITEM.get()) {
                tile.largeRock = true;
                tile.largeRockDir = player.getDirection().getOpposite();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(BettasMain.LARGE_ROCK.get())) {
                    decor.push(BettasMain.LARGE_ROCK.get());
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == BettasMain.MEDIUM_ROCK_ITEM.get()) {
                tile.mediumRock = true;
                tile.mediumRockDir = player.getDirection().getCounterClockWise();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(BettasMain.MEDIUM_ROCK.get())) {
                    decor.push(BettasMain.MEDIUM_ROCK.get());
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == BettasMain.SMALL_ROCK_ITEM.get()) {
                tile.smallRock = true;
                tile.smallRockDir = player.getDirection();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(BettasMain.SMALL_ROCK.get())) {
                    decor.push(BettasMain.SMALL_ROCK.get());
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == BettasMain.FILTER_ITEM.get()) {
                tile.filter = true;
                tile.filterDir = player.getDirection().getCounterClockWise();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(BettasMain.FILTER.get())) {
                    decor.push(BettasMain.FILTER.get());
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == BettasMain.HEATER_ITEM.get()) {
                tile.heater = true;
                tile.heaterDir = player.getDirection().getCounterClockWise();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(BettasMain.HEATER.get())) {
                    decor.push(BettasMain.HEATER.get());
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == Blocks.SEAGRASS.asItem()) {
                tile.seagrass = true;
                tile.seagrassDir = player.getDirection();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(Blocks.SEAGRASS)) {
                    decor.push(Blocks.SEAGRASS);
                    player.getItemInHand(hand).shrink(1);
                }
                return ActionResultType.CONSUME;
            } else if(item == Blocks.KELP.asItem()) {
                tile.kelp = true;
                tile.kelpDir = player.getDirection();
                world.sendBlockUpdated(pos, state, state, -1);

                if(!decor.contains(Blocks.KELP)) {
                    decor.push(Blocks.KELP);
                    player.getItemInHand(hand).shrink(1);
                }
            }
        }
        return super.use(state, world, pos, player, hand, blockRayTraceResult);
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
        builder.add(WATERLOGGED, NORTH, EAST, SOUTH, WEST);
    }
}
