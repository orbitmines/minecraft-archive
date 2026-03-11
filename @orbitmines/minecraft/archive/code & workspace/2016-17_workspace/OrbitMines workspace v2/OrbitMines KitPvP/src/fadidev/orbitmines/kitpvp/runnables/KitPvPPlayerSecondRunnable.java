package fadidev.orbitmines.kitpvp.runnables;

import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.api.utils.ItemUtils;
import fadidev.orbitmines.kitpvp.OrbitMinesKitPvP;
import fadidev.orbitmines.kitpvp.handlers.KitPvPMap;
import fadidev.orbitmines.kitpvp.handlers.KitPvPPlayer;
import fadidev.orbitmines.kitpvp.utils.enums.KitPvPKit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Fadi on 10-9-2016.
 */
public class KitPvPPlayerSecondRunnable extends PlayerSecondRunnable {

    private OrbitMinesKitPvP kitPvP;
    private Location voteSign;

    public KitPvPPlayerSecondRunnable() {
        kitPvP = OrbitMinesKitPvP.getKitPvP();
        voteSign = new Location(kitPvP.getLobby(), -21, 77, 0);
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        KitPvPPlayer omp = (KitPvPPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(!p.getWorld().getName().equals(kitPvP.getLobby().getName())){
            if(!omp.isOpMode() && omp.getKitSelected() == null && omp.isPlayer())
                p.teleport(kitPvP.getSpawn());

            if(omp.isSpectator()){
                if(p.getOpenInventory().getTopInventory() != null && p.getOpenInventory().getTopInventory().getName() != null && p.getOpenInventory().getTopInventory().getName().equals(kitPvP.getTeleporterInv().getInventory().getName()))
                    kitPvP.getTeleporterInv().open(p);

                p.setGameMode(GameMode.SPECTATOR);
            }
            else{
                KitPvPKit kit = omp.getKitSelected();
                if(kit != null && kit.getNextArrow() != 0){
                    int arrows = 0;
                    for(ItemStack i : p.getInventory().getContents()){
                        if(i != null && i.getType() == Material.ARROW)
                            arrows++;
                    }

                    if(arrows != kit.getMaxArrows(omp.getKitLevelSelected()) && p.getInventory().contains(Material.BOW)){
                        if(omp.getArrowSeconds() == -1)
                            omp.setArrowSeconds(kit.getNextArrow());

                        omp.tickArrowTimer();
                        if(omp.getArrowSeconds() == 0){
                            p.getInventory().addItem(ItemUtils.itemstack(Material.ARROW, 1, "§b§l" + kit.getName() + " §a§lLvL " + omp.getKitLevelSelected() + "§8 || §bArrow"));

                            omp.setArrowSeconds(kit.getNextArrow());
                        }
                    }
                }

                if(!omp.isOpMode())
                    p.setGameMode(GameMode.SURVIVAL);
            }
        }
        else{
            if(p.getLocation().getY() <= 50 || p.getLocation().distance(kitPvP.getSpawn()) >= 50){
                p.teleport(kitPvP.getSpawn());

                if(!omp.isOpMode())
                    p.setGameMode(GameMode.SURVIVAL);
            }

            if(p.getLocation().distance(voteSign) > 16)
                return;

            for(KitPvPMap map : kitPvP.getMaps()){
                map.updateVoteSign(omp);
            }

            String[] lines = { "", "§lMap Reset", kitPvP.getCurrentMap().getMinutes() + "m" + kitPvP.getCurrentMap().getSeconds() + "s", "" };
            omp.getPlayer().sendSignChange(voteSign, lines);
        }
    }

    @Override
    protected void giveLobbyItems(OMPlayer omPlayer) {

    }
}
