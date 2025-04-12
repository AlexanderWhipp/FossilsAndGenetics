package net.shadowtek.fossilgencraft.item.custom;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.registries.RegistryObject;

public class TestTubeItem extends Item {
    private final Item TEST_TUBE_WATER;


    public TestTubeItem(Properties properties, Item TEST_TUBE_WATER) {
        super(properties);
        this.TEST_TUBE_WATER = TEST_TUBE_WATER;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);

        if (hitResult.getType() == BlockHitResult.Type.BLOCK) {
            BlockState state = level.getBlockState(hitResult.getBlockPos());

            if (state.is(Blocks.WATER) && state.getFluidState().isSource()) {
                if (!level.isClientSide) {
                    ItemStack newStack = new ItemStack(TEST_TUBE_WATER);
                    stack.shrink(1);
                    if (!player.getInventory().add(newStack)) {
                        player.drop(newStack, false);
                    }
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0f, 1.0f);
                    player.awardStat(Stats.ITEM_USED.get(this));
                }
                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }
}
