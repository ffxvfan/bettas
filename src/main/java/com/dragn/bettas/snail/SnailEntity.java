package com.dragn.bettas.snail;

import com.dragn.bettas.BettasMain;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.FindWaterGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.entity.passive.fish.AbstractFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.Random;

public class SnailEntity extends AbstractFishEntity implements IAnimatable {

    private static final DataParameter<Integer> TEXTURE = EntityDataManager.defineId(SnailEntity.class, DataSerializers.INT);

    public static boolean checkSnailSpawnRules(EntityType<SnailEntity> snailEntityEntityType, IServerWorld world, SpawnReason reason, BlockPos pos, Random random) {
        return world.isWaterAt(pos);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 3d)
                .add(Attributes.MOVEMENT_SPEED, 1d);
    }

    static class SnailMovementController extends MovementController {

        public SnailMovementController(MobEntity mob) {
            super(mob);
        }

        public void tick() {
            if (this.operation == Action.MOVE_TO) {
                this.operation = Action.WAIT;
                this.mob.setSpeed((float) (this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
            }
        }
    }



    private final AnimationFactory factory = new AnimationFactory(this);

    public SnailEntity(EntityType<? extends AbstractFishEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new SnailMovementController(this);
        this.noCulling = true;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, (event) -> PlayState.CONTINUE));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(1, new RandomWalkingGoal(this, 0.25d, 30));
        this.goalSelector.addGoal(2, new FindWaterGoal(this));
    }

    @Override
    protected ItemStack getBucketItemStack() {
        return BettasMain.SNAIL_BUCKET.get().getDefaultInstance();
    }

    @Override
    protected SoundEvent getFlopSound() {
        return null;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TEXTURE, 0);
    }

    public int getTexture() {
        return this.entityData.get(TEXTURE);
    }

    public void setTexture(int texture) {
        this.entityData.set(TEXTURE, texture);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putInt("Texture", getTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        if(compoundNBT.contains("Texture")) {
            setTexture(compoundNBT.getInt("Texture"));
        }
    }

    @Nullable
    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficultyInstance, SpawnReason reason, @Nullable ILivingEntityData entityData, @Nullable CompoundNBT compoundNBT) {
        if(compoundNBT != null && compoundNBT.contains("Texture")) {
            setTexture(compoundNBT.getInt("Texture"));
        } else {
            setTexture(BettasMain.RANDOM.nextInt(Texture.values().length));
        }
        return super.finalizeSpawn(world, difficultyInstance, reason, entityData, compoundNBT);
    }
}
