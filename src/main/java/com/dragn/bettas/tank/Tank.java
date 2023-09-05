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

    private static final VoxelShape NORTH = createBox(TankTileRenderer.NORTH_VERTS);
    private static final VoxelShape EAST = createBox(TankTileRenderer.EAST_VERTS);
    private static final VoxelShape SOUTH = createBox(TankTileRenderer.SOUTH_VERTS);
    private static final VoxelShape WEST = createBox(TankTileRenderer.WEST_VERTS);
    private static final VoxelShape BOTTOM = createBox(TankTileRenderer.BOTTOM_VERTS);

    private static final VoxelShape[] SHAPES = {SOUTH, WEST, NORTH, EAST};

    private static VoxelShape createBox(float[] v) {
        return VoxelShapes.box(v[0], v[1], v[2], v[3], v[4], v[5]);
    }



    public VoxelShape shape = VoxelShapes.or(NORTH, EAST, SOUTH, WEST, BOTTOM);

    public Tank() {
        super(AbstractBlock.Properties.of(Material.GLASS).strength(0.7f).sound(SoundType.GLASS).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader iBlockReader, BlockPos pos, ISelectionContext context) {
        return this.shape;
    }

    public void addSide(Direction direction) {
        this.shape = VoxelShapes.join(this.shape, SHAPES[direction.get2DDataValue()], IBooleanFunction.OR);
    }

    public void removeSide(Direction direction) {
        this.shape = VoxelShapes.join(this.shape, SHAPES[direction.get2DDataValue()], IBooleanFunction.ONLY_FIRST);
    }

    @Override
    public BlockState updateShape(BlockState state1, Direction direction, BlockState state2, IWorld iWorld, BlockPos pos1, BlockPos pos2) {
        if(direction == Direction.UP || direction == Direction.DOWN) {
            return state1;
        }

        Block block = state2.getBlock();
        if(!iWorld.isClientSide()) {
            if (block.is(this)) {
                ((TankTile) iWorld.getBlockEntity(pos1)).addConnected(direction);
                ((TankTile) iWorld.getBlockEntity(pos2)).addConnected(direction.getOpposite());
                this.removeSide(direction);
                ((Tank) block).removeSide(direction.getOpposite());
            } else if (block.is(Blocks.AIR)) {
                ((TankTile) iWorld.getBlockEntity(pos1)).removeConnected(direction);
                this.addSide(direction);
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
