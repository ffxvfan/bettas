package com.dragn.bettas;

import com.dragn.bettas.betta.BettaEntity;
import com.dragn.bettas.biome.BettaBiome;
import com.dragn.bettas.decor.*;
import com.dragn.bettas.tank.Tank;
import com.dragn.bettas.tank.TankTile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

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
            return new ItemStack(BETTA_SPAWN_EGG.get());
        }
    };


    /* BETTA ENTITY */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    public static final RegistryObject<EntityType<BettaEntity>> BETTA_ENTITY = ENTITY_TYPES.register("betta",
            () -> EntityType.Builder.of(BettaEntity::new, EntityClassification.WATER_AMBIENT)
                    .sized(0.2F, 0.125F)
                    .build(new ResourceLocation(MODID, "betta").toString()));


    /* BETTA BLOCKS */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Block> TANK = BLOCKS.register("tank", Tank::new);
    public static final RegistryObject<Substrate> SUBSTRATE = BLOCKS.register("substrate", Substrate::new);
    public static final RegistryObject<Heater> HEATER = BLOCKS.register("heater", Heater::new);
    public static final RegistryObject<Filter> FILTER = BLOCKS.register("filter", Filter::new);
    public static final RegistryObject<SmallLog> SMALL_LOG = BLOCKS.register("small_log", SmallLog::new);
    public static final RegistryObject<BigLog> BIG_LOG = BLOCKS.register("big_log", BigLog::new);
    public static final RegistryObject<SmallRock> SMALL_ROCK = BLOCKS.register("small_rock", SmallRock::new);
    public static final RegistryObject<MediumRock> MEDIUM_ROCK = BLOCKS.register("medium_rock", MediumRock::new);
    public static final RegistryObject<LargeRock> LARGE_ROCK = BLOCKS.register("large_rock", LargeRock::new);
    public static final RegistryObject<Seagrass> SEAGRASS = BLOCKS.register("seagrass", Seagrass::new);
    public static final RegistryObject<Kelp> KELP = BLOCKS.register("kelp", Kelp::new);



    /* BETTA TILE ENTITIES */
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final RegistryObject<TileEntityType<TankTile>> TANK_TILE = TILE_ENTITIES.register("tank_tile",
            () -> TileEntityType.Builder.of(TankTile::new, TANK.get()).build(null));


    /* BETTA ITEMS */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> BETTA_SPAWN_EGG = ITEMS.register("betta_spawn_egg",
            () -> new ForgeSpawnEggItem(BETTA_ENTITY, 0xC37FCC, 0xEFE9F0, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));

    public static final RegistryObject<BucketItem> BETTA_BUCKET = ITEMS.register("betta_bucket",
            () -> new FishBucketItem(BETTA_ENTITY, () -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));

    public static final RegistryObject<Item> TANK_ITEM = ITEMS.register("tank_item",
            () -> new BlockItem(TANK.get(), new Item.Properties().tab(BETTAS_TAB)));

    public static final RegistryObject<Item> HEATER_ITEM = ITEMS.register("heater_item",
            () -> new Item(new Item.Properties().tab(BETTAS_TAB)));

    public static final RegistryObject<Item> FILTER_ITEM = ITEMS.register("filter_item",
            () -> new Item(new Item.Properties().tab(BETTAS_TAB)));

    public static final RegistryObject<Item> SMALL_LOG_ITEM = ITEMS.register("small_log_item",
            () -> new Item(new Item.Properties().tab(BETTAS_TAB)));

    public static final RegistryObject<Item> BIG_LOG_ITEM = ITEMS.register("big_log_item",
            () -> new Item(new Item.Properties().tab(BETTAS_TAB)));

    public static final RegistryObject<Item> SMALL_ROCK_ITEM = ITEMS.register("small_rock_item",
            () -> new Item(new Item.Properties().tab(BETTAS_TAB)));

    public static final RegistryObject<Item> MEDIUM_ROCK_ITEM = ITEMS.register("medium_rock_item",
            () -> new Item(new Item.Properties().tab(BETTAS_TAB)));

    public static final RegistryObject<Item> LARGE_ROCK_ITEM = ITEMS.register("large_rock_item",
            () -> new Item(new Item.Properties().tab(BETTAS_TAB)));


    public BettasMain() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ENTITY_TYPES.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        ITEMS.register(modEventBus);
        BettaBiome.BIOMES.register(modEventBus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
