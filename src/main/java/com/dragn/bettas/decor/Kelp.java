package com.dragn.bettas.decor;

import net.minecraft.block.*;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

import javax.annotation.Nullable;

public class Kelp extends KelpBlock {

    public static final VoxelShape SHAPE = VoxelShapes.join(
            Block.box(6.35264, 0.9596, 3.84, 9.647359999999999, 4.6204, 3.84),
            Block.box(8, 0.9596, 2.19264, 8, 4.6204, 5.487359999999999), IBooleanFunction.OR);

    public static final DirectionProperty FACING = HorizontalBlock.FACING;


    public Kelp() {
        super(Properties.copy(Blocks.KELP_PLANT));
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext blockItemUseContext) {
        return this.defaultBlockState().setValue(FACING, blockItemUseContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }
}
