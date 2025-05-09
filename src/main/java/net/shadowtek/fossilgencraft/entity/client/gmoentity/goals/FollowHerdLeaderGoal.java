package net.shadowtek.fossilgencraft.entity.client.gmoentity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;

import java.util.List;

public class FollowHerdLeaderGoal extends Goal {
    private final PathfinderMob mob;
    private LivingEntity leader;
    private final double speed;
    private final double minDistance;
    private final double maxDistance;
    private final String geneOne;

    public FollowHerdLeaderGoal(PathfinderMob mob, double speed, double minDistance, double maxDistance, String geneOne){
        this.mob = mob;
        this.speed = speed;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.geneOne = geneOne;
    }

    @Override
    public boolean canUse() {
        if(!(mob instanceof GMOEntity gmoEntity) || !(mob instanceof GMOLandEntity landEntity)) {
            return false;
        }
        if(mob instanceof GMOEntity && gmoEntity.canHerd()) {
            if (gmoEntity.getHerdLeader() == null || !gmoEntity.getHerdLeader().isAlive()) {
                List<GMOEntity> nearby = mob.level().getEntitiesOfClass(GMOEntity.class, mob.getBoundingBox().inflate(8),
                        e -> e != mob && e.canHerd() && e.isHerdLeader() && e.getTypeVariant().equals(geneOne));
                if (!nearby.isEmpty()) {
                    gmoEntity.setHerdLeader(nearby.get(0));
                } else {
                    gmoEntity.setHerdLeaderStatus(true);
                    return false;
                }
            }
            leader = gmoEntity.getHerdLeader();
            return leader != null && mob.distanceTo(leader) > minDistance;
        }
        if(mob instanceof GMOLandEntity && gmoEntity.canHerd()) {
            if (landEntity.getHerdLeader() == null || !landEntity.getHerdLeader().isAlive()) {
                List<GMOLandEntity> nearby = mob.level().getEntitiesOfClass(GMOLandEntity.class, mob.getBoundingBox().inflate(8),
                        e -> e != mob && e.canHerd() && e.isHerdLeader() && e.getTypeVariant().equals(geneOne));
                if (!nearby.isEmpty()) {
                    landEntity.setHerdLeader(nearby.get(0));
                } else {
                    landEntity.setHerdLeaderStatus(true);
                    return false;
                }
            }
            leader = landEntity.getHerdLeader();
            return leader != null && mob.distanceTo(leader) > minDistance;
        } else {
            return false;
        }

    }

    @Override
    public void start() {
        mob.getNavigation().moveTo(leader, speed);
    }

    @Override
    public boolean canContinueToUse() {
        return leader != null && leader.isAlive()  && mob.distanceTo(leader) > minDistance && mob.distanceTo(leader) < maxDistance;
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
    }
}
