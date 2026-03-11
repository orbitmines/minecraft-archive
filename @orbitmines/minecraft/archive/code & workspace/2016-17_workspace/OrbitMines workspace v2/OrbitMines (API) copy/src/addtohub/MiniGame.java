package addtohub;

import org.bukkit.Material;

public enum MiniGame {

	//hub

	SURVIVAL_GAMES(Material.IRON_SWORD, "Survival Games", "SG", "SG", 24),
	ULTRA_HARD_CORE(Material.GOLDEN_APPLE, "UHC", "UHC", "UHC", 50),
	SKYWARS(Material.GRASS, "Skywars", "SW", "Skywars", 8),
	CHICKEN_FIGHT(Material.FEATHER, "Chicken Fight", "CF", "CF", 16),
	GHOST_ATTACK(Material.SKULL_ITEM, "Ghost Attack", "GA", "GA", 8),
	SPLEEF(Material.DIAMOND_SPADE, "Spleef", "SP", "Spleef", 16),
	SPLATCRAFT(Material.INK_SACK, "Splatcraft", "SC", "Splatcraft", 16);
	
	private Material material;
	private String name;
	private String shortname;
	private String signname;
	private int maxplayers;
	
	MiniGame(Material material, String name, String shortname, String signname, int maxplayers){
		this.material = material;
		this.name = name;
		this.shortname = shortname;
		this.signname = signname;
		this.maxplayers = maxplayers;
		
	}
	
	public Material getMaterial(){
		return material;
	}
	
	public short getDurability(){
		switch(this){
			case GHOST_ATTACK:
				return 1;
			default:
				return 0;
		}
	}
	
	public String getName(){
		return name;
	}
	
	public String getShortName(){
		return shortname;
	}
	
	public String getSignName(){
		return signname;
	}
	
	public int getMaxPlayers(){
		return maxplayers;
	}
	
	public static MiniGame fromShortName(String shortname){
		for(MiniGame type : MiniGame.values()){
			if(type.getShortName().equals(shortname)){
				return type;
			}
		}
		return null;
	}
}

