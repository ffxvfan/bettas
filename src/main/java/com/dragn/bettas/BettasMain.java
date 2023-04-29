package com.dragn.bettas;

import com.dragn.bettas.betta.BettaEntity;
import com.dragn.bettas.biome.BettaBiome;
import com.dragn.bettas.decor.Decor;
import com.dragn.bettas.network.NetworkManager;
import com.dragn.bettas.snail.SnailEntity;
import com.dragn.bettas.tank.Tank;
import com.dragn.bettas.tank.TankTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.Heightmap;
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
import org.lwjgl.system.CallbackI;
import software.bernie.geckolib3.GeckoLib;

import java.util.HashMap;
import java.util.Random;

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
    public static final RegistryObject<EntityType<BettaEntity>> BETTA_ENTITY = ENTITY_TYPES.register("betta", () -> EntityType.Builder.of(BettaEntity::new, EntityClassification.WATER_AMBIENT).sized(0.1f, 0.175f).build(new ResourceLocation(MODID, "betta").toString()));
    public static final RegistryObject<EntityType<SnailEntity>> SNAIL_ENTITY = ENTITY_TYPES.register("snail", () -> EntityType.Builder.of(SnailEntity::new, EntityClassification.WATER_AMBIENT).sized(0.1f, 0.09f).build(new ResourceLocation(MODID, "snail").toString()));


    /* BETTA BLOCKS */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Tank> TANK = BLOCKS.register("tank", Tank::new);
    public static final RegistryObject<Decor> BIG_LOG = BLOCKS.register("big_log", () -> new Decor(AbstractBlock.Properties.of(Material.WOOD).noOcclusion()));
    public static final RegistryObject<Decor> FILTER = BLOCKS.register("filter", () -> new Decor(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
    public static final RegistryObject<Decor> HEATER = BLOCKS.register("heater", () -> new Decor(AbstractBlock.Properties.of(Material.METAL).noOcclusion()));
    public static final RegistryObject<Decor> LARGE_ROCK = BLOCKS.register("large_rock", () -> new Decor(AbstractBlock.Properties.of(Material.STONE).noOcclusion()));
    public static final RegistryObject<Decor> MEDIUM_ROCK = BLOCKS.register("medium_rock", () -> new Decor(AbstractBlock.Properties.of(Material.STONE).noOcclusion()));
    public static final RegistryObject<Decor> SMALL_LOG = BLOCKS.register("small_log", () -> new Decor(AbstractBlock.Properties.of(Material.WOOD).noOcclusion()));
    public static final RegistryObject<Decor> SMALL_ROCK = BLOCKS.register("small_rock", () -> new Decor(AbstractBlock.Properties.of(Material.STONE).noOcclusion()));
    public static final RegistryObject<Decor> KELP = BLOCKS.register("kelp", () -> new Decor(AbstractBlock.Properties.copy(Blocks.KELP)));
    public static final RegistryObject<Decor> SEAGRASS = BLOCKS.register("seagrass", () -> new Decor(AbstractBlock.Properties.copy(Blocks.SEAGRASS)));
    public static final RegistryObject<Decor> SUBSTRATE = BLOCKS.register("substrate", () -> new Decor(AbstractBlock.Properties.of(Material.SAND).noOcclusion()));


    /* ITEMS */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<ForgeSpawnEggItem> BETTA_SPAWN_EGG = ITEMS.register("betta_spawn_egg", () -> new ForgeSpawnEggItem(BETTA_ENTITY, 0xC37FCC, 0xEFE9F0, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<ForgeSpawnEggItem> SNAIL_SPAWN_EGG = ITEMS.register("snail_spawn_egg", () -> new ForgeSpawnEggItem(SNAIL_ENTITY, 0xf5cb71, 0xad5a0c, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<BucketItem> BETTA_BUCKET = ITEMS.register("betta_bucket", () -> new FishBucketItem(BETTA_ENTITY, () -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<BucketItem> SNAIL_BUCKET = ITEMS.register("snail_bucket", () -> new FishBucketItem(SNAIL_ENTITY, () -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<BlockItem> TANK_ITEM = ITEMS.register("tank", () -> new BlockItem(TANK.get(), new Item.Properties().tab(BETTAS_TAB)));
    public static final RegistryObject<Item> BIG_LOG_ITEM = ITEMS.register("big_log_item", () -> new Item(new Item.Properties().tab(BETTAS_TAB)));
    public static final RegistryObject<Item> FILTER_ITEM = ITEMS.register("filter_item", () -> new Item(new Item.Properties().tab(BETTAS_TAB)));
    public static final RegistryObject<Item> HEATER_ITEM = ITEMS.register("heater_item", () -> new Item(new Item.Properties().tab(BETTAS_TAB)));
    public static final RegistryObject<Item> LARGE_ROCK_ITEM = ITEMS.register("large_rock_item", () -> new Item(new Item.Properties().tab(BETTAS_TAB)));
    public static final RegistryObject<Item> MEDIUM_ROCK_ITEM = ITEMS.register("medium_rock_item", () -> new Item(new Item.Properties().tab(BETTAS_TAB)));
    public static final RegistryObject<Item> SMALL_LOG_ITEM = ITEMS.register("small_log_item", () -> new Item(new Item.Properties().tab(BETTAS_TAB)));
    public static final RegistryObject<Item> SMALL_ROCK_ITEM = ITEMS.register("small_rock_item", () -> new Item(new Item.Properties().tab(BETTAS_TAB)));


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
        NetworkManager.init();
    }
}
