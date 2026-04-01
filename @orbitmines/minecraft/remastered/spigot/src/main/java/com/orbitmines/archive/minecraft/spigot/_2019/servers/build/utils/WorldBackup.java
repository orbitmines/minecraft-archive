package com.orbitmines.archive.minecraft.spigot._2019.servers.build.utils;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.utils.Message;
import com.orbitmines.archive.minecraft.spigot._2019.servers.build.Build;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import org.bukkit.Bukkit;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class WorldBackup {

    private static void copyDirectory(Path source, Path target) throws IOException {
        Files.walk(source).forEach(s -> {
            try {
                Path t = target.resolve(source.relativize(s));
                if (Files.isDirectory(s)) {
                    Files.createDirectories(t);
                } else {
                    Files.copy(s, t);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void create(File file) {
        String dataFolderPath = Build.getInstance().getDataFolder().getPath();

        File backupsFolder = new File(dataFolderPath + "/world_backups");

        if (!backupsFolder.exists())
            backupsFolder.mkdir();

        String date = DateUtils.format(DateUtils.now(), DateUtils.DATE_TIME_FORMAT);

        File backup = new File(dataFolderPath + "/world_backups/" + file.getName() + "_" + date);

        int i = 1;
        while (backup.exists()) {
            backup.renameTo(new File(dataFolderPath + "/world_backups/" + file.getName() + "_" +
                date + "-#" + i));
        }

        try {
            backup.mkdir();
            copyDirectory(file.toPath(), backup.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            Bukkit.broadcastMessage(Message.format("Backup", Color.FUCHSIA, "Failed to backup map '" + file.getName() + "'."));
        }

        Bukkit.broadcastMessage(Message.format("Backup", Color.FUCHSIA, "Created backup of map '" + file.getName() + "'."));
    }
}
