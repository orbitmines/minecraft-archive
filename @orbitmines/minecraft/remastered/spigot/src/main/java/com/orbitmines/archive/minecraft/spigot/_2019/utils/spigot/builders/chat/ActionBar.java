package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat;

import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotTimer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/*
* OrbitMines - @author Fadi Shawki - 29-7-2017
*/
public class ActionBar {

    private static List<ActionBar> actionBars = new CopyOnWriteArrayList<>();
    
    private static SpigotServer plugin;
    
    static {
        plugin = SpigotServer.getInstance();
    }

    private final MutableString message;
    private Player player;
    private long stay;

    private boolean isFirstRun = false;

    private SpigotTimer timer;

    public ActionBar(SpigotPlayer player, MutableString message, long stay) {
        this(player.bukkit(), message, stay);
    }

    public ActionBar(Player player, MutableString message, long stay) {
        this.player = player;
        this.message = message;
        this.stay = stay;
    }

    public MutableString getMessage() {
        return message;
    }

    public Player getPlayer() {
        return player;
    }

    public long getStay() {
        return stay;
    }

    public void setStay(int stay) {
        this.stay = stay;
    }

    public void send() {
        start();
    }

    public ActionBar copy() {
        return new ActionBar(player, message, stay);
    }

    public ActionBar copy(Player player) {
        return new ActionBar(player, message, stay);
    }

    private void start() {
        this.timer = new SpigotTimer(plugin, Interval.of(TimeUnit.TICK, (int) stay), Interval.of(TimeUnit.TICK, 1)) {

            @Override
            public void onInterval() {
                if (!player.getPlayer().isOnline()) {
                    stop();
                    return;
                }

                /* In case array fails, do not fail the start method */
                if (!isFirstRun) {
                    actionBars.add(0, ActionBar.this);
                    isFirstRun = true;
                }

                ActionBar last = getLastActionBar(player);

                /* Check if most recent actionbar is this actionbar */
                if (last == ActionBar.this)
                    ActionBar.plugin.getNms().actionBar().send(Collections.singletonList(player.getPlayer()), message.getString());

                onRun();
            }

            @Override
            public void onFinish() {
                stop();
            }
        };

        this.timer.async().start();
    }

    public void onRun() { }

    public void stop() {
        actionBars.remove(this);

        if (timer != null)
            timer.cancel();
    }

    public void forceStop() {
        ActionBar last = getLastActionBar(player);

        stop();

        if (last == this)
            /* Clear ActionBar, if we no longer need to display any action bar */
            plugin.getNms().actionBar().send(Collections.singletonList(player.getPlayer()), "");
    }

    private ActionBar getLastActionBar(Player player) {
        for (ActionBar next : actionBars) {
            if (next != null && next.getPlayer().equals(player))
                return next;
        }

        return null;
    }
}
