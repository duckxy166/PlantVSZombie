package com.saharat.plantvszombie.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WallnutEntity extends PathAwareEntity {
    private float manualFacingYaw;

    public WallnutEntity(EntityType<? extends WallnutEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
    }

    public static DefaultAttributeContainer.Builder createWallnutAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void initGoals() {
    }

    @Override
    protected void mobTick() {
        super.mobTick();

        EntityNavigation navigation = this.getNavigation();
        if (!navigation.isIdle()) {
            navigation.stop();
        }
        this.setVelocity(Vec3d.ZERO);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    public void setManualFacingYaw(float yaw) {
        this.manualFacingYaw = MathHelper.wrapDegrees(yaw);
        this.setYaw(this.manualFacingYaw);
        this.bodyYaw = this.manualFacingYaw;
        this.prevBodyYaw = this.manualFacingYaw;
        this.headYaw = this.manualFacingYaw;
        this.prevHeadYaw = this.manualFacingYaw;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("ManualFacingYaw", this.manualFacingYaw);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setManualFacingYaw(nbt.getFloat("ManualFacingYaw"));
    }
}
