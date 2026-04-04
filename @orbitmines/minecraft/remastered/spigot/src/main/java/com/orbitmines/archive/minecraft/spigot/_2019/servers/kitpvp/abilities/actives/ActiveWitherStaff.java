package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.concurrent.ThreadLocalRandom;

public class ActiveWitherStaff implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(5 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();

        /* Check for soul */
        int soulSlot = findSoulSlot(player);
        if (soulSlot == -1)
            return;

        /* Consume soul */
        ItemStack soul = player.getInventory().getItem(soulSlot);
        if (soul.getAmount() > 1)
            soul.setAmount(soul.getAmount() - 1);
        else
            player.getInventory().setItem(soulSlot, null);

        /* Shoot wither skulls */
        Location eyeLoc = player.getEyeLocation();
        Vector direction = eyeLoc.getDirection().multiply(1.5);

        launchSkull(player, eyeLoc, direction);

        /* Spray skulls */
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < 2; i++) {
            Vector spray = direction.clone().add(new Vector(
                r.nextDouble(-0.5, 0.5),
                r.nextDouble(-0.3, 0.3),
                r.nextDouble(-0.5, 0.5)
            ));
            launchSkull(player, eyeLoc, spray);
        }

        omp.playSound(Sound.ENTITY_WITHER_SHOOT);
    }

    private void launchSkull(Player shooter, Location loc, Vector velocity) {
        WitherSkull skull = shooter.launchProjectile(WitherSkull.class);
        skull.setVelocity(velocity);
        skull.setCharged(false);
    }

    private int findSoulSlot(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item == null || item.getType() != Material.REDSTONE)
                continue;

            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                if (meta.getDisplayName().contains("Soul"))
                    return i;
            }
        }
        return -1;
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
