package net.shadowtek.fossilgencraft.entity.client.gmoentity;

import java.util.Arrays;
import java.util.Comparator;

public enum GeneVariantSelector {
    CHICKEN(0),
    FROG(1),
    SALMON(2),
    PARROT(3);

    private static final GeneVariantSelector[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(GeneVariantSelector::getId)).toArray(GeneVariantSelector[]::new);
    private final int id;


    GeneVariantSelector(int id) {
        this.id = id;
    }
    public int getId(){
        return this.id;
    }


    public static GeneVariantSelector byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
