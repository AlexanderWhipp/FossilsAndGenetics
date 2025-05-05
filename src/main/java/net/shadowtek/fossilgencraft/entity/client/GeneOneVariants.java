package net.shadowtek.fossilgencraft.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum GeneOneVariants {
    CHICKEN(0),
    FROG(1);

    private static final GeneOneVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(GeneOneVariants::getId)).toArray(GeneOneVariants[]::new);
    private final int id;


    GeneOneVariants(int id) {
        this.id = id;
    }
    public int getId(){
        return this.id;
    }


    public static GeneOneVariants byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
