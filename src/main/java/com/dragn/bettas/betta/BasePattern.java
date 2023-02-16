package com.dragn.bettas.betta;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;

public enum BasePattern {
    CLASSIC(new ResourceLocation(BettasMain.MODID, "textures/entity/classic.png")),
    FADE(new ResourceLocation(BettasMain.MODID, "textures/entity/fade.png")),
    GALAXY_KOI(new ResourceLocation(BettasMain.MODID, "textures/entity/galaxykoi.png")),
    KOI(new ResourceLocation(BettasMain.MODID, "textures/entity/koi.png")),
    SAMURAI(new ResourceLocation(BettasMain.MODID, "textures/entity/samurai.png")),
    WHITE_PASTEL(new ResourceLocation(BettasMain.MODID, "textures/entity/whitepastel.png"));

    public final ResourceLocation resourceLocation;

    BasePattern(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static BasePattern patternFromOrdinal(int pattern) {
        return BasePattern.values()[pattern % BasePattern.values().length];
    }
}