package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneOneAssignmentInfo;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneThreeAssignmentInfo;
import net.shadowtek.fossilgencraft.data.loader.GeneOneAssignmentManager;
import net.shadowtek.fossilgencraft.data.loader.GeneThreeAssignmentManager;
import net.shadowtek.fossilgencraft.entity.custom.gmotypes.GMOLandEntity;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import java.util.function.Supplier;
import static net.shadowtek.fossilgencraft.entity.custom.GMOEntity.*;


public class GMOLandEntitySpawnEgg extends GeneticallyModifiedSpawnEggItem{

    public GMOLandEntitySpawnEgg(Supplier<? extends EntityType<? extends PathfinderMob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()){
            ItemStack stack = pContext.getItemInHand();
            Entity entity = this.getType(stack).spawn(
                    (ServerLevel) pContext.getLevel(), stack, pContext.getPlayer(),
                    pContext.getClickedPos(), MobSpawnType.SPAWN_EGG, true, false
            );



            if (entity instanceof GMOLandEntity mob) {
                String geneSpecies1 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_1.get());
                String geneSpecies2 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_2.get());
                String geneSpecies3 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_3.get());
                String geneSpecies4 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_4.get());
                String geneSpecies5 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_5.get());
                String geneSpecies6 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_6.get());
                String geneSpecies7 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_7.get());
                String geneSpecies8 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_8.get());
                String geneSpecies9 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_9.get());
                String geneSpecies10 = stack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_10.get());


                GeneOneAssignmentInfo geneOneAssignment = GeneOneAssignmentManager.getGeneInfoForEntity(geneSpecies1);
                GeneThreeAssignmentInfo geneThreeAssignment = GeneThreeAssignmentManager.getGeneInfoForEntity(geneSpecies3);


                Double maxHealth = geneOneAssignment.base_attribute_values().get(0).attribute_value();
                Double maxMoveSpeed = geneOneAssignment.base_attribute_values().get(1).attribute_value();
                Double maxJump = geneOneAssignment.base_attribute_values().get(2).attribute_value();
                Double gravity = geneOneAssignment.base_attribute_values().get(3).attribute_value();
                Double fallHeight = geneOneAssignment.base_attribute_values().get(4).attribute_value();


                //Default Modifier values
                Double healthModifier = geneThreeAssignment.geneThreeModifiers().get(0).attributeValue();
                Double speedModifier = geneThreeAssignment.geneThreeModifiers().get(1).attributeValue();
                Double jumpModifier = geneThreeAssignment.geneThreeModifiers().get(2).attributeValue();
                Double gravityModifier = geneThreeAssignment.geneThreeModifiers().get(3).attributeValue();
                Double fallHeightModifier =geneThreeAssignment.geneThreeModifiers().get(4).attributeValue();

                //Default creature size
                String creatureSize = geneOneAssignment.creature_size();
                if(creatureSize == null){
                    creatureSize = "small";
                }


                //Default Modifiers
                boolean hasHealthModifier = geneThreeAssignment.geneThreeModifiers().get(0).modifierActive();
                boolean hasSpeedModifier =geneThreeAssignment.geneThreeModifiers().get(1).modifierActive();
                boolean hasjumpModifier = geneThreeAssignment.geneThreeModifiers().get(2).modifierActive();
                boolean hasGravityModifier = geneThreeAssignment.geneThreeModifiers().get(3).modifierActive();
                boolean hasFallHeightModifier = geneThreeAssignment.geneThreeModifiers().get(4).modifierActive();


                if(maxHealth != null){
                    if(hasHealthModifier) {
                        maxHealth += healthModifier;

                    }
                    mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(maxHealth);

                }
                if(maxMoveSpeed != null)
                {
                    if(hasSpeedModifier)
                    {
                        maxMoveSpeed += speedModifier;
                    }
                    mob.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(maxMoveSpeed);
                }

                if(maxJump != null)
                {
                    if (hasjumpModifier)
                    {
                        maxJump += jumpModifier;
                    }
                    mob.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(maxJump);
                }

                if(gravity != null)
                {
                    if(hasGravityModifier) {
                        if (creatureSize.equals("small")) {
                            gravity = gravityModifier;
                        }

                        if (creatureSize.equals("medium")) {
                            if(gravityModifier < 0.3) {
                                gravity = 0.3;
                            } else {
                                gravity = gravityModifier;
                            }
                        }
                        if (creatureSize.equals("large")) {
                            if(gravityModifier < 0.4) {
                                gravity = 0.4;
                            } else {
                                gravity = gravityModifier;
                            }
                        }
                    }
                    mob.getAttribute(Attributes.GRAVITY).setBaseValue(gravity);

                }
                if(fallHeight != null)
                {
                    if(hasFallHeightModifier) {
                        if (creatureSize.equals("small")) {
                            fallHeight = fallHeightModifier;
                        }
                        if (creatureSize.equals("medium")) {
                            if (fallHeightModifier > 100.0) {
                                fallHeight = 100.0;
                            } else {
                                fallHeight = fallHeightModifier;
                            }
                        }
                        if (creatureSize.equals("large")) {
                            if (fallHeightModifier > 50.0) {
                                fallHeight = 50.0;
                            } else {
                                fallHeight = fallHeightModifier;
                            }
                        }
                    }
                    mob.getAttribute(Attributes.SAFE_FALL_DISTANCE).setBaseValue(fallHeight);
                }

                mob.getEntityData().set(GENE_VARIANT_ONE, geneSpecies1);
                mob.getEntityData().set(GENE_VARIANT_TWO, geneSpecies2);
                mob.getEntityData().set(GENE_VARIANT_THREE, geneSpecies3);
                mob.getEntityData().set(GENE_VARIANT_FOUR, geneSpecies4);
                mob.getEntityData().set(GENE_VARIANT_FIVE, geneSpecies5);
                mob.getEntityData().set(GENE_VARIANT_SIX,geneSpecies6);
                mob.getEntityData().set(GENE_VARIANT_SEVEN,geneSpecies7);
                mob.getEntityData().set(GENE_VARIANT_EIGHT, geneSpecies8);
                mob.getEntityData().set(GENE_VARIANT_NINE, geneSpecies9);
                mob.getEntityData().set(GENE_VARIANT_TEN, geneSpecies10);

            }
        }
        return InteractionResult.SUCCESS;

    }
}
