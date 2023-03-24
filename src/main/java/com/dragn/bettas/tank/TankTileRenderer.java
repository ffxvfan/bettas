package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.Decor;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItemUseContext;
import net.minecraftforge.client.model.data.EmptyModelData;

public class TankTileRenderer extends TileEntityRenderer<TankTile> {

    private static final BlockRendererDispatcher renderer = Minecraft.getInstance().getBlockRenderer();

    public TankTileRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(TankTile tankTile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightVal, int overlay) {
        matrixStack.pushPose();
        tankTile.decor.forEach((name, direction) -> {
            renderer.renderBlock(
                    BettasMain.DECOR_MAP.get(name).get().defaultBlockState().setValue(Decor.FACING, direction),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        });
        matrixStack.popPose();
    }
}
