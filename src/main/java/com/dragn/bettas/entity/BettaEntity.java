package com.dragn.bettas.entity;

import com.dragn.bettas.init.ItemInit;
import com.dragn.bettas.mapping.Model;
import com.dragn.bettas.mapping.Pattern;
import com.dragn.bettas.mapping.PatternManager;
import net.minecraft.block.Blocks;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Random;

public class BettaEntity extends AbstractFishEntity implements IAnimatable {

    private static final DataParameter<Integer> MODEL;
    private static final DataParameter<Integer> BASE_PATTERN;
    private static final DataParameter<int[]> COLOR_MAP;

    private final AnimationFactory factory = new AnimationFactory(this);

    public BettaEntity(EntityType<? extends AbstractFishEntity> type, World world) {
        super(type, world);
        this.noCulling = true;
    }

    public static boolean checkFishSpawnRules(EntityType<? extends AbstractFishEntity> type, IWorld world, SpawnReason reason, BlockPos blockPos, Random random) {
        return world.getBlockState(blockPos).is(Blocks.WATER) && world.getBlockState(blockPos.above()).is(Blocks.WATER) && random.nextFloat() > 0.90;
    }

    protected void registerGoals() {
        super.registerGoals();
    }

    @Override
    protected ItemStack getBucketItemStack() {
        return ItemInit.BETTA_BUCKET.get().getDefaultInstance();
    }

    protected void saveToBucketTag(ItemStack itemStack) {
        super.saveToBucketTag(itemStack);
        CompoundNBT compoundNBT = itemStack.getOrCreateTag();
        compoundNBT.putInt("Model", getModel());
        compoundNBT.putInt("BasePattern", getBasePattern());
        compoundNBT.putIntArray("ColorMap", getColorMap());
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().loop("swim"));
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 8, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public ResourceLocation getTextureLocation() {
        return PatternManager.getTextureLocation(Pattern.patternFromOrdinal(getBasePattern()), getColorMap());
    }

    /* INTERNAL DATA */
    static {
        MODEL = EntityDataManager.defineId(BettaEntity.class, DataSerializers.INT);
        BASE_PATTERN = EntityDataManager.defineId(BettaEntity.class, DataSerializers.INT);
        COLOR_MAP = EntityDataManager.defineId(BettaEntity.class, PatternManager.COLOR_SERIALIZER);
    }

    public int getModel() {
        return this.entityData.get(MODEL);
    }

    public int getBasePattern() {
        return this.entityData.get(BASE_PATTERN);
    }

    public int[] getColorMap() {
        return this.entityData.get(COLOR_MAP);
    }

    public void setModel(int model) {
        this.entityData.set(MODEL, model);
    }

    public void setBasePattern(int baseTexture) {
        this.entityData.set(BASE_PATTERN, baseTexture);
    }

    public void setColorMap(int[] colorMap) {
        this.entityData.set(COLOR_MAP, colorMap);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        if(compoundNBT.contains("Model")) {
            setModel(compoundNBT.getInt("Model"));
        }
        if(compoundNBT.contains("BasePattern")) {
            setBasePattern(compoundNBT.getInt("BasePattern"));
        }
        if(compoundNBT.contains("ColorMap")) {
            setColorMap(compoundNBT.getIntArray("ColorMap"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Model", getModel());
        compoundNBT.putInt("BasePattern", getBasePattern());
        compoundNBT.putIntArray("ColorMap", getColorMap());
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld serverWorld, DifficultyInstance difficultyInstance, SpawnReason spawnReason, @Nullable ILivingEntityData  livingEntityData, @Nullable CompoundNBT compoundNBT) {
        if(compoundNBT.contains("Model") && compoundNBT.contains("BasePattern") && compoundNBT.contains("ColorMap")) {
            setModel(compoundNBT.getInt("Model"));
            setBasePattern(compoundNBT.getInt("BasePattern"));
            setColorMap(compoundNBT.getIntArray("ColorMap"));
        } else {
            Random r = new Random();
            setModel(r.nextInt(Model.values().length));
            setBasePattern(r.nextInt(Pattern.values().length));
            setColorMap(PatternManager.generateMap());
        }
        return super.finalizeSpawn(serverWorld, difficultyInstance, spawnReason, livingEntityData, compoundNBT);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(MODEL, 0);
        this.entityData.define(BASE_PATTERN, 0);
        this.entityData.define(COLOR_MAP, new int[7]);
    }
}
