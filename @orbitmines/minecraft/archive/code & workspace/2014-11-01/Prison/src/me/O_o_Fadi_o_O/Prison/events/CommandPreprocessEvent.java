package me.O_o_Fadi_o_O.Prison.events;

import me.O_o_Fadi_o_O.Prison.Start;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

public class CommandPreprocessEvent implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onUnknown(PlayerCommandPreprocessEvent e){
		if(!(e.isCancelled())){
			Player p = e.getPlayer();
			
			String s = e.getMessage().split(" ") [0];
			
			HelpTopic topic = Bukkit.getServer().getHelpMap().getHelpTopic(s);
			
			if(topic == null){
				
				p.sendMessage(Start.TAG + "Unknown Command: §4" + s + "§7. Use §4/help§7 for Help!");
				e.setCancelled(true);
			}
			
		    String[] args = e.getMessage().split(" ");
		    if(args[0].equalsIgnoreCase("/vote")){
		    	e.setCancelled(true);
		    	
				p.sendMessage("");
				p.sendMessage("§6§lOrbitMines§b§lVote §8| §7Vote for §b§lRewards§7!");
				p.sendMessage("§6§lOrbitMines§b§lVote §8| §7Reward in the §4§lPrison§7 Server:");
				p.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				if(p.hasPermission("nametag.group.A")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l2.500.000$ §7(§c§lA§7)");	
				}
				else if(p.hasPermission("nametag.group.B")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l2.000.000$ §7(§a§lB§7)");	
				}
				else if(p.hasPermission("nametag.group.C")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l1.500.000$ §7(§b§lC§7)");	
				}
				else if(p.hasPermission("nametag.group.D")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l1.000.000$ §7(§d§lD§7)");	
				}
				else if(p.hasPermission("nametag.group.E")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l600.000$ §7(§5§lE§7)");	
				}
				else if(p.hasPermission("nametag.group.F")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l400.000$ §7(§1§lF§7)");	
				}
				else if(p.hasPermission("nametag.group.G")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l300.000$ §7(§e§lG§7)");	
				}
				else if(p.hasPermission("nametag.group.H")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l200.000$ §7(§4§lH§7)");	
				}
				else if(p.hasPermission("nametag.group.I")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l175.000$ §7(§2§lI§7)");	
				}
				else if(p.hasPermission("nametag.group.J")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l150.000$ §7(§f§lJ§7)");	
				}
				else if(p.hasPermission("nametag.group.K")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l125.000$ §7(§6§lK§7)");	
				}
				else if(p.hasPermission("nametag.group.L")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l100.000$ §7(§7§lL§7)");	
				}
				else if(p.hasPermission("nametag.group.M")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l75.000$ §7(§8§lM§7)");	
				}
				else if(p.hasPermission("nametag.group.N")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l50.000$ §7(§0§lN§7)");	
				}
				else if(p.hasPermission("nametag.group.O")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l40.000$ §7(§3§lO§7)");	
				}
				else if(p.hasPermission("nametag.group.P")){
					p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §c§l25.000$ §7(§f§lP§7)");	
				}
				p.sendMessage("§6§lOrbitMines§b§lVote §8| §7  - §e§l10 EXP Levels");
				p.sendMessage("§6§lOrbitMines§b§lVote §8| §7");
				p.sendMessage("§6§lOrbitMines§b§lVote §8| §7Vote at §b§lwww.orbitmines.com");
				p.playSound(p.getLocation(), Sound.SUCCESSFUL_HIT, 5, 1);
		    }
		}
	}
}
