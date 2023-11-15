package com.dragn.bettas.seahorse;


import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SeaHorseRender extends GeoEntityRenderer<SeaHorseEntity> {
    public SeaHorseRender(EntityRendererManager renderManager) {
        super(renderManager, new SeaHorseModel());
    }
}
