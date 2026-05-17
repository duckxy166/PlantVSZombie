package com.saharat.plantvszombie.mixin;

import net.minecraft.entity.mob.ZombieEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin {
    @Inject(method = "burnsInDaylight", at = @At("HEAD"), cancellable = true)
    private void plantvszombie$disableSunBurn(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
