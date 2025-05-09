package net.shadowtek.fossilgencraft.entity.client.gmoentity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;

public class GmoAttackGoal extends MeleeAttackGoal {
    private final TamableAnimal modifiedEntity;
    private int runAttackTicks;


    public GmoAttackGoal(TamableAnimal mob, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(mob, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.modifiedEntity = mob;
    }

    @Override
    public boolean canUse() {
        if(mob instanceof GMOEntity gmoEntity && (gmoEntity.isAggressive() || gmoEntity.defendOwner())|| mob instanceof GMOLandEntity gmoLandEntity && (gmoLandEntity.isAggressive()|| gmoLandEntity.defendOwner())){
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void start() {
        super.start();
        this.runAttackTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.modifiedEntity.setAggressive(false);
    }

    @Override
    public void tick() {
        super.tick();
        this.runAttackTicks++;
        if(this.runAttackTicks >=5 && this.getTicksUntilNextAttack() < this.getAttackInterval() / 2) {
            this.modifiedEntity.setAggressive(true);
        } else {
            this.modifiedEntity.setAggressive(false);
        }
    }
}
