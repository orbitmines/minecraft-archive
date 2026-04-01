package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.builders.chat;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ConsoleUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.mutable.MutablePlayerString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */
public class AdvancementMessage<P extends SpigotPlayer> {
    // TODO : Check 1.14, Add these messages to actual achievements

    private static long NEXT_ID = 0;

    private static final SpigotServer server;

    @Getter @Setter private String icon;
    @Getter @Setter private MutablePlayerString title;

    @Getter @Setter private Frame frame;
    @Getter @Setter private boolean announce;
    @Getter @Setter private boolean popup;
    
    static {
        server = SpigotServer.getInstance();
    }

    public AdvancementMessage(String icon, MutablePlayerString<P> title) {
        this.icon = icon;
        this.title = title;

        this.frame = Frame.GOAL;
        this.announce = false;
        this.popup = true;
    }

    public void send(P player) {
        NamespacedKey id = nextId();

        try {
            Bukkit.getUnsafe().loadAdvancement(id, serialize(player));
            //DEBUG ConsoleUtils.success("Advancement " + id + " created!");
        } catch (IllegalArgumentException ex) {
            /* Already exists */
            ConsoleUtils.warn("Advancement " + id + " already exists!");
            return;
        }

        award(id, player);

        new BukkitRunnable() {
            @Override
            public void run() {
                revoke(id, player);
                Bukkit.getUnsafe().removeAdvancement(id);
            }
        }.runTaskLater(server.getPlugin(), 100);
    }

    public void send(P... players) {
        for (P player : players) {
            send(player);
        }
    }

    private void send(Collection<? extends P> players) {
        for (P player : players) {
            send(player);
        }
    }

    private NamespacedKey nextId() {
        NamespacedKey id = new NamespacedKey(server.getPlugin(), "msd-" + 3 + String.format("%09d", NEXT_ID));
        NEXT_ID++;

        return id;
    }

    public AdvancementMessage copy() {
        AdvancementMessage copy = new AdvancementMessage(icon, title);
        copy.frame = this.frame;
        copy.announce = this.announce;
        copy.popup = this.popup;
        return copy;
    }

    private void award(NamespacedKey id, P player) {
        Advancement advancement = Bukkit.getAdvancement(id);

        AdvancementProgress progress = player.getAdvancementProgress(advancement);

        if (progress.isDone())
            return;

        for (String criteria : progress.getRemainingCriteria()) {
            progress.awardCriteria(criteria);
        }
    }

    private void revoke(NamespacedKey id, P player) {
        Advancement advancement = Bukkit.getAdvancement(id);

        AdvancementProgress progress = player.getAdvancementProgress(advancement);

        if (!progress.isDone())
            return;

        for (String criteria : progress.getAwardedCriteria()) {
            progress.revokeCriteria(criteria);
        }
    }

    private String serialize(P player) {
        JsonObject json = new JsonObject();

        JsonObject icon = new JsonObject();
        icon.addProperty("item", this.icon);

        JsonObject display = new JsonObject();
        display.add("icon", icon);
        display.addProperty("title", this.title.toString(player));
        display.addProperty("description", "");
        display.addProperty("background", "minecraft:textures/gui/advancements/backgrounds/adventure.png");
        display.addProperty("frame", this.frame.toString());
        display.addProperty("announce_to_chat", this.announce);
        display.addProperty("show_toast", this.popup);
        display.addProperty("hidden", true);

        JsonObject criteria = new JsonObject();
        JsonObject trigger = new JsonObject();

        trigger.addProperty("trigger", "minecraft:impossible");
        criteria.add("impossible", trigger);

        json.add("criteria", criteria);
        json.add("display", display);

        return new GsonBuilder().setPrettyPrinting().create().toJson(json);
    }

    public enum Frame {

        GOAL("goal"),
        TASK("task"),
        CHALLENGE("challenge");

        private final String name;

        Frame(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
