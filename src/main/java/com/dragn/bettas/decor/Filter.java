package com.dragn.bettas.decor;

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

public class Filter extends Block {

    public static final VoxelShape SHAPE = Stream.of(
            Block.box(14.16, 14.09, 11.100000000000001, 15.87, 17.509999999999998, 11.384999999999998),
            Block.box(14.16, 14.09, 5.399999999999999, 15.87, 17.509999999999998, 5.685000000000002),
            Block.box(15.87, 10.67, 5.399999999999999, 17.009999999999998, 17.509999999999998, 11.384999999999998),
            Block.box(6.75, 16.085, 5.399999999999999, 9.315000000000001, 16.369999999999997, 11.384999999999998),
            Block.box(15.3, 14.09, 5.685000000000002, 15.584999999999997, 17.225, 11.100000000000001),
            Block.box(14.16, 14.09, 5.685000000000002, 14.445, 16.655, 11.100000000000001),
            Block.box(14.16, 13.805, 5.399999999999999, 15.87, 14.09, 11.384999999999998),
            Block.box(19.86, 15.537799999999997, 6.539999999999999, 21.57, 15.8228, 10.244999999999997)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final DirectionProperty FACING = HorizontalBlock.FACING;


    public Filter() {
        super(Properties.of(Material.STONE).noOcclusion());
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
