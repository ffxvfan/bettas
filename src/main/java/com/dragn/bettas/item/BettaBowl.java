package com.dragn.bettas.item;

import com.dragn.bettas.betta.BettaEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.FishBucketItem;

import java.util.function.Supplier;

public class BettaBowl extends FishBucketItem {

    public BettaBowl(Supplier<? extends EntityType<BettaEntity>> bettaType, Supplier<? extends Fluid> fluidSupplier, Properties properties) {
        super(bettaType, fluidSupplier, properties);
    }
}
