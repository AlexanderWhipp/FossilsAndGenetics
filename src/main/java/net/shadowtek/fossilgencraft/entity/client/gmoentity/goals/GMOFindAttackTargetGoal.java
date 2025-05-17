package net.shadowtek.fossilgencraft.entity.client.gmoentity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;

public class GMOFindAttackTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final TamableAnimal mob;




    public GMOFindAttackTargetGoal(TamableAnimal mob, Class targetClass, boolean mustSee) {
        super(mob, targetClass, mustSee);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        return (mob instanceof GMOEntity gmoEntity && gmoEntity.isAggressive() || mob instanceof GMOLandEntity gmoLandEntity && (gmoLandEntity.isAggressive()));
    }
}
