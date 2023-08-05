package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;

public enum Algae {
    ALGAE0(new ResourceLocation(BettasMain.MODID, "textures/blocks/tankwalls.png")),
    ALGAE1(new ResourceLocation(BettasMain.MODID, "textures/blocks/algaestage1.png")),
    ALGAE2(new ResourceLocation(BettasMain.MODID, "textures/blocks/algaestage2.png")),
    ALGAE3(new ResourceLocation(BettasMain.MODID, "textures/blocks/algaestage3.png")),
    ALGAE4(new ResourceLocation(BettasMain.MODID, "textures/blocks/algaestage4.png"));

    public final ResourceLocation resourceLocation;

    Algae(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static Algae patternFromOrdinal(int pattern) {
        return Algae.values()[pattern % com.dragn.bettas.betta.BasePattern.values().length];
    }
}