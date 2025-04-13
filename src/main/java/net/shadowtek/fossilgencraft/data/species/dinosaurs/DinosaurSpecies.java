package net.shadowtek.fossilgencraft.data.species.dinosaurs;

import net.shadowtek.fossilgencraft.data.species.dinosaurs.fixedtraits.DinosaurDiet;
import net.shadowtek.fossilgencraft.data.species.dinosaurs.fixedtraits.DinosaurPeriod;

//These are the set Immutable Dinosaur Tags for example: Diet, It must be A Carnivore, Herbivore or Omnivore -> set this during step one of registering a dinosaur in ModDinosaurSpecies.java
public class DinosaurSpecies {

    private final DinosaurDiet diet;
    private final DinosaurPeriod period;

    public DinosaurSpecies(DinosaurDiet diet, DinosaurPeriod period){
        this.diet= diet;
        this.period= period;
    }
    public DinosaurDiet getDiet(){
        return this.diet;
    }
    public DinosaurPeriod getDinosaurPeriod(){
        return this.period;
    }
}
