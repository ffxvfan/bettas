package com.dragn.bettas;

import com.dragn.bettas.betta.BettaEntity;
import com.dragn.bettas.block.Substrate;
import com.dragn.bettas.block.Tank;
import com.dragn.bettas.item.BettaBowl;
import com.dragn.bettas.biome.BettaBiome;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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

    /* BETTA REGISTERS */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BettasMain.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BettasMain.MODID);


    /* BETTA INVENTORY TAB TAB */
    public static final ItemGroup BETTAS_TAB = new ItemGroup("betta_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BETTA_SPAWN_EGG.get());
        }
    };


    /* BETTA ENTITY */
    public static final RegistryObject<EntityType<BettaEntity>> BETTA_ENTITY = ENTITY_TYPES.register("betta",
            () -> EntityType.Builder.of(BettaEntity::new, EntityClassification.WATER_AMBIENT)
                    .sized(0.3F, 0.125F)
                    .build(new ResourceLocation(MODID, "betta").toString()));


    /* BETTA BLOCKS */
    public static final RegistryObject<Block> TANK = BLOCKS.register("tank", Tank::new);

    public static final RegistryObject<Block> SUBSTRATE = BLOCKS.register("substrate", Substrate::new);


    /* BETTA ITEMS */
    public static final RegistryObject<Item> BETTA_SPAWN_EGG = ITEMS.register("betta_spawn_egg",
            () -> new ForgeSpawnEggItem(BETTA_ENTITY, 0xC37FCC, 0xEFE9F0, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));

    public static final RegistryObject<Item> BETTA_BUCKET = ITEMS.register("betta_bowl",
            () -> new BettaBowl(BETTA_ENTITY, () -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));

    public static final RegistryObject<Item> TANK_ITEM = ITEMS.register("tank_item",
            () -> new BlockItem(TANK.get(), new Item.Properties().tab(BETTAS_TAB)));

    public static final RegistryObject<Item> SUBSTRATE_ITEM = ITEMS.register("substrate_item",
            () -> new BlockItem(SUBSTRATE.get(), new Item.Properties().tab(BETTAS_TAB)));


    public BettasMain() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ENTITY_TYPES.register(modEventBus);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BettaBiome.BIOMES.register(modEventBus);

        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);
    }
}
