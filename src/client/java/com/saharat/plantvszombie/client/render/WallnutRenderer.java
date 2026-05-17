package com.saharat.plantvszombie.client.render;

import com.saharat.plantvszombie.PlantVSZombie;
import com.saharat.plantvszombie.entity.WallnutEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WallnutRenderer extends MobEntityRenderer<WallnutEntity, WallnutEntityModel> {
    private static final Identifier TEXTURE = new Identifier(PlantVSZombie.MOD_ID, "textures/block/wallnut.png");

    public WallnutRenderer(EntityRendererFactory.Context context) {
        super(context, new WallnutEntityModel(context.getPart(WallnutEntityModel.LAYER)), 0.45F);
    }

    @Override
    public Identifier getTexture(WallnutEntity entity) {
        return TEXTURE;
    }
}
