package addtohub;

import org.bukkit.Material;

/**
 * Created by Fadi on 6-9-2016.
 */
public class InteractManager {
    private boolean handleCosmeticPerks(){
        if(item.getType() == Material.ENDER_CHEST && item.getItemMeta().getDisplayName().equals("§9§nCosmetic Perks")){
            e.setCancelled(true);
            omp.updateInventory();

            if(!omp.isInLapisParkour()){
                new CosmeticPerksInv().open(p);
            }

            return true;
        }
        return false;
    }

    /* Global Lobby Items */
    private boolean handleAchievements(){
        if(item.getType() == Material.EXP_BOTTLE && item.getItemMeta().getDisplayName().equals("§d§nAchievements")){
            e.setCancelled(true);
            omp.updateInventory();

            p.sendMessage("§a§oComing Soon...");
            // TODO ADD ACHIEVEMENTS
            return true;
        }
        return false;
    }

    private boolean handleMonsterEggs(){
        if(item != null && item.getType() == Material.MONSTER_EGG){
            e.setCancelled(true);
            omp.updateInventory();

            return true;
        }
        return false;
    }

    private boolean handleWrittenBook(){
        if(item.getType() != Material.WRITTEN_BOOK){
            e.setCancelled(true);
            return true;
        }
        return false;
    }

    private boolean handleServerSelector(){
        if(item.getType() == Material.ENDER_PEARL && item.getItemMeta().getDisplayName().equals("§3§nServer Selector")){
            e.setCancelled(true);
            omp.updateInventory();

            api.getServerSelector().open(p);

            return true;
        }
        return false;
    }
}
