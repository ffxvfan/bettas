package com.dragn.bettas.koi;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class KoiModel extends AnimatedGeoModel<KoiEntity> {

    public static final ResourceLocation MODEL_RESOURCE_LOCATION = new ResourceLocation(BettasMain.MODID, "geo/koi.geo.json");
    public static final ResourceLocation animationResource = new ResourceLocation(BettasMain.MODID, "animations/koi.animation.json");

    @Override
    public ResourceLocation getModelLocation(KoiEntity koiEntity) {
        return MODEL_RESOURCE_LOCATION;
    }

    @Override
    public ResourceLocation getTextureLocation(KoiEntity koiEntity) {
        return Variant.patternFromOrdinal(koiEntity.getTexture()).resourceLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(KoiEntity koiEntity) {
        return animationResource;
    }
}
