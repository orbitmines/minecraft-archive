package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RegainHealthEvent implements Listener {

    private final KitPvP server;

    public RegainHealthEvent(KitPvP server) {
        this.server = server;
    }

    @EventHandler
    public void onRegain(EntityRegainHealthEvent event) {
        if (!(event.getEntity() instanceof Player) || event.getRegainReason() != EntityRegainHealthEvent.RegainReason.SATIATED || event.getRegainReason() != EntityRegainHealthEvent.RegainReason.REGEN)
            return;

        KitPvPPlayer player = server.getPlayer((Player) event.getEntity());

        if (player.getSelectedKit() == null)
            return;

        /* Increase health regen */
        double amount = event.getAmount() * 1.75;
        amount *= player.getSelectedKit().getHealthRegen().getMultiplier();

        event.setAmount(amount);

        //TODO RESDET DAMAGE
    }
}
