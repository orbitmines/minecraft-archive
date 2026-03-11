package fadidev.orbitmines.creative.cmd;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.regions.Region;
import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.WorldUtils;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 10-9-2016.
 */
public class WorldEditCommand extends Command {

    WorldEdit worldEdit;
    String[] alias = { "//set", "//walls", "//line", "//replace" };

    public WorldEditCommand(){
        worldEdit = WorldEdit.getInstance();
    }

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] strings) {

    }

    @Override
    public boolean dispatchCancelled(OMPlayer omPlayer, String[] a) {
        CreativePlayer omp = (CreativePlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasWEAccess(a[0])){
            try{
                if(!omp.isInPvPPlot()){
                    Region s = worldEdit.getSession(p.getName()).getSelection(worldEdit.getSession(p.getName()).getSelectionWorld());
                    World w = Bukkit.getWorld(s.getWorld().getName());
                    int x1 = s.getMinimumPoint().getBlockX();
                    int y1 = s.getMinimumPoint().getBlockY();
                    int z1 = s.getMinimumPoint().getBlockZ();
                    int x2 = s.getMaximumPoint().getBlockX();
                    int y2 = s.getMaximumPoint().getBlockY();
                    int z2 = s.getMaximumPoint().getBlockZ();

                    Location l1 = new Location(w, x1, y1, z1);
                    Location l2 = new Location(w, x2, y2, z2);

                    boolean canUse = true;
                    for(Block b : WorldUtils.getBlocksBetween(l1, l2)){
                        if(!omp.isOnPlot(b.getLocation())) {
                            canUse = false;
                            break;
                        }
                    }

                    if(!canUse){
                        p.sendMessage(CreativeMessages.CMD_WE_OUTSIDE_PLOT.get(omp));
                        return true;
                    }
                    else{
                        return false;
                    }
                }
                else{
                    p.sendMessage(CreativeMessages.CMD_WE_IN_PVP_PLOT.get(omp));
                    return true;
                }
            }catch(Exception ex){
                return false;
            }
        }
        else{
            p.sendMessage(CreativeMessages.CMD_WE_BUY_AT_SHOP.get(omp));
            return true;
        }
    }
}
