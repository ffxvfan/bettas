package com.dragn.bettas.entity;

import com.dragn.bettas.mappings.Model;
import com.dragn.bettas.mappings.Pattern;
import com.dragn.bettas.mappings.PatternMapper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Random;

public class BettaEntity extends AbstractFishEntity implements IAnimatable {

    private static final DataParameter<Integer> MODEL;

    public final ResourceLocation textureLocation = new PatternMapper(Pattern.CLASSIC).getResourceLocation();

    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public BettaEntity(EntityType<? extends AbstractFishEntity> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap setAttributes() {
        return AnimalEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3F)
                .add(Attributes.MOVEMENT_SPEED, 0.5F)
                .build();
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected ItemStack getBucketItemStack() {
        return null;
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("swim", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }


    // VARIANTS
    static {
        MODEL = EntityDataManager.defineId(BettaEntity.class, DataSerializers.INT);
    }


    public int getModel() {
        return this.entityData.get(MODEL);
    }

    public void setModel(int model) {
        this.entityData.set(MODEL, model);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        if(compoundNBT.contains("Model")) {
            setModel(compoundNBT.getInt("Model"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Model", getModel());
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld serverWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData  livingEntityData, @Nullable CompoundNBT compoundNBT) {
        Random r = new Random();
        setModel(r.nextInt(Model.values().length));
        return super.finalizeSpawn(serverWorld, difficultyInstance, spawnReason, livingEntityData, compoundNBT);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MODEL, 0);
    }
}
