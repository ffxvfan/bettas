package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.Decor;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
            switch(name) {
                case "big_log_item":
                    renderer.renderBlock(BettasMain.BIG_LOG.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "filter_item":
                    renderer.renderBlock(BettasMain.FILTER.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "heater_item":
                    renderer.renderBlock(BettasMain.HEATER.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "kelp":
                    renderer.renderBlock(BettasMain.KELP.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "large_rock_item":
                    renderer.renderBlock(BettasMain.LARGE_ROCK.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "medium_rock_item":
                    renderer.renderBlock(BettasMain.MEDIUM_ROCK.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "seagrass":
                    renderer.renderBlock(BettasMain.SEAGRASS.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "small_log_item":
                    renderer.renderBlock(BettasMain.SMALL_LOG.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "small_rock_item":
                    renderer.renderBlock(BettasMain.SMALL_ROCK.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
                case "sand":
                    renderer.renderBlock(BettasMain.SUBSTRATE.get().defaultBlockState().setValue(Decor.FACING, direction), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
                    break;
            }
        });
        matrixStack.popPose();
    }
}
