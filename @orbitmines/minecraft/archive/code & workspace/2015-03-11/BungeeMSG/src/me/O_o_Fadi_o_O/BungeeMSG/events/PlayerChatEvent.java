package me.O_o_Fadi_o_O.BungeeMSG.events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.O_o_Fadi_o_O.BungeeMSG.Start;
import me.O_o_Fadi_o_O.BungeeMSG.managers.AdvertiseManager;
import me.O_o_Fadi_o_O.BungeeMSG.managers.BannedWordsManager;
import me.O_o_Fadi_o_O.BungeeMSG.managers.ConfigManager;
import me.O_o_Fadi_o_O.BungeeMSG.managers.LogManager;
import me.O_o_Fadi_o_O.BungeeMSG.managers.SpamManager;
import me.O_o_Fadi_o_O.BungeeMSG.managers.StorageManager;
import me.O_o_Fadi_o_O.BungeeMSG.managers.TitleManager;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class PlayerChatEvent implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(ChatEvent e){
		Connection c = e.getSender();
		
		if(c instanceof ProxiedPlayer){
			ProxiedPlayer p = (ProxiedPlayer) c;
			String[] a = e.getMessage().split(" ");
			if(!a[0].startsWith("/") && StorageManager.useautoglobalchat == true){
				int key = 0;
				String message = e.getMessage();

				for(int groupid : StorageManager.groupmessages.keySet()){
					if(StorageManager.groupservers.get(groupid).contains(p.getServer().getInfo())){
						key = groupid;
					}
				}
				
				if(StorageManager.groupmessages.containsKey(key)){
					e.setCancelled(true);
					
					if(!StorageManager.mute.contains(p.getUniqueId())){
						if(SpamManager.canMessage(p, message, false)){
							if(AdvertiseManager.canChat(p, message)){
								message = SpamManager.checkForCaps(p, message);
								message = BannedWordsManager.checkForBannedWords(p, message);
								
								String tosend = StorageManager.groupmessages.get(key).replace("&", "§").replace("%sender%", p.getName()).replace("%msg%", message).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§"));
								
								for(ServerInfo info : StorageManager.groupservers.get(key)){
									for(ProxiedPlayer player : info.getPlayers()){
										Start.sendMessageNullCheck(player, TitleManager.importTitle(player, tosend));
									}
								}
								if(StorageManager.uselog == true && StorageManager.readglobal == true){
									LogManager.getLog().info("[GLOBAL-AUTO] " + p.getName() + ": " + message);
								}
							}
						}
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.mutedmessage.replace("&", "§")));
					}
				}
			}
			if(StorageManager.reloadcommand.contains(a[0].toLowerCase())){
				e.setCancelled(true);
				
				if(p.hasPermission("BungeeMSG.reload")){
					if(StorageManager.uselog == true && StorageManager.readreloads == true){
						LogManager.getLog().info("[RELOAD] " + p.getName() + " started a reload...");
					}
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, "§7Reloading §6config.yml§7..."));
					ConfigManager.reloadConfig();
					Start.loadConfigData();
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, "§7Reloading §6muted.yml§7..."));
					ConfigManager.reloadMuted();
					Start.loadMutedData();
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, "§7Reloading §6banned-words.yml§7..."));
					ConfigManager.reloadBannedWords();
					Start.loadBannedWordsData();
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, "§7Reloading §6domain-whitelist.yml§7..."));
					ConfigManager.reloadDomainWhitelist();
					Start.loadDomainWhitelistData(true);
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, "§7Reload §aCompleted§7!"));
				}
				else{
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.noreloadpermission.replace("&", "§")));
				}
			}
			if(StorageManager.spycommand.contains(a[0].toLowerCase())){
				e.setCancelled(true);
				
				if(p.hasPermission("BungeeMSG.spy")){
					if(!StorageManager.spy.containsKey(p.getName())){
						StorageManager.spy.put(p.getName(), false);
					}
					
					if(StorageManager.spy.get(p.getName()) == true){
						StorageManager.spy.put(p.getName(), false);
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.spydisable.replace("&", "§")));

						if(StorageManager.uselog == true && StorageManager.readspies == true){
							LogManager.getLog().info("[SPY] " + p.getName() + " Disabled Spy Mode.");
						}
					}
					else{
						StorageManager.spy.put(p.getName(), true);
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.spyenable.replace("&", "§")));
						
						if(StorageManager.uselog == true && StorageManager.readspies == true){
							LogManager.getLog().info("[SPY] " + p.getName() + " Enabled Spy Mode.");
						}
					}
				}
				else{
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.nospypermission.replace("&", "§")));
				}
			}
			if(StorageManager.togglecommand.contains(a[0].toLowerCase())){
				e.setCancelled(true);
				
				if(a.length == 1){
					if(p.hasPermission("BungeeMSG.toggle")){
						if(!StorageManager.toggle.containsKey(p.getName())){
							StorageManager.toggle.put(p.getName(), true);
						}
						
						if(StorageManager.toggle.get(p.getName()) == true){
							StorageManager.toggle.put(p.getName(), false);
							Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.toggledisable.replace("&", "§")));
							
							if(StorageManager.uselog == true && StorageManager.readtoggles == true){
								LogManager.getLog().info("[TOGGLE] " + p.getName() + " Disabled receiving Private Messages.");
							}
						}
						else{
							StorageManager.toggle.put(p.getName(), true);
							Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.toggleenable.replace("&", "§")));
							
							if(StorageManager.uselog == true && StorageManager.readtoggles == true){
								LogManager.getLog().info("[TOGGLE] " + p.getName() + " Enabled receiving Private Messages.");
							}
						}
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.notogglepermission.replace("&", "§")));
					}
				}
				else if(a.length == 2){
					if(p.hasPermission("BungeeMSG.toggle.other")){
						ProxiedPlayer p2 = null;
						
						for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
							if(player.getName().equalsIgnoreCase(a[1])){
								p2 = player;
							}
						}
						
						if(p2 != null){
							if(!StorageManager.toggle.containsKey(p2.getName())){
								StorageManager.toggle.put(p2.getName(), true);
							}
							
							if(StorageManager.toggle.get(p2.getName()) == true){
								StorageManager.toggle.put(p2.getName(), false);
								Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.toggledisabletosender.replace("&", "§").replace("%toggled%", p2.getName())));
								Start.sendMessageNullCheck(p2, TitleManager.importTitle(p2, StorageManager.toggledisabletoplayer.replace("&", "§").replace("%sender%", p.getName())));
								
								if(StorageManager.uselog == true && StorageManager.readtoggles == true){
									LogManager.getLog().info("[TOGGLE] " + p.getName() + " Disabled receiving Private Messages for player " + p2.getName() + ".");
								}
							}
							else{
								StorageManager.toggle.put(p2.getName(), true);
								Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.toggleenabletosender.replace("&", "§").replace("%toggled%", p2.getName())));
								Start.sendMessageNullCheck(p2, TitleManager.importTitle(p2, StorageManager.toggleenabletoplayer.replace("&", "§").replace("%sender%", p.getName())));
								
								if(StorageManager.uselog == true && StorageManager.readtoggles == true){
									LogManager.getLog().info("[TOGGLE] " + p.getName() + " Enabled receiving Private Messages for player " + p2.getName() + ".");
								}
							}
						}
						else{
							Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.notonline.replace("&", "§").replace("%receiver%", a[1])));
						}
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.notoggleotherpermission.replace("&", "§")));
					}
				}
				else{
					if(p.hasPermission("BungeeMSG.toggle") || p.hasPermission("BungeeMSG.toggle.other")){
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.wrongusagetoggle.replace("&", "§").replace("%cmd%", a[0].toLowerCase())));
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.notogglepermission.replace("&", "§")));
					}
				}
			}
			if(StorageManager.mutecommand.contains(a[0].toLowerCase())){
				e.setCancelled(true);
				
				if(a.length == 2){
					if(p.hasPermission("BungeeMSG.mute")){
						ProxiedPlayer p2 = null;
						
						for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
							if(player.getName().equalsIgnoreCase(a[1])){
								p2 = player;
							}
						}
						
						if(p2 != null){
							UUID uuid = p2.getUniqueId();
							
							if(StorageManager.mute.contains(uuid)){
								StorageManager.mute.remove(uuid);
								Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.unmutetosender.replace("&", "§").replace("%muted%", p2.getName())));
								Start.sendMessageNullCheck(p2, TitleManager.importTitle(p2, StorageManager.unmutetoplayer.replace("&", "§").replace("%sender%", p.getName())));
								
								if(StorageManager.uselog == true && StorageManager.readmutes == true){
									LogManager.getLog().info("[MUTE] " + p.getName() + " unmuted " + p2.getName() + " (UUID: " + p2.getUniqueId().toString() + ").");
								}
							}
							else{
								StorageManager.mute.add(uuid);
								Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.mutetosender.replace("&", "§").replace("%muted%", p2.getName())));
								Start.sendMessageNullCheck(p2, TitleManager.importTitle(p2, StorageManager.mutetoplayer.replace("&", "§").replace("%sender%", p.getName())));

								if(StorageManager.uselog == true && StorageManager.readmutes == true){
									LogManager.getLog().info("[MUTE] " + p.getName() + " muted " + p2.getName() + " (UUID: " + p2.getUniqueId().toString() + ").");
								}
							}
							
							List<String> mutedlist = new ArrayList<String>();
							for(UUID muteduuid : StorageManager.mute){
								mutedlist.add(muteduuid.toString());
							}
							ConfigManager.muted.set("MutedUUIDs", mutedlist);
							ConfigManager.saveMuted();
						}
						else{
							Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.notonline.replace("&", "§").replace("%receiver%", a[1])));
						}
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.notoggleotherpermission.replace("&", "§")));
					}
				}
				else{
					if(p.hasPermission("BungeeMSG.mute")){
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.wrongusagemute.replace("&", "§").replace("%cmd%", a[0].toLowerCase())));
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.nomutepermission.replace("&", "§")));
					}
				}
			}
			if(StorageManager.globalcommand.contains(a[0].toLowerCase())){
				e.setCancelled(true);
				
				int commandlength = a[0].length();
				boolean hasperm = false;
				
				if(StorageManager.permissionrequiredforglobalmessage == true){
					if(p.hasPermission("BungeeMSG.global")){
						hasperm = true;
					}
				}
				else{
					hasperm = true;
				}
				
				if(hasperm){
					if(!StorageManager.mute.contains(p.getUniqueId())){
						if(a.length > 1){
							String message = e.getMessage().substring(commandlength + 1);
	
							if(SpamManager.canMessage(p, message, false)){
								if(AdvertiseManager.canChat(p, message)){
									message = SpamManager.checkForCaps(p, message);
									message = BannedWordsManager.checkForBannedWords(p, message);
									
									String tosend = StorageManager.globalmessage.replace("&", "§").replace("%cmd%", a[0].toLowerCase()).replace("%msg%", message).replace("%sender%", p.getName()).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§"));
									for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
										Start.sendMessageNullCheck(player, TitleManager.importTitle(player, tosend));
									}
									
									if(StorageManager.uselog == true && StorageManager.readglobal == true){
										LogManager.getLog().info("[GLOBAL-CMD] " + p.getName() + ": " + message);
									}
								}
							}
						}
						else{
							Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.wrongusageglobal.replace("&", "§").replace("%cmd%", a[0].toLowerCase())));
						}
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.mutedmessage.replace("&", "§")));
					}
				}
				else{
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.noglobalpermission.replace("&", "§")));
				}
			}
			if(StorageManager.messagecommand.contains(a[0].toLowerCase())){
				e.setCancelled(true);
					
				int commandlength = a[0].length();
				
				if(!StorageManager.mute.contains(p.getUniqueId())){
					if(a.length >= 3){
						ProxiedPlayer toPlayer = null;
						
						for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
							if(a[1].equalsIgnoreCase(player.getName())){
								toPlayer = player;
							}
						}
						
						if(toPlayer != null){
							if(toPlayer != p){
								if(!StorageManager.toggle.containsKey(p.getName()) || StorageManager.toggle.get(p.getName()) == true){
									String message = e.getMessage().substring(commandlength + toPlayer.getName().length() + 2);
									
									if(SpamManager.canMessage(p, message, true)){
										if(AdvertiseManager.canChat(p, message)){
											message = SpamManager.checkForCaps(p, message);
											message = BannedWordsManager.checkForBannedWords(p, message);
											
											if(StorageManager.toggle.containsKey(toPlayer.getName()) && StorageManager.toggle.get(toPlayer.getName()) == false){
												if(StorageManager.tellsenderifdisabled == true){
													Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.disabledmessage.replace("&", "§").replace("%receiver%", toPlayer.getName())));
												}
												else{
													String tosender = StorageManager.tosender.replace("&", "§").replace("%sender%", p.getName()).replace("%receiver%", toPlayer.getName()).replace("%msg%", message).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§")).replace("%server-receiver%", StorageManager.servernames.get(toPlayer.getServer().getInfo()).replace("&", "§"));
													tosender = TitleManager.importTitle(p, tosender);
													Start.sendMessageNullCheck(p, TitleManager.importTitle(p, tosender));
												}
											}
											else{
												String toreceiver = StorageManager.toreceiver.replace("&", "§").replace("%sender%", p.getName()).replace("%receiver%", toPlayer.getName()).replace("%msg%", message).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§")).replace("%server-receiver%", StorageManager.servernames.get(toPlayer.getServer().getInfo()).replace("&", "§"));
												Start.sendMessageNullCheck(toPlayer, TitleManager.importTitle(toPlayer, toreceiver));
												String tosender = StorageManager.tosender.replace("&", "§").replace("%sender%", p.getName()).replace("%receiver%", toPlayer.getName()).replace("%msg%", message).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§")).replace("%server-receiver%", StorageManager.servernames.get(toPlayer.getServer().getInfo()).replace("&", "§"));
												Start.sendMessageNullCheck(p, TitleManager.importTitle(p, tosender));
												for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
													if(StorageManager.spy.containsKey(player.getName()) && StorageManager.spy.get(player.getName()) == true){
														String tospy = StorageManager.spyprefix.replace("&", "§") + StorageManager.spymessage.replace("&", "§").replace("%sender%", p.getName()).replace("%receiver%", toPlayer.getName()).replace("%msg%", message).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§")).replace("%server-receiver%", StorageManager.servernames.get(toPlayer.getServer().getInfo()).replace("&", "§"));;
														Start.sendMessageNullCheck(player, TitleManager.importTitle(player, tospy));
													}
												}
												if(StorageManager.infoenabled == true && StorageManager.lastmsg.get(toPlayer) == null){
													Start.sendMessageNullCheck(toPlayer, TitleManager.importTitle(toPlayer, StorageManager.info.replace("&", "§")));
												}
												StorageManager.lastmsg.put(toPlayer, p);
												StorageManager.lastmsg.put(p, toPlayer);
												
												if(StorageManager.uselog == true && StorageManager.readprivatemessages == true){
													LogManager.getLog().info("[MSG] " + p.getName() + " > " + toPlayer.getName() + ": " + message);
												}
											}
										}
									}
								}
								else{
									Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.toggleonsend.replace("&", "§")));
								}
							}
							else{
								Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.tothemselves.replace("&", "§")));
							}
						}
						else{
							Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.notonline.replace("&", "§").replace("%receiver%", a[1])));
						}
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.wrongusagemsg.replace("&", "§").replace("%cmd%", a[0].toLowerCase())));
					}
				}
				else{
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.mutedmessage.replace("&", "§")));
				}
			}
			if(StorageManager.replycommand.contains(a[0].toLowerCase())){
				e.setCancelled(true);
				int commandlength = a[0].length();
				
				if(!StorageManager.mute.contains(p.getUniqueId())){
					if(a.length >= 2){
						if(StorageManager.lastmsg.containsKey(p)){
							ProxiedPlayer toPlayer = null;
							
							for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
								if(StorageManager.lastmsg.get(p) == player){
									toPlayer = player;
								}
							}
							
							if(toPlayer != null){
								if(!StorageManager.toggle.containsKey(p.getName()) || StorageManager.toggle.get(p.getName()) == true){
									String message = e.getMessage().substring(commandlength + 1);

									if(SpamManager.canMessage(p, message, true)){
										if(AdvertiseManager.canChat(p, message)){
											message = SpamManager.checkForCaps(p, message);
											message = BannedWordsManager.checkForBannedWords(p, message);
											
											if(StorageManager.toggle.containsKey(toPlayer.getName()) && StorageManager.toggle.get(toPlayer.getName()) == false){
												if(StorageManager.tellsenderifdisabled == true){
													Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.disabledmessage.replace("&", "§").replace("%receiver%", toPlayer.getName())));
												}
												else{
													String tosender = StorageManager.tosender.replace("&", "§").replace("%sender%", p.getName()).replace("%receiver%", toPlayer.getName()).replace("%msg%", message).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§")).replace("%server-receiver%", StorageManager.servernames.get(toPlayer.getServer().getInfo()).replace("&", "§"));
													Start.sendMessageNullCheck(p, TitleManager.importTitle(p, tosender));
												}
											}
											else{
												Start.sendMessageNullCheck(toPlayer, TitleManager.importTitle(toPlayer, StorageManager.toreceiver.replace("&", "§").replace("%sender%", p.getName()).replace("%receiver%", toPlayer.getName()).replace("%msg%", message)).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§")).replace("%server-receiver%", StorageManager.servernames.get(toPlayer.getServer().getInfo()).replace("&", "§")));
												String tosender = StorageManager.tosender.replace("&", "§").replace("%sender%", p.getName()).replace("%receiver%", toPlayer.getName()).replace("%msg%", message).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§")).replace("%server-receiver%", StorageManager.servernames.get(toPlayer.getServer().getInfo()).replace("&", "§"));
												Start.sendMessageNullCheck(p, TitleManager.importTitle(p, tosender));
												for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
													if(StorageManager.spy.containsKey(player.getName()) && StorageManager.spy.get(player.getName()) == true){
														String tospy = StorageManager.spyprefix.replace("&", "§") + StorageManager.spymessage.replace("&", "§").replace("%sender%", p.getName()).replace("%receiver%", toPlayer.getName()).replace("%msg%", message).replace("%server-sender%", StorageManager.servernames.get(p.getServer().getInfo()).replace("&", "§")).replace("%server-receiver%", StorageManager.servernames.get(toPlayer.getServer().getInfo()).replace("&", "§"));;
														Start.sendMessageNullCheck(player, TitleManager.importTitle(player, tospy));
													}
												}
												StorageManager.lastmsg.put(toPlayer, p);
												StorageManager.lastmsg.put(p, toPlayer);
												
												if(StorageManager.uselog == true && StorageManager.readprivatemessages == true){
													LogManager.getLog().info("[MSG] " + p.getName() + " > " + toPlayer.getName() + ": " + message);
												}
											}
										}
									}
								}
								else{
									Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.toggleonsend.replace("&", "§")));
								}
							}						
							else{
								StorageManager.lastmsg.remove(p);
								Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.noreceiver.replace("&", "§")));
							}
						}
						else{
							Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.noreceiver.replace("&", "§")));
						}
					}
					else{
						Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.wrongusagereply.replace("&", "§").replace("%cmd%", a[0].toLowerCase())));
					}
				}
				else{
					Start.sendMessageNullCheck(p, TitleManager.importTitle(p, StorageManager.mutedmessage.replace("&", "§")));
				}
			}
		}
	}
}
