package fadidev.orbitmines.hub.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import fadidev.orbitmines.hub.handlers.players.HubPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by Fadi on 10-9-2016.
 */
public class HubSkullCommand extends Command {

    String[] alias = { "/skull" };

    @Override
    public String[] getAlias() {
        return alias;
    }

    @Override
    public void dispatch(OMPlayer omp, String[] a) {
        Player p = omp.getPlayer();

        if(omp.hasPerms(StaffRank.OWNER) || omp.hasPerms(StaffRank.BUILDER) && ((HubPlayer) omp).inBuilderWorld()){
            if(a.length == 2){
                p.sendMessage(Messages.CMD_SKULL_GIVE.get(omp, a[1]));

                ItemStack item = new ItemStack(Material.SKULL_ITEM, 1);
                SkullMeta meta = (SkullMeta) item.getItemMeta();
                meta.setDisplayName("§6" + a[1] + "'s §7Skull");
                meta.setOwner(a[1]);
                item.setItemMeta(meta);
                item.setDurability((short) 3);

                p.getInventory().addItem(item);
            }
            else{
                p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§6" + a[0].toLowerCase() + " <player>§7)");
            }
        }
        else{
            omp.unknownCommand(a[0]);
        }
    }
}
