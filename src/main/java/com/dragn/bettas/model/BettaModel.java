package com.dragn.bettas.model;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.entity.BettaEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BettaModel extends AnimatedGeoModel<BettaEntity> {

    public static final ResourceLocation modelResource = new ResourceLocation(BettasMain.MODID, "geo/halfmoon.geo.json");
    public static final ResourceLocation textureResource = new ResourceLocation(BettasMain.MODID, "textures/entity/fade.png");
    public static final ResourceLocation animationResource = new ResourceLocation(BettasMain.MODID, "animations/betta.animations.json");

    @Override
    public ResourceLocation getModelLocation(BettaEntity object) {
        return modelResource;
    }

    @Override
    public ResourceLocation getTextureLocation(BettaEntity object) {
        return textureResource;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BettaEntity animatable) {
        return animationResource;
    }
}
