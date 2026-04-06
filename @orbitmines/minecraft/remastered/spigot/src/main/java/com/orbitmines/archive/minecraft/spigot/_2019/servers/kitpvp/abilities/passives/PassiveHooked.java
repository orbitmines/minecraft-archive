package com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.passives;

import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.abilities.Passive;
import com.orbitmines.archive.minecraft.spigot._2019.servers.kitpvp.events.KitEvent;
import org.bukkit.event.Event;

public class PassiveHooked implements Passive.Handler<Event> {

    @Override
    public void trigger(KitEvent<Event> passiveEvent, Event event, int level) {
        /* Mechanic handled by FishermanRodEvent */
    }
}
