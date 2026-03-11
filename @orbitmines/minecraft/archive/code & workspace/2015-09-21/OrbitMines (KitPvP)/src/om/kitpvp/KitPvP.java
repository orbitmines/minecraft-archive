package om.kitpvp;

import om.api.API;
import om.api.utils.enums.Server;

import org.bukkit.plugin.java.JavaPlugin;

public class KitPvP extends JavaPlugin {

	private API api;
	
	public void onEnable() {
		api = new API(Server.KITPVP);
	}
	
	public API getAPI() {
		return api;
	}
}
