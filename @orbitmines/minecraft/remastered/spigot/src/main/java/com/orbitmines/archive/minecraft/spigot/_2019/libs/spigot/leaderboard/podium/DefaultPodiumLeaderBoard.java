package com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.podium;

import com.orbitmines.archive.minecraft._2019.libs.player.Name;
import com.orbitmines.archive.minecraft._2019.libs.player.OfflinePlayer;
import com.orbitmines.archive.minecraft._2019.libs.rank.VipRank;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.leaderboard.LeaderBoard;
import com.orbitmines.archive.minecraft._2019.utils.database.DatabaseManager;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Selectable;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.Table;
import com.orbitmines.archive.minecraft._2019.utils.database.lib.builder.mysql.MySQLQueryBuilder;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockDataUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.BlockUtils;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.SkullTextures;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.Hologram;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.placeholders.SpigotServer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;

import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

/*
 * OrbitMines - @author Fadi Shawki - 29-7-2017
 */
public class DefaultPodiumLeaderBoard extends LeaderBoard {

    private static List<Podium> podiums = new ArrayList<>();

    protected int size;

    protected ArrayList<LinkedHashMap<Selectable, Object>> ordered;

    public DefaultPodiumLeaderBoard(Location location, String[] data, int size, Table table, Selectable uuidColumn, Selectable column, MySQLQueryBuilder queryBuilder) {
        super(location, table, uuidColumn, column, queryBuilder);

        int place = Integer.parseInt(data[1]);
        float yaw = Float.parseFloat(data[2]);

        for (Podium podium : podiums) {
            if (podium.getPlace() != place)
                continue;

            podiums.add(new Podium(location, place, yaw));
            getLeaderBoards().remove(this);
            return;
        }

        this.size = size;
        this.ordered = new ArrayList<>();

        podiums.add(new Podium(location, place, yaw));
    }

    @Override
    public void update() {
        /* Clear from previous update */
        this.ordered.clear();

        /* Update top {size} players */
        ArrayList<LinkedHashMap<Selectable, Object>> entries = DatabaseManager.getInstance().getDefault().getEntries(queryBuilder, columnArray[0], columnArray[1]);

        this.ordered = getOnLeaderBoard(entries);

        /* Update Hologram */
        for (Podium podium : podiums) {
            podium.update();
        }
    }

    public int getSize() {
        return size;
    }

    /* Override this method to change the displayed uuids */
    protected ArrayList<LinkedHashMap<Selectable, Object>> getOnLeaderBoard(ArrayList<LinkedHashMap<Selectable, Object>> entries) {
        ArrayList<LinkedHashMap<Selectable, Object>> ordered = new ArrayList<>(entries);
        ordered.sort((m1, m2) -> asInteger(m2.get(columnArray[1])) - asInteger(m1.get(columnArray[1])));

        if (ordered.size() > size)
            ordered = new ArrayList<>(ordered.subList(0, size));

        return ordered;
    }

    /* Override this method to change to change the message displayed at the end */
    public String getValue(OfflinePlayer player, int count) {
        return "§6" + count + "";
    }

    private final class Podium {

        private final Location location;
        private final int place;
        private final float yaw;

        private final Hologram hologram;

        private OfflinePlayer player;
        private int count;

        public Podium(Location location, int place, float yaw) {
            this.location = location;
            this.place = place;
            this.yaw = yaw;

            hologram = new Hologram(location, 0, Hologram.Face.UP);

            String placeString;
            switch (place) {
                case 1:
                    placeString = "§6§l1st";
                    break;
                case 2:
                    placeString = "§7§l2nd";
                    break;
                case 3:
                    placeString = "§c§l3rd";
                    break;
                default:
                    placeString = "§8§l" + place + "th";
            }
            hologram.addLine(() -> placeString, false);

            hologram.addLine(() -> {
                if (player != null) {
                    return player.getName(Name.NICK_COLORED);
                } else {
                    return VipRank.NONE.getPrefixColor().getCc() + "UNKNOWN PLAYER";
                }
            }, false);

            hologram.addLine(() -> getValue(player, count), false);
            hologram.create();
        }

        public Location getLocation() {
            return location;
        }

        public int getPlace() {
            return place;
        }

        public float getYaw() {
            return yaw;
        }

        public void update() {
            if (ordered == null)
                return;

            if (ordered.size() < place) {
                player = null;
                count = 0;

                SpigotServer.getInstance().runSync(() -> {
                    Block block = BlockDataUtils.setBlock(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), Material.SKELETON_SKULL);

                    Skull skull = (Skull) block.getState();
                    skull.setRotation(BlockUtils.getBlockFaceFromYaw(yaw));

                    block.getState().update(true);
                });
                return;
            }

            LinkedHashMap<Selectable, Object> entry = ordered.get(place - 1);

            UUID uuid = UUID.fromString((String) entry.get(columnArray[0]));
            this.player = OfflinePlayer.get(uuid);

            count = asInteger(entry.get(columnArray[1]));

            SpigotServer.getInstance().runSync(() -> {
                hologram.update();

                Block block = BlockDataUtils.setBlock(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ(), Material.PLAYER_HEAD);

                Skull skull = (Skull) block.getState();
                SkullTextures.applyTo(skull, uuid, () -> SpigotServer.getInstance().runSync(() -> {
                    Block refreshed = location.getBlock();
                    if (refreshed.getState() instanceof Skull s) {
                        SkullTextures.applyTo(s, uuid, null);
                        s.update(true, true);
                    }
                }));
                skull.setRotation(BlockUtils.getBlockFaceFromYaw(yaw).getOppositeFace());

                skull.update(true, true);
            });
        }
    }
}
