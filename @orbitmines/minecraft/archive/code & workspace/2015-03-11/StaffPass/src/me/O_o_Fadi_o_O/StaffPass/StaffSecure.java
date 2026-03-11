/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.O_o_Fadi_o_O.StaffPass;

import java.util.HashMap;

import me.O_o_Fadi_o_O.StaffPass.commands.CmdExecutor;
import me.O_o_Fadi_o_O.StaffPass.controllers.ConfigController;
import me.O_o_Fadi_o_O.StaffPass.controllers.PlayerController;
import me.O_o_Fadi_o_O.StaffPass.listeners.PlayerListener;
import me.O_o_Fadi_o_O.StaffPass.struct.Config;
import me.O_o_Fadi_o_O.StaffPass.struct.StaffSecureUser;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Harry5573
 */
public class StaffSecure extends JavaPlugin {

      private static StaffSecure plugin;

      public static StaffSecure get() {
            return plugin;
      }

      public HashMap<String, StaffSecureUser> users = new HashMap<>();
      public Config configuration = null;

      @SuppressWarnings("deprecation")
      @Override
      public void onEnable() {
            plugin = this;
            try {

                  users.clear();

                  log("Loading plugin version " + this.getDescription().getVersion());

                  ConfigController.onEnable();

                  registerCommands();
                  registerListeners();

                  for (Player player : getServer().getOnlinePlayers()) {
                        PlayerController.handleLogin(player);
                  }

            } catch (Exception ex) {
                  log("Error while enabling staffsecure version " + this.getDescription().getVersion());
            }
      }

      @Override
      public void onDisable() {
            log("Stopping plugin version " + this.getDescription().getVersion());
      }

      public void log(String message) {
            getLogger().info(message);
      }

      private void registerCommands() {
            getCommand("login").setExecutor(new CmdExecutor());
            getCommand("staffsecure").setExecutor(new CmdExecutor());
            getCommand("password").setExecutor(new CmdExecutor());
            getCommand("resetpassword").setExecutor(new CmdExecutor());
      }

      private void registerListeners() {
            PluginManager pm = getServer().getPluginManager();

            pm.registerEvents(new PlayerListener(), this);
      }

}
