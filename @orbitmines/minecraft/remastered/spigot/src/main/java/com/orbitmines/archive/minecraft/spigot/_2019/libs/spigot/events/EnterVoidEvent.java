package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public abstract class EnterVoidEvent<S extends OMServer<S, P>, P extends OMPlayer<S, P>> implements Listener {

    private final S server;
    private final World world;

    public EnterVoidEvent(S server, World world) {
        this.server = server;
        this.world = world;
    }

    public abstract Location getRespawnLocation(P player);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        P player = server.getPlayer((Player) event.getEntity());

        if (event.getCause() != EntityDamageEvent.DamageCause.VOID || !event.getEntity().getWorld().getName().equals(world.getName()))
            return;

        event.setDamage(player.getHealth());

        Location respawnLocation = getRespawnLocation(player);

        if (respawnLocation == null)
            return;

        event.getEntity().setFallDistance(0F);
        event.getEntity().teleport(respawnLocation);
    }
}
