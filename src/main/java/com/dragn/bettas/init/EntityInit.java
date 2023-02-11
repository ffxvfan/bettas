package com.dragn.bettas.init;

import com.dragn.bettas.entity.BettaEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static com.dragn.bettas.BettasMain.MODID;

public class EntityInit {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MODID);

    public static final RegistryObject<EntityType<BettaEntity>> BETTA_ENTITY = ENTITY_TYPES.register("betta",
            () -> EntityType.Builder.of(BettaEntity::new, EntityClassification.WATER_AMBIENT)
                    .sized(0.3F, 0.125F)
                    .build(new ResourceLocation(MODID, "betta").toString()));
}
