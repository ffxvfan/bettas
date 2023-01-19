package com.dragn.bettas.event;


import com.dragn.bettas.BettasMain;
import com.dragn.bettas.entity.BettaEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvent {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        event.put(BettasMain.BETTA_ENTITY.get(), BettaEntity.setAttributes());
    }
}
