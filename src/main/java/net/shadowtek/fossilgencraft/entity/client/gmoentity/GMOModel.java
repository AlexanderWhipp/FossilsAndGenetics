package net.shadowtek.fossilgencraft.entity.client.gmoentity;

import com.google.common.collect.Maps;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.PathfinderMob;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.client.GeneOneVariants;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import software.bernie.geckolib.GeckoLib;
import software.bernie.geckolib.model.GeoModel;

import java.util.Map;

public class GMOModel extends GeoModel<GMOEntity> {

    private static final Map<GeneOneVariants, ResourceLocation> MODEL_LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GeneOneVariants.class), map -> {
                map.put(GeneOneVariants.CHICKEN,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "geo/gmoentity/base/chicken.geo.json"));
                map.put(GeneOneVariants.FROG,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "geo/gmoentity/base/frog.geo.json"));
            });

 private static final Map<GeneOneVariants, ResourceLocation> SKIN_LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GeneOneVariants.class), map -> {
                map.put(GeneOneVariants.CHICKEN,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/base/chicken.png"));
                map.put(GeneOneVariants.FROG,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "textures/gmoentity/base/frog.png"));
            });

  private static final Map<GeneOneVariants, ResourceLocation> ANIMATIONS_LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GeneOneVariants.class), map -> {
                map.put(GeneOneVariants.CHICKEN,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "animations/base/chicken.move.walk.json"));
                map.put(GeneOneVariants.FROG,
                        ResourceLocation.fromNamespaceAndPath(FossilGenCraft.MOD_ID, "animations/base/frog.move.hop.json"));
            });




    @Override
    public ResourceLocation getModelResource(GMOEntity animatable) {
        return MODEL_LOCATION_BY_VARIANT.get(animatable.getTypeVariant());
    }

    @Override
    public ResourceLocation getTextureResource(GMOEntity animatable) {
        return SKIN_LOCATION_BY_VARIANT.get(animatable.getTypeVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(GMOEntity animatable) {
        return ANIMATIONS_LOCATION_BY_VARIANT.get(animatable.getTypeVariant());
    }
}
