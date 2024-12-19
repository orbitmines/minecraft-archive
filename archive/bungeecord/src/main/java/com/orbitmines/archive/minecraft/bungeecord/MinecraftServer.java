package com.orbitmines.archive.minecraft.bungeecord;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public MinecraftServer(String name, String version, String RAM) {
        this.name = name;
        this.version = version;
        this.RAM = RAM;
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
                "java -Xms" + this.RAM + " -Xmx" + this.RAM + " -XX:+UseG1GC -XX:+UnlockExperimentalVMOptions -XX:MaxGCPauseMillis=100 -XX:+DisableExplicitGC -XX:TargetSurvivorRatio=90 -XX:G1NewSizePercent=50 -XX:G1MaxNewSizePercent=80 -XX:InitiatingHeapOccupancyPercent=10 -XX:G1MixedGCLiveThresholdPercent=35 -XX:+AggressiveOpts -XX:+AlwaysPreTouch -XX:+ParallelRefProcEnabled -Dusing.aikars.flags=mcflags.emc.gs -Dfile.encoding=UTF-8 -Djline.terminal=jline.UnsupportedTerminal -jar server.jar",
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.running = false;
    }

    public void create() throws IOException {
        getDirectory().mkdirs();
        Files.copy(
            getServerJar().toPath(),
            new File(getDirectory().getPath() + "/server.jar").toPath(),
            StandardCopyOption.REPLACE_EXISTING
        );
    }

    public File getDirectory() { return new File(PARENT_DIR + "/servers/" + this.name); }

    public File getServerJar() {
        return new File(PARENT_DIR + "/spigot/spigot-" + this.version + ".jar");
    }

    public void stop() {
        this.process.destroy();
        this.running = false;
    }
}
