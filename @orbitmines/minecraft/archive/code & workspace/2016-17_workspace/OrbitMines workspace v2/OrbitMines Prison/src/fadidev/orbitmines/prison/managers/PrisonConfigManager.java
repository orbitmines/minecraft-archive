package fadidev.orbitmines.prison.managers;

import fadidev.orbitmines.api.utils.Utils;
import fadidev.orbitmines.prison.OrbitMinesPrison;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class PrisonConfigManager {
 
	private OrbitMinesPrison prison;
	
	private Map<String, FileConfiguration> configs;
	private Map<String, File> files;
	
	public PrisonConfigManager(){
		prison = OrbitMinesPrison.getPrison();
		configs = new HashMap<>();
		files = new HashMap<>();
	}

	public void setup(String... configs){
		if(!prison.getDataFolder().exists())
			prison.getDataFolder().mkdir();
		
		for(String config : configs){
			File f = new File(prison.getDataFolder(), config + ".yml");
			files.put(config, f);
			
			if(!f.exists()){
				try{
					Files.copy(prison.getResource(config + ".yml"), f.toPath());
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
