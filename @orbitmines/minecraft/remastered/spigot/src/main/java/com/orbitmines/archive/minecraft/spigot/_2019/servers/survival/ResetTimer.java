package com.orbitmines.archive.minecraft.spigot._2019.servers.survival;

/*
 * OrbitMines - @author Fadi Shawki - 2018
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.spigot._2019.libs.spigot.database.models.survival.SurvivalPlayerModel;
import com.orbitmines.archive.minecraft.spigot._2019.servers.survival.player.SurvivalPlayer;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.TimeUtils;
import com.orbitmines.archive.minecraft._2019.utils.state.StateProvider;
import com.orbitmines.archive.minecraft._2019.utils.language.Language;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.npcs.Hologram;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.Interval;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.PassiveRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotRunnable;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.SpigotTimer;
import com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.worlds.generators.DefaultWorldType;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Deprecated
public enum ResetTimer {

    NETHER_RESET("Nether", DefaultWorldType.NETHER, Color.RED, TimeUnit.DAYS, 90) {
        @Override
        public World getWorld(Survival survival) {
            return survival.getWorldNether();
        }

        @Override
        public void setWorld(Survival survival, World world) {
            survival.setWorldNether(world);
        }
    },
    END_RESET("End", DefaultWorldType.END, Color.YELLOW, TimeUnit.DAYS, 180) {
        @Override
        public World getWorld(Survival survival) {
            return survival.getWorldTheEnd();
        }

        @Override
        public void setWorld(Survival survival, World world) {
            survival.setWorldTheEnd(world);
        }
    };

    @Getter private final String name;
    @Getter private final DefaultWorldType worldType;
    @Getter private final Color color;
    @Getter private final TimeUnit timeUnit;
    @Getter private final int amount;

    @Getter private long nextReset;
    @Getter private List<Hologram> holograms;
    private SpigotRunnable runnable;

    ResetTimer(String name, DefaultWorldType worldType, Color color, TimeUnit timeUnit, int amount) {
        this.name = name;
        this.worldType = worldType;
        this.color = color;
        this.timeUnit = timeUnit;
        this.amount = amount;
        this.holograms = new ArrayList<>();
    }

    public String getDisplayName() {
        return color.getCc() + "§l" + name;
    }

    public long getNextReset() {
        return nextReset;
    }

    public String getResetInString(Language language) {
        long left = nextReset - System.currentTimeMillis();
        return left >= 0 ? TimeUtils.humanFriendlyTimer(language, left) : "Resetting...";
    }

    public World getWorld(Survival survival) {
        throw new IllegalStateException();
    }

    public void setWorld(Survival survival, World world) {
        throw new IllegalStateException();
    }

    public void setup(Survival survival) {
        setupNextReset();

        /* If the reset should've already happened when the server starts. */
        if (nextReset - System.currentTimeMillis() <= 0)
            reset(survival);
        else
            startRunnable(survival);
    }

    private void setupNextReset() {
        Date plannedReset = getPlannedReset();
        if (plannedReset == null) {
            plannedReset = new Date(System.currentTimeMillis() + timeUnit.toMillis(amount));
            setPlannedReset(plannedReset);
        }

        this.nextReset = plannedReset.getTime();
    }

    private Date getPlannedReset() {
        String date = StateProvider.getInstance().getString("survival:planned_reset:" + toString().toLowerCase());

        if (date == null)
            return null;

        return DateUtils.parse(date, DateUtils.DATE_TIME_FORMAT);
    }

    private void setPlannedReset(Date date) {
        StateProvider.getInstance().setString("survival:planned_reset:" + toString().toLowerCase(), DateUtils.format(date, DateUtils.DATE_TIME_FORMAT));
    }

    private void reset(Survival survival) {
//        String worldName = getWorld(survival).getName();
//
////        survival.broadcast("Survival", Color.BLUE, "De " + getDisplayName() + "§7 is aan het resetten...", "The " + getDisplayName() + "§7 is resetting...");
//
//        for (SurvivalPlayer omp : survival.getPlayers()) {
//            omp.connect(Server.HUB, true);
//        }
//
//        delete(survival);
//        setWorld(survival, survival.getWorldLoader().loadWorld(worldName, false, worldType));
//
//        //TODO
////        Database.get().update(Table.SERVER_DATA, new Set(TableServerData.DATA, nextReset() + ""), new Where(TableServerData.SERVER, survival.getServer().toString()), new Where(TableServerData.TYPE, toString()));
//
//        Bukkit.shutdown();
    }

    private void startRunnable(Survival survival) {
        if (runnable != null)
            runnable.cancel();

        long left = nextReset - System.currentTimeMillis();

        /* Only start reset runnable if the reset will happen today, as the server restarts every 24 hours :) */
        if (left <= TimeUnit.DAYS.toMillis(1)) {
            if (left > TimeUnit.MINUTES.toMillis(5)) {
                runnable = new SpigotTimer(survival, Interval.of(com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit.SECOND, (int) (left / 1000F) - 5), Interval.of(com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit.SECOND, 1)) {
                    @Override
                    public void onInterval() {
                        for (Hologram hologram : holograms) {
                            hologram.update();
                        }
                    }

                    @Override
                    public void onFinish() {
//                        initiateReset(survival, (int) (nextReset - System.currentTimeMillis()) / 1000);
                    }
                };
                runnable.async().start();
            } else {
//                initiateReset(survival, (int) left / 1000);
            }
        } else {
            /* Otherwise we just update all the holograms every minute instead of every second */
            runnable = new PassiveRunnable<Survival>(survival, Interval.of(com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit.MINUTE, 1)) {
                @Override
                public void onRun() {
                    for (Hologram hologram : holograms) {
                        hologram.update();
                    }
                }
            };
            runnable.async().start();
        }
    }

    private void initiateReset(Survival survival, int seconds) {
        if (runnable != null)
            runnable.cancel();

        BossBar bossBar = Bukkit.createBossBar("", BarColor.RED, BarStyle.SOLID);

        runnable = new SpigotTimer(survival, Interval.of(com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit.SECOND, seconds), Interval.of(com.orbitmines.archive.minecraft.spigot._2019.utils.spigot.runnable.TimeUnit.SECOND, 1)) {
            @Override
            public void onInterval() {
                for (Hologram hologram : holograms) {
                    hologram.update();
                }

                bossBar.setTitle(Server.SURVIVAL.getDisplayName() + " §c§lRestarting in " + TimeUtils.humanFriendlyTimer(Language.ENGLISH, (getRemainingTicks() / 20) * 1000) + "...");
                bossBar.setProgress(getProgress());

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!bossBar.getPlayers().contains(player))
                        bossBar.addPlayer(player);
                }
            }

            @Override
            public void onFinish() {
                reset(survival);
            }
        };
        runnable.async().start();
    }

    private void delete(Survival survival) {
        World world = getWorld(survival);

        if (world != null) {
            File worldFile = world.getWorldFolder();

            /* Teleport all players online to spawn */
            for (SurvivalPlayer omp : survival.getPlayers()) {
                if (!omp.getWorld().getName().equals(world.getName()))
                    continue;

                omp.teleport(survival.getSpawn());
                omp.setLogoutLocation(omp.getLocation());
                omp.update(SurvivalPlayerModel.column.LOGOUT_LOCATION);
            }

            /* Update logout location for all players */
//            Database.get().update(Table.SURVIVAL_PLAYERS, new Set(TableSurvivalPlayers.LOGOUT_LOCATION, Serializer.serialize(survival.getLobbySpawn())), new Where(Where.Operator.LIKE, TableSurvivalPlayers.LOGOUT_LOCATION, world.getName() + "%"));
            //TODO

            /* Delete All Claims in the world */
            survival.getClaimHandler().deleteClaims(world);

            /* Unload World */
            Bukkit.unloadWorld(world, true);

            /* Backup World */
            backup(survival);

            /* Delete World */
            com.orbitmines.archive.minecraft._2019.utils.FileUtils.deleteDirectory(worldFile);
        }
    }

    private void backup(Survival survival) {
        File file = getWorld(survival).getWorldFolder();
        File dataFolder = survival.getDataFolder();

        File backupsFolder = new File(dataFolder.getPath() + "/WorldBackups");

        if (!backupsFolder.exists())
            backupsFolder.mkdir();

        String date = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date());

        File backup = new File(dataFolder.getPath() + "/WorldBackups/" + file.getName() + "_" + date);

        int i = 1;
        while (backup.exists()) {
            backup.renameTo(new File(dataFolder.getPath() + "/WorldBackups/" + file.getName() + "_" +
                    date + "-#" + i));
        }

        try {
            backup.mkdir();
            FileUtils.copyDirectory(file, backup);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
