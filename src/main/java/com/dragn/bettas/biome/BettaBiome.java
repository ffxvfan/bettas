package com.dragn.bettas.biome;

import com.dragn.bettas.BettasMain;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.carver.ConfiguredCarvers;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class BettaBiome {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, BettasMain.MODID);

    public static final RegistryObject<Biome> BETTA_BIOME = BIOMES.register("betta_biome", () -> makeBettaBiome(
            () -> SurfaceBuilder.SWAMP.configured(SurfaceBuilder.CONFIG_GRASS)
    ));

    private static Biome makeBettaBiome(final Supplier<ConfiguredSurfaceBuilder<?>> surfaceBuilderSupplier) {

        MobSpawnInfo.Builder mobSpawnInfoBuilder = new MobSpawnInfo.Builder()
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SPIDER, 100, 4, 4))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE, 95, 4, 4))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ZOMBIE_VILLAGER, 5, 1, 1))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SKELETON, 100, 4, 4))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.CREEPER, 100, 4, 4))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 100, 4, 4))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.ENDERMAN, 10, 1, 4))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.WITCH, 5, 1, 1))
                .addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 1, 1, 1))
                .addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 12, 4, 4))
                .addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.PIG, 10, 4, 4))
                .addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.CHICKEN, 10, 4, 4))
                .addSpawn(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.COW, 8, 4, 4))
                .addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(EntityType.BAT, 10, 8, 8))
                .addSpawn(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(BettasMain.BETTA_ENTITY.get(), 15, 1, 2))
                .addSpawn(EntityClassification.WATER_AMBIENT, new MobSpawnInfo.Spawners(BettasMain.SNAIL_ENTITY.get(), 3, 1, 2));

        BiomeGenerationSettings.Builder biomeGenerationSettingsBuilder = new BiomeGenerationSettings.Builder()
                .surfaceBuilder(surfaceBuilderSupplier)
                .addStructureStart(StructureFeatures.SWAMP_HUT)
                .addStructureStart(StructureFeatures.MINESHAFT)
                .addStructureStart(StructureFeatures.RUINED_PORTAL_SWAMP)
                .addStructureStart(StructureFeatures.BURIED_TREASURE)
                .addStructureStart(StructureFeatures.OCEAN_RUIN_WARM)
                .addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CAVE)
                .addCarver(GenerationStage.Carving.AIR, ConfiguredCarvers.CANYON)
                .addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_WATER)
                .addFeature(GenerationStage.Decoration.LAKES, Features.LAKE_LAVA)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIRT)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GRAVEL)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIORITE)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GRANITE)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_ANDESITE)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_COAL)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_IRON)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_GOLD)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_REDSTONE)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_DIAMOND)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.ORE_LAPIS)
                .addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Features.DISK_CLAY)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SWAMP_TREE)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.FLOWER_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_GRASS_NORMAL)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_DEAD_BUSH)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_WATERLILLY)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.BROWN_MUSHROOM_NORMAL)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.RED_MUSHROOM_NORMAL)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_SUGAR_CANE_SWAMP)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.PATCH_PUMPKIN)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_WATER)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SPRING_LAVA)
                .addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP)
                .addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Features.FREEZE_TOP_LAYER);

        BiomeAmbience.Builder biomeAmbienceBuilder = new BiomeAmbience.Builder()
                .waterColor(8709850)
                .waterFogColor(6198935)
                .fogColor(12638463)
                .skyColor(7907327)
                .foliageColorOverride(5755462)
                .grassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP)
                .ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS);

        return new Biome.Builder()
                .precipitation(Biome.RainType.RAIN)
                .biomeCategory(Biome.Category.SWAMP)
                .depth((float) -0.2)
                .scale((float) 0.1)
                .temperature(0.8f)
                .downfall(0.9f)
                .specialEffects(biomeAmbienceBuilder.build())
                .mobSpawnSettings(mobSpawnInfoBuilder.build())
                .generationSettings(biomeGenerationSettingsBuilder.build())
                .build();
    }
}
