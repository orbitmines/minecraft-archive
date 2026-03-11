package addtohub;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 6-9-2016.
 */
public class Click {

    @Override
    protected void checkCancelItems(ItemStack bitem) {

    }

    @Override
    protected void checkConfirmItems(ItemStack bitem, ItemStack pitem) {
        KitPvPPlayer omp = KitPvPPlayer.getKitPvPPlayer(p);

        else{

            for(Booster booster : Booster.values()){
                if(bitem.getItemMeta().getDisplayName().equals(booster.getName())){
                    //TODO IF NOT ACTIVE
                    omp.removeVIPPoints(booster.getPrice());

                    p.closeInventory();

                    kitpvp.setBooster(new ActiveBooster(booster, p.getName(), 30, 0));
                    Bukkit.broadcastMessage("" + omp.getName() + " ?7activated a ?aBooster?7! (?ax" + booster.getMultiplier() + "?7)");
                    return;
                }
            }
        }
    }
}
