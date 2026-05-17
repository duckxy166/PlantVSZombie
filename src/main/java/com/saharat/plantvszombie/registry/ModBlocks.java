package com.saharat.plantvszombie.registry;

import com.saharat.plantvszombie.PlantVSZombie;
import com.saharat.plantvszombie.block.ZombieSpawnerBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModBlocks {
    public static final Block ZOMBIE_SPAWNER = register(
            "zombie_spawner",
            new ZombieSpawnerBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).strength(3.0F))
    );

    private ModBlocks() {
    }

    public static void initialize() {
    }

    private static Block register(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(PlantVSZombie.MOD_ID, name), block);
    }
}
