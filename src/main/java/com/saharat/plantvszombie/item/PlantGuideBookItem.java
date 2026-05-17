package com.saharat.plantvszombie.item;

import com.saharat.plantvszombie.entity.PeashooterEntity;
import com.saharat.plantvszombie.entity.SunflowerEntity;
import com.saharat.plantvszombie.entity.WallnutEntity;
import com.saharat.plantvszombie.registry.ModItems;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class PlantGuideBookItem extends Item {
    public PlantGuideBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            giveSeedPacketIfMissing(user, ModItems.SUNFLOWER_SEED_PACKET, "Sunflower Seed Packet");
            giveSeedPacketIfMissing(user, ModItems.PEASHOOTER_SEED_PACKET, "Peashooter Seed Packet");
            giveSeedPacketIfMissing(user, ModItems.WALLNUT_SEED_PACKET, "Wall-nut Seed Packet");

            user.sendMessage(Text.literal("Plant Guide"), false);
            user.sendMessage(Text.literal("- Right-click with this book: restore missing seed packets."), false);
            user.sendMessage(Text.literal("- Sneak-right-click a plant: pick it back up as a seed packet."), false);
            user.sendMessage(Text.literal("- Sunflower seed packets are free to replant."), false);
            user.sendMessage(Text.literal("- Right-click Peashooter, Sunflower, or Wall-nut: set its facing direction."), false);
            user.sendMessage(Text.literal("- Sunflowers make Sun (25). Peashooters cost 100 sun. Wall-nuts cost 50 sun."), false);
            world.playSound(null, user.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 0.8F, 1.0F);
        }

        return TypedActionResult.success(stack, world.isClient);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = entity.getWorld();

        if (entity instanceof SunflowerEntity sunflower) {
            if (!world.isClient) {
                if (user.isSneaking()) {
                    giveStack(user, new ItemStack(ModItems.SUNFLOWER_SEED_PACKET));
                    sunflower.discard();
                    world.playSound(null, sunflower.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 0.8F, 1.1F);
                    user.sendMessage(Text.literal("Sunflower returned to seed packet"), true);
                } else {
                    sunflower.setManualFacingYaw(user.getYaw());
                    world.playSound(null, sunflower.getBlockPos(), SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 0.7F, 1.4F);
                    user.sendMessage(Text.literal("Sunflower facing set"), true);
                }
            }
            return ActionResult.success(world.isClient);
        }

        if (entity instanceof PeashooterEntity peashooter) {
            if (!world.isClient) {
                if (user.isSneaking()) {
                    giveStack(user, new ItemStack(ModItems.PEASHOOTER_SEED_PACKET));
                    peashooter.discard();
                    world.playSound(null, peashooter.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 0.8F, 1.1F);
                    user.sendMessage(Text.literal("Peashooter returned to seed packet"), true);
                } else {
                    peashooter.setManualFacingYaw(user.getYaw());
                    world.playSound(null, peashooter.getBlockPos(), SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 0.7F, 1.4F);
                    user.sendMessage(Text.literal("Peashooter facing set"), true);
                }
            }
            return ActionResult.success(world.isClient);
        }

        if (entity instanceof WallnutEntity wallnut) {
            if (!world.isClient) {
                if (user.isSneaking()) {
                    giveStack(user, new ItemStack(ModItems.WALLNUT_SEED_PACKET));
                    wallnut.discard();
                    world.playSound(null, wallnut.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 0.8F, 1.1F);
                    user.sendMessage(Text.literal("Wall-nut returned to seed packet"), true);
                } else {
                    wallnut.setManualFacingYaw(user.getYaw());
                    world.playSound(null, wallnut.getBlockPos(), SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 0.7F, 1.4F);
                    user.sendMessage(Text.literal("Wall-nut facing set"), true);
                }
            }
            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }

    private static void giveSeedPacketIfMissing(PlayerEntity user, Item item, String name) {
        if (hasItem(user, item)) {
            return;
        }

        giveStack(user, new ItemStack(item));
        user.sendMessage(Text.literal("Added " + name), true);
    }

    private static boolean hasItem(PlayerEntity user, Item item) {
        for (int slot = 0; slot < user.getInventory().size(); slot++) {
            if (user.getInventory().getStack(slot).isOf(item)) {
                return true;
            }
        }
        return false;
    }

    private static void giveStack(PlayerEntity user, ItemStack stack) {
        if (!user.getInventory().insertStack(stack)) {
            user.dropItem(stack, false);
        }
    }
}
