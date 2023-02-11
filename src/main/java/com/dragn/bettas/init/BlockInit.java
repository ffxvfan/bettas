package com.dragn.bettas.init;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.block.Tank;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BettasMain.MODID);

    public static final RegistryObject<Block> TANK = BLOCKS.register("tank",
            () -> new Tank(AbstractBlock.Properties.of(Material.GLASS).noOcclusion()));
}
