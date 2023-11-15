package com.dragn.bettas.seahorse;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SeaHorseModel extends AnimatedGeoModel<SeaHorseEntity> {

    public static final ResourceLocation MODEL_RESOURCE_LOCATION = new ResourceLocation(BettasMain.MODID, "geo/seahorse.geo.json");
    public static final ResourceLocation animationResource = new ResourceLocation(BettasMain.MODID, "animations/seahorse.animation.json");

    @Override
    public ResourceLocation getModelLocation(SeaHorseEntity SeaHorseEntity) {
        return MODEL_RESOURCE_LOCATION;
    }

    @Override
    public ResourceLocation getTextureLocation(SeaHorseEntity SeaHorseEntity) {
        return Variant.patternFromOrdinal(SeaHorseEntity.getTexture()).resourceLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(SeaHorseEntity SeaHorseEntity) {
        return animationResource;
    }
}
