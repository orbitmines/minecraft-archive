package fadidev.bungeemsg.events;

import java.util.List;
import java.util.UUID;

import fadidev.bungeemsg.handlers.*;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.Connection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import fadidev.bungeemsg.BungeeMSG;
import fadidev.bungeemsg.managers.LogManager;
import fadidev.bungeemsg.utils.PlayerUtils;
import fadidev.bungeemsg.utils.Utils;
import fadidev.bungeemsg.utils.enums.Config;
import fadidev.bungeemsg.utils.enums.Cooldown;
import fadidev.bungeemsg.utils.enums.LogReadType;
import fadidev.bungeemsg.utils.enums.Message;
import fadidev.bungeemsg.utils.enums.Variable;

public class PlayerChatEvent implements Listener {
	
	private BungeeMSG msg;
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(ChatEvent e){
		this.msg = BungeeMSG.getInstance();
		Connection c = e.getSender();
		
		if(c instanceof ProxiedPlayer){
			String[] a = e.getMessage().split(" ");
			ProxiedPlayer p = (ProxiedPlayer) c;
			BungeePlayer bp = msg.getBungeePlayers().get(p);
			LogManager lM = msg.getLogManager();
			
			Channel ch = null;
			String symb = null;
			
			loop:
			for(Channel channel : msg.getChannels()){
				if(!channel.usePermission() || bp.hasPermission(channel.getPermission())){
					for(String symbol : channel.getStartSymbols()){
						if(e.getMessage().toLowerCase().startsWith(symbol.toLowerCase())){
							ch = channel;
							symb = symbol;
							break loop;
						}
					}
				}
			}
			
			if(ch != null){
				e.setCancelled(true);
				String message = e.getMessage().substring(symb.length());
				
				if(!bp.isMuted()){
					if(bp.canMessage(message, Cooldown.LAST_GLOBAL)){
						MessageParser mP = ch.getMessage().getParser(bp);
						mP.parseVariable(Variable.SENDER, p.getName());
						mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
						mP.parseVariable(Variable.MSG, message);
						mP.send(p);
					
						if(!mP.isCancelled()){
							for(ProxiedPlayer player : bp.getNotIgnored()){
								if(!ch.usePermission() || player.hasPermission(ch.getPermission())){
									mP.send(player, p);
								}
							}
							
							lM.info(ch, "[CHANNEL | " + ch.getName() + "] " + p.getName() + ": '" + message + "'");
						}
					}
				}
				else{
					MessageParser mP = Message.MUTED.getParser(bp);
					mP.send(p);
				}
			}
			else{
				ch = null;
				
				loop:
				for(Channel channel : msg.getChannels()){
					if(!channel.usePermission() || bp.hasPermission(channel.getPermission())){
						for(String symbol : channel.getToggleSymbols()){
							if(e.getMessage().length() >= symbol.length() && e.getMessage().equalsIgnoreCase(symbol)){
								ch = channel;
								break loop;
							}
						}
					}
				}
				
				if(ch != null){
					e.setCancelled(true);
					
					Channel channel = bp.getToggledChannel();
					if(channel != null){
						if(channel == ch){
							MessageParser mP = channel.getDisabledMessage().getParser(bp);
							mP.send(p);
							
							bp.setToggledChannel(null);
						}
						else{
							MessageParser mP = channel.getDisabledMessage().getParser(bp);
							mP.send(p);
							
							MessageParser mP2 = ch.getEnabledMessage().getParser(bp);
							mP2.send(p);
							
							bp.setToggledChannel(ch);
						}
					}
					else{
						MessageParser mP = ch.getEnabledMessage().getParser(bp);
						mP.send(p);
						
						bp.setToggledChannel(ch);
					}
				}
				else{
					if(!a[0].startsWith("/")){
						String message = e.getMessage();
						
						if(bp.getToggledChannel() != null){
							e.setCancelled(true);
							
							Channel channel = bp.getToggledChannel();
							
							if(!bp.isMuted()){
								if(bp.canMessage(message, Cooldown.LAST_GLOBAL)){
									MessageParser mP = channel.getMessage().getParser(bp);
									mP.parseVariable(Variable.SENDER, p.getName());
									mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
									mP.parseVariable(Variable.MSG, message);
									mP.send(p);
								
									if(!mP.isCancelled()){
										for(ProxiedPlayer player : bp.getNotIgnored()){
											if(!channel.usePermission() || player.hasPermission(channel.getPermission())){
												mP.send(player, p);
											}
										}
										
										lM.info(channel, "[CHANNEL | " + channel.getName() + "] " + p.getName() + ": '" + message + "'");
									}
								}
							}
							else{
								MessageParser mP = Message.MUTED.getParser(bp);
								mP.send(p);
							}
						}
						else{
							if(msg.useAutoGlobal()){
								Group group = null;
								ServerInfo server = p.getServer().getInfo();
								
								for(Group g : msg.getGroups()){
									if(g.getServers().contains(server)){
										group = g;
									}
								}
								
								if(group != null){
									if(!bp.isMuted()){
										if(bp.canMessage(message, Cooldown.LAST_GLOBAL)){
											MessageParser mP = group.getMSGLoader().getParser(bp);
											mP.parseVariable(Variable.SENDER, p.getName());
											mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
											mP.parseVariable(Variable.MSG, message);
											mP.send(p);
										
											if(!mP.isCancelled()){
												for(ServerInfo info : group.getServers()){
													for(ProxiedPlayer player : bp.getNotIgnored(info.getPlayers())){
														mP.send(player, p);
													}
												}
												
												lM.info(group, "[GLOBAL-AUTO] " + p.getName() + ": " + message);
											}
										}
									}
									else{
										MessageParser mP = Message.MUTED.getParser(bp);
										mP.send(p);
									}
								}
							}
						}
					}
					else{
						for(Command cmd : msg.getCommands()){
							if(cmd.isUsed() && cmd.getCommands().contains(a[0].toLowerCase())){
								e.setCancelled(true);
								
								if(!cmd.usePermission() || bp.hasPermission(cmd.getPermission())){
									switch(cmd.getType()){
										case RELOAD:
											{
												lM.info(LogReadType.RELOADS, p.getServer().getInfo(), "[RELOAD] " + p.getName() + " started a reload...");
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
												msg.loadData(true);
												
												p.sendMessage("§7Reload §aCompleted§7!");
											}
											break;
                                        case SETRANK:
                                            {
                                                if(a.length == 3){
                                                    ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);

                                                    if(p2 != null){
                                                        BungeePlayer bp2 = msg.getBungeePlayers().get(p2);

                                                        Rank rank = Rank.getRank(a[2]);
                                                        if(rank != null){
                                                            bp2.setRank(rank);

                                                            MessageParser mP = Message.RANK_SET.getParser(bp);
                                                            mP.parseVariable(Variable.RECEIVER, p2.getName());
                                                            mP.parseVariable(Variable.RANK, a[2]);
                                                            mP.send(p);
                                                        }
                                                        else{
                                                            MessageParser mP = Message.UNKNOWN_RANK.getParser(bp);
                                                            mP.parseVariable(Variable.RANK, a[2]);
                                                            mP.send(p);
                                                        }

                                                        lM.info(LogReadType.RANK_SET, p.getServer().getInfo(), "[SETRANK] " + p.getName() + " has set " + p2.getName() + "'s Rank to " + a[2] + ".");
                                                    }
                                                    else{
                                                        MessageParser mP = Message.NOT_ONLINE.getParser(bp);
                                                        mP.parseVariable(Variable.RECEIVER, a[1]);
                                                        mP.send(p);
                                                    }
                                                }
                                                else{
                                                    MessageParser mP = cmd.getWrongUsage().getParser(bp);
                                                    mP.parseVariable(Variable.CMD, a[0].toLowerCase());
                                                    mP.send(p);
                                                }
                                            }
                                            break;
										case SPY:
											{
												if(bp.isSpy()){
													MessageParser mP = Message.SPY_DISABLE.getParser(bp);
													mP.send(p);
													
													lM.info(LogReadType.SPIES, p.getServer().getInfo(), "[SPY] " + p.getName() + " Disabled Spy Mode.");
												}
												else{
													MessageParser mP = Message.SPY_ENABLE.getParser(bp);
													mP.send(p);
													
													lM.info(LogReadType.SPIES, p.getServer().getInfo(), "[SPY] " + p.getName() + " Enabled Spy Mode.");
												}
												
												bp.setSpy(!bp.isSpy());
											}
											break;
										case TOGGLE:
											{
												if(a.length == 1 || !p.hasPermission(cmd.getPermission() + ".other")){
													if(bp.hasMSGEnabled()){
														MessageParser mP = Message.PM_DISABLED.getParser(bp);
														mP.send(p);
														
														lM.info(LogReadType.TOGGLES, p.getServer().getInfo(), "[TOGGLE] " + p.getName() + " Disabled receiving Private Messages.");
													}
													else{
														MessageParser mP = Message.PM_ENABLED.getParser(bp);
														mP.send(p);
														
														lM.info(LogReadType.TOGGLES, p.getServer().getInfo(), "[TOGGLE] " + p.getName() + " Enabled receiving Private Messages.");
													}
													
													bp.setMSGEnabled(!bp.hasMSGEnabled());
												}
												else{
													if(a.length == 2){
														ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
														
														if(p2 != null){
															BungeePlayer bp2 = msg.getBungeePlayers().get(p2);
															if(bp2.hasMSGEnabled()){
																MessageParser mP = Message.PM_DISABLED_TO_SENDER.getParser(bp);
																mP.parseVariable(Variable.TOGGLED, p2.getName());
																mP.send(p);
																
																MessageParser mP2 = Message.PM_DISABLED_TO_PLAYER.getParser(bp);
																mP2.parseVariable(Variable.SENDER, p.getName());
																mP2.send(p2);
																
																lM.info(LogReadType.TOGGLES, p.getServer().getInfo(), "[TOGGLE] " + p.getName() + " Disabled receiving Private Messages for player " + p2.getName() + ".");
															}
															else{
																MessageParser mP = Message.PM_ENABLED_TO_SENDER.getParser(bp);
																mP.parseVariable(Variable.TOGGLED, p2.getName());
																mP.send(p);
																
																MessageParser mP2 = Message.PM_ENABLED_TO_PLAYER.getParser(bp);
																mP2.parseVariable(Variable.SENDER, p.getName());
																mP2.send(p2);
																
																lM.info(LogReadType.TOGGLES, p.getServer().getInfo(), "[TOGGLE] " + p.getName() + " Enabled receiving Private Messages for player " + p2.getName() + ".");
															}
															
															bp2.setMSGEnabled(!bp2.hasMSGEnabled());
														}
														else{
															MessageParser mP = Message.NOT_ONLINE.getParser(bp);
															mP.parseVariable(Variable.RECEIVER, a[1]);
															mP.send(p);
														}
													}
													else{
														MessageParser mP = cmd.getWrongUsage().getParser(bp);
														mP.parseVariable(Variable.CMD, a[0].toLowerCase());
														mP.send(p);
													}
												}
											}
											break;
										case MUTE:
											{
												if(a.length == 2){
													ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
													
													if(p2 != null){
														List<UUID> uuidList = msg.getMutedUUIDs();
														UUID uuid = p2.getUniqueId();
														
														if(uuidList.contains(uuid)){
															uuidList.remove(uuid);
															
															MessageParser mP = Message.UNMUTE_TO_SENDER.getParser(bp);
															mP.parseVariable(Variable.MUTED, p2.getName());
															mP.send(p);
															
															MessageParser mP2 = Message.UNMUTE_TO_PLAYER.getParser(bp);
															mP2.parseVariable(Variable.SENDER, p.getName());
															mP2.send(p2);
															
															lM.info(LogReadType.MUTES, p.getServer().getInfo(), "[MUTE] " + p.getName() + " unmuted " + p2.getName() + " (UUID: " + p2.getUniqueId().toString() + ").");
														}
														else{
															uuidList.add(uuid);
															
															MessageParser mP = Message.MUTE_TO_SENDER.getParser(bp);
															mP.parseVariable(Variable.MUTED, p2.getName());
															mP.send(p);
															
															MessageParser mP2 = Message.MUTE_TO_PLAYER.getParser(bp);
															mP2.parseVariable(Variable.SENDER, p.getName());
															mP2.send(p2);
															
															lM.info(LogReadType.MUTES, p.getServer().getInfo(), "[MUTE] " + p.getName() + " muted " + p2.getName() + " (UUID: " + p2.getUniqueId().toString() + ").");
														}
														
														msg.getConfigManager().get(Config.MUTED).set("MutedUUIDs", Utils.parseStringList(uuidList));
														msg.getConfigManager().save(Config.MUTED);
													}
													else{
														MessageParser mP = Message.NOT_ONLINE.getParser(bp);
														mP.parseVariable(Variable.RECEIVER, a[1]);
														mP.send(p);
													}
												}
												else{
													MessageParser mP = cmd.getWrongUsage().getParser(bp);
													mP.parseVariable(Variable.CMD, a[0].toLowerCase());
													mP.send(p);
												}
											}
											break;
										case GLOBAL:
											{
												if(!bp.isMuted()){
													ServerInfo server = p.getServer().getInfo();
													Group group = null;
													for(Group g : msg.getGroups()){
														if(g.getServers().contains(server)){
															group = g;
															break;
														}
													}
													
													if(group != null && a.length > 1){
														String message = e.getMessage().substring(a[0].length() + 1);
														
														if(bp.canMessage(message, Cooldown.LAST_GLOBAL)){
															MessageParser mP = group.getMSGLoader().getParser(bp);
															mP.parseVariable(Variable.SENDER, p.getName());
															mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
															mP.parseVariable(Variable.MSG, message);
															mP.send(p);
														
															if(!mP.isCancelled()){
																for(ServerInfo info : group.getServers()){
																	for(ProxiedPlayer player : bp.getNotIgnored(info.getPlayers())){
																		mP.send(player, p);
																	}
																}
																
																lM.info(group, "[GLOBAL-CMD] " + p.getName() + ": " + message);
															}
														}
													}
													else{
														p.sendMessage("Hi");
														MessageParser mP = cmd.getWrongUsage().getParser(bp);
														mP.parseVariable(Variable.CMD, a[0].toLowerCase());
														mP.send(p);
													}
												}
												else{
													MessageParser mP = Message.MUTED.getParser(bp);
													mP.send(p);
												}
											}
											break;
										case MUTE_ALL:
											{
												if(a.length == 1){
													if(msg.isAllMuted()){
														MessageParser mP = Message.UNMUTE_ALL_TO_SENDER.getParser(bp);
														mP.send(p);
														
														MessageParser mP2 = Message.UNMUTE_ALL_TO_PLAYER.getParser(bp);
														mP2.parseVariable(Variable.SENDER, p.getName());
														
														for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
															mP2.send(player);
														}
														
														lM.info(LogReadType.MUTE_ALL, p.getServer().getInfo(), "[MUTE-ALL] " + p.getName() + " Disabled Mute All Mode.");
													}
													else{
														MessageParser mP = Message.MUTE_ALL_TO_SENDER.getParser(bp);
														mP.send(p);
														
														MessageParser mP2 = Message.MUTE_ALL_TO_PLAYER.getParser(bp);
														mP2.parseVariable(Variable.SENDER, p.getName());
														
														for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
															mP2.send(player);
														}
														
														lM.info(LogReadType.MUTE_ALL, p.getServer().getInfo(), "[MUTE-ALL] " + p.getName() + " Enabled Mute All Mode.");
													}
													
													msg.setAllMuted(!msg.isAllMuted());
												}
												else if(a.length == 2){
													ServerInfo server = ProxyServer.getInstance().getServerInfo(a[1]);
													
													if(server != null){
														List<ServerInfo> mutedServers = msg.getServersMuted();
														
														if(mutedServers.contains(server)){
															mutedServers.remove(server);
															
															MessageParser mP = Message.MUTE_SERVER_TO_SENDER.getParser(bp);
															mP.parseVariable(Variable.SERVER_NAME, msg.getServerNames().get(server));
															mP.send(p);
															
															MessageParser mP2 = Message.MUTE_ALL_TO_PLAYER.getParser(bp);
															mP2.parseVariable(Variable.SENDER, p.getName());
														
															for(ProxiedPlayer player : server.getPlayers()){
																mP2.send(player);
															}
															
															lM.info(LogReadType.MUTE_ALL, p.getServer().getInfo(), "[MUTE-ALL] " + p.getName() + " Enabled Mute All Mode for server '" + server.getName() + "'.");
														}
														else{
															mutedServers.add(server);
															
															MessageParser mP = Message.UNMUTE_SERVER_TO_SENDER.getParser(bp);
															mP.parseVariable(Variable.SERVER_NAME, msg.getServerNames().get(server));
															mP.send(p);
															
															MessageParser mP2 = Message.UNMUTE_ALL_TO_PLAYER.getParser(bp);
															mP2.parseVariable(Variable.SENDER, p.getName());
														
															for(ProxiedPlayer player : server.getPlayers()){
																mP2.send(player);
															}
															
															lM.info(LogReadType.MUTE_ALL, p.getServer().getInfo(), "[MUTE-ALL] " + p.getName() + " Disabled Mute All Mode for server '" + server.getName() + "'.");
														}
													}
													else{
														MessageParser mP = cmd.getWrongUsage().getParser(bp);
														mP.parseVariable(Variable.CMD, a[0].toLowerCase());
														mP.send(p);
													}
												}
												else{
													MessageParser mP = cmd.getWrongUsage().getParser(bp);
													mP.parseVariable(Variable.CMD, a[0].toLowerCase());
													mP.send(p);
												}
											}
											break;
										case IGNORE:
											{
												if(a.length == 2){
													ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
													
													if(p2 != null){
														if(p != p2){
															List<UUID> ignored = bp.getIgnored();
															if(ignored.contains(p2.getUniqueId())){
																ignored.remove(p2.getUniqueId());
																
																MessageParser mP = Message.IGNORE_DISABLE.getParser(bp);
																mP.parseVariable(Variable.IGNORED, p2.getName());
																mP.send(p);
															}
															else{
																ignored.add(p2.getUniqueId());
																
																MessageParser mP = Message.IGNORE_ENABLE.getParser(bp);
																mP.parseVariable(Variable.IGNORED, p2.getName());
																mP.send(p);
															}
															
															bp.setIgnored(ignored);
														}
														else{
															MessageParser mP = Message.TO_THEMSELVES.getParser(bp);
															mP.send(p);
														}
													}
													else{
														MessageParser mP = Message.NOT_ONLINE.getParser(bp);
														mP.parseVariable(Variable.RECEIVER, a[1]);
														mP.send(p);
													}
												}
												else{
													MessageParser mP = cmd.getWrongUsage().getParser(bp);
													mP.parseVariable(Variable.CMD, a[0].toLowerCase());
													mP.send(p);
												}
											}
											break;
										case REPORT:
											{
												if(a.length > 2){
													ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
													
													if(p2 != null){
														if(p != p2){
															if(!bp.onCooldown(Cooldown.REPORT)){
																String reason = e.getMessage().substring(a[0].length() + p2.getName().length() + 2);
																
																MessageParser mP = Message.REPORTED_TO_SENDER.getParser(bp);
																mP.parseVariable(Variable.REPORTED, p2.getName());
																mP.parseVariable(Variable.SERVER_REPORTED, msg.getServerNames().get(p2.getServer().getInfo()));
																mP.parseVariable(Variable.REASON, reason);
																mP.send(p);
																
																MessageParser mP2 = Message.REPORTED_TO_STAFF.getParser(bp);
																mP2.parseVariable(Variable.REPORTED, p2.getName());
																mP2.parseVariable(Variable.SERVER_REPORTED, msg.getServerNames().get(p2.getServer().getInfo()));
																mP2.parseVariable(Variable.SENDER, p.getName());
																mP2.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																mP2.parseVariable(Variable.REASON, reason);
																
																for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
																	if(player != p && player.hasPermission("BungeeMSG.notifyreport")){
																		mP2.send(player);
																	}
																}
																
																lM.info(LogReadType.REPORTS, p.getServer().getInfo(), "[REPORT] (" + p.getServer().getInfo().getName() + ") " + p.getName() + " > (" + p2.getServer().getInfo().getName() + ") " + p2.getName() + " (Reason: " + reason + ")");
																
																bp.resetCooldown(Cooldown.REPORT);
															}
															else{
																MessageParser mP = Message.REPORT_ON_COOLDOWN.getParser(bp);
																mP.send(p);
															}
														}
														else{
															MessageParser mP = Message.TO_THEMSELVES.getParser(bp);
															mP.send(p);
														}
													}
													else{
														MessageParser mP = Message.NOT_ONLINE.getParser(bp);
														mP.parseVariable(Variable.RECEIVER, a[1]);
														mP.send(p);
													}
												}
												else{
													MessageParser mP = cmd.getWrongUsage().getParser(bp);
													mP.parseVariable(Variable.CMD, a[0].toLowerCase());
													mP.send(p);
												}
											}
											break;
										case HELP_OP:
											{
												if(a.length > 1){
													if(!bp.onCooldown(Cooldown.HELPOP)){
														String reason = e.getMessage().substring(a[0].length() +1);
														
														MessageParser mP = Message.HELPOP_TO_SENDER.getParser(bp);
														mP.parseVariable(Variable.REASON, reason);
														mP.send(p);
														
														MessageParser mP2 = Message.HELPOP_TO_STAFF.getParser(bp);
														mP2.parseVariable(Variable.SENDER, p.getName());
														mP2.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
														mP2.parseVariable(Variable.REASON, reason);
														
														for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
															if(player != p && player.hasPermission("BungeeMSG.notifyhelpop")){
																mP2.send(player);
															}
														}
														
														lM.info(LogReadType.HELP_OPS, p.getServer().getInfo(), "[HELPOP] (" + p.getServer().getInfo().getName() + ") " + p.getName() + ": " + reason);
														
														bp.resetCooldown(Cooldown.HELPOP);
													}
													else{
														MessageParser mP = Message.HELPOP_ON_COOLDOWN.getParser(bp);
														mP.send(p);
													}
												}
												else{
													MessageParser mP = cmd.getWrongUsage().getParser(bp);
													mP.parseVariable(Variable.CMD, a[0].toLowerCase());
													mP.send(p);
												}
											}
											break;
										case BROADCAST:
											{
												if(a.length > 1){
													String message = e.getMessage().substring(a[0].length() + 1);
													
													MessageParser mP = Message.BROADCAST.getParser(bp);
													mP.parseVariable(Variable.SENDER, p.getName());
													mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
													mP.parseVariable(Variable.MSG, message);
													
													for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
														mP.send(player);
													}
												}
												else{
													MessageParser mP = cmd.getWrongUsage().getParser(bp);
													mP.parseVariable(Variable.CMD, a[0].toLowerCase());
													mP.send(p);
												}
											}
											break;
										case MESSAGE:
											{
												if(!bp.isMuted()){
													if(a.length > 2){
														ProxiedPlayer p2 = PlayerUtils.getPlayer(a[1]);
													
														if(p2 != null){
															if(p != p2){
																if(bp.hasMSGEnabled()){
																	String message = e.getMessage().substring(a[0].length() + p2.getName().length() + 2);
																
																	if(bp.canMessage(message, Cooldown.LAST_MSG)){
																		BungeePlayer bp2 = msg.getBungeePlayers().get(p2);
																		
																		if(!bp2.hasMSGEnabled()){
																			if(msg.tellPMDisabled()){
																				MessageParser mP = Message.PM_TOGGLED_OTHER.getParser(bp);
																				mP.parseVariable(Variable.RECEIVER, p2.getName());
																				mP.send(p);
																			}
																			else{
																				MessageParser mP = Message.PM_TO_SENDER.getParser(bp);
																				mP.parseVariable(Variable.SENDER, p.getName());
																				mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																				mP.parseVariable(Variable.RECEIVER, p2.getName());
																				mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerNames().get(p2.getServer().getInfo()));
																				mP.parseVariable(Variable.MSG, message);
																				
																				if(!bp.hasIgnored(bp2, mP, message)){
																					mP.send(p);
																				}
																			}
																		}
																		else{
																			MessageParser mP = Message.PM_TO_SENDER.getParser(bp);
																			mP.parseVariable(Variable.SENDER, p.getName());
																			mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																			mP.parseVariable(Variable.RECEIVER, p2.getName());
																			mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerNames().get(p2.getServer().getInfo()));
																			mP.parseVariable(Variable.MSG, message);
																			
																			if(!bp.hasIgnored(bp2, mP, message) && !mP.isCancelled()){
																				MessageParser mP2 = Message.PM_TO_RECEIVER.getParser(bp);
																				mP2.parseVariable(Variable.SENDER, p.getName());
																				mP2.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																				mP2.parseVariable(Variable.RECEIVER, p2.getName());
																				mP2.parseVariable(Variable.SERVER_RECEIVER, msg.getServerNames().get(p2.getServer().getInfo()));
																				mP2.parseVariable(Variable.MSG, message);
																				mP2.send(p2, p);
																				mP.send(p, p2);
																				
																				MessageParser mP3 = Message.SPY_MESSAGE.getParser(bp);
																				mP3.parseVariable(Variable.SENDER, p.getName());
																				mP3.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																				mP3.parseVariable(Variable.RECEIVER, p2.getName());
																				mP3.parseVariable(Variable.SERVER_RECEIVER, msg.getServerNames().get(p2.getServer().getInfo()));
																				mP3.parseVariable(Variable.MSG, message);
																				
																				for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
																					BungeePlayer bplayer = msg.getBungeePlayers().get(player);
																					
																					if(bplayer.isSpy()){
																						mP3.send(player);
																					}
																				}
																				
																				if(msg.hasReplyInfo() && bp2.getLastMSG() == null){
																					MessageParser mP4 = Message.REPLY_INFO.getParser(bp);
																					mP4.send(p2);
																				}
																				
																				bp.setLastMSG(message);
																				bp.setLastMSGTo(bp2);
																				bp2.setLastMSGTo(bp);
																				
																				lM.info(LogReadType.PRIVATE_MESSAGES, p.getServer().getInfo(), "[MSG] " + p.getName() + " > " + p2.getName() + ": " + message);
																			}
																		}
																	}
																}
																else{
																	MessageParser mP = Message.PM_TOGGLED.getParser(bp);
																	mP.send(p);
																}
															}
															else{
																MessageParser mP = Message.TO_THEMSELVES.getParser(bp);
																mP.send(p);
															}
														}
														else{
															MessageParser mP = Message.NOT_ONLINE.getParser(bp);
															mP.parseVariable(Variable.RECEIVER, a[1]);
															mP.send(p);
														}
													}
													else{
														MessageParser mP = cmd.getWrongUsage().getParser(bp);
														mP.parseVariable(Variable.CMD, a[0].toLowerCase());
														mP.send(p);
													}
												}
												else{
													MessageParser mP = Message.MUTED.getParser(bp);
													mP.send(p);
												}
											}
											break;
										case REPLY:
											{
												if(!bp.isMuted()){
													if(a.length > 1){
														if(bp.getLastMSGTo() != null){
                                                            ProxiedPlayer p2 = bp.getLastMSGTo().getPlayer();

															if(bp.hasMSGEnabled()){
																String message = e.getMessage().substring(a[0].length() + 1);
															
																if(bp.canMessage(message, Cooldown.LAST_MSG)){
																	BungeePlayer bp2 = msg.getBungeePlayers().get(p2);
																	
																	if(!bp2.hasMSGEnabled()){
																		if(msg.tellPMDisabled()){
																			MessageParser mP = Message.PM_TOGGLED_OTHER.getParser(bp);
																			mP.parseVariable(Variable.RECEIVER, p2.getName());
																			mP.send(p);
																		}
																		else{
																			MessageParser mP = Message.PM_TO_SENDER.getParser(bp);
																			mP.parseVariable(Variable.SENDER, p.getName());
																			mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																			mP.parseVariable(Variable.RECEIVER, p2.getName());
																			mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerNames().get(p2.getServer().getInfo()));
																			mP.parseVariable(Variable.MSG, message);
																			
																			if(!bp.hasIgnored(bp2, mP, message)){
																				mP.send(p);
																			}
																		}
																	}
																	else{
																		MessageParser mP = Message.PM_TO_SENDER.getParser(bp);
																		mP.parseVariable(Variable.SENDER, p.getName());
																		mP.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																		mP.parseVariable(Variable.RECEIVER, p2.getName());
																		mP.parseVariable(Variable.SERVER_RECEIVER, msg.getServerNames().get(p2.getServer().getInfo()));
																		mP.parseVariable(Variable.MSG, message);
																		
																		if(!bp.hasIgnored(bp2, mP, message) && !mP.isCancelled()){
																			MessageParser mP2 = Message.PM_TO_RECEIVER.getParser(bp);
																			mP2.parseVariable(Variable.SENDER, p.getName());
																			mP2.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																			mP2.parseVariable(Variable.RECEIVER, p2.getName());
																			mP2.parseVariable(Variable.SERVER_RECEIVER, msg.getServerNames().get(p2.getServer().getInfo()));
																			mP2.parseVariable(Variable.MSG, message);
																			mP2.send(p2, p);
																			mP.send(p, p2);
																			
																			MessageParser mP3 = Message.SPY_MESSAGE.getParser(bp);
																			mP3.parseVariable(Variable.SENDER, p.getName());
																			mP3.parseVariable(Variable.SERVER_SENDER, msg.getServerNames().get(p.getServer().getInfo()));
																			mP3.parseVariable(Variable.RECEIVER, p2.getName());
																			mP3.parseVariable(Variable.SERVER_RECEIVER, msg.getServerNames().get(p2.getServer().getInfo()));
																			mP3.parseVariable(Variable.MSG, message);
																			
																			for(ProxiedPlayer player : ProxyServer.getInstance().getPlayers()){
																				BungeePlayer bplayer = msg.getBungeePlayers().get(player);
																				
																				if(bplayer.isSpy()){
																					mP3.send(player);
																				}
																			}
																			
																			if(msg.hasReplyInfo() && bp2.getLastMSG() == null){
																				MessageParser mP4 = Message.REPLY_INFO.getParser(bp);
																				mP4.send(p2);
																			}
																			
																			bp.setLastMSG(message);
																			bp.setLastMSGTo(bp2);
																			bp2.setLastMSGTo(bp);
																			
																			lM.info(LogReadType.PRIVATE_MESSAGES, p.getServer().getInfo(), "[MSG] " + p.getName() + " > " + p2.getName() + ": " + message);
																		}
																	}
																}
															}
															else{
																MessageParser mP = Message.PM_TOGGLED.getParser(bp);
																mP.send(p);
															}
														}
														else{
															MessageParser mP = Message.NO_RECEIVER.getParser(bp);
															mP.send(p);
														
															bp.setLastMSGTo(null);
														}
													}
													else{
														MessageParser mP = cmd.getWrongUsage().getParser(bp);
														mP.parseVariable(Variable.CMD, a[0].toLowerCase());
														mP.send(p);
													}
												}
												else{
													MessageParser mP = Message.MUTED.getParser(bp);
													mP.send(p);
												}
											}
											break;
									}
								}
								else{
									MessageParser mP = cmd.getNoPermission().getParser(bp);
									mP.send(p);
								}
								
								break;
							}
						}
					}
				}
			}
		}
	}
}
