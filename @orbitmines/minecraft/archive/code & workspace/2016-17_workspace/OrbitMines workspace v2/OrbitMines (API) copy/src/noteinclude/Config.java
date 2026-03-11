package noteinclude;

public enum Config {

	CONFIG,
	CHESTSHOPS,
	ISLANDS,
	PLAYERDATA,
	PLOTS,
	SHOPS,
	WARPS,
	VOTERS,
	SETTINGS;
	
	public String getFileName(){
		return toString().toLowerCase() + ".yml";
	}
}
