package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.mojang.authlib.GameProfile;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import net.minecraft.server.v1_14_R1.DimensionManager;
import net.minecraft.server.v1_14_R1.EntityPlayer;
import net.minecraft.server.v1_14_R1.MinecraftServer;
import net.minecraft.server.v1_14_R1.PlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import java.util.*;

public class PlayerUtils {

    private static JavaPlugin plugin;

    static {
        plugin = SpigotServer.getInstance();
    }

    public static void updateInventory(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                player.updateInventory();
            }
        }.runTaskLater(plugin, 1);
    }

    private static Map<UUID, OfflinePlayer> cachedOfflinePlayers = new HashMap<>();
    public static OfflinePlayer getOfflinePlayer(UUID uuid) {
        if (cachedOfflinePlayers.containsKey(uuid))
            return cachedOfflinePlayers.get(uuid);

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        cachedOfflinePlayers.put(uuid, offlinePlayer);

        return offlinePlayer;
    }

    public static Player loadOfflinePlayer(UUID uuid) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);

        if (!offlinePlayer.hasPlayedBefore())
            return null;

        MinecraftServer minecraftserver = MinecraftServer.getServer();
        GameProfile gameprofile = new GameProfile(offlinePlayer.getUniqueId(), offlinePlayer.getName());
        EntityPlayer entity = new EntityPlayer(minecraftserver, minecraftserver.getWorldServer(DimensionManager.OVERWORLD), gameprofile, new PlayerInteractManager(minecraftserver.getWorldServer(DimensionManager.OVERWORLD)));

        Player player = entity.getBukkitEntity();

        if (player == null)
            return null;

        player.loadData();

        return player;
    }

    public static Block getTargetBlock(LivingEntity livingEntity, int range) {
        BlockIterator blockIterator = new BlockIterator(livingEntity, range);
        Block lastBlock = blockIterator.next();
        while (blockIterator.hasNext()) {
            lastBlock = blockIterator.next();
            if (lastBlock.getType() == Material.AIR)
                continue;

            break;
        }
        return lastBlock;
    }

    public static boolean isInsideBlock(Player player, Block block) {
        Location blockHitBoxA = block.getLocation();
        Location blockHitBoxB = block.getLocation().add(1, 1, 1);

        return isInside(player, blockHitBoxA, blockHitBoxB);
    }
    public static boolean isInside(Player player, Location hitBoxA, Location hitBoxB) {
        Location playerHitBoxA = player.getLocation().subtract(0.3, 0, 0.3);
        Location playerHitBoxB = player.getLocation().add(0.3, 1.8, 0.3);

        return playerHitBoxA.getX() < hitBoxB.getX() && playerHitBoxB.getX() > hitBoxA.getX() && playerHitBoxA.getY() < hitBoxB.getY() && playerHitBoxB.getY() > hitBoxA.getY();
    }

    public static void removeOneHeldItem(Player player) {
        ItemStack item = player.getItemOnCursor();

        if (item.getAmount() != 1) {
            item.setAmount(item.getAmount() - 1);
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
        } else {
            player.getInventory().setItem(player.getInventory().getHeldItemSlot(), null);
        }
    }

    public static List<Player> getNearbyPlayers(Location loc, double range) {
        List<Player> list = new ArrayList<>();

        for (Player p : loc.getWorld().getPlayers()) {
            Location loc2 = p.getLocation();
            double distance = loc.distance(loc2);

            if (distance <= range)
                list.add(p);
        }

        return list;
    }

    public static Player getClosestPlayer(Location location) {
        return getClosestPlayer(location, null);
    }

    public static Player getClosestPlayer(Location location, OMServer.BroadcastExclusion<Player> exclusion) {
        Player closest = null;
        double distance = 0;

        for (Player player : location.getWorld().getPlayers()) {
            if (exclusion != null && !exclusion.includes(player))
                continue;

            double d = location.distance(player.getLocation());

            if (closest == null || d < distance) {
                closest = player;
                distance = d;
            }
        }

        return closest;
    }
}
