package com.orbitmines.archive.minecraft.spigot._2019.servers.prison;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.items.Backpack;
import com.orbitmines.archive.minecraft.spigot._2019.servers.prison.items.PrisonItem;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ConsoleUtils;
import lombok.Getter;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrisonInventory {

    @Getter
    protected final PrisonPlayer player;
    protected final PlayerInventory bukkit;

    @Getter
    private final PrisonItem[] contents;
    @Getter
    private final PrisonItem helmet;
    @Getter
    private final PrisonItem chestplate;
    @Getter
    private final PrisonItem leggings;
    @Getter
    private final PrisonItem boots;
    @Getter
    private final PrisonItem itemInOffHand;

    public PrisonInventory(PrisonPlayer player) {
        this.player = player;
        this.bukkit = player.bukkit().getInventory();

        this.contents = new PrisonItem[this.bukkit.getContents().length];

        for (int i = 0; i < this.contents.length; i++) {
            int index = i;

            this.contents[i] = new PrisonItem() {
                @Override
                public ItemStack getItem() {
                    return PrisonInventory.this.bukkit.getItem(index);
                }

                @Override
                public void setItem(ItemStack itemStack) {
                    PrisonInventory.this.bukkit.setItem(index, itemStack);
                }
            };
        }

        this.helmet = new PrisonItem() {
            @Override
            public ItemStack getItem() {
                return PrisonInventory.this.bukkit.getHelmet();
            }

            @Override
            public void setItem(ItemStack itemStack) {
                PrisonInventory.this.bukkit.setHelmet(itemStack);
            }
        };
        this.chestplate = new PrisonItem() {
            @Override
            public ItemStack getItem() {
                return PrisonInventory.this.bukkit.getChestplate();
            }

            @Override
            public void setItem(ItemStack itemStack) {
                PrisonInventory.this.bukkit.setChestplate(itemStack);
            }
        };
        this.leggings = new PrisonItem() {
            @Override
            public ItemStack getItem() {
                return PrisonInventory.this.bukkit.getLeggings();
            }

            @Override
            public void setItem(ItemStack itemStack) {
                PrisonInventory.this.bukkit.setLeggings(itemStack);
            }
        };
        this.boots = new PrisonItem() {
            @Override
            public ItemStack getItem() {
                return PrisonInventory.this.bukkit.getBoots();
            }

            @Override
            public void setItem(ItemStack itemStack) {
                PrisonInventory.this.bukkit.setBoots(itemStack);
            }
        };

        this.itemInOffHand = new PrisonItem() {
            @Override
            public ItemStack getItem() {
                return PrisonInventory.this.bukkit.getBoots();
            }

            @Override
            public void setItem(ItemStack itemStack) {
                PrisonInventory.this.bukkit.setBoots(itemStack);
            }
        };
    }

    public PrisonItem getHeldItem() {
        return this.contents[player.getHeldItemSlot()];
    }

    public int getSize() {
        int size = contents.length;

        for (PrisonItem item : contents) {
            if (item.isBackpack())
                size += item.getBackpackSize() - 1;
        }

        if (itemInOffHand.isBackpack())
            size += itemInOffHand.getBackpackSize();
        else
            size += 1;

        return size;
    }

    //TODO! CHECK FOR THE NBT-TAGS & REVERT THE TWO!
    /* returns remaining amount */
    public int addItem(ItemStack itemStack, int amount) {
        if (amount > 0) {
            List<PrisonItem> items = Arrays.stream(contents).filter(PrisonItem::isBackpack).collect(Collectors.toList());

            for (PrisonItem item : items) {
                amount = fillUpInventory(item, item.getAsBackpack(), itemStack, amount);
                ConsoleUtils.msg(amount + "");
            }

        }

        if (amount > 0) {
            amount = fillUpInventory(null, bukkit, itemStack, amount);
        }

        return amount;
    }

    public PrisonItem findFirst(ItemStack itemStack) {
        int slot = bukkit.first(itemStack);

        if (slot != -1)
            return contents[slot];

        if (itemStack.isSimilar(itemInOffHand.getItem()))
            return itemInOffHand;

        if (itemStack.isSimilar(helmet.getItem()))
            return helmet;

        if (itemStack.isSimilar(chestplate.getItem()))
            return chestplate;

        if (itemStack.isSimilar(leggings.getItem()))
            return leggings;

        if (itemStack.isSimilar(boots.getItem()))
            return boots;

        return null;
    }

    public PrisonItem getItem(InventoryType.SlotType type, int slot) {
        switch (type) {
            case QUICKBAR:
                if (slot == 40)
                    return itemInOffHand;

                return contents[slot];

            case CONTAINER:
                return contents[slot];

            case ARMOR:
                if (slot == 39)
                    return helmet;

                else if (slot == 38)
                    return chestplate;

                else if (slot == 37)
                    return leggings;

                else
                    return boots;
        }
        return null;
    }

    //TODO isSimilar: Werkt dit op NBT tags?
    private int fillUpInventory(Backpack backpack, Inventory inventory, ItemStack item, int amount) {
        int initialAmount = amount;

        int maxStackSize = item.getMaxStackSize();

        for (int i = 0; i < inventory.getStorageContents().length; i++) {

            if (amount == 0)
                break;

            ItemStack itemStack = inventory.getItem(i);

            if (itemStack != null && !item.isSimilar(itemStack))
                continue;

            else if (itemStack == null) {
                ItemStack newItem = item.clone();

                if (maxStackSize > amount) {
                    newItem.setAmount(amount);

                    amount = 0;
                    break;
                } else {
                    newItem.setAmount(maxStackSize);

                    amount -= maxStackSize;
                }

                inventory.setItem(i, newItem);
                continue;
            }

            if (maxStackSize == itemStack.getAmount())
                continue;

            if (itemStack.getAmount() + amount > maxStackSize) {
                itemStack.setAmount(maxStackSize);
                inventory.setItem(i, itemStack);
                amount -= maxStackSize - itemStack.getAmount();
            } else {
                itemStack.setAmount(itemStack.getAmount() + amount);
                inventory.setItem(i, itemStack);
                amount = 0;
                break;
            }
        }

        // Update backpack if items were added
        if (backpack != null && initialAmount != amount)
            backpack.updateBackpack(inventory);

        return amount;
    }
}
