package com.orbitmines.minecraft.spigot.servers.fog.raid;

import com.orbitmines.minecraft.spigot.servers.fog.mob.Bee;
import com.orbitmines.minecraft.spigot.servers.fog.mob.Bull;
import com.orbitmines.minecraft.spigot.servers.fog.mob.FireMage;
import com.orbitmines.minecraft.spigot.servers.fog.mob.FoGMob;
import com.orbitmines.minecraft.spigot.servers.fog.mob.FoGZombie;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import com.orbitmines.minecraft.spigot.servers.fog.util.Fogi18n;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A time-boxed wave of mobs scaled by average online player level.
 * Progress bar is the ratio of (mobs remaining / mobs spawned).
 */
public class Raid {

    private static final Random RANDOM = new Random();

    @Getter private final Run run;
    @Getter private final int level;
    @Getter private final List<FoGMob> mobs = new ArrayList<>();
    @Getter private final BossBar bossBar;
    private final int initialCount;

    public Raid(Run run, int level) {
        this.run = run;
        this.level = level;
        this.bossBar = Bukkit.createBossBar(Fogi18n.defaultText("raid.title", level), BarColor.RED, BarStyle.SEGMENTED_10);
        int base = 6 + level;
        this.initialCount = base;
        this.bossBar.setProgress(1.0);
    }

    public void start(Location centre) {
        for (Player p : centre.getWorld().getPlayers()) bossBar.addPlayer(p);
        for (int i = 0; i < initialCount; i++) {
            Location at = centre.clone().add(RANDOM.nextInt(30) - 15, 0, RANDOM.nextInt(30) - 15);
            FoGMob mob = pickMob();
            mob.spawn(at);
            mobs.add(mob);
        }
    }

    private FoGMob pickMob() {
        int roll = RANDOM.nextInt(100);
        if (roll < 50) return new FoGZombie(run);
        if (roll < 75) return new FireMage(run);
        if (roll < 90) return new Bee(run);
        return new Bull(run);
    }

    public void tick() {
        int alive = 0;
        for (FoGMob m : mobs) {
            if (m.isAlive()) { alive++; m.tick(); }
        }
        bossBar.setProgress((double) alive / (double) Math.max(1, initialCount));
        if (alive == 0) {
            bossBar.removeAll();
        }
    }

    public boolean isOver() {
        for (FoGMob m : mobs) if (m.isAlive()) return false;
        return true;
    }

    public void cleanup() {
        for (FoGMob m : mobs) m.remove();
        bossBar.removeAll();
    }
}
