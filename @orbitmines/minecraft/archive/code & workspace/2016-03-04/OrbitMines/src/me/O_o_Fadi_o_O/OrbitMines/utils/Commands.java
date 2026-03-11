package me.O_o_Fadi_o_O.OrbitMines.utils;

import me.O_o_Fadi_o_O.OrbitMines.Start;
import me.O_o_Fadi_o_O.OrbitMines.managers.VoteManager;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.Server;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.StaffRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.Utils.VIPRank;
import me.O_o_Fadi_o_O.OrbitMines.utils.survival.SurvivalPlayer;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Commands implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String l, String[] a) {
		if(ServerData.isServer(Server.HUB) && cmd.getName().equalsIgnoreCase("setlastdonator")){
			if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
				if(a.length == 0){
					sender.sendMessage("4§lOP §8| §7Gebruik §6/setlastdonator <speler>");
				}
				else if(a.length == 1){
		    		ServerData.getHub().setLastDonatorString(a[0]);
					sender.sendMessage("§4§lOP §8| §7Je hebt §6" + a[0] + "§7 als de laatste donateur gezet§7!");
				}
				else{
					sender.sendMessage("§4§lOP §8| §7Gebruik §6/setlastdonator <speler>");
				}
			}
			else{
				OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
				omp.unknownCommand("/" + cmd.getName().toLowerCase());
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setvip")){
			if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
				if(a.length == 1){
					sender.sendMessage("§4§lOP §8| §7Gebruik §6/setvip <speler> <vip>");
				}
				else if(a.length == 2){			
		    		Player p2 = Utils.getPlayer(a[0]);
		    		
		    		if(p2 != null){
						try{
							VIPRank rank = VIPRank.valueOf(a[1]);
							
							OMPlayer omp = OMPlayer.getOMPlayer(p2);
							omp.setVIP(rank);
							
							sender.sendMessage("§4§lOP §8| §7Je hebt §6" + p2.getName() + "'s§7 VIP Rank veranderd in §6" + rank.toString() + "§7!");
							
						}catch(Exception ex){
							sender.sendMessage("§4§lOP §8| §7VIP Rank §6" + a[1] + "§7 is geen rank!");
						}
		    		}
		    		else{
		    			sender.sendMessage("§4§lOP §8| §6" + a[0] + "§7 is niet §aonline§7!");
		    		}
				}
				else{
					sender.sendMessage("§4§lOP §8| §7Gebruik §6/setvip <speler> <vip>");
				}
			}
			else{
				OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
				omp.unknownCommand("/" + cmd.getName().toLowerCase());
			}
		}
		else if(cmd.getName().equalsIgnoreCase("setstaff")){
			if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
				if(a.length == 1){
					sender.sendMessage("§4§lOP §8| §7Gebruik §6/setstaff <speler> <staff>");
				}
				else if(a.length == 2){			
		    		Player p2 = Utils.getPlayer(a[0]);
		    		
		    		if(p2 != null){
						try{
							StaffRank rank = StaffRank.valueOf(a[1]);
							
							OMPlayer omp = OMPlayer.getOMPlayer(p2);
							omp.setStaff(rank);
							
							sender.sendMessage("§4§lOP §8| §7Je hebt §6" + p2.getName() + "'s§7 Staff Rank veranderd in §6" + rank.toString() + "§7!");
							
						}catch(Exception ex){
							sender.sendMessage("§4§lOP §8| §7Staff Rank §6" + a[1] + "§7 is geen rank!");
						}
		    		}
		    		else{
		    			sender.sendMessage("§4§lOP §8| §6" + a[0] + "§7 is niet §aonline§7!");
		    		}
				}
				else{
					sender.sendMessage("§4§lOP §8| §7Gebruik §6/setstaff <speler> <staff>");
				}
			}
			else{
				OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
				omp.unknownCommand("/" + cmd.getName().toLowerCase());
			}
		}
		else if(cmd.getName().equalsIgnoreCase("resetMonthlyVIPPoints")){
			if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
				
				if(a.length == 1){
					Player player = Utils.getPlayer(a[0]);
					
					if(player != null){
						final OMPlayer omplayer = OMPlayer.getOMPlayer(player);
						omplayer.setReceivedMonthlyBonus(false);
						
						final Title t = new Title("", "§7Je kan nu je §bMaandelijkse VIP Points§7 ontvangen.");
						new BukkitRunnable(){
							public void run(){
								t.send(omplayer.getPlayer());
							}
						}.runTaskLater(Start.getInstance(), 100);
						
						sender.sendMessage("§b§lVIP Points §8| §b" + player.getName() + "§7 kan nu zijn/haar §b§lMaandelijkse VIP Points§7 ontvangen!");
					}
					else{
						sender.sendMessage("§b§lVIP Points §8| §b" + a[0] + "§7 is niet §aonline§7!");
					}
				}
				else{
					sender.sendMessage("§b§lVIP Points §8| §7Gebruik §b/resetMonthlyVIPPoints <speler>");
				}
				
			}
		}
		else if(cmd.getName().equalsIgnoreCase("giveMonthlyVIPPoints")){
			if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
				
				if(a.length == 1){
					Player player = Utils.getPlayer(a[0]);
					
					if(player != null){
						final OMPlayer omplayer = OMPlayer.getOMPlayer(player);
						
						if(!omplayer.hasReceivedMonthlyBonus()){
							int amount = omplayer.getVIPRank().getMonthlyBonus();
							if(amount != 0){
								omplayer.addVIPPoints(amount);
								omplayer.setReceivedMonthlyBonus(true);
							}
							sender.sendMessage("§b§lVIP Points §8| §7Je hebt §b" + omplayer.getName() + " §b§l" + amount + " VIP Points§7 gegeven!");
						}
						else{
							sender.sendMessage("§b§lVIP Points §8| §b" + a[0] + "§7 heeft al zijn/haar §bMaandelijkse VIP Points§7 ontvangen!");
							
							final Title t = new Title("", "§7Je hebt al jouw §bMaandelijkse VIP Points§7!");
							new BukkitRunnable(){
								public void run(){
									t.send(omplayer.getPlayer());
								}
							}.runTaskLater(Start.getInstance(), 100);
						}
					}
					else{
						sender.sendMessage("§b§lVIP Points §8| §b" + a[0] + "§7 is niet §aonline§7!");
					}
				}
				else{
					sender.sendMessage("§b§lVIP Points §8| §7Gebruik §b/giveMonthlyVIPPoints <speler>");
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("votes")){
			if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
				if(a.length == 0){
					if(sender instanceof Player){
						OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
						sender.sendMessage("§b§lVote §8| §7Jouw votes deze maand: §b§l" + omp.getVotes());
					}
				}
				else if(a.length == 2){
					if(a[0].equalsIgnoreCase("add")){
						sender.sendMessage("§b§lVote §8| §7Je hebt §b" + a[1] + "§7 een §b§lVote§7 gegeven!");
						new VoteManager().registerVote(a[1]);
					}
					else{
						sender.sendMessage("§b§lVote §8| §7Gebruik §b/votes add <speler>");
					}
				}
				else{
					sender.sendMessage("§b§lVote §8| §7Gebruik §b/votes add <speler>");
				}
			}
			else{
				OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
				sender.sendMessage("§b§lVote §8| §7Jouw votes deze maand: §b§l" + omp.getVotes());
			}
		}
		else if(cmd.getName().equalsIgnoreCase("vippoints")){
			if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
				if(a.length == 0){
					if(sender instanceof Player){
						OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
						sender.sendMessage("§b§lVIP Points §8| §7Jouw VIP Points: §b§l" + omp.getVIPPoints());
					}
				}
				else if(a.length == 3){
					if(a[0].equalsIgnoreCase("give")){
						int amount = Integer.parseInt(a[2]);
						Player player = Utils.getPlayer(a[1]);
						
						if(player != null){
							OMPlayer omplayer = OMPlayer.getOMPlayer(player);
							omplayer.addVIPPoints(amount);
							
							sender.sendMessage("§b§lVIP Points §8| §7Je hebt §b" + a[1] + " §b§l" + amount + " VIP Points§7 gegeven!");
						}
						else{
							sender.sendMessage("§b§lVIP Points §8| §b" + a[1] + " §7is niet §aonline§7!");
						}
					}
					else if(a[0].equalsIgnoreCase("remove")){
						int amount = Integer.parseInt(a[2]);
						Player player = Utils.getPlayer(a[1]);
						
						if(player != null){
							OMPlayer omplayer = OMPlayer.getOMPlayer(player);
							omplayer.removeVIPPoints(amount);
							
							sender.sendMessage("§b§lVIP Points §8| §7Je hebt §b§l" + amount + " VIP Points§7 van §b" + a[1] + "§7 weggehaald!");
						}
						else{
							sender.sendMessage("§b§lVIP Points §8| §b" + a[1] + " §7is niet §aonline§7!");
						}
					}
					else{
						sender.sendMessage("§b§lVIP Points §8| §7/vippoints give|remove <speler> <aantal>");
					}
				}
				else{
					sender.sendMessage("§b§lVIP Points §8| §7/vippoints give|remove <speler> <aantal>");
				}
			}
			else{
				OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
				sender.sendMessage("§b§lVIP Points §8| §7Jouw VIP Points: §b§l" + omp.getVIPPoints());
			}
		}
		else if(cmd.getName().equalsIgnoreCase("omt")){
			if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
				if(a.length == 0){
					if(sender instanceof Player){
						OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
						sender.sendMessage("§e§lOMT §8| §7Jouw OrbitMines Tokens: §e§l" + omp.getOrbitMinesTokens());
					}
				}
				else if(a.length == 3){
					if(a[0].equalsIgnoreCase("give")){
						int amount = Integer.parseInt(a[2]);
						Player player = Utils.getPlayer(a[1]);
						
						if(player != null){
							OMPlayer omplayer = OMPlayer.getOMPlayer(player);
							omplayer.addOrbitMinesTokens(amount);
							
							sender.sendMessage("§e§lOMT §8| §7Je hebt §e" + a[1] + " §e§l" + amount + " OMT§7 gegeven!");
							
						}
						else{
							sender.sendMessage("§e§lOMT §8| §e" + a[1] + " §7is niet §aonline§7!");
						}
					}
					else if(a[0].equalsIgnoreCase("remove")){
						int amount = Integer.parseInt(a[2]);
						Player player = Utils.getPlayer(a[1]);
						
						if(player != null){
							OMPlayer omplayer = OMPlayer.getOMPlayer(player);
							omplayer.removeOrbitMinesTokens(amount);
							
							sender.sendMessage("§e§lOMT §8| §7Je hebt §e§l" + amount + " OMT§7 van §e" + a[1] + "§7 weggehaald!");
						}
						else{
							sender.sendMessage("§e§lOMT §8| §e" + a[1] + " §7is niet §aonline§7!");
						}
					}
					else{
						sender.sendMessage("§e§lOMT §8| §7/omt give|remove <speler> <aantal>");
					}
				}
				else{
					sender.sendMessage("§e§lOMT §8| §7/omt give|remove <speler> <aantal>");
				}
			}
			else{
				OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
				sender.sendMessage("§e§lOMT §8| §7Jouw OrbitMines Tokens: §e§l" + omp.getOrbitMinesTokens());
			}
		}
		else if(cmd.getName().equalsIgnoreCase("money")){
			if(ServerData.isServer(Server.SURVIVAL)){
				if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
					if(a.length == 0){
						if(sender instanceof Player){
							OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
							sender.sendMessage("§7Jouw Geld: §2§l" + omp.getSurvivalPlayer().getMoney() + "$");
						}
					}
					else if(a.length == 3){
						if(a[0].equalsIgnoreCase("give")){
							int amount = Integer.parseInt(a[2]);
							Player player = Utils.getPlayer(a[1]);
							
							if(player != null){
								SurvivalPlayer sp = OMPlayer.getOMPlayer(player).getSurvivalPlayer();
								sp.addMoney(amount);
								
								sender.sendMessage("§7Je hebt §2" + a[1] + " §2§l" + a[2] + "$§7 gegeven!");
							}
							else{
								sender.sendMessage("§2" + a[1] + " §7is niet §aonline§7!");
							}
						}
						else if(a[0].equalsIgnoreCase("remove")){
							int amount = Integer.parseInt(a[2]);
							Player player = Utils.getPlayer(a[1]);
							
							if(player != null){
								SurvivalPlayer sp = OMPlayer.getOMPlayer(player).getSurvivalPlayer();
								sp.removeMoney(amount);
								
								sender.sendMessage("§7Je hebt §2§l" + a[2] + "$§7 van §2" + a[1] + "§7 weggehaald!");
							}
							else{
								sender.sendMessage("§2" + a[1] + " §7is niet §aonline§7!");
							}
							
						}
						else{
							sender.sendMessage("§7/money give|remove <speler> <aantal>");
						}
						
					}
					else{
						sender.sendMessage("§7/money give|remove <speler> <aantal>");
					}
				}
				else{
					OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
					sender.sendMessage("§7Jouw Geld: §2§l" + omp.getSurvivalPlayer().getMoney() + "$");
				}
			}
			else{
				if(sender instanceof Player){
					OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
					omp.unknownCommand("/" + cmd.getName());
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("addhomes")){
			if(ServerData.isServer(Server.SURVIVAL)){
				if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
					if(a.length == 2){
						final int amount = Integer.parseInt(a[1]);
						final Player player = Utils.getPlayer(a[0]);
						
						if(player != null){
							SurvivalPlayer sp = OMPlayer.getOMPlayer(player).getSurvivalPlayer();
							sp.setExtraHomes(sp.getExtraHomes() + amount);
							
							sender.sendMessage("§7Je hebt §2" + a[0] + " §6" + a[1] + " Homes§7 gegeven!");
							
							new BukkitRunnable(){
								public void run(){
									Title t = new Title("", "§6+" + amount + " Homes");
									t.send(player);
								}
							}.runTaskLater(Start.getInstance(), 40);
						}
						else{
							sender.sendMessage("§2" + a[0] + " §7is niet §aonline§7!");
						}
					}
					else{
						sender.sendMessage("§7/addhomes <speler> <aantal>");
					}
				}
				else{
					OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
					omp.unknownCommand("/" + cmd.getName());
				}
			}
			else{
				if(sender instanceof Player){
					OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
					omp.unknownCommand("/" + cmd.getName());
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("addshops")){
			if(ServerData.isServer(Server.SURVIVAL)){
				if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
					if(a.length == 2){
						final int amount = Integer.parseInt(a[1]);
						final Player player = Utils.getPlayer(a[0]);
						
						if(player != null){
							SurvivalPlayer sp = OMPlayer.getOMPlayer(player).getSurvivalPlayer();
							sp.setExtraShops(sp.getExtraShops() + amount);
							
							sender.sendMessage("§7Je hebt §2" + a[0] + " §6" + a[1] + " Chest Shops§7 gegeven!");
							
							new BukkitRunnable(){
								public void run(){
									Title t = new Title("", "§6+" + amount + " Chest Shops");
									t.send(player);
								}
							}.runTaskLater(Start.getInstance(), 40);
						}
						else{
							sender.sendMessage("§2" + a[0] + " §7is niet §aonline§7!");
						}
					}
					else{
						sender.sendMessage("§7/addshops <speler> <aantal>");
					}
				}
				else{
					OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
					omp.unknownCommand("/" + cmd.getName());
				}
			}
			else{
				if(sender instanceof Player){
					OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
					omp.unknownCommand("/" + cmd.getName());
				}
			}
		}
		else if(cmd.getName().equalsIgnoreCase("addwarps")){
			if(ServerData.isServer(Server.SURVIVAL)){
				if(sender instanceof ConsoleCommandSender || sender instanceof Player && OMPlayer.getOMPlayer((Player) sender).isOpMode()){
					if(a.length == 2){
						final int amount = Integer.parseInt(a[1]);
						final Player player = Utils.getPlayer(a[0]);
						
						if(player != null){
							SurvivalPlayer sp = OMPlayer.getOMPlayer(player).getSurvivalPlayer();
							sp.setExtraWarps(sp.getExtraWarps() + amount);
							
							sender.sendMessage("§7Je hebt §2" + a[0] + " §3" + a[1] + " Warp§7 gegeven!");
							
							new BukkitRunnable(){
								public void run(){
									Title t = new Title("", "§3+" + amount + " Warp");
									t.send(player);
								}
							}.runTaskLater(Start.getInstance(), 40);
						}
						else{
							sender.sendMessage("§2" + a[0] + " §7is niet §aonline§7!");
						}
					}
					else{
						sender.sendMessage("§7/addwarps <speler> <aantal>");
					}
				}
				else{
					OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
					omp.unknownCommand("/" + cmd.getName());
				}
			}
			else{
				if(sender instanceof Player){
					OMPlayer omp = OMPlayer.getOMPlayer((Player) sender);
					omp.unknownCommand("/" + cmd.getName());
				}
			}
		}
		else{}
		
		return false;
	}
}
