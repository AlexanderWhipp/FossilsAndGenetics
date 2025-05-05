package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.shadowtek.fossilgencraft.FossilGenCraft;
import net.shadowtek.fossilgencraft.data.DnaExtractionInfo;
import net.shadowtek.fossilgencraft.data.loader.DnaExtractionManager;
import net.shadowtek.fossilgencraft.event.ModDataComponents;
import net.shadowtek.fossilgencraft.item.ModItems;


public class SyringeItem extends Item {

    public SyringeItem(Properties pProperties) {
        super(pProperties);

    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        Level level = player.level();
        if (level.isClientSide()) {
            return InteractionResult.PASS;
        }
        System.err.println("--- Syringe Interaction Start (Server) ---"); // Start marker
        System.err.println("Target: " + target.getType().getDescriptionId());

        EntityType<?> targetEntityType = target.getType();

        DnaExtractionInfo extractionInfo = DnaExtractionManager.getInfoForEntity(targetEntityType);


        if (extractionInfo == null) {
            System.err.println("--> No DnaExtractionInfo found. Returning PASS."); // Reason for stopping
            System.err.println("--- Syringe Interaction End ---");
            return InteractionResult.PASS;
        }


            Item sampleItem = BuiltInRegistries.ITEM.get(extractionInfo.sampleItem());
            if (sampleItem == null || sampleItem == Items.AIR) {
                // Log an error if the item defined in JSON doesn't exist
                System.err.println("Invalid sample_item defined for entity {}: {}" + extractionInfo.entityId() + extractionInfo.sampleItem());
                return InteractionResult.FAIL;
            }
            ItemStack sampleOutPutStack = new ItemStack(sampleItem);
            try {
                sampleOutPutStack.set(ModDataComponents.DNA_SPECIES_ID.get(), extractionInfo.dnaSpecies());
                sampleOutPutStack.set(ModDataComponents.DNA_TYPE_ID.get(), extractionInfo.dnaType());
                sampleOutPutStack.set(ModDataComponents.DNA_INTEGRITY_ID.get(), extractionInfo.dnaIntegrity());
                sampleOutPutStack.set(ModDataComponents.CONTAMINATED_SCORE.get(), extractionInfo.dnaContaminationScore());
                sampleOutPutStack.set(ModDataComponents.DNA_ERA_ID.get(), extractionInfo.dnaEraId());
                sampleOutPutStack.set(ModDataComponents.DNA_FULL_GENOME_CODE.get(), extractionInfo.fullGeneCode());

            } catch (Exception e) {
                // Catch potential errors if component registration failed, though unlikely now
                FossilGenCraft.LOGGER.error("Failed to set data components on blood sample!", e);
                System.err.println("Failed to apply data components");
                return InteractionResult.FAIL;
            }
            boolean addedToInventory = player.getInventory().add(sampleOutPutStack); // Try adding to main inventory
            if (!addedToInventory) {
                player.drop(sampleOutPutStack, false); // Drop the item if inventory is full
            }

            // Apply cooldown TO THE PLAYER for using this item
            player.getCooldowns().addCooldown(this, 20); // 20 ticks = 1 second player cooldown

            // Consume the syringe used (pStack is the stack in the player's hand)
            // Only consume if not in creative mode
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }


            // Interaction fully succeeded
            return InteractionResult.SUCCESS;

        } // End of if (extractionInfo != null)


    }
