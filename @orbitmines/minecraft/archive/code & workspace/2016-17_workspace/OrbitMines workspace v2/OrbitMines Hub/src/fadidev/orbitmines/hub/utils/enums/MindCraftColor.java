package fadidev.orbitmines.hub.utils.enums;

/**
 * Created by Fadi on 10-9-2016.
 */
public enum MindCraftColor {

    BLUE("§1§lBlue", 11),
    YELLOW("§e§lYellow", 4),
    GREEN("§a§lGreen", 5),
    RED("§c§lRed", 14),
    LIGHT_BLUE("§b§lLight Blue", 3),
    ORANGE("§6§lOrange", 1);

    private String name;
    private byte data;

    MindCraftColor(String name, int data){
        this.name = name;
        this.data = (byte) data;
    }

    public String getName() {
        return name;
    }

    public byte getData() {
        return data;
    }
}
