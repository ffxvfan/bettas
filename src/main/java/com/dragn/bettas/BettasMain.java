package com.dragn.bettas;

import com.dragn.bettas.init.BlockInit;
import com.dragn.bettas.init.EntityInit;
import com.dragn.bettas.init.ItemInit;
import com.dragn.bettas.render.BettaRender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(BettasMain.MODID)
public class BettasMain
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "bettas";

    public BettasMain() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);
        EntityInit.ENTITY_TYPES.register(modEventBus);
        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BETTA_ENTITY.get(), BettaRender::new);
    }
}
