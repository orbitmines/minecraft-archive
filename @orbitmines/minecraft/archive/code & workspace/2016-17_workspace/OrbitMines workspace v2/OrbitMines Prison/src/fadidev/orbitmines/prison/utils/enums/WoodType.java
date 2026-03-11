package fadidev.orbitmines.prison.utils.enums;

import org.bukkit.Material;

/**
 * Created by Fadi on 27-9-2016.
 */
public enum WoodType {

    OAK(Material.LOG, 0, 12, "Oak"),
    SPRUCE(Material.LOG, 1, 13, "Spruce"),
    BIRCH(Material.LOG, 2, 14, "Birch"),
    JUNGLE(Material.LOG, 3, 15, "Jungle"),
    ACACIA(Material.LOG_2, 0, 12, "Acacia"),
    DARK_OAK(Material.LOG_2, 1, 13, "Dark Oak");

    private Material material;
    private short durability;
    private byte data;
    private String name;

    WoodType(Material material, int durability, int data, String name){
        this.material = material;
        this.durability = (short) durability;
        this.data = (byte) data;
        this.name = name;
    }

    public Material getMaterial() {
        return material;
    }

    public short getDurability() {
        return durability;
    }

    public byte getData() {
        return data;
    }

    public String getName() {
        return name;
    }

    public short getPlankDurability() {
        return (short) ordinal();
    }

    public static WoodType fromDurability(Material material, int durability){
        for(WoodType type : WoodType.values()){
            if(type.getMaterial() == material && type.getDurability() == durability)
                return type;
        }
        return null;
    }

    public static WoodType fromPlankDurability(int durability){
        for(WoodType type : WoodType.values()){
            if(type.getPlankDurability() == durability)
                return type;
        }
        return null;
    }
}
