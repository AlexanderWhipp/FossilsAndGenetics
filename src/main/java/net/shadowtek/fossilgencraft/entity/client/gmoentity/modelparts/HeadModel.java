package net.shadowtek.fossilgencraft.entity.client.gmoentity.modelparts;

import net.minecraft.resources.ResourceLocation;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;
import software.bernie.geckolib.model.GeoModel;

public class HeadModel extends GeoModel<GMOLandEntity> {
    @Override
    public ResourceLocation getModelResource(GMOLandEntity animatable) {
        return animatable.gene2SpeciesHeadModelLocation();
    }

    @Override
    public ResourceLocation getTextureResource(GMOLandEntity animatable) {
        return animatable.gene2SpeciesHeadTextureLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(GMOLandEntity animatable) {
        String path = animatable.gene1SpeciesModelLocation();
        return ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, path);
    }
}
