package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ActiveInvisibility implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(30 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();

        /* 6 seconds of invisibility (120 ticks) */
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 120, 0, true, false, true));

        omp.playSound(Sound.ENTITY_ILLUSIONER_MIRROR_MOVE);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
