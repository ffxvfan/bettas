package com.dragn.bettas.mappings;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Random;

public class PatternMapper  {

    private static final int HEAD_C = 0xffb0b0b0;
    private static final int HEAD_S = 0xff000000;
    private static final int BODY_C = 0xff848484;
    private static final int BODY_S = 0xff5d5d5d;
    private static final int FIN_C  = 0xffdcdcdc;
    private static final int FIN_S  = 0xffb1b1b1;
    private static final int GALAXY = 0xff303030;

    private enum Palette {
        BLUES(
                new int[]{0x400dcff, 0x41bc8dd, 0x435a6dd, 0x1f3581dd, 0x1f3558ff, 0x152295ff, 0x153a7aff, 0x1576caff, 0x156cafff, 0x6294ff},
                new int[]{0x400c8ff, 0x41ba6dd, 0x43581dd, 0x1f3562dd, 0x14223dff, 0x15227aff, 0x152e53ff, 0x156cafff, 0x1584afff, 0x5b76ff}
        ),
        REDS(
                new int[]{0xf1001bcc, 0x790000cc, 0xd81d00cc, 0xbe1d16cc, 0xff1d16cc, 0xff0000cc, 0x5f0000cc, 0xcf0000ff, 0xed0000ff, 0x610000ff},
                new int[]{0xc90000cc, 0x680000cc, 0xd81d00cc, 0xa31d16cc, 0xc91d16cc, 0xc90000cc, 0x360000cc, 0xad0000ff, 0xcf0000ff, 0x4b0000ff}
        ),
        PINKS(
                new int[]{0xeb2891cc, 0xd67cb4cc, 0xff5e91cc, 0xdc277dcc, 0xd05b88cc},
                new int[]{0xbd1754cc, 0xc25e91cc, 0xc25e7dcc, 0xb42761cc, 0xac4a72cc}
        ),
        GREENS(
                new int[]{0xb76cff, 0xd2a0ff, 0xb75aff, 0x4fb849ff, 0x8ab367ff, 0x6fcc9dff, 0x54ff91cc, 0x54c382ff, 0xb471ff, 0xb37fff},
                new int[]{0x9c58ff, 0xb789ff, 0xa549ff, 0x389c3cff, 0x6f9955ff, 0x6fbc88ff, 0x54d282ff, 0x54ad71ff, 0xa263ff, 0x9567ff}
        ),
        ORANGES(
                new int[]{0xff8c00ff, 0xff5d00ff, 0xbb3f00ff, 0xff7d55ff, 0x852700ff, 0x985200ff, 0xe64700ff, 0xbc4600ff, 0xff8500ff, 0x821d00ff},
                new int[]{0xe37a00ff, 0xd95d00ff, 0x9b3d00ff, 0xe66c55ff, 0x6f1d00ff, 0x843800ff, 0x762c00ff, 0xb13d00ff, 0xce6700ff, 0x701300ff}
        ),
        PASTELS(
                new int[]{0xffd50060, 0xffd5ff84, 0x47e23f, 0xff7949, 0xff330051, 0xff00003f, 0xff50003f, 0x5c8e0028, 0xff000021, 0xb76dff1e},
                new int[]{0xffd50084, 0xe2c1e2b5, 0x47e266, 0xff7966, 0xff33006d, 0xff000063, 0xff50005e, 0x5c8e0049, 0xff00002d, 0xb76dff33}
        ),
        PURPLES(
                new int[]{0x450043b2, 0x45004366, 0x380043d8, 0x600043e5},
                new int[]{0x450043cc, 0x4500438c, 0x380043e5, 0x540039f2}
        ),
        GREYS(
                new int[]{0x373636ff, 0xebedecff, 0xd3, 0xa5, 0xa},
                new int[]{0x232220ff, 0xcecfcfff, 0xe5, 0xc6, 0x23}
        );

        private static final Random r = new Random();

        private final int[] colors;
        private final int[] shades;

        Palette(int[] colors, int[] shades) {
            this.colors = colors;
            this.shades = shades;
        }

        public static Palette getRandomPalette() {
            return Palette.values()[r.nextInt(Palette.values().length)];
        }

        public int getRandomColor() {
            return colors[r.nextInt(colors.length)];
        }

        public int getRandomShade() {
            return colors[r.nextInt(shades.length)];
        }
    }

    private final ResourceLocation resourceLocation;
    public static int ID = 0;
    public PatternMapper(Pattern pattern) {
        try {
            int[] map = generateMap();

            DynamicTexture dynamicTexture = new DynamicTexture(NativeImage.read(Minecraft.getInstance().getResourceManager().getResource(pattern.resourceLocation).getInputStream()));

            for(int x = 0; x < dynamicTexture.getPixels().getWidth(); x++) {
                for(int y = 0; y < dynamicTexture.getPixels().getHeight(); y++) {
                    dynamicTexture.getPixels().setPixelRGBA(x, y, 0);
                }
            }

            resourceLocation = Minecraft.getInstance().textureManager.register(String.valueOf(ID++), dynamicTexture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    private int[] generateMap() {
        int[] map = new int[8];
        map[0] = 0;

        Palette palette = Palette.getRandomPalette();
        map[1] = palette.getRandomColor();
        map[2] = palette.getRandomShade();

        palette = Palette.getRandomPalette();
        map[3] = palette.getRandomColor();
        map[4] = palette.getRandomShade();

        palette = Palette.getRandomPalette();
        map[5] = palette.getRandomColor();
        map[6] = palette.getRandomShade();

        palette = Palette.getRandomPalette();
        map[7] = palette.getRandomColor();

        return map;
    }

    private int pixelToIndex(int i) {
        switch(i) {
            case HEAD_C:
                return 1;
            case HEAD_S:
                return 2;
            case BODY_S:
                return 3;
            case BODY_C:
                return 4;
            case FIN_S:
                return 5;
            case FIN_C:
                return 6;
            case GALAXY:
                return 7;
            default:
                return 0;
        }
    }
}
