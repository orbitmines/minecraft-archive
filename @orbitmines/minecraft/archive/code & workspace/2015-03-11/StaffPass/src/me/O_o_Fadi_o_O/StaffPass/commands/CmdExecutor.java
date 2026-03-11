/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.O_o_Fadi_o_O.StaffPass.commands;

import me.O_o_Fadi_o_O.StaffPass.StaffSecure;
import me.O_o_Fadi_o_O.StaffPass.controllers.ConfigController;
import me.O_o_Fadi_o_O.StaffPass.controllers.PlayerController;
import me.O_o_Fadi_o_O.StaffPass.struct.StaffSecureUser;
import me.O_o_Fadi_o_O.StaffPass.utils.EncryptionUtils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author devan_000
 */
public class CmdExecutor implements CommandExecutor {

      private final StaffSecure plugin = StaffSecure.get();

      @Override
      public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
            boolean isSenderPlayer = sender instanceof Player;

            if (commandLabel.toLowerCase().equals("login")) {
                  if (!isSenderPlayer) {
                        plugin.log("That is not a console command.");
                        return true;
                  }

                  Player player = (Player) sender;

                  if (player == null) {
                        return true;
                  }

                  if (!player.hasPermission("staffsecure.staff")) {
                        return true;
                  }

                  StaffSecureUser user = plugin.users.get(player.getUniqueId().toString());

                  //Well they have perms now...
                  if (user == null) {
                        PlayerController.handleLogin(player);
                        return true;
                  }

                  if (user.isLoggedIn) {
                        player.sendMessage("§7You are already logged in!");
                        return true;
                  }

                  if (user.config.encryptedPassword == null) {
                        player.sendMessage("§7Use §6/password <pass>");
                        return true;
                  }

                  if (args.length != 1) {
                        player.sendMessage("§7Use §6/login <pass>");
                        return true;
                  }

                  if (args.length == 1) {
                        if (!user.config.encryptedPassword.equals(EncryptionUtils.getMD5(args[0]))) {
                              player.kickPlayer(ChatColor.RED + "You entered an incorrect password");
                              return true;
                        }
                        player.sendMessage("§7Logged in!");
                        plugin.users.get(player.getUniqueId().toString()).isLoggedIn = true;
                        plugin.users.get(player.getUniqueId().toString()).config.loggedIntoLastLoggedIp = true;
                        return true;
                  }
            }

            if (command.getName().equalsIgnoreCase("password")) {
                  if (!isSenderPlayer) {
                        plugin.log("That is not a console command.");
                        return true;
                  }

                  Player player = (Player) sender;

                  if (!player.hasPermission("staffsecure.staff")) {
                        return true;
                  }

                  StaffSecureUser user = plugin.users.get(player.getUniqueId().toString());

                  if (user.isLoggedIn) {
                        player.sendMessage("§7You already logged in!");
                        return true;
                  }

                  if (user.config.encryptedPassword != null) {
                        player.sendMessage("§7You already have a password set!");
                        return true;
                  }

                  if (args.length != 1) {
                        player.sendMessage("§7Use §6/password <pass>");
                        return true;
                  }

                  if (args.length == 1) {
                        plugin.users.get(player.getUniqueId().toString()).config.setPassword(EncryptionUtils.getMD5(args[0]));
                        player.sendMessage("§7Password set! You must now /login");
                        return true;
                  }
            }

            if (commandLabel.toLowerCase().equals("staffsecure")) {
                  if (!sender.hasPermission("staffsecure.admin")) {
                        return true;
                  }

                  if (args.length != 1) {
                        sender.sendMessage("§7Use §6/staffsecure <reload>");
                        return true;
                  }

                  if (args.length == 1) {
                        if (args[0].contains("reload")) {
                              plugin.reloadConfig();
                              sender.sendMessage("§6Plugins config reloaded!");
                              return true;
                        } else {
                              sender.sendMessage("Use §6/staffsecure <reload>");
                              return true;
                        }
                  }
            }

            if (commandLabel.equalsIgnoreCase("resetpassword")) {
                  if (isSenderPlayer) {
                        sender.sendMessage("§7That is not a player command.");
                        return true;
                  }

                  if (args.length != 1) {
                        plugin.log("Use §6/resetpassword <playername>");
                        return true;
                  }

                  if (args.length == 1) {
                        Player toReset = Bukkit.getPlayerExact(args[0]);

                        if (toReset == null) {
                              plugin.log("We could not find the player " + args[0] + " !");
                              return true;
                        }

                        ConfigController.getUserFile(toReset.getUniqueId().toString()).delete();
                        toReset.kickPlayer(ChatColor.GOLD + "Your password has been reset");

                        plugin.users.remove(toReset.getUniqueId().toString());

                        plugin.log("You reset the password for " + toReset.getName() + " !");
                  }
            }

            return false;
      }

}
