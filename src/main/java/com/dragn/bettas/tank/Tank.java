package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.Decor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
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
import net.minecraftforge.fml.RegistryObject;
import org.lwjgl.system.CallbackI;

import javax.annotation.Nullable;
import java.util.HashMap;

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

    public Tank() {
        super(AbstractBlock.Properties.of(Material.GLASS).noOcclusion().strength(0.7F));
        registerDefaultState(getStateDefinition().any()
                .setValue(WATERLOGGED, false)
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false));
    }

    @Override
    public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, IWorld world, BlockPos pos1, BlockPos pos2) {
        switch(direction) {
            case NORTH:
                return state1.setValue(NORTH, state2.is(this));
            case EAST:
                return state1.setValue(EAST, state2.is(this));
            case SOUTH:
                return state1.setValue(SOUTH, state2.is(this));
            case WEST:
                return state1.setValue(WEST, state2.is(this));
        }
        return super.updateShape(state1, direction, state2, world, pos1, pos2);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader iBlockReader, BlockPos pos, ISelectionContext context) {
        VoxelShape shape = BASE;
        if(!iBlockReader.getBlockState(pos.north()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, NORTH_B, IBooleanFunction.OR);
        }

        if(!iBlockReader.getBlockState(pos.east()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, EAST_B, IBooleanFunction.OR);
        }

        if(!iBlockReader.getBlockState(pos.south()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, SOUTH_B, IBooleanFunction.OR);
        }

        if(!iBlockReader.getBlockState(pos.west()).getBlock().is(this)) {
            shape = VoxelShapes.join(shape, WEST_B, IBooleanFunction.OR);
        }
        return shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState()
                .setValue(WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER)
                .setValue(NORTH, context.getLevel().getBlockState(context.getClickedPos().north()).is(this))
                .setValue(EAST, context.getLevel().getBlockState(context.getClickedPos().east()).is(this))
                .setValue(SOUTH, context.getLevel().getBlockState(context.getClickedPos().south()).is(this))
                .setValue(WEST, context.getLevel().getBlockState(context.getClickedPos().west()).is(this));
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if(!world.isClientSide) {
            String itemName = player.getItemInHand(hand).getItem().getRegistryName().getPath();
            TankTile tankTile = (TankTile) world.getBlockEntity(pos);

            if(Decor.PROPERTY_MAP.get(itemName) != null) {
                Decor decor = new Decor(itemName, player.getDirection().getOpposite().ordinal());
                tankTile.decor.push(decor);
                world.sendBlockUpdated(pos, state, state, -1);
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
