package com.dragn.bettas.event;


import com.dragn.bettas.BettasMain;
import com.dragn.bettas.betta.BettaEntity;
import com.dragn.bettas.betta.BettaRender;
import com.dragn.bettas.biome.BettaBiome;
import com.dragn.bettas.block.TankModel;
import com.dragn.bettas.block.TankModelLoader;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.SimpleBakedModel;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BettaEvent {

    @SubscribeEvent
    public static void entityAttrbiuteCreationEvent(EntityAttributeCreationEvent event) {
        event.put(BettasMain.BETTA_ENTITY.get(), BettaEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {

        /* REGISTER BETTA RENDERER */
        RenderingRegistry.registerEntityRenderingHandler(BettasMain.BETTA_ENTITY.get(), BettaRender::new);


        /* REGISTER TANK RENDERER */
        RenderTypeLookup.setRenderLayer(BettasMain.TANK.get(), RenderType.cutout());


        /* REGISTER BETTA BIOME */
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(
                BettaBiome.BETTA_BIOME.get()
        )));
        BiomeDictionary.addTypes(key, WET, SWAMP, OVERWORLD);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(key, 10));


        /* REGISTER BETTA SPAWNING */
        EntitySpawnPlacementRegistry.register(
                BettasMain.BETTA_ENTITY.get(),
                EntitySpawnPlacementRegistry.PlacementType.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                BettaEntity::checkFishSpawnRules);
    }

    @SubscribeEvent
    public static void modelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new ResourceLocation(BettasMain.MODID, "tank_model_loader"),
                ModelLoaderRegistry.VanillaProxy.Loader.INSTANCE);
    }

    @SubscribeEvent
    public static void modelBakeEvent(ModelBakeEvent event) {
        event.getModelRegistry().put(new ResourceLocation(BettasMain.MODID, "tank_model"), new TankModel());
    }
}
