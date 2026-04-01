package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.restarter;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.BungeePlayer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.pubsub.publishers.ServerRestartPublisher;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.vote.TopVoterHandler;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.builds.chat.BossBar;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable.BungeeRunnable;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable.BungeeTimer;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable.Interval;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.utils.runnable.TimeUnit;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.jedis.JedisManager;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import redis.clients.jedis.Jedis;

@Deprecated /* Move Top Vote Handler logic elsewhere, and add language support */
public class Restarter extends BungeeRunnable {

    private static Interval RESTART_TIMER = Interval.of(TimeUnit.MINUTE, 10);

    private final Bungeecord bungee;
    private final String startUpMonth;
    private final int startUpYear;
    private final long startUpTime;

    private boolean initiatedRestart = false;

    public Restarter(Bungeecord bungee) {
        super(bungee, Interval.of(TimeUnit.MINUTE, 5));

        this.bungee = bungee;
        this.startUpMonth = DateUtils.humanFriendlyMonth();
        this.startUpYear = DateUtils.getYear();
        this.startUpTime = System.currentTimeMillis();

        String month = getLastVoteMonth();

        if (month == null) {
            new NullPointerException("Could not get bungee:last_vote_month from redis, aborting top voter check").printStackTrace();
            return;
        }

        if (month.equals(toString(startUpMonth, startUpYear)))
            return;

        new TopVoterHandler(bungee).process();
    }

    @Override
    public void run() {
        if (initiatedRestart)
            return;

        if ((System.currentTimeMillis() - startUpTime) >= 1000 * 60 * 60 * 6)
            initiateRestart();
    }

    private String toString(String month, int year) {
        return month + "-" + year;
    }

    private String getLastVoteMonth() {
        try (Jedis jedis = JedisManager.get()) {
            return jedis.get("bungee:last_vote_month");
        }
    }

    private void initiateRestart() {
        System.out.println("Initiating Restart...");
        initiatedRestart = true;

        BossBar bossBar = new BossBar("§8§lOrbit§7§lMines §c§lRestarting in " + TimeUtils.humanFriendlyTimeUnit(Language.ENGLISH, RESTART_TIMER.toMillis()) + "...", BossBar.Color.RED, BossBar.Style.SOLID);

        for (ProxiedPlayer player : plugin.getProxy().getPlayers()) {
            bossBar.addPlayer(player);
        }

        new BungeeTimer(bungee, RESTART_TIMER, Interval.of(TimeUnit.SECOND, 1)) {
            @Override
            public void onFinish() {
                /* Kick all Players */
                for (BungeePlayer omp : bungee.getPlayers()) {
                    omp.disconnect(new TextComponent(
                        "§8§lOrbit§7§lMines\n" +
                        "§c§l" + omp.translate("bungeecord", "connection.header") + "\n" +
                        "\n" +
                        "§7" + omp.translate("bungeecord", "connection.restarting")
                    ));
                }

                /* Shutdown all servers */
                new ServerRestartPublisher(bungee).publish("Daily Restart", DateUtils.now());
            }

            @Override
            public void onInterval() {
                bossBar.setTitle("§8§lOrbit§7§lMines §c§lRestarting in " + TimeUtils.humanFriendlyTimer(Language.ENGLISH, getRemainingSeconds() * 1000) + "...");
                bossBar.setProgress(getProgress());

                for (ProxiedPlayer player : bungee.getProxy().getPlayers()) {
                    bossBar.addPlayer(player);
                }
            }
        }.async().start();
    }

}
