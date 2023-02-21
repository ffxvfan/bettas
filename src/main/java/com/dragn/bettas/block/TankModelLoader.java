package com.dragn.bettas.block;

import com.google.common.collect.Lists;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.renderer.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModelBuilder;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.IModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import java.util.List;
import java.util.function.Function;

public class TankModelLoader implements IModelLoader<ModelLoaderRegistry.VanillaProxy> {

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

    @Override
    public ModelLoaderRegistry.VanillaProxy read(JsonDeserializationContext deserializationContext, JsonObject modelContents) {
        return new ModelLoaderRegistry.VanillaProxy(getModelElements(deserializationContext, modelContents)) {
            @Override
            public TankModel bake(IModelConfiguration owner, ModelBakery bakery, Function<RenderMaterial, TextureAtlasSprite> spriteGetter, IModelTransform modelTransform, ItemOverrideList overrides, ResourceLocation modelLocation) {
                TextureAtlasSprite particle = spriteGetter.apply(owner.resolveTexture("particle"));
                IModelBuilder<?> builder = IModelBuilder.of(owner, overrides, particle);
                addQuads(owner, builder, bakery, spriteGetter, modelTransform, modelLocation);
                return (TankModel) builder.build();
            }
        };
    }

    // TAKEN FROM ModelLoaderRegistry.java
    private List<BlockPart> getModelElements(JsonDeserializationContext deserializationContext, JsonObject object) {
        List<BlockPart> list = Lists.newArrayList();
        if (object.has("elements")) {
            for(JsonElement jsonelement : JSONUtils.getAsJsonArray(object, "elements")) {
                list.add(deserializationContext.deserialize(jsonelement, BlockPart.class));
            }
        }
        return list;
    }
}
