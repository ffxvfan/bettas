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

    private static void renderBottom(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f, int lightVal, int overlay) {

        // north
        iVertexBuilder.vertex(matrix4f, 0, 0, 0).color(255, 255, 255, 255).uv(6.125f/64f, 0.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, -1).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 0).color(255, 255, 255, 255).uv(6.125f/64f, 0.1875f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, -1).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1/64f, 0).color(255, 255, 255, 255).uv(8.125f/64f, 0.1875f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, -1).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 0).color(255, 255, 255, 255).uv(8.125f/64f, 0.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, -1).endVertex();

        // east
        iVertexBuilder.vertex(matrix4f, 0, 0, 0).color(255, 255, 255, 255).uv(4.25f/64f, 4/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 0, 1).color(255, 255, 255, 255).uv(4.25f/64f, 4.0625f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 1).color(255, 255, 255, 255).uv(6.3125f/64f, 4.0625f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 0).color(255, 255, 255, 255).uv(6.3125f/64f, 4/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 1, 0, 0).endVertex();

        // south
        iVertexBuilder.vertex(matrix4f, 0, 0, 1).color(255, 255, 255, 255).uv(6.125f/64f, 0.25f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, 1).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 1).color(255, 255, 255, 255).uv(6.125f/64f, 0.3125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, 1).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1/64f, 1).color(255, 255, 255, 255).uv(8.125f/64f, 0.3125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, 1).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 1).color(255, 255, 255, 255).uv(8.125f/64f, 0.25f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, 1).endVertex();

        // west
        iVertexBuilder.vertex(matrix4f, 1, 0, 0).color(255, 255, 255, 255).uv(6.125f/64f, 0).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, -1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 1).color(255, 255, 255, 255).uv(6.125f/64f, 0.0625f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, -1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1/64f, 1).color(255, 255, 255, 255).uv(8.1875f/64f, 0.0625f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, -1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1/64f, 0).color(255, 255, 255, 255).uv(8.1875f/64f, 0).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, -1, 0, 0).endVertex();

        // up
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 0).color(255, 255, 255, 255).uv(2/64f, 2.0625f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 1).color(255, 255, 255, 255).uv(2/64f, 0).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1/64f, 1).color(255, 255, 255, 255).uv(0, 0).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 1/64f, 0).color(255, 255, 255, 255).uv(0, 2.0625f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();

        // down
        iVertexBuilder.vertex(matrix4f, 0, 0, 0).color(255, 255, 255, 255).uv(4/64f, 0).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 0, 1).color(255, 255, 255, 255).uv(4/64f, 2.0625f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 1).color(255, 255, 255, 255).uv(2/64f, 2.0625f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1, 0, 0).color(255, 255, 255, 255).uv(2/64f, 0).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
    }

    private static void renderFront(IVertexBuilder iVertexBuilder, Matrix4f matrix4f, Matrix3f matrix3f, int lightVal, int overlay) {

        // north x1->x2, y1->y2, z1
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 0).color(255, 255, 255, 255).uv(2.5f/64f, 6.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, -1).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 65/64f, 0).color(255, 255, 255, 255).uv(2.5f/64f, 6.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, -1).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 65/64f, 0).color(255, 255, 255, 255).uv(2.5625f/64f, 8.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, -1).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 1/64f, 0).color(255, 255, 255, 255).uv(2.5625f/64f, 8.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, -1).endVertex();

        // east x1, y1->y2, z1->z2
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 0).color(255, 255, 255, 255).uv(0, 2.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 1).color(255, 255, 255, 255).uv(0, 4.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 65/64f, 1).color(255, 255, 255, 255).uv(2.0625f/64f, 4.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 65/64f, 0).color(255, 255, 255, 255).uv(2.0625f/64f, 2.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 1, 0, 0).endVertex();

        // south x1->x2, y1->y2, z2
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 1).color(255, 255, 255, 255).uv(2.625f/64f, 6.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, 1).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 65/64f, 1).color(255, 255, 255, 255).uv(2.625f/64f, 8.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, 1).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 65/64f, 1).color(255, 255, 255, 255).uv(2.6875f/64f, 8.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, 1).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 1/64f, 1).color(255, 255, 255, 255).uv(2.6875f/64f, 6.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 0, 1).endVertex();

        // west x2, y1->y2, z1->z2
        iVertexBuilder.vertex(matrix4f, 1/64f, 1/64f, 0).color(255, 255, 255, 255).uv(2.125f/64f, 2.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, -1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 1/64f, 1).color(255, 255, 255, 255).uv(2.125f/64f, 4.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, -1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 65/64f, 1).color(255, 255, 255, 255).uv(4.1875f/64f, 4.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, -1, 0, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 65/64f, 0).color(255, 255, 255, 255).uv(4.1875f/64f, 2.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, -1, 0, 0).endVertex();

        // up x1->x2, y2, z1->z2
        iVertexBuilder.vertex(matrix4f, 0, 65/64f, 0).color(255, 255, 255, 255).uv(2.0625f/64f, 8.1875f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 65/64f, 1).color(255, 255, 255, 255).uv(2.0625f/64f, 6.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 65/64f, 1).color(255, 255, 255, 255).uv(2/64f, 6.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 65/64f, 0).color(255, 255, 255, 255).uv(2/64f, 8.1875f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();

        // down x1->x2, y1, z1->z2
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 0).color(255, 255, 255, 255).uv(2.1875f/64f, 6.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 0, 1/64f, 1).color(255, 255, 255, 255).uv(2.1875f/64f, 8.1875f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 1/64f, 1).color(255, 255, 255, 255).uv(2.125f/64f, 8.1875f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
        iVertexBuilder.vertex(matrix4f, 1/64f, 1/64f, 0).color(255, 255, 255, 255).uv(2.125f/64f, 6.125f/32f).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, 0, 1, 0).endVertex();
    }

    private static void renderPart(IVertexBuilder iVertexBuilder, MatrixStack matrixStack, int lightVal, int overlay, float[] vertices, float[] uvs) {
        // north x1->x2, y1->y2, z1  (1, 1, 1), (1, 1, 0)
        // east x1, y1->y2, z1->z2   (1, 1, 1), (0, 1, 1)
        // south x1->x2, y1->y2, z2  (1, 1, 0), (1, 1, 1)
        // west x2, y1->y2, z1->z2   (0, 1, 1), (1, 1, 1)
        // up x1->x2, y2, z1->z2     (1, 0, 1), (1, 1, 1)
        // down x1->x2, y1, z1->z2   (1, 1, 1), (1, 0, 1)

        // west, up, south, east, down, north

        // x1

        Matrix4f matrix4f = matrixStack.last().pose();
        Matrix3f matrix3f = matrixStack.last().normal();

        for(int i = 0; i < 6; i++) {



            iVertexBuilder.vertex(matrix4f, ).endVertex();
            iVertexBuilder.vertex(matrix4f, ).endVertex();
        }
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
        renderFront(iVertexBuilder, matrix4f, matrix3f, lightVal, overlay);


        matrixStack.popPose();
    }
}
