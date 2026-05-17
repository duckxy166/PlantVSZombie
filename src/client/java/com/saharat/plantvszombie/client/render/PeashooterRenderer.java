package com.saharat.plantvszombie.client.render;

import com.saharat.plantvszombie.PlantVSZombie;
import com.saharat.plantvszombie.entity.PeashooterEntity;
import com.saharat.plantvszombie.registry.ModItems;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class PeashooterRenderer extends EntityRenderer<PeashooterEntity> {
    private static final Identifier TEXTURE = new Identifier(PlantVSZombie.MOD_ID, "textures/block/texture.png");
    private static final double MODEL_X_OFFSET = 0.0D;
    private static final double MODEL_Y_OFFSET = 0.5D;
    private static final double MODEL_Z_OFFSET = 0.0D;
    private static final double HEAD_PIVOT_X = 0.0D;
    private static final double HEAD_PIVOT_Y = 0.0D;
    private static final double HEAD_PIVOT_Z = 0.0D;

    private final ItemRenderer itemRenderer;
    private final ItemStack baseStack = new ItemStack(ModItems.PEASHOOTER_BASE_MODEL_ITEM);
    private final ItemStack headStack = new ItemStack(ModItems.PEASHOOTER_HEAD_MODEL_ITEM);

    public PeashooterRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.35F;
    }

    @Override
    public void render(PeashooterEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(MODEL_X_OFFSET, MODEL_Y_OFFSET, MODEL_Z_OFFSET);
        this.renderModelItem(entity, matrices, vertexConsumers, light, this.baseStack);
        this.renderHead(entity, tickDelta, matrices, vertexConsumers, light);
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private void renderHead(PeashooterEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        float headYaw = MathHelper.lerpAngleDegrees(tickDelta, entity.prevHeadYaw, entity.headYaw);
        float pitch = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());

        matrices.push();
        matrices.translate(HEAD_PIVOT_X, HEAD_PIVOT_Y, HEAD_PIVOT_Z);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-headYaw));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-pitch));
        matrices.translate(-HEAD_PIVOT_X, -HEAD_PIVOT_Y, -HEAD_PIVOT_Z);
        this.renderModelItem(entity, matrices, vertexConsumers, light, this.headStack);
        matrices.pop();
    }

    private void renderModelItem(PeashooterEntity entity, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ItemStack stack) {
        this.itemRenderer.renderItem(
                stack,
                ModelTransformationMode.NONE,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                entity.getId()
        );
    }

    @Override
    public Identifier getTexture(PeashooterEntity entity) {
        return TEXTURE;
    }
}
