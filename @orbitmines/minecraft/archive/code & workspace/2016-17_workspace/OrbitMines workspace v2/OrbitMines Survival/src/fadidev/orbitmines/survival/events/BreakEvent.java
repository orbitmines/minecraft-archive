package fadidev.orbitmines.survival.events;

import fadidev.orbitmines.survival.OrbitMinesSurvival;
import fadidev.orbitmines.survival.handlers.Region;
import fadidev.orbitmines.survival.handlers.ShopSign;
import fadidev.orbitmines.survival.handlers.SurvivalMessages;
import fadidev.orbitmines.survival.handlers.SurvivalPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Created by Fadi-Laptop on 16-9-2016.
 */
public class BreakEvent implements Listener {

    private OrbitMinesSurvival survival;

    public BreakEvent(){
        survival = OrbitMinesSurvival.getSurvival();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player p = e.getPlayer();
        SurvivalPlayer omp = SurvivalPlayer.getSurvivalPlayer(p);

        if(omp.isLoaded()){
            if(omp.isOpMode())
                return;

            if(p.getWorld().getName().equals(survival.getLobby().getName())){
                e.setCancelled(true);
            }
            else if(p.getWorld().getName().equals(survival.getSurvivalWorld().getName())){
                if(Region.isInRegion(omp, e.getBlock().getLocation()))
                    e.setCancelled(true);

                if(e.isCancelled())
                    return;

                ShopSign sign = ShopSign.getShopSign(e.getBlock().getLocation());

                if(sign != null && omp.getShopSigns().contains(sign)){
                    sign.delete();
                    p.sendMessage(SurvivalMessages.REMOVE_CHESTSHOP.get(omp));
                }
            }
        }
        else{
            omp.notLoaded();
            e.setCancelled(true);
        }
    }
}
