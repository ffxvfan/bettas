package com.dragn.bettas.event;


import com.dragn.bettas.BettasMain;
import com.dragn.bettas.betta.BettaEntity;
import com.dragn.bettas.betta.BettaRender;
import com.dragn.bettas.biome.BettaBiome;
import com.dragn.bettas.snail.SnailEntity;
import com.dragn.bettas.snail.SnailRender;
import com.dragn.bettas.tank.TankTileRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@Mod.EventBusSubscriber(modid = BettasMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BettaEvent {

    @SubscribeEvent
    public static void entityAttrbiuteCreationEvent(EntityAttributeCreationEvent event) {
        event.put(BettasMain.BETTA_ENTITY.get(), BettaEntity.createAttributes().build());
        event.put(BettasMain.SNAIL_ENTITY.get(), SnailEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {

        /* REGISTER RENDERERS */
        RenderingRegistry.registerEntityRenderingHandler(BettasMain.BETTA_ENTITY.get(), BettaRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BettasMain.SNAIL_ENTITY.get(), SnailRender::new);


        /* REGISTER TANK RENDERER */
        RenderTypeLookup.setRenderLayer(BettasMain.TANK.get(), RenderType.cutout());


        /* REGISTER BETTA SPAWNING */
        EntitySpawnPlacementRegistry.register(
                BettasMain.BETTA_ENTITY.get(),
                EntitySpawnPlacementRegistry.PlacementType.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                BettaEntity::checkFishSpawnRules);

        EntitySpawnPlacementRegistry.register(
                BettasMain.SNAIL_ENTITY.get(),
                EntitySpawnPlacementRegistry.PlacementType.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                SnailEntity::checkSnailSpawnRules
        );


        /* REGISTER TANK TILE ENTITY*/
        ClientRegistry.bindTileEntityRenderer(BettasMain.TANK_TILE.get(), TankTileRenderer::new);
    }
}
