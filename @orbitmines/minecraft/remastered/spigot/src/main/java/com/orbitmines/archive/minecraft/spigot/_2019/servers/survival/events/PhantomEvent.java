package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

import java.util.List;

/*
* OrbitMines - @author Fadi Shawki - 2017
*/
public class PhantomEvent implements Listener {

    private final double spawnDistance = Math.pow(40D, 2D);
    private final Survival survival;

    public PhantomEvent(Survival survival) {
        this.survival = survival;
    }

    @EventHandler
    public void onChange(PlayerBedEnterEvent event) {
        if (event.isCancelled())
            return;

        SurvivalPlayer player = survival.getPlayer(event.getPlayer());
        player.setLastBedEnter(DateUtils.now());
        player.update(SurvivalPlayerModel.column.LAST_BED_ENTER);

        new ActionBar(player.bukkit(), () -> "§7§l" + player.translate("survival", "player.disabled_phantom_spawning"), 100).send();
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.isCancelled() || !(event.getEntity() instanceof Phantom) || event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL)
            return;

        List<Player> players = PlayerUtils.getNearbyPlayers(event.getLocation(), spawnDistance);

        for (Player player : players) {
            SurvivalPlayer omp = survival.getPlayer(player);

            if (!omp.canSpawnPhantom())
                event.setCancelled(true);
        }
    }
}
