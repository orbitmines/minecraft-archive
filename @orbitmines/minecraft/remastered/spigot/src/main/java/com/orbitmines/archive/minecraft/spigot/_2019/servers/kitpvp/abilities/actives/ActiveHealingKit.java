package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.actives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Active;
import com.orbitmines.archive.minecraft._2019.utils.cooldown.Cooldown;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ActiveHealingKit implements Active.Handler {

    private final Cooldown cooldown = new Cooldown(1000);

    @Override
    public void trigger(PlayerInteractEvent event, KitPvPPlayer omp, int level) {
        Player player = omp.bukkit();
        double maxHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
        player.setHealth(maxHealth);

        /* Consume the item */
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getAmount() > 1)
            item.setAmount(item.getAmount() - 1);
        else
            player.getInventory().setItemInMainHand(null);

        omp.playSound(Sound.ENTITY_PLAYER_LEVELUP);
    }

    @Override
    public Cooldown getCooldown(int level) {
        return cooldown;
    }
}
