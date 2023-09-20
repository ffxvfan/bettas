package com.dragn.bettas;

import com.dragn.bettas.betta.BettaEntity;
import com.dragn.bettas.biome.BettaBiome;
import com.dragn.bettas.decor.Decor;
import com.dragn.bettas.item.AlgaeScraper;
import com.dragn.bettas.item.AllRound;
import com.dragn.bettas.snail.SnailEntity;
import com.dragn.bettas.tank.Tank;
import com.dragn.bettas.tank.TankTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataSerializerEntry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;


import java.util.Objects;
import java.util.Random;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

@Mod(BettasMain.MODID)
public class BettasMain {

    public static Random RANDOM = new Random();
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "bettas";


    /* BETTA INVENTORY TAB */
    public static final ItemGroup BETTAS_TAB = new ItemGroup("betta_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BETTA_BUCKET.get());
        }
    };


    /* ENTITIES */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    public static final RegistryObject<EntityType<BettaEntity>> BETTA_ENTITY = ENTITY_TYPES.register("betta", () -> EntityType.Builder.of(BettaEntity::new, EntityClassification.WATER_AMBIENT).sized(0.0625f, 0.059375f).build(new ResourceLocation(MODID, "betta").toString()));
    public static final RegistryObject<EntityType<SnailEntity>> SNAIL_ENTITY = ENTITY_TYPES.register("snail", () -> EntityType.Builder.of(SnailEntity::new, EntityClassification.WATER_AMBIENT).sized(0.1f, 0.0125f).build(new ResourceLocation(MODID, "snail").toString()));


    /* BETTA BLOCKS */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Tank> TANK = BLOCKS.register("tank", Tank::new);
    static {
        final String[] plainDecor = {"big_log", "filter", "heater", "large_rock", "medium_rock", "small_log", "small_rock"};
        for(String name : plainDecor) {
            Block block = (new Decor(name));
            ForgeRegistries.BLOCKS.register(block);
        }
        ForgeRegistries.BLOCKS.register(new Decor("kelp", Items.KELP));
        ForgeRegistries.BLOCKS.register(new Decor("seagrass", Items.SEAGRASS));
        ForgeRegistries.BLOCKS.register(new Decor("substrate", Items.SAND));
    }
    public static final RegistryObject<Block> TANK2 = BLOCKS.register("tank2", () -> new Block(AbstractBlock.Properties.of(Material.GLASS)));


    /* ITEMS */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<ForgeSpawnEggItem> BETTA_SPAWN_EGG = ITEMS.register("betta_spawn_egg", () -> new ForgeSpawnEggItem(BETTA_ENTITY, 0xC37FCC, 0xEFE9F0, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> SNAIL_SPAWN_EGG = ITEMS.register("snail_spawn_egg", () -> new ForgeSpawnEggItem(SNAIL_ENTITY, 0xf5cb71, 0xad5a0c, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<BucketItem> BETTA_BUCKET = ITEMS.register("betta_bucket", () -> new FishBucketItem(BETTA_ENTITY, () -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<BucketItem> SNAIL_BUCKET = ITEMS.register("snail_bucket", () -> new FishBucketItem(SNAIL_ENTITY, () -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<BlockItem> TANK_ITEM = ITEMS.register("tank", () -> new BlockItem(TANK.get(), new Item.Properties().tab(BETTAS_TAB)));
    public static final RegistryObject<Item> ALGAE_SCRAPER = ITEMS.register("algae_scraper", AlgaeScraper::new);
    public static final RegistryObject<Item> ALLROUND = ITEMS.register("allround", AllRound::new);


    /* BETTA TILE ENTITIES */
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final RegistryObject<TileEntityType<TankTile>> TANK_TILE = TILE_ENTITIES.register("tank_tile", () -> TileEntityType.Builder.of(TankTile::new, TANK.get()).build(null));


    /* BETTA COLOR MAP SERIALIZATION */
    public static final DeferredRegister<DataSerializerEntry> SERIALIZERS = DeferredRegister.create(ForgeRegistries.DATA_SERIALIZERS, MODID);
    public static final RegistryObject<DataSerializerEntry> COLOR_SERIALIZER = SERIALIZERS.register("color_serializer",
            () -> new DataSerializerEntry(new IDataSerializer<int[]>() {
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
            }));


    public BettasMain() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::setup);

        ENTITY_TYPES.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        SERIALIZERS.register(modEventBus);

        BettaBiome.BIOMES.register(modEventBus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void setup(final FMLCommonSetupEvent event) {
        /* REGISTER BETTA BIOME */
        RegistryKey<Biome> key = RegistryKey.create(ForgeRegistries.Keys.BIOMES, Objects.requireNonNull(ForgeRegistries.BIOMES.getKey(
                BettaBiome.BETTA_BIOME.get()
        )));
        BiomeDictionary.addTypes(key, WET, SWAMP, OVERWORLD);
        BiomeManager.addBiome(BiomeManager.BiomeType.WARM, new BiomeManager.BiomeEntry(key, 10));
    }
}
