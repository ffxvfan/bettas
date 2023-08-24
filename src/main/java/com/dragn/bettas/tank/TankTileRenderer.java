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
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TankTileRenderer extends TileEntityRenderer<TankTile> {

    private static final RenderMaterial TEXTURE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/tank"));

    private static IVertexBuilder renderBottom(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f, int lightVal, int overlay) {

        // up
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 0).uv(2/64f, 2/64f).normal(matrix3f, 0, 1, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 1).uv(2/64f, 0).normal(matrix3f, 0, 1, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1/64f, 1).uv(0, 0).normal(matrix3f, 0, 1, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1/64f, 0).uv(0, 2/64f).normal(matrix3f, 0, 1, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();

        // down
        iVertexBuilder.vertex(matrix4f, 0, 0, 0).uv(4/64f, 0).normal(matrix3f, 0, 1, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 0, 1).uv(4/64f, 2/64f).normal(matrix3f, 0, 1, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 1).uv(2/64f, 2/64f).normal(matrix3f, 0, 1, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 0).uv(2/64f, 0).normal(matrix3f, 0, 1, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();

        // north
        iVertexBuilder.vertex(matrix4f, 0, 0, 0).uv(6/64f, 0).normal(matrix3f, 0, 0, -1).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1, 0).uv(6/64f, 0).normal(matrix3f, 0, 0, -1).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1, 0).uv(8/64f, 0).normal(matrix3f, 0, 0, -1).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 0).uv(8/64f, 0).normal(matrix3f, 0, 0, -1).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();

        // east
        iVertexBuilder.vertex(matrix4f, 0, 0, 0).uv(4/64f, 4/64f).normal(matrix3f, 1, 0, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 0, 1).uv(4/64f, 4/64f).normal(matrix3f, 1, 0, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1, 1).uv(4/64f, 6/64f).normal(matrix3f, 1, 0, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1, 0).uv(4/64f, 4/64f).normal(matrix3f, 1, 0, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();

        // south
        iVertexBuilder.vertex(matrix4f, 0, 0, 1).uv(6/64f, 0).normal(matrix3f, 0, 0, 1).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1, 1).uv(6/64f, 0).normal(matrix3f, 0, 0, 1).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1, 1).uv(8/64f, 0).normal(matrix3f, 0, 0, 1).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 1).uv(8/64f, 0).normal(matrix3f, 0, 0, 1).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();

        // west
        iVertexBuilder.vertex(matrix4f, 1, 0, 0).uv(4/64f, 4/64f).normal(matrix3f, -1, 0, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 1).uv(4/64f, 4/64f).normal(matrix3f, -1, 0, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1, 1).uv(4/64f, 6/64f).normal(matrix3f, -1, 0, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1, 0).uv(4/64f, 4/64f).normal(matrix3f, -1, 0, 0).uv2(lightVal).color(255, 255, 255, 255).overlayCoords(overlay).endVertex();

        return iVertexBuilder;
    }

    public TankTileRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }


    @Override
    public void render(TankTile tankTile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightVal, int overlay) {
        matrixStack.pushPose();
        IVertexBuilder iVertexBuilder = TEXTURE.buffer(buffer, RenderType::entityCutoutNoCull);

        Matrix4f matrix4f = matrixStack.last().pose();
        Matrix3f matrix3f = matrixStack.last().normal();

        renderBottom(iVertexBuilder, matrix4f, matrix3f, lightVal, overlay);


        matrixStack.popPose();
    }
}
