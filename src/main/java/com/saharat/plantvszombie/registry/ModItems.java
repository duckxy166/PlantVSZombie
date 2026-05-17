package com.saharat.plantvszombie.registry;

import com.saharat.plantvszombie.PlantVSZombie;
import com.saharat.plantvszombie.item.PlantGuideBookItem;
import com.saharat.plantvszombie.item.PeashooterSeedPacketItem;
import com.saharat.plantvszombie.item.SunflowerSeedPacketItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {
    public static final Item PEASHOOTER_MODEL_ITEM = register("peashooter", new Item(new FabricItemSettings()));
    public static final Item PEASHOOTER_BASE_MODEL_ITEM = register("peashooter_base", new Item(new FabricItemSettings()));
    public static final Item PEASHOOTER_HEAD_MODEL_ITEM = register("peashooter_head", new Item(new FabricItemSettings()));
    public static final Item SUNFLOWER_MODEL_ITEM = register("sunflower", new Item(new FabricItemSettings()));
    public static final Item WALLNUT_MODEL_ITEM = register("wallnut", new Item(new FabricItemSettings()));
    public static final Item SUN = register("sun", new Item(new FabricItemSettings()));
    public static final Item PEASHOOTER_SEED_PACKET = register("peashooter_seed_packet", new PeashooterSeedPacketItem(new FabricItemSettings().maxCount(1)));
    public static final Item SUNFLOWER_SEED_PACKET = register("sunflower_seed_packet", new SunflowerSeedPacketItem(new FabricItemSettings().maxCount(1)));
    public static final Item PLANT_GUIDE_BOOK = register("plant_guide_book", new PlantGuideBookItem(new FabricItemSettings().maxCount(1)));
    public static final Item ZOMBIE_SPAWNER = register("zombie_spawner", new BlockItem(ModBlocks.ZOMBIE_SPAWNER, new FabricItemSettings()));

    public static final Item PEASHOOTER_SPAWN_EGG = register(
            "peashooter_spawn_egg",
            new SpawnEggItem(ModEntities.PEASHOOTER, 0x4D8F35, 0x173D1E, new FabricItemSettings())
    );
    public static final Item SUNFLOWER_SPAWN_EGG = register(
            "sunflower_spawn_egg",
            new SpawnEggItem(ModEntities.SUNFLOWER, 0xF6D64A, 0x5B8C2A, new FabricItemSettings())
    );
    public static final Item WALLNUT_SPAWN_EGG = register(
            "wallnut_spawn_egg",
            new SpawnEggItem(ModEntities.WALLNUT, 0x8A541F, 0x241107, new FabricItemSettings())
    );

    private ModItems() {
    }

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(PEASHOOTER_SPAWN_EGG);
            entries.add(SUNFLOWER_SPAWN_EGG);
            entries.add(WALLNUT_SPAWN_EGG);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(SUN));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> entries.add(ZOMBIE_SPAWNER));
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(PEASHOOTER_SEED_PACKET);
            entries.add(SUNFLOWER_SEED_PACKET);
            entries.add(PLANT_GUIDE_BOOK);
        });
    }

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(PlantVSZombie.MOD_ID, name), item);
    }
}
