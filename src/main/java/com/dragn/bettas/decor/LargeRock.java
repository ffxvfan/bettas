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

public class LargeRock extends Block {

    public static final VoxelShape SHAPE = Stream.of(
            Block.box(8.5, 0.75, 9.75, 10, 2, 11),
            Block.box(5.5, 0.75, 10.5, 7, 2, 11.75),
            Block.box(6, 0.75, 9.5, 8.5, 3.5, 11.5),
            Block.box(6.5, 0.25, 7.25, 8.5, 2.25, 9.25),
            Block.box(7, 0.5, 8.25, 9, 3.25, 10.75),
            Block.box(-1.5, 0.25, 11.75, 0.5, 2.25, 13.75)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final DirectionProperty FACING = HorizontalBlock.FACING;


    public LargeRock() {
        super(AbstractBlock.Properties.of(Material.STONE).noOcclusion());
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
