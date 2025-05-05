package net.shadowtek.fossilgencraft.entity.client.gmoentity;

import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;

public class GMOBehaviour {
    private boolean healthModifier;

    public GMOBehaviour(GMOEntity entity){
        this.healthModifier = entity.getHealthModifier();
    }

    public boolean getHealthModifier(){
        return healthModifier;
    }


}
