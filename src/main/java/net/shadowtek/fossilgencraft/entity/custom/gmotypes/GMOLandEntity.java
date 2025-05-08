package net.shadowtek.fossilgencraft.entity.custom.gmotypes;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;

public class GMOLandEntity extends GMOEntity {
    //Land-Locked Creatures
    public GMOLandEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public static AttributeSupplier setLandAttributes(){
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH,20)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.JUMP_STRENGTH,0.5)
                .add(Attributes.GRAVITY, 0.7)
                .add(Attributes.SAFE_FALL_DISTANCE, 10)
                .build();
    }


}
