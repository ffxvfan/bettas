package com.dragn.bettas.decor;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class Substrate extends Block {

    public static final VoxelShape SHAPE = Block.box(0.25, 0.25, 0.25, 15.75, 1, 15.75);

    public Substrate() {
        super(AbstractBlock.Properties.of(Material.SAND).noOcclusion());
    }
}
