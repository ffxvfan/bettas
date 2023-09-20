package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class TankModel implements IDynamicBakedModel {

    public static final RenderMaterial MATERIAL = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/base"));



    private final Function<RenderMaterial, TextureAtlasSprite> spriteGetter;

    public TankModel(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        this.spriteGetter = spriteGetter;
    }


    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if(side == null) {
            return Collections.EMPTY_LIST;
        }
        List<BakedQuad> quads = new ArrayList<>();
        TextureAtlasSprite sprite = spriteGetter.apply(MATERIAL);

        //"from": [0, 0, 0],
        //"to": [16, 0.5, 16],

        //0        1        2        3        4        5        6        7
        //|xxxxxxxx|yyyyyyyy|zzzzzzzz|FFFFFFFF|uuuuuuuu|vvvvvvvv|00000000|00zzyyxx|
        float[] verts = {0, 0, 0, 16, 0, 0, 16, 0, 16, 0, 0, 16};
        float[] uv = {0.375f, 0.1875f, 0.40625f, 0.1875f, 0.40625f, 0.25f, 0.375f, 0.25f};
        int[] vertices = new int[32];

        int vi = 0, uvi = 0;
        for(int i = 0; i < 32; i+=8, vi+=3, uvi+=2) {
            vertices[i] = Float.floatToIntBits(verts[vi]/16);
            vertices[i+1] = Float.floatToIntBits(verts[vi+1]/16);
            vertices[i+2] = Float.floatToIntBits(verts[vi+2]/16);
            vertices[i+3] = 0xFFFFFFFF;
            vertices[i+4] = Float.floatToIntBits(uv[uvi]);
            vertices[i+5] = Float.floatToIntBits(uv[uvi+1]);
            vertices[i+6] = 0x00000000;
            vertices[i+7] = 0x0000FF00;
        }
        BakedQuad quad = new BakedQuad(
                vertices,
                -1,
                side,
                spriteGetter.apply(MATERIAL),
                false
        );
        quads.add(quad);

        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return null;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return null;
    }
}
