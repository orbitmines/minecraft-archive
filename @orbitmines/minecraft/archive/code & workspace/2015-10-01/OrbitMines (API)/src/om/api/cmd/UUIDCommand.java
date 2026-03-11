package om.api.cmd;

import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import om.api.handlers.Command;
import om.api.handlers.players.OMPlayer;
import om.api.utils.UUIDUtils;
import om.api.utils.enums.ranks.StaffRank;
import om.api.utils.others.ComponentMessage;

import org.bukkit.entity.Player;

public class UUIDCommand extends Command {

	String[] alias = { "/uuid" };
	
	@Override
	public String[] getCMDs() {
		return alias;
	}

	@Override
	public void dispatch(OMPlayer omp, String[] a) {
		Player p = omp.getPlayer();
		
		if(omp.hasPerms(StaffRank.Moderator)){
			if(a.length == 2){
				UUID uuid = UUIDUtils.getUUID(a[1]);

				if(uuid != null){
					p.sendMessage("");
					p.sendMessage("§7Loading §6" + a[1] + "'s §7UUID info...");
					
					ComponentMessage cm = new ComponentMessage();
					cm.addPart(" §7UUID: ", null, null, null, null);
					cm.addPart("§6" + uuid.toString(), ClickEvent.Action.SUGGEST_COMMAND, uuid.toString(), Action.SHOW_TEXT, "§7Copy §6UUID§7.");
					cm.addPart("§7.", null, null, null, null);
					cm.send(p);
					
					p.sendMessage(" §7Name History:");
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
				else{
					if(a[1].length() > 16){
						uuid = java.util.UUID.fromString(a[1]);
						
						if(uuid != null){
							p.sendMessage("");
							p.sendMessage("§7Loading §6" + a[1] + "§7 info...");
							
							ComponentMessage cm = new ComponentMessage();
							cm.addPart(" §7UUID: ", null, null, null, null);
							cm.addPart("§6" + uuid.toString(), ClickEvent.Action.SUGGEST_COMMAND, uuid.toString(), Action.SHOW_TEXT, "§7Copy §6UUID§7.");
							cm.addPart("§7.", null, null, null, null);
							cm.send(p);
							
							p.sendMessage(" §7Name History:");
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
						else{
							p.sendMessage("§7Invalid UUID. (§6" + a[0].toLowerCase() + " <player>§7)");
						}
					}
					else{
						p.sendMessage("§7Invalid Player. (§6" + a[0].toLowerCase() + " <player>§7)");
					}
				}
			}
			else{
				p.sendMessage("§7Invalid Usage. (§6" + a[0].toLowerCase() + " <player | uuid>§7)");
			}
		}
		else{
			omp.unknownCommand(a[0]);
		}
	}
}
