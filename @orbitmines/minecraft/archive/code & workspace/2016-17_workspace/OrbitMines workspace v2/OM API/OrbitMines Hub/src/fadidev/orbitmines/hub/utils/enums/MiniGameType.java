package fadidev.orbitmines.hub.utils.enums;

import org.bukkit.Material;

/**
 * Created by Fadi on 8-9-2016.
 */
public enum MiniGameType {

    SURVIVAL_GAMES(Material.IRON_SWORD, "Survival Games", "SG", "SG", 24),
    ULTRA_HARD_CORE(Material.GOLDEN_APPLE, "UHC", "UHC", "UHC", 50),
    SKYWARS(Material.GRASS, "Skywars", "SW", "Skywars", 8),
    CHICKEN_FIGHT(Material.FEATHER, "Chicken Fight", "CF", "CF", 16),
    GHOST_ATTACK(Material.SKULL_ITEM, "Ghost Attack", "GA", "GA", 8),
    SPLEEF(Material.DIAMOND_SPADE, "Spleef", "SP", "Spleef", 16),
    SPLATCRAFT(Material.INK_SACK, "Splatcraft", "SC", "Splatcraft", 16);

    private Material material;
    private String name;
    private String shortName;
    private String signName;
    private int maxPlayers;

    MiniGameType(Material material, String name, String shortName, String signName, int maxPlayers){
        this.material = material;
        this.name = name;
        this.shortName = shortName;
        this.signName = signName;
        this.maxPlayers = maxPlayers;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public String getSignName() {
        return signName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public short getDurability(){
        switch(this){
            case GHOST_ATTACK:
                return 1;
            default:
                return 0;
        }
    }

    public static MiniGameType fromShortName(String shortName){
        for(MiniGameType type : MiniGameType.values()){
            if(type.getShortName().equals(shortName)){
                return type;
            }
        }
        return null;
    }
}
