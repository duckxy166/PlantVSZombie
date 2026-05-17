package com.saharat.plantvszombie;

import com.saharat.plantvszombie.registry.ModEntities;
import com.saharat.plantvszombie.registry.ModBlocks;
import com.saharat.plantvszombie.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlantVSZombie implements ModInitializer {
    public static final String MOD_ID = "plantvszombie";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModEntities.initialize();
        ModBlocks.initialize();
        ModItems.initialize();

        LOGGER.info("PlantVSZombie mod loaded - let the lawn defense begin!");
    }
}
