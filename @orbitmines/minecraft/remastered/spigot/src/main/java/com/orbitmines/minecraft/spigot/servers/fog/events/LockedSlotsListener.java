package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

/**
 * Keeps the lobby-kit items pinned to their fixed slots (the player can't move,
 * swap, or drop them) AND treats a plain click on one of those slots as a
 * trigger — firing the same action that right-clicking the item in hand would.
 *
 * <p>Needed because slots 16 / 17 (top-right of the main inventory) can't be
 * right-clicked in the world without first moving the item to the hotbar, which
 * we also forbid. So the only sensible UX is to dispatch from the inventory
 * click itself.</p>
 */
public class LockedSlotsListener implements Listener {

    private final FoG server;

    public LockedSlotsListener(FoG server) {
        this.server = server;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof org.bukkit.entity.Player bp)) return;
        /* Only police the player's own inventory, not other inventories. */
        Inventory top = event.getView().getTopInventory();
        boolean clickingPlayerInv = event.getClickedInventory() == bp.getInventory();
        boolean shiftingIntoPlayerInv = event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY
                && event.getClickedInventory() == top;
        if (!clickingPlayerInv && !shiftingIntoPlayerInv) return;

        int slot = event.getSlot();
        if (!server.getLobbyKit().getLockedSlots().contains(slot)) return;
        /* Always cancel so the item can't be picked up / swapped out. */
        event.setCancelled(true);

        /* Only dispatch the action when the player's own inventory is clicked
           directly — shift-click-from-top-inventory shouldn't open another GUI. */
        if (!clickingPlayerInv) return;

        FoGPlayer player = server.getPlayer(bp);
        if (player == null) return;
        /* Close the inventory first on the next tick if the action opens another GUI.
           The kit's own fireSlotAction handles no-ops when a hologram selector is up. */
        int finalSlot = slot;
        server.runSync(() -> {
            bp.closeInventory();
            server.getLobbyKit().fireSlotAction(finalSlot, player);
        });
    }

    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof org.bukkit.entity.Player bp)) return;
        for (int raw : event.getRawSlots()) {
            int slot = event.getView().convertSlot(raw);
            if (server.getLobbyKit().getLockedSlots().contains(slot)) {
                event.setCancelled(true);
                return;
            }
        }
    }
}
