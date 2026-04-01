package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.scoreboard;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.rank.StaffRank;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMPlayer;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.OMServer;
import com.orbitmines.archive.minecraft._2019.utils.mutable.MutableString;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.ColorUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards.ScoreboardSet;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.scoreboards.ScoreboardTeam;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public abstract class DefaultScoreboard<S extends OMServer<S, P>, P extends OMPlayer<S, P>> extends ScoreboardSet<P> {

    private S server;

    private List<ScoreboardTeam> teams;
    private Map<StaffRank, ScoreboardTeam> staffRankTeams;
    private Map<VipRank, ScoreboardTeam> vipRankTeams;

    public DefaultScoreboard(S server, P player, MutableString title, MutableString... scores) {
        super(player, title, scores);

        this.server = server;

        teams = new ArrayList<>();
        staffRankTeams = new HashMap<>();
        vipRankTeams = new HashMap<>();

        StaffRank[] staffRanks = StaffRank.values();
        ArrayUtils.reverse(staffRanks);

        for (StaffRank rank : staffRanks) {
            if (rank.isNone())
                continue;

            ScoreboardTeam team = new ScoreboardTeam(getTeamName(rank));

            team.setPrefix(rank.getAsPrefix(null) + "§r ");

            team.setColor(ColorUtils.toChatColor(rank.getPrefixColor()));

            teams.add(team);
            staffRankTeams.put(rank, team);
        }

        VipRank[] vipRanks = VipRank.values();
        ArrayUtils.reverse(vipRanks);

        for (VipRank rank : vipRanks) {
            ScoreboardTeam team = new ScoreboardTeam(getTeamName(rank));

            if (!rank.isNone())
                team.setPrefix(rank.getAsPrefix(null) + "§r ");

            team.setColor(ColorUtils.toChatColor(rank.getPrefixColor()));

            teams.add(team);
            vipRankTeams.put(rank, team);
        }
    }

    /* Order ranks in tablist */
    private String getTeamName(StaffRank staffRank) {
        switch (staffRank) {

            case OWNER:
                return "a";
            case ADMIN:
                return "b";
            case DEVELOPER:
                return "c";
            case MODERATOR:
                return "d";
            case HELPER:
                return "e";
            case BUILDER:
                return "f";
            case YOUTUBE:
                return "g";
            case TWITCH:
                return "h";
        }
        throw new IllegalStateException();
    }
    private String getTeamName(VipRank vipRank) {
        switch (vipRank) {

            case EMERALD:
                return "i";
            case DIAMOND:
                return "j";
            case GOLD:
                return "k";
            case IRON:
                return "l";
            case NONE:
                return "m";
        }
        throw new IllegalStateException();
    }

    @Override
    public List<ScoreboardTeam> getTeams() {
        update();
        return teams;
    }

    private void update() {
        for (ScoreboardTeam team : teams) {
            team.getMembers().clear();
        }

        for (P player : server.getPlayers()) {
            ScoreboardTeam team;
            if (player.getStaffRank().isNone()) {
                team = vipRankTeams.get(player.getVipRank());
            } else {
                team = staffRankTeams.get(player.getStaffRank());
            }

            team.getMembers().add(player.bukkit());
        }
    }
}
