package net.shadowtek.fossilgencraft.entity.custom;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.level.Level;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneNineAssignmentInfo;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneSevenAssignmentInfo;
import net.shadowtek.fossilgencraft.data.loader.GeneNineAssignmentManager;
import net.shadowtek.fossilgencraft.data.loader.GeneSevenAssignmentManager;
import net.shadowtek.fossilgencraft.entity.client.gmoentity.goals.FollowHerdLeaderGoal;
import net.shadowtek.fossilgencraft.entity.client.gmoentity.goals.LayEggGoal;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;



public class GMOEntity extends PathfinderMob implements GeoEntity {
    public static final EntityDataAccessor<String> GENE_VARIANT_ONE =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> GENE_VARIANT_TWO =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);

    public static final EntityDataAccessor<String> GENE_VARIANT_THREE =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);


    public static final EntityDataAccessor<String> GENE_VARIANT_FOUR =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);


    public static final EntityDataAccessor<String> GENE_VARIANT_FIVE =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);


    public static final EntityDataAccessor<String> GENE_VARIANT_SIX =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);


    public static final EntityDataAccessor<String> GENE_VARIANT_SEVEN =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);


    public static final EntityDataAccessor<String> GENE_VARIANT_EIGHT =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);


    public static final EntityDataAccessor<String> GENE_VARIANT_NINE =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);


    public static final EntityDataAccessor<String> GENE_VARIANT_TEN =
            SynchedEntityData.defineId(GMOEntity.class, EntityDataSerializers.STRING);





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

            event.getController().setAnimation(RawAnimation.begin().then("chicken.move.walk", Animation.LoopType.LOOP));
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


        //FossilGenCraft Custom Goals, only active if genes are present!
        this.goalSelector.addGoal(5, new FollowHerdLeaderGoal(this,1,1.0,10,getTypeVariant()));
        this.goalSelector.addGoal(6, new LayEggGoal(this));




    }



    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(GENE_VARIANT_ONE, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_TWO, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_THREE, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_FOUR, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_FIVE, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_SIX, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_SEVEN, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_EIGHT, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_NINE, "minecraft:chicken");
        pBuilder.define(GENE_VARIANT_TEN, "minecraft:chicken");

    }
    public String getTypeVariant(){
        return this.entityData.get(GENE_VARIANT_ONE);
    }
    public String getGene2VariantType(){
        return this.entityData.get(GENE_VARIANT_TWO);
    }
    public String getGene3VariantType(){return this.entityData.get(GENE_VARIANT_THREE);}
    public String getGene4VariantType(){return this.entityData.get(GENE_VARIANT_FOUR);}
    public String getGene5VariantType(){return this.entityData.get(GENE_VARIANT_FIVE);}
    public String getGene6VariantType(){return this.entityData.get(GENE_VARIANT_SIX);}
    public String getGene7VariantType(){return this.entityData.get(GENE_VARIANT_SEVEN);}
    public String getGene8VariantType(){return this.entityData.get(GENE_VARIANT_EIGHT);}
    public String getGene9VariantType(){return this.entityData.get(GENE_VARIANT_NINE);}
    public String getGene10VariantType(){return this.entityData.get(GENE_VARIANT_TEN);}

    public LivingEntity herdLeader;
    private boolean isLeader = false;


    public boolean canHerd(){
        GeneNineAssignmentInfo geneNineAssignment = GeneNineAssignmentManager.getGeneNineInfoForEntity(getGene9VariantType());

        if(geneNineAssignment.canHerd()){
            return true;
        } else {
            return false;
        }
    }
    public boolean canLayEggs(){
        GeneSevenAssignmentInfo geneSevenAssignment = GeneSevenAssignmentManager.getGeneSevenInfoForEntity(getGene7VariantType());

        if(geneSevenAssignment.geneSevenGoals().stream().anyMatch(goal -> goal.goal().equals("lays_eggs"))){
            return true;
        } else {
            return false;
        }


    }

    public void setHerdLeader(LivingEntity leader){
       this.herdLeader = leader;
    }
    public LivingEntity getHerdLeader(){
        return this.herdLeader;
    }
    public boolean isHerdLeader() {
        return isLeader;
    }
    public void setHerdLeaderStatus(boolean leader){
        this.isLeader = leader;
    }


    public int fuseTimer = 0;
    public final int maxFuseTime = 30;
    public boolean isExploding = false;


    @Override
    public void tick() {
        super.tick();

        if (getTypeVariant().equals("minecraft:creeper") || getGene3VariantType().equals("minecraft:creeper")) {
            if (shouldExplode()) {
                if (!isExploding) {
                    isExploding = true;
                }
                fuseTimer++;
                if (fuseTimer >= maxFuseTime) {
                    explode();
                }
                } else {
                    fuseTimer = 0;
                    isExploding = false;
                }
        }
    }
        private boolean shouldExplode(){
            return this.getTarget() != null && this.distanceTo(this.getTarget()) < 3.0D;
        }
        private void explode(){
            this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, Level.ExplosionInteraction.MOB);
            this.discard();
        }

        @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putString("Gene1", this.getTypeVariant());
        pCompound.putString(   "Gene2", this.getGene2VariantType());
        pCompound.putString("Gene3", this.getGene3VariantType());
        pCompound.putString("Gene4", this.getGene4VariantType());
        pCompound.putString("Gene5", this.getGene5VariantType());
        pCompound.putString("Gene6", this.getGene6VariantType());
        pCompound.putString("Gene7", this.getGene7VariantType());
        pCompound.putString("Gene8", this.getGene8VariantType());
        pCompound.putString("Gene9", this.getGene9VariantType());
        pCompound.putString("Gene10", this.getGene10VariantType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.entityData.set(GENE_VARIANT_ONE, pCompound.getString("Gene1"));
        this.entityData.set(GENE_VARIANT_TWO, pCompound.getString("Gene2"));
        this.entityData.set(GENE_VARIANT_THREE, pCompound.getString("Gene3"));
        this.entityData.set(GENE_VARIANT_FOUR, pCompound.getString("Gene4"));
        this.entityData.set(GENE_VARIANT_FIVE, pCompound.getString("Gene5"));
        this.entityData.set(GENE_VARIANT_SIX, pCompound.getString("Gene6"));
        this.entityData.set(GENE_VARIANT_SEVEN, pCompound.getString("Gene7"));
        this.entityData.set(GENE_VARIANT_EIGHT, pCompound.getString("Gene8"));
        this.entityData.set(GENE_VARIANT_NINE, pCompound.getString("Gene9"));
        this.entityData.set(GENE_VARIANT_TEN, pCompound.getString("Gene10"));
    }
}
