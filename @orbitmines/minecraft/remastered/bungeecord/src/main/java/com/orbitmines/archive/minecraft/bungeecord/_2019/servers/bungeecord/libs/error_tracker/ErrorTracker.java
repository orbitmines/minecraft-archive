package com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.error_tracker;

/*
 * OrbitMines - @author Fadi Shawki - 2019
 * Refactored into BungeeCord plugin
 */

import com.orbitmines.archive.minecraft._2019.libs.Environment;
import com.orbitmines.archive.minecraft._2019.libs.Server;
import com.orbitmines.archive.minecraft.bungeecord._2019.servers.bungeecord.libs.Bungeecord;
import lombok.Getter;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ErrorTracker implements Runnable {

    private static final long POLL_INTERVAL_MS = 2000;

    @Getter private final Bungeecord bungeecord;
    @Getter private final File errorsDir;
    @Getter private final File serversDir;
    @Getter private final Map<Server, ServerTracker> serverTrackers;

    private volatile boolean running;
    private Thread thread;

    public ErrorTracker(Bungeecord bungeecord) {
        this.bungeecord = bungeecord;
        this.serverTrackers = new HashMap<>();

        String root = Environment.get("OM_ROOT", ".");
        this.errorsDir = new File(root + "/.orbitmines/error_logs");
        this.serversDir = new File(root + "/.orbitmines/servers");
    }

    public void start() {
        if (!errorsDir.exists())
            errorsDir.mkdirs();

        for (Server server : Server.values()) {
            File logFile = getLogFile(server);
            ServerTracker tracker = new ServerTracker(errorsDir, logFile, bungeecord, server);

            /* Skip existing log content on startup to avoid re-reporting old errors */
            if (logFile.exists())
                tracker.setCursor(logFile.length());

            serverTrackers.put(server, tracker);
        }

        running = true;
        thread = new Thread(this, "ErrorTracker");
        thread.setDaemon(true);
        thread.start();

        bungeecord.getLogger().info("ErrorTracker started, monitoring " + serverTrackers.size() + " servers.");
    }

    public void stop() {
        running = false;
        if (thread != null)
            thread.interrupt();
    }

    @Override
    public void run() {
        while (running) {
            for (ServerTracker tracker : serverTrackers.values()) {
                try {
                    tracker.run();
                } catch (Exception ex) {
                    bungeecord.getLogger().severe("ErrorTracker error for " + tracker.getServiceName() + ": " + ex.getMessage());
                }
            }

            try {
                Thread.sleep(POLL_INTERVAL_MS);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void resetCursor(Server server) {
        ServerTracker tracker = serverTrackers.get(server);
        if (tracker != null)
            tracker.setCursor(0);
    }

    private File getLogFile(Server server) {
        if (server == Server.BUNGEECORD)
            return new File(serversDir, "BungeeCord/proxy.log.0");

        return new File(serversDir, server.getPluginName() + "/logs/latest.log");
    }
}
