package fadidev.orbitmines.hub.managers;

import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.hub.OrbitMinesHub;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HubConfigManager {
 
	private OrbitMinesHub hub;
	
	private Map<String, FileConfiguration> configs;
	private Map<String, File> files;
	
	public HubConfigManager(){
		hub = OrbitMinesHub.getHub();
		configs = new HashMap<>();
		files = new HashMap<>();
	}

	public void setup(String... configs){
		if(!hub.getDataFolder().exists())
			hub.getDataFolder().mkdir();
		
		for(String config : configs){
			File f = new File(hub.getDataFolder(), config + ".yml");
			files.put(config, f);
			
			if(!f.exists()){
				try{
					Files.copy(hub.getResource(config + ".yml"), f.toPath());
				}
				catch(IOException ex){
					Utils.consoleWarning("Error while creating " + config + ".yml");
				}
			}

			this.configs.put(config, YamlConfiguration.loadConfiguration(f));
		}
	}
	
	public FileConfiguration get(String config){
		return configs.get(config);
	}
	 
	public void save(String config){
		try{
			get(config).save(files.get(config));
		}
		catch(IOException ex){
			Utils.consoleWarning("Error while saving " + config + ".yml");
		}
	}
	
	public void reload(String config){
		configs.put(config, YamlConfiguration.loadConfiguration(files.get(config)));
	}
}
