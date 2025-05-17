package net.shadowtek.fossilgencraft.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
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
import net.shadowtek.fossilgencraft.block.entity.custom.AmberExtractorBlockEntity;
import net.shadowtek.fossilgencraft.block.entity.custom.FreezeDryerBlockEntity;
import org.jetbrains.annotations.Nullable;

public class FreezeDryerBlock extends BaseEntityBlock{
    public static final MapCodec<FreezeDryerBlock> CODEC = simpleCodec(FreezeDryerBlock::new);



    public FreezeDryerBlock(BlockBehaviour.Properties properties){
        super(properties);
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FreezeDryerBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState pState, ServerLevel pLevel, BlockPos pPos,
                                               boolean pMovedByPiston) {
        if(pLevel.getBlockEntity(pPos) instanceof FreezeDryerBlockEntity freezeDryerBlockEntity){
            freezeDryerBlockEntity.drops();
            pLevel.updateNeighbourForOutputSignal(pPos, this);
        }



        super.affectNeighborsAfterRemoval(pState, pLevel, pPos, pMovedByPiston);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel,
                                          BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof FreezeDryerBlockEntity FreezeDryerBlockEntity) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(FreezeDryerBlockEntity , Component.literal("Freeze Dryer")), pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if (pLevel.isClientSide()){
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.FREEZEDRYER_BE.get(),
                (level, blockpos, blockstate, freezeDryerBlockEntity) -> freezeDryerBlockEntity.tick(level, blockpos, blockstate));

    }
}


