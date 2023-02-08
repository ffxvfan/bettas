package com.dragn.bettas.event;


import com.dragn.bettas.entity.BettaEntity;
import com.dragn.bettas.init.EntityInit;
import com.dragn.bettas.render.BettaRender;
import com.dragn.bettas.world.gen.BettaBiomeGeneration;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BettaEvent {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.BETTA_ENTITY.get(), BettaEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BETTA_ENTITY.get(), BettaRender::new);
        BettaBiomeGeneration.generateBiomes();

        EntitySpawnPlacementRegistry.register(
                EntityInit.BETTA_ENTITY.get(),
                EntitySpawnPlacementRegistry.PlacementType.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                BettaEntity::checkFishSpawnRules);
    }
}
