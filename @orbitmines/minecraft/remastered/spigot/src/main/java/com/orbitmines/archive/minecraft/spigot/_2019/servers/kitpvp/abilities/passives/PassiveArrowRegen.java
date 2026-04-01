package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_builders.KitItemBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.event.Event;

public class PassiveArrowRegen implements Passive.Handler<Event> {

    /* Every Level means: x seconds to gain 1 arrow */

    @Override
    public void trigger(KitEvent passiveEvent, Event event, int level) {
        if ((System.currentTimeMillis() / 1000) % level != 0)
            return;

        KitPvPPlayer player = passiveEvent.getPlayer();

        if (player.getSelectedKit() == null)
            return;

        /* Only allow up to 64 arrows in the inventory */
        if (InventoryUtils.getAmount(player.getInventory(), Material.ARROW) >= 64)
            return;

        player.getInventory().addItem(new KitItemBuilder(player.getSelectedKit(), Material.ARROW).build());
    }
}
