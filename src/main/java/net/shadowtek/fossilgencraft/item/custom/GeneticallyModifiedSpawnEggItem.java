package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.shadowtek.fossilgencraft.data.geneassignmentinfo.GeneOneAssignmentInfo;
import net.shadowtek.fossilgencraft.data.loader.GeneOneAssignmentManager;
import net.shadowtek.fossilgencraft.entity.client.GeneOneVariants;
import net.shadowtek.fossilgencraft.entity.custom.GMOEntity;
import net.shadowtek.fossilgencraft.event.ModDataComponents;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static net.shadowtek.fossilgencraft.entity.custom.GMOEntity.*;

public class GeneticallyModifiedSpawnEggItem extends SpawnEggItem {


    public GeneticallyModifiedSpawnEggItem(Supplier<? extends EntityType<? extends PathfinderMob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        String geneLabel1 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_1.get());
        String geneLabel2 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_2.get());
        String geneLabel3 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_3.get());
        String geneLabel4 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_4.get());
        String geneLabel5 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_5.get());
        String geneLabel6 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_6.get());
        String geneLabel7 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_7.get());
        String geneLabel8 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_8.get());
        String geneLabel9 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_9.get());
        String geneLabel10 = pStack.get(ModDataComponents.DNA_COMPLETED_GENOME_GENE_LABEL_SLOT_10.get());


        if (geneLabel1 != null && !geneLabel1.isEmpty()){
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.dna.gene_label_1", geneLabel1)
            );
        }
        if (geneLabel2 != null && !geneLabel2.isEmpty()){
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.dna.gene_label_2", geneLabel2)
            );
        }
        if (geneLabel3 != null && !geneLabel3.isEmpty()){
            pTooltipComponents.add(
                    Component.translatable("tooltip.fossilgencraft.dna.gene_label_2", geneLabel3)
            );
        }

    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
       if(!pContext.getLevel().isClientSide()){
           ItemStack stack = pContext.getItemInHand();
           Entity entity = this.getType(stack).spawn(
                   (ServerLevel) pContext.getLevel(), stack, pContext.getPlayer(),
                   pContext.getClickedPos(), MobSpawnType.SPAWN_EGG, true, false
           );



           if (entity instanceof GMOEntity mob) {
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
               int variantID;
               if (geneSpecies1.equals("minecraft:chicken")) {
                   variantID = 0;
                   mob.getEntityData().set(GENE_VARIANT_ONE, geneSpecies1);
               }
               if (geneSpecies2.equals("minecraft:chicken")) {
                   variantID = 0;
                   mob.getEntityData().set(GENE_VARIANT_TWO, geneSpecies2);
               }
               if (geneSpecies1.equals("minecraft:frog")) {
                   variantID = 1;
                   mob.getEntityData().set(GENE_VARIANT_ONE, geneSpecies1);
               }
               if (geneSpecies2.equals("minecraft:frog")) {
                   variantID = 1;
                   mob.getEntityData().set(GENE_VARIANT_TWO, geneSpecies2);
               }
               if (geneSpecies3.equals("minecraft:chicken")) {
                   variantID = 0;
                   mob.getEntityData().set(GENE_VARIANT_THREE, geneSpecies3);
                   mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(100);
                   System.out.println("Applying chicken gene 3");
               }
               if (geneSpecies3.equals("minecraft:frog")) {
                   variantID = 1;
                   mob.getEntityData().set(GENE_VARIANT_THREE, geneSpecies3);
                   mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(10);
                   System.out.println("Applying frog gene 3");

               }
       }
    }
       return InteractionResult.SUCCESS;
}
    @Override
    public Optional<Mob> spawnOffspringFromSpawnEgg(Player pPlayer, Mob pMob, EntityType<? extends Mob> pEntityType, ServerLevel pServerLevel, Vec3 pPos, ItemStack pStack) {
        return super.spawnOffspringFromSpawnEgg(pPlayer, pMob, pEntityType, pServerLevel, pPos, pStack);
    }
}
