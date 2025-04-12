package net.shadowtek.fossilgencraft.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.block.entity.custom.GeneSplicerComputerBlockEntity;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.world.level.block.state.BlockBehaviour.simpleCodec;

public class GeneSplicerComputerBlock extends BaseEntityBlock {
    public static final MapCodec<GeneSplicerComputerBlock> CODEC = simpleCodec(GeneSplicerComputerBlock::new);



    public GeneSplicerComputerBlock(BlockBehaviour.Properties properties){
        super(properties);
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new GeneSplicerComputerBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof GeneSplicerComputerBlockEntity geneSplicerComputerBlockEntity) {
                geneSplicerComputerBlockEntity.drops();
            }
        }
        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel,
                                              BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof GeneSplicerComputerBlockEntity GeneSplicerBlockEntity) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(GeneSplicerBlockEntity , Component.literal("Freeze Dryer")), pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return ItemInteractionResult.sidedSuccess(pLevel.isClientSide());
    }
//Crafting logic Disabled for Menu Screen Tests
  //  @Override
 //   public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
   //     if (pLevel.isClientSide()){
    //        return null;
     //   }
     //   return createTickerHelper(pBlockEntityType, ModBlockEntities.GENE_SPLICER_COMPUTER_BE.get(),
       //         (level, blockpos, blockstate, geneSplicerBlockEntity) -> GeneSplicerComputerBlockEntity.tick(level, blockpos, blockstate));

   // }
}

