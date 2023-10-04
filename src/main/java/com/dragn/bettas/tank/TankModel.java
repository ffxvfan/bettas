package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.crafting.conditions.FalseCondition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class TankModel implements IDynamicBakedModel {

    public static final RenderMaterial BASE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/base"));
    public static final RenderMaterial WALLS = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/walls"));

    // west, up, south, east, down, north (|00zzyyxx|)
    private static final Direction[] DIRECTIONS = {Direction.WEST, Direction.UP, Direction.SOUTH, Direction.EAST, Direction.DOWN, Direction.NORTH};
    private static final int[] NORMALS = {0x810000, 0x7F00, 0x7F, 0x7F0000, 0x8100, 0x81};
    private static final int[] INDICES3 = {0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2};
    private static final int[] INDICES6 = {0, 1, 2, 3, 4, 5, 0, 1, 2, 3, 4, 5};


    private static final float[] DOWN = {0, 0, 0, 1, 0.03125f, 1};
    private static final float[] NORTH_WEST = {0, 0, 0, 0.03125f, 1, 0.03125f};
    private static final float[] NORTH_EAST = {0.96875f, 0, 0, 1, 1, 0.03125f};
    private static final float[] SOUTH_EAST = {0.96875f, 0, 0.96875f, 1, 1, 1};
    private static final float[] SOUTH_WEST = {0, 0, 0.96875f, 0.03125f, 1, 1};
    private static final float[] SOUTH_UP = {0, 0.96875f, 0.96875f, 1, 1, 1};
    private static final float[] EAST_UP = {0.96875f, 0.96875f, 0, 1, 1, 1};
    private static final float[] NORTH_UP = {0, 0.96875f, 0, 1, 1, 0.03125f};
    private static final float[] WEST_UP = {0, 0.96875f, 0, 0.03125f, 1, 1};


    private final Function<RenderMaterial, TextureAtlasSprite> spriteGetter;
    private final List<BakedQuad> cache;
    private final ItemOverrideList overrides;

    public TankModel(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;

        this.cache = genModel();
    }

    private List<BakedQuad> genModel() {
        List<BakedQuad> quads = new ArrayList<>(asShape(DOWN, spriteGetter.apply(BASE)));
        Arrays.asList(NORTH_WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST, SOUTH_UP, EAST_UP, NORTH_UP, WEST_UP).forEach(v -> quads.addAll(asShape(v, spriteGetter.apply(WALLS))));

        return quads;
    }


    //0        1        2        3        4        5        6        7
    //|xxxxxxxx|yyyyyyyy|zzzzzzzz|FFFFFFFF|uuuuuuuu|vvvvvvvv|00000000|00zzyyxx|

    private List<BakedQuad> asShape(float[] vertices, TextureAtlasSprite sprite) {
        List<BakedQuad> quads = new ArrayList<>();
        float[] uvs = {sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1()};

        float[] v = new float[3];
        for(int i = 0, u = 0; i < 6; i++, u+=4) {
            int pos1 = INDICES3[i];
            int pos2 = INDICES3[i+1];
            int pos3 = INDICES3[i+2];

            v[pos1] = vertices[INDICES6[i+3]];
            v[pos2] = vertices[INDICES6[i+1]];
            v[pos3] = vertices[INDICES6[i+2]];

            //0        1        2        3        4        5        6        7
            //|xxxxxxxx|yyyyyyyy|zzzzzzzz|FFFFFFFF|uuuuuuuu|vvvvvvvv|00000000|00zzyyxx|
            int[] data = new int[32];
            data[3] = data[11] = data[19] = data[27] = 0xFFFFFFFF;
            data[6] = data[14] = data[22] = data[30] = 0x00000000;
            data[7] = data[15] = data[23] = data[31] = NORMALS[i];

            data[0] = Float.floatToRawIntBits(v[0]);
            data[1] = Float.floatToRawIntBits(v[1]);
            data[2] = Float.floatToRawIntBits(v[2]);
            data[4] = Float.floatToRawIntBits(uvs[0]);
            data[5] = Float.floatToRawIntBits(uvs[1]);

            v[pos3] = vertices[INDICES6[i + 5]];
            data[16] = Float.floatToRawIntBits(v[0]);
            data[17] = Float.floatToRawIntBits(v[1]);
            data[18] = Float.floatToRawIntBits(v[2]);
            data[20] = Float.floatToRawIntBits(uvs[2]);
            data[21] = Float.floatToRawIntBits(uvs[3]);

            int[] winding = {4, 2, 1, 1, 0, 3, 1, 0, 3, 4, 2, 1};
            int idx = (DIRECTIONS[i].getAxisDirection().getStep() + 1) * 3;

            v[pos2] = vertices[INDICES6[i + winding[idx]]];
            data[8] = Float.floatToRawIntBits(v[0]);
            data[9] = Float.floatToRawIntBits(v[1]);
            data[10] = Float.floatToRawIntBits(v[2]);
            data[12] = Float.floatToRawIntBits(uvs[winding[idx+4]]);
            data[13] = Float.floatToRawIntBits(uvs[winding[idx+5]]);

            v[pos2] = vertices[INDICES6[i + winding[idx+3]]];
            data[24] = Float.floatToRawIntBits(v[0]);
            data[25] = Float.floatToRawIntBits(v[1]);
            data[26] = Float.floatToRawIntBits(v[2]);
            data[28] = Float.floatToRawIntBits(uvs[winding[idx+1]]);
            data[29] = Float.floatToRawIntBits(uvs[winding[idx+2]]);

            quads.add(new BakedQuad(data, -1, DIRECTIONS[i], sprite, false));
        }
        return quads;
    }

    private List<BakedQuad> genModel2() {
        RenderMaterial stone = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation("block/stone"));
        float[] verts = {0, 0, 0, 1, 1, 1};

        return new ArrayList<>(asShape(verts, this.spriteGetter.apply(stone)));
    }


    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if(side != null) {
            return Collections.emptyList();
        }
        return genModel2();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
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
        return this.spriteGetter.apply(WALLS);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.overrides;
    }
}
