package com.saharat.plantvszombie.item;

import com.saharat.plantvszombie.entity.PeashooterEntity;
import com.saharat.plantvszombie.entity.SunflowerEntity;
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
import net.minecraft.world.World;

public class PlantGuideBookItem extends Item {
    public PlantGuideBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        World world = entity.getWorld();

        if (entity instanceof SunflowerEntity sunflower) {
            if (!world.isClient) {
                ItemStack seedPacket = new ItemStack(ModItems.SUNFLOWER_SEED_PACKET);
                if (!user.getInventory().insertStack(seedPacket)) {
                    user.dropItem(seedPacket, false);
                }

                sunflower.discard();
                world.playSound(null, sunflower.getBlockPos(), SoundEvents.ITEM_BOOK_PAGE_TURN, SoundCategory.PLAYERS, 0.8F, 1.1F);
                user.sendMessage(Text.literal("Sunflower returned to seed packet"), true);
            }
            return ActionResult.success(world.isClient);
        }

        if (entity instanceof PeashooterEntity peashooter) {
            if (!world.isClient) {
                peashooter.setManualFacingYaw(user.getYaw());
                world.playSound(null, peashooter.getBlockPos(), SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.PLAYERS, 0.7F, 1.4F);
                user.sendMessage(Text.literal("Peashooter facing set"), true);
            }
            return ActionResult.success(world.isClient);
        }

        return ActionResult.PASS;
    }
}
