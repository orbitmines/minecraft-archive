package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.error_tracker;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 * Refactored into BungeeCord plugin
 */

import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.discord.CustomChannel;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft._2019.utils.discord.DiscordBot;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import lombok.Getter;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Tracker implements Runnable {

    protected final File errorsDir;
    protected final File file;
    protected final Bungeecord bungeecord;

    @Getter private final String serviceName;
    @Getter private final String serviceDisplayName;
    @Getter private final Image serviceImage;

    private final ConcurrentHashMap<String, Long> cursors = new ConcurrentHashMap<>();

    public Tracker(File errorsDir, File file, Bungeecord bungeecord, String serviceName, String serviceDisplayName, Image serviceImage) {
        this.errorsDir = errorsDir;
        this.file = file;
        this.bungeecord = bungeecord;

        this.serviceName = serviceName;
        this.serviceDisplayName = serviceDisplayName;
        this.serviceImage = serviceImage;
    }

    public abstract void processLine(String line, int lineNumber, List<String> previousLines, List<String> nextLines);

    @Override
    public void run() {
        try {
            long start = System.currentTimeMillis();

            if (file.exists()) {
                long fileLength = file.length();
                long cursor = getCursor(fileLength);

                if (fileLength > cursor) {
                    RandomAccessFile raf = new RandomAccessFile(this.file, "r");
                    raf.seek(cursor);

                    List<String> lines = new ArrayList<>();

                    String line;
                    while ((line = raf.readLine()) != null) {
                        lines.add(line);
                    }
                    processLines(lines);

                    setCursor(raf.getFilePointer());
                    raf.close();

                    long duration = System.currentTimeMillis() - start;
                    if (lines.size() > 1)
                        bungeecord.getLogger().info("ErrorTracker: Processed " + NumberUtils.locale(lines.size()) + " lines for " + this.serviceName + " in " + NumberUtils.locale(duration) + "ms.");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void setCursor(long cursor) {
        cursors.put("error_tracker:" + this.serviceName + ":cursor", cursor);
    }

    public long getCursor(long fileLength) {
        Long cursor = cursors.get("error_tracker:" + this.serviceName + ":cursor");

        if (cursor == null)
            return 0L;

        /* Always a new file when fileLength is shorter than cursor */
        if (fileLength < cursor)
            return 0L;

        return cursor;
    }

    private void processLines(List<String> lines) {
        int index = 0;
        for (String line : lines) {
            List<String> previousLines;
            try {
                previousLines = lines.subList(0, index);
            } catch (IndexOutOfBoundsException ex) {
                previousLines = null;
            }
            List<String> nextLines;
            try {
                nextLines = lines.subList(index + 1, lines.size());
            } catch (IndexOutOfBoundsException ex) {
                nextLines = null;
            }
            processLine(line, index + 1, previousLines, nextLines);

            index++;
        }
    }

    protected TextChannel getChannel() {
        DiscordBot bot = bungeecord.getDiscordBot();
        if (bot == null)
            return null;

        return bot.getTextChannel(CustomChannel.ERRORS);
    }
}
