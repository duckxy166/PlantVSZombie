package com.saharat.plantvszombie.mixin;

import com.saharat.plantvszombie.entity.PeashooterEntity;
import com.saharat.plantvszombie.entity.SunflowerEntity;
import com.saharat.plantvszombie.entity.WallnutEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntity {
    protected ZombieEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initCustomGoals", at = @At("TAIL"))
    private void plantvszombie$targetPlants(CallbackInfo ci) {
        this.targetSelector.add(0, new ActiveTargetGoal<>(this, WallnutEntity.class, true));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PeashooterEntity.class, true));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, SunflowerEntity.class, true));
    }

    @Inject(method = "burnsInDaylight", at = @At("HEAD"), cancellable = true)
    private void plantvszombie$disableSunBurn(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
