package com.dragn.bettas.snail;

import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SnailRender extends GeoEntityRenderer<SnailEntity> {
    public SnailRender(EntityRendererManager renderManager) {
        super(renderManager, new SnailModel());
    }
}
