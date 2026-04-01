package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.item_handlers;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.PlayerUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.item_handlers.ItemHoverActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class ItemHoverPlayerTracker extends ItemHoverActionBar<KitPvPPlayer> {

    public ItemHoverPlayerTracker(SpigotServer<KitPvPPlayer> server) {
        super(server, KitPvP.PLAYER_TRACKER, false, (player, item) -> {
            Player closest = PlayerUtils.getClosestPlayer(
                player.getLocation(),
                p -> !p.equals(player.bukkit()) && p.getGameMode() == GameMode.SURVIVAL && !p.hasPotionEffect(PotionEffectType.INVISIBILITY)
            );

            if (closest == null)
                return "§c§lNo players nearby.";

            server.runSync(() -> player.setCompassTarget(closest.getLocation()));

            KitPvPPlayer closestPlayer = server.getPlayer(closest);

            return
                "§c§lTracking: " + closestPlayer.getRank().getPrefixColor().getCc() + "§l" + closestPlayer.getRawName() +
                "       §c§l" + player.translate("kitpvp", "player.word.distance") + ": " + closestPlayer.getRank().getPrefixColor().getCc() + "§l" + String.format("%.1f", player.getLocation().distance(closest.getLocation()));
        });
    }

    @Override
    public void onEnter(KitPvPPlayer player, ItemStack item, int slot) {
        super.onEnter(player, item, slot);
        player.playSound(Sound.UI_BUTTON_CLICK);
    }
}
