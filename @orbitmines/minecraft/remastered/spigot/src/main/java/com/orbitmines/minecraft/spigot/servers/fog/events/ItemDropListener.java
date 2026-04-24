package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Blocks FoG lobby-kit items from being dropped. Those items are stamped with
 * {@code interactive_kit:item_id} by {@link
 * com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.kits.interactive.InteractiveKit.Interaction#applyId}.
 */
public class ItemDropListener implements Listener {

    private final FoG server;

    public ItemDropListener(FoG server) {
        this.server = server;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack stack = event.getItemDrop().getItemStack();
        Long id = server.getNms().customItem().getMetaDataLong(stack, "interactive_kit", "item_id");
        if (id != null && id >= 0) {
            event.setCancelled(true);
        }
    }
}
