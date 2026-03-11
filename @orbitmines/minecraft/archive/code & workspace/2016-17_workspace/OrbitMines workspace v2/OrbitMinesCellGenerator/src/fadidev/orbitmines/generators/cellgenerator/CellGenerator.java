package fadidev.orbitmines.generators.cellgenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class CellGenerator extends JavaPlugin {

	private static CellGenerator plugin;
	
	public ChunkGenerator getDefaultWorldGenerator(String w, String id){
		return new PlotGenerator();
	}
	
	public void onEnable(){
		plugin = this;
	}
	
	public void onDisable(){
		
	}
	
	public static CellGenerator getInstance(){
		return plugin;
	}
}
