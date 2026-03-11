package om.api.utils.enums;

public enum Config {

	CONFIG(),
	CHESTSHOPS,
	ISLANDS,
	PLAYERDATA,
	PLOTS,
	SHOPS,
	WARPS,
	VOTERS;
	
	public String getFileName(){
		return toString().toLowerCase() + ".yml";
	}
}
