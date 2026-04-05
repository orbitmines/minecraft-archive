package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PassiveHealOnKill implements Passive.Handler<PlayerDeathEvent> {

    @Override
    public void trigger(KitEvent<PlayerDeathEvent> passiveEvent, PlayerDeathEvent event, int level) {
        Player killer = passiveEvent.getPlayer().bukkit();

        double healAmount = getHealAmount(level);
        double newHealth = Math.min(killer.getHealth() + healAmount, killer.getMaxHealth());
        killer.setHealth(newHealth);
    }

    public double getHealAmount(int level) {
        switch (level) {
            case 1: return 10.0D;
            case 2: return 12.0D;
            case 3: return 14.0D;
            default: throw new ArrayIndexOutOfBoundsException();
        }
    }
}
