package com.dragn.bettas.betta;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Arrays;


/* MOVED FROM BETTA ENTITY BECAUSE DYNAMIC TEXTURE CAN NOT BE ON SERVER DIST */
public class TextureGen {
    public static ResourceLocation generateTexture(BasePattern basePattern, int[] map) {
        try {
            return Minecraft.getInstance().textureManager.register(String.valueOf(Arrays.hashCode(map)), new DynamicTexture(NativeImage.read(
                    Minecraft.getInstance().getResourceManager().getResource(basePattern.resourceLocation).getInputStream()
            )) {
                @Override
                public void upload() {
                    this.bind();
                    for (int x = 0; x < getPixels().getWidth(); x++) {
                        for (int y = 0; y < getPixels().getHeight(); y++) {
                            switch (getPixels().getPixelRGBA(x, y)) {
                                case 0xff0b0b0b:
                                    getPixels().setPixelRGBA(x, y, map[0]);
                                    break;
                                case 0xff000000:
                                    getPixels().setPixelRGBA(x, y, map[1]);
                                    break;
                                case 0xff848484:
                                    getPixels().setPixelRGBA(x, y, map[2]);
                                    break;
                                case 0xff5d5d5d:
                                    getPixels().setPixelRGBA(x, y, map[3]);
                                    break;
                                case 0xffdcdcdc:
                                    getPixels().setPixelRGBA(x, y, map[4]);
                                    break;
                                case 0xffb1b1b1:
                                    getPixels().setPixelRGBA(x, y, map[5]);
                                    break;
                                case 0xff303030:
                                    getPixels().setPixelRGBA(x, y, map[6]);
                                    break;
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
}
