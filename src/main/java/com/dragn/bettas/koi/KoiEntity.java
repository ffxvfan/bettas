package com.dragn.bettas.koi;

import com.dragn.bettas.BettasMain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.fish.AbstractGroupFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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

import javax.annotation.Nullable;

public class KoiEntity extends AbstractGroupFishEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);


    public KoiEntity(EntityType<? extends AbstractGroupFishEntity> p_i50246_1_, World p_i50246_2_) {
        super(p_i50246_1_, p_i50246_2_);
    }

    public int getMaxSchoolSize() {
        return 8;
    }

    protected ItemStack getBucketItemStack() {
        return new ItemStack(BettasMain.KOI_BUCKET.get());
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SALMON_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SALMON_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.SALMON_HURT;
    }

    protected SoundEvent getFlopSound() {
        return SoundEvents.SALMON_FLOP;
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {

        if (event.isMoving()) {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("swim", ILoopType.EDefaultLoopTypes.LOOP));

        } else
            event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));


        return PlayState.CONTINUE;
    }


    //Controls animations
    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this,"controller",5,this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }




    private static final DataParameter<Integer> VARIANT = EntityDataManager.defineId(KoiEntity.class, DataSerializers.INT);
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, 0);
    }

    public int getTexture() {
        return this.entityData.get(VARIANT);
    }

    public void setTexture(int texture) {
        this.entityData.set(VARIANT, texture);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Variant", getTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        if(compoundNBT.contains("Variant")) {
            setTexture(compoundNBT.getInt("Variant"));
        }
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason reason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT compoundNBT) {
        if(compoundNBT != null && compoundNBT.contains("Variant")) {
            setTexture(compoundNBT.getInt("Variant"));
        } else {
            setTexture(BettasMain.RANDOM.nextInt(Variant.values().length));
        }
        return super.finalizeSpawn(world, difficultyInstance, reason, entityData, compoundNBT);
    }
}
