package net.shadowtek.fossilgencraft.entity.custom;

import com.google.common.collect.Maps;
import com.sun.jna.platform.win32.Variant;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.entity.client.GeneOneVariants;
import net.shadowtek.fossilgencraft.entity.client.gmoentity.GMOBehaviour;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Map;

public class GMOEntity extends PathfinderMob implements GeoEntity {
    public static final EntityDataAccessor<Integer> GENE_VARIANT_ONE =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> GENE_VARIANT_TWO =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> GENE_VARIANT_THREE =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.INT);


    public static final EntityDataAccessor<Boolean> HEALTH_MODIFIER =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.BOOLEAN);




protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("chicken.move.walk");

private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public GMOEntity(EntityType<? extends PathfinderMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller",0,this::predicate));

    }
    protected <T extends GMOEntity> PlayState predicate(final AnimationState<T> event) {
        if (event.isMoving()) {
            //Disabling just stop that error loop in the system output
           // event.getController().setAnimation(RawAnimation.begin().then("chicken.move.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }


    public static AttributeSupplier setAttributes(){
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4f)
                .build();
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this,1.00));




    }



    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(GENE_VARIANT_ONE, 0);
        pBuilder.define(GENE_VARIANT_TWO, 0);


        pBuilder.define(GENE_VARIANT_THREE, 0);
        pBuilder.define(HEALTH_MODIFIER, true);
    }
    public int getTypeVariant(){
        return this.entityData.get(GENE_VARIANT_ONE);
    }
    public int getGene2VariantType(){
        return this.entityData.get(GENE_VARIANT_TWO);
    }
     public int getGene3VariantType(){
        return this.entityData.get(GENE_VARIANT_THREE);
    }

    public GeneOneVariants getVariant(){
        return GeneOneVariants.byId(this.getTypeVariant() & 255);
    }
    public GeneOneVariants getGeneTwoVariant(){
        return GeneOneVariants.byId(this.getGene2VariantType() & 255);
    }
    public GeneOneVariants getGeneThreeVariant(){
        return GeneOneVariants.byId(this.getGene3VariantType() & 255);
    }

    public void setVariant(GeneOneVariants variant){
    this.entityData.set(GENE_VARIANT_ONE, variant.getId() & 255);
    this.entityData.set(GENE_VARIANT_TWO, variant.getId() & 255);
    this.entityData.set(GENE_VARIANT_THREE, variant.getId() & 255);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Gene1", this.getTypeVariant());
        pCompound.putInt("Gene2", this.getGene2VariantType());
        pCompound.putInt("Gene3", this.getGene3VariantType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(GENE_VARIANT_ONE, pCompound.getInt("Gene1"));
        this.entityData.set(GENE_VARIANT_TWO, pCompound.getInt("Gene2"));
        this.entityData.set(GENE_VARIANT_THREE  , pCompound.getInt("Gene3"));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty,
                                        MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
         GeneOneVariants geneOneVariant = GeneOneVariants.byId(this.entityData.get(GENE_VARIANT_ONE));
         GeneOneVariants geneTwoVariant = GeneOneVariants.byId(this.entityData.get(GENE_VARIANT_TWO));
         GeneOneVariants geneThreeVariant = GeneOneVariants.byId(this.entityData.get(GENE_VARIANT_THREE));
         this.setVariant(geneOneVariant);
         this.setVariant(geneTwoVariant);
         this.setVariant(geneThreeVariant);

        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }
}
