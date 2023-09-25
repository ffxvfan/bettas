package com.dragn.bettas.tank;

import com.dragn.bettas.BettasMain;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

public class TankLoader implements IModelLoader<TankLoader.TankModelGeometry> {
    public static final ResourceLocation LOCATION = new ResourceLocation(BettasMain.MODID, "tank_loader");
    public static final TankLoader INSTANCE = new TankLoader();

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public TankModelGeometry read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new TankModelGeometry();
    }

    public static class TankModelGeometry implements IModelGeometry<TankModelGeometry> {

        @Override
        public IBakedModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
            return new TankModel(owner, bakery, spriteGetter, modelTransform, overrides, modelLocation);
        }

        @Override
        public Collection<RenderMaterial> getTextures(IModelConfiguration owner, Function<ResourceLocation, IUnbakedModel> modelGetter, Set<Pair<String, String>> missingTextureErrors) {
            return Arrays.asList(TankModel.BASE, TankModel.WALLS);
        }
    }
}
