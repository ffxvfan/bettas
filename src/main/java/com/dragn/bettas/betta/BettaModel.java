package com.dragn.bettas.betta;

import com.dragn.bettas.BettasMain;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BettaModel extends AnimatedGeoModel<BettaEntity> {

    public static final ResourceLocation animationResource = new ResourceLocation(BettasMain.MODID, "animations/betta.animations.json");

    @Override
    public ResourceLocation getModelLocation(BettaEntity bettaEntity) {
        return Model.modelFromOrdinal(bettaEntity.getModel()).resourceLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(BettaEntity bettaEntity) {
        return bettaEntity.getTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BettaEntity bettaEntity) {
        return animationResource;
    }
}
