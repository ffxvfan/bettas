package com.dragn.bettas.network;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class BettaNetwork {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(BettasMain.MODID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init() {
        INSTANCE.registerMessage(0, SOpenBettaGuidebook.class, SOpenBettaGuidebook::encode, SOpenBettaGuidebook::decode, SOpenBettaGuidebook::handle);
    }
}
