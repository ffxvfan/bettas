package com.dragn.bettas.init;

import com.dragn.bettas.BettasMain;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BettasMain.MODID);

    public static final ItemGroup BETTAS_TAB = new ItemGroup("betta_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BETTA_SPAWN_EGG.get());
        }
    };

    public static final RegistryObject<Item> BETTA_SPAWN_EGG = ITEMS.register("betta_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.BETTA_ENTITY, 0xC37FCC, 0xEFE9F0, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));

    public static final RegistryObject<Item> BETTA_BUCKET = ITEMS.register("betta_bucket",
            () -> new FishBucketItem(EntityInit.BETTA_ENTITY, () -> Fluids.WATER, new Item.Properties().stacksTo(1).tab(BETTAS_TAB)));

    public static final RegistryObject<Item> TANK_ITEM = ITEMS.register("tank_item",
            () -> new BlockItem(BlockInit.TANK.get(), new Item.Properties().tab(BETTAS_TAB)));
}
