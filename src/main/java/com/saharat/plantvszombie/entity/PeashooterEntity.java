package com.saharat.plantvszombie.entity;

import com.saharat.plantvszombie.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Comparator;

public class PeashooterEntity extends PathAwareEntity {
    private static final double TARGET_RANGE = 12.0D;
    private static final double TARGET_CONE_DOT_THRESHOLD = 0.5D;
    private static final int SHOOT_INTERVAL_TICKS = 30;
    private static final float PEA_SPEED = 0.75F;
    private static final boolean STATIC_MODE = false;
    private static final TargetMode TARGET_MODE = TargetMode.ALL_LIVING;

    private int shootCooldown;
    private float manualFacingYaw;

    private enum TargetMode {
        ZOMBIES_ONLY,
        ALL_LIVING
    }

    public PeashooterEntity(EntityType<? extends PeashooterEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
    }

    public static DefaultAttributeContainer.Builder createPeashooterAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, TARGET_RANGE);
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

        if (STATIC_MODE || this.getWorld().isClient) {
            return;
        }

        LivingEntity target = this.findTarget();
        if (target == null) {
            this.faceManualDirection();
            if (this.shootCooldown > 0) {
                this.shootCooldown--;
            }
            return;
        }

        this.faceTarget(target);
        if (this.shootCooldown <= 0) {
            this.shootPeaAt(target);
            this.shootCooldown = SHOOT_INTERVAL_TICKS;
        } else {
            this.shootCooldown--;
        }
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
        this.faceManualDirection();
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("ManualFacingYaw", this.manualFacingYaw);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.manualFacingYaw = nbt.getFloat("ManualFacingYaw");
        this.faceManualDirection();
    }

    private LivingEntity findTarget() {
        Box searchBox = this.getBoundingBox().expand(TARGET_RANGE, 4.0D, TARGET_RANGE);

        return this.getWorld().getEntitiesByClass(LivingEntity.class, searchBox, this::isValidPeashooterTarget)
                .stream()
                .min(Comparator.comparingDouble(this::squaredDistanceTo))
                .orElse(null);
    }

    private boolean isValidPeashooterTarget(LivingEntity entity) {
        double maxDistanceSquared = TARGET_RANGE * TARGET_RANGE;

        if (entity == this
                || entity instanceof PeashooterEntity
                || !entity.isAlive()
                || entity.isSpectator()
                || this.squaredDistanceTo(entity) > maxDistanceSquared
                || !this.isInManualFacingCone(entity)
                || !this.canSee(entity)) {
            return false;
        }

        return switch (TARGET_MODE) {
            case ZOMBIES_ONLY -> entity instanceof Monster;
            case ALL_LIVING -> true;
        };
    }

    private boolean isInManualFacingCone(LivingEntity entity) {
        double dx = entity.getX() - this.getX();
        double dz = entity.getZ() - this.getZ();
        double distance = Math.sqrt(dx * dx + dz * dz);
        if (distance < 1.0E-7D) {
            return true;
        }

        double yawRadians = this.manualFacingYaw * MathHelper.RADIANS_PER_DEGREE;
        double facingX = -Math.sin(yawRadians);
        double facingZ = Math.cos(yawRadians);
        double targetX = dx / distance;
        double targetZ = dz / distance;

        return facingX * targetX + facingZ * targetZ >= TARGET_CONE_DOT_THRESHOLD;
    }

    private void faceManualDirection() {
        this.setYaw(this.manualFacingYaw);
        this.bodyYaw = this.manualFacingYaw;
        this.prevBodyYaw = this.manualFacingYaw;
        this.headYaw = this.manualFacingYaw;
        this.prevHeadYaw = this.manualFacingYaw;
        this.setPitch(0.0F);
    }

    private void faceTarget(LivingEntity target) {
        double dx = target.getX() - this.getX();
        double dy = target.getBodyY(0.55D) - (this.getY() + 0.75D);
        double dz = target.getZ() - this.getZ();
        double horizontalDistance = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) (MathHelper.atan2(dz, dx) * MathHelper.DEGREES_PER_RADIAN) - 90.0F;
        float pitch = (float) -(MathHelper.atan2(dy, horizontalDistance) * MathHelper.DEGREES_PER_RADIAN);

        this.headYaw = yaw;
        this.setPitch(MathHelper.clamp(pitch, -45.0F, 45.0F));
    }

    private void shootPeaAt(LivingEntity target) {
        Vec3d start = this.getProjectileSpawnPos(target);
        Vec3d targetPos = target.getPos().add(0.0D, target.getHeight() * 0.55D, 0.0D);
        Vec3d velocity = targetPos.subtract(start).normalize();

        PeaProjectileEntity pea = new PeaProjectileEntity(ModEntities.PEA_PROJECTILE, this.getWorld());
        pea.setOwner(this);
        pea.setPosition(start.x, start.y, start.z);
        pea.setVelocity(velocity.x, velocity.y, velocity.z, PEA_SPEED, 0.0F);

        this.getWorld().spawnEntity(pea);
        this.getWorld().playSound(null, this.getBlockPos(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.55F, 1.35F);
    }

    private Vec3d getProjectileSpawnPos(LivingEntity target) {
        Vec3d horizontalDirection = new Vec3d(target.getX() - this.getX(), 0.0D, target.getZ() - this.getZ());
        if (horizontalDirection.lengthSquared() < 1.0E-7D) {
            horizontalDirection = Vec3d.fromPolar(0.0F, this.getYaw());
        }

        horizontalDirection = horizontalDirection.normalize();
        return this.getPos().add(horizontalDirection.multiply(0.55D)).add(0.0D, 0.68D, 0.0D);
    }
}
