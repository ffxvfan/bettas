package com.dragn.bettas.koi;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class KoiRender extends GeoEntityRenderer<KoiEntity> {
    public KoiRender(EntityRendererManager renderManager) {
        super(renderManager, new KoiModel());
    }
}
