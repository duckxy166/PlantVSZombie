package com.saharat.plantvszombie.client.render;

import com.saharat.plantvszombie.PlantVSZombie;
import com.saharat.plantvszombie.entity.SunflowerEntity;
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

public class SunflowerRenderer extends EntityRenderer<SunflowerEntity> {
    private static final Identifier TEXTURE = new Identifier(PlantVSZombie.MOD_ID, "textures/block/sunflower.png");
    private static final double MODEL_Y_OFFSET = 0.5D;

    private final ItemRenderer itemRenderer;
    private final ItemStack sunflowerStack = new ItemStack(ModItems.SUNFLOWER_MODEL_ITEM);

    public SunflowerRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
        this.shadowRadius = 0.35F;
    }

    @Override
    public void render(SunflowerEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.translate(0.0D, MODEL_Y_OFFSET, 0.0D);
        this.itemRenderer.renderItem(
                this.sunflowerStack,
                ModelTransformationMode.NONE,
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                entity.getId()
        );
        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public Identifier getTexture(SunflowerEntity entity) {
        return TEXTURE;
    }
}
