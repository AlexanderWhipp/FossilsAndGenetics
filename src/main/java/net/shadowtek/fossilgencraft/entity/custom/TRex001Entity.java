package net.shadowtek.fossilgencraft.entity.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.shadowtek.fossilgencraft.entity.ModEntities;


public class TRex001Entity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public TRex001Entity(EntityType<? extends Animal> pEntityType, Level pLevel) {

        super(pEntityType, pLevel);
    }
    //public float getEyeHeight(Pose pose, EntityDimensions size) {
        //if (this.isBaby()) {
         // /  return 1.0F;  // Baby T. rex eye height
        //} else {
           // return 2.0F;  // Adult T. rex eye height
       // }
   // }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0,new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this,2.0));
        this.goalSelector.addGoal(2, new BreedGoal(this,1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, stack -> stack.is(Items.PORKCHOP), false));

        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));

        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));




    }
    public static AttributeSupplier.Builder createAttributes()  {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 50.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35)
                .add(Attributes.FOLLOW_RANGE, 24.0);


    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.PORKCHOP);
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return ModEntities.TREX001.get().create(pLevel);
    }
    private void setupAnimationStates() {
        if(this.idleAnimationTimeout<=0) {
            this.idleAnimationTimeout = 80;
            this.idleAnimationState.start(this.tickCount);

        }else{
            --this.idleAnimationTimeout;
        }
    }


    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()){
            this.setupAnimationStates();
        }


    }

}
