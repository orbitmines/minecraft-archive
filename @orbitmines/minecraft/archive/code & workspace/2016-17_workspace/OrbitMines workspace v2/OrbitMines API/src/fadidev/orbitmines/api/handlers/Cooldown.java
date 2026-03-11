package fadidev.orbitmines.api.handlers;

import fadidev.orbitmines.api.handlers.chat.ActionBar;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fadi on 5-9-2016.
 */
public abstract class Cooldown {

    static Map<OMPlayer, Double> PREV_DOUBLE = new HashMap<>();

    private long cooldown;
    private String name;
    private String itemName;
    private Action action;

    public Cooldown(long cooldown, String name, String itemName, Action action){
        this.cooldown = cooldown;
        this.name = name;
        this.itemName = itemName;
        this.action = action;
    }

    //To reduce time for certain players
    public abstract long getCooldown(OMPlayer omp);

    public long getCooldown() {
        return cooldown;
    }

    public String getName() {
        return name;
    }

    public String getItemName() {
        return itemName;
    }

    public Action getAction() {
        return action;
    }

    public void updateActionBar(OMPlayer omp){
        ItemStack item = omp.getPlayer().getItemInHand();

        if(item == null || item.getItemMeta() == null || item.getItemMeta().getDisplayName() == null || getName() == null)
            return;


        boolean equals = item.getItemMeta().getDisplayName().endsWith(getName().replace("§l", "§n")) || item.getItemMeta().getDisplayName().endsWith(getItemName());

        if(equals && omp.onCooldown(this)){
            double cooldown = getCooldown(omp) / 1000;
            double left = cooldown - ((System.currentTimeMillis() - omp.getCooldown(this)) / 1000);

            String format = "ss,S";
            if(left < 10)
                format = "s,S";
            if(left > 60)
                format = "m: ss,S";
            if(left > 600)
                format = "mm: ss,S";

            String leftString = (new SimpleDateFormat(format).format(new Date(getCooldown(omp) - (System.currentTimeMillis() - omp.getCooldown(this))))).replace(":", "m");
            leftString = leftString.substring(0, leftString.indexOf(",") +2) + "s";

            String bar = "";
            if(leftString.contains("m"))
                left = (Integer.parseInt(leftString.substring(0, leftString.indexOf("m"))) * 60) + Integer.parseInt(leftString.substring(leftString.indexOf("m") +2, leftString.indexOf(","))) + (Double.parseDouble(leftString.substring(leftString.indexOf(",") +1, leftString.indexOf(",") +2)) / 10);
            else
                left = Integer.parseInt(leftString.substring(0, leftString.indexOf(","))) + (Double.parseDouble(leftString.substring(leftString.indexOf(",") +1, leftString.indexOf(",") +2)) / 10);
            
            double red = 40 - (((left / cooldown) * 100) / 2.5) + 2;

            /*
             * Fix incorrect numbers;
             */
            if(PREV_DOUBLE.containsKey(omp) && (PREV_DOUBLE.get(omp) - red) >= 1.1)
                red = PREV_DOUBLE.get(omp);
            else
                PREV_DOUBLE.put(omp, red);

            bar += "§a|||||||||||||||||||||||||||||||||||||||| §8| §f" + leftString + " §8| " + getName();
            bar = bar.substring(0, (int) red) + "§c" + bar.substring((int) red);

            ActionBar actionbar = new ActionBar(omp.getPlayer(), bar, 20);
            actionbar.send();
        }
        else{
            if(equals){
                ActionBar actionbar = new ActionBar(omp.getPlayer(), getAction().getActionName(omp) + " §8| " + getName(), 20);
                actionbar.send();
            }
        }
    }

    public enum Action {

        OTHER,
        LEFT_CLICK,
        RIGHT_CLICK;

        public String getActionName(OMPlayer omp){
            switch(this){
                case LEFT_CLICK:
                    return Messages.COOLDOWN_LEFT_CLICK.get(omp);
                case RIGHT_CLICK:
                    return Messages.COOLDOWN_RIGHT_CLICK.get(omp);
                default:
                    return null;
            }
        }
    }
}
