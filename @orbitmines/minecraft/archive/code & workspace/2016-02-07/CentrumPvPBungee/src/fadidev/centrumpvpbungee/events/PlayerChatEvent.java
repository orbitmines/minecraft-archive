package fadidev.centrumpvpbungee.events;

import java.util.List;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fadidev.centrumpvpbungee.CentrumPvPBungee;
import fadidev.centrumpvpbungee.handlers.BungeePlayer;
import fadidev.centrumpvpbungee.handlers.Channel;
import fadidev.centrumpvpbungee.handlers.Group;
import fadidev.centrumpvpbungee.utils.PlayerUtils;
import fadidev.centrumpvpbungee.utils.Utils;
import fadidev.centrumpvpbungee.utils.enums.Config;

public class PlayerChatEvent implements Listener {
	
	private CentrumPvPBungee msg;
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(ChatEvent e){
		this.msg = CentrumPvPBungee.getInstance();
		Connection c = e.getSender();
		
		if(c instanceof ProxiedPlayer){
			String[] a = e.getMessage().split(" ");
			ProxiedPlayer p = (ProxiedPlayer) c;
			BungeePlayer bp = msg.getBungeePlayers().get(p);
			
			if(!a[0].startsWith("/")){
				Channel channel = bp.getChannel();
				Group group = bp.getGroup();
				
				if(channel != null && group != null){
					e.setCancelled(true);
					
					String format = group.getFormat(p, e.getMessage());
					
					for(ServerInfo info : channel.getServers()){
						for(ProxiedPlayer player : info.getPlayers()){
							player.sendMessage(format);
						}
					}
				}
			}
			else{
				if(a[0].equalsIgnoreCase("/chat")){
					e.setCancelled(true);
					
					if(a[0].length() > 1){
						if(a[1].equalsIgnoreCase("reload")){
							if(p.hasPermission("CentrumPvPBungee.admin")){
								for(Config config : Config.getCorrectOrder()){
									if(config != Config.PLAYERDATA){
										p.sendMessage("§7Reloading §6" + config.getFileName() + "§7...");
									}
									else{
										int players = ProxyServer.getInstance().getPlayers().size();
										if(players == 1){
											p.sendMessage("§7Restoring data for §6" + players + " Player§7...");
										}
										else{
											p.sendMessage("§7Restoring data for §6" + players + " Players§7...");
										}
									}
									msg.getConfigManager().reload(config);
								}
								msg.loadData();
								
								p.sendMessage("§7Reload §aCompleted§7!");
							}
							else{
								
							}
						}
						else if(a[1].equalsIgnoreCase("off")){
							
						}
						else if(a[2].equalsIgnoreCase("on")){
							
						}
						else if(a[1].equalsIgnoreCase("clear")){
							
						}
						else{
							
						}
					}
					else{
						p.sendMessage("");
					}
				}
				else if(a[0].equalsIgnoreCase("/mute")){
					if(a.length == 2){
						ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
						
						if(p2 != null){
							List<UUID> uuidList = msg.getMutedUUIDs();
							UUID uuid = p2.getUniqueId();
							
							if(uuidList.contains(uuid)){
								uuidList.remove(uuid);

								p.sendMessage("§7You have unmuted §6" + p2.getName() + "§7.");
							}
							else{
								uuidList.add(uuid);
								

								p.sendMessage("§7You have muted §6" + p2.getName() + "§7.");
							}
							
							msg.getConfigManager().get(Config.MUTED).set("MutedUUIDs", Utils.parseStringList(uuidList));
							msg.getConfigManager().save(Config.MUTED);
						}
						else{
							p.sendMessage("§7Player §6" + a[1] + "§7 is not online.");
						}
					}
					else{
						p.sendMessage("§7Use §6/mute <player>§7.");
					}
				}
			}
		}
	}
}
