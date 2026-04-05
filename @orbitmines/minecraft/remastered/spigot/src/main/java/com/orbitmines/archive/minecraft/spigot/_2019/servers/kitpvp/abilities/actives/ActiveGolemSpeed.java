package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives.PassiveIronGolemSummon;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import org.bukkit.Sound;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ActiveGolemSpeed implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(20 * 1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        IronGolem golem = PassiveIronGolemSummon.getGolem(player.getUniqueId());

        if (golem == null) {
            new ActionBar(player, () -> "§c§lNo Iron Golem summoned!", 40).send();
            return;
        }

        golem.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4 * 20, 1));
        omp.playSound(Sound.ENTITY_IRON_GOLEM_REPAIR);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
