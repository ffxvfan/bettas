package com.dragn.bettas.seahorse;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;


public enum Variant {
    A(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_1.png")),
    B(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_2.png")),
    C(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_3.png")),
    D(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_4.png")),
    E(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_5.png")),
    F(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_6.png")),
    G(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_7.png")),
    H(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_8.png")),
    I(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_9.png")),
    J(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_10.png")),
    K(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_11.png")),
    L(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_12.png")),
    M(new ResourceLocation(BettasMain.MODID, "textures/entity/seahorse_13.png"));

    public final ResourceLocation resourceLocation;

    Variant(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static Variant patternFromOrdinal(int pattern) {
        return Variant.values()[pattern % Variant.values().length];
    }
}
