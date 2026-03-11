package fadidev.spigotbridge.managers;

import fadidev.spigotbridge.SpigotBridge;
import fadidev.spigotbridge.utils.Utils;
import fadidev.spigotbridge.utils.enums.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;


public class ConfigManager {
 
	private SpigotBridge msg;
	
	private Map<Config, FileConfiguration> configs;
	private Map<Config, File> files;
	
	public ConfigManager(){
		msg = SpigotBridge.getInstance();
		configs = new HashMap<>();
		files = new HashMap<>();
	}

	public void setup(Config... configs){
		if(!msg.getDataFolder().exists()){
			msg.getDataFolder().mkdir();
		}
		
		File fD = msg.getDataFolder();

		if(!fD.exists()){
			fD.mkdir();
		}
		
		for(Config config : configs){
			File f = new File(msg.getDataFolder(), config.getFileName());
			files.put(config, f);
			
			if(!f.exists()){
				copyFile(config.getFileName(), f.toPath());
			}

			this.configs.put(config, YamlConfiguration.loadConfiguration(f));
		}
	}
	
	public FileConfiguration get(Config config){
		return configs.get(config);
	}
	 
	public void save(Config config){
		try{
			this.configs.get(config).save(new File(msg.getDataFolder(), config.getFileName()));
		}
		catch(IOException ex){
			Utils.warnConsole("Error while saving " + config.getFileName());
			ex.printStackTrace();
		}
	}
	
	public void reload(Config config){
		this.configs.put(config, YamlConfiguration.loadConfiguration(this.files.get(config)));
	}
	
	private void copyFile(String filename, Path path){
		try{
			Files.copy(msg.getResource(filename), path);
		}catch(IOException ex){
			Utils.warnConsole("Error while creating " + filename);
			ex.printStackTrace();
		}
	}
}
