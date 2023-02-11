package com.dragn.bettas.mapping;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;

public enum Model {
    DOUBLETAIL(new ResourceLocation(BettasMain.MODID, "geo/doubletail.geo.json")),
    DUMBO(new ResourceLocation(BettasMain.MODID, "geo/dumbo.geo.json")),
    HALFMOON(new ResourceLocation(BettasMain.MODID, "geo/halfmoon.geo.json")),
    PLAKAT(new ResourceLocation(BettasMain.MODID, "geo/plakat.geo.json")),
    VEIL(new ResourceLocation(BettasMain.MODID, "geo/veil.geo.json"));

    public final ResourceLocation resourceLocation;

    Model(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static Model modelFromOrdinal(int ordinal) {
        return Model.values()[ordinal % Model.values().length];
    }
}