package com.orbitmines.minecraft.spigot.servers.fog.raid;

import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/** Tracks active raids per run. */
public class RaidManager {

    private final Run run;
    private final List<Raid> active = new ArrayList<>();

    public RaidManager(Run run) {
        this.run = run;
    }

    /** Compute raid level from online players in this run. Lower-level players contribute less. */
    public int computeLevel(List<Player> onlineMembers, int[] levels) {
        int total = 0;
        for (int l : levels) total += Math.max(1, l);
        int n = Math.max(1, onlineMembers.size());
        return Math.max(1, total / n);
    }

    public Raid startRaid(Location at, int level) {
        Raid r = new Raid(run, level);
        r.start(at);
        active.add(r);
        return r;
    }

    public void tick() {
        active.removeIf(r -> {
            r.tick();
            if (r.isOver()) { r.cleanup(); return true; }
            return false;
        });
    }

    public List<Raid> getActive() { return active; }
}
