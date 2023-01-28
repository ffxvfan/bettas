package com.dragn.bettas;

import com.dragn.bettas.entity.BettaEntity;
import com.dragn.bettas.render.BettaRender;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

@Mod(BettasMain.MODID)
public class BettasMain
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "bettas";

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static final RegistryObject<EntityType<BettaEntity>> BETTA_ENTITY = ENTITY_TYPES.register("betta_entity",
            () -> EntityType.Builder.of(BettaEntity::new,
                    EntityClassification.WATER_CREATURE)
                    .sized(0.1F, 0.1F)
                    .build(new ResourceLocation(MODID, "betta").toString()));

    public BettasMain() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);
        ENTITY_TYPES.register(modEventBus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(BETTA_ENTITY.get(), BettaRender::new);
    }
}
