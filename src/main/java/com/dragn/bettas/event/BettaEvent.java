package com.dragn.bettas.event;


import com.dragn.bettas.BettasMain;
import com.dragn.bettas.entity.BettaEntity;
import com.dragn.bettas.init.BlockInit;
import com.dragn.bettas.init.EntityInit;
import com.dragn.bettas.render.BettaRender;
import com.dragn.bettas.world.gen.BettaBiomeGeneration;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.loot.LootEntryManager;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BettaEvent {

    @SubscribeEvent
    public static void addEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.BETTA_ENTITY.get(), BettaEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.BETTA_ENTITY.get(), BettaRender::new);
        RenderTypeLookup.setRenderLayer(BlockInit.TANK.get(), RenderType.cutout());

        BettaBiomeGeneration.generateBiomes();

        EntitySpawnPlacementRegistry.register(
                EntityInit.BETTA_ENTITY.get(),
                EntitySpawnPlacementRegistry.PlacementType.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                BettaEntity::checkFishSpawnRules);
    }
}
