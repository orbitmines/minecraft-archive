package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ItemUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.nms.itemstack.ItemStackNms;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class InteractEvent implements Listener {

    private final KitPvP server;

    public InteractEvent(KitPvP server) {
        this.server = server;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        KitPvPPlayer omp = server.getPlayer(event.getPlayer());

        if (omp.isSpectator()) {
            event.setCancelled(true);
            return;
        }

        if (omp.getSelectedKit() != null) {
            if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
                return;

            /* In arena */
            ItemStack item = event.getItem();

            if (item == null)
                return;

            int slot = omp.getInventory().first(item);

            ItemStackNms nms = server.getNms().customItem();
            Map<Active, Integer> actives = Active.from(nms, item);

            /* No Actives on this item */
            if (actives == null)
                return;

            /* Active found, so we cancel the event */
            event.setCancelled(true);

            for (Active active : actives.keySet()) {
                Active.Handler handler = active.getHandler();
                int level = actives.get(active);

                if (active.canUse(nms, item, level)) {
                    /* Reset Cooldown */
                    item = active.resetCooldown(nms, item, level);
                    omp.getInventory().setItem(slot, item);

                    /* Trigger active */
                    String name = active.getColor().getCc() + "§l" + active.getName();
                    omp.sendRawMessage("Active", Color.BLUE, "You have used " + name + "§7.");
                    handler.trigger(event, omp, level);
                }
            }

            return;
        }

        /* In lobby */
        Block block = event.getClickedBlock();
        if (block == null || !ItemUtils.isSign(block.getType()))
            return;

        //TODO:
//        KitPvPMap map = KitPvPMap.getMap(block.getLocation());
//
//        if (map == null)
//            return;
//
//        KitPvPMap previous = KitPvPMap.getVotedFor(omp.getUUID());
//        if (previous != null)
//            previous.getVotes().remove(omp.getUUID());
//
//        map.getVotes().add(omp.getUUID());
//
//        omp.playSound(Sound.ENTITY_ARROW_HIT_PLAYER);
    }
}
