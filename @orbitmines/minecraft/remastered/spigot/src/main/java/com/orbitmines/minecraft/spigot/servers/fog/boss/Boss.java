package com.orbitmines.minecraft.spigot.servers.fog.boss;

import com.orbitmines.minecraft.spigot.servers.fog.mob.FoGMob;
import com.orbitmines.minecraft.spigot.servers.fog.run.Run;
import lombok.Getter;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

/** Extends FoGMob with a BossBar so the client sees progressive health. */
public abstract class Boss extends FoGMob {

    @Getter protected BossBar bossBar;

    protected Boss(Run run) {
        super(run);
    }

    protected void initBossBar(String title, BarColor color) {
        this.bossBar = org.bukkit.Bukkit.createBossBar(title, color, BarStyle.SOLID);
        this.bossBar.setProgress(1.0);
    }

    public void addViewer(Player player) {
        if (bossBar != null) bossBar.addPlayer(player);
    }

    public void removeViewer(Player player) {
        if (bossBar != null) bossBar.removePlayer(player);
    }

    public void updateBar() {
        if (bossBar == null || !isAlive()) return;
        bossBar.setProgress(Math.max(0.0, Math.min(1.0, entity.getHealth() / entity.getMaxHealth())));
    }

    public void destroy() {
        if (bossBar != null) bossBar.removeAll();
        super.remove();
    }
}
