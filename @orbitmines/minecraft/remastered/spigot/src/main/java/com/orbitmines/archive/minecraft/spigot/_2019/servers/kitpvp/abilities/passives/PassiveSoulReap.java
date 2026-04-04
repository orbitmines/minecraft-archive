package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PassiveSoulReap implements Passive.Handler<Event> {

    @Override
    public void trigger(KitEvent<Event> passiveEvent, Event event, int level) {
        ItemStack soul = new ItemStack(Material.REDSTONE, level);
        ItemMeta meta = soul.getItemMeta();
        meta.setDisplayName("§5Soul");
        soul.setItemMeta(meta);

        passiveEvent.getPlayer().getInventory().addItem(soul);
        passiveEvent.getPlayer().playSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    }
}
