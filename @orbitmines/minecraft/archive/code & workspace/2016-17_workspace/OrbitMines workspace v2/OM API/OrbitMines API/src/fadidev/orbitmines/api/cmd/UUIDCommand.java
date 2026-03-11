package fadidev.orbitmines.api.cmd;

import fadidev.orbitmines.api.handlers.Command;
import fadidev.orbitmines.api.handlers.Messages;
import fadidev.orbitmines.api.handlers.OMPlayer;
import fadidev.orbitmines.api.handlers.chat.ComponentMessage;
import fadidev.orbitmines.api.utils.UUIDUtils;
import fadidev.orbitmines.api.utils.enums.ranks.StaffRank;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class UUIDCommand extends Command {

	String[] alias = { "/uuid" };
	
	@Override
	public String[] getAlias() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.MODERATOR)){
			if(a.length == 2){
				UUID uuid = UUIDUtils.getUUID(a[1]);

				if(uuid != null){
					p.sendMessage("");
                    p.sendMessage(Messages.CMD_INFO_LOADING_UUID.get(omp, a[1]));

                    send(omp, uuid);
				}
				else{
					if(a[1].length() > 16){
						uuid = UUID.fromString(a[1]);
						
						if(uuid != null){
							p.sendMessage("");
                            p.sendMessage(Messages.CMD_INFO_LOADING.get(omp, a[1]));
							
							send(omp, uuid);
						}
						else{
							p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " UUID. (§6" + a[0].toLowerCase() + " <player>§7)");
						}
					}
					else{
						p.sendMessage("§7" + Messages.WORD_INVALID.get(omp) + " Player. (§6" + a[0].toLowerCase() + " <player>§7)");
					}
				}
			}
			else{
				p.sendMessage("§7" + Messages.WORD_INVALID_COMMAND.get(omp) + ". (§6" + a[0].toLowerCase() + " <player | uuid>§7)");
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}

	private void send(OMPlayer omp, UUID uuid){
	    Player p = omp.getPlayer();

        ComponentMessage cm = new ComponentMessage();
        cm.addPart(" §7UUID: ");
        cm.addPart("§6" + uuid.toString(), ClickEvent.Action.SUGGEST_COMMAND, uuid.toString(), Action.SHOW_TEXT, Messages.CMD_INFO_COPY_UUID.get(omp));
        cm.addPart("§7.");
        cm.send(p);

        p.sendMessage(Messages.CMD_INFO_HISTORY.get(omp));
        HashMap<String, String> names = UUIDUtils.getNames(uuid);
        for(String name : names.keySet()){
            if(names.get(name) != null){
                p.sendMessage("  §6" + name + " " + names.get(name));
            }
            else{
                p.sendMessage("  §6" + name);
            }
        }
    }
}
