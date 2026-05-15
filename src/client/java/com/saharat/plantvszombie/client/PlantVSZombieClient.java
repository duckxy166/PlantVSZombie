package com.saharat.plantvszombie.client;

import com.saharat.plantvszombie.PlantVSZombie;
import net.fabricmc.api.ClientModInitializer;

public class PlantVSZombieClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PlantVSZombie.LOGGER.info("PlantVSZombie client initialized.");
    }
}
