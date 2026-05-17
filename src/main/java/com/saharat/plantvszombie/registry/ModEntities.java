package com.saharat.plantvszombie.registry;

import com.saharat.plantvszombie.PlantVSZombie;
import com.saharat.plantvszombie.entity.PeaProjectileEntity;
import com.saharat.plantvszombie.entity.PeashooterEntity;
import com.saharat.plantvszombie.entity.SunflowerEntity;
import com.saharat.plantvszombie.entity.WallnutEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModEntities {
    public static final EntityType<PeashooterEntity> PEASHOOTER = Registry.register(
            Registries.ENTITY_TYPE,
            id("peashooter"),
            FabricEntityTypeBuilder.<PeashooterEntity>create(SpawnGroup.CREATURE, PeashooterEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0F, 1.0F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(3)
                    .build()
    );

    public static final EntityType<PeaProjectileEntity> PEA_PROJECTILE = Registry.register(
            Registries.ENTITY_TYPE,
            id("pea_projectile"),
            FabricEntityTypeBuilder.<PeaProjectileEntity>create(SpawnGroup.MISC, PeaProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(10)
                    .build()
    );

    public static final EntityType<SunflowerEntity> SUNFLOWER = Registry.register(
            Registries.ENTITY_TYPE,
            id("sunflower"),
            FabricEntityTypeBuilder.<SunflowerEntity>create(SpawnGroup.CREATURE, SunflowerEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0F, 1.0F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(3)
                    .build()
    );

    public static final EntityType<WallnutEntity> WALLNUT = Registry.register(
            Registries.ENTITY_TYPE,
            id("wallnut"),
            FabricEntityTypeBuilder.<WallnutEntity>create(SpawnGroup.CREATURE, WallnutEntity::new)
                    .dimensions(EntityDimensions.fixed(0.9F, 1.15F))
                    .trackRangeBlocks(10)
                    .trackedUpdateRate(3)
                    .build()
    );

    private ModEntities() {
    }

    public static void initialize() {
        FabricDefaultAttributeRegistry.register(PEASHOOTER, PeashooterEntity.createPeashooterAttributes());
        FabricDefaultAttributeRegistry.register(SUNFLOWER, SunflowerEntity.createSunflowerAttributes());
        FabricDefaultAttributeRegistry.register(WALLNUT, WallnutEntity.createWallnutAttributes());
    }

    private static Identifier id(String path) {
        return new Identifier(PlantVSZombie.MOD_ID, path);
    }
}
