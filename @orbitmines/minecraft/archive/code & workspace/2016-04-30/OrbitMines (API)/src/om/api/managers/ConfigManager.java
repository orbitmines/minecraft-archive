package om.api.managers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import om.api.API;
import om.api.utils.enums.Config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
 
public class ConfigManager {
 
	private API api;
	
	private Map<Config, FileConfiguration> configs;
	private Map<Config, File> files;
	
	public ConfigManager(){
		api = API.getInstance();
		configs = new HashMap<Config, FileConfiguration>();
		files = new HashMap<Config, File>();
	}

	public void setup(Config... configs){
		if(!api.getDataFolder().exists()){
			api.getDataFolder().mkdir();
		}
		
		for(Config config : configs){
			File f = new File(api.getDataFolder(), config.getFileName());
			files.put(config, f);
			
			if(!f.exists()){
				try{
					Files.copy(api.getResource(config.getFileName()), f.toPath());
				}
				catch(IOException ex){
					Bukkit.getConsoleSender().sendMessage("[OrbitMines API] Error while creating " + config.getFileName());
				}
			}

			this.configs.put(config, YamlConfiguration.loadConfiguration(f));
		}
	}
	
	public FileConfiguration get(Config config){
		return configs.get(config);
	}
	 
	public void save(Config config){
		try{
			get(config).save(files.get(config));
		}
		catch(IOException ex){
			Bukkit.getConsoleSender().sendMessage("[OrbitMines API] Error while saving " + config.getFileName());
		}
	}
	
	public void reload(Config config){
		configs.put(config, YamlConfiguration.loadConfiguration(files.get(config)));
	}
}
