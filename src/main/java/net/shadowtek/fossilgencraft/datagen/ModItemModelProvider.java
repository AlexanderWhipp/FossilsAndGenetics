package net.shadowtek.fossilgencraft.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.MossBlock;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FossilGenCraft.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels(){
        basicItem(ModItems.AMBER.get()); //To be removed
        basicItem(ModItems.FOSSIL_IMPRINT.get());
        basicItem(ModItems.AMBER_SHARD.get());

        basicItem(ModItems.TEST_TUBE_WATER.get());
        basicItem(ModItems.TEST_TUBE_EMPTY.get());
        basicItem(ModItems.TEST_TUBE_BLOOD_CHICKEN.get());
        basicItem(ModItems.TEST_TUBE_BLOOD_PIG.get());

        basicItem(ModItems.SYRINGE_EMPTY.get());
        basicItem(ModItems.SYRINGE_CONTAMINATED.get());
        basicItem(ModItems.SYRINGE_BLOOD_CHICKEN.get());
        basicItem(ModItems.SYRINGE_BLOOD_PIG.get());

        basicItem(ModItems.DNA_SAMPLE_CHICKEN.get());
        basicItem(ModItems.DNA_SAMPLE_PIG.get());
        basicItem(ModItems.CONTAMINATED_MOSQUITO_SAMPLE.get());
        basicItem(ModItems.PURIFIED_MOSQUITO_SAMPLE.get());
        basicItem(ModItems.COMPLETED_GENOME.get());
        basicItem(ModItems.UNSEQUENCED_DNA_CONCENTRATE.get());
        basicItem(ModItems.EGG_CELL.get());
        basicItem(ModItems.EMBRYO.get());
        basicItem(ModItems.FUNGAL_ENZYME_SAMPLE.get());
        basicItem(ModItems.ENZYME_RESIDUE.get());
        basicItem(ModItems.ENZYME_RESIDUE_DRIED.get());
        basicItem(ModItems.MORTAR_AND_PESTLE.get());
        basicItem(ModItems.ENZYME_POWDER.get());
        basicItem(ModItems.ASH.get());
        basicItem(ModItems.SULFUR.get());
        basicItem(ModItems.PIG_FAT.get());
        basicItem(ModItems.DETERGENT.get());
        basicItem(ModItems.CHEMICAL_LYSIS_BUFFER.get());
        basicItem(ModItems.CONTAMINANT_RESIDUE.get());
        basicItem(ModItems.SEQUENCING_REAGENT.get());
        basicItem(ModItems.FLOURESCENT_GEL.get());


        basicItem(ModItems.SEQUENCED_DNA_BASE_CHAIN.get());



        withExistingParent(ModItems.TREX_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));


    }
}
