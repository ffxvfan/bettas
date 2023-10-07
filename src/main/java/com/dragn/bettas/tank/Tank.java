package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.Decor;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.WATERLOGGED;

public class Tank extends Block implements IWaterLoggable {

    public Tank() {
        super(AbstractBlock.Properties.of(Material.GLASS).strength(0.7f).sound(SoundType.GLASS).noOcclusion());
        this.registerDefaultState(this.getStateDefinition().any().setValue(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader iBlockReader, BlockPos pos, ISelectionContext context) {
        TileEntity tile = iBlockReader.getBlockEntity(pos);
        if(tile instanceof TankTile) {
            return ((TankTile) tile).shape;
        }
        return VoxelShapes.empty();
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, IWorld iWorld, BlockPos pos1, BlockPos pos2) {
        Block block = iWorld.getBlockState(pos2).getBlock();
        if (block instanceof Tank) {
            ((TankTile) iWorld.getBlockEntity(pos1)).addConnected(direction);
            ((TankTile) iWorld.getBlockEntity(pos2)).addConnected(direction.getOpposite());
        } else if (block instanceof AirBlock) {
            ((TankTile) iWorld.getBlockEntity(pos1)).removeConnected(direction);
        }
        return state1;
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
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        if(!world.isClientSide && hand == Hand.MAIN_HAND) {
            TankTile tankTile = (TankTile) world.getBlockEntity(pos);
            Item item = player.getItemInHand(hand).getItem();
            if(item == Items.AIR) {
                ItemStack itemStack = tankTile.removeDecor();
                if(itemStack != null) {
                    world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, itemStack));
                }
            } else if(Decor.ITEM_TO_DECOR.containsKey(item)) {
                tankTile.addDecor(item, player.getDirection());
            }
        }
        return super.use(state, world, pos, player, hand, blockRayTraceResult);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }
}
