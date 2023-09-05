package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import org.lwjgl.system.CallbackI;

public class TankTileRenderer extends TileEntityRenderer<TankTile> {

    private static final ResourceLocation[] ALGAE_LEVEL = {
            new ResourceLocation(BettasMain.MODID, "textures/blocks/algae0.png"),
            new ResourceLocation(BettasMain.MODID, "textures/blocks/algae1.png"),
            new ResourceLocation(BettasMain.MODID, "textures/blocks/algae2.png"),
            new ResourceLocation(BettasMain.MODID, "textures/blocks/algae3.png"),
            new ResourceLocation(BettasMain.MODID, "textures/blocks/algae4.png"),
    };

    private static final int[] NORMALS = {-1, 0, 0, 1, 0, 0, 0, 1, 0, 0, -1, -1, 0, 0, 1, 0, 0, -1};

    private static void renderPart(IVertexBuilder iVertexBuilder, MatrixStack matrixStack, int lightVal, int overlay, float[] vertices, float[] uvs) {
        Matrix4f matrix4f = matrixStack.last().pose();
        Matrix3f matrix3f = matrixStack.last().normal();

        float[] v = new float[3];
        for(int i = 0, u = 0; i < 6; u+=4, i++) {

            int pos1 = i % 3;
            int pos2 = (i + 1) % 3;
            int pos3 = (i + 2) % 3;

            v[pos1] = vertices[(i + 3) % 6];
            v[pos2] = vertices[(i + 1) % 6];
            v[pos3] = vertices[(i + 2) % 6];

            iVertexBuilder.vertex(matrix4f, v[0], v[1], v[2]).color(255, 255, 255, 255).uv(uvs[u], uvs[u+1]).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, NORMALS[i], NORMALS[i+6], NORMALS[i+12]).endVertex();

            v[pos2] = vertices[(i + 4) % 6];
            iVertexBuilder.vertex(matrix4f, v[0], v[1], v[2]).color(255, 255, 255, 255).uv(uvs[u+2], uvs[u+1]).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, NORMALS[i], NORMALS[i+6], NORMALS[i+12]).endVertex();

            v[pos3] = vertices[(i + 5) % 6];
            iVertexBuilder.vertex(matrix4f, v[0], v[1], v[2]).color(255, 255, 255, 255).uv(uvs[u+2], uvs[u+3]).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, NORMALS[i], NORMALS[i+6], NORMALS[i+12]).endVertex();

            v[pos2] = vertices[(i + 1) % 6];
            iVertexBuilder.vertex(matrix4f, v[0], v[1], v[2]).color(255, 255, 255, 255).uv(uvs[u], uvs[u+3]).overlayCoords(overlay).uv2(lightVal).normal(matrix3f, NORMALS[i], NORMALS[i+6], NORMALS[i+12]).endVertex();
        }
    }

    public static final float[] BOTTOM_VERTS = {0, 0, 0, 1, 0.015625f, 1};
    private static final float[] BOTTOM_UVS = {0.3828125f, 0.0f, 0.51171875f, 0.00390625f, 0.125f, 0.12890625f, 0.0f, 0.0f, 0.3828125f, 0.015625f, 0.5078125f, 0.01953125f, 0.265625f, 0.25f, 0.39453125f, 0.25390625f, 0.25f, 0.0f, 0.125f, 0.12890625f, 0.3828125f, 0.0078125f, 0.5078125f, 0.01171875f};

    public static final float[] WEST_VERTS = {0, 0.015625f, 0, 0.015625f, 1.015625f, 1};
    private static final float[] WEST_UVS = {0.1328125f, 0.1328125f, 0.26171875f, 0.2578125f, 0.12890625f, 0.51171875f, 0.125f, 0.3828125f, 0.1640625f, 0.3828125f, 0.16796875f, 0.5078125f, 0.0f, 0.1328125f, 0.12890625f, 0.2578125f, 0.13671875f, 0.3828125f, 0.1328125f, 0.51171875f, 0.15625f, 0.3828125f, 0.16015625f, 0.5078125f};

    public static final float[] EAST_VERTS = {0.984375f, 0.015625f, 0, 1, 1.015625f, 1};
    private static final float[] EAST_UVS = {0.0f, 0.2578125f, 0.12890625f, 0.3828125f, 0.14453125f, 0.51171875f, 0.140625f, 0.3828125f, 0.1796875f, 0.3828125f, 0.18359375f, 0.5078125f, 0.25f, 0.0f, 0.37890625f, 0.125f, 0.15234375f, 0.3828125f, 0.1484375f, 0.51171875f, 0.171875f, 0.3828125f, 0.17578125f, 0.5078125f};

    public static final float[] NORTH_VERTS = {0.015625f, 0.015625f, 0, 0.984375f, 1.015625f, 0.015625f};
    private static final float[] NORTH_UVS = {0.1953125f, 0.3828125f, 0.19921875f, 0.5078125f, 0.50390625f, 0.02734375f, 0.3828125f, 0.0234375f, 0.2578125f, 0.2578125f, 0.37890625f, 0.3828125f, 0.1875f, 0.3828125f, 0.19140625f, 0.5078125f, 0.50390625f, 0.03125f, 0.3828125f, 0.03515625f, 0.1328125f, 0.2578125f, 0.25390625f, 0.3828125f};

    public static final float[] SOUTH_VERTS = {0.015625f, 0.015625f, 0.984375f, 0.984375f, 1.015625f, 1};
    private static final float[] SOUTH_UVS = {0.2109375f, 0.3828125f, 0.21484375f, 0.5078125f, 0.50390625f, 0.04296875f, 0.3828125f, 0.0390625f, 0.0f, 0.3828125f, 0.12109375f, 0.5078125f, 0.203125f, 0.3828125f, 0.20703125f, 0.5078125f, 0.50390625f, 0.046875f, 0.3828125f, 0.05078125f, 0.265625f, 0.125f, 0.38671875f, 0.25f};



    public TankTileRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(TankTile tankTile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightVal, int overlay) {
        matrixStack.pushPose();

        IVertexBuilder iVertexBuilder = buffer.getBuffer(RenderType.entityTranslucent(ALGAE_LEVEL[tankTile.algae]));
        renderPart(iVertexBuilder, matrixStack, lightVal, overlay, BOTTOM_VERTS, BOTTOM_UVS);

        if((tankTile.connected & TankTile.CONNECTED_NORTH) == 0) {
            renderPart(iVertexBuilder, matrixStack, lightVal, overlay, NORTH_VERTS, NORTH_UVS);
        }
        if((tankTile.connected & TankTile.CONNECTED_EAST) == 0) {
            renderPart(iVertexBuilder, matrixStack, lightVal, overlay, EAST_VERTS, EAST_UVS);
        }
        if((tankTile.connected & TankTile.CONNECTED_SOUTH) == 0) {
            renderPart(iVertexBuilder, matrixStack, lightVal, overlay, SOUTH_VERTS, SOUTH_UVS);
        }
        if((tankTile.connected & TankTile.CONNECTED_WEST) == 0) {
            renderPart(iVertexBuilder, matrixStack, lightVal, overlay, WEST_VERTS, WEST_UVS);
        }

        matrixStack.popPose();
    }
}
