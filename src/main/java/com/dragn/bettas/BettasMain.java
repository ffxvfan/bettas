package com.dragn.bettas;

import com.dragn.bettas.init.BlockInit;
import com.dragn.bettas.init.EntityInit;
import com.dragn.bettas.init.ItemInit;
import com.dragn.bettas.world.biome.BettaBiome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(BettasMain.MODID)
public class BettasMain {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "bettas";


    public BettasMain() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        EntityInit.ENTITY_TYPES.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        BettaBiome.BIOMES.register(modEventBus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
