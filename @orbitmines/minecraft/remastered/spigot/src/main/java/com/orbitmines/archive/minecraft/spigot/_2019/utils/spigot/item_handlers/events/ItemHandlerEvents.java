package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.events;

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemHover;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemInteraction;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

/*
* OrbitMines - @author Fadi Shawki - 29-7-2017
*/
public class ItemHandlerEvents<P extends SpigotPlayer> implements Listener {

    private final SpigotServer<P> server;

    public ItemHandlerEvents(SpigotServer<P> server) {
        this.server = server;
    }

    /*
        ItemInteraction
     */

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event) {
        P player = server.getPlayer(event.getPlayer());

        ItemStack item = event.getItem();
        if (item == null)
            return;

        for (ItemInteraction<P> interaction : ItemInteraction.getItemInteractions()) {
            if (!interaction.equals(item))
                continue;

            event.setCancelled(true);

            interaction.onInteract(player, event, item);

            switch (event.getAction()) {

                case LEFT_CLICK_AIR:
                case LEFT_CLICK_BLOCK:
                    interaction.onLeftClick(player, event, item);
                    break;
                case RIGHT_CLICK_AIR:
                case RIGHT_CLICK_BLOCK:
                    interaction.onRightClick(player, event, item);
                    break;
            }
            break;
        }
    }

    /*
        ItemHover
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onHover(PlayerItemHeldEvent event) {
        P player = server.getPlayer(event.getPlayer());

        /* Cancel previous hover */
        ItemStack previous = player.getInventory().getItem(event.getPreviousSlot());
        if (previous != null) {
            for (ItemHover<P> hover : ItemHover.getItemHovers()) {
                if (!hover.equals(previous))
                    continue;

                hover.leave(player);
                break;
            }
        }

        /* Start next hover */
        ItemStack next = player.getInventory().getItem(event.getNewSlot());
        if (next != null) {
            for (ItemHover<P> hover : ItemHover.getItemHovers()) {
                if (!hover.equals(next))
                    continue;

                hover.enter(player, next, event.getNewSlot());
                break;
            }
        }
    }
}
