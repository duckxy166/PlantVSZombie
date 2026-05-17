package com.saharat.plantvszombie.item;

import com.saharat.plantvszombie.entity.PeashooterEntity;
import com.saharat.plantvszombie.entity.SunflowerEntity;
import com.saharat.plantvszombie.registry.ModEntities;
import com.saharat.plantvszombie.registry.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PeashooterSeedPacketItem extends Item {
    private static final int PEASHOOTER_COST = 100;
    private static final int SUN_ITEMS_COST = PEASHOOTER_COST / SunflowerEntity.SUN_VALUE;

    public PeashooterSeedPacketItem(Settings settings) {
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
            if (!player.getAbilities().creativeMode && getSunAmount(player) < PEASHOOTER_COST) {
                player.sendMessage(Text.literal("Need " + PEASHOOTER_COST + " sun"), true);
                return ActionResult.FAIL;
            }

            PeashooterEntity peashooter = ModEntities.PEASHOOTER.create(world);
            if (peashooter == null) {
                return ActionResult.FAIL;
            }

            peashooter.refreshPositionAndAngles(
                    placePos.getX() + 0.5D,
                    placePos.getY(),
                    placePos.getZ() + 0.5D,
                    player.getYaw(),
                    0.0F
            );
            peashooter.setManualFacingYaw(player.getYaw());

            if (!world.isSpaceEmpty(peashooter)) {
                return ActionResult.FAIL;
            }

            if (!player.getAbilities().creativeMode) {
                consumeSun(player, SUN_ITEMS_COST);
            }

            world.spawnEntity(peashooter);
            world.playSound(null, placePos, SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.BLOCKS, 0.8F, 1.0F);
            if (player instanceof ServerPlayerEntity) {
                player.sendMessage(Text.literal("Sun: " + getSunAmount(player)), true);
            }
        }

        return ActionResult.success(world.isClient);
    }

    private static int getSunAmount(PlayerEntity player) {
        int sunItems = 0;
        for (int slot = 0; slot < player.getInventory().size(); slot++) {
            ItemStack stack = player.getInventory().getStack(slot);
            if (stack.isOf(ModItems.SUN)) {
                sunItems += stack.getCount();
            }
        }
        return sunItems * SunflowerEntity.SUN_VALUE;
    }

    private static void consumeSun(PlayerEntity player, int sunItemsToConsume) {
        int remaining = sunItemsToConsume;
        for (int slot = 0; slot < player.getInventory().size() && remaining > 0; slot++) {
            ItemStack stack = player.getInventory().getStack(slot);
            if (!stack.isOf(ModItems.SUN)) {
                continue;
            }

            int consumed = Math.min(stack.getCount(), remaining);
            stack.decrement(consumed);
            remaining -= consumed;
        }
    }
}
