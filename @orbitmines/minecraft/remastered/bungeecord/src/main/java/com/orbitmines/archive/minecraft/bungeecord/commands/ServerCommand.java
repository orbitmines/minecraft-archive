package com.orbitmines.archive.minecraft.bungeecord.commands;

import com.orbitmines.archive.minecraft.MinecraftServer;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.command.ConsoleCommandSender;

public class ServerCommand extends Command {

    private final MinecraftServer server;

    public ServerCommand(MinecraftServer server) {
        super("[" + server.getName() + "]");
        this.server = server;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ConsoleCommandSender))
            return;

        if (args.length == 0) {
            sender.sendMessage("Usage: [§a" + server.getName() + "§r] <command>");
            return;
        }

        if (!server.isRunning()) {
            sender.sendMessage("Server [§a" + server.getName() + "§r] is not running.");
            return;
        }

        String command = String.join(" ", args);
        server.sendCommand(command);
        sender.sendMessage("Sent command to [§a" + server.getName() + "§r]: " + command);
    }
}
