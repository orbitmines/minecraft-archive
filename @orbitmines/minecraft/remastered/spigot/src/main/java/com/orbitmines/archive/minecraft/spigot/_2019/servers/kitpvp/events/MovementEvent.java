package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class MovementEvent implements Listener {

    private KitPvP server;

    public MovementEvent(KitPvP server) {
        this.server = server;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        KitPvPPlayer player = server.getPlayer(event.getPlayer());

        if (player.getSelectedKit() == null || player.isSpectator()) {
            return;
        }

        ItemStack item = player.getInventory().getBoots();

        if (item == null) {
            return;
        }

        Map<Passive, Integer> passives = Passive.from(server.getNms().customItem(), item, Passive.Interaction.MOVEMENT);

        if (passives == null) {
            return;
        }

        for (Passive passive : passives.keySet()) {
            passive.getHandler().trigger(new KitEvent<>(player, event), event, passives.get(passive));
        }
    }
}
