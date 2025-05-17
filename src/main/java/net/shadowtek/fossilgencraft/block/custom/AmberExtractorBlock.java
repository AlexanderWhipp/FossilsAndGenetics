package net.shadowtek.fossilgencraft.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.shadowtek.fossilgencraft.block.entity.ModBlockEntities;
import net.shadowtek.fossilgencraft.block.entity.custom.AmberExtractorBlockEntity;
import org.jetbrains.annotations.Nullable;

public class AmberExtractorBlock extends BaseEntityBlock {
    public static final VoxelShape SHAPE = Block.box(2,0,2,14,13,14);//Sets the shape of the Model
    public static final MapCodec<AmberExtractorBlock> CODEC = simpleCodec(AmberExtractorBlock::new);

    public AmberExtractorBlock(Properties pProperties) {
        super(pProperties);
    }
    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL; //Important otherwise block will be invisible
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AmberExtractorBlockEntity(pPos, pState);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState pState, ServerLevel pLevel, BlockPos pPos,
                                               boolean pMovedByPiston) {
        if(pLevel.getBlockEntity(pPos) instanceof AmberExtractorBlockEntity amberExtractorBlockEntity){
            amberExtractorBlockEntity.drops();
            pLevel.updateNeighbourForOutputSignal(pPos, this);
        }



        super.affectNeighborsAfterRemoval(pState, pLevel, pPos, pMovedByPiston);
    }

    @Override
    protected InteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, //enables right-clicking/interactions with the block
                                          BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if(pLevel.getBlockEntity(pPos) instanceof AmberExtractorBlockEntity amberExtractorBlockEntity) {
            if(!pLevel.isClientSide()) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(amberExtractorBlockEntity, Component.literal("Amber Extractor")), pPos);
                return InteractionResult.SUCCESS; //Opens inventory of block when right-clicked
            }

            if(pPlayer.isCrouching() && pLevel.isClientSide()) {
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }
        return createTickerHelper(pBlockEntityType, ModBlockEntities.AMBER_EXTRACTOR_BE.get(),
                (level, blockpos, blockstate, amberExtractorBlockEntity)->amberExtractorBlockEntity.tick(level, blockpos, blockstate)); //Calls a method once every tick (20 times a second) inside of the block entity class
    }
}
