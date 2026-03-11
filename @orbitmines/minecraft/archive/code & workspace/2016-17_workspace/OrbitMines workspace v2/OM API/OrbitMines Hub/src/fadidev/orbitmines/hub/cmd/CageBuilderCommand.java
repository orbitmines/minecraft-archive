package fadidev.orbitmines.hub.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.hub.OrbitMinesHub;
import fadidev.orbitmines.hub.handlers.CageBuilder;
import fadidev.orbitmines.hub.handlers.HubMessages;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Fadi on 7-9-2016.
 */
public class CageBuilderCommand extends Command {

    private OrbitMinesHub hub;
    private String[] alias = { "/cb", "/cagebuilder" };

    public CageBuilderCommand(){
        this.hub = OrbitMinesHub.getHub();
    }

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omPlayer, String[] a) {
        HubPlayer omp = (HubPlayer) omPlayer;
        Player p = omp.getPlayer();

        if(omp.hasPerms(StaffRank.OWNER)){
            if(a.length >= 2){
                if(omp.getCageBuilder() == null || a[1].equalsIgnoreCase("new")){
                    if(a[1].equalsIgnoreCase("new") && a.length == 3){
                        p.sendMessage(HubMessages.CMD_CAGE_CREATE.get(omp));
                        omp.setCageBuilder(new CageBuilder(a[2], p.getLocation().getBlock().getLocation()));
                    }
                    else{
                        p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6" + a[0].toLowerCase() + " new <name>");
                    }
                }
                else{
                    if(a[1].equalsIgnoreCase("wand")){
                        p.sendMessage(HubMessages.CMD_CAGE_WAND.get(omp));
                        p.getInventory().addItem(CageBuilder.getWand());
                    }
                    else if(a[1].equalsIgnoreCase("generate")){
                        p.sendMessage(HubMessages.CMD_CAGE_GENERATING.get(omp));
                        omp.getCageBuilder().generate();
                        p.sendMessage(HubMessages.CMD_CAGE_GENERATED.get(omp));
                    }
                    else{
                        p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6" + a[0].toLowerCase() + " new|wand|generate");
                    }
                }
            }
            else{
                p.sendMessage("§7" + Messages.WORD_USE.get(omp) + " §6" + a[0].toLowerCase() + " new|wand|generate");
            }
        }
        else{
            omp.unknownCommand(a[0]);
        }
    }
}
