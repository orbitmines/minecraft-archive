package fadidev.orbitmines.creative.runnables;

import fadidev.orbitmines.api.handlers.Cooldowns;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.Title;
import fadidev.orbitmines.api.runnables.orbitmines.PlayerSecondRunnable;
import fadidev.orbitmines.creative.OrbitMinesCreative;
import fadidev.orbitmines.creative.handlers.CreativeMessages;
import fadidev.orbitmines.creative.handlers.CreativePlayer;
import fadidev.orbitmines.creative.handlers.Plot;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Created by Fadi on 14-9-2016.
 */
public class CreativePlayerSecondRunnable extends PlayerSecondRunnable {

    private OrbitMinesCreative creative;

    public CreativePlayerSecondRunnable(){
        creative = OrbitMinesCreative.getCreative();
    }

    @Override
    protected void run(OMPlayer omPlayer) {
        CreativePlayer omp = (CreativePlayer) omPlayer;
        Player p = omp.getPlayer();

        if(p.getWorld().getName().equals(creative.getLobby().getName())){
            if(p.getLocation().getY() <= 50 || p.getLocation().distance(creative.getSpawn()) >= 50 && !omp.isOpMode()){
                p.teleport(creative.getSpawn());
            }
        }

        if(omp.getCooldowns().containsKey(Cooldowns.TELEPORTING)){
            if(omp.onCooldown(Cooldowns.TELEPORTING)){
                int seconds = (int) ((Cooldowns.TELEPORTING.getCooldown(omp) /1000) - ((System.currentTimeMillis() - omp.getCooldown(Cooldowns.TELEPORTING)) / 1000));

                if(omp.getPvPPlot() != null){
                    Title t = new Title("", CreativeMessages.LEAVING_PLOT.get(omp, seconds + ""), 0, 40, 0);
                    t.send(p);
                }
                else{
                    Title t = new Title("", CreativeMessages.TELEPORTING_TO_SPAWN.get(omp, seconds + ""), 0, 40, 0);
                    t.send(p);
                }
            }
            else{
                omp.removeCooldown(Cooldowns.TELEPORTING);
                p.teleport(creative.getSpawn());

                if(omp.getPvPPlot() != null){
                    Title t = new Title("", CreativeMessages.LEFT_PLOT.get(omp), 0, 40, 20);
                    t.send(p);

                    List<CreativePlayer> cplayers = Plot.getPvPPlayers(omp.getPvPPlot());
                    for(CreativePlayer cplayer : cplayers){
                        cplayer.getPlayer().playSound(cplayer.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 5, 1);
                        cplayer.getPlayer().sendMessage(CreativeMessages.LEFT_PLOT_PLAYER.get(cplayer, p.getName(), omp.getPvPPlot().getPlotId() + "", (cplayers.size() -1) + "", omp.getPvPPlot().getPvPMaxPlayers() + ""));
                    }

                    omp.setKitSelected(null);
                    omp.setPvPPlot(null);
                    omp.clearInventory();
                }
                else{
                    Title t = new Title("", CreativeMessages.TELEPORTED_TO_SPAWN.get(omp), 0, 40, 20);
                    t.send(p);
                }
            }
        }
        
        if(omp.getPvPPlot() != null){
            p.setGameMode(GameMode.SURVIVAL);

            if(omp.getKitSelected() != null){
                for(CreativePlayer player : creative.getCreativePlayers()){
                    if(player.getPvPPlot() == null || player.getPvPPlot().getPlotId() != omp.getPvPPlot().getPlotId() || player.getPvPPlot().getPlotId() == omp.getPvPPlot().getPlotId() && player.getKitSelected() == null)
                        p.hidePlayer(player.getPlayer());
                    else
                        p.showPlayer(player.getPlayer());
                }
            }
            else{
                for(Player player : Bukkit.getOnlinePlayers()){
                    p.showPlayer(player);
                }
            }
        }
        else{
            for(Player player : Bukkit.getOnlinePlayers()){
                p.showPlayer(player);
            }
        }
    }

    @Override
    protected void giveLobbyItems(OMPlayer omPlayer) {

    }
}
