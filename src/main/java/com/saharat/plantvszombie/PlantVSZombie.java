package com.saharat.plantvszombie;

import com.saharat.plantvszombie.registry.ModEntities;
import com.saharat.plantvszombie.registry.ModBlocks;
import com.saharat.plantvszombie.registry.ModItems;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlantVSZombie implements ModInitializer {
    public static final String MOD_ID = "plantvszombie";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final String STARTING_SUN_TAG = MOD_ID + ".starting_sun_given";
    private static final String STARTING_PLANT_KIT_TAG = MOD_ID + ".starting_plant_kit_given";
    private static final int STARTING_SUN = 50;

    @Override
    public void onInitialize() {
        ModEntities.initialize();
        ModBlocks.initialize();
        ModItems.initialize();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> giveStartingKit(handler.player));

        LOGGER.info("PlantVSZombie mod loaded - let the lawn defense begin!");
    }

    private static void giveStartingKit(ServerPlayerEntity player) {
        if (!player.getCommandTags().contains(STARTING_SUN_TAG)) {
            int sunItems = Math.max(1, STARTING_SUN / com.saharat.plantvszombie.entity.SunflowerEntity.SUN_VALUE);
            giveStack(player, new ItemStack(ModItems.SUN, sunItems));
            player.addCommandTag(STARTING_SUN_TAG);
            player.sendMessage(Text.literal("Starting Sun: " + STARTING_SUN), true);
        }

        if (!player.getCommandTags().contains(STARTING_PLANT_KIT_TAG)) {
            giveStack(player, new ItemStack(ModItems.SUNFLOWER_SEED_PACKET));
            giveStack(player, new ItemStack(ModItems.PLANT_GUIDE_BOOK));
            player.addCommandTag(STARTING_PLANT_KIT_TAG);
        }
    }

    private static void giveStack(ServerPlayerEntity player, ItemStack stack) {
        if (!player.getInventory().insertStack(stack)) {
            player.dropItem(stack, false);
        }
    }
}
