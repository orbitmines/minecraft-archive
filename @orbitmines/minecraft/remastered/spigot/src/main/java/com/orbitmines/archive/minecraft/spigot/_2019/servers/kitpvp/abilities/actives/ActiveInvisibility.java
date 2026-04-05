package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ActiveInvisibility implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(30 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        KitPvP kitPvP = (KitPvP) KitPvP.getInstance();

        /* Save armor */
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack leggings = player.getInventory().getLeggings();
        ItemStack boots = player.getInventory().getBoots();

        /* Clear armor for true invisibility */
        player.getInventory().setHelmet(null);
        player.getInventory().setChestplate(null);
        player.getInventory().setLeggings(null);
        player.getInventory().setBoots(null);

        /* 6 seconds of invisibility (120 ticks) */
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 120, 0, true, false, true));

        omp.playSound(Sound.ENTITY_ILLUSIONER_MIRROR_MOVE);

        /* Restore armor after invisibility wears off */
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline())
                    return;

                /* Only restore if player is still alive (has a kit selected) */
                KitPvPPlayer omp2 = kitPvP.getPlayer(player);
                if (omp2.getSelectedKit() == null)
                    return;

                player.getInventory().setHelmet(helmet);
                player.getInventory().setChestplate(chestplate);
                player.getInventory().setLeggings(leggings);
                player.getInventory().setBoots(boots);

                omp.playSound(Sound.ENTITY_ILLUSIONER_MIRROR_MOVE);
            }
        }.runTaskLater(kitPvP.getPlugin(), 120L);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
