package com.orbitmines.archive.minecraft;

import lombok.Getter;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MinecraftServer {

    static String PARENT_DIR = "./../..";

    @Getter private String name;
    @Getter private Thread thread;
    @Getter private Process process;
    @Getter private boolean running;

    private String RAM;
    @Getter String version;
    @Getter int port;

    public String getIp() { return "127.0.0.1"; }

    public MinecraftServer(String name, String version, String RAM, int port) {
        this.name = name;
        this.version = version;
        this.RAM = RAM;
        this.port = port;
    }

    public Thread run() {
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                MinecraftServer.this.runSync();
            }
        });

        this.thread.start();
        return this.thread;
    }

    public void runSync() {
        try {
            this.create();

            this.running = true;
            this.process = Runtime.getRuntime().exec(
// 1.8 - 1.13
//                    "java -Xms" + this.RAM + " -Xmx" + this.RAM + " -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=100 -XX:+DisableExplicitGC -XX:TargetSurvivorRatio=90 -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=80 -XX:InitiatingHeapOccupancyPercent=10 -XX:G1MixedGCLiveThresholdPercent=35 -XX:+AggressiveOpts -XX:+AlwaysPreTouch -XX:+ParallelRefProcEnabled -Dusing.aikars.flags=mcflags.emc.gs -Dfile.encoding=UTF-8 -Djline.terminal=jline.UnsupportedTerminal -jar server.jar",
                    "java -Xms" + this.RAM + " -Xmx" + this.RAM + " -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=100 -XX:+DisableExplicitGC -XX:TargetSurvivorRatio=90 -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=80 -XX:InitiatingHeapOccupancyPercent=10 -XX:G1MixedGCLiveThresholdPercent=35 -XX:+AlwaysPreTouch -XX:+ParallelRefProcEnabled -Dusing.aikars.flags=mcflags.emc.gs -Dfile.encoding=UTF-8 -Djline.terminal=jline.UnsupportedTerminal -jar server.jar nogui",
                    null,
                    this.getDirectory()
            );

            BufferedReader input = new BufferedReader(new InputStreamReader(this.process.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(this.process.getErrorStream()));

            String s;
            while ((s = input.readLine()) != null) {
                System.out.println("[\033[1;32m" + this.name + "\033[0m] " + s);
            }
            while ((s = error.readLine()) != null) {
                System.out.println("[\033[1;31m" + this.name + "\033[0m] " + s);
            }
        } catch (IOException | ConfigurationException e) {
            e.printStackTrace();
        }
        this.running = false;
    }

    public void create() throws IOException, ConfigurationException {
        getDirectory().mkdirs();

        Files.copy(
            getServerJar().toPath(),
            new File(getDirectory().getPath() + "/server.jar").toPath(),
            StandardCopyOption.REPLACE_EXISTING
        );

        // If the server isn't shut down properly lock files could be locked.
        for (File directory : getDirectory().listFiles()) {
            if (!directory.isDirectory()) continue;

            File lockFile = new File(directory.getPath() + "/session.lock");
            lockFile.delete();
        }

        this.installPlugin(PARENT_DIR + "/plugins/archive-spigot.jar");

        PrintWriter eula = new PrintWriter(getDirectory().getPath() + "/eula.txt", "UTF-8");
        eula.println("eula=true");
        eula.close();

        new File(getDirectory().getPath() + "/server.properties").createNewFile();
        PropertiesConfiguration properties = new PropertiesConfiguration(getDirectory().getPath() + "/server.properties");
        properties.setProperty("generator-settings", "");
        properties.setProperty("force-gamemode", "false");
        properties.setProperty("allow-nether", "true");
        properties.setProperty("enforce-whitelist", "false");
        properties.setProperty("gamemode", "0");
        properties.setProperty("enable-query", "false");
        properties.setProperty("player-idle-timeout", "0");
        properties.setProperty("difficulty", "1");
        properties.setProperty("spawn-monsters", "true");
        properties.setProperty("op-permission-level", "4");
        properties.setProperty("pvp", "true");
        properties.setProperty("snooper-enabled", "true");
        properties.setProperty("level-type", "DEFAULT");
        properties.setProperty("hardcore", "false");
        properties.setProperty("enable-command-block", "false");
        properties.setProperty("max-players", "20");
        properties.setProperty("network-compression-threshold", "256");
        properties.setProperty("resource-pack-sha1", "");
        properties.setProperty("max-world-size", "29999984");
        properties.setProperty("server-port", this.getPort());
        properties.setProperty("server-ip", "");
        properties.setProperty("spawn-npcs", "true");
        properties.setProperty("allow-flight", "false");
        properties.setProperty("level-name", "world");
        properties.setProperty("view-distance", "10");
        properties.setProperty("resource-pack", "");
        properties.setProperty("spawn-animals", "true");
        properties.setProperty("white-list", "false");
        properties.setProperty("generate-structures", "true");
        properties.setProperty("online-mode", "true");
        properties.setProperty("max-build-height", "256");
        properties.setProperty("level-seed", "");
        properties.setProperty("prevent-proxy-connections", "false");
        properties.setProperty("use-native-transport", "true");
        properties.setProperty("enable-rcon", "false");
        properties.setProperty("motd", "A Minecraft Server");
        properties.save();
    }

    public void installPlugin(String file) throws IOException {
        getPluginDirectory().mkdirs();

        Files.copy(
            new File(file).toPath(),
            new File(getPluginDirectory().getPath() + "/" + file.split("/")[file.split("/").length - 1]).toPath(),
            StandardCopyOption.REPLACE_EXISTING
        );
    }

    public File getDirectory() { return new File(PARENT_DIR + "/servers/" + this.name); }
    public File getPluginDirectory() { return new File(getDirectory().getPath() + "/plugins"); }

    public File getServerJar() {
        return new File(PARENT_DIR + "/spigot/spigot-" + this.version + ".jar");
    }

    public void sendCommand(String command) {
        OutputStream stream = this.process.getOutputStream();
        try {
            stream.write(command.getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (this.process != null) this.process.destroy();
        this.running = false;
    }

    public void delete() {
        this.stop();
        FileUtils.delete(getDirectory());
    }
}
