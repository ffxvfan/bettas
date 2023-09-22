package com.dragn.bettas.koi;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;

public enum Variant {
    A(new ResourceLocation(BettasMain.MODID, "textures/entity/koi1.png")),
    B(new ResourceLocation(BettasMain.MODID, "textures/entity/koi2.png")),
    C(new ResourceLocation(BettasMain.MODID, "textures/entity/koi3.png")),
    D(new ResourceLocation(BettasMain.MODID, "textures/entity/koi4.png")),
    E(new ResourceLocation(BettasMain.MODID, "textures/entity/koi5.png")),
    F(new ResourceLocation(BettasMain.MODID, "textures/entity/koi6.png")),
    G(new ResourceLocation(BettasMain.MODID, "textures/entity/koi7.png")),
    H(new ResourceLocation(BettasMain.MODID, "textures/entity/koi8.png")),
    I(new ResourceLocation(BettasMain.MODID, "textures/entity/koi9.png")),
    J(new ResourceLocation(BettasMain.MODID, "textures/entity/koi10.png")),
    K(new ResourceLocation(BettasMain.MODID, "textures/entity/koi11.png")),
    L(new ResourceLocation(BettasMain.MODID, "textures/entity/koi12.png")),
    M(new ResourceLocation(BettasMain.MODID, "textures/entity/koi13.png")),
    N(new ResourceLocation(BettasMain.MODID, "textures/entity/koi14.png")),
    O(new ResourceLocation(BettasMain.MODID, "textures/entity/koi15.png")),
    P(new ResourceLocation(BettasMain.MODID, "textures/entity/koi16.png")),
    Q(new ResourceLocation(BettasMain.MODID, "textures/entity/koi17.png")),
    R(new ResourceLocation(BettasMain.MODID, "textures/entity/koi18.png")),
    S(new ResourceLocation(BettasMain.MODID, "textures/entity/koi19.png")),
    T(new ResourceLocation(BettasMain.MODID, "textures/entity/koi20.png")),
    U(new ResourceLocation(BettasMain.MODID, "textures/entity/koi21.png")),
    V(new ResourceLocation(BettasMain.MODID, "textures/entity/koi22.png")),
    W(new ResourceLocation(BettasMain.MODID, "textures/entity/koi23.png")),
    X(new ResourceLocation(BettasMain.MODID, "textures/entity/koi24.png")),
    Y(new ResourceLocation(BettasMain.MODID, "textures/entity/koi25.png")),
    Z(new ResourceLocation(BettasMain.MODID, "textures/entity/koi26.png")),
    AA(new ResourceLocation(BettasMain.MODID, "textures/entity/koi27.png")),
    BB(new ResourceLocation(BettasMain.MODID, "textures/entity/koi28.png")),
    CC(new ResourceLocation(BettasMain.MODID, "textures/entity/koi29.png")),
    DD(new ResourceLocation(BettasMain.MODID, "textures/entity/koi30.png")),
    EE(new ResourceLocation(BettasMain.MODID, "textures/entity/koi31.png")),
    FF(new ResourceLocation(BettasMain.MODID, "textures/entity/koi32.png")),
    GG(new ResourceLocation(BettasMain.MODID, "textures/entity/koi33.png")),
    HH(new ResourceLocation(BettasMain.MODID, "textures/entity/koi34.png")),
    II(new ResourceLocation(BettasMain.MODID, "textures/entity/koi35.png")),
    JJ(new ResourceLocation(BettasMain.MODID, "textures/entity/koi36.png")),
    KK(new ResourceLocation(BettasMain.MODID, "textures/entity/koi37.png")),
    LL(new ResourceLocation(BettasMain.MODID, "textures/entity/koi38.png")),
    MM(new ResourceLocation(BettasMain.MODID, "textures/entity/koi39.png")),
    NN(new ResourceLocation(BettasMain.MODID, "textures/entity/koi40.png"));

    public final ResourceLocation resourceLocation;

    Variant(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public static Variant patternFromOrdinal(int pattern) {
        return Variant.values()[pattern % Variant.values().length];
    }
}
