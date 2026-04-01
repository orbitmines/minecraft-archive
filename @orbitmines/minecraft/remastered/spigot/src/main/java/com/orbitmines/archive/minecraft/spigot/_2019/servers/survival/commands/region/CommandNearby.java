package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.commands.region;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.Command;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.commands.brigadier.executors.Executor0;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.region.Region;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.VectorUtils;
import org.bukkit.block.BlockFace;

public class CommandNearby extends Command<Survival, SurvivalPlayer> {

    public CommandNearby(Survival plugin) {
        super(plugin, Server.SURVIVAL, "near", "nearby", "nearbyregion", "nearbyrg", "nearrg");

        executes((Executor0<Survival, SurvivalPlayer>) (player) -> {
            if (!player.getWorld().equals(plugin.getWorld())) {
                player.setLastRegion(null);
                player.sendMessage("Region", Color.RED, "survival", "player.command.nearby.none");
                return;
            }

            Region region = Region.getNearest(player.getLocation());
            player.setLastRegion(region);

            int blocks = (int) VectorUtils.distance2D(player.getLocation().toVector(), region.getLocation().toVector());
            long regionId = region.getId();

            if (blocks <= Region.PROTECTION) {
                player.sendMessage("Region", Color.LIME, "survival", "player.command.nearby.at_region", "§aRegion " + regionId + "§7");
                return;
            }

            BlockFace face = BlockUtils.getFacing(player.getLocation(), region.getLocation());

            StringBuilder stringBuilder = new StringBuilder();
            String[] parts = face.toString().split("_");

            for (int i = 0; i < parts.length; i++) {
                if (i != 0)
                    stringBuilder.append(" ");

                stringBuilder.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
            }

            player.sendMessage("Region", Color.LIME, "survival", "player.command.nearby.closest", "§aRegion " + regionId + "§7", "§a" + NumberUtils.locale(blocks) + " Blocks§7", "§a" + stringBuilder.toString() + "§7");
        });
    }

    @Override
    public String getDescription(SurvivalPlayer player) {
        return player.translate("survival", "player.command.nearby.description");
    }
}
