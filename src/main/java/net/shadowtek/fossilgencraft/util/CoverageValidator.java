package net.shadowtek.fossilgencraft.util;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.shadowtek.fossilgencraft.event.ModDataComponents;

import java.util.HashSet;
import java.util.Set;


public class CoverageValidator {


    public static boolean validateContainerCoverage(AbstractContainerMenu menu) {
        Set<Integer> covered = new HashSet<>();

        // Build the expected range: 0 to 100 inclusive

        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (!stack.isEmpty()) {
                Integer start = stack.get(ModDataComponents.DNA_CHAIN_START_POS.get());
                Integer end = stack.get(ModDataComponents.DNA_CHAIN_END_POS.get());

                if (start != null && end != null) {
                    for (int i = Math.min(start, end); i <= Math.max(start, end); i++) {
                        covered.add(i);
                    }
                }
            }
        }
        Set<Integer> expected = new HashSet<>();
        for (int i = 1; i <= 100; i++) {
            expected.add(i); // or skip 10s here if needed

        }
        System.out.println("[DEBUG] Covered values: " + covered);
        System.out.println("[DEBUG] Missing values: " + getMissingValues(expected, covered));

        return covered.containsAll(expected);
    }

    private static Set<Integer> getMissingValues(Set<Integer> expected, Set<Integer> actual) {
        Set<Integer> missing = new HashSet<>(expected);
        missing.removeAll(actual);
        return missing;
    }
}






