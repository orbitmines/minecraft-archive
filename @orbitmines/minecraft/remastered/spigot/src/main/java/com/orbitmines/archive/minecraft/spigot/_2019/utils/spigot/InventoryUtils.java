package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryUtils {

    public static int getAmount(Inventory inventory, Material material) {
        return ItemUtils.selectItemStacks(inventory.getContents(), material).
                stream().
                mapToInt(ItemStack::getAmount).
                sum();
    }

    public static boolean hasItems(Inventory inventory, Material material, int amount) {
        return getAmount(inventory, material) >= amount;
    }

    public static int removeItems(Inventory inventory, Material material) {
        int count = 0;

        for (ItemStack itemStack : ItemUtils.selectItemStacks(inventory.getContents(), material)) {
            count += itemStack.getAmount();

            inventory.remove(itemStack);
        }

        return count;
    }

    /* Remove items from player's inventory without moving any items from their position, returns the items actually removed */
    public static List<ItemStack> removeItems(Inventory inventory, Material material, int amount) {
        List<ItemStack> removedItems = new ArrayList<>();

        int count = 0;
        for (ItemStack itemStack : ItemUtils.selectItemStacks(inventory.getContents(), material)) {

            if (count + itemStack.getAmount() <= amount) {
                removedItems.add(new ItemStack(itemStack));
                count += itemStack.getAmount();
                inventory.setItem(inventory.first(itemStack), null);

                if (count == amount)
                    return removedItems;

            } else {
                /* LeftOvers */
                ItemStack newItem = new ItemStack(itemStack);
                newItem.setAmount(itemStack.getAmount() - (amount - count));

                ItemStack removedItem = new ItemStack(itemStack);
                removedItem.setAmount(amount - count);
                removedItems.add(removedItem);

                inventory.setItem(inventory.first(itemStack), newItem);

                return removedItems;
            }
        }

        return removedItems;
    }

    public static int getSlotsRequired(Material material, int amount) {
        int maxStackSize = material.getMaxStackSize();
        int leftOver = maxStackSize % amount;

        return leftOver == 0 ? amount / maxStackSize : (amount + maxStackSize - leftOver) /  maxStackSize;
    }

    public static int getEmptySlotCount(Inventory inventory) {
        return ItemUtils.selectItemStacksEmpty(inventory.getContents()).size();
    }
}
