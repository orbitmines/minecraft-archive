package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.database.models.PlayerModel;
import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.arguments.PlayerModelArgument;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor1;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.ItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.item.mutable.MutableItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.guis.GUI;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CommandInvSee<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends Command<S, P> {

    public CommandInvSee(S plugin) {
        super(plugin, "invsee");

        withArg(
            new PlayerModelArgument<S, P>(true).executes((Executor1<S, P,
                PlayerModel, PlayerModelArgument<S, P>>
            ) (player, model) -> {
                Player target;
                boolean wasOnline;

                if (plugin.getPlayer(model.getUUID()) != null) {
                    target = plugin.getPlayer(model.getUUID()).bukkit();
                    wasOnline = true;
                } else {
                    target = PlayerUtils.loadOfflinePlayer(model.getUUID());

                    if (target == null) {
                        player.sendMessage("InvSee", Color.RED, "survival", "player.claim.gui.cannot_find_player");
                        return;
                    }

                    wasOnline = false;
                }

                plugin.runSync(() -> new InventoryViewer(player, model, target, wasOnline).open());

            })
        );

        requires(StaffRank.ADMIN);
    }

    @Override
    public String getDescription(P player) {
        return player.translate("spigot", "player.command.invsee.description");
    }

    public class InventoryViewer extends GUI<P> {

        private final HashSet<Integer> emptySlots = new HashSet<>(Arrays.asList(40, 42, 43, 44));
        private final int helmetSlot = 36;
        private final int chestplateSlot = 37;
        private final int leggingsSlot = 38;
        private final int bootsSlot = 39;
        private final int offHandSlot = 41;

        private final P player;
        private final PlayerModel targetModel;
        private final Player target;
        private final boolean wasOnline;

        private SpigotRunnable tracker;

        public InventoryViewer(P player, PlayerModel targetModel, Player target, boolean wasOnline) {
            super(45, "§0§l" + targetModel.getRawName() + "'s Inventory", player);

            this.player = player;
            this.targetModel = targetModel;
            this.target = target;
            this.wasOnline = wasOnline;

            PlayerInventory playerInventory = target.getInventory();

            /* Top of Player Inventory */
            for (int i = 0; i < 27; i++) {
                int slot = i + 9;
                set(i, new Item<>(() -> playerInventory.getItem(slot)));
            }

            /* Hot bar */
            for(int i = 27; i < 36; i++){
                int slot = i - 27;
                set(i, new Item<>(() -> playerInventory.getItem(slot)));
            }

            set(helmetSlot, new Item<>(playerInventory::getHelmet));
            set(chestplateSlot, new Item<>(playerInventory::getChestplate));
            set(leggingsSlot, new Item<>(playerInventory::getLeggings));
            set(bootsSlot, new Item<>(playerInventory::getBoots));
            set(offHandSlot, new Item<>(playerInventory::getItemInOffHand));

            for (int i : emptySlots) {
                set(i, new Item<P, MutableItemBuilder>(() -> new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1, "§7 "), event -> event.setCancelled(true)));
            }
        }

        @Override
        public void processClickEvent(InventoryClickEvent event) {
            InventoryView view = event.getView();

            if (!view.getTopInventory().equals(this.inventory))
                return;

            InventoryAction action = event.getAction();

//            if (action == InventoryAction.COLLECT_TO_CURSOR)
//                return;

            int slot = event.getSlot();

            if (this.emptySlots.contains(slot)) {
                event.setCancelled(true);
                return;
            }

            event.setCancelled(false);

            if (this.inventory.equals(event.getClickedInventory())) {
                updateSlot(slot);
            } else if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
                Set<Integer> slots = new HashSet<>();
                for (int i = 0; i < inventory.getSize(); i++) {
                    slots.add(i);
                }

                updateSlots(slots);
            }
        }

        @Override
        public void processDragEvent(InventoryDragEvent event) {
            InventoryView view = event.getView();

            if (!view.getTopInventory().equals(this.inventory))
                return;

            Set<Integer> slots = event.getInventorySlots();

            for (int emptySlot : emptySlots) {
                if (!slots.contains(emptySlot))
                    continue;

                event.setCancelled(true);
                return;
            }

            event.setCancelled(false);
            updateSlots(slots);
        }

        @Override
        protected void update(boolean open) {
            ItemStack[] queuedUpdate = queuedUpdate();
            inventory.setContents(queuedUpdate);

            if (open) {
                player.openInventory(this.inventory);

                if (this.tracker == null || !this.tracker.isRunning())
                    startTracker();
            }

            player.updateInventory();
        }

        private void updateSlot(int slot) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    processSlotUpdate(slot);
                    onSlotUpdate(slot);
                }
            }.runTaskLater(viewer.server().getPlugin(), 0);
        }

        private void updateSlots(Collection<Integer> slots) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    for (int slot : slots) {
                        processSlotUpdate(slot);
                        onSlotUpdate(slot);
                    }
                }
            }.runTaskLater(viewer.server().getPlugin(), 0);
        }

        private void processSlotUpdate(int slot) {
            PlayerInventory playerInventory = target.getInventory();

            if (slot < 27) {
                /* Inventory */
                playerInventory.setItem(slot + 9, getItem(slot));
            } else if (slot < 36) {
                /* Hot bar */
                playerInventory.setItem(slot - 27, getItem(slot));
            } else if (slot == helmetSlot) {
                playerInventory.setHelmet(getItem(slot));
            } else if (slot == chestplateSlot) {
                playerInventory.setChestplate(getItem(slot));
            } else if (slot == leggingsSlot) {
                playerInventory.setLeggings(getItem(slot));
            } else if (slot == bootsSlot) {
                playerInventory.setBoots(getItem(slot));
            } else if (slot == offHandSlot) {
                playerInventory.setItemInOffHand(getItem(slot));
            }
        }

        private void onSlotUpdate(int slot) {
            inventory.setItem(slot, queuedUpdate(slot));

            player.updateInventory();

            if (target.isOnline())
                target.updateInventory();
        }

        private ItemStack getItem(int slot) {
            return this.inventory.getItem(slot);
        }

        private void startTracker() {
            tracker = new SpigotRunnable<S>(player.server(), Interval.of(TimeUnit.TICK, 1)) {
                @Override
                public void run() {
                    if (!player.bukkit().isOnline() || !inventory.equals(player.getOpenInventory().getTopInventory())) {
                        cancel();

                        if (player.bukkit().isOnline())
                            player.closeInventory();

                        return;
                    }

                    if (wasOnline) {
                        /* Player is Online, when he's offline, close inventory */
                        if (target.isOnline()) {
                            update();
                            return;
                        }

                        cancel();
                        player.closeInventory();
                    } else {
                        /* Player is offline, when he's online, close inventory */
                        P player2 = CommandInvSee.this.getPlugin().getPlayer(target.getUniqueId());

                        if (player2 == null) {
                            target.saveData();
                            return;
                        }

                        cancel();
                        player.closeInventory();
                    }
                }
            }.start();
        }
    }
}
