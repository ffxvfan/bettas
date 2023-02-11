package com.dragn.bettas.block;

import net.minecraft.block.AbstractGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class Tank extends AbstractGlassBlock {

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    private static final VoxelShape SHAPE =  Stream.of(
            Block.box(15.5, 15.5, 0.5, 16, 16, 15.5),
            Block.box(0, 15.5, 0.5, 0.5, 16, 15.5),
            Block.box(0.5, 15.5, 15.5, 15.5, 16, 16),
            Block.box(0.5, 15.5, 0, 15.5, 16, 0.5),
            Block.box(15.5, 0.5, 0, 16, 16, 0.5),
            Block.box(15.5, 0.5, 15.5, 16, 16, 16),
            Block.box(0, 0.5, 15.5, 0.5, 16, 16),
            Block.box(0, 0.5, 0, 0.5, 16, 0.5),
            Block.box(0, 0, 0, 16, 0.5, 16)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();


    public Tank(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public VoxelShape getVisualShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
}
