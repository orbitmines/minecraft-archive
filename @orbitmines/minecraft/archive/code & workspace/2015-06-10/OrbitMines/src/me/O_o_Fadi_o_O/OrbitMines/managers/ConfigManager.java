package me.O_o_Fadi_o_O.OrbitMines.managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.utils.ServerData;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
 
public class ConfigManager {
 
	private ConfigManager(){}
 
	static ConfigManager instance = new ConfigManager();
 
	public static ConfigManager getInstance(){
		return instance;
	}
 
	static Start start = Start.getInstance();
	
	public static FileConfiguration config;
	static File cfile;
	
	public static FileConfiguration plots;
	static File pfile;
	
	public static FileConfiguration playerdata;
	static File pdfile;

	public static void setup(){
	 
		if(!start.getDataFolder().exists()){
			start.getDataFolder().mkdir();
		}

		cfile = new File(start.getDataFolder(), "config.yml");
		
		if(!cfile.exists()){
			try{
				Files.copy(start.getResource("config.yml"), cfile.toPath());
			}
			catch(IOException ex){
				Bukkit.getConsoleSender().sendMessage("[OrbitMines] Error while creating config.yml");
			}
		}

		config = YamlConfiguration.loadConfiguration(cfile);
		
		if(ServerData.isServer(Server.CREATIVE)){
			pfile = new File(start.getDataFolder(), "plots.yml");
			
			if(!pfile.exists()){
				try{
					Files.copy(start.getResource("plots.yml"), pfile.toPath());
				}
				catch(IOException ex){
					Bukkit.getConsoleSender().sendMessage("[OrbitMines] Error while creating plots.yml");
				}
			}

			plots = YamlConfiguration.loadConfiguration(pfile);
		}
		
		if(ServerData.isServer(Server.CREATIVE)){
			pdfile = new File(start.getDataFolder(), "playerdata.yml");
			
			if(!pdfile.exists()){
				try{
					Files.copy(start.getResource("playerdata.yml"), pdfile.toPath());
				}
				catch(IOException ex){
					Bukkit.getConsoleSender().sendMessage("[OrbitMines] Error while creating playerdata.yml");
				}
			}

			playerdata = YamlConfiguration.loadConfiguration(pdfile);
		}
	}
	
	public FileConfiguration getConfig(){
		return config;
	}
	 
	public static void saveConfig(){
		try{
			config.save(cfile);
		}
		catch(IOException ex){
			Bukkit.getConsoleSender().sendMessage("[OrbitMines] Error while saving config.yml");
		}
	}
	
	public static void reloadConfig(){
		config = YamlConfiguration.loadConfiguration(cfile);
	}
	
	public FileConfiguration getPlots(){
		return plots;
	}
	 
	public static void savePlots(){
		try{
			plots.save(pfile);
		}
		catch(IOException ex){
			Bukkit.getConsoleSender().sendMessage("[OrbitMines] Error while saving plots.yml");
		}
	}
	
	public static void reloadPlots(){
		plots = YamlConfiguration.loadConfiguration(pfile);
	}
	
	public FileConfiguration getPlayerData(){
		return playerdata;
	}
	 
	public static void savePlayerData(){
		try{
			playerdata.save(pdfile);
		}
		catch(IOException ex){
			Bukkit.getConsoleSender().sendMessage("[OrbitMines] Error while saving playerdata.yml");
		}
	}
	
	public static void reloadPlayerData(){
		playerdata = YamlConfiguration.loadConfiguration(pdfile);
	}
	 
	public PluginDescriptionFile getDesc(){
		return start.getDescription();
	}
}
