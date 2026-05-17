package com.saharat.plantvszombie.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ZombieSpawnerBlock extends Block {
    private static final int SPAWN_INTERVAL_TICKS = 100;

    public ZombieSpawnerBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onBlockAdded(state, world, pos, oldState, notify);

        if (!world.isClient && !oldState.isOf(state.getBlock())) {
            world.scheduleBlockTick(pos, this, SPAWN_INTERVAL_TICKS);
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        this.spawnZombieAbove(world, pos, random);
        world.scheduleBlockTick(pos, this, SPAWN_INTERVAL_TICKS);
    }

    private void spawnZombieAbove(ServerWorld world, BlockPos pos, Random random) {
        BlockPos spawnPos = pos.up();
        ZombieEntity zombie = EntityType.ZOMBIE.create(world);
        if (zombie == null || !world.getWorldBorder().contains(spawnPos)) {
            return;
        }

        zombie.refreshPositionAndAngles(
                spawnPos.getX() + 0.5D,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5D,
                random.nextFloat() * 360.0F,
                0.0F
        );

        if (!world.isSpaceEmpty(zombie)) {
            return;
        }

        zombie.initialize(world, world.getLocalDifficulty(spawnPos), SpawnReason.SPAWNER, null, null);
        world.spawnEntity(zombie);
    }
}
