package fadidev.orbitmines.minigames.utils.enums;

import fadidev.orbitmines.api.handlers.firework.FireWork;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

/**
 * Created by Fadi on 8-9-2016.
 */

public enum Rarity {

    COMMON("§a§lCommon", 25, "§a", 25, Color.GREEN, Color.LIME, FireworkEffect.Type.BALL),
    RARE("§6§lRare", 50, "§6", 5, Color.ORANGE, Color.YELLOW, FireworkEffect.Type.BALL),
    LEGENDARY("§c§lLegendary", 100, "§c", 1, Color.MAROON, Color.RED, FireworkEffect.Type.BALL_LARGE);

    private String name;
    private int refund;
    private String color;
    private int amount;
    private Color color1;
    private Color color2;
    private FireworkEffect.Type type;

    Rarity(String name, int refund, String color, int amount, Color color1, Color color2, FireworkEffect.Type type){
        this.name = name;
        this.refund = refund;
        this.color = color;
        this.amount = amount;
        this.color1 = color1;
        this.color2 = color2;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getRefund() {
        return refund;
    }

    public String getColor() {
        return color;
    }

    public int getAmount() {
        return amount;
    }

    public Color getColor1() {
        return color1;
    }

    public Color getColor2() {
        return color2;
    }

    public FireworkEffect.Type getType() {
        return type;
    }

    public void firework(Location l){
        FireWork fw = new FireWork(l);

        fw.getBuilder().withColor(getColor1());
        fw.getBuilder().withColor(getColor2());
        fw.getBuilder().with(getType());
        fw.getBuilder().withTrail();

        fw.build();
        fw.explode();
    }
}
