package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvPPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class DamageByEntityEvent implements Listener {

    private final KitPvP server;

    public DamageByEntityEvent(KitPvP server) {
        this.server = server;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            KitPvPPlayer playerDamager = server.getPlayer(damager);

            if (playerDamager.getSelectedKit() == null || playerDamager.isSpectator()) {
                event.setCancelled(true);
                return;
            }

            ItemStack item = damager.getInventory().getItemInMainHand();

            if (item == null)
                return;

            Map<Passive, Integer> passives = Passive.from(server.getNms().customItem(), item, Passive.Interaction.HIT_OTHER);

            /* No Passives on this item */
            if (passives == null)
                return;

            for (Passive passive : passives.keySet()) {
                passive.getHandler().trigger(new KitEvent<>(playerDamager, event), event, passives.get(passive));
            }
        } else if (event.getDamager() instanceof Firework) {
            event.setCancelled(true);
            /* Firework used by kits */
        } else if (event.getDamager() instanceof Arrow) {
            /* Head shot */
            Entity entity = event.getEntity();
            Arrow arrow = (Arrow) event.getDamager();

            if (entity.getLocation().subtract(arrow.getLocation()).getY() > 1.4) {
                event.setDamage(event.getDamage() * 1.5);
                arrow.getWorld().playEffect(arrow.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);

                if (arrow.getShooter() instanceof Player) {
                    KitPvPPlayer shooter = server.getPlayer((Player) arrow);
                    new ActionBar(shooter.bukkit(), () -> "§c§lHead Shot!", 60).send();
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void afterOnDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled() || !(event.getDamager() instanceof Player))
            return;

        Player damager = (Player) event.getDamager();
        KitPvPPlayer playerDamager = server.getPlayer(damager);
        playerDamager.addDamageDealt(event.getFinalDamage());
    }
}
