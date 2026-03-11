package me.O_o_Fadi_o_O.OrbitMinesBungee;

import me.O_o_Fadi_o_O.OrbitMinesBungee.events.PingEvent;
import me.O_o_Fadi_o_O.OrbitMinesBungee.events.PlayerChatEvent;
import me.O_o_Fadi_o_O.OrbitMinesBungee.events.PlayerConnectEvent;
import me.O_o_Fadi_o_O.OrbitMinesBungee.events.PlayerDisconnectEvent;
import me.O_o_Fadi_o_O.OrbitMinesBungee.events.PlayerTabCompleteEvent;
import me.O_o_Fadi_o_O.OrbitMinesBungee.passes.Passwords;
import me.O_o_Fadi_o_O.OrbitMinesBungee.runnables.Runnables;
import me.O_o_Fadi_o_O.OrbitMinesBungee.utils.Database;
import me.O_o_Fadi_o_O.OrbitMinesBungee.utils.ServerData;
import net.md_5.bungee.api.plugin.Plugin;

public class Start extends Plugin {
	
	public static Start plugin;
	
	public void onEnable(){
		plugin = this;

		new Database("sql-3.verygames.net", 3306, "minecraft4268", "minecraft4268", "hnfxy5h48");
		new ServerData.BungeeServer();
		
		new Runnables().startRunnables(this);
		new Passwords().register();
		regiterEvents();
	}
	
	public static Start getInstance(){
		return plugin;
	}
	
	private void regiterEvents(){
		this.getProxy().getPluginManager().registerListener(this, new PingEvent());
		this.getProxy().getPluginManager().registerListener(this, new PlayerChatEvent());
		this.getProxy().getPluginManager().registerListener(this, new PlayerConnectEvent());
		this.getProxy().getPluginManager().registerListener(this, new PlayerDisconnectEvent());
		this.getProxy().getPluginManager().registerListener(this, new PlayerTabCompleteEvent());
	}
}
