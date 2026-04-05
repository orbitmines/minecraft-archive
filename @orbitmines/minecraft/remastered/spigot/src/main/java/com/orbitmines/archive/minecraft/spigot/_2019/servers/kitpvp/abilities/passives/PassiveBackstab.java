package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.Location;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

public class PassiveBackstab implements Passive.Handler<EntityDamageByEntityEvent> {

    @Override
    public void trigger(KitEvent<EntityDamageByEntityEvent> passiveEvent, EntityDamageByEntityEvent event, int level) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player damager = passiveEvent.getPlayer().bukkit();
        Player victim = (Player) event.getEntity();

        /* Check if attacker is behind the victim */
        Vector victimDirection = victim.getLocation().getDirection().normalize();
        Vector toAttacker = damager.getLocation().toVector().subtract(victim.getLocation().toVector()).normalize();

        /* Dot product < 0 means attacker is behind (victim facing away) */
        if (victimDirection.dot(toAttacker) >= 0)
            return;

        /* Double the damage */
        event.setDamage(event.getDamage() * 2);

        /* Blood particle effect (redstone block break) — visible to all */
        Location victimLoc = victim.getLocation().add(0, 1, 0);
        for (int i = 0; i < 5; i++)
            victim.getWorld().playEffect(victimLoc, Effect.STEP_SOUND, Material.REDSTONE_BLOCK);

        /* Redstone block break sound — audible to all */
        victim.getWorld().playSound(victimLoc, Sound.BLOCK_STONE_BREAK, 1.5f, 0.5f);
    }
}
