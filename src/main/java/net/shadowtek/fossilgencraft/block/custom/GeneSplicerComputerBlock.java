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
import net.shadowtek.fossilgencraft.block.entity.custom.SplicerBlockEntity;
import org.jetbrains.annotations.Nullable;


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
        return new SplicerBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState pState, ServerLevel pLevel, BlockPos pPos,
                                               boolean pMovedByPiston) {
        if(pLevel.getBlockEntity(pPos) instanceof SplicerBlockEntity splicerBlockEntity){
            splicerBlockEntity.drops();
            pLevel.updateNeighbourForOutputSignal(pPos, this);
        }



        super.affectNeighborsAfterRemoval(pState, pLevel, pPos, pMovedByPiston);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel,
                                          BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof SplicerBlockEntity SplicerBlockEntity) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(SplicerBlockEntity , Component.literal("DNA Splicer")), pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.GENE_SPLICER_COMPUTER_BE.get(),
                (level, blockpos, blockstate, geneSplicerBlockEntity)->geneSplicerBlockEntity.tick(level, blockpos, blockstate)); //Calls a method once every tick (20 times a second) inside of the block entity class
    }
}


