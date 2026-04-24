package com.orbitmines.minecraft.spigot.servers.fog.events;

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.events.CommandEvents;
import com.orbitmines.minecraft.spigot.servers.fog.FoG;
import com.orbitmines.minecraft.spigot.servers.fog.FoGPlayer;

public class FoGCommandEvents extends CommandEvents<FoG, FoGPlayer> {

    public FoGCommandEvents(FoG server) {
        super(server);
    }
}
