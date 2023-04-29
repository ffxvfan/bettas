package com.dragn.bettas.snail;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;

public enum Texture {
    BROWN(new ResourceLocation(BettasMain.MODID, "textures/entity/brownmysterysnail.png")),
    CREAM(new ResourceLocation(BettasMain.MODID, "textures/entity/creammysterysnail.png")),
    GOLD(new ResourceLocation(BettasMain.MODID, "textures/entity/goldmysterysnail.png")),
    PURPLE(new ResourceLocation(BettasMain.MODID, "textures/entity/purplemysterysnail.png"));

    public final ResourceLocation resourceLocation;

    Texture(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static Texture patternFromOrdinal(int pattern) {
        return Texture.values()[pattern % Texture.values().length];
    }
}
