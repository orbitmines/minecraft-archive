package com.orbitmines.archive.minecraft.spigot._2019.servers.survival.events;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalClaim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.Survival;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.database.models.Claim;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat.ActionBar;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotTimer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class FlyEvent implements Listener {

    private Survival survival;

    private Map<SurvivalPlayer, BukkitTask> noFall;
    private Map<SurvivalPlayer, SpigotTimer> timers;

    public FlyEvent(Survival survival) {
        this.survival = survival;
        this.noFall = new HashMap<>();
        this.timers = new HashMap<>();
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        SurvivalPlayer player = survival.getPlayer(event.getPlayer());

        /* OpMode can fly anywhere */
        if (player.isOpMode() || player.getGameMode() != GameMode.SURVIVAL)
            return;

        Claim claim = survival.getClaimHandler().getClaimAt(player.getLocation(), false, player.getLastClaim());
        if (claim != null)
            player.setLastClaim(claim);

        if (claim == null || !claim.canAccess(player, false)) {
            if (player.isFlying() && !timers.containsKey(player))
                disableFly(player, true);

        } else if (noFall.containsKey(player)) {
            player.setAllowFlight(true);
            player.setFlying(true);

            noFall.get(player).cancel();
            noFall.remove(player);
        } else if (timers.containsKey(player)) {
            timers.get(player).cancel();
            timers.remove(player);
        }
    }

    private void disableFly(SurvivalPlayer player, boolean noFall) {
        BossBar bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        bossBar.addPlayer(player.bukkit());

        SpigotTimer timer = new SpigotTimer(survival, Interval.of(TimeUnit.SECOND, 3), Interval.of(TimeUnit.TICK, 1)) {
            @Override
            public void onInterval() {
                String time = TimeUtils.humanFriendlyTimer(player.getLanguage(), getRemainingTicks() * 50 + 1000);
                bossBar.setTitle("§c§l" + player.translate("survival", "player.claim.fly.disabling_in", time));
                bossBar.setProgress(getProgress());
            }

            @Override
            public void onFinish() {
                bossBar.removeAll();

                player.setFlying(false);
                player.setAllowFlight(false);

                new ActionBar(player.bukkit(), () -> "§c§l" + player.translate("survival", "player.claim.fly.only_in_claims", "§a§l" + SurvivalClaim.Permission.ACCESS.getName(player) + "§c§l"), 100).send();

                if (!noFall)
                    return;

                FlyEvent.this.noFall.put(player, new BukkitRunnable() {
                    @Override
                    public void run() {
                        FlyEvent.this.noFall.remove(player);
                    }
                }.runTaskLater(survival.getPlugin(), 150));
            }

            @Override
            public void cancel() {
                super.cancel();
                bossBar.removeAll();
            }
        };
        timer.start();

        timers.put(player, timer);
    }

    @EventHandler
    public void onFall(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player) || event.getCause() != EntityDamageEvent.DamageCause.FALL)
            return;

        SurvivalPlayer player = survival.getPlayer((Player) event.getEntity());

        if (!noFall.containsKey(player))
            return;

        event.setCancelled(true);
        noFall.get(player).cancel();
        noFall.remove(player);
    }
}
