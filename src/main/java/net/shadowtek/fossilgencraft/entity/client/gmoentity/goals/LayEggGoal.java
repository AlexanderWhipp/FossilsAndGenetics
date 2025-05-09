package net.shadowtek.fossilgencraft.entity.client.gmoentity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;

public class LayEggGoal extends Goal {
    private final PathfinderMob mob;


    private int eggCooldown = 0;

    public LayEggGoal(PathfinderMob mob){
        this.mob = mob;
    }
    @Override
    public boolean canUse() {
        return mob instanceof GMOEntity gmoEntity && gmoEntity.canLayEggs() || mob instanceof GMOLandEntity gmoLandEntity && gmoLandEntity.canLayEggs();
    }

    @Override
    public void tick() {
        if(++eggCooldown >= 600) {
            eggCooldown = 0;

            ItemStack egg = new ItemStack(Items.EGG);
            mob.level().addFreshEntity(new ItemEntity(mob.level(), mob.getX(), mob.getY(),mob.getZ(),egg));
        }
    }

    @Override
    public void stop() {
        eggCooldown = 0;
    }
}
