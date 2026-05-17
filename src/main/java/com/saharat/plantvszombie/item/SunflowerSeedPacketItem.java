package com.saharat.plantvszombie.item;

import com.saharat.plantvszombie.entity.SunflowerEntity;
import com.saharat.plantvszombie.registry.ModEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SunflowerSeedPacketItem extends Item {
    public SunflowerSeedPacketItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        PlayerEntity player = context.getPlayer();
        if (player == null) {
            return ActionResult.PASS;
        }

        BlockPos placePos = context.getBlockPos().offset(context.getSide());
        if (!world.getBlockState(placePos).isReplaceable()) {
            return ActionResult.FAIL;
        }

        if (!world.isClient) {
            SunflowerEntity sunflower = ModEntities.SUNFLOWER.create(world);
            if (sunflower == null) {
                return ActionResult.FAIL;
            }

            sunflower.refreshPositionAndAngles(
                    placePos.getX() + 0.5D,
                    placePos.getY(),
                    placePos.getZ() + 0.5D,
                    player.getYaw(),
                    0.0F
            );
            sunflower.setManualFacingYaw(player.getYaw());

            if (!world.isSpaceEmpty(sunflower)) {
                return ActionResult.FAIL;
            }

            world.spawnEntity(sunflower);
            world.playSound(null, placePos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 0.8F, 1.15F);
        }

        return ActionResult.success(world.isClient);
    }
}
