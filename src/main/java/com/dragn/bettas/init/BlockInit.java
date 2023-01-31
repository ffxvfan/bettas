package com.dragn.bettas.init;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BettasMain.MODID);

    public static final RegistryObject<Block> STORE_TANK = BLOCKS.register("store_tank",
            () -> new Block(AbstractBlock.Properties.of(Material.GLASS)));
}
