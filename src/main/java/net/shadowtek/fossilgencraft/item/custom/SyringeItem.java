package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.shadowtek.fossilgencraft.item.ModItems;


public class SyringeItem extends Item {

    public SyringeItem(Properties pProperties) {
        super(pProperties);

    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level level = player.level();
        if (!level.isClientSide()) {
            if (stack.getItem() == ModItems.SYRINGE_EMPTY.get()){
                ItemStack filledSyringe = ItemStack.EMPTY;

                if (target instanceof Chicken) {
                    filledSyringe = new ItemStack(ModItems.SYRINGE_BLOOD_CHICKEN.get());
                } else if
                    (target instanceof Pig) {
                        filledSyringe = new ItemStack(ModItems.SYRINGE_BLOOD_PIG.get());
                    }
                if (!filledSyringe.isEmpty()){
                    target.hurt(level.damageSources().playerAttack(player), 1.0F);

                    stack.shrink(1);

                    if (!player.getInventory().add(filledSyringe)) {
                        player.drop(filledSyringe, false);
                    }
                    player.getCooldowns().addCooldown(this, 20);
                }
            }
            return InteractionResult.SUCCESS;
        }


        return InteractionResult.PASS;
    }

}
