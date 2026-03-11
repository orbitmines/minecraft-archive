package fadidev.spigotspleef.managers;

import fadidev.spigotspleef.SpigotSpleef;
import fadidev.spigotspleef.utils.Utils;
import fadidev.spigotspleef.utils.enums.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
 
	private SpigotSpleef ss;
	
	private Map<Config, FileConfiguration> configs;
	private Map<Config, File> files;
	
	public ConfigManager(){
		ss = SpigotSpleef.getInstance();
		configs = new HashMap<>();
		files = new HashMap<>();
	}

	public void setup(Config... configs){
		if(!ss.getDataFolder().exists()){
			ss.getDataFolder().mkdir();
		}
		
		for(Config config : configs){
			File f = new File(ss.getDataFolder(), config.getFileName());
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
			this.configs.get(config).save(this.files.get(config));
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
			Files.copy(ss.getResource(filename), path);
		}catch(IOException ex){
			Utils.warnConsole("Error while creating " + filename);
			ex.printStackTrace();
		}
	}
}
