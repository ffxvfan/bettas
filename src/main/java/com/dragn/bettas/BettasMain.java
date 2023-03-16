package com.dragn.bettas;

import com.dragn.bettas.betta.BettaEntity;
import com.dragn.bettas.biome.BettaBiome;
import com.dragn.bettas.decor.Decor;
import com.dragn.bettas.tank.Tank;
import com.dragn.bettas.tank.TankTile;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DataSerializerEntry;
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

    /* BETTA ITEMS */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final RegistryObject<Item> BETTA_SPAWN_EGG = ITEMS.register("betta_spawn_egg",
            () -> new ForgeSpawnEggItem(BETTA_ENTITY, 0xC37FCC, 0xEFE9F0, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));
    public static final RegistryObject<BucketItem> BETTA_BUCKET = ITEMS.register("betta_bucket",
            () -> new FishBucketItem(BETTA_ENTITY, () -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));


    /* BETTA BLOCKS */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final RegistryObject<Tank> TANK = BLOCKS.register("tank", Tank::new);
    public static final RegistryObject<Item> TANK_ITEM = ITEMS.register("tank",
            () -> new BlockItem(TANK.get(), new Item.Properties().tab(BETTAS_TAB)));

    static {
        Decor.UNIQUE_PROPERTY_MAP.forEach((name, properties) -> {
            RegistryObject<Decor> decor = BLOCKS.register(name, () -> new Decor(name, Direction.NORTH.ordinal()));
            ITEMS.register(name, () -> new BlockItem(decor.get(), new Item.Properties().tab(BETTAS_TAB)));
        });
    }


    /* BETTA TILE ENTITIES */
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);
    public static final RegistryObject<TileEntityType<TankTile>> TANK_TILE = TILE_ENTITIES.register("tank_tile",
            () -> TileEntityType.Builder.of(TankTile::new, TANK.get()).build(null));


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

        ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCKS.register(modEventBus);
        TILE_ENTITIES.register(modEventBus);
        SERIALIZERS.register(modEventBus);

        BettaBiome.BIOMES.register(modEventBus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
