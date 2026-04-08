package com.orbitmines.archive.minecraft.spigot._2019.servers.hub.player;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.libs.database.models.TimePlayed;
import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.settings.PlayerVisibility;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.Hub;
import com.orbitmines.archive.minecraft.spigot._2019.servers.hub.scoreboard.HubScoreboard;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HubPlayer extends OMPlayer<Hub, HubPlayer> implements PlayerVisibility<Hub, HubPlayer> {

    public HubPlayer(Player player, Hub server) {
        super(player, server);

        setGameMode(GameMode.ADVENTURE);
    }

    @Override
    public HubPlayer getInstance() {
        return this;
    }

    @Override
    public boolean onJoin() {
        super.onJoin();

        getInventory().setHeldItemSlot(4);

        updateVisibility();
        new BukkitRunnable() { //2FA LOGIN
            @Override
            public void run() {
                if (isFirstLogin())
                    sendMessage("Server", Color.BLUE, "hub", "player.welcome", "§8§lOrbit§7§lMines§7", getName(Name.RAW_COLORED) + "§7", "§3§lEnder Pearl§7");
                else
                    sendMessage("Server", Color.BLUE, "hub", "player.welcome_back", getName(Name.RAW_COLORED) + "§7", "§3§lEnder Pearl§7");
            }
        }.runTaskLaterAsynchronously(server.getPlugin(), 20);

        server.runSync(() -> {
            if (isFirstLogin())
                server.getNewPlayerKit().copyToInventory(this);
            else
                server.getKit().copyToInventory(this);

            resetScoreboard();
            setScoreboard(new HubScoreboard(server, this));

            /* Custom lobby teleport — after all default setup */
            if (getLobbyPreferenceMap() != null)
                server.teleportToPlayerLobby(this, null);
        });

        return true;
    }

    @Override
    public void onFirstLogin() {
        TimePlayed played = getTimePlayed(Server.HUB, false);

        if (played != null && played.getSeconds() != 0)
            return;

        /* Rotate 90 degrees so new players see survival & kitpvp */
        Location l = getLocation().clone();
        l.setYaw(l.getYaw() + 90F);

        server.runSync(() -> teleport(l));
    }
}
