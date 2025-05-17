package net.shadowtek.fossilgencraft.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.shadowtek.fossilgencraft.block.entity.custom.supercomputer.SuperComputerTerminalBlockEntity;
import org.jetbrains.annotations.Nullable;



public class SuperComputerTerminal extends BaseEntityBlock {
    public static final MapCodec<SuperComputerTerminal> CODEC = simpleCodec(SuperComputerTerminal::new);

    public SuperComputerTerminal(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new SuperComputerTerminalBlockEntity(pPos, pState);
    }

    @SuppressWarnings({"NullableProblems", "RedundantMethodOverride"})
    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected void affectNeighborsAfterRemoval(BlockState pState, ServerLevel pLevel, BlockPos pPos,
                                               boolean pMovedByPiston) {
        if(pLevel.getBlockEntity(pPos) instanceof SuperComputerTerminalBlockEntity superComputerTerminalBlockEntity){
            superComputerTerminalBlockEntity.drops();
            pLevel.updateNeighbourForOutputSignal(pPos, this);
        }



        super.affectNeighborsAfterRemoval(pState, pLevel, pPos, pMovedByPiston);
    }


    @SuppressWarnings("NullableProblems")
    @Override
    protected  InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if(entity instanceof SuperComputerTerminalBlockEntity SuperComputerTerminalBlockEntity) {
                ((ServerPlayer) pPlayer).openMenu(new SimpleMenuProvider(SuperComputerTerminalBlockEntity , Component.literal("Supercomputer")), pPos);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }

        return InteractionResult.SUCCESS;
    }
    @SuppressWarnings("NullableProblems")
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        // Ticking logic runs server-side for this block
        return null;
    }

}
