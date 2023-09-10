package com.dragn.bettas.decor;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.Item;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;
import java.util.Map;

public class Decor extends Block {

    public static Map<Item, Decor> ITEM_TO_DECOR = new HashMap<>();
    public static Map<Decor, Item> DECOR_TO_ITEM = new HashMap<>();
    public static Map<String, Decor> NAME_TO_DECOR = new HashMap<>();

    public static final DirectionProperty FACING = HorizontalBlock.FACING;

    private static final Material MATERIAL = new Material(MaterialColor.NONE, false, false, false, false, false, false, PushReaction.IGNORE);
    private static final Item.Properties PROPERTIES = new Item.Properties().tab(BettasMain.BETTAS_TAB);


    private void addMappings(Item item) {
        ITEM_TO_DECOR.put(item, this);
        DECOR_TO_ITEM.put(this, item);
        for(BlockState state : this.getStateDefinition().getPossibleStates()) {
            NAME_TO_DECOR.put(state.toString(), this);
        }
    }

    public Decor(String name) {
        super(AbstractBlock.Properties.of(MATERIAL).noOcclusion());
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));
        this.setRegistryName(BettasMain.MODID, name);

        Item item = (new Item(PROPERTIES)).setRegistryName(BettasMain.MODID, name);
        ForgeRegistries.ITEMS.register(item);
        addMappings(item);
    }

    public Decor(String name, Item item) {
        super(AbstractBlock.Properties.of(MATERIAL).noOcclusion());
        registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH));

        this.setRegistryName(BettasMain.MODID, name);

        addMappings(item);
    }

    public BlockState facing(Direction direction) {
        return defaultBlockState().setValue(FACING, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
