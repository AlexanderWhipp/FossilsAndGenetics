package net.shadowtek.fossilgencraft.entity.client;

import java.util.Arrays;
import java.util.Comparator;

public enum GeneTwoVariants {
    CHICKEN(0),
    FROG(1);

    private static final GeneTwoVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(GeneTwoVariants::getId)).toArray(GeneTwoVariants[]::new);
    private final int id;


    GeneTwoVariants(int id) {
        this.id = id;
    }
    public int getId(){
        return this.id;
    }

    public static GeneTwoVariants byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
