package com.saharat.plantvszombie.entity;

import com.saharat.plantvszombie.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SunflowerEntity extends PathAwareEntity {
    public static final int SUN_VALUE = 25;

    private static final int SUN_GENERATION_INTERVAL_TICKS = 200;

    private int sunCooldown = SUN_GENERATION_INTERVAL_TICKS;

    public SunflowerEntity(EntityType<? extends SunflowerEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
    }

    public static DefaultAttributeContainer.Builder createSunflowerAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
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

        if (this.getWorld().isClient) {
            return;
        }

        if (this.sunCooldown > 0) {
            this.sunCooldown--;
            return;
        }

        this.spawnSun();
        this.sunCooldown = SUN_GENERATION_INTERVAL_TICKS;
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

    private void spawnSun() {
        ItemEntity sun = new ItemEntity(
                this.getWorld(),
                this.getX(),
                this.getY() + 1.0D,
                this.getZ(),
                new ItemStack(ModItems.SUN)
        );
        sun.setPickupDelay(10);
        sun.setVelocity(0.0D, 0.18D, 0.0D);

        this.getWorld().spawnEntity(sun);
        this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 0.35F, 1.6F);
    }
}
