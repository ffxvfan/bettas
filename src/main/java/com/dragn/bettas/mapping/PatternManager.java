package com.dragn.bettas.mapping;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class PatternManager {

    private static final HashMap<Integer, ResourceLocation> textureMap = new HashMap<>();

    public static ResourceLocation getTextureLocation(Pattern basePattern, int[] map) {
        int ID = Arrays.hashCode(map);
        if(!textureMap.containsKey(ID)) {
            textureMap.put(ID, generateTexture(basePattern, map));
        }
        return textureMap.get(ID);
    }

    public static final IDataSerializer<int[]> COLOR_SERIALIZER = new IDataSerializer<int[]>() {
        @Override
        public void write(PacketBuffer buffer, int[] list) {
            buffer.writeVarIntArray(list);
        }

        @Override
        public int[] read(PacketBuffer buffer) {
            return buffer.readVarIntArray();
        }

        @Override
        public int[] copy(int[] list) {
            return list;
        }
    };

    static {
        DataSerializers.registerSerializer(COLOR_SERIALIZER);
    }

    private static ResourceLocation generateTexture(Pattern basePattern, int[] map) {
        try {
            return Minecraft.getInstance().textureManager.register(String.valueOf(Arrays.hashCode(map)), new DynamicTexture(NativeImage.read(
                    Minecraft.getInstance().getResourceManager().getResource(basePattern.resourceLocation).getInputStream()
            )) {
                @Override
                public void upload() {
                    this.bind();
                    for(int x = 0; x < getPixels().getWidth(); x++) {
                        for(int y = 0; y < getPixels().getHeight(); y++) {
                            int index = pixelToIndex(getPixels().getPixelRGBA(x, y));
                            if(index != -1) {
                                getPixels().setPixelRGBA(x, y, map[index]);
                            }
                        }
                    }
                    getPixels().upload(0, 0, 0, false);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int HEAD_C = 0xff0b0b0b;
    private static final int HEAD_S = 0xff000000;
    private static final int BODY_C = 0xff848484;
    private static final int BODY_S = 0xff5d5d5d;
    private static final int FIN_C  = 0xffdcdcdc;
    private static final int FIN_S  = 0xffb1b1b1;
    private static final int GALAXY = 0xff303030;

    public static int[] generateMap() {
        int[] map = new int[7];

        Palette palette = Palette.getRandomPalette();
        map[0] = palette.getRandomColor();
        map[1] = palette.getRandomShade();

        palette = Palette.getRandomPalette();
        map[2] = palette.getRandomColor();
        map[3] = palette.getRandomShade();

        palette = Palette.getRandomPalette();
        map[4] = palette.getRandomColor();
        map[5] = palette.getRandomShade();

        palette = Palette.getRandomPalette();
        map[6] = palette.getRandomColor();

        return map;
    }

    private static int pixelToIndex(int i) {
        switch(i) {
            case HEAD_C:
                return 0;
            case HEAD_S:
                return 1;
            case BODY_S:
                return 2;
            case BODY_C:
                return 3;
            case FIN_S:
                return 4;
            case FIN_C:
                return 5;
            case GALAXY:
                return 6;
            default:
                return -1;
        }
    }
}
