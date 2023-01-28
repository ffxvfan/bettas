package com.dragn.bettas.model;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.entity.BettaEntity;
import com.dragn.bettas.mappings.Model;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BettaModel extends AnimatedGeoModel<BettaEntity> {

    public static final ResourceLocation animationResource = new ResourceLocation(BettasMain.MODID, "animations/betta.animations.json");

    @Override
    public ResourceLocation getModelLocation(BettaEntity object) {
        return Model.modelFromOrdinal(object.getModel()).resourceLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(BettaEntity object) {
        return object.textureLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BettaEntity animatable) {
        return animationResource;
    }
}
