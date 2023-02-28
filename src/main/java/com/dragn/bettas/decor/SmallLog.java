package com.dragn.bettas.decor;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
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

public class SmallLog extends Block {

    public static final VoxelShape SHAPE = Stream.of(
            Block.box(13.92, 1.88, 5.780000000000001, 15, 2.96, 7.699999999999999),
            Block.box(14.16, 1.2800000000000002, 2.42, 14.76, 2.12, 4.460000000000001),
            Block.box(12.48, 0.5600000000000005, 4.219999999999999, 14.4, 0.9199999999999999, 4.58),
            Block.box(12.12, -1.12, 4.34, 13.32, -1, 4.460000000000001),
            Block.box(14.04, -2.92, 3.620000000000001, 15, -1.96, 5.539999999999999)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final DirectionProperty FACING = HorizontalBlock.FACING;


    public SmallLog() {
        super(AbstractBlock.Properties.of(Material.WOOD).noOcclusion());
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
