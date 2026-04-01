package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.events;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldAdvancementsFix implements Listener {

    private final SpigotServer server;

    public WorldAdvancementsFix(SpigotServer server) {
        this.server = server;

        for (World world : Bukkit.getWorlds()) {
            hideAdvancementsFor(world);
        }
    }

    private void hideAdvancementsFor(World world) {
        world.setGameRule(GameRule.SHOW_ADVANCEMENT_MESSAGES, false);
        Bukkit.getLogger().info("Achievements are now hidden for world '" + world.getName() + "'.");
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        hideAdvancementsFor(event.getWorld());
    }
}
