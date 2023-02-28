package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.dragn.bettas.decor.*;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraftforge.client.model.data.EmptyModelData;
import software.bernie.shadowed.eliotlash.mclib.math.functions.limit.Min;

public class TankTileRenderer extends TileEntityRenderer<TankTile> {

    private static final BlockRendererDispatcher renderer = Minecraft.getInstance().getBlockRenderer();

    public TankTileRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(TankTile tankTile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int lightVal, int overlay) {
        matrixStack.pushPose();
        if(tankTile.substrate) {
            renderer.renderBlock(BettasMain.SUBSTRATE.get().defaultBlockState(), matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.bigLog) {
            renderer.renderBlock(
                    BettasMain.BIG_LOG.get().defaultBlockState().setValue(BigLog.FACING, tankTile.bigLogDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.smallLog) {
            renderer.renderBlock(
                    BettasMain.SMALL_LOG.get().defaultBlockState().setValue(SmallLog.FACING, tankTile.smallLogDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.filter) {
            renderer.renderBlock(
                    BettasMain.FILTER.get().defaultBlockState().setValue(Filter.FACING, tankTile.filterDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.heater) {
            renderer.renderBlock(
                    BettasMain.HEATER.get().defaultBlockState().setValue(Heater.FACING, tankTile.heaterDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.largeRock) {
            renderer.renderBlock(
                    BettasMain.LARGE_ROCK.get().defaultBlockState().setValue(LargeRock.FACING, tankTile.largeRockDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.mediumRock) {
            renderer.renderBlock(
                    BettasMain.MEDIUM_ROCK.get().defaultBlockState().setValue(MediumRock.FACING, tankTile.mediumRockDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.smallRock) {
            renderer.renderBlock(
                    BettasMain.SMALL_ROCK.get().defaultBlockState().setValue(SmallRock.FACING, tankTile.smallRockDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.seagrass) {
            renderer.renderBlock(
                    BettasMain.SEAGRASS.get().defaultBlockState().setValue(Seagrass.FACING, tankTile.seagrassDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        if(tankTile.kelp) {
            renderer.renderBlock(
                    BettasMain.KELP.get().defaultBlockState().setValue(Kelp.FACING, tankTile.kelpDir),
                    matrixStack, buffer, lightVal, overlay, EmptyModelData.INSTANCE);
        }
        matrixStack.popPose();
    }
}
