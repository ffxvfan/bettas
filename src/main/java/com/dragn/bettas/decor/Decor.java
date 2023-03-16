package com.dragn.bettas.decor;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonAppend;

import java.util.HashMap;
import java.util.Properties;

public class Decor extends Block {

    public static final HashMap<String, Properties> UNIQUE_PROPERTY_MAP = new HashMap<String, Properties>() {{
        put("big_log", AbstractBlock.Properties.of(Material.WOOD).noOcclusion());
        put("filter", Properties.of(Material.METAL).noOcclusion());
        put("heater", AbstractBlock.Properties.of(Material.STONE).noOcclusion());
        put("large_rock", AbstractBlock.Properties.of(Material.STONE).noOcclusion());
        put("medium_rock", AbstractBlock.Properties.of(Material.STONE).noOcclusion());
        put("small_log", AbstractBlock.Properties.of(Material.WOOD).noOcclusion());
        put("small_rock", AbstractBlock.Properties.of(Material.STONE).noOcclusion());
    }};

    public static final HashMap<String, Properties> PROPERTY_MAP = new HashMap<String, Properties>() {{
        put("kelp", Properties.copy(Blocks.KELP_PLANT));
        put("seagrass", AbstractBlock.Properties.copy(Blocks.SEAGRASS));
        put("substrate", AbstractBlock.Properties.of(Material.SAND).noOcclusion());
        this.putAll(UNIQUE_PROPERTY_MAP);
    }};

    public static final DirectionProperty FACING = HorizontalBlock.FACING;
    public final String name;

    public Decor(String name, int direction) {
        super(PROPERTY_MAP.get(name));
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.values()[direction]));
        this.name = name;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING);
    }

    public int getFacing() {
        return stateDefinition.any().getValue(FACING).ordinal();
    }

    public BlockState withDirection() {
        return stateDefinition.any().getBlockState();
    }
}
