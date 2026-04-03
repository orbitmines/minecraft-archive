package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.error_tracker;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 * Refactored into BungeeCord plugin
 */

import com.orbitmines.archive.minecraft._2019.libs.Color;
import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Image;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft._2019.utils.DateUtils;
import com.orbitmines.archive.minecraft._2019.utils.NumberUtils;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import net.dv8tion.jda.api.utils.FileUpload;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServerTracker extends Tracker {

    private static final String TIME_PATTERN_STRING = "\\[?(\\d{2}:\\d{2}:\\d{2})]?";
    private static final String THREAD_PATTERN_STRING = "\\[?(.+?(?=]))]";

    // (\[?(\d{2}:\d{2}:\d{2})]?\s\[?(.+?(?=]))]:?\s)?
    private static final String PREFIX_PATTERN_STRING = "(" + TIME_PATTERN_STRING + "\\s" + THREAD_PATTERN_STRING + ":?\\s)?";

    private static final String EXCEPTION_PATTERN_STRING = "(.*?Exception((.*?(?=:\\s))|\\n|$))(:\\s(.*))?";
    // Group 4 = Exception
    // Group 8 = Message (Optional)

    private static final String STACKTRACE_PATTERN_STRING = "\\sat\\s(.+)|...\\s\\d+\\smore";

    private static final String CAUSED_BY_PATTERN_STRING = "Caused\\sby:\\s";

    private static final Pattern EXCEPTION_PATTERN = Pattern.compile(PREFIX_PATTERN_STRING + EXCEPTION_PATTERN_STRING);
    private static final Pattern CAUSED_BY_EXCEPTION_PATTERN = Pattern.compile(PREFIX_PATTERN_STRING + CAUSED_BY_PATTERN_STRING + EXCEPTION_PATTERN_STRING);
    private static final Pattern STACKTRACE_PATTERN = Pattern.compile(PREFIX_PATTERN_STRING + STACKTRACE_PATTERN_STRING);

    public ServerTracker(File errorsDir, File file, Bungeecord bungeecord, Server server) {
        super(errorsDir, file, bungeecord, server.getPluginName(), server.getName(), Image.icon(server));
    }

    @Override
    public void processLine(String line, int lineNumber, List<String> previousLines, List<String> nextLines) {
        Matcher exceptionMatcher = EXCEPTION_PATTERN.matcher(line);

        while (exceptionMatcher.find()) {
            {
                // Skip caused by
                Matcher causedByMatcher = CAUSED_BY_EXCEPTION_PATTERN.matcher(line);

                while (causedByMatcher.find()) {
                    return;
                }
            }

            UUID errorId = UUID.randomUUID();

            TimeZone timeZone = Calendar.getInstance().getTimeZone();
            String threadTime = group(exceptionMatcher, 2);
            String time = (threadTime != null ? threadTime : DateUtils.format(DateUtils.now(), DateUtils.TIME_FORMAT)) + " " + timeZone.getID();
            String thread = group(exceptionMatcher, 3);
            String exception = group(exceptionMatcher, 4);
            String clazz = clazz(exception);

            String message = group(exceptionMatcher, 8);
            String header = header(clazz, message);

            bungeecord.getLogger().info("ErrorTracker: Found error for " + this.getServiceName() + " '" + clazz + "' [" + errorId + "] at line " + NumberUtils.locale(lineNumber));

            // Build complete stacktrace
            List<String> stackTrace = new ArrayList<>();
            if (previousLines != null && !previousLines.isEmpty())
                stackTrace.add(previousLines.get(previousLines.size() - 1));

            stackTrace.add(line);

            List<String> causedBy = new ArrayList<>();

            int index = 0;
            while (nextLines != null && index < nextLines.size()) {
                String stackLine = nextLines.get(index);
                Matcher stacktraceMatcher = STACKTRACE_PATTERN.matcher(stackLine);
                Matcher causedByMatcher = CAUSED_BY_EXCEPTION_PATTERN.matcher(stackLine);

                boolean found = false;

                while (stacktraceMatcher.find()) {
                    stackTrace.add(stackLine);
                    found = true;
                }

                while (!found && causedByMatcher.find()) {
                    String stackException = group(causedByMatcher, 4);
                    String stackClazz = clazz(stackException);
                    String stackMessage = group(causedByMatcher, 8);

                    causedBy.add(header(stackClazz, stackMessage));

                    stackTrace.add(stackLine);
                    found = true;
                }

                if (!found)
                    break;

                index++;
            }

            // Save error log file
            buildErrorFile(errorId, clazz, stackTrace);

            // Send to Discord
            TextChannel channel = getChannel();
            if (channel != null) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor(header);
                if (exception != null)
                    builder.setDescription(exception);

                builder.setColor(Color.MAROON.getAwtColor());

                if (message != null)
                    builder.addField("Message", message, true);

                if (!causedBy.isEmpty())
                    builder.addField("Caused by", String.join("\u200B", causedBy), true);

                builder.addField("Error ID", errorId.toString(), false);
                if (thread != null)
                    builder.addField("Thread", thread, true);

                builder.setFooter(this.getServiceDisplayName() + " / " + Environment.get() + " / " + time, this.getServiceImage().getUrl());

                channel.sendMessage(Environment.getEveryoneOrDev(bungeecord.getDiscordBot()) + " " + header).queue();
                channel.sendMessageEmbeds(builder.build()).queue();

                File errorFile = getErrorFile(errorId, clazz);
                if (errorFile.exists())
                    channel.sendFiles(FileUpload.fromData(errorFile)).queue();
            }
        }
    }

    private String group(Matcher matcher, int group) {
        try {
            return matcher.group(group);
        } catch (IndexOutOfBoundsException ex) {
            return null;
        }
    }

    private String clazz(String exception) {
        if (exception == null)
            return null;

        String[] s = exception.split("\\.");
        return s[s.length - 1];
    }

    private String header(String clazz, String message) {
        return clazz + (message != null ? ": " + message : "");
    }

    private File getErrorFile(UUID fileUUID, String clazz) {
        return new File(errorsDir.getAbsolutePath() + "/" + clazz + "_" + fileUUID + ".txt");
    }

    private void buildErrorFile(UUID fileUUID, String clazz, List<String> stackTrace) {
        File file = getErrorFile(fileUUID, clazz);

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)))) {
            for (String line : stackTrace) {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
