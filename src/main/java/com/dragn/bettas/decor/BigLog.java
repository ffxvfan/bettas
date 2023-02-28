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

public class BigLog extends Block {

    public static final VoxelShape SHAPE = Stream.of(
            Block.box(5.745000000000003, -4.22, 8.379999999999997, 13.515000000000002, -4.035, 10.229999999999999),
            Block.box(5.745000000000003, 1.7000000000000002, 9.489999999999998, 13.515000000000002, 3.55, 9.674999999999999),
            Block.box(5.745000000000003, 1.7000000000000002, 5.234999999999998, 13.515000000000002, 3.55, 5.419999999999998),
            Block.box(5.745000000000003, -2.925, 11.339999999999998, 13.515000000000002, -1.0750000000000002, 11.524999999999999),
            Block.box(5.745000000000003, -2.925, 7.084999999999999, 13.515000000000002, -1.0750000000000002, 7.269999999999998),
            Block.box(5.745000000000003, 4.63, 6.529999999999999, 13.515000000000002, 4.8149999999999995, 8.379999999999997),
            Block.box(5.745000000000003, 0.4049999999999998, 6.529999999999999, 13.515000000000002, 0.5899999999999999, 8.379999999999997),
            Block.box(5.745000000000003, 0.03500000000000014, 8.379999999999997, 13.515000000000002, 0.21999999999999975, 10.229999999999999),
            Block.box(7.965000000000002, 6.695, 2.089999999999998, 10.185, 7.4350000000000005, 3.5699999999999985)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final DirectionProperty FACING = HorizontalBlock.FACING;


    public BigLog() {
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
