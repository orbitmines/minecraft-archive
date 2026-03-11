package fadidev.orbitmines.api.utils.enums.perks;

import org.bukkit.Material;

/**
 * Created by Fadi on 10-10-2016.
 */
public enum Color {
    
    AQUA("§b", "§bLight Blue", "LightBlue", 12, org.bukkit.Color.AQUA),
    BLACK("§0", "§0Black", "Black", 0, org.bukkit.Color.BLACK),
    BLUE("§9", "§9Blue", "Blue", 4, org.bukkit.Color.BLUE),
    FUCHSIA("§d", "§dPink", "Pink", 9, org.bukkit.Color.FUCHSIA),
    GRAY("§8", "§8Dark Gray", "Gray", 8, org.bukkit.Color.GRAY),
    GREEN("§2", "§2Green", "Green", 2, org.bukkit.Color.GREEN),
    LIME("§a", "§aLight Green", "LightGreen", 10, org.bukkit.Color.LIME),
    MAROON("§4", "§4Dark Red", "DarkRed", 0, org.bukkit.Color.MAROON),
    NAVY("§1", "§1Dark Blue", "DarkBlue", 12, org.bukkit.Color.NAVY),
    ORANGE("§6", "§6Orange", "Orange", 14, org.bukkit.Color.ORANGE),
    PURPLE("§5", "§5Purple", "Purple", 5, org.bukkit.Color.PURPLE),
    RED("§c", "§cRed", "Red", 1, org.bukkit.Color.RED),
    SILVER("§7", "§7Gray", "LightGray", 7, org.bukkit.Color.SILVER),
    TEAL("§3", "§3Cyan", "Cyan", 6, org.bukkit.Color.TEAL),
    WHITE("§f", "§fWhite", "White", 15, org.bukkit.Color.WHITE),
    YELLOW("§e", "§eYellow", "Yellow", 11, org.bukkit.Color.YELLOW),
    ELYTRA("§7", "§7Elytra", "Elytra", 0, null);

    private String color;
    private String name;
    private String databaseName;
    private short durability;
    private org.bukkit.Color bukkitColor;

    Color(String color, String name, String databaseName, int durability, org.bukkit.Color bukkitColor){
        this.color = color;
        this.name = name;
        this.databaseName = databaseName;
        this.durability = (short) durability;
        this.bukkitColor = bukkitColor;
    }

    public String getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public short getDurability() {
        return durability;
    }

    public org.bukkit.Color getBukkitColor() {
        return bukkitColor;
    }

    public Material getMaterial(){
        return this == ELYTRA ? Material.ELYTRA: Material.LEATHER_CHESTPLATE;
    }
}
