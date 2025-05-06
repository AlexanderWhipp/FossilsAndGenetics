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
                .add(Attributes.JUMP_STRENGTH)
                .add(Attributes.GRAVITY)
                .add(Attributes.SAFE_FALL_DISTANCE)
                .build();
    }


}
