package me.O_o_Fadi_o_O.OrbitMinesGalaxyGenerator;

import me.O_o_Fadi_o_O.OrbitMinesGalaxyGenerator.managers.GalaxyGenerator;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Start extends JavaPlugin {

	private static Start plugin;
	
	public ChunkGenerator getDefaultWorldGenerator(String w, String id){
		return new GalaxyGenerator();
	}
	
	public void onEnable(){
		plugin = this;
	}
	
	public void onDisable(){
		
	}
	
	public static Start getInstance(){
		return plugin;
	}
}
