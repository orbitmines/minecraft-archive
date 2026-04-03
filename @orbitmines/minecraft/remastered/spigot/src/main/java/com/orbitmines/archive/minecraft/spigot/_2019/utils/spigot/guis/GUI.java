package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemStack;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class GUI<P extends SpigotPlayer> {

    @Getter protected Inventory inventory;
    @Getter protected Item<P, ?>[] items;
    @Getter protected P viewer;

    public GUI(int size, String title, P viewer) {
        this.inventory = Bukkit.createInventory(null, size, title);
        this.items = new Item[size];
        this.viewer = viewer;
    }

    public void open() {
        update(true);

        viewer.setLastGUI(this);
    }

    public void update() {
        update(false);
    }

    protected void update(boolean open) {
        SpigotServer.getInstance().runAsync(() -> {
            beforeUpdateAsync();

            ItemStack[] queuedUpdate = queuedUpdate();

            /* Update inventory Sync */
            SpigotServer.getInstance().runSync(() -> {
                inventory.setContents(queuedUpdate);

                if (open)
                    viewer.openInventory(inventory);
            });

            afterUpdateAsync();
        });
    }

    protected ItemStack[] queuedUpdate() {
        ItemStack[] queuedUpdate = new ItemStack[inventory.getSize()];

        /* Generate inventory Async */
        for (int slot = 0; slot < this.items.length; slot++) {
            Item<P, ?> item = this.items[slot];

            if (item == null)
                continue;

            queuedUpdate[slot] = item.build();
        }

        return queuedUpdate;
    }

    protected ItemStack queuedUpdate(int slot) {
        Item<P, ?> item = this.items[slot];

        if (item == null)
            return null;

        return item.build();
    }

    public void beforeUpdateAsync() {

    }

    public void afterUpdateAsync() {

    }

    public void set(int row, int slot, Item<P, ?> item) {
        set(row * 9 + slot, item);
    }

    public void set(int slot, Item<P, ?> item) {
        this.items[slot] = item;
    }

    public void clear(int row, int slot) {
        clear(row * 9 + slot);
    }

    public void clear(int slot) {
        this.items[slot] = null;

        inventory.setItem(slot, null);
    }

    public void clear() {
        Arrays.fill(this.items, null);

        inventory.clear();
    }

    public void processClickEvent(InventoryClickEvent event) {
        InventoryView view = event.getView();
        Inventory clicked = event.getClickedInventory();

        if (clicked == null || !event.getClickedInventory().equals(this.inventory)) {
            Inventory topInventory = view.getTopInventory();

            if (!topInventory.equals(this.inventory)) {
                try {
                    if (view.getTitle().startsWith("§0§l")) {
                        /* TODO: Temp Fix */
                        event.setCancelled(true);
                        return;
                    }
                } catch (IllegalStateException ex) {}

                return;
            }

            /* Cancel items being put in the GUI */
            if (event.isShiftClick())
                event.setCancelled(true);

            return;
        }

        event.setCancelled(true);

        Item<P, ?> item = this.items[event.getSlot()];
        if (item == null || item.clickEvent == null)
            return;

        onClick(event, item);
    }

    protected void onClick(InventoryClickEvent event, Item<P, ?> item) {
        SpigotServer.getInstance().runAsync(() -> item.clickEvent.onClick(event));
    }

    public void close() {
        SpigotServer.getInstance().runSync(() -> viewer.closeInventory());
    }

    public void processDragEvent(InventoryDragEvent event) {
        Inventory clicked = event.getInventory();

        if (!clicked.equals(this.inventory))
            return;

        event.setCancelled(true);
    }

    protected void onDrag(InventoryDragEvent event) {

    }

    public static class Item<P extends SpigotPlayer, I extends MutableItemStack> {

        @Getter protected final I mutableItemStack;
        @Getter @Setter protected GUIClickEvent<P> clickEvent;

        public Item(I mutableItemStack) {
            this(mutableItemStack, null);
        }

        public Item(I mutableItemStack, GUIClickEvent<P> clickEvent) {
            this.mutableItemStack = mutableItemStack;
            this.clickEvent = clickEvent;
        }

        public ItemStack build() {
            return mutableItemStack.toItemStack();
        }

        public Item<P, I> click(GUIClickEvent<P> clickEvent) {
            this.clickEvent = clickEvent;

            return this;
        }
    }

    @FunctionalInterface
    public interface GUIClickEvent<P> {

        void onClick(InventoryClickEvent event);

    }
}
