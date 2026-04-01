package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.KitPvP;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Map;

public class ProjectileEvents implements Listener {

    private final KitPvP server;

    public ProjectileEvents(KitPvP server) {
        this.server = server;
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent event) {
        ItemStack bow = event.getBow();

        Map<Passive, Integer> passives = Passive.from(server.getNms().customItem(), bow, Passive.Interaction.APPLY_TO_ARROW);

        /* No Passives on this item */
        if (passives == null)
            return;

        Entity arrow = event.getProjectile();

        /* Apply to metadata so we can apply passive effects when the arrow hits. */
        arrow.setMetadata("passive", new FixedMetadataValue(server, passives));
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();

        /* Not an arrow, move on. */
        if (!(projectile instanceof Arrow))
            return;

        /* No passives, move on. */
        if (!projectile.hasMetadata("passive")) {
            /* Clear arrow so it doesn't just sit on the ground. */
            projectile.remove();
            return;
        }

        Map<Passive, Integer> passives = (Map<Passive, Integer>) projectile.getMetadata("passive").get(0).value();

        for (Passive passive : passives.keySet()) {
            Player shooter = null;
            if (projectile.getShooter() instanceof Player)
                shooter = (Player) projectile.getShooter();

            passive.getHandler().trigger(new KitEvent<>(server.getPlayer(shooter), event), event, passives.get(passive));
        }

        /* Clear arrow so it doesn't just sit on the ground. */
        projectile.remove();
    }
}
