package com.dragn.bettas.event;


import com.dragn.bettas.BettasMain;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)

public class ForgeEvent {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void registerBiomes(BiomeLoadingEvent event) {
        switch (event.getCategory()) {

            case SWAMP:
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(BettasMain.KOI_ENTITY.get(), 4, 2, 6));
                break;

            case RIVER:
                event.getSpawns().getSpawner(EntityClassification.CREATURE).add(new MobSpawnInfo.Spawners(BettasMain.KOI_ENTITY.get(), 4, 2, 6));
                break;

        }
    }
}