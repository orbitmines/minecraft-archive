/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.O_o_Fadi_o_O.StaffPass.controllers;

import me.O_o_Fadi_o_O.StaffPass.StaffSecure;
import me.O_o_Fadi_o_O.StaffPass.struct.StaffSecureUser;
import me.O_o_Fadi_o_O.StaffPass.struct.StaffSecureUserConfig;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author devan_000
 */
public class PlayerController {

      private static final StaffSecure plugin = StaffSecure.get();

      public static StaffSecureUser handleLogin(Player player) {

            if (!player.hasPermission("staffsecure.staff")) {
                  return null;
            }

            if (!ConfigController.getUserFile(player.getUniqueId().toString()).exists()) {
                  ConfigController.createUserFile(player.getUniqueId().toString());
            }

            if (plugin.users.get(player.getUniqueId().toString()) == null) {
                  plugin.users.put(player.getUniqueId().toString(), getUser(player));
            }

            String previousIp = plugin.users.get(player.getUniqueId().toString()).config.playerIP;
            if (previousIp == null) {
                  previousIp = "0.0.0.0";
            }

            if (plugin.users.get(player.getUniqueId().toString()).config.encryptedPassword == null) {
                  player.sendMessage("§7Use §6/password <pass>");
            }

            plugin.users.get(player.getUniqueId().toString()).config.setIP(player.getAddress().toString().split(":")[0].replace("/", ""));

            boolean hasIpChanged = !player.getAddress().toString().split(":")[0].replace("/", "").equals(previousIp);
            if (hasIpChanged) {
                  player.sendMessage("Your IP has changed since the last time you logged in. We have logged you out.");
                  plugin.users.get(player.getUniqueId().toString()).config.setLoggedInToLastIP(false);
            }

            if (!hasIpChanged && plugin.users.get(player.getUniqueId().toString()).config.loggedIntoLastLoggedIp) {
                  plugin.users.get(player.getUniqueId().toString()).isLoggedIn = true;
                  return null;
            }

            if (hasIpChanged) {
                  plugin.users.get(player.getUniqueId().toString()).isLoggedIn = false;
                  if (plugin.users.get(player.getUniqueId().toString()).config.encryptedPassword != null) {
                        player.sendMessage("§7You need to login! §6/login <password>");
                  }
            }
            return plugin.users.get(player.getUniqueId().toString());
      }

      private static StaffSecureUser getUser(Player player) {
            return new StaffSecureUser(player.getName(), player.getUniqueId().toString(), new StaffSecureUserConfig(YamlConfiguration.loadConfiguration(ConfigController.getUserFile(player.getUniqueId().toString())), ConfigController.getUserFile(player.getUniqueId().toString())));
      }
}
