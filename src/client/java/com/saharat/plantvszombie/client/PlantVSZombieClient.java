package com.saharat.plantvszombie.client;

import com.saharat.plantvszombie.PlantVSZombie;
import com.saharat.plantvszombie.client.render.PeashooterRenderer;
import com.saharat.plantvszombie.client.render.SunflowerRenderer;
import com.saharat.plantvszombie.client.render.WallnutEntityModel;
import com.saharat.plantvszombie.client.render.WallnutRenderer;
import com.saharat.plantvszombie.registry.ModEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

public class PlantVSZombieClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.PEASHOOTER, PeashooterRenderer::new);
        EntityRendererRegistry.register(ModEntities.PEA_PROJECTILE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SUNFLOWER, SunflowerRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(WallnutEntityModel.LAYER, WallnutEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.WALLNUT, WallnutRenderer::new);

        PlantVSZombie.LOGGER.info("PlantVSZombie client initialized.");
    }
}
