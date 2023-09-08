package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
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

import javax.annotation.Nullable;

public class Tank extends Block {

    public Tank() {
        super(AbstractBlock.Properties.of(Material.GLASS).strength(0.7f).sound(SoundType.GLASS).noOcclusion());
    }



    @Override
    public VoxelShape getShape(BlockState state, IBlockReader iBlockReader, BlockPos pos, ISelectionContext context) {
        TileEntity tile = iBlockReader.getBlockEntity(pos);

        VoxelShape shape = TankTile.BOTTOM;
        if(tile instanceof TankTile) {
            if ((((TankTile) tile).connected & TankTile.CONNECTED_NORTH) == 0) {
                shape = VoxelShapes.join(shape, TankTile.NORTH, IBooleanFunction.OR);
            }
            if ((((TankTile) tile).connected & TankTile.CONNECTED_EAST) == 0) {
                shape = VoxelShapes.join(shape, TankTile.EAST, IBooleanFunction.OR);
            }
            if ((((TankTile) tile).connected & TankTile.CONNECTED_SOUTH) == 0) {
                shape = VoxelShapes.join(shape, TankTile.SOUTH, IBooleanFunction.OR);
            }
            if ((((TankTile) tile).connected & TankTile.CONNECTED_WEST) == 0) {
                shape = VoxelShapes.join(shape, TankTile.WEST, IBooleanFunction.OR);
            }
        }
        return shape;
    }



    @Override
    public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, IWorld iWorld, BlockPos pos1, BlockPos pos2) {
        if(!iWorld.isClientSide() && !(direction == Direction.UP || direction == Direction.DOWN)) {
            Block block = iWorld.getBlockState(pos2).getBlock();
            if (block instanceof Tank) {
                ((TankTile) iWorld.getBlockEntity(pos1)).addConnected(direction);
                ((TankTile) iWorld.getBlockEntity(pos2)).addConnected(direction.getOpposite());
            } else if (block instanceof AirBlock) {
                ((TankTile) iWorld.getBlockEntity(pos1)).removeConnected(direction);
            }
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
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult blockRayTraceResult) {
        TankTile tileEntity = (TankTile) world.getBlockEntity(pos);
        if(!world.isClientSide && hand == Hand.MAIN_HAND) {

        }
        return super.use(state, world, pos, entity, hand, blockRayTraceResult);
    }
}
