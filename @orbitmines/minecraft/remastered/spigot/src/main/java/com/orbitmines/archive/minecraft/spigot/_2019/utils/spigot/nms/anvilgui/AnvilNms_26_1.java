package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.anvilgui;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class AnvilNms_26_1 implements AnvilNms {

    private static JavaPlugin plugin;

    static {
        plugin = SpigotServer.getInstance().getPlugin();
    }

    private class AnvilContainer extends AnvilMenu {
        public AnvilContainer(ServerPlayer player, int containerId) {
            super(containerId, player.getInventory(), ContainerLevelAccess.create(player.level(), player.blockPosition()));
        }

        @Override
        public boolean stillValid(net.minecraft.world.entity.player.Player player) {
            return true;
        }
    }

    private Player player;
    private int totalExp;

    private AnvilClickEventHandler handler;

    private HashMap<AnvilSlot, ItemStack> items = new HashMap<>();

    private Inventory inv;

    private Listener listener;

    public AnvilNms_26_1(Player player, AnvilClickEventHandler handler, AnvilCloseEvent closeEvent) {
        this.player = player;
        this.handler = handler;

        AnvilNms nms = this;

        this.listener = new Listener() {
            @EventHandler
            public void onInventoryClick(InventoryClickEvent event) {
                if (event.getView().getTopInventory().equals(inv))
                    event.setCancelled(true);

                if (event.getWhoClicked() instanceof Player) {
                    if (event.getInventory().equals(inv)) {
                        event.setCancelled(true);

                        ItemStack item = event.getCurrentItem();
                        int slot = event.getRawSlot();
                        String name = "";

                        if (item != null) {
                            if (item.hasItemMeta()) {
                                ItemMeta meta = item.getItemMeta();

                                if (meta.hasDisplayName()) {
                                    name = meta.getDisplayName();
                                }
                            }
                        }

                        AnvilClickEvent clickEvent = new AnvilClickEvent(nms, AnvilSlot.bySlot(slot), name);

                        SpigotServer.getInstance().runAsync(() -> handler.onAnvilClick(clickEvent));

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                AnvilNms_26_1.this.player.setLevel(totalExp);
                            }
                        }.runTaskLater(plugin, 1);
                    }
                }
            }

            @EventHandler
            public void onInventoryClose(InventoryCloseEvent event) {
                if (event.getPlayer() instanceof Player) {
                    Inventory inv = event.getInventory();

                    if (inv.equals(AnvilNms_26_1.this.inv)) {
                        inv.clear();
                        destroy();

                        if (closeEvent != null)
                            closeEvent.onClose();
                    }
                }
            }

            @EventHandler
            public void onPlayerQuit(PlayerQuitEvent event) {
                if (event.getPlayer().equals(getPlayer())) {
                    destroy();

                    if (closeEvent != null)
                        closeEvent.onClose();
                }
            }
        };

        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HashMap<AnvilSlot, ItemStack> getItems() {
        return items;
    }

    @Override
    public ItemStack getSlot(Player p, int slot) {
        return p.getOpenInventory().getTopInventory().getItem(slot);
    }

    @Override
    public void setSlot(AnvilSlot slot, ItemStack item) {
        items.put(slot, item);
        player.getOpenInventory().getTopInventory().setItem(slot.getSlot(), item);
    }

    @Override
    public void open() {
        this.totalExp = player.getLevel();

        ServerPlayer p = ((CraftPlayer) player).getHandle();

        int c = p.nextContainerCounter();

        AnvilContainer container = new AnvilContainer(p, c);
        container.cost.set(0);

        inv = container.getBukkitView().getTopInventory();

        for (AnvilSlot slot : items.keySet()) {
            inv.setItem(slot.getSlot(), items.get(slot));
        }

        p.connection.send(new ClientboundOpenScreenPacket(c, MenuType.ANVIL, Component.translatable("container.repair")));

        p.containerMenu = container;
        p.initMenu(container);
    }

    public void destroy() {
        SpigotServer.getInstance().runSync(() -> inv.clear());

        player = null;
        handler = null;
        items = null;

        HandlerList.unregisterAll(listener);

        listener = null;
    }
}
