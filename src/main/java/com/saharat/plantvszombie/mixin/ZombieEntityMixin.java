package com.saharat.plantvszombie.mixin;

import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin {
    private static final long MORNING_START_TICK = 0L;
    private static final long MORNING_END_TICK = 6000L;
    private static final long FULL_DAY_TICKS = 24000L;

    @Inject(method = "burnsInDaylight", at = @At("HEAD"), cancellable = true)
    private void plantvszombie$skipMorningSunBurn(CallbackInfoReturnable<Boolean> cir) {
        ZombieEntity zombie = (ZombieEntity) (Object) this;
        World world = zombie.getWorld();
        long timeOfDay = Math.floorMod(world.getTimeOfDay(), FULL_DAY_TICKS);

        if (timeOfDay >= MORNING_START_TICK && timeOfDay < MORNING_END_TICK) {
            cir.setReturnValue(false);
        }
    }
}
