package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TankTileRenderer extends TileEntityRenderer<TankTile> {

    private static final RenderMaterial MATERIAL = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/tank"));

    public TankTileRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }


    @Override
    public void render(TankTile tankTile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightVal, int overlay) {
        matrixStack.pushPose();
        IVertexBuilder iVertexBuilder = MATERIAL.buffer(buffer, RenderType::entityCutout);
        Matrix4f matrix4f = matrixStack.last().pose();
        iVertexBuilder.vertex(matrix4f, 0, 1, 0).color(255, 255, 255, 255).uv(0, 1/32).overlayCoords(overlay).uv2(lightVal).normal(1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1, 0).color(255, 255, 255, 255).uv(1/32, 1/32).overlayCoords(overlay).uv2(lightVal).normal(1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 0).color(255, 255, 255, 255).uv(1/32, 0).overlayCoords(overlay).uv2(lightVal).normal(1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 0, 0).color(255, 255, 255, 255).uv(0, 0).overlayCoords(overlay).uv2(lightVal).normal(1, 0, 0).endVertex();
        matrixStack.popPose();
    }
}
