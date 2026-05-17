package com.saharat.plantvszombie.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PeaProjectileEntity extends ThrownItemEntity {
    private static final float DAMAGE = 4.0F;
    private static final int MAX_AGE_TICKS = 80;

    public PeaProjectileEntity(EntityType<? extends PeaProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public PeaProjectileEntity(EntityType<? extends PeaProjectileEntity> entityType, LivingEntity owner, World world) {
        super(entityType, owner, world);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.SLIME_BALL;
    }

    @Override
    protected float getGravity() {
        return 0.0F;
    }

    @Override
    protected boolean canHit(Entity entity) {
        return super.canHit(entity) && !isPlantEntity(entity);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!this.getWorld().isClient) {
            Entity hitEntity = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            if (hitEntity != owner && !isPlantEntity(hitEntity)) {
                Vec3d velocityBeforeDamage = hitEntity.getVelocity();
                if (hitEntity instanceof LivingEntity livingEntity) {
                    livingEntity.timeUntilRegen = 0;
                }
                hitEntity.damage(this.getDamageSources().thrown(this, owner), DAMAGE);
                hitEntity.setVelocity(velocityBeforeDamage);
            }
            this.discard();
        }
    }

    private static boolean isPlantEntity(Entity entity) {
        return entity instanceof PeashooterEntity || entity instanceof SunflowerEntity || entity instanceof WallnutEntity;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);

        if (!this.getWorld().isClient) {
            this.discard();
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.getWorld().isClient && this.age > MAX_AGE_TICKS) {
            this.discard();
        }
    }
}
