package com.dragn.bettas.render;

import com.dragn.bettas.entity.BettaEntity;
import com.dragn.bettas.model.BettaModel;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BettaRender extends GeoEntityRenderer<BettaEntity> {

    public BettaRender(EntityRendererManager renderManager) {
        super(renderManager, new BettaModel());
    }
}
