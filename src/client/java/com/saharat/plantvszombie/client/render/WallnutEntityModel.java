package com.saharat.plantvszombie.client.render;

import com.saharat.plantvszombie.PlantVSZombie;
import com.saharat.plantvszombie.entity.WallnutEntity;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WallnutEntityModel extends EntityModel<WallnutEntity> {
    public static final EntityModelLayer LAYER = new EntityModelLayer(new Identifier(PlantVSZombie.MOD_ID, "wallnut"), "main");

    private final ModelPart root;

    public WallnutEntityModel(ModelPart root) {
        this.root = root;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData root = modelData.getRoot();

        ModelPartData body = root.addChild(
                "body",
                ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-4.0F, -2.0F, -3.0F, 8.0F, 2.0F, 6.0F)
                        .uv(0, 0).cuboid(-5.0F, -5.0F, -4.0F, 10.0F, 3.0F, 8.0F)
                        .uv(32, 16).cuboid(-6.0F, -9.0F, -4.5F, 12.0F, 4.0F, 9.0F)
                        .uv(0, 0).cuboid(-5.0F, -12.0F, -4.0F, 10.0F, 3.0F, 8.0F)
                        .uv(32, 0).cuboid(-3.5F, -15.0F, -3.0F, 7.0F, 3.0F, 6.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F)
        );

        body.addChild(
                "face",
                ModelPartBuilder.create()
                        .uv(0, 16).cuboid(-3.0F, -11.0F, -5.0F, 2.0F, 2.5F, 1.0F)
                        .uv(0, 16).cuboid(1.0F, -11.0F, -5.0F, 2.0F, 2.5F, 1.0F)
                        .uv(16, 16).cuboid(-2.6F, -10.6F, -5.6F, 0.5F, 0.5F, 0.5F)
                        .uv(16, 16).cuboid(1.4F, -10.6F, -5.6F, 0.5F, 0.5F, 0.5F)
                        .uv(48, 0).cuboid(-2.0F, -6.8F, -5.2F, 4.0F, 0.6F, 0.8F)
                        .uv(48, 0).cuboid(-0.35F, -13.2F, -5.1F, 0.7F, 11.0F, 0.7F)
                        .uv(48, 0).cuboid(-4.0F, -13.0F, -5.1F, 3.0F, 0.6F, 0.7F)
                        .uv(48, 0).cuboid(1.0F, -13.0F, -5.1F, 3.0F, 0.6F, 0.7F),
                ModelTransform.NONE
        );

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(WallnutEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
