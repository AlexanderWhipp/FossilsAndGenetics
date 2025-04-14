package net.shadowtek.fossilgencraft.data.species.pleistocene;


import net.shadowtek.fossilgencraft.data.species.pleistocene.fixedtraits.PleistoceneDiet;
import net.shadowtek.fossilgencraft.data.species.pleistocene.fixedtraits.PleistocenePeriod;

public class PleistoceneSpecies {
    private final PleistoceneDiet diet;
    private final PleistocenePeriod period;

    public PleistoceneSpecies(PleistoceneDiet diet, PleistocenePeriod period){
        this.diet= diet;
        this.period= period;
    }
    public PleistoceneDiet getDiet(){
        return this.diet;
    }
    public PleistocenePeriod getDinosaurPeriod(){
        return this.period;
    }
}

