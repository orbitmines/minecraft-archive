package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class DamageEvent implements Listener {

    private final KitPvP server;

    public DamageEvent(KitPvP server) {
        this.server = server;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player entity = (Player) event.getEntity();
            KitPvPPlayer player = server.getPlayer(entity);

            if (player.getSelectedKit() == null || player.isSpectator()) {
                event.setCancelled(true);
                return;
            }

            Map<Passive, Integer> totalPassive = new HashMap<>();

            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (item == null)
                    continue;

                Map<Passive, Integer> passives = Passive.from(server.getNms().customItem(), item, Passive.Interaction.ON_HIT);

                /* No Passives on this item */
                if (passives == null)
                    continue;

                for (Passive passive : passives.keySet()) {
                    int level = passives.get(passive);

                    if (!totalPassive.containsKey(passive)) {
                        totalPassive.put(passive, level);
                    } else {
                        int totalLevel = totalPassive.get(passive);

                        if (passive.isStackable())
                            totalPassive.put(passive, totalLevel + level);
                        else if (level > totalLevel)
                            totalPassive.put(passive, level);
                    }
                }
            }

            for (Passive passive : totalPassive.keySet()) {
                passive.getHandler().trigger(new KitEvent<>(player, event), event, totalPassive.get(passive));
            }
        }
    }
}
