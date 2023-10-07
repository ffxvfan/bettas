package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.data.IDynamicBakedModel;
import net.minecraftforge.client.model.data.IModelData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class TankModel implements IDynamicBakedModel {

    public static final RenderMaterial BASE = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/base"));
    public static final RenderMaterial WALLS = new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/walls"));

    public static final RenderMaterial[] ALGAE_LEVELS = {
            new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/algae0")),
            new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/algae1")),
            new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/algae2")),
            new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/algae3")),
            new RenderMaterial(AtlasTexture.LOCATION_BLOCKS, new ResourceLocation(BettasMain.MODID, "blocks/algae4"))
    };

    private static final float[] DOWN = {0, 0, 0, 1, 0.03125f, 1};
    private static final float[] NORTH_WEST = {0, 0.03125f, 0, 0.03125f, 1.03125f, 0.03125f};
    private static final float[] NORTH_EAST = {0.96875f, 0.03125f, 0, 1, 1.03125f, 0.03125f};
    private static final float[] SOUTH_EAST = {0.96875f, 0.03125f, 0.96875f, 1, 1.03125f, 1};
    private static final float[] SOUTH_WEST = {0, 0.03125f, 0.96875f, 0.03125f, 1.03125f, 1};
    private static final float[] SOUTH_UP = {0, 1, 0.96875f, 1, 1.03125f, 1};
    private static final float[] EAST_UP = {0.96875f, 1, 0, 1, 1.03125f, 1};
    private static final float[] NORTH_UP = {0, 1, 0, 1, 1.03125f, 0.03125f};
    private static final float[] WEST_UP = {0, 1, 0, 0.03125f, 1.03125f, 1};
    private static final float[] NORTH = {0.03125f, 0.03125f, 0, 0.96875f, 0.96875f, 0};
    private static final float[] EAST = {1, 0.03125f, 0.03125f, 1, 0.96875f, 0.96875f};
    private static final float[] SOUTH = {0.03125f, 0.03125f, 1, 0.96875f, 0.96875f, 1};
    private static final float[] WEST = {0, 0.03125f, 0.03125f, 0, 0.96875f, 0.96875f};

    private final Function<RenderMaterial, TextureAtlasSprite> spriteGetter;
    private final ItemOverrideList overrides;

    public TankModel(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
        this.spriteGetter = spriteGetter;
        this.overrides = overrides;

    }

    //0        1        2        3        4        5        6        7
    //|xxxxxxxx|yyyyyyyy|zzzzzzzz|FFFFFFFF|uuuuuuuu|vvvvvvvv|00000000|00zzyyxx|
    private int[] fillData(int[] v1, int[] v2, int[] v3, int[] v4, int[] uvs, int n) {
        int[] data = new int[32];
        data[3] = data[11] = data[19] = data[27] = -1;
        data[6] = data[14] = data[22] = data[20] = 0;
        data[7] = data[15] = data[23] = data[31] = n;
        data[4] = data[12] = uvs[0];
        data[5] = data[29] = uvs[1];
        data[20] = data[28] = uvs[2];
        data[13] = data[21] = uvs[3];

        data[0] = v1[0];
        data[1] = v1[1];
        data[2] = v1[2];

        data[8] = v2[0];
        data[9] = v2[1];
        data[10] = v2[2];

        data[16] = v3[0];
        data[17] = v3[1];
        data[18] = v3[2];

        data[24] = v4[0];
        data[25] = v4[1];
        data[26] = v4[2];

        return data;
    }

    private List<BakedQuad> asShape(float[] v, TextureAtlasSprite sprite) {
        List<BakedQuad> quads = new ArrayList<>();
        int[] uvs = {Float.floatToRawIntBits(sprite.getU0()), Float.floatToRawIntBits(sprite.getV0()), Float.floatToRawIntBits(sprite.getU1()), Float.floatToRawIntBits(sprite.getV1())};

        int[] v1 = {Float.floatToRawIntBits(v[0]), Float.floatToRawIntBits(v[1]), Float.floatToRawIntBits(v[2])};
        int[] v2 = {Float.floatToRawIntBits(v[0]), Float.floatToRawIntBits(v[1]), Float.floatToRawIntBits(v[5])};
        int[] v3 = {Float.floatToRawIntBits(v[0]), Float.floatToRawIntBits(v[4]), Float.floatToRawIntBits(v[2])};
        int[] v4 = {Float.floatToRawIntBits(v[0]), Float.floatToRawIntBits(v[4]), Float.floatToRawIntBits(v[5])};
        int[] v5 = {Float.floatToRawIntBits(v[3]), Float.floatToRawIntBits(v[1]), Float.floatToRawIntBits(v[2])};
        int[] v6 = {Float.floatToRawIntBits(v[3]), Float.floatToRawIntBits(v[1]), Float.floatToRawIntBits(v[5])};
        int[] v7 = {Float.floatToRawIntBits(v[3]), Float.floatToRawIntBits(v[4]), Float.floatToRawIntBits(v[2])};
        int[] v8 = {Float.floatToRawIntBits(v[3]), Float.floatToRawIntBits(v[4]), Float.floatToRawIntBits(v[5])};

        quads.add(new BakedQuad(fillData(v1, v5, v6, v2, uvs, 0x8100), -1, Direction.DOWN, sprite, true)); // DOWN[v1, v2, v6, v5] 0x8100
        quads.add(new BakedQuad(fillData(v3, v4, v8, v7, uvs, 0x7F00), -1, Direction.UP, sprite, true)); // UP[v3, v7, v8, v4] 0x7F00
        quads.add(new BakedQuad(fillData(v1, v2, v4, v3, uvs, 0x81), -1, Direction.NORTH, sprite, true)); // NORTH[v1, v2, v4, v3] 0x81
        quads.add(new BakedQuad(fillData(v5, v7, v8, v6, uvs, 0x7F), -1, Direction.SOUTH, sprite, true)); // SOUTH[v5, v7, v8, v6] 0x7F
        quads.add(new BakedQuad(fillData(v1, v3, v7, v5, uvs, 0x810000), -1, Direction.WEST, sprite, true)); // WEST[v1, v5, v7, v3] 0x810000
        quads.add(new BakedQuad(fillData(v2, v6, v8, v4, uvs, 0x7F0000), -1, Direction.EAST, sprite, true)); // EAST[v2, v4, v8, v6] 0x7F0000

        return quads;
    }

    @Nonnull
    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @Nonnull Random rand, @Nonnull IModelData extraData) {
        if(side != null) {
            return Collections.emptyList();
        }

        List<BakedQuad> quads = new ArrayList<>();

        byte connected = 0;
        int algae = 0;

        if(extraData.getData(TankTile.CONNECTED) != null) {
            connected = extraData.getData(TankTile.CONNECTED);
        }

        if(extraData.getData(TankTile.ALGAE) != null) {
            algae = extraData.getData(TankTile.ALGAE);
        }

        TextureAtlasSprite algaeSprite = this.spriteGetter.apply(ALGAE_LEVELS[algae]);
        TextureAtlasSprite wallSprite = this.spriteGetter.apply(WALLS);

        boolean up = (connected & (1 << Direction.UP.get3DDataValue())) == 0;
        boolean down = (connected & (1 << Direction.DOWN.get3DDataValue())) == 0;
        boolean north = (connected & (1 << Direction.NORTH.get3DDataValue())) == 0;
        boolean east = (connected & (1 << Direction.EAST.get3DDataValue())) == 0;
        boolean south = (connected & (1 << Direction.SOUTH.get3DDataValue())) == 0;
        boolean west = (connected & (1 << Direction.WEST.get3DDataValue())) == 0;

        if(down) {
            quads.addAll(asShape(DOWN, this.spriteGetter.apply(BASE)));
        }

        if(north && east) {
            quads.addAll(asShape(NORTH_EAST, wallSprite));
        }

        if(north && west) {
            quads.addAll(asShape(NORTH_WEST, wallSprite));
        }

        if(south && east) {
            quads.addAll(asShape(SOUTH_EAST, wallSprite));
        }

        if(south && west) {
            quads.addAll(asShape(SOUTH_WEST, wallSprite));
        }

        if(north && up) {
            quads.addAll(asShape(NORTH_UP, wallSprite));
        }

        if(east && up) {
            quads.addAll(asShape(EAST_UP, wallSprite));
        }

        if(south && up) {
            quads.addAll(asShape(SOUTH_UP, wallSprite));
        }

        if(west && up) {
            quads.addAll(asShape(WEST_UP, wallSprite));
        }

        if(north) {
            quads.addAll(asShape(NORTH, algaeSprite));
        }

        if(east) {
            quads.addAll(asShape(EAST, algaeSprite));
        }

        if(south) {
            quads.addAll(asShape(SOUTH, algaeSprite));
        }

        if(west) {
            quads.addAll(asShape(WEST, algaeSprite));
        }

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
        return this.spriteGetter.apply(WALLS);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return this.overrides;
    }
}
