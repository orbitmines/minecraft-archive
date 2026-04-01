package com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.*;

/*
* OrbitMines - @author Fadi Shawki - 29-7-2017
*/
public class ScoreboardTeam {

    @Getter private String name;
    @Getter private List<Player> members;
    @Setter private String prefix;
    @Setter private String suffix;
    @Setter private ChatColor color;
    @Setter private boolean allowFriendlyFire;
    @Setter private boolean canSeeFriendlyInvisibles;
    @Getter private Map<Team.Option, Team.OptionStatus> options;

    public ScoreboardTeam(String name, Player... members) {
        this(name, new ArrayList<>(Arrays.asList(members)));
    }

    public ScoreboardTeam(String name, List<Player> members) {
        this.name = name;
        this.members = members;
        this.color = ChatColor.WHITE;
        this.allowFriendlyFire = true;
        this.canSeeFriendlyInvisibles = false;
        this.options = new HashMap<>();
        this.options.put(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        this.options.put(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
    }

    public void update(org.bukkit.scoreboard.Scoreboard scoreboard) {
        Team t = scoreboard.getTeam(name);
        if (t == null)
            t = scoreboard.registerNewTeam(name);

        for (Player member : members) {
            if (!t.hasEntry(member.getName()))
                t.addEntry(member.getName());
        }

        if (prefix != null && !t.getPrefix().equals(prefix))
            t.setPrefix(prefix);
        if (suffix != null && !t.getSuffix().equals(suffix))
            t.setSuffix(suffix);

        if (t.getColor() != color)
            t.setColor(color);

        if (t.allowFriendlyFire() != allowFriendlyFire)
            t.setAllowFriendlyFire(allowFriendlyFire);

        if (t.canSeeFriendlyInvisibles() != canSeeFriendlyInvisibles)
            t.setCanSeeFriendlyInvisibles(canSeeFriendlyInvisibles);

        for (Team.Option option : options.keySet()) {
            if (t.getOption(option) != options.get(option))
                t.setOption(option, options.get(option));
        }
    }
}
