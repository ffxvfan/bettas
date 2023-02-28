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
import java.util.stream.Stream;

public class Seagrass extends SeaGrassBlock {

    public static final VoxelShape SHAPE = Stream.of(
            Block.box(6.08, 0.8300000000000001, 11.04, 9.92, 4.67, 11.04),
            Block.box(8.96, 0.8300000000000001, 10.08, 8.96, 4.67, 13.92),
            Block.box(7.04, 0.8300000000000001, 10.08, 7.04, 4.67, 13.92),
            Block.box(6.08, 0.8300000000000001, 12.96, 9.92, 4.67, 12.96)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    public Seagrass() {
        super(AbstractBlock.Properties.copy(Blocks.SEAGRASS));
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
