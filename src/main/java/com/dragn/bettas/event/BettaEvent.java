package com.dragn.bettas.event;


import com.dragn.bettas.BettasMain;
import com.dragn.bettas.betta.BettaEntity;
import com.dragn.bettas.betta.BettaRender;
import com.dragn.bettas.koi.KoiEntity;
import com.dragn.bettas.koi.KoiRender;
import com.dragn.bettas.snail.SnailEntity;
import com.dragn.bettas.snail.SnailRender;
import com.dragn.bettas.tank.TankLoader;
import com.dragn.bettas.tank.TankModel;
import com.dragn.bettas.tank.TankTileRenderer;
import net.minecraft.block.BlockRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import software.bernie.example.registry.BlockRegistry;

@Mod.EventBusSubscriber(modid = BettasMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BettaEvent {

    @SubscribeEvent
    public static void entityAttrbiuteCreationEvent(EntityAttributeCreationEvent event) {
        event.put(BettasMain.BETTA_ENTITY.get(), BettaEntity.createAttributes().build());
        event.put(BettasMain.SNAIL_ENTITY.get(), SnailEntity.createAttributes().build());
        event.put(BettasMain.KOI_ENTITY.get(), KoiEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {

        /* REGISTER RENDERERS */
        RenderingRegistry.registerEntityRenderingHandler(BettasMain.BETTA_ENTITY.get(), BettaRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BettasMain.SNAIL_ENTITY.get(), SnailRender::new);
        RenderingRegistry.registerEntityRenderingHandler(BettasMain.KOI_ENTITY.get(), KoiRender::new);


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
                SnailEntity::checkSnailSpawnRules);


        EntitySpawnPlacementRegistry.register(
                BettasMain.KOI_ENTITY.get(),
                EntitySpawnPlacementRegistry.PlacementType.IN_WATER,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                SnailEntity::checkFishSpawnRules);


        /* SET BLOCK RENDER LAYERS */
        RenderTypeLookup.setRenderLayer(BettasMain.TANK.get(), RenderType.translucent());


        /* REGISTER TANK TILE ENTITY*/
        ClientRegistry.bindTileEntityRenderer(BettasMain.TANK_TILE.get(), TankTileRenderer::new);
    }

    @SubscribeEvent
    public static void modelRegistryEvent(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(TankLoader.LOCATION, TankLoader.INSTANCE);
    }
}
